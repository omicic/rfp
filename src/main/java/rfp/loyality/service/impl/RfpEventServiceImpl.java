package rfp.loyality.service.impl;

import rfp.loyality.service.RfpEventService;
import rfp.loyality.domain.RfpEvent;
import rfp.loyality.repository.RfpEventRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

/**
 * Service Implementation for managing RfpEvent.
 */
@Service
@Transactional
public class RfpEventServiceImpl implements RfpEventService {

    private final Logger log = LoggerFactory.getLogger(RfpEventServiceImpl.class);

    private final RfpEventRepository rfpEventRepository;

    public RfpEventServiceImpl(RfpEventRepository rfpEventRepository) {
        this.rfpEventRepository = rfpEventRepository;
    }

    /**
     * Save a rfpEvent.
     *
     * @param rfpEvent the entity to save
     * @return the persisted entity
     */
    @Override
    public RfpEvent save(RfpEvent rfpEvent) {
        log.debug("Request to save RfpEvent : {}", rfpEvent);
        return rfpEventRepository.save(rfpEvent);
    }

    /**
     * Get all the rfpEvents.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<RfpEvent> findAll(Pageable pageable) {
        log.debug("Request to get all RfpEvents");
        return rfpEventRepository.findAll(pageable);
    }


    /**
     * Get one rfpEvent by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<RfpEvent> findOne(Long id) {
        log.debug("Request to get RfpEvent : {}", id);
        return rfpEventRepository.findById(id);
    }

    /**
     * Delete the rfpEvent by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete RfpEvent : {}", id);
        rfpEventRepository.deleteById(id);
    }
}
