package bangdori.api.product.service;

import bangdori.api.product.dto.CodeDTO;
import bangdori.api.product.dto.ProductDTO;
import bangdori.api.product.dto.ProductImageInfoDTO;
import bangdori.api.product.entity.ProductImageInfo;
import bangdori.api.product.entity.ProductInfo;
import bangdori.api.product.entity.ProductRemarksInfo;
import bangdori.api.product.repository.ProductImageInfoRepository;
import bangdori.api.product.repository.ProductRemarksInfoRepository;
import bangdori.api.product.repository.ProductRepository;
import bangdori.api.user.dto.UserPulicInfoDTO;
import bangdori.api.user.entity.UserInfo;
import bangdori.api.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductRemarksInfoRepository productRemarksInfoRepository;
    private final ProductImageInfoRepository productImageInfoRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

public List<ProductDTO> getProductList(Long corpNo) {
    List<ProductInfo> products = productRepository.findAllByUseYnAndCorpNoOrderByNewDtmDesc("1", corpNo );

    return products.stream().map(product -> {
        // ProductInfo 엔티티를 DTO로 변환하면서, 해당 ProductInfo에 해당하는 모든 remarkCd를 가져오기
        List<String> remarkCds = productRemarksInfoRepository.findByProductInfoProdNo(product.getProdNo())
                .stream()
                .map(ProductRemarksInfo::getRemarkCd)
                .collect(Collectors.toList());  // 여러 개의 remarkCd를 리스트로 수집

        return ProductDTO.fromEntity(product, remarkCds); // remarkCd 리스트를 전달하여 DTO 생성
    }).collect(Collectors.toList());
}

    @Transactional
    public void saveProduct(ProductInfo productInfo, List<ProductRemarksInfo> remarksInfoList , List<ProductImageInfo> imageInfoList) {
        // ProductInfo 저장

        ProductInfo savedProductInfo = productRepository.save(productInfo);

        List<ProductRemarksInfo> updatedRemarksInfoList = remarksInfoList.stream()
                .map(remarksInfo -> ProductRemarksInfo.builder()
                        .productInfo(savedProductInfo)   // 저장된 productInfo 객체를 설정
                        .remarkCd(remarksInfo.getRemarkCd())  // remarkCd 설정
                        .useYn("1")                 // 기본값 Y 설정
                        .regDtm(LocalDateTime.now()) // 현재 시간으로 등록일시 설정
                        .build())
                .collect(Collectors.toList());
        // ProductRemarksInfo 저장
        productRemarksInfoRepository.saveAll(updatedRemarksInfoList);

        // 3. ProductImageInfo 저장
        List<ProductImageInfo> updatedImageInfoList = imageInfoList.stream()
                .map(imageInfo -> ProductImageInfo.builder()
                        .productInfo(savedProductInfo)
                        .managementFileName(imageInfo.getManagementFileName())
                        .realFileName(imageInfo.getRealFileName())
                        .useYn("1")
                        .regDtm(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());
        productImageInfoRepository.saveAll(updatedImageInfoList);
    }


    @Transactional
    public void updateNewDtm(Long prodNo, Long userNo) {
        ProductInfo product = productRepository.findById(prodNo)
                .orElseThrow(() -> new IllegalArgumentException("Product not found"));

        UserInfo user = userRepository.findById(userNo)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        product.updateNewDtm();
        product.updateChgUserId(user.getUserNo());



        // 저장
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


    @Value("${env.serverUrl}")
    String serverUrl;


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
    public void removeFileAndUpdateDB(String fileName) throws Exception {
        // 1. 파일 삭제 처리  >> 나중에 합시다 ?
       /* Path file = Paths.get("static/" + fileName); // 파일 경로
        System.out.println("this error********* file:     "+ file);

        boolean fileDeleted = Files.deleteIfExists(file); // 파일이 존재하면 삭제
        System.out.println("this error********* fileDeleted  :     "+ fileDeleted);
*/
        // 2. DB에서 해당 파일 정보의 useYn을 0으로 업데이트

        // 파일명으로 해당 파일 정보를 찾고, useYn을 "N"으로 업데이트
        System.out.println("fileName ?? " + fileName);
        int updateCount = productImageInfoRepository.updateUseYnByRealFileName(fileName, "0");

        System.out.println("fileName ?? " + fileName);
        // DB 업데이트가 되지 않았다면 예외 처리
        if (updateCount == 0) {
            throw new Exception("파일의 useYn 업데이트 실패");
        }
    }

    public ProductInfo findByProdNo(Long prodNo) {
        return productRepository.findByProdNo(prodNo).orElse(null);
    }

    @Transactional
    public void updateRemarks(Long prodNo, List<ProductRemarksInfo> updatedRemarksInfoList) {
        // 상품 번호로 관련 remarks 조회
        List<ProductRemarksInfo> existingRemarks = productRemarksInfoRepository.findByProductInfoProdNo(prodNo);
        
        // 기존 remarks 데이터 삭제
        if (!existingRemarks.isEmpty()) {
            productRemarksInfoRepository.updateUseYnByProdNo(prodNo , "0");
        }

        // 새로운 remarks 데이터 저장
        productRemarksInfoRepository.saveAll(updatedRemarksInfoList);
    }


    @Transactional
    public void updateSaveImg(ProductInfo existingProductInfo, List<ProductImageInfo> imageInfoList) {

        List<ProductImageInfo> updatedImageInfoList = imageInfoList.stream()
                .map(imageInfo -> ProductImageInfo.builder()
                        .productInfo(existingProductInfo)
                        .managementFileName(imageInfo.getManagementFileName())
                        .realFileName(imageInfo.getRealFileName())
                        .useYn("1")
                        .regDtm(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        productImageInfoRepository.saveAll(updatedImageInfoList);
    }

    public List<UserPulicInfoDTO> getUserList(Long userNo) {
        return userRepository.findByCorpInfoOfUserNo(userNo);
    }
}
