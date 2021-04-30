package pjatk.socialeventorganizer.SocialEventOrganizer.service;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pjatk.socialeventorganizer.SocialEventOrganizer.mapper.AddressMapper;
import pjatk.socialeventorganizer.SocialEventOrganizer.mapper.LocationMapper;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Location;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.LocationDescriptionItem;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.enums.LocationDescriptionItemEnum;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.AddressRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.LocationDescriptionRequestForFilteringLocations;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.request.LocationRequest;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.AddressResponse;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.response.LocationResponse;
import pjatk.socialeventorganizer.SocialEventOrganizer.repository.LocationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Value
@AllArgsConstructor
@Slf4j
public class LocationService {

    LocationRepository locationRepository;

    AddressService addressService;

    LocationDescriptionItemService locationDescriptionItemService;

    AddressMapper addressMapper;

    LocationMapper locationMapper;


    public ImmutableList<Location> findAll() {
        final List<Location> locationList = (List<Location>) locationRepository.findAll();
        return ImmutableList.copyOf(locationList);
    }


    public ImmutableList<Location> findByLocationDescription(LocationDescriptionRequestForFilteringLocations request) {
        final List<LocationDescriptionItemEnum> locationDescriptionList = request.getDescriptionItems();
        final String[] locationStringList = locationDescriptionList.stream()
                .map(LocationDescriptionItemEnum::getValue)
                .toArray(String[]::new);
        final List<Integer> locationIdList = locationRepository.findLocationsIdByDescription(locationStringList);

        final List<Location> locationList = new ArrayList<>();

        for (Integer integer : locationIdList) {
            final Optional<Location> location = locationRepository.findById(Long.valueOf(integer));
            location.ifPresent(locationList::add);
        }

        return ImmutableList.copyOf(locationList);

    }

    public LocationResponse addNewLocation(LocationRequest locationRequest) {

        final AddressRequest addressRequest = locationRequest.getAddressRequest();
        final AddressResponse addressResponse = addressService.addNewAddress(addressRequest);

        final Location location = locationMapper.mapToDTO(locationRequest, addressResponse.getId());

        final List<LocationDescriptionItemEnum> locationDescription = locationRequest.getLocationDescription();
        locationDescription.forEach(locationDescriptionItemEnum -> log.info("DESC ITEM " + locationDescriptionItemEnum.toString()));

        boolean servesFood = false;
        for (LocationDescriptionItemEnum locationDescriptionItemEnum : locationDescription) {
            final LocationDescriptionItem item = locationDescriptionItemService.getById(locationDescriptionItemEnum.value);
            if (locationDescriptionItemEnum.equals(LocationDescriptionItemEnum.SERVES_FOOD)) {
                servesFood = true;
            }
            location.addLocationDescription(item);
        }
        log.info("TRYING TO SAVE LOCATION " + location.toString());

        locationRepository.save(location);

        //CHECK IF CONTAINS 'SERVES_FOOD' if yes -> add to catering

        return locationMapper.mapToResponse(location, servesFood);

    }

}
