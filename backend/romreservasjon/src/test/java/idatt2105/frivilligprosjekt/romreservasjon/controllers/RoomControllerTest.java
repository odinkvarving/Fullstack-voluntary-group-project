package idatt2105.frivilligprosjekt.romreservasjon.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import idatt2105.frivilligprosjekt.romreservasjon.model.Room;
import idatt2105.frivilligprosjekt.romreservasjon.model.Section;
import idatt2105.frivilligprosjekt.romreservasjon.repository.RoomRepository;
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

@RunWith(SpringRunner.class)
@ContextConfiguration
@WebAppConfiguration
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class RoomControllerTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RoomRepository roomRepository;

    private Room room1;
    private Room room2;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setup() {
        Set<Section> sections1 = new HashSet<>();
        Section section1 = new Section("testSection1", "testDescription1", 2.0, 5);
        Section section2 = new Section("testSection2", "testDescription2", 5.0, 3);
        sections1.add(section1);
        sections1.add(section2);

        room1 = new Room("testRoom1", "testAddress1", "2", "2", "testDescription1", 8.0, 8);
        room1.setSections(sections1);
        roomRepository.save(room1);

        Set<Section> sections2 = new HashSet<>();
        Section section3 = new Section("testSection3", "testDescription3", 3.0, 6);
        Section section4 = new Section("testSection4", "testDescription4", 6.0, 4);
        sections2.add(section3);
        sections2.add(section4);

        room2 = new Room("testRoom2", "testAddress2", "3", "3", "testDescription2", 9.0, 9);
        room2.setSections(sections2);
        roomRepository.save(room2);

        mockMvc = MockMvcBuilders.webAppContextSetup(context).apply(springSecurity()).build();
    }

    @Test
    public void testFindAllRooms() throws Exception {

        this.mockMvc.perform(get("/rooms")
                .with(SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(greaterThanOrEqualTo(1))))
                .andExpect(jsonPath("$[0].name", is("testRoom1")))
                .andExpect(jsonPath("$[1].name", is("testRoom2")))
                .andExpect(jsonPath("$.length()", is(2)));
    }

    @Test
    public void testFindRoomById() throws Exception {

        this.mockMvc.perform(get("/rooms/1")
                .with(ReservationControllerTest.SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(1)));
    }

    @Test
    public void testSaveRoom() throws Exception {

        Set<Section> sections3 = new HashSet<>();
        Section section5 = new Section("testSection5", "testDescription5", 3.0, 6);
        Section section6 = new Section("testSection6", "testDescription6", 6.0, 4);
        sections3.add(section5);
        sections3.add(section6);

        Room room3 = new Room("testRoom3", "testAddress3", "4", "4", "testDescription3", 10.0, 10);
        room3.setSections(sections3);

        String jsonString = objectMapper.writeValueAsString(room3);

        this.mockMvc.perform(MockMvcRequestBuilders.post("/rooms")
                .with(ReservationControllerTest.SecurityMockMvcRequestPostProcessor.admin())
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", isA(LinkedHashMap.class)))
                .andExpect(jsonPath("$.id", is(3)));
    }

    public static class SecurityMockMvcRequestPostProcessor {
        public static RequestPostProcessor admin() {
            return user("admin").roles("ADMIN");
        }
    }
}
