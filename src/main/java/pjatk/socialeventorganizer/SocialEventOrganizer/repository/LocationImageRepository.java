package pjatk.socialeventorganizer.SocialEventOrganizer.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.LocationImage;

import java.util.List;
import java.util.Optional;

@Repository
public interface LocationImageRepository extends CrudRepository<LocationImage, Long> {

    Optional<List<LocationImage>> findAllByLocationId(long locationId);
}
