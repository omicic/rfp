package rfp.loyality.web.rest;

import com.codahale.metrics.annotation.Timed;
import rfp.loyality.domain.RfpEventAttendance;
import rfp.loyality.service.RfpEventAttendenceService;
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
 * REST controller for managing RfpEventAttendence.
 */
@RestController
@RequestMapping("/api")
public class RfpEventAttendenceResource {

    private final Logger log = LoggerFactory.getLogger(RfpEventAttendenceResource.class);

    private static final String ENTITY_NAME = "rfpEventAttendence";

    private final RfpEventAttendenceService rfpEventAttendenceService;

    public RfpEventAttendenceResource(RfpEventAttendenceService rfpEventAttendenceService) {
        this.rfpEventAttendenceService = rfpEventAttendenceService;
    }

    /**
     * POST  /rfp-event-attendences : Create a new rfpEventAttendence.
     *
     * @param rfpEventAttendence the rfpEventAttendence to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rfpEventAttendence, or with status 400 (Bad Request) if the rfpEventAttendence has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rfp-event-attendences")
    @Timed
    public ResponseEntity<RfpEventAttendance> createRfpEventAttendence(@RequestBody RfpEventAttendance rfpEventAttendence) throws URISyntaxException {
        log.debug("REST request to save RfpEventAttendence : {}", rfpEventAttendence);
        if (rfpEventAttendence.getId() != null) {
            throw new BadRequestAlertException("A new rfpEventAttendence cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RfpEventAttendance result = rfpEventAttendenceService.save(rfpEventAttendence);
        return ResponseEntity.created(new URI("/api/rfp-event-attendences/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rfp-event-attendences : Updates an existing rfpEventAttendence.
     *
     * @param rfpEventAttendence the rfpEventAttendence to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rfpEventAttendence,
     * or with status 400 (Bad Request) if the rfpEventAttendence is not valid,
     * or with status 500 (Internal Server Error) if the rfpEventAttendence couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rfp-event-attendences")
    @Timed
    public ResponseEntity<RfpEventAttendance> updateRfpEventAttendence(@RequestBody RfpEventAttendance rfpEventAttendence) throws URISyntaxException {
        log.debug("REST request to update RfpEventAttendence : {}", rfpEventAttendence);
        if (rfpEventAttendence.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RfpEventAttendance result = rfpEventAttendenceService.save(rfpEventAttendence);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rfpEventAttendence.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rfp-event-attendences : get all the rfpEventAttendences.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rfpEventAttendences in body
     */
    @GetMapping("/rfp-event-attendences")
    @Timed
    public ResponseEntity<List<RfpEventAttendance>> getAllRfpEventAttendences(Pageable pageable) {
        log.debug("REST request to get a page of RfpEventAttendences");
        Page<RfpEventAttendance> page = rfpEventAttendenceService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rfp-event-attendences");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rfp-event-attendences/:id : get the "id" rfpEventAttendence.
     *
     * @param id the id of the rfpEventAttendence to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rfpEventAttendence, or with status 404 (Not Found)
     */
    @GetMapping("/rfp-event-attendences/{id}")
    @Timed
    public ResponseEntity<RfpEventAttendance> getRfpEventAttendence(@PathVariable Long id) {
        log.debug("REST request to get RfpEventAttendence : {}", id);
        Optional<RfpEventAttendance> rfpEventAttendence = rfpEventAttendenceService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rfpEventAttendence);
    }

    /**
     * DELETE  /rfp-event-attendences/:id : delete the "id" rfpEventAttendence.
     *
     * @param id the id of the rfpEventAttendence to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rfp-event-attendences/{id}")
    @Timed
    public ResponseEntity<Void> deleteRfpEventAttendence(@PathVariable Long id) {
        log.debug("REST request to delete RfpEventAttendence : {}", id);
        rfpEventAttendenceService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
