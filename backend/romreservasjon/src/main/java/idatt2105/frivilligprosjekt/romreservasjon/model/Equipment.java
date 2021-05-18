package idatt2105.frivilligprosjekt.romreservasjon.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "equipment")
public class Equipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    @OneToMany(mappedBy = "equipment", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "equipment-reservation")
    private Set<EquipmentReservation> inReservations = new HashSet<>();

    public Equipment() {
    }

    public Equipment(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<EquipmentReservation> getInReservations() {
        return inReservations;
    }

    public void setInReservations(Set<EquipmentReservation> inReservations) {
        this.inReservations = inReservations;
    }
}
