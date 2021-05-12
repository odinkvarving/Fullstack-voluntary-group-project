package idatt2105.frivilligprosjekt.romreservasjon.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "account")
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int account_id;

    private String name;
    private String email;
    private String password;
    private String phone;
    private boolean is_admin;
    private LocalDateTime expiration_date;

    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Reservation> reservations = new HashSet<>();

    public Account(String name, String email, String password, String phone, boolean is_admin, LocalDateTime expiration_date) {
        setName(name);
        setEmail(email);
        setPassword(password);
        setPhone(phone);
        setIs_admin(is_admin);
        setExpiration_date(expiration_date);
    }

    public Account() {

    }

    public int getAccount_id() {
        return account_id;
    }

    public void setAccount_id(int id) {
        this.account_id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        if(password == null || password.trim().length() == 0)
            throw new IllegalArgumentException("Empty password");

        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public boolean isIs_admin() {
        return is_admin;
    }

    public void setIs_admin(boolean is_admin) {
        this.is_admin = is_admin;
    }

    public LocalDateTime getExpiration_date() {
        return expiration_date;
    }

    public void setExpiration_date(LocalDateTime expiration_date) {
        this.expiration_date = expiration_date;
    }

    public Set<Reservation> getReservations() {
        return reservations;
    }

    public void setReservations(Set<Reservation> reservations) {
        this.reservations = reservations;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + account_id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", phone='" + phone + '\'' +
                ", is_admin=" + is_admin +
                ", expiration_date=" + expiration_date +
                ", reservations=" + reservations +
                '}';
    }
}
