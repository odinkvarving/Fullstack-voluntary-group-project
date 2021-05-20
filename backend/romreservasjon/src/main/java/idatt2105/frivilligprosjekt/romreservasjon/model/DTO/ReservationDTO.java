package idatt2105.frivilligprosjekt.romreservasjon.model.DTO;

import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;

public class ReservationDTO {

    private Reservation reservation;
    private int accountId;

    public ReservationDTO(Reservation reservation, int accountId) {
        this.reservation = reservation;
        this.accountId = accountId;
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
}
