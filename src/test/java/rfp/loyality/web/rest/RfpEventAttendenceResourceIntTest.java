package rfp.loyality.web.rest;

import rfp.loyality.RfpApp;

import rfp.loyality.domain.RfpEventAttendence;
import rfp.loyality.repository.RfpEventAttendenceRepository;
import rfp.loyality.service.RfpEventAttendenceService;
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
 * Test class for the RfpEventAttendenceResource REST controller.
 *
 * @see RfpEventAttendenceResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RfpApp.class)
public class RfpEventAttendenceResourceIntTest {

    private static final String DEFAULT_ATTENDENCE_DATE = "AAAAAAAAAA";
    private static final String UPDATED_ATTENDENCE_DATE = "BBBBBBBBBB";

    @Autowired
    private RfpEventAttendenceRepository rfpEventAttendenceRepository;

    @Autowired
    private RfpEventAttendenceService rfpEventAttendenceService;

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

    private MockMvc restRfpEventAttendenceMockMvc;

    private RfpEventAttendence rfpEventAttendence;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RfpEventAttendenceResource rfpEventAttendenceResource = new RfpEventAttendenceResource(rfpEventAttendenceService);
        this.restRfpEventAttendenceMockMvc = MockMvcBuilders.standaloneSetup(rfpEventAttendenceResource)
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
    public static RfpEventAttendence createEntity(EntityManager em) {
        RfpEventAttendence rfpEventAttendence = new RfpEventAttendence()
            .attendenceDate(DEFAULT_ATTENDENCE_DATE);
        return rfpEventAttendence;
    }

    @Before
    public void initTest() {
        rfpEventAttendence = createEntity(em);
    }

    @Test
    @Transactional
    public void createRfpEventAttendence() throws Exception {
        int databaseSizeBeforeCreate = rfpEventAttendenceRepository.findAll().size();

        // Create the RfpEventAttendence
        restRfpEventAttendenceMockMvc.perform(post("/api/rfp-event-attendences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpEventAttendence)))
            .andExpect(status().isCreated());

        // Validate the RfpEventAttendence in the database
        List<RfpEventAttendence> rfpEventAttendenceList = rfpEventAttendenceRepository.findAll();
        assertThat(rfpEventAttendenceList).hasSize(databaseSizeBeforeCreate + 1);
        RfpEventAttendence testRfpEventAttendence = rfpEventAttendenceList.get(rfpEventAttendenceList.size() - 1);
        assertThat(testRfpEventAttendence.getAttendenceDate()).isEqualTo(DEFAULT_ATTENDENCE_DATE);
    }

    @Test
    @Transactional
    public void createRfpEventAttendenceWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rfpEventAttendenceRepository.findAll().size();

        // Create the RfpEventAttendence with an existing ID
        rfpEventAttendence.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRfpEventAttendenceMockMvc.perform(post("/api/rfp-event-attendences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpEventAttendence)))
            .andExpect(status().isBadRequest());

        // Validate the RfpEventAttendence in the database
        List<RfpEventAttendence> rfpEventAttendenceList = rfpEventAttendenceRepository.findAll();
        assertThat(rfpEventAttendenceList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRfpEventAttendences() throws Exception {
        // Initialize the database
        rfpEventAttendenceRepository.saveAndFlush(rfpEventAttendence);

        // Get all the rfpEventAttendenceList
        restRfpEventAttendenceMockMvc.perform(get("/api/rfp-event-attendences?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rfpEventAttendence.getId().intValue())))
            .andExpect(jsonPath("$.[*].attendenceDate").value(hasItem(DEFAULT_ATTENDENCE_DATE.toString())));
    }
    
    @Test
    @Transactional
    public void getRfpEventAttendence() throws Exception {
        // Initialize the database
        rfpEventAttendenceRepository.saveAndFlush(rfpEventAttendence);

        // Get the rfpEventAttendence
        restRfpEventAttendenceMockMvc.perform(get("/api/rfp-event-attendences/{id}", rfpEventAttendence.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rfpEventAttendence.getId().intValue()))
            .andExpect(jsonPath("$.attendenceDate").value(DEFAULT_ATTENDENCE_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRfpEventAttendence() throws Exception {
        // Get the rfpEventAttendence
        restRfpEventAttendenceMockMvc.perform(get("/api/rfp-event-attendences/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRfpEventAttendence() throws Exception {
        // Initialize the database
        rfpEventAttendenceService.save(rfpEventAttendence);

        int databaseSizeBeforeUpdate = rfpEventAttendenceRepository.findAll().size();

        // Update the rfpEventAttendence
        RfpEventAttendence updatedRfpEventAttendence = rfpEventAttendenceRepository.findById(rfpEventAttendence.getId()).get();
        // Disconnect from session so that the updates on updatedRfpEventAttendence are not directly saved in db
        em.detach(updatedRfpEventAttendence);
        updatedRfpEventAttendence
            .attendenceDate(UPDATED_ATTENDENCE_DATE);

        restRfpEventAttendenceMockMvc.perform(put("/api/rfp-event-attendences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRfpEventAttendence)))
            .andExpect(status().isOk());

        // Validate the RfpEventAttendence in the database
        List<RfpEventAttendence> rfpEventAttendenceList = rfpEventAttendenceRepository.findAll();
        assertThat(rfpEventAttendenceList).hasSize(databaseSizeBeforeUpdate);
        RfpEventAttendence testRfpEventAttendence = rfpEventAttendenceList.get(rfpEventAttendenceList.size() - 1);
        assertThat(testRfpEventAttendence.getAttendenceDate()).isEqualTo(UPDATED_ATTENDENCE_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingRfpEventAttendence() throws Exception {
        int databaseSizeBeforeUpdate = rfpEventAttendenceRepository.findAll().size();

        // Create the RfpEventAttendence

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRfpEventAttendenceMockMvc.perform(put("/api/rfp-event-attendences")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpEventAttendence)))
            .andExpect(status().isBadRequest());

        // Validate the RfpEventAttendence in the database
        List<RfpEventAttendence> rfpEventAttendenceList = rfpEventAttendenceRepository.findAll();
        assertThat(rfpEventAttendenceList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRfpEventAttendence() throws Exception {
        // Initialize the database
        rfpEventAttendenceService.save(rfpEventAttendence);

        int databaseSizeBeforeDelete = rfpEventAttendenceRepository.findAll().size();

        // Get the rfpEventAttendence
        restRfpEventAttendenceMockMvc.perform(delete("/api/rfp-event-attendences/{id}", rfpEventAttendence.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RfpEventAttendence> rfpEventAttendenceList = rfpEventAttendenceRepository.findAll();
        assertThat(rfpEventAttendenceList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RfpEventAttendence.class);
        RfpEventAttendence rfpEventAttendence1 = new RfpEventAttendence();
        rfpEventAttendence1.setId(1L);
        RfpEventAttendence rfpEventAttendence2 = new RfpEventAttendence();
        rfpEventAttendence2.setId(rfpEventAttendence1.getId());
        assertThat(rfpEventAttendence1).isEqualTo(rfpEventAttendence2);
        rfpEventAttendence2.setId(2L);
        assertThat(rfpEventAttendence1).isNotEqualTo(rfpEventAttendence2);
        rfpEventAttendence1.setId(null);
        assertThat(rfpEventAttendence1).isNotEqualTo(rfpEventAttendence2);
    }
}
