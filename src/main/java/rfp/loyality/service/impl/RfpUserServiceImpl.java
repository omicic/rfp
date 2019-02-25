package rfp.loyality.service.impl;

import rfp.loyality.service.RfpUserService;
import rfp.loyality.domain.RfpUser;
import rfp.loyality.repository.RfpUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Service Implementation for managing RfpUser.
 */
@Service
@Transactional
public class RfpUserServiceImpl implements RfpUserService {

    private final Logger log = LoggerFactory.getLogger(RfpUserServiceImpl.class);

    private final RfpUserRepository rfpUserRepository;

    public RfpUserServiceImpl(RfpUserRepository rfpUserRepository) {
        this.rfpUserRepository = rfpUserRepository;
    }

    /**
     * Save a rfpUser.
     *
     * @param rfpUser the entity to save
     * @return the persisted entity
     */
    @Override
    public RfpUser save(RfpUser rfpUser) {
        log.debug("Request to save RfpUser : {}", rfpUser);
        return rfpUserRepository.save(rfpUser);
    }

    /**
     * Get all the rfpUsers.
     *
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public List<RfpUser> findAll() {
        log.debug("Request to get all RfpUsers");
        return rfpUserRepository.findAll();
    }


    /**
     * Get one rfpUser by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RfpUser> findOne(Long id) {
        log.debug("Request to get RfpUser : {}", id);
        return rfpUserRepository.findById(id);
    }

    /**
     * Delete the rfpUser by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RfpUser : {}", id);
        rfpUserRepository.deleteById(id);
    }
}
