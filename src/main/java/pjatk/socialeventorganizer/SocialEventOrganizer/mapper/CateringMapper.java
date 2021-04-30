package pjatk.socialeventorganizer.SocialEventOrganizer.mapper;


import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import pjatk.socialeventorganizer.SocialEventOrganizer.convertors.Converter;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Catering;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.CateringRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.CateringResponse;

import java.math.BigDecimal;
import java.math.BigInteger;

@Component
@Slf4j
public class CateringMapper {

    public Catering mapToDTO(CateringRequest request, long addressId) {

        final String requestServiceCost = request.getServiceCost();
        log.info("COST STRING " + requestServiceCost);

        final BigDecimal serviceCost = Converter.convertPriceString(requestServiceCost);
        log.info("COST CONVERTED " + serviceCost);

        final String requestPhoneNumber = request.getPhoneNumber();
        final BigInteger phoneNumber = Converter.convertPhoneNumberString(requestPhoneNumber);

        final String requestDescription = request.getDescription();
        final String description = Converter.convertDescriptionsString(requestDescription);

        return Catering.builder()
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(phoneNumber)
                .serviceCost(serviceCost)
                .description(description)
                .businessId(request.getBusinessId())
                .addressId((int)addressId)
                .build();
    }

    public Catering updateMapToDTO(CateringRequest request, Long id) {
        final String requestServiceCost = request.getServiceCost();
        final BigDecimal serviceCost = Converter.convertPriceString(requestServiceCost);

        final String requestPhoneNumber = request.getPhoneNumber();
        final BigInteger phoneNumber = Converter.convertPhoneNumberString(requestPhoneNumber);

        final String requestDescription = request.getDescription();
        final String description = Converter.convertDescriptionsString(requestDescription);

        return Catering.builder()
                .id(id)
                .name(request.getName())
                .email(request.getEmail())
                .phoneNumber(phoneNumber)
                .serviceCost(serviceCost)
                .description(description)
                .businessId(request.getBusinessId())
                .build();
    }

    public CateringResponse mapToResponse(Catering catering) {
        return CateringResponse.builder()
                .id(catering.getId())
                .name(catering.getName())
                .email(catering.getEmail())
                .build();
    }
}
