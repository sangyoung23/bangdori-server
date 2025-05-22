package bangdori.api.domain.product.service;

import bangdori.api.domain.product.repository.ProductRemarksInfoRepository;
import bangdori.api.domain.product.repository.ProductRepository;
import bangdori.api.domain.product.dto.ProductDTO;
import bangdori.api.domain.product.entity.ProductImageInfo;
import bangdori.api.domain.product.entity.ProductInfo;
import bangdori.api.domain.product.entity.ProductRemarksInfo;
import bangdori.api.domain.product.repository.ProductImageInfoRepository;
import bangdori.api.domain.user.dto.UserPublicInfoDTO;
import bangdori.api.domain.user.entity.UserInfo;
import bangdori.api.domain.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    @Value("${env.serverUrl}")
    String serverUrl;

    private final ProductRepository productRepository;
    private final ProductRemarksInfoRepository productRemarksInfoRepository;
    private final ProductImageInfoRepository productImageInfoRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    public List<ProductDTO> getProductList(Long corpNo) {
        List<ProductInfo> products = productRepository.findAllWithRemarksByUseYnAndCorpNo("1", corpNo);

        return products.stream().map(product -> {
            List<String> remarkCds = product.getProductRemarksInfos()
                    .stream()
                    .map(ProductRemarksInfo::getRemarkCd)
                    .collect(Collectors.toList());

            return ProductDTO.fromEntity(product, remarkCds);
        }).collect(Collectors.toList());
    }

    @Transactional
    public void saveProduct(ProductInfo productInfo, List<ProductRemarksInfo> remarksInfoList, List<ProductImageInfo> imageInfoList) {
        ProductInfo savedProductInfo = productRepository.save(productInfo);

        List<ProductRemarksInfo> remarks = buildRemarksInfoList(remarksInfoList, savedProductInfo);
        productRemarksInfoRepository.saveAll(remarks);

        List<ProductImageInfo> images = buildImageInfoList(imageInfoList, savedProductInfo);
        productImageInfoRepository.saveAll(images);
    }

    private List<ProductRemarksInfo> buildRemarksInfoList(List<ProductRemarksInfo> remarksInfoList, ProductInfo productInfo) {
        return remarksInfoList.stream()
                .map(remarksInfo -> ProductRemarksInfo.builder()
                        .productInfo(productInfo)
                        .remarkCd(remarksInfo.getRemarkCd())
                        .useYn("1")
                        .regDtm(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
    }

    private List<ProductImageInfo> buildImageInfoList(List<ProductImageInfo> imageInfoList, ProductInfo productInfo) {
        return imageInfoList.stream()
                .map(imageInfo -> ProductImageInfo.builder()
                        .productInfo(productInfo)
                        .managementFileName(imageInfo.getManagementFileName())
                        .realFileName(imageInfo.getRealFileName())
                        .useYn("1")
                        .regDtm(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
    }

    public void createProduct(ProductDTO productDto, List<String> remarkCds, List<MultipartFile> imges) {
        ProductInfo productInfo = ProductInfo.fromDto(productDto);

        List<ProductRemarksInfo> remarksInfoList = Optional.ofNullable(remarkCds)
                .orElse(Collections.emptyList())
                .stream()
                .map(remarkCd -> ProductRemarksInfo.builder()
                        .productInfo(productInfo)
                        .remarkCd(remarkCd)
                        .useYn("1")
                        .regDtm(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        List<ProductImageInfo> imageInfoList = Optional.ofNullable(imges)
                .orElse(Collections.emptyList())
                .stream()
                .map(image -> {
                    String fileName = fileStorageService.saveFile(image);
                    return ProductImageInfo.builder()
                            .productInfo(productInfo)
                            .managementFileName(fileName)
                            .realFileName(image.getOriginalFilename())
                            .useYn("1")
                            .regDtm(LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());

        saveProduct(productInfo, remarksInfoList, imageInfoList);
    }

    @Transactional
    public void updateNewDtm(Long prodNo, Map<String, Object> params) {
        Long userNo = Long.parseLong(params.get("userNo").toString());

        ProductInfo product = productRepository.findById(prodNo)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        UserInfo user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        product.updateNewDtm();
        product.updateChgUserId(user.getUserNo());

        productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(Long prodNo) {
        ProductInfo productInfo = productRepository.findById(prodNo).orElse(null);
        if (productInfo != null) {
            productInfo.updateUseYn("0");
        } else {
            throw new IllegalArgumentException("Product not found with id: " + prodNo);
        }
    }

    public List<String> getImgsrcByProdNo (Long prodNo){
        List<String> mngFileNms =  productImageInfoRepository.findByProductInfoProdNoAndUseYn(prodNo,"1")
                .stream()
                .map(ProductImageInfo::getManagementFileName)
                .collect(Collectors.toList());


        List<String> mngFileNm = new ArrayList<>();
        for(String fileNm :mngFileNms){
            fileNm = serverUrl + "api/ContentItem?image="+ fileNm;
            mngFileNm.add(fileNm);
        }

     return mngFileNm;
    }

    @Transactional
    public void removeFileAndUpdateDB(Map<String, String> params) {
        String filePath = params.get("filePath");
        String fileName = filePath.substring(filePath.lastIndexOf("=") + 1);
        int updateCount = productImageInfoRepository.updateUseYnByRealFileName(fileName, "0");

        if (updateCount == 0) {
            throw new IllegalArgumentException("파일의 useYn 업데이트 실패");
        }
    }

    @Transactional
    public void updateProduct(Long prodNo, ProductDTO productDto, List<String> remarkCds, List<MultipartFile> images) {
        ProductInfo existingProductInfo = productRepository.findByProdNo(prodNo)
                .orElseThrow(() -> new IllegalArgumentException("해당 매물 정보를 찾을 수 없습니다."));

        existingProductInfo.updateFromDto(productDto);

        updateRemarks(prodNo, remarkCds);
        updateImages(existingProductInfo, images);
    }

    private void updateRemarks(Long prodNo, List<String> remarkCds) {
        List<ProductRemarksInfo> existingRemarks = productRemarksInfoRepository.findByProductInfoProdNo(prodNo);

        if (!existingRemarks.isEmpty()) {
            productRemarksInfoRepository.updateUseYnByProdNo(prodNo, "0");
        }

        if (remarkCds == null || remarkCds.isEmpty()) {
            return;
        }

        List<ProductRemarksInfo> newRemarks = remarkCds.stream()
                .map(remarkCd -> ProductRemarksInfo.builder()
                        .productInfo(ProductInfo.builder().prodNo(prodNo).build())
                        .remarkCd(remarkCd)
                        .useYn("1")
                        .regDtm(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        productRemarksInfoRepository.saveAll(newRemarks);
    }

    private void updateImages(ProductInfo productInfo, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) return;

        List<ProductImageInfo> imageInfoList = images.stream()
                .map(image -> {
                    String fileName = fileStorageService.saveFile(image);
                    return ProductImageInfo.builder()
                            .productInfo(productInfo)
                            .managementFileName(fileName)
                            .realFileName(image.getOriginalFilename())
                            .useYn("1")
                            .regDtm(LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());

        productImageInfoRepository.saveAll(imageInfoList);
    }

    public List<UserPublicInfoDTO> getUserList(Long userNo) {
        return userRepository.findByCorpInfoOfUserNo(userNo);
    }
}
