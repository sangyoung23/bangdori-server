package bangdori.api.product.Cotroller;

import bangdori.api.comm.ApiResponse;
import bangdori.api.product.dto.ProductDTO;
import bangdori.api.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
