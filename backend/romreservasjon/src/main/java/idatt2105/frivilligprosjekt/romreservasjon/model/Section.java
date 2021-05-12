package idatt2105.frivilligprosjekt.romreservasjon.model;


import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "section")
public class Section {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int section_id;

    private String name;
    private double size;
    private int max_persons;

    @OneToMany(mappedBy = "section", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> inReservations = new HashSet<>();

    @ManyToOne(fetch = FetchType.LAZY)
    private Room room;

    public Section() {
    }

    public Section(String name, double size, int max_persons) {
        this.name = name;
        this.size = size;
        this.max_persons = max_persons;
    }

    public int getSection_id() {
        return section_id;
    }

    public void setSection_id(int section_id) {
        this.section_id = section_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public int getMax_persons() {
        return max_persons;
    }

    public void setMax_persons(int max_persons) {
        this.max_persons = max_persons;
    }

    public Set<Reservation> getInReservations() {
        return inReservations;
    }

    public void setInReservations(Set<Reservation> inReservations) {
        this.inReservations = inReservations;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}