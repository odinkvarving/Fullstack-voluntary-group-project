package idatt2105.frivilligprosjekt.romreservasjon.model;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

    private LocalDateTime from_date;
    private LocalDateTime to_date;

    @ManyToOne(fetch = FetchType.LAZY)
    private Section section;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account account;

    public Reservation() {
    }

    public Reservation(LocalDateTime from_date, LocalDateTime to_date, Section section, Account account) {
        this.from_date = from_date;
        this.to_date = to_date;
        this.section = section;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getFrom_date() {
        return from_date;
    }

    public void setFrom_date(LocalDateTime from_date) {
        this.from_date = from_date;
    }

    public LocalDateTime getTo_date() {
        return to_date;
    }

    public void setTo_date(LocalDateTime to_date) {
        this.to_date = to_date;
    }

    public Section getSection() {
        return section;
    }

    public void setSection(Section section) {
        this.section = section;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "Reservation{" +
                "id=" + id +
                ", from_date=" + from_date +
                ", to_date=" + to_date +
                ", section=" + section.getName() +
                ", account=" + account.getName() +
                '}';
    }
}
