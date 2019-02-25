package rfp.loyality.service;

import rfp.loyality.domain.RfpEventAttendence;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RfpEventAttendence.
 */
public interface RfpEventAttendenceService {

    /**
     * Save a rfpEventAttendence.
     *
     * @param rfpEventAttendence the entity to save
     * @return the persisted entity
     */
    RfpEventAttendence save(RfpEventAttendence rfpEventAttendence);

    /**
     * Get all the rfpEventAttendences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RfpEventAttendence> findAll(Pageable pageable);


    /**
     * Get the "id" rfpEventAttendence.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RfpEventAttendence> findOne(Long id);

    /**
     * Delete the "id" rfpEventAttendence.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
