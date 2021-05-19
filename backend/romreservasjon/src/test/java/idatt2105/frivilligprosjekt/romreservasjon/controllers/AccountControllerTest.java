package idatt2105.frivilligprosjekt.romreservasjon.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import idatt2105.frivilligprosjekt.romreservasjon.model.*;
import idatt2105.frivilligprosjekt.romreservasjon.repository.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class AccountControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private EquipmentReservationRepository equipmentReservationRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    @Autowired
    private RoomRepository roomRepository;

    private Account account1;
    private Account account2;

    private Room room;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        account1 = new Account("testName1", "testEmail1", "testPassword1", "1234567", true, null);
        account2 = new Account("testName2", "testEmail2", "testPassword2", "1234567", true, null);
        accountRepository.save(account1);
        accountRepository.save(account2);

        Set<Section> sections = new HashSet<>();
        Section section1 = new Section("testSection1", "testDescription1", 2.0, 5);
        Section section2 = new Section("testSection2", "testDescription2", 5.0, 3);
        sections.add(section1);
        sections.add(section2);

        Equipment equipment1 = new Equipment("PC-screen");
        Equipment equipment2 = new Equipment("Speakers");
        equipmentRepository.save(equipment1);
        equipmentRepository.save(equipment2);

        room = new Room("testRoom", "testAddress", "2", "2", "testDescription", 8.0, 8);
        room.setSections(sections);
        roomRepository.save(room);

        Reservation reservation1 = new Reservation(null, null, 5, section1, account1);
        Reservation reservation2 = new Reservation(null, null, 3, section2, account2);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        EquipmentReservation equipmentReservation1 = new EquipmentReservation(null, null, equipment1, account1);
        EquipmentReservation equipmentReservation2 = new EquipmentReservation(null, null, equipment2, account2);
        equipmentReservationRepository.save(equipmentReservation1);
        equipmentReservationRepository.save(equipmentReservation2);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testGetAllAccounts() throws Exception {

        this.mockMvc.perform(get("/accounts")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("testName1")))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testFindingAccountByEmail() throws Exception {

        this.mockMvc.perform(get("/accounts/email=testEmail1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.*", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(content().string(containsString("testEmail1")));
    }

    @Test
    public void testFindingAccountById() throws Exception {

        this.mockMvc.perform(get("/accounts/1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testSavingAccount() throws Exception {
        Account account = new Account("savedAccount", "saveEmail", "savePassword", "1234567", false, null);
        String jsonString = objectMapper.writeValueAsString(account);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void testUpdateAccount() throws Exception {
        assertThat(this.account1.getId()).isEqualTo(1);
        assertThat(this.account1.getName()).isEqualTo("testName1");

        this.account1.setName("updatedName");

        String jsonString = objectMapper.writeValueAsString(this.account1);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/accounts/1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("updatedName")));
    }

    @Test
    public void testDeletingAccount() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/accounts/1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindAccountReservations() throws Exception {

        this.mockMvc.perform(get("/accounts/1/reservations")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].number_of_people", is(5)));
    }

    @Test
    public void testCreateAccountReservation() throws Exception {
        Account account3 = new Account("testName3", "testEmail3", "testPassword3", "1234567", false, null);
        accountRepository.save(account3);

        Section section3 = new Section("testSection3", "testDescription3", 5.0, 3);
        room.getSections().add(section3);
        roomRepository.save(room);

        Reservation reservation = new Reservation(null, null, 2, section3, account3);

        String jsonString = objectMapper.writeValueAsString(reservation);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/accounts/3/reservations")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(true)));

    }

    @Test
    public void testFindAccountEquipmentReservations() throws Exception {

        this.mockMvc.perform(get("/accounts/1/equipment-reservations")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].id", is(1)));
    }

    @Test
    public void testCreateAccountEquipmentReservation() throws Exception {

        Equipment equipment = new Equipment("Extender");
        equipmentRepository.save(equipment);

        EquipmentReservation equipmentReservation = new EquipmentReservation(null, null, equipment, account1);

        String jsonString = objectMapper.writeValueAsString(equipmentReservation);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/accounts/1/equipment-reservations")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(true)));
    }

    public static class SecurityMockMvcRequestPostProcessor {
        public static RequestPostProcessor admin() {
            return user("admin").roles("ADMIN");
        }
    }
}
