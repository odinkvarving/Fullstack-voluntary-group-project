package idatt2105.frivilligprosjekt.romreservasjon.repository;

import idatt2105.frivilligprosjekt.romreservasjon.model.Section;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SectionRepository extends CrudRepository<Section, Integer> {

    Section findSectionByRoomId(int roomId);
}
