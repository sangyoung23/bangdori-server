package bangdori.api.product.Cotroller;

import bangdori.api.comm.ApiResponse;
import bangdori.api.product.dto.ProductDTO;
import bangdori.api.product.entity.ProductImageInfo;
import bangdori.api.product.entity.ProductInfo;
import bangdori.api.product.entity.ProductRemarksInfo;
import bangdori.api.product.service.FileStorageService;
import bangdori.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ApiResponse apiResponse;
    private final FileStorageService fileStorageService;

    @GetMapping("/products")
    public ApiResponse getProductList(@RequestParam HashMap<String, Object> params) {
        List<ProductDTO> productList = productService.getProductList();
//        System.out.println("check  " + productList.get(0).getProductRemarksInfos().get(0));
        return apiResponse.addResult("LIST", productList);
    }




    @PostMapping("/addProdReg")
    public ApiResponse addProdReg(@RequestPart("productDto") ProductDTO productDto,
                                  @RequestPart(value = "remarkCds", required = false) List<String> remarkCds,
                                  @RequestPart(value = "imges", required = false) List<MultipartFile> imges) {

        try {
            ProductInfo productInfo = ProductInfo.fromDto(productDto);

            // remarks 배열 처리
            List<ProductRemarksInfo> remarksInfoList = Optional.ofNullable(remarkCds)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(remarkCd -> ProductRemarksInfo.builder()
                            .productInfo(productInfo)
                            .remarkCd(remarkCd)
                            .useYn("Y")
                            .regDtm(LocalDateTime.now())
                            .build())
                    .collect(Collectors.toList());

            // 이미지를 처리하여 ProductImageInfo 생성
            List<ProductImageInfo> imageInfoList = Optional.ofNullable(imges)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(image -> {
                        String fileName = fileStorageService.saveFile(image); // 파일 저장
                        return ProductImageInfo.builder()
                                .productInfo(productInfo)
                                .managementFileName(fileName)
                                .realFileName(image.getOriginalFilename())
                                .useYn("Y")
                                .regDtm(LocalDateTime.now())
                                .build();
                    })
                    .collect(Collectors.toList());

            // ProductInfo, ProductRemarksInfo, ProductImageInfo 저장
            productService.saveProduct(productInfo, remarksInfoList, imageInfoList);
            // ProductInfo와 ProductRemarksInfo 리스트를 저장
        } catch (Exception e) {
            throw new RuntimeException(e); // return apiResponse.error();
        }
        return apiResponse.success();
    }



    /**
     * 매물 최신일자 Update
     */
    @PostMapping("/updateNewDtm")
    public ApiResponse updateNewDtm(@RequestBody Map<String, Object> prams) {
        try {
            productService.updateNewDtm( Long.parseLong(prams.get("prodNo").toString()));
        } catch (Exception e) {
            return apiResponse.error();
        }
        return apiResponse.success();
    }

    /**
     * 매물 Delete
     */
    @PostMapping("/deleteProduct")
    public ApiResponse deleteProduct(@RequestBody Map<String, Object> prams) {
        try {
            productService.deleteProduct( Long.parseLong(prams.get("prodNo").toString()));
        } catch (Exception e) {
            return apiResponse.error();
        }
        return apiResponse.success();
    }

}
