package rfp.loyality.web.rest;

import rfp.loyality.RfpApp;

import rfp.loyality.domain.RfpLocation;
import rfp.loyality.repository.RfpLocationRepository;
import rfp.loyality.service.RfpLocationService;
import rfp.loyality.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.Validator;

import javax.persistence.EntityManager;
import java.util.List;


import static rfp.loyality.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RfpLocationResource REST controller.
 *
 * @see RfpLocationResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RfpApp.class)
public class RfpLocationResourceIntTest {

    private static final String DEFAULT_LOCATION_NAME = "AAAAAAAAAA";
    private static final String UPDATED_LOCATION_NAME = "BBBBBBBBBB";

    private static final Integer DEFAULT_RUN_DAY_OF_WEEK = 1;
    private static final Integer UPDATED_RUN_DAY_OF_WEEK = 2;

    @Autowired
    private RfpLocationRepository rfpLocationRepository;

    @Autowired
    private RfpLocationService rfpLocationService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    @Autowired
    private Validator validator;

    private MockMvc restRfpLocationMockMvc;

    private RfpLocation rfpLocation;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RfpLocationResource rfpLocationResource = new RfpLocationResource(rfpLocationService);
        this.restRfpLocationMockMvc = MockMvcBuilders.standaloneSetup(rfpLocationResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter)
            .setValidator(validator).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static RfpLocation createEntity(EntityManager em) {
        RfpLocation rfpLocation = new RfpLocation()
            .locationName(DEFAULT_LOCATION_NAME)
            .runDayOfWeek(DEFAULT_RUN_DAY_OF_WEEK);
        return rfpLocation;
    }

    @Before
    public void initTest() {
        rfpLocation = createEntity(em);
    }

    @Test
    @Transactional
    public void createRfpLocation() throws Exception {
        int databaseSizeBeforeCreate = rfpLocationRepository.findAll().size();

        // Create the RfpLocation
        restRfpLocationMockMvc.perform(post("/api/rfp-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpLocation)))
            .andExpect(status().isCreated());

        // Validate the RfpLocation in the database
        List<RfpLocation> rfpLocationList = rfpLocationRepository.findAll();
        assertThat(rfpLocationList).hasSize(databaseSizeBeforeCreate + 1);
        RfpLocation testRfpLocation = rfpLocationList.get(rfpLocationList.size() - 1);
        assertThat(testRfpLocation.getLocationName()).isEqualTo(DEFAULT_LOCATION_NAME);
        assertThat(testRfpLocation.getRunDayOfWeek()).isEqualTo(DEFAULT_RUN_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void createRfpLocationWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rfpLocationRepository.findAll().size();

        // Create the RfpLocation with an existing ID
        rfpLocation.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRfpLocationMockMvc.perform(post("/api/rfp-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpLocation)))
            .andExpect(status().isBadRequest());

        // Validate the RfpLocation in the database
        List<RfpLocation> rfpLocationList = rfpLocationRepository.findAll();
        assertThat(rfpLocationList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRfpLocations() throws Exception {
        // Initialize the database
        rfpLocationRepository.saveAndFlush(rfpLocation);

        // Get all the rfpLocationList
        restRfpLocationMockMvc.perform(get("/api/rfp-locations?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rfpLocation.getId().intValue())))
            .andExpect(jsonPath("$.[*].locationName").value(hasItem(DEFAULT_LOCATION_NAME.toString())))
            .andExpect(jsonPath("$.[*].runDayOfWeek").value(hasItem(DEFAULT_RUN_DAY_OF_WEEK)));
    }
    
    @Test
    @Transactional
    public void getRfpLocation() throws Exception {
        // Initialize the database
        rfpLocationRepository.saveAndFlush(rfpLocation);

        // Get the rfpLocation
        restRfpLocationMockMvc.perform(get("/api/rfp-locations/{id}", rfpLocation.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rfpLocation.getId().intValue()))
            .andExpect(jsonPath("$.locationName").value(DEFAULT_LOCATION_NAME.toString()))
            .andExpect(jsonPath("$.runDayOfWeek").value(DEFAULT_RUN_DAY_OF_WEEK));
    }

    @Test
    @Transactional
    public void getNonExistingRfpLocation() throws Exception {
        // Get the rfpLocation
        restRfpLocationMockMvc.perform(get("/api/rfp-locations/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRfpLocation() throws Exception {
        // Initialize the database
        rfpLocationService.save(rfpLocation);

        int databaseSizeBeforeUpdate = rfpLocationRepository.findAll().size();

        // Update the rfpLocation
        RfpLocation updatedRfpLocation = rfpLocationRepository.findById(rfpLocation.getId()).get();
        // Disconnect from session so that the updates on updatedRfpLocation are not directly saved in db
        em.detach(updatedRfpLocation);
        updatedRfpLocation
            .locationName(UPDATED_LOCATION_NAME)
            .runDayOfWeek(UPDATED_RUN_DAY_OF_WEEK);

        restRfpLocationMockMvc.perform(put("/api/rfp-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRfpLocation)))
            .andExpect(status().isOk());

        // Validate the RfpLocation in the database
        List<RfpLocation> rfpLocationList = rfpLocationRepository.findAll();
        assertThat(rfpLocationList).hasSize(databaseSizeBeforeUpdate);
        RfpLocation testRfpLocation = rfpLocationList.get(rfpLocationList.size() - 1);
        assertThat(testRfpLocation.getLocationName()).isEqualTo(UPDATED_LOCATION_NAME);
        assertThat(testRfpLocation.getRunDayOfWeek()).isEqualTo(UPDATED_RUN_DAY_OF_WEEK);
    }

    @Test
    @Transactional
    public void updateNonExistingRfpLocation() throws Exception {
        int databaseSizeBeforeUpdate = rfpLocationRepository.findAll().size();

        // Create the RfpLocation

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRfpLocationMockMvc.perform(put("/api/rfp-locations")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpLocation)))
            .andExpect(status().isBadRequest());

        // Validate the RfpLocation in the database
        List<RfpLocation> rfpLocationList = rfpLocationRepository.findAll();
        assertThat(rfpLocationList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRfpLocation() throws Exception {
        // Initialize the database
        rfpLocationService.save(rfpLocation);

        int databaseSizeBeforeDelete = rfpLocationRepository.findAll().size();

        // Get the rfpLocation
        restRfpLocationMockMvc.perform(delete("/api/rfp-locations/{id}", rfpLocation.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RfpLocation> rfpLocationList = rfpLocationRepository.findAll();
        assertThat(rfpLocationList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RfpLocation.class);
        RfpLocation rfpLocation1 = new RfpLocation();
        rfpLocation1.setId(1L);
        RfpLocation rfpLocation2 = new RfpLocation();
        rfpLocation2.setId(rfpLocation1.getId());
        assertThat(rfpLocation1).isEqualTo(rfpLocation2);
        rfpLocation2.setId(2L);
        assertThat(rfpLocation1).isNotEqualTo(rfpLocation2);
        rfpLocation1.setId(null);
        assertThat(rfpLocation1).isNotEqualTo(rfpLocation2);
    }
}
