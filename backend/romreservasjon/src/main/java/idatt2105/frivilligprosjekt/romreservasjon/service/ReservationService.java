package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.repository.ReservationRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    /**
     * Method for finding all Accounts registered in the database
     *
     * @return a collection of all Accounts registered in the database
     */
    public List<Reservation> findAll() {
        Iterable<Reservation> itAccounts = reservationRepository.findAll();
        List<Reservation> reservations = new ArrayList<>();

        itAccounts.forEach(reservations::add);

        return reservations;
    }

    /**
     *Method for registering a new Reservation to the database
     *
     * @param reservation the new Reservation to be registered
     * @return true or false
     */
    public boolean saveReservation(Reservation reservation) {
       return true;
    }
}
