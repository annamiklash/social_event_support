package pjatk.socialeventorganizer.SocialEventOrganizer.repository;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Catering;

import java.util.List;

@Repository
public interface CateringRepository extends CrudRepository<Catering, Long> {

    /*
    IF A METHOD NEEDS A CUSTOM IMPLEMENTATION OR IT DOES NOT EXITS IN CrudRepository interface => it needs to be written here.
    Otherwise you dont need to, just call the method from ServiceImpl
     */

//    List<Catering> findByCityContaining(String city);

    List<Catering> findByNameContaining(String name);

    @Query("SELECT id_address FROM catering where id_catering = :id")
    long findCateringAddressId(@Param("id") long cateringId);


}
