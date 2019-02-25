package rfp.loyality.service;

import rfp.loyality.domain.RfpUser;

import java.util.List;
import java.util.Optional;

/**
 * Service Interface for managing RfpUser.
 */
public interface RfpUserService {

    /**
     * Save a rfpUser.
     *
     * @param rfpUser the entity to save
     * @return the persisted entity
     */
    RfpUser save(RfpUser rfpUser);

    /**
     * Get all the rfpUsers.
     *
     * @return the list of entities
     */
    List<RfpUser> findAll();


    /**
     * Get the "id" rfpUser.
     *
     * @param id the id of the entity
     * @return the entity
     */
    Optional<RfpUser> findOne(Long id);

    /**
     * Delete the "id" rfpUser.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
