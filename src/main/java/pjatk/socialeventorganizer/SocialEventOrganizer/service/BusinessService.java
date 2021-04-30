package pjatk.socialeventorganizer.SocialEventOrganizer.service;


import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pjatk.socialeventorganizer.SocialEventOrganizer.mapper.AddressMapper;
import pjatk.socialeventorganizer.SocialEventOrganizer.mapper.BusinessMapper;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Business;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.AddressRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.BusinessRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.AddressResponse;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.BusinessResponse;
import pjatk.socialeventorganizer.SocialEventOrganizer.repository.BusinessRepository;

import java.util.List;

@Service
@Value
@AllArgsConstructor
@Slf4j
public class BusinessService {

    BusinessRepository businessRepository;

    AddressService addressService;

    AddressMapper addressMapper;

    BusinessMapper businessMapper;


    public ImmutableList<Business> findAll() {
        final List<Business> businessList = (List<Business>) businessRepository.findAll();
        return ImmutableList.copyOf(businessList);
    }

    public BusinessResponse addNewBusiness(BusinessRequest businessRequest) {

        final AddressRequest addressRequest = businessRequest.getAddressRequest();
        final AddressResponse addressResponse = addressService.addNewAddress(addressRequest);

        final Business business = businessMapper.mapToDTO(businessRequest, addressResponse.getId());

        log.info("TRYING TO SAVE BUSINESS");
        businessRepository.save(business);
        return businessMapper.mapToResponse(business);
    }

}
