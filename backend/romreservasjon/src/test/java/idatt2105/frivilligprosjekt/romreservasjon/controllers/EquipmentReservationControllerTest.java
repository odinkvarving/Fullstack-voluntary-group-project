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

import java.util.LinkedHashMap;

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
public class EquipmentReservationControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private EquipmentReservationRepository equipmentReservationRepository;

    @Autowired
    private EquipmentRepository equipmentRepository;

    private Account account1;
    private Account account2;

    private EquipmentReservation equipmentReservation1;
    private EquipmentReservation equipmentReservation2;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        account1 = new Account("testName1", "testEmail1", "testPassword1", "1234567", true, null);
        account2 = new Account("testName2", "testEmail2", "testPassword2", "1234567", true, null);
        accountRepository.save(account1);
        accountRepository.save(account2);

        Equipment equipment1 = new Equipment("PC-screen");
        Equipment equipment2 = new Equipment("Speakers");
        equipmentRepository.save(equipment1);
        equipmentRepository.save(equipment2);

        equipmentReservation1 = new EquipmentReservation(null, null, equipment1, account1);
        equipmentReservation2 = new EquipmentReservation(null, null, equipment2, account2);
        equipmentReservationRepository.save(equipmentReservation1);
        equipmentReservationRepository.save(equipmentReservation2);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testGetAllEquipmentReservations() throws Exception {

        this.mockMvc.perform(get("/equipment-reservations")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testSaveEquipmentReservation() throws Exception {

        Equipment equipment3 = new Equipment("HDMI-cable");
        equipmentRepository.save(equipment3);

        EquipmentReservation equipmentReservation = new EquipmentReservation(null, null, equipment3, account1);

        System.out.println(equipmentReservation.getEquipment().getName());

        String jsonString = objectMapper.writeValueAsString(equipmentReservation);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/equipment-reservations")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .content(jsonString)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void testUpdateEquipmentReservation() throws Exception {

        assertThat(this.equipmentReservation1.getId()).isEqualTo(1);

        Equipment equipment = new Equipment("Speaker");
        equipmentRepository.save(equipment);

        this.equipmentReservation1.setEquipment(equipment);

        String jsonString = objectMapper.writeValueAsString(equipmentReservation1);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/equipment-reservations/1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testFindEquipmentReservationById() throws Exception {

        this.mockMvc.perform(get("/equipment-reservations/1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testDeleteEquipmentReservation() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/equipment-reservations/1")
                .with(AccountControllerTest.SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    @Test
    public void testFindReservationEquipment() throws Exception {

        this.mockMvc.perform(get("/equipment-reservations/1/equipments")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    public static class SecurityMockMvcRequestPostProcessor {
        public static RequestPostProcessor admin() {
            return user("admin").roles("ADMIN");
        }
    }
}
