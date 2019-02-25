package rfp.loyality.repository;

import rfp.loyality.domain.RfpEvent;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RfpEvent entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RfpEventRepository extends JpaRepository<RfpEvent, Long> {

}
