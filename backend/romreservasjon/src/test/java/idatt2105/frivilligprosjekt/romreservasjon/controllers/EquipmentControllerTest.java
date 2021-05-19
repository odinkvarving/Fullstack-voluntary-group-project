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
public class EquipmentControllerTest {

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

    private Equipment equipment1;
    private Equipment equipment2;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        account1 = new Account("testName1", "testEmail1", "testPassword1", "1234567", true, null);
        account2 = new Account("testName2", "testEmail2", "testPassword2", "1234567", true, null);
        accountRepository.save(account1);
        accountRepository.save(account2);

        equipment1 = new Equipment("PC-screen");
        equipment2 = new Equipment("Speakers");
        equipmentRepository.save(equipment1);
        equipmentRepository.save(equipment2);

        EquipmentReservation equipmentReservation1 = new EquipmentReservation(null, null, equipment1, account1);
        EquipmentReservation equipmentReservation2 = new EquipmentReservation(null, null, equipment2, account2);
        equipmentReservationRepository.save(equipmentReservation1);
        equipmentReservationRepository.save(equipmentReservation2);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testGetAllEquipment() throws Exception {
        this.mockMvc.perform(get("/equipments")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("PC-screen")))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testFindEquipmentById() throws Exception {
        this.mockMvc.perform(get("/equipments/1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testSaveEquipment() throws Exception {
        Equipment equipment = new Equipment("HDMI-cable");
        String jsonString = objectMapper.writeValueAsString(equipment);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/equipments")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(true)));
    }

    @Test
    public void testUpdateEquipment() throws Exception {
        assertThat(this.equipment1.getId()).isEqualTo(1);
        assertThat(this.equipment1.getName()).isEqualTo("PC-screen");

        this.equipment1.setName("Asus PC-screen");

        String jsonString = objectMapper.writeValueAsString(this.equipment1);

        this.mockMvc.perform(MockMvcRequestBuilders.put("/equipments/1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.name", is("Asus PC-screen")));
    }

    @Test
    public void testDeleteEquipment() throws Exception {

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/equipments/1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }

    public static class SecurityMockMvcRequestPostProcessor {
        public static RequestPostProcessor admin() {
            return user("admin").roles("ADMIN");
        }
    }
}
