package rfp.loyality.service;

import rfp.loyality.domain.RfpEvent;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RfpEvent.
 */
public interface RfpEventService {

    /**
     * Save a rfpEvent.
     *
     * @param rfpEvent the entity to save
     * @return the persisted entity
     */
    RfpEvent save(RfpEvent rfpEvent);

    /**
     * Get all the rfpEvents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RfpEvent> findAll(Pageable pageable);


    /**
     * Get the "id" rfpEvent.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RfpEvent> findOne(Long id);

    /**
     * Delete the "id" rfpEvent.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
