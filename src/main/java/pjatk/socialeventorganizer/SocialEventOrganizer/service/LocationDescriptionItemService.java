package pjatk.socialeventorganizer.SocialEventOrganizer.service;

import com.google.common.collect.ImmutableList;
import lombok.AllArgsConstructor;
import lombok.Value;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.LocationDescriptionItem;
import pjatk.socialeventorganizer.SocialEventOrganizer.repository.LocationDescriptionItemRepository;

import java.util.List;

@Service
@Value
@AllArgsConstructor
@Slf4j
public class LocationDescriptionItemService {

    LocationDescriptionItemRepository repository;

    public ImmutableList<LocationDescriptionItem> findAll() {
        final List<LocationDescriptionItem> itemsList = (List<LocationDescriptionItem>) repository.findAll();
        return ImmutableList.copyOf(itemsList);
    }



    public LocationDescriptionItem getById(String id) {
        return repository.getLocationDescriptionItemById(id);
    }
}
