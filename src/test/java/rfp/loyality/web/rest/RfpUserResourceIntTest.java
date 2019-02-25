package rfp.loyality.web.rest;

import rfp.loyality.RfpApp;

import rfp.loyality.domain.RfpUser;
import rfp.loyality.repository.RfpUserRepository;
import rfp.loyality.service.RfpUserService;
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
 * Test class for the RfpUserResource REST controller.
 *
 * @see RfpUserResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = RfpApp.class)
public class RfpUserResourceIntTest {

    private static final String DEFAULT_USER_NAME = "AAAAAAAAAA";
    private static final String UPDATED_USER_NAME = "BBBBBBBBBB";

    @Autowired
    private RfpUserRepository rfpUserRepository;

    @Autowired
    private RfpUserService rfpUserService;

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

    private MockMvc restRfpUserMockMvc;

    private RfpUser rfpUser;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RfpUserResource rfpUserResource = new RfpUserResource(rfpUserService);
        this.restRfpUserMockMvc = MockMvcBuilders.standaloneSetup(rfpUserResource)
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
    public static RfpUser createEntity(EntityManager em) {
        RfpUser rfpUser = new RfpUser()
            .userName(DEFAULT_USER_NAME);
        return rfpUser;
    }

    @Before
    public void initTest() {
        rfpUser = createEntity(em);
    }

    @Test
    @Transactional
    public void createRfpUser() throws Exception {
        int databaseSizeBeforeCreate = rfpUserRepository.findAll().size();

        // Create the RfpUser
        restRfpUserMockMvc.perform(post("/api/rfp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpUser)))
            .andExpect(status().isCreated());

        // Validate the RfpUser in the database
        List<RfpUser> rfpUserList = rfpUserRepository.findAll();
        assertThat(rfpUserList).hasSize(databaseSizeBeforeCreate + 1);
        RfpUser testRfpUser = rfpUserList.get(rfpUserList.size() - 1);
        assertThat(testRfpUser.getUserName()).isEqualTo(DEFAULT_USER_NAME);
    }

    @Test
    @Transactional
    public void createRfpUserWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = rfpUserRepository.findAll().size();

        // Create the RfpUser with an existing ID
        rfpUser.setId(1L);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRfpUserMockMvc.perform(post("/api/rfp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpUser)))
            .andExpect(status().isBadRequest());

        // Validate the RfpUser in the database
        List<RfpUser> rfpUserList = rfpUserRepository.findAll();
        assertThat(rfpUserList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRfpUsers() throws Exception {
        // Initialize the database
        rfpUserRepository.saveAndFlush(rfpUser);

        // Get all the rfpUserList
        restRfpUserMockMvc.perform(get("/api/rfp-users?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(rfpUser.getId().intValue())))
            .andExpect(jsonPath("$.[*].userName").value(hasItem(DEFAULT_USER_NAME.toString())));
    }
    
    @Test
    @Transactional
    public void getRfpUser() throws Exception {
        // Initialize the database
        rfpUserRepository.saveAndFlush(rfpUser);

        // Get the rfpUser
        restRfpUserMockMvc.perform(get("/api/rfp-users/{id}", rfpUser.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(rfpUser.getId().intValue()))
            .andExpect(jsonPath("$.userName").value(DEFAULT_USER_NAME.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingRfpUser() throws Exception {
        // Get the rfpUser
        restRfpUserMockMvc.perform(get("/api/rfp-users/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRfpUser() throws Exception {
        // Initialize the database
        rfpUserService.save(rfpUser);

        int databaseSizeBeforeUpdate = rfpUserRepository.findAll().size();

        // Update the rfpUser
        RfpUser updatedRfpUser = rfpUserRepository.findById(rfpUser.getId()).get();
        // Disconnect from session so that the updates on updatedRfpUser are not directly saved in db
        em.detach(updatedRfpUser);
        updatedRfpUser
            .userName(UPDATED_USER_NAME);

        restRfpUserMockMvc.perform(put("/api/rfp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(updatedRfpUser)))
            .andExpect(status().isOk());

        // Validate the RfpUser in the database
        List<RfpUser> rfpUserList = rfpUserRepository.findAll();
        assertThat(rfpUserList).hasSize(databaseSizeBeforeUpdate);
        RfpUser testRfpUser = rfpUserList.get(rfpUserList.size() - 1);
        assertThat(testRfpUser.getUserName()).isEqualTo(UPDATED_USER_NAME);
    }

    @Test
    @Transactional
    public void updateNonExistingRfpUser() throws Exception {
        int databaseSizeBeforeUpdate = rfpUserRepository.findAll().size();

        // Create the RfpUser

        // If the entity doesn't have an ID, it will throw BadRequestAlertException
        restRfpUserMockMvc.perform(put("/api/rfp-users")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(rfpUser)))
            .andExpect(status().isBadRequest());

        // Validate the RfpUser in the database
        List<RfpUser> rfpUserList = rfpUserRepository.findAll();
        assertThat(rfpUserList).hasSize(databaseSizeBeforeUpdate);
    }

    @Test
    @Transactional
    public void deleteRfpUser() throws Exception {
        // Initialize the database
        rfpUserService.save(rfpUser);

        int databaseSizeBeforeDelete = rfpUserRepository.findAll().size();

        // Get the rfpUser
        restRfpUserMockMvc.perform(delete("/api/rfp-users/{id}", rfpUser.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<RfpUser> rfpUserList = rfpUserRepository.findAll();
        assertThat(rfpUserList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(RfpUser.class);
        RfpUser rfpUser1 = new RfpUser();
        rfpUser1.setId(1L);
        RfpUser rfpUser2 = new RfpUser();
        rfpUser2.setId(rfpUser1.getId());
        assertThat(rfpUser1).isEqualTo(rfpUser2);
        rfpUser2.setId(2L);
        assertThat(rfpUser1).isNotEqualTo(rfpUser2);
        rfpUser1.setId(null);
        assertThat(rfpUser1).isNotEqualTo(rfpUser2);
    }
}
