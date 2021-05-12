package idatt2105.frivilligprosjekt.romreservasjon.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "room")
public class Room {

    @Id
    @GeneratedValue
    private int room_id;

    private String name;
    private double size;
    private int max_persons;

    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Section> sections = new HashSet<>();

    public Room() {
    }

    public Room(String name, double size, int max_persons) {
        this.name = name;
        this.size = size;
        this.max_persons = max_persons;
    }

    public int getRoom_id() {
        return room_id;
    }

    public void setRoom_id(int room_id) {
        this.room_id = room_id;
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

    public Set<Section> getSections() {
        return sections;
    }

    public void setSections(Set<Section> sections) {
        this.sections = sections;
    }
}