package idatt2105.frivilligprosjekt.romreservasjon.model.DTO;

import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;

public class ReservationDTO {

    private Reservation reservation;
    private int accountId;
    private int sectionId;

    public ReservationDTO(Reservation reservation, int accountId, int sectionId) {
        this.reservation = reservation;
        this.accountId = accountId;
        this.sectionId = sectionId;
    }

    public ReservationDTO() {
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
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
}
