package pjatk.socialeventorganizer.SocialEventOrganizer.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Location;

import java.util.List;

@Repository

public interface LocationRepository extends CrudRepository<Location, Long> {

    @Query(value = "SELECT search_locations_by_description(:descriptionItems);")
    List<Integer> findLocationsIdByDescription(@Param("descriptionItems") String[] descriptionItems);
}
