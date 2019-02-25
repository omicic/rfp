package rfp.loyality.web.rest;

import com.codahale.metrics.annotation.Timed;
import rfp.loyality.domain.RfpUser;
import rfp.loyality.service.RfpUserService;
import rfp.loyality.web.rest.errors.BadRequestAlertException;
import rfp.loyality.web.rest.util.HeaderUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing RfpUser.
 */
@RestController
@RequestMapping("/api")
public class RfpUserResource {

    private final Logger log = LoggerFactory.getLogger(RfpUserResource.class);

    private static final String ENTITY_NAME = "rfpUser";

    private final RfpUserService rfpUserService;

    public RfpUserResource(RfpUserService rfpUserService) {
        this.rfpUserService = rfpUserService;
    }

    /**
     * POST  /rfp-users : Create a new rfpUser.
     *
     * @param rfpUser the rfpUser to create
     * @return the ResponseEntity with status 201 (Created) and with body the new rfpUser, or with status 400 (Bad Request) if the rfpUser has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/rfp-users")
    @Timed
    public ResponseEntity<RfpUser> createRfpUser(@RequestBody RfpUser rfpUser) throws URISyntaxException {
        log.debug("REST request to save RfpUser : {}", rfpUser);
        if (rfpUser.getId() != null) {
            throw new BadRequestAlertException("A new rfpUser cannot already have an ID", ENTITY_NAME, "idexists");
        }
        RfpUser result = rfpUserService.save(rfpUser);
        return ResponseEntity.created(new URI("/api/rfp-users/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /rfp-users : Updates an existing rfpUser.
     *
     * @param rfpUser the rfpUser to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated rfpUser,
     * or with status 400 (Bad Request) if the rfpUser is not valid,
     * or with status 500 (Internal Server Error) if the rfpUser couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/rfp-users")
    @Timed
    public ResponseEntity<RfpUser> updateRfpUser(@RequestBody RfpUser rfpUser) throws URISyntaxException {
        log.debug("REST request to update RfpUser : {}", rfpUser);
        if (rfpUser.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        RfpUser result = rfpUserService.save(rfpUser);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, rfpUser.getId().toString()))
            .body(result);
    }

    /**
     * GET  /rfp-users : get all the rfpUsers.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of rfpUsers in body
     */
    @GetMapping("/rfp-users")
    @Timed
    public List<RfpUser> getAllRfpUsers() {
        log.debug("REST request to get all RfpUsers");
        return rfpUserService.findAll();
    }

    /**
     * GET  /rfp-users/:id : get the "id" rfpUser.
     *
     * @param id the id of the rfpUser to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the rfpUser, or with status 404 (Not Found)
     */
    @GetMapping("/rfp-users/{id}")
    @Timed
    public ResponseEntity<RfpUser> getRfpUser(@PathVariable Long id) {
        log.debug("REST request to get RfpUser : {}", id);
        Optional<RfpUser> rfpUser = rfpUserService.findOne(id);
        return ResponseUtil.wrapOrNotFound(rfpUser);
    }

    /**
     * DELETE  /rfp-users/:id : delete the "id" rfpUser.
     *
     * @param id the id of the rfpUser to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/rfp-users/{id}")
    @Timed
    public ResponseEntity<Void> deleteRfpUser(@PathVariable Long id) {
        log.debug("REST request to delete RfpUser : {}", id);
        rfpUserService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
