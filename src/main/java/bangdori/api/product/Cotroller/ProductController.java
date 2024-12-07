package bangdori.api.product.Cotroller;

import bangdori.api.comm.ApiResponse;
import bangdori.api.comm.Constants;
import bangdori.api.product.dto.ProductDTO;
import bangdori.api.product.dto.ProductRequest;
import bangdori.api.product.entity.ProductInfo;
import bangdori.api.product.entity.ProductRemarksInfo;
import bangdori.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ApiResponse apiResponse;

    @GetMapping("/products")
    public ApiResponse getProductList(@RequestParam HashMap<String, Object> params) {
        List<ProductDTO> productList = productService.getProductList();
//        System.out.println("check  " + productList.get(0).getProductRemarksInfos().get(0));
        return apiResponse.addResult("LIST", productList);
    }




    @PostMapping("/addProdReg")
    public ApiResponse addProdReg(@RequestBody ProductRequest productRequest) {

        try {
            ProductDTO productDto = productRequest.getProductDto();
            ProductInfo productInfo = ProductInfo.fromDto(productDto);

            // remarks 배열을 처리하여 ProductRemarksInfo 리스트로 변환
            List<String> remarkCds = Optional.ofNullable(productRequest.getRemarkCds())
                    .orElse(Collections.emptyList());

            List<ProductRemarksInfo> remarksInfoList = remarkCds.stream()
                    .map(remarkCd -> ProductRemarksInfo.builder()
                            .productInfo(productInfo)   // 해당 productInfo 설정
                            .remarkCd(remarkCd)         // remarkCd 값 설정
                            .useYn("Y")                 // 기본값을 Y로 설정, 필요에 따라 수정 가능
                            .regDtm(LocalDateTime.now()) // 등록일시
                            .build())
                    .collect(Collectors.toList());

            // ProductInfo와 ProductRemarksInfo 리스트를 저장

            productService.saveProduct(productInfo, remarksInfoList);
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
