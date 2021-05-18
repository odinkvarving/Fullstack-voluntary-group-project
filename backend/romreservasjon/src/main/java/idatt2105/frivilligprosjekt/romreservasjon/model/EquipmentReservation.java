package idatt2105.frivilligprosjekt.romreservasjon.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "equipment_reservation")
public class EquipmentReservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private LocalDateTime from_date;

    private LocalDateTime to_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "equipment-reservation")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Account account;

    public EquipmentReservation() {
    }

    public EquipmentReservation(LocalDateTime from_date, LocalDateTime to_date, Equipment equipment, Account account) {
        this.from_date = from_date;
        this.to_date = to_date;
        this.equipment = equipment;
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

    public Equipment getEquipment() {
        return equipment;
    }

    public void setEquipment(Equipment equipment) {
        this.equipment = equipment;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "EquipmentReservation{" +
                "id=" + id +
                ", from_date=" + from_date +
                ", to_date=" + to_date +
                ", account=" + account +
                '}';
    }
}
