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
     * Adds a message to the chatfield in a Section.
     *
     * @param accountId  the id of the Account that made the message.
     * @param sectionId the id of the Section the message belongs to.
     * @param message the message.
     */
    public void addMessageToSection(int accountId, int sectionId, String message) {
        logger.info("adding message from account: " + accountId + ", to section: " + sectionId + ", with message: \n" + message);
        this.sectionChatRepository.save(new SectionChat(accountId, sectionId, message));
    }

    /**
     * Method for finding all messages that is in a section.
     *
     * @param sectionId the id of the section to find the messages to.
     * @return a list sorted by date with messages.
     */
    public List<SectionChat> getMessagesInSectionSorted(int sectionId) {
        logger.debug("Returned all messages for section: " + sectionId);
        return this.sectionChatRepository.findSectionChatBySectionIdOrderByTimeStamp(sectionId);
    }

    /**
     * Updates the message with the given id.
     *
     * @param id        the id of the chat message to update
     * @param message   the new state to set to the SectionChat
     */
    public void updateSectionChat(int id, String message) {
        try {
            SectionChat foundMessage = this.sectionChatRepository.findById(id).orElseThrow(NoSuchElementException::new);
            foundMessage.setMessage(message);
            this.sectionChatRepository.save(foundMessage);
        } catch (NoSuchElementException e) {
            logger.error("error did not find the chat-message that is to be updated", e);
        }
    }

    /**
     * Deletes the chat message with the given id.
     *
     * @param id the chat message to delete
     */
    public void deleteSectionChat(int id) {
        this.sectionChatRepository.deleteById(id);
    }

    /**
     * Deletes all chat messages on the section with the given id
     *
     * @param sectionId the id of the section
     */
    public void deleteAllMessagesBySection(int sectionId) {
        List<SectionChat> messages = this.sectionChatRepository.findSectionChatBySectionId(sectionId);
        this.sectionChatRepository.deleteAll(messages);
    }

    /**
     * Deletes all chat messages written by the account with the given id
     *
     * @param accountId the id of the account
     */
    public void deleteAllMessagesByAccount(int accountId) {
        List<SectionChat> messages = this.sectionChatRepository.findSectionChatByAccountId(accountId);
        this.sectionChatRepository.deleteAll(messages);
    }

}
