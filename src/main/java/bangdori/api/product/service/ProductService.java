package bangdori.api.product.service;

import bangdori.api.product.dto.ProductDTO;
import bangdori.api.product.entity.ProductInfo;
import bangdori.api.product.entity.ProductRemarksInfo;
import bangdori.api.product.repository.ProductRemarksInfoRepository;
import bangdori.api.product.repository.ProductRepository;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductRemarksInfoRepository productRemarksInfoRepository;

public List<ProductDTO> getProductList() {
    List<ProductInfo> products = productRepository.findAllByUseYnOrderByNewDtmDesc("1");

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
    public void saveProduct(ProductInfo productInfo, List<ProductRemarksInfo> remarksInfoList) {
        // ProductInfo 저장

        ProductInfo savedProductInfo = productRepository.save(productInfo);

        List<ProductRemarksInfo> updatedRemarksInfoList = remarksInfoList.stream()
                .map(remarksInfo -> ProductRemarksInfo.builder()
                        .productInfo(savedProductInfo)   // 저장된 productInfo 객체를 설정
                        .remarkCd(remarksInfo.getRemarkCd())  // remarkCd 설정
                        .useYn("Y")                 // 기본값 Y 설정
                        .regDtm(LocalDateTime.now()) // 현재 시간으로 등록일시 설정
                        .build())
                .collect(Collectors.toList());
        // ProductRemarksInfo 저장
        productRemarksInfoRepository.saveAll(updatedRemarksInfoList);
    }


    @Transactional
    public void updateNewDtm(Long prodNo) {
        ProductInfo productInfo = productRepository.findById(prodNo).orElse(null);
        productInfo.updateNewDtm();
    }

    @Transactional
    public void deleteProduct(Long prodNo) {
        ProductInfo productInfo = productRepository.findById(prodNo).orElse(null);
        productInfo.updateUseYn("0");
    }
}
