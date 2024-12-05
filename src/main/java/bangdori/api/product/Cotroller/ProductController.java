package bangdori.api.product.Cotroller;

import bangdori.api.comm.ApiResponse;
import bangdori.api.comm.Constants;
import bangdori.api.product.dto.ProductDTO;
import bangdori.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;
    private final ApiResponse apiResponse;

    @GetMapping("/products")
    public ApiResponse getProductList(@RequestParam HashMap<String, Object> params) {
        List<ProductDTO> productList = productService.getProductList();
        return apiResponse.addResult("LIST", productList);
    }

    @PostMapping("/addProdReg")
    public ApiResponse addProdReg(@RequestBody ProductDTO productDTO) {

        try{
            productService.addProdReg(productDTO);
        } catch (Exception e) {
            return apiResponse.error();
        }
        return apiResponse.success();
    }
}
