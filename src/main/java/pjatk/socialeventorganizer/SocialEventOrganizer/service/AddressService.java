package pjatk.socialeventorganizer.SocialEventOrganizer.service;

import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pjatk.socialeventorganizer.SocialEventOrganizer.mapper.AddressMapper;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Address;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.exception.IllegalArgumentException;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.AddressRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.AddressResponse;
import pjatk.socialeventorganizer.SocialEventOrganizer.repository.AddressRepository;

import java.util.Optional;

@Service
@Value
@AllArgsConstructor
@Slf4j
public class AddressService {

    AddressRepository repository;

    AddressMapper mapper;

    public Address findById(Long id) {
        final Optional<Address> byId = repository.findById(id);
        return byId.get();
    }


    public AddressResponse addNewAddress(AddressRequest request) {
        final Address address = mapper.mapToDTO(request);
        log.info("TRYING TO SAVE ADDRESS");
        repository.save(address);
        log.info("SAVED ADDRESS");

        return mapper.mapToResponse(address);

    }

    public void deleteAddress(Long id) {
        if (!addressWithIdExists(id)) {
            throw new IllegalArgumentException("Address with ID " + id + " does not exist");
        }

        log.info("TRYING TO DELETE ADDRESS WITH ID " + id);
        repository.deleteById(id);
    }

    private boolean addressWithIdExists(Long id) {
        log.info("CHECKING IF ADDRESS WITH ID " + id + " EXISTS");
        return repository.existsById(id);
    }
}
