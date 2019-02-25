package rfp.loyality.service;

import rfp.loyality.domain.RfpLocation;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

/**
 * Service Interface for managing RfpLocation.
 */
public interface RfpLocationService {

    /**
     * Save a rfpLocation.
     *
     * @param rfpLocation the entity to save
     * @return the persisted entity
     */
    RfpLocation save(RfpLocation rfpLocation);

    /**
     * Get all the rfpLocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<RfpLocation> findAll(Pageable pageable);


    /**
     * Get the "id" rfpLocation.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RfpLocation> findOne(Long id);

    /**
     * Delete the "id" rfpLocation.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
