package rfp.loyality.web.rest;

import rfp.loyality.RfpApp;

import rfp.loyality.domain.RfpEvent;
import rfp.loyality.repository.RfpEventRepository;
import rfp.loyality.service.RfpEventService;
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
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;


import static rfp.loyality.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the RfpEventResource REST controller.
 *
 * @see RfpEventResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RfpApp.class)
public class RfpEventResourceIntTest {

    private static final LocalDate DEFAULT_EVENT_DATE = LocalDate.ofEpochDay(0L);
    private static final LocalDate UPDATED_EVENT_DATE = LocalDate.now(ZoneId.systemDefault());

    private static final String DEFAULT_EVENT_CODE = "AAAAAAAAAA";
    private static final String UPDATED_EVENT_CODE = "BBBBBBBBBB";

    @Autowired
    private RfpEventRepository rfpEventRepository;

    @Autowired
    private RfpEventService rfpEventService;

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

    private MockMvc restRfpEventMockMvc;

    private RfpEvent rfpEvent;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RfpEventResource rfpEventResource = new RfpEventResource(rfpEventService);
        this.restRfpEventMockMvc = MockMvcBuilders.standaloneSetup(rfpEventResource)
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
    public static RfpEvent createEntity(EntityManager em) {
        RfpEvent rfpEvent = new RfpEvent()
            .eventDate(DEFAULT_EVENT_DATE)
            .eventCode(DEFAULT_EVENT_CODE);
        return rfpEvent;
    }

    @Before
    public void initTest() {
        rfpEvent = createEntity(em);
    }

    @Test
    @Transactional
    public void createRfpEvent() throws Exception {
        int databaseSizeBeforeCreate = rfpEventRepository.findAll().size();

        // Create the RfpEvent
        restRfpEventMockMvc.perform(post("/api/rfp-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpEvent)))
            .andExpect(status().isCreated());

        // Validate the RfpEvent in the database
        List<RfpEvent> rfpEventList = rfpEventRepository.findAll();
        assertThat(rfpEventList).hasSize(databaseSizeBeforeCreate + 1);
        RfpEvent testRfpEvent = rfpEventList.get(rfpEventList.size() - 1);
        assertThat(testRfpEvent.getEventDate()).isEqualTo(DEFAULT_EVENT_DATE);
        assertThat(testRfpEvent.getEventCode()).isEqualTo(DEFAULT_EVENT_CODE);
    }

    @Test
    @Transactional
    public void createRfpEventWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rfpEventRepository.findAll().size();

        // Create the RfpEvent with an existing ID
        rfpEvent.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRfpEventMockMvc.perform(post("/api/rfp-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpEvent)))
            .andExpect(status().isBadRequest());

        // Validate the RfpEvent in the database
        List<RfpEvent> rfpEventList = rfpEventRepository.findAll();
        assertThat(rfpEventList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRfpEvents() throws Exception {
        // Initialize the database
        rfpEventRepository.saveAndFlush(rfpEvent);

        // Get all the rfpEventList
        restRfpEventMockMvc.perform(get("/api/rfp-events?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rfpEvent.getId().intValue())))
            .andExpect(jsonPath("$.[*].eventDate").value(hasItem(DEFAULT_EVENT_DATE.toString())))
            .andExpect(jsonPath("$.[*].eventCode").value(hasItem(DEFAULT_EVENT_CODE.toString())));
    }
    
    @Test
    @Transactional
    public void getRfpEvent() throws Exception {
        // Initialize the database
        rfpEventRepository.saveAndFlush(rfpEvent);

        // Get the rfpEvent
        restRfpEventMockMvc.perform(get("/api/rfp-events/{id}", rfpEvent.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rfpEvent.getId().intValue()))
            .andExpect(jsonPath("$.eventDate").value(DEFAULT_EVENT_DATE.toString()))
            .andExpect(jsonPath("$.eventCode").value(DEFAULT_EVENT_CODE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRfpEvent() throws Exception {
        // Get the rfpEvent
        restRfpEventMockMvc.perform(get("/api/rfp-events/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRfpEvent() throws Exception {
        // Initialize the database
        rfpEventService.save(rfpEvent);

        int databaseSizeBeforeUpdate = rfpEventRepository.findAll().size();

        // Update the rfpEvent
        RfpEvent updatedRfpEvent = rfpEventRepository.findById(rfpEvent.getId()).get();
        // Disconnect from session so that the updates on updatedRfpEvent are not directly saved in db
        em.detach(updatedRfpEvent);
        updatedRfpEvent
            .eventDate(UPDATED_EVENT_DATE)
            .eventCode(UPDATED_EVENT_CODE);

        restRfpEventMockMvc.perform(put("/api/rfp-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRfpEvent)))
            .andExpect(status().isOk());

        // Validate the RfpEvent in the database
        List<RfpEvent> rfpEventList = rfpEventRepository.findAll();
        assertThat(rfpEventList).hasSize(databaseSizeBeforeUpdate);
        RfpEvent testRfpEvent = rfpEventList.get(rfpEventList.size() - 1);
        assertThat(testRfpEvent.getEventDate()).isEqualTo(UPDATED_EVENT_DATE);
        assertThat(testRfpEvent.getEventCode()).isEqualTo(UPDATED_EVENT_CODE);
    }

    @Test
    @Transactional
    public void updateNonExistingRfpEvent() throws Exception {
        int databaseSizeBeforeUpdate = rfpEventRepository.findAll().size();

        // Create the RfpEvent

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRfpEventMockMvc.perform(put("/api/rfp-events")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpEvent)))
            .andExpect(status().isBadRequest());

        // Validate the RfpEvent in the database
        List<RfpEvent> rfpEventList = rfpEventRepository.findAll();
        assertThat(rfpEventList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRfpEvent() throws Exception {
        // Initialize the database
        rfpEventService.save(rfpEvent);

        int databaseSizeBeforeDelete = rfpEventRepository.findAll().size();

        // Get the rfpEvent
        restRfpEventMockMvc.perform(delete("/api/rfp-events/{id}", rfpEvent.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RfpEvent> rfpEventList = rfpEventRepository.findAll();
        assertThat(rfpEventList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RfpEvent.class);
        RfpEvent rfpEvent1 = new RfpEvent();
        rfpEvent1.setId(1L);
        RfpEvent rfpEvent2 = new RfpEvent();
        rfpEvent2.setId(rfpEvent1.getId());
        assertThat(rfpEvent1).isEqualTo(rfpEvent2);
        rfpEvent2.setId(2L);
        assertThat(rfpEvent1).isNotEqualTo(rfpEvent2);
        rfpEvent1.setId(null);
        assertThat(rfpEvent1).isNotEqualTo(rfpEvent2);
    }
}
