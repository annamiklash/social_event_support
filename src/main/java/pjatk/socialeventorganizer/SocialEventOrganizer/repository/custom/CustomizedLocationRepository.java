package pjatk.socialeventorganizer.SocialEventOrganizer.repository.custom;

import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Location;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.enums.LocationDescriptionItemEnum;

import java.util.List;

public interface CustomizedLocationRepository {

    List<Location> findLocationByDescription(List<LocationDescriptionItemEnum> locationDescription);
}
