package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Room;
import idatt2105.frivilligprosjekt.romreservasjon.model.SectionChat;
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

    /**
     * GetMapping for finding all rooms in database
     *
     * @return a List of the rooms that was found
     */
    @GetMapping("/rooms")
    public List<Room> findAll(){
        return roomService.findAll();
    }

    /**
     * GetMapping for finding a specific Room by id
     *
     * @param id the PathVariable for the id of the Room
     * @return the Room that was found
     */
    @GetMapping("/rooms/{id}")
    public Room findById(@PathVariable int id){
        return roomService.findById(id);
    }

    /**
     * PostMapping for saving a new Room
     *
     * @param room requesting the body of a new Room
     * @return the Room that was saved
     */
    @PostMapping("/rooms")
    public Room saveRoom(@RequestBody Room room) {
        logger.info("Trying to save Room:\n" + room.toString());
        return roomService.saveRoom(room);
    }

    /**
     * GetMapping for getting all messages in a specific Section in a Room
     *
     * @param section_id the id of the Section
     * @return a list of messages in this Section
     */
    @GetMapping("/messages/sections/{section_id}")
    public List<SectionChat> getSectionMessagesBySectionId(@PathVariable int section_id) {
            return sectionChatService.getMessagesInSectionSorted(section_id);
    }

    /**
     * DeleteMapping for deleting a message specific message by id
     *
     * @param id the id of the message to delete
     */
    @DeleteMapping("messages/{id}")
    public void deleteMessage(@PathVariable int id) {
        this.sectionChatService.deleteSectionChat(id);
    }

    /**
     * PostMapping for adding a new message to a specific Section by id
     *
     * @param section_id PathVariable for the id of the Section
     * @param message requesting the body of a message
     * @return the message that was added
     */
    @PostMapping("/messages/sections/{section_id}")
    public SectionChat addMessageToSection(@PathVariable int section_id, @RequestBody SectionChat message) {
            return this.sectionChatService.addMessageToSection(message.getAccountId(), section_id, message.getMessage());
    }

    /**
     * PutMapping for updating a specific message by id
     *
     * @param message_id the PathVariable for the id of the message
     * @param message requesting the body of a new message (updated version)
     * @return the message that was updated
     */
    @PutMapping("/messages/{message_id}")
    public SectionChat updateMessage(@PathVariable int message_id, @RequestBody SectionChat message) {
        return this.sectionChatService.updateSectionChat(message_id, message.getMessage());
    }

    /**
     * GetMapping for finding a specific message by id
     *
     * @param message_id the PathVariable for the id of the message
     * @return the message that was found
     */
    @GetMapping("/messages/{message_id}")
    public SectionChat getMessageById(@PathVariable int message_id) {
        return this.sectionChatService.getSectionChatById(message_id);
    }
}
