package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.SectionChat;
import idatt2105.frivilligprosjekt.romreservasjon.repository.SectionChatRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class SectionChatService {

    private static final Logger logger = LoggerFactory.getLogger(SectionChatService.class);
    @Autowired
    private SectionChatRepository sectionChatRepository;

    /**
     * Adds a message to a specific Section by id
     *
     * @param accountId  the id of the Account that made the message
     * @param sectionId the id of the Section the message belongs to
     * @param message the message to add
     */
    public SectionChat addMessageToSection(int accountId, int sectionId, String message) {
        logger.info("adding message from account: " + accountId + ", to section: " + sectionId + ", with message: \n" + message);
        return this.sectionChatRepository.save(new SectionChat(accountId, sectionId, message));
    }

    /**
     * Method for finding all messages that is in a section.
     *
     * @param sectionId the id of the section to find the messages to.
     * @return a list sorted by date with messages.
     */
    public List<SectionChat> getMessagesInSectionSorted(int sectionId) {
        logger.debug("Returned all messages for section: " + sectionId);
        return this.sectionChatRepository.findSectionChatsBySectionIdOrderByTimeStampDesc(sectionId);
    }

    /**
     * Method for updating a specific message by id
     *
     * @param id the id of the chat message to update
     * @param message the new message to set to the SectionChat
     */
    public SectionChat updateSectionChat(int id, String message) {
        try {
            SectionChat foundMessage = this.sectionChatRepository.findById(id).orElseThrow(NoSuchElementException::new);
            foundMessage.setMessage(message);
            return this.sectionChatRepository.save(foundMessage);
        } catch (NoSuchElementException e) {
            logger.error("ERROR: did not find the chat-message that is to be updated", e);
        }
        return null;
    }

    /**
     * Method for finding a specific message by id
     *
     * @param id the id of the message
     * @return the message that was found
     */
    public SectionChat getSectionChatById(int id) {
        try {
            return this.sectionChatRepository.findById(id).orElseThrow(NoSuchElementException::new);
        }catch (NoSuchElementException e) {
            logger.error("ERROR: did not find any messages with this id", e);
        }
        return null;
    }

    /**
     * Method for deleting a specific message by id
     *
     * @param id the id of the message to be deleted
     */
    public void deleteSectionChat(int id) {
        this.sectionChatRepository.deleteById(id);
    }
}
