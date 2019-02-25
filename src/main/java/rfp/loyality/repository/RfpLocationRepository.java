package rfp.loyality.repository;

import rfp.loyality.domain.RfpLocation;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RfpLocation entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RfpLocationRepository extends JpaRepository<RfpLocation, Long> {

}
