package bangdori.api.product.service;

import bangdori.api.product.dto.ProductDTO;
import bangdori.api.product.entity.ProductInfo;
import bangdori.api.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<ProductDTO> getProductList() {
        return productRepository.findAllByUseYnOrderByNewDtmDesc("1").stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public void addProdReg(ProductDTO productDTO){
        ProductInfo productInfo = new ProductInfo();
        //productInfo.set productDTO.get
    }
}
