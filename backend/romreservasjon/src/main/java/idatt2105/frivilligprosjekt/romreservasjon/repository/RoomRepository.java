package idatt2105.frivilligprosjekt.romreservasjon.repository;

import idatt2105.frivilligprosjekt.romreservasjon.model.Room;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoomRepository extends CrudRepository<Room, Integer> {
}
