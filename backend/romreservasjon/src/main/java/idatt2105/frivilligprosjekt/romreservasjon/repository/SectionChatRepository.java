package idatt2105.frivilligprosjekt.romreservasjon.repository;

import idatt2105.frivilligprosjekt.romreservasjon.model.SectionChat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectionChatRepository extends CrudRepository<SectionChat, Integer> {

    /**
     * Custom method for finding messages in a specific Section by id
     * sorted by TimeStamp in a descending order
     *
     * @param sectionId the id of the Section
     * @return a List of SectionChats (messages) that was found - in order
     */
    List<SectionChat> findSectionChatsBySectionIdOrderByTimeStampDesc(int sectionId);
}
