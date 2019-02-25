package rfp.loyality.repository;

import rfp.loyality.domain.RfpEventAttendance;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the RfpEventAttendence entity.
 */
@SuppressWarnings("unused")
@Repository
public interface RfpEventAttendenceRepository extends JpaRepository<RfpEventAttendance, Long> {

}
