package idatt2105.frivilligprosjekt.romreservasjon.controller;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.model.EquipmentReservation;
import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.service.AccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Set;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
public class AccountController {

    Logger logger = LoggerFactory.getLogger(AccountController.class);

    @Autowired
    private AccountService accountService;

    /**
     * GetMapping for getting all registered Accounts
     *
     * @return a list of all registered Accounts
     */
    @GetMapping("accounts")
    public List<Account> getAllAccounts() {
        return accountService.findAll();
    }

    /**
     * Find account by email
     *
     * @param email
     * @return
     */
    @GetMapping("accounts/email={email}")
    public Account findAccountByEmail(@PathVariable String email){
        return accountService.findByEmail(email);
    }


    /**
     * Find account by ID
     *
     * @param id
     * @return
     */
    @GetMapping("/accounts/{id}")
    public Account findById(@PathVariable int id){
        return accountService.findById(id);
    }

    /**
     * PostMapping for saving a new Account to the database
     *
     * @param account requesting the body of the Account to save
     * @return true or false whether the Account was created successfully or not
     */
    @PostMapping("/accounts")
    public boolean saveAccount(@RequestBody Account account) {
        logger.info("Trying to save account:\n" + account.toString());
        boolean success = accountService.saveAccount(account);
        if (success) {
            logger.info("The account was successfully created");
        }
        return success;
    }

    /**
     * PutMapping for updating a specific Account
     *
     * @param newAccount requesting the body of a new Account (updated version)
     * @param account_id the PathVariable of the id for the Account
     * @return the Account that was updated
     */
    @PutMapping("/accounts/{account_id}")
    public Account updateAccount(@PathVariable("account_id") int account_id, @RequestBody Account newAccount) {
        return accountService.updateAccount(account_id, newAccount);
    }

    /**
     * DeleteMapping for deleting an Account
     *
     * @param account_id the PathVariable for the id of the Account
     */
    @DeleteMapping("/accounts/{account_id}")
    public void deleteAccount(@PathVariable("account_id") int account_id) {
        accountService.deleteAccount(account_id);
    }

    /**
     * GetMapping for finding all Reservations for an Account
     *
     * @param account_id the ID of the Account
     * @return a Set containing the Reservations for the Account
     */
    @GetMapping("/accounts/{account_id}/reservations")
    public Set<Reservation> findAccountReservations(@PathVariable int account_id) {
       logger.info("Finding reservations for Account with ID: " + account_id);
        return accountService.findAccountReservations(account_id);
    }

    /**
     * GetMapping for finding all EquipmentReservations for an Account
     *
     * @param account_id the ID of the Account
     * @return a Set containing the EquipmentReservations for the Account
     */
    @GetMapping("/accounts/{account_id}/equipment-reservations")
    public Set<EquipmentReservation> findAccountEquipmentReservations(@PathVariable int account_id) {
        logger.info("Finding equipment-reservations for Account with ID: " + account_id);
        return accountService.findAccountEquipmentReservations(account_id);
    }

    /**
     * PostMapping for registering a new Reservation for a specific Account
     *
     * @param reservation requesting the Body of a new Reservation
     * @param account_id the ID of the Account
     * @return true or false
     */
    @PostMapping("/accounts/{account_id}/reservations")
    public boolean createAccountReservation(@RequestBody Reservation reservation, @PathVariable int account_id) {
        logger.info("Trying to create reservation: \n" + reservation.toString());
        boolean success = accountService.createAccountReservation(reservation, account_id);
        if(success) {
            logger.info("The reservation was successfully registered");
        }
        return success;
    }

    /**
     * PostMapping for registering a new EquipmentReservation for a specific Account
     *
     * @param reservation requesting the Body of a new EquipmentReservation
     * @param account_id the ID of the Account
     * @return true or false
     */
    @PostMapping("/accounts/{account_id}/equipment-reservations")
    public boolean createAccountEquipmentReservation(@RequestBody EquipmentReservation reservation, @PathVariable int account_id) {
        logger.info("Trying to create equipment-reservation: \n" + reservation.toString());
        boolean success = accountService.createAccountEquipmentReservation(reservation, account_id);
        if(success) {
            logger.info("The equipment-reservation was successfully registered");
        }
        return success;
    }

    /**
     * GetMapping for finding account with given resetsuffix.
     * Functionality is based on code from Systemutvikling 2 project.
     *
     * @param suffix: the reset suffix.
     * @return account with given suffix.
     */
    @GetMapping("/reset/{suffix}")
    public Account getAccountByResetSuffix(@PathVariable("suffix") String suffix) {
        return this.accountService.findAccountBySuffix(suffix);
    }

    /**
     * PostMapping for generating reset link and sending it to input email.
     * Functionality is based on code from Systemutvikling 2 project.
     *
     * @param mail: account email which will receive mail.
     */
    @PostMapping("/reset/{mail}")
    public void requestPasswordReset(@PathVariable("mail") String mail) {
        this.accountService.generatePasswordReset(mail);
    }
}
