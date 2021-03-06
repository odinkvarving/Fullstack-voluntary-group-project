package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.model.DTO.ReservationDTO;
import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.model.Section;
import idatt2105.frivilligprosjekt.romreservasjon.repository.ReservationRepository;
import idatt2105.frivilligprosjekt.romreservasjon.repository.SectionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class ReservationService {

    private static final Logger logger = LoggerFactory.getLogger(ReservationService.class);

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private SectionRepository sectionRepository;

    @Autowired
    private AccountService accountService;

    @Autowired
    private MailService mailService;

    /**
     * Method for finding all Reservations registered in the database
     *
     * @return a collection of all Reservations registered in the database
     */
    public List<ReservationDTO> findAll() {
        Iterable<Reservation> itReservations = reservationRepository.findAll();
        List<ReservationDTO> reservations = new ArrayList<>();

        itReservations.forEach(reservation -> {
            reservations.add(new ReservationDTO(reservation, reservation.getAccount().getId(), reservation.getSection().getId()));
        });
        return reservations;
    }

    /**
     *Method for registering a new Reservation to the database
     *
     * @param reservation the new Reservation to be registered
     * @return true or false
     */
    public boolean saveReservation(Reservation reservation, String jwt) {
        if(accountService.findById(reservation.getAccount().getId(), jwt) == null){
            logger.info("Can't create reservation for another user unless you are admin...");
            return false;
        }else{
            if(!validateReservation(reservation)){
                return false;
            }else {
                try {
                    reservation.setSection(reservation.getSection());
                    reservation.setAccount(reservation.getAccount());
                    reservationRepository.save(reservation);
                    return true;
                } catch (DataAccessException e) {
                    e.printStackTrace();
                    logger.info("Could not fetch either the Account or Section for this reservation.");
                    return false;
                }
            }
        }
    }

    /**
     * Helper method that checks whether a reservation is overlapping with existing ones
     *
     * @param reservation the Reservation to be validated
     * @return true or false
     */
    private boolean validateReservation(Reservation reservation){
        Section section = sectionRepository.findById(reservation.getSection().getId()).orElse(null);
        if(section == null){
            logger.info("Section not found in database...");
            return false;
        }else {
            List<Reservation> relevantDateReservations = section.getInReservations().stream().filter(res -> {
                return res.getFrom_date().getYear() == reservation.getFrom_date().getYear()
                        && res.getFrom_date().getMonth() == reservation.getFrom_date().getMonth()
                        && res.getFrom_date().getDayOfMonth() == reservation.getFrom_date().getDayOfMonth();
            }).collect(Collectors.toList());
            for (Reservation res : relevantDateReservations) {
                if ((res.getFrom_date().isBefore(reservation.getTo_date()) && reservation.getFrom_date().isBefore(res.getFrom_date())) || (res.getTo_date().isAfter(reservation.getFrom_date())
                        && res.getFrom_date().isBefore(reservation.getFrom_date())) || (reservation.getFrom_date().isBefore(res.getFrom_date()) && reservation.getTo_date().isAfter(res.getTo_date()))
                        ||res.getTo_date().isEqual(reservation.getTo_date()) || res.getFrom_date().isEqual(reservation.getFrom_date())) {
                    logger.info("Reservation is overlapping with another reservation...");
                    return false;
                }
            }

            return true;
        }
    }

    /**
     * Save a new reservation in all sections when user books the whole room
     *
     * @param reservations the list of Reservations to be saved
     * @return true or false
     */
    public boolean saveRoomReservation(List<Reservation> reservations, String jwt){
        if(accountService.findById(reservations.get(0).getAccount().getId(), jwt) == null){
            logger.info("Can't create reservation for another user unless you are admin...");
            return false;
        }else{
            for(Reservation reservation : reservations){
                if(!validateReservation(reservation)){
                    return false;
                }
            }

            try {
                for(Reservation reservation : reservations){
                    reservation.setSection(reservation.getSection());
                    reservation.setAccount(reservation.getAccount());
                    reservationRepository.save(reservation);
                }
                return true;
            } catch (DataAccessException e) {
                e.printStackTrace();
                logger.info("Could not fetch either the Account or Section for this reservation.");
                return false;
            }
        }
    }

    /**
     * Method for updating a specific Reservation
     *
     * @param id the id of the Reservation
     * @param reservation the updated version of the Reservation with the specified ID
     * @return the Reservation that was updated
     */
    public Reservation updateReservation(int id, Reservation reservation, String jwt) {
        if(!accountService.isAdmin(jwt)){
            logger.info("Only admin can update reservations");
            return null;
        }else {
            try {
                Section section = reservationRepository.findById(id).get().getSection();
                reservation.setId(id);
                reservation.setAccount(reservation.getAccount());
                reservation.setSection(section);
                logger.info("Reservation updated");
                notifyUpdateAccount(reservation.getAccount().getId(), jwt);
                return reservationRepository.save(reservation);
            } catch (DataAccessException e) {
                logger.info("Could not update reservation");
                return null;
            }
        }
    }

    public void notifyUpdateAccount(int accountId, String jwt){
        Account account = accountService.findById(accountId, jwt);
        mailService.sendUpdatedReservationMail(account.getEmail(), "Room reservation updated!", jwt);
    }

    /**
     * Method for finding a specific Reservation by id
     *
     * @param id the id of the Reservation
     * @return the Reservation that was found
     */
    public ReservationDTO findReservationById(int id) {
        try {
            Reservation reservation = reservationRepository.findById(id).orElseThrow(NoSuchElementException::new);
            return new ReservationDTO(reservation, reservation.getAccount().getId(), reservation.getSection().getId());
        }catch (DataAccessException e) {
            e.printStackTrace();
            logger.info("Could not find a reservation with this ID");
        }
        return null;
    }

    /**
     * Method for deleting a specific Reservation by id
     *
     * @param id the id of the Reservation to be deleted
     */
    public boolean deleteReservation(int id, String jwt) {
        if(!accountService.isAdmin(jwt)){
            logger.info("Only admin can delete reservations");
            return false;
        }else {
            try {
                Reservation reservation = reservationRepository.findById(id).orElse(null);
                if(reservation != null){
                    notifyDeleteReservation(reservation.getAccount().getId(), jwt);
                }
                reservationRepository.deleteById(id);
                return true;
            } catch (DataAccessException e) {
                e.printStackTrace();
                logger.info("Could not delete this reservation. ID does not exist");
                return false;
            }
        }
    }

    /**
     * Method for sending an email to an Account if Reservation is deleted
     *
     * @param accountId the id of the Account to send email to
     * @param jwt the session token
     */
    public void notifyDeleteReservation(int accountId, String jwt){
        Account account = accountService.findById(accountId, jwt);
        mailService.sendDeletedReservationMail(account.getEmail(), "Room reservation updated!", jwt);
    }

    /**
     * Method for finding all Reservations for a specific Section by id
     *
     * @param id the id of the Section
     * @return a List of ReservationDTO's that was found
     */
    public List<ReservationDTO> findReservationsBySectionId(int id) {
        try {
            List<Reservation> res = reservationRepository.findReservationsBySectionId(id);
            List<ReservationDTO> dto = new ArrayList<>();
            res.forEach(reservation -> dto.add(new ReservationDTO(reservation, reservation.getAccount().getId(), reservation.getSection().getId())));
            return dto;
        }catch (DataAccessException e) {
            logger.error("Could not find any reservations for this section", e);
        }
        return null;
    }
}
