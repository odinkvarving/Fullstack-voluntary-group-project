package idatt2105.frivilligprosjekt.romreservasjon.controller;


import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.model.Room;
import idatt2105.frivilligprosjekt.romreservasjon.service.RoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RoomController {

    @Autowired
    private RoomService roomService;

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
}
