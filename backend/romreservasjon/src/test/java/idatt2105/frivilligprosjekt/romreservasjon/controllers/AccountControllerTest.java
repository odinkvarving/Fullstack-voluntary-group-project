package idatt2105.frivilligprosjekt.romreservasjon.controllers;

import idatt2105.frivilligprosjekt.romreservasjon.controller.AccountController;
import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.model.Room;
import idatt2105.frivilligprosjekt.romreservasjon.model.Section;
import idatt2105.frivilligprosjekt.romreservasjon.repository.AccountRepository;
import idatt2105.frivilligprosjekt.romreservasjon.repository.ReservationRepository;
import idatt2105.frivilligprosjekt.romreservasjon.repository.RoomRepository;
import idatt2105.frivilligprosjekt.romreservasjon.service.ReservationService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Set;

import static org.hamcrest.Matchers.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.*;

//WebMvcTest needed?
@RunWith(SpringRunner.class)
@WebMvcTest(AccountController.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
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
    private RoomRepository roomRepository;

    @Autowired
    private ReservationService reservationService;

    private Account account1;
    private Account account2;

    private Room room;

    private Reservation reservation1;
    private  Reservation reservation2;

    private Section section1;
    private Section section2;

    private Set<Reservation> reservationSet;

    @Before
    public void setup() {
        account1 = new Account("testName1", "testEmail1", "testPassword1", "1234567", true, null);
        account2 = new Account("testName2", "testEmail2", "testPassword2", "1234567", true, null);
        accountRepository.save(account1);
        accountRepository.save(account2);

        Set<Section> sections = new HashSet<>();
        section1 = new Section("testSection1", "testDescription1", 2.0, 5);
        section2 = new Section("testSection2", "testDescription2", 5.0, 3);
        sections.add(section1);
        sections.add(section2);

        room = new Room("testRoom", "testAddress", "testDescription", 8.0, 8);
        room.setSections(sections);
        roomRepository.save(room);

        reservation1 = new Reservation(null, null, 5, section1, account1);
        reservation2 = new Reservation(null, null, 3, section2, account2);
        reservationRepository.save(reservation1);
        reservationRepository.save(reservation2);

        mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity())
                .build();
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
        this.mockMvc.perform(get("/accounts/email=testEmail1")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testSavingAccount() throws Exception {
        this.mockMvc.perform(MockMvcRequestBuilders.post("/accounts")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("NAME"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value("EMAIL"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.password").value("PASSWORD"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.phone").value("1234567"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.is_admin").value(true))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expiration_date").value(LocalDateTime.of(2022, 5, 4, 12, 15)));
   //https://stackoverflow.com/questions/51346781/how-to-test-post-method-in-spring-boot-using-mockito-and-junit
    }

    public static class SecurityMockMvcRequestPostProcessor {
        public static RequestPostProcessor admin() {
            return user("admin").roles("ADMIN");
        }
    }
}
