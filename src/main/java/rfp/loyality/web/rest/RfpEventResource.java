package rfp.loyality.web.rest;

import com.codahale.metrics.annotation.Timed;
import rfp.loyality.domain.RfpEvent;
import rfp.loyality.service.RfpEventService;
import rfp.loyality.web.rest.errors.BadRequestAlertException;
import rfp.loyality.web.rest.util.HeaderUtil;
import rfp.loyality.web.rest.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RfpEvent.
 */
@RestController
@RequestMapping("/api")
public class RfpEventResource {

    private final Logger log = LoggerFactory.getLogger(RfpEventResource.class);

    private static final String ENTITY_NAME = "rfpEvent";

    private final RfpEventService rfpEventService;

    public RfpEventResource(RfpEventService rfpEventService) {
        this.rfpEventService = rfpEventService;
    }

    /**
     * POST  /rfp-events : Create a new rfpEvent.
     *
     * @param rfpEvent the rfpEvent to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rfpEvent, or with status 400 (Bad Request) if the rfpEvent has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rfp-events")
    @Timed
    public ResponseEntity<RfpEvent> createRfpEvent(@RequestBody RfpEvent rfpEvent) throws URISyntaxException {
        log.debug("REST request to save RfpEvent : {}", rfpEvent);
        if (rfpEvent.getId() != null) {
            throw new BadRequestAlertException("A new rfpEvent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RfpEvent result = rfpEventService.save(rfpEvent);
        return ResponseEntity.created(new URI("/api/rfp-events/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rfp-events : Updates an existing rfpEvent.
     *
     * @param rfpEvent the rfpEvent to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rfpEvent,
     * or with status 400 (Bad Request) if the rfpEvent is not valid,
     * or with status 500 (Internal Server Error) if the rfpEvent couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rfp-events")
    @Timed
    public ResponseEntity<RfpEvent> updateRfpEvent(@RequestBody RfpEvent rfpEvent) throws URISyntaxException {
        log.debug("REST request to update RfpEvent : {}", rfpEvent);
        if (rfpEvent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RfpEvent result = rfpEventService.save(rfpEvent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rfpEvent.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rfp-events : get all the rfpEvents.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rfpEvents in body
     */
    @GetMapping("/rfp-events")
    @Timed
    public ResponseEntity<List<RfpEvent>> getAllRfpEvents(Pageable pageable) {
        log.debug("REST request to get a page of RfpEvents");
        Page<RfpEvent> page = rfpEventService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rfp-events");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rfp-events/:id : get the "id" rfpEvent.
     *
     * @param id the id of the rfpEvent to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rfpEvent, or with status 404 (Not Found)
     */
    @GetMapping("/rfp-events/{id}")
    @Timed
    public ResponseEntity<RfpEvent> getRfpEvent(@PathVariable Long id) {
        log.debug("REST request to get RfpEvent : {}", id);
        Optional<RfpEvent> rfpEvent = rfpEventService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rfpEvent);
    }

    /**
     * DELETE  /rfp-events/:id : delete the "id" rfpEvent.
     *
     * @param id the id of the rfpEvent to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rfp-events/{id}")
    @Timed
    public ResponseEntity<Void> deleteRfpEvent(@PathVariable Long id) {
        log.debug("REST request to delete RfpEvent : {}", id);
        rfpEventService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
