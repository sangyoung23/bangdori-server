package bangdori.api.domain.product.controller;

import bangdori.api.comm.response.ApiResponse;
import bangdori.api.comm.Constants;
import bangdori.api.domain.product.dto.ProductDTO;
import bangdori.api.domain.product.service.ProductService;
import bangdori.api.domain.user.dto.UserPublicInfoDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

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

        productService.createProduct(productDto, remarkCds, imges);
        return new ApiResponse().success();
    }

    // 매물 수정
    @PutMapping("/{prodNo}")
    public ApiResponse updateProduct(
            @PathVariable Long prodNo,
            @RequestPart("productDto") ProductDTO productDto,
            @RequestPart(value = "remarkCds", required = false) List<String> remarkCds,
            @RequestPart(value = "imges", required = false) List<MultipartFile> imges) {

        productService.updateProduct(prodNo, productDto, remarkCds, imges);
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
        productService.removeFileAndUpdateDB(params);
        return new ApiResponse().success();
    }

    // 최신일자 갱신
    @PatchMapping("/{prodNo}/refresh")
    public ApiResponse updateNewDtm(@PathVariable Long prodNo,
                                    @RequestBody Map<String, Object> params) {
        productService.updateNewDtm(prodNo, params);
        return new ApiResponse().success();
    }
}
