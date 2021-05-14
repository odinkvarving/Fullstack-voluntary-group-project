package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.model.Room;
import idatt2105.frivilligprosjekt.romreservasjon.model.Section;
import idatt2105.frivilligprosjekt.romreservasjon.repository.ReservationRepository;
import idatt2105.frivilligprosjekt.romreservasjon.repository.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    @Autowired
    private RoomRepository roomRepository;

    /**
     * Retrieve all rooms
     *
     * @return rooms
     */
    public List<Room> findAll(){
        Iterable<Room> roomsIt = roomRepository.findAll();
        List<Room> rooms = new ArrayList<>();

        roomsIt.forEach(rooms::add);

        return rooms;
    }

    /**
     * Find room by id
     *
     * @param id
     * @return room
     */
    public Room findById(int id){
        return roomRepository.findById(id).orElse(null);
    }


    /**
     * Create new room
     *
     * @param room
     * @return room that was created
     */
    public Room saveRoom(Room room){
        room.getSections().forEach(section -> section.setRoom(room));

        return roomRepository.save(room);
    }
}
