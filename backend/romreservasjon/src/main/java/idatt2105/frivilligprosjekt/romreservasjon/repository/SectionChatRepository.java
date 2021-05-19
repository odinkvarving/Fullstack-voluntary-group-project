package idatt2105.frivilligprosjekt.romreservasjon.repository;

import idatt2105.frivilligprosjekt.romreservasjon.model.SectionChat;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface SectionChatRepository extends CrudRepository<SectionChat, Integer> {
    List<SectionChat> findSectionChatBySectionIdOrderByTimeStamp(int sectionId);

    List<SectionChat> findSectionChatBySectionId(int sectionId);

    List<SectionChat> findSectionChatByAccountIdOrderByTimeStamp(int accountId);

    List<SectionChat> findSectionChatByAccountId(int accountId);
}
