package pjatk.socialeventorganizer.SocialEventOrganizer.mapper;

import org.springframework.stereotype.Component;
import pjatk.socialeventorganizer.SocialEventOrganizer.convertors.Converter;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Business;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.enums.BusinessVerificationStatusEnum;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.BusinessRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.BusinessResponse;

@Component
public class BusinessMapper {

    public Business mapToDTO(BusinessRequest businessRequest, long addressId) {

        return Business.builder()
                .firstName(businessRequest.getFirstName())
                .lastName(businessRequest.getLastName())
                .businessName(businessRequest.getBusinessName())
                .email(businessRequest.getEmail())
                .password(businessRequest.getPassword())
                .phoneNumber(Converter.convertPhoneNumberString(businessRequest.getPhoneNumber()))
                .verificationStatus(String.valueOf(BusinessVerificationStatusEnum.NOT_VERIFIED))
                .isCatering(businessRequest.isCatering())
                .isLocation(businessRequest.isLocation())
                .isService(businessRequest.isService())
                .addressId((int) addressId)
                .build();
    }

    public BusinessResponse mapToResponse(Business business) {
        return BusinessResponse.builder()
                .id(business.getId())
                .email(business.getEmail())
                .build();
    }
}
