package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Room;
import idatt2105.frivilligprosjekt.romreservasjon.repository.RoomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoomService {

    Logger logger = LoggerFactory.getLogger(RoomService.class);

    @Autowired
    private RoomRepository roomRepository;

    /**
     * Method for finding all rooms from database
     *
     * @return a list of all rooms that were found
     */
    public List<Room> findAll(){
        Iterable<Room> roomsIt = roomRepository.findAll();
        List<Room> rooms = new ArrayList<>();

        roomsIt.forEach(rooms::add);

        return rooms;
    }

    /**
     * Method for finding a specific Room by id
     *
     * @param id the id of the Room to be found
     * @return the Room that was found
     */
    public Room findById(int id){
        try {
            return roomRepository.findById(id).orElse(null);
        }catch (DataAccessException e) {
            logger.error("Could not find room with this id", e);
        }
        return null;
    }

    /**
     * Method for creating a new Room
     *
     * @param room the Room to be saved
     * @return the Room that was saved
     */
    public Room saveRoom(Room room){
        try {
            logger.info("Room was saved successfully");
            room.getSections().forEach(section -> section.setRoom(room));
            return roomRepository.save(room);
        }catch (DataAccessException e) {
            logger.error("Could not save room", e);
        }
        return null;
    }
}
