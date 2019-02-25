package rfp.loyality.service.impl;

import rfp.loyality.service.RfpLocationService;
import rfp.loyality.domain.RfpLocation;
import rfp.loyality.repository.RfpLocationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RfpLocation.
 */
@Service
@Transactional
public class RfpLocationServiceImpl implements RfpLocationService {

    private final Logger log = LoggerFactory.getLogger(RfpLocationServiceImpl.class);

    private final RfpLocationRepository rfpLocationRepository;

    public RfpLocationServiceImpl(RfpLocationRepository rfpLocationRepository) {
        this.rfpLocationRepository = rfpLocationRepository;
    }

    /**
     * Save a rfpLocation.
     *
     * @param rfpLocation the entity to save
     * @return the persisted entity
     */
    @Override
    public RfpLocation save(RfpLocation rfpLocation) {
        log.debug("Request to save RfpLocation : {}", rfpLocation);
        return rfpLocationRepository.save(rfpLocation);
    }

    /**
     * Get all the rfpLocations.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RfpLocation> findAll(Pageable pageable) {
        log.debug("Request to get all RfpLocations");
        return rfpLocationRepository.findAll(pageable);
    }


    /**
     * Get one rfpLocation by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RfpLocation> findOne(Long id) {
        log.debug("Request to get RfpLocation : {}", id);
        return rfpLocationRepository.findById(id);
    }

    /**
     * Delete the rfpLocation by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RfpLocation : {}", id);
        rfpLocationRepository.deleteById(id);
    }
}
