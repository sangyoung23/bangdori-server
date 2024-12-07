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
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductRemarksInfoRepository productRemarksInfoRepository;


    public List<ProductDTO> getProductList() {
        return productRepository.findAllByUseYnOrderByNewDtmDesc("1").stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

//    @Transactional
//    public void addProdReg(ProductDTO productDTO){
//        ProductInfo productInfo = ProductInfo.builder()
//                .tradeType(productDTO.getTradeType())
//                .title(productDTO.getTitle())
//                .type(productDTO.getType())
//                .statusCd("10")
//                .entrancePwd(productDTO.getEntrancePwd())
//                .unitPwd(productDTO.getUnitPwd())
//                .phoneNo1(productDTO.getPhoneNo1())
//                .phoneNo2(productDTO.getPhoneNo2())
//                .unitNo(productDTO.getUnitNo())
//                .etc(productDTO.getEtc())
//                .deposit(productDTO.getDeposit())
//                .monthlyRent(productDTO.getMonthlyRent())
//                .salePrice(productDTO.getSalePrice())
//                .depositTotal(productDTO.getDepositTotal())
//                .rentTotal(productDTO.getRentTotal())
//                .premiumFee(productDTO.getPremiumFee())
//                .premiumYn(productDTO.getPremiumYn())
//                .directionCd(productDTO.getDirectionCd())
//                .rcmCd(productDTO.getRcmCd())
//                .roomCd(productDTO.getRoomCd())
//                .bathCd(productDTO.getBathCd())
//                .moveInCd(productDTO.getMoveInCd())
//                .prodAddr(productDTO.getProdAddr())
//                .prodRoadAddr(productDTO.getProdRoadAddr())
//                .prodDtlAddr(productDTO.getProdDtlAddr())
//                .newDtm(LocalDateTime.now()) // 시스템에서 현재 시간 설정
//                .useYn("1")
//                //.regUserId(productDTO.getRegUserId())
//                .regUserId(1234L)
//                .regDtm(LocalDateTime.now()) // 시스템에서 현재 시간 설정
//                //.chgUserId(productDTO.getChgUserId())
//                .chgUserId(1234L)
//                .chgDtm(LocalDateTime.now()) // 시스템에서 현재 시간 설정
//                .build();
//
//        ProductInfo savedProductInfo =productRepository.save(productInfo);
//        ProductRemarksInfo remarksInfo = ProductRemarksInfo.builder()
//                .productInfo(savedProductInfo)  // 위에서 저장한 ProductInfo 객체를 연결
//                .remarkCd(remarksDto.getRemarkCd())
//                .useYn(remarksDto.getUseYn())
//                .regDtm(LocalDateTime.now())
//                .build();
//
//        productRemarksInfoRepository.save(remarksInfo);
//                System.out.println("productInfo   "+ productInfo.getTitle());
//
//    }


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
}
