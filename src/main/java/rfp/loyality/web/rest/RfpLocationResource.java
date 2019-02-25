package rfp.loyality.web.rest;

import com.codahale.metrics.annotation.Timed;
import rfp.loyality.domain.RfpLocation;
import rfp.loyality.service.RfpLocationService;
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
 * REST controller for managing RfpLocation.
 */
@RestController
@RequestMapping("/api")
public class RfpLocationResource {

    private final Logger log = LoggerFactory.getLogger(RfpLocationResource.class);

    private static final String ENTITY_NAME = "rfpLocation";

    private final RfpLocationService rfpLocationService;

    public RfpLocationResource(RfpLocationService rfpLocationService) {
        this.rfpLocationService = rfpLocationService;
    }

    /**
     * POST  /rfp-locations : Create a new rfpLocation.
     *
     * @param rfpLocation the rfpLocation to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rfpLocation, or with status 400 (Bad Request) if the rfpLocation has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rfp-locations")
    @Timed
    public ResponseEntity<RfpLocation> createRfpLocation(@RequestBody RfpLocation rfpLocation) throws URISyntaxException {
        log.debug("REST request to save RfpLocation : {}", rfpLocation);
        if (rfpLocation.getId() != null) {
            throw new BadRequestAlertException("A new rfpLocation cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RfpLocation result = rfpLocationService.save(rfpLocation);
        return ResponseEntity.created(new URI("/api/rfp-locations/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rfp-locations : Updates an existing rfpLocation.
     *
     * @param rfpLocation the rfpLocation to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rfpLocation,
     * or with status 400 (Bad Request) if the rfpLocation is not valid,
     * or with status 500 (Internal Server Error) if the rfpLocation couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rfp-locations")
    @Timed
    public ResponseEntity<RfpLocation> updateRfpLocation(@RequestBody RfpLocation rfpLocation) throws URISyntaxException {
        log.debug("REST request to update RfpLocation : {}", rfpLocation);
        if (rfpLocation.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RfpLocation result = rfpLocationService.save(rfpLocation);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rfpLocation.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rfp-locations : get all the rfpLocations.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of rfpLocations in body
     */
    @GetMapping("/rfp-locations")
    @Timed
    public ResponseEntity<List<RfpLocation>> getAllRfpLocations(Pageable pageable) {
        log.debug("REST request to get a page of RfpLocations");
        Page<RfpLocation> page = rfpLocationService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/rfp-locations");
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * GET  /rfp-locations/:id : get the "id" rfpLocation.
     *
     * @param id the id of the rfpLocation to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rfpLocation, or with status 404 (Not Found)
     */
    @GetMapping("/rfp-locations/{id}")
    @Timed
    public ResponseEntity<RfpLocation> getRfpLocation(@PathVariable Long id) {
        log.debug("REST request to get RfpLocation : {}", id);
        Optional<RfpLocation> rfpLocation = rfpLocationService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rfpLocation);
    }

    /**
     * DELETE  /rfp-locations/:id : delete the "id" rfpLocation.
     *
     * @param id the id of the rfpLocation to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rfp-locations/{id}")
    @Timed
    public ResponseEntity<Void> deleteRfpLocation(@PathVariable Long id) {
        log.debug("REST request to delete RfpLocation : {}", id);
        rfpLocationService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
