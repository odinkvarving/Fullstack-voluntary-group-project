package idatt2105.frivilligprosjekt.romreservasjon.controller;


import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.model.Room;
import idatt2105.frivilligprosjekt.romreservasjon.model.SectionChat;
import idatt2105.frivilligprosjekt.romreservasjon.repository.SectionChatRepository;
import idatt2105.frivilligprosjekt.romreservasjon.repository.SectionRepository;
import idatt2105.frivilligprosjekt.romreservasjon.service.RoomService;
import idatt2105.frivilligprosjekt.romreservasjon.service.SectionChatService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoomController {

    private static final Logger logger = LoggerFactory.getLogger(RoomController.class);


    @Autowired
    private RoomService roomService;

    @Autowired
    private SectionChatService sectionChatService;

    @Autowired
    private SectionRepository sectionRepository;

    /**
     * Retrieve all rooms
     *
     * @return rooms
     */
    @GetMapping("/rooms")
    public List<Room> findAll(){
        return roomService.findAll();
    }

    /**
     * Find room by id
     *
     * @param id
     * @return room
     */
    @GetMapping("/rooms/{id}")
    public Room findById(@PathVariable int id){
        return roomService.findById(id);
    }

    /**
     * Save a room
     *
     * @param room
     * @return room that is saved
     */
    @PostMapping("/rooms")
    public Room saveRoom(@RequestBody Room room) {
        return roomService.saveRoom(room);
    }

    /**
     * Endpoint for getting all messages in a specific Section
     *
     * @param room_id the id of the Room
     * @param section_id the id of the Section
     * @return a list of messages in this Section
     */
    @GetMapping("/rooms/{room_id}/sections/{section_id}/messages")
    public List<SectionChat> getSectionMessagesBySectionId(@PathVariable int room_id, @PathVariable int section_id) {
        if(sectionRepository.findSectionByRoomId(room_id) != null) {
            return sectionChatService.getMessagesInSectionSorted(section_id);
        }else {
            logger.info("Could not find the specified section");
        }
        return null;
    }

    /**
     * Endpoint for deleting a message.
     *
     * @param id the id of the message to delete.
     */
    @DeleteMapping("messages/{id}")
    public void deleteMessage(@PathVariable int id) {
        this.sectionChatService.deleteSectionChat(id);
    }

    /**
     * Endpoint for adding a message to a specific section.
     *
     * @param message the message to add to the section.
     */
    @PostMapping("/rooms/{room_id}/sections/{section_id}/messages")
    public void addMessageToSection(@PathVariable int room_id, @PathVariable int section_id, @RequestBody SectionChat message) {
        if(sectionRepository.findSectionByRoomId(room_id) != null) {
            this.sectionChatService.addMessageToSection(message.getAccountId(), section_id, message.getMessage());
        }else {
            logger.info("Could not find the specified section");
        }
    }
}
