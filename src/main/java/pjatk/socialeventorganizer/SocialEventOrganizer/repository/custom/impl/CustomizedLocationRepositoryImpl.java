package pjatk.socialeventorganizer.SocialEventOrganizer.repository.custom.impl;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.dto.Location;
import pjatk.socialeventorganizer.SocialEventOrganizer.model.enums.LocationDescriptionItemEnum;
import pjatk.socialeventorganizer.SocialEventOrganizer.repository.custom.CustomizedLocationRepository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
@Slf4j
@AllArgsConstructor
public class CustomizedLocationRepositoryImpl implements CustomizedLocationRepository {

    EntityManager entityManager;

    @Override
    public List<Location> findLocationByDescription(List<LocationDescriptionItemEnum> locationDescription) {
        String sql = "SELECT distinct l.id_location, l.name, l.email, l.phone_number, " +
                "l.seating_capacity, l.standing_capacity, l.description, l.daily_rent_cost, " +
                "l.size_in_sq_meters, l.id_business, l.id_address " +
                "FROM location l " +
                "JOIN location_description ld ON l.id_location = ld.id_location";
        String where = " WHERE ";

        if (locationDescription.size() == 0) {
            Query query = entityManager.createNativeQuery(sql, Location.class);
            return query.getResultList();
        } else {
            String res = "";
            for (LocationDescriptionItemEnum item : locationDescription) {
                String sqlPart = "ld.description_item_name = '"  + item.value + "' AND ";
                res += sqlPart;
            }
            sql = sql + where + res;
            final int length = sql.length();
            sql = sql.substring(0, length - 4);
            log.info(sql);
            Query query = entityManager.createNativeQuery(sql, Location.class);
            List result = query.getResultList();
            log.info(String.valueOf(result.size()));

            return result;

        }
    }
}
