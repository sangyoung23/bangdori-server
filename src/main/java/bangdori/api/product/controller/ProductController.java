package bangdori.api.product.controller;

import bangdori.api.comm.ApiResponse;
import bangdori.api.comm.ErrorCode;
import bangdori.api.product.dto.ProductDTO;
import bangdori.api.product.entity.ProductImageInfo;
import bangdori.api.product.entity.ProductInfo;
import bangdori.api.product.entity.ProductRemarksInfo;
import bangdori.api.product.service.FileStorageService;
import bangdori.api.product.service.ProductService;
import bangdori.api.user.dto.UserPulicInfoDTO;
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
    private final ApiResponse apiResponse;
    private final FileStorageService fileStorageService;

    @GetMapping("/products")
    public ApiResponse getProductList(@RequestParam HashMap<String, Object> params) {
        Long userNo = Long.parseLong(params.get("userNo").toString());
        List<ProductDTO> productList = productService.getProductList(userNo);
        return apiResponse.addResult("LIST", productList);
    }

    @GetMapping("/userList")
    public ApiResponse getUserList(@RequestParam HashMap<String, Object> params) {

        Long userNo = Long.parseLong(params.get("userNo").toString());
        List<UserPulicInfoDTO> userInfoList =  productService.getUserList(userNo);
        return apiResponse.addResult("LIST", userInfoList);
    }


    /**
     * 매물/매매/임대 등록
     * */
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
                            .useYn("1")
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
                                .useYn("1")
                                .regDtm(LocalDateTime.now())
                                .build();
                    })
                    .collect(Collectors.toList());

            // ProductInfo, ProductRemarksInfo, ProductImageInfo 저장
            productService.saveProduct(productInfo, remarksInfoList, imageInfoList);
        } catch (Exception e) {
            throw new RuntimeException(e); // return apiResponse.error();
        }
        return apiResponse.success();
    }


    /**
     * 매물/매매/임대 수정
     * */
    @PostMapping("/udpateProdInfo")
    public ApiResponse udpateProdInfo(@RequestPart("prodNo") String strProdNo,
            @RequestPart("productDto") ProductDTO productDto,
                                  @RequestPart(value = "remarkCds", required = false) List<String> remarkCds,
                                  @RequestPart(value = "imges", required = false) List<MultipartFile> imges) {

        try {
            // 기존 ProductInfo 조회

            Long prodNo = Long.parseLong(strProdNo);
            ProductInfo existingProductInfo = productService.findByProdNo(prodNo);
            if (existingProductInfo == null) {
                return apiResponse.setMessage(ErrorCode.PRD0001);
            }

            existingProductInfo.updateFromDto(productDto);

            // remarks 배열 처리
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


            // 이미지를 처리하여 ProductImageInfo 생성
            List<ProductImageInfo> imageInfoList = Optional.ofNullable(imges)
                    .orElse(Collections.emptyList())
                    .stream()
                    .map(image -> {
                        String fileName = fileStorageService.saveFile(image); // 파일 저장
                        return ProductImageInfo.builder()
                                .productInfo(existingProductInfo)
                                .managementFileName(fileName)
                                .realFileName(image.getOriginalFilename())
                                .useYn("1")
                                .regDtm(LocalDateTime.now())
                                .build();
                    })
                    .collect(Collectors.toList());

            // ProductInfo, ProductRemarksInfo, ProductImageInfo 저장
            productService.updateSaveImg(existingProductInfo, imageInfoList);
        } catch (Exception e) {
            throw new RuntimeException(e); // return apiResponse.error();
        }
        return apiResponse.success();
    }

    /**
     * 사진파일 리스트 가져오기
     * */
    @GetMapping("/getImgsrcByProdNo")
    public ApiResponse getImgsrcByProdNo(@RequestParam HashMap<String, Object> params) {
        Long prodNo =  Long.parseLong((String) params.get("prodNo"));
        List<String> mngFileNm = productService.getImgsrcByProdNo(prodNo);

        return apiResponse.addResult("LIST", mngFileNm);
    }

    @PostMapping("/removeServerImage")
    public ApiResponse removeServerImage(@RequestBody Map<String, String> params) {

        try {
            String filePath = params.get("filePath");
            String fileName = filePath.substring(filePath.lastIndexOf("=") + 1);

            productService.removeFileAndUpdateDB(fileName);


            // 성공 응답 반환
            return apiResponse.success();

        } catch (Exception e) {
            e.printStackTrace();
            // 실패 응답 반환
            return apiResponse.error();
        }
    }


    /**
     * 매물 최신일자 Update
     */
    @PostMapping("/updateNewDtm")
    public ApiResponse updateNewDtm(@RequestBody Map<String, Object> params) {
        try {
            Long prodNo = Long.parseLong(params.get("prodNo").toString());
            Long userNo = Long.parseLong(params.get("userNo").toString());

            // 매물의 newDtm 및 chg_user_id 업데이트
            productService.updateNewDtm(prodNo, userNo);
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
