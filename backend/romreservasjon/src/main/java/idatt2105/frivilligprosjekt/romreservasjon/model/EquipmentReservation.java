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

    private LocalDateTime due_date;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference(value = "equipment-reservation")
    private Equipment equipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JsonBackReference
    private Account account;

    public EquipmentReservation() {
    }

    public EquipmentReservation(LocalDateTime due_date, Equipment equipment, Account account) {
        this.due_date = due_date;
        this.equipment = equipment;
        this.account = account;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDateTime getDue_date() {
        return due_date;
    }

    public void setDue_date(LocalDateTime due_date) {
        this.due_date = due_date;
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
                ", due_date=" + due_date +
                ", account=" + account +
                '}';
    }
}
