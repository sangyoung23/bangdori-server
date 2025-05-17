package bangdori.api.domain.product.controller;

import bangdori.api.comm.response.ApiResponse;
import bangdori.api.comm.Constants;
import bangdori.api.domain.product.dto.ProductDTO;
import bangdori.api.domain.product.entity.ProductImageInfo;
import bangdori.api.domain.product.entity.ProductInfo;
import bangdori.api.domain.product.entity.ProductRemarksInfo;
import bangdori.api.domain.product.service.FileStorageService;
import bangdori.api.domain.product.service.ProductService;
import bangdori.api.domain.user.dto.UserPublicInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;
    private final FileStorageService fileStorageService;

    // 매물 조회
    @GetMapping
    public ApiResponse getProductList(@RequestParam Long corpNo) {
        List<ProductDTO> productList = productService.getProductList(corpNo);
        return new ApiResponse()
                .success()
                .addResult(Constants.KEY_LIST, productList);
    }

    // 관리자 정보 조회
    @GetMapping("/users")
    public ApiResponse getUserList(@RequestParam Long userNo) {
        List<UserPublicInfoDTO> userInfoList = productService.getUserList(userNo);
        return new ApiResponse()
                .success()
                .addResult(Constants.KEY_LIST, userInfoList);
    }

    // 매물 생성
    @PostMapping
    public ApiResponse createProduct(@RequestPart("productDto") ProductDTO productDto,
                                     @RequestPart(value = "remarkCds", required = false) List<String> remarkCds,
                                     @RequestPart(value = "imges", required = false) List<MultipartFile> imges) {

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

        productService.saveProduct(productInfo, remarksInfoList, imageInfoList);
        return new ApiResponse().success();
    }

    // 매물 수정
    @PutMapping("/{prodNo}")
    public ApiResponse updateProduct(@PathVariable Long prodNo,
                                     @RequestPart("productDto") ProductDTO productDto,
                                     @RequestPart(value = "remarkCds", required = false) List<String> remarkCds,
                                     @RequestPart(value = "imges", required = false) List<MultipartFile> imges) {

        ProductInfo existingProductInfo = productService.findByProdNo(prodNo);
        if (existingProductInfo == null) {
            throw new IllegalArgumentException("해당 매물 정보를 찾을 수 없습니다.");
        }

        existingProductInfo.updateFromDto(productDto);

        List<ProductRemarksInfo> updatedRemarksInfoList = Optional.ofNullable(remarkCds)
                .orElse(Collections.emptyList())
                .stream()
                .map(remarkCd -> ProductRemarksInfo.builder()
                        .productInfo(existingProductInfo)
                        .remarkCd(remarkCd)
                        .useYn("1")
                        .regDtm(LocalDateTime.now())
                        .build())
                .collect(Collectors.toList());

        productService.updateRemarks(prodNo, updatedRemarksInfoList);

        List<ProductImageInfo> imageInfoList = Optional.ofNullable(imges)
                .orElse(Collections.emptyList())
                .stream()
                .map(image -> {
                    String fileName = fileStorageService.saveFile(image);
                    return ProductImageInfo.builder()
                            .productInfo(existingProductInfo)
                            .managementFileName(fileName)
                            .realFileName(image.getOriginalFilename())
                            .useYn("1")
                            .regDtm(LocalDateTime.now())
                            .build();
                })
                .collect(Collectors.toList());

        productService.updateSaveImg(existingProductInfo, imageInfoList);
        return new ApiResponse().success();
    }

    // 매물 삭제
    @DeleteMapping("/{prodNo}")
    public ApiResponse deleteProduct(@PathVariable Long prodNo) {
        productService.deleteProduct(prodNo);
        return new ApiResponse().success();
    }

    // 매물 사진 목록 조회
    @GetMapping("/{prodNo}/images")
    public ApiResponse getProductImages(@PathVariable Long prodNo) {
        List<String> mngFileNm = productService.getImgsrcByProdNo(prodNo);
        return new ApiResponse()
                .success()
                .addResult(Constants.KEY_LIST, mngFileNm);
    }

    // 이미지 삭제
    @DeleteMapping("/images")
    public ApiResponse removeServerImage(@RequestBody Map<String, String> params) {
        String filePath = params.get("filePath");
        String fileName = filePath.substring(filePath.lastIndexOf("=") + 1);
        productService.removeFileAndUpdateDB(fileName);
        return new ApiResponse().success();
    }

    // 최신일자 갱신
    @PatchMapping("/{prodNo}/refresh")
    public ApiResponse updateNewDtm(@PathVariable Long prodNo,
                                    @RequestBody Map<String, Object> params) {
        Long userNo = Long.parseLong(params.get("userNo").toString());
        productService.updateNewDtm(prodNo, userNo);
        return new ApiResponse().success();
    }
}
