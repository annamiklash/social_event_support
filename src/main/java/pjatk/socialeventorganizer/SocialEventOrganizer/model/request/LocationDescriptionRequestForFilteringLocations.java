package pjatk.socialeventorganizer.SocialEventOrganizer.model.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.enums.LocationDescriptionItemEnum;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDescriptionRequestForFilteringLocations {

    @NotNull
    List<LocationDescriptionItemEnum> descriptionItems;

}
