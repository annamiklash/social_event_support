package pjatk.socialeventorganizer.SocialEventOrganizer.service;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pjatk.socialeventorganizer.SocialEventOrganizer.mapper.CateringMapper;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Catering;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.exception.IllegalArgumentException;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.exception.NotFoundException;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.AddressRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.CateringRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.AddressResponse;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.CateringResponse;
import pjatk.socialeventorganizer.SocialEventOrganizer.repository.CateringRepository;

import java.util.List;
import java.util.Optional;

@Service
@Value
@AllArgsConstructor
@Slf4j
public class CateringService {

    CateringRepository repository;

    CateringMapper cateringMapper;

    LocationService locationService;

    AddressService addressService;


    public ImmutableList<Catering> findAll() {
        final List<Catering> cateringList = (List<Catering>) repository.findAll();
        return ImmutableList.copyOf(cateringList);
    }

    public ImmutableList<Catering> findByName(String name) {
        final List<Catering> cateringList = repository.findByNameContaining(name);
        if (cateringList != null && !cateringList.isEmpty()) {
            return ImmutableList.copyOf(cateringList);
        } else {
            throw new NotFoundException("No catering with the name " + name + " was found");
        }
    }

//    public ImmutableList<Catering> findByCity(String city) {
//        final List<Catering> cateringList = repository.findByCityContaining(city);
//        if (cateringList != null && !cateringList.isEmpty()) {
//            return ImmutableList.copyOf(cateringList);
//        } else {
//            throw new NotFoundException("No catering in the city " + city + " was found");
//        }
//    }

    public CateringResponse addNewCatering(CateringRequest request) {
        final AddressRequest addressRequest = request.getAddressRequest();

        final AddressResponse addressResponse = addressService.addNewAddress(addressRequest);

        final Catering catering = cateringMapper.mapToDTO(request, addressResponse.getId());
        log.info("TRYING TO SAVE" + catering.toString());
        repository.save(catering);
        log.info("SAVED CATERING");

        return cateringMapper.mapToResponse(catering);
    }


    public void updateCatering(Long cateringId, CateringRequest request) {
        if (!cateringWithIdExists(cateringId)) {
            throw new IllegalArgumentException("Catering with ID " + cateringId + " does not exist");
        }

        final Catering catering = cateringMapper.updateMapToDTO(request, cateringId);
        log.info("TRYING TO UPDATE " + catering);
        repository.save(catering);
        log.info("UPDATED");
    }

    //TODO: ad cascade on delete to db model
    public void deleteCatering(Long id) {
        if (!cateringWithIdExists(id)) {
            throw new IllegalArgumentException("Catering with ID " + id + " does not exist");
        }
        log.info("TRYING TO FIND ADDRESS ID");
        final long addressId = repository.findCateringAddressId(id);
        final Optional<Catering> optionalCatering = repository.findById(id);
        final Catering catering = optionalCatering.get();
        catering.setAddressId(null);
        repository.deleteById(id);

        addressService.deleteAddress(addressId);
        log.info("TRYING TO DELETE CATERING WITH ID " + id);
    }

    public boolean cateringWithIdExists(Long id) {
        log.info("CHECKING IF CATERING WITH ID " + id + " EXISTS");
        return repository.existsById(id);
    }

}
