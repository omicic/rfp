package rfp.loyality.repository;

import rfp.loyality.domain.RfpUser;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RfpUser entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RfpUserRepository extends JpaRepository<RfpUser, Long> {

}
