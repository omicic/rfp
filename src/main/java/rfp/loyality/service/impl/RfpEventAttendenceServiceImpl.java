package rfp.loyality.service.impl;

import rfp.loyality.service.RfpEventAttendenceService;
import rfp.loyality.domain.RfpEventAttendance;
import rfp.loyality.repository.RfpEventAttendenceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RfpEventAttendence.
 */
@Service
@Transactional
public class RfpEventAttendenceServiceImpl implements RfpEventAttendenceService {

    private final Logger log = LoggerFactory.getLogger(RfpEventAttendenceServiceImpl.class);

    private final RfpEventAttendenceRepository rfpEventAttendenceRepository;

    public RfpEventAttendenceServiceImpl(RfpEventAttendenceRepository rfpEventAttendenceRepository) {
        this.rfpEventAttendenceRepository = rfpEventAttendenceRepository;
    }

    /**
     * Save a rfpEventAttendence.
     *
     * @param rfpEventAttendence the entity to save
     * @return the persisted entity
     */
    @Override
    public RfpEventAttendance save(RfpEventAttendance rfpEventAttendence) {
        log.debug("Request to save RfpEventAttendence : {}", rfpEventAttendence);
        return rfpEventAttendenceRepository.save(rfpEventAttendence);
    }

    /**
     * Get all the rfpEventAttendences.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RfpEventAttendance> findAll(Pageable pageable) {
        log.debug("Request to get all RfpEventAttendences");
        return rfpEventAttendenceRepository.findAll(pageable);
    }


    /**
     * Get one rfpEventAttendence by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RfpEventAttendance> findOne(Long id) {
        log.debug("Request to get RfpEventAttendence : {}", id);
        return rfpEventAttendenceRepository.findById(id);
    }

    /**
     * Delete the rfpEventAttendence by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RfpEventAttendence : {}", id);
        rfpEventAttendenceRepository.deleteById(id);
    }
}
