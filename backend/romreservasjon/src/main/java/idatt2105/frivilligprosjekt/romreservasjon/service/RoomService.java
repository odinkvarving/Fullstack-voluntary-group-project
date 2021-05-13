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

    public List<Room> findAll(){
        Iterable<Room> roomsIt = roomRepository.findAll();
        List<Room> rooms = new ArrayList<>();

        roomsIt.forEach(rooms::add);

        return rooms;
    }

    public Room findById(int id){
        return roomRepository.findById(id).orElse(null);
    }

    public Room saveRoom(Room room){
        room.getSections().forEach(section -> section.setRoom(room));

        return roomRepository.save(room);
    }

    public List<Reservation> findReservationsByRoomIdAndSectionId(int roomId, int sectionId){
        Room room = roomRepository.findById(roomId).orElse(null);
        if(room != null){
            Section section = room.getSections().stream().filter(s -> s.getId() == sectionId).findFirst().orElse(null);
            if(section == null){
                return null;
            }else {
                return new ArrayList<>(section.getInReservations());
            }
        }else{
            return null;
        }
    }
}
