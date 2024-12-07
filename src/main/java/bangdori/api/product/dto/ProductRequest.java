package bangdori.api.product.dto;

import lombok.Getter;

import java.util.List;

@Getter
public class ProductRequest {
    private ProductDTO productDto; // ProductDto
    private List<String> remarkCds; // remarks는 remarkCd 값만 받음
}
