package idatt2105.frivilligprosjekt.romreservasjon.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
public class SectionChat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int accountId;

    private int sectionId;

    @JsonFormat(pattern = "MM-dd/yy HH:mm")
    private LocalDateTime timeStamp;

    @Lob
    @Column(columnDefinition = "TEXT")
    private String message;

    /**
     * Constructor for a chat-message.
     *
     * @param accountId  the id of the account that created the message.
     * @param sectionId the section the message is bound to.
     * @param message    the message itself.
     */
    public SectionChat(int accountId, int sectionId, String message) {
        this.accountId = accountId;
        this.sectionId = sectionId;
        this.timeStamp = LocalDateTime.now();
        this.message = message;
    }

    public SectionChat() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public int getSectionId() {
        return sectionId;
    }

    public void setSectionId(int sectionId) {
        this.sectionId = sectionId;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
