package idatt2105.frivilligprosjekt.romreservasjon.controller;


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

    @GetMapping("/rooms")
    public List<Room> findAll(){
        return roomService.findAll();
    }

    @GetMapping("/rooms/{id}")
    public Room findById(@PathVariable int id){
        return roomService.findById(id);
    }

    @PostMapping("/rooms")
    public Room saveRoom(@RequestBody Room room) {
        return roomService.saveRoom(room);
    }
}
