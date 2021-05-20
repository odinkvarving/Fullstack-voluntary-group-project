package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.model.EquipmentReservation;
import idatt2105.frivilligprosjekt.romreservasjon.model.PasswordReset;
import idatt2105.frivilligprosjekt.romreservasjon.model.Reservation;
import idatt2105.frivilligprosjekt.romreservasjon.repository.AccountRepository;
import idatt2105.frivilligprosjekt.romreservasjon.repository.PasswordResetRepository;
import idatt2105.frivilligprosjekt.romreservasjon.util.JwtUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    @Autowired
    private PasswordResetRepository passwordResetRepository;

    /**
     * Method for finding all Accounts registered in the database
     *
     * @return a collection of all Accounts registered in the database
     */
    public List<Account> findAll(String jwt) {
        if(isAdmin(jwt)){
            Iterable<Account> itAccounts = accountRepository.findAll();
            List<Account> accounts = new ArrayList<>();

            itAccounts.forEach(accounts::add);

            return accounts;
        }else{
            logger.info("Only admins can use this endpoint...");
            return null;
        }
    }

    /**
     * Method for finding an Account with a specific email
     *
     * @param email the email of the Account
     * @return the Account that was found with this email
     */
    public Account findByEmail(String email, String jwt){
        if(isSameUser(jwt, email) || isAdmin(jwt)){
            Optional<Account> account = accountRepository.findByEmail(email);
            return account.orElse(null);
        }else {
            return null;
        }
    }

    /**
     * Method for finding a specific Account
     *
     * @param id the ID of the Account to be found
     * @return the Account that was found
     */
    public Account findById(int id, String jwt){
        Optional<Account> account = accountRepository.findById(id);
        if(account.isPresent()){
            if(isSameUser(jwt, account.get().getEmail()) || isAdmin(jwt) ){
                return account.get();
            }else{
                logger.info("Can't access others userdata unless you are an admin...");
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * Registers a new account. The account must have a unique email
     * address, i.e. one which is not associated with an already registered account
     *
     * @param account the new Account to be registered
     * @return true if the Account was registered, false if an Account with the given id already exists
     */
    public boolean saveAccount(Account account, String jwt) {
        if(isAdmin(jwt)){
            Optional<Account> acc = accountRepository.findById(account.getId());
            if (acc.isPresent()) {
                logger.info("Error! Could not create account, ID already exists");
                return false;
            } else {
                account.setPassword(passwordEncoder.encode(account.getPassword()));
                accountRepository.save(account);
                return true;
            }
        }else{
            return false;
        }
    }

    /**
     * Method for updating a specific Account
     *
     * @param id the id of the account (not used)
     * @param account the updated version of the Account with the specified ID
     * @return the Account that was updated
     */
    public Account updateAccount(int id, Account account, String jwt) {
        if(isAdmin(jwt)){
            try {
                logger.info("Account updated!");
                account.setId(id);
                return accountRepository.save(account);
            } catch (DataAccessException e) {
                logger.info("Could not update account");
                return null;
            }
        }else{
            return null;
        }
    }

    /**
     * Method for deleting an Account with the specific ID from the database
     * If no Account with the given ID exists, nothing happens
     *
     * @param id the id of the account to delete.
     */
    public void deleteAccount(int id, String jwt) {
        if(isAdmin(jwt)){
            try {
                this.accountRepository.deleteById(id);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                logger.error("null was passed as an argument while trying to delete account");
            }
        }
    }

    /**
     * Method for finding all Reservations for a specific Account
     *
     * @param id the ID of the Account
     * @return a Set containing the Reservations for the Account
     */
    public Set<Reservation> findAccountReservations(int id) {
        try {
            logger.info("Successfully found all reservations");
            return accountRepository.findById(id).orElseThrow(NoSuchElementException::new).getReservations();
        }catch (DataAccessException e) {
            e.printStackTrace();
            logger.info("Could not find any reservations for this account");
        }
        return null;
    }

    /**
     * Method for finding all EquipmentReservations for a specific Account
     *
     * @param id the ID of the Account
     * @return a Set containing the EquipmentReservations for the Account
     */
    public Set<EquipmentReservation> findAccountEquipmentReservations(int id) {
        try {
            logger.info("Successfully found all equipment-reservations");
            return accountRepository.findById(id).orElseThrow(NoSuchElementException::new).getEquipmentReservations();
        }catch (DataAccessException e) {
            e.printStackTrace();
            logger.info("Could not find any equipment-reservations for this account");
        }
        return null;
    }


    /**
     * Method for finding account by reset suffix in URL.
     * Functionality is based on code from Systemutvikling 2 project.
     *
     * @param suffix: reset suffix.
     * @return account with given reset suffix.
     */
    public Account findAccountBySuffix(String suffix) {
        try {
            PasswordReset reset = this.passwordResetRepository.findBySuffix(suffix).orElseThrow(Exception::new);
            logger.info("accountId: " + reset.getAccountId());
            logger.info("suffix: " + reset.getSuffix());
            return accountRepository.findById(reset.getAccountId()).orElse(null);
        } catch(Exception error) {
            logger.error(error.toString());
            return null;
        }
    }

    /**
     * Sends a password reset link to the provided mail address if it exists in the database.
     * Functionality is based on code from Systemutvikling 2 project.
     *
     * @param mailToReset the mail to reset the password to.
     */
    public void generatePasswordReset(String mailToReset) {
        try {
            Account account = accountRepository.findByEmail(mailToReset).orElseThrow(NoSuchElementException::new);
            logger.info("Found account with email: " + account.getEmail());

            String suffix = "";
            boolean newSuffix = false;
            while (!newSuffix) {
                suffix = generateRandomAlphanumericString(75);
                if (!this.passwordResetRepository.findBySuffix(suffix).isPresent()) {
                    newSuffix = true;
                }
            }

            PasswordReset passwordReset = new PasswordReset(account.getId(), suffix);
            logger.info("AccountId: " + passwordReset.getAccountId() + " - Suffix: " + passwordReset.getSuffix());
            passwordResetRepository.save(passwordReset);
            mailService.sendMail(mailToReset, "Glemt passord", suffix);
            logger.info("Generated password reset entity/mail for email: " + mailToReset);
        } catch (NoSuchElementException nee) {
            logger.info("A password reset was requested for a user that does not exist in the database: " + mailToReset);
        }
    }

    /**
     * Method for creating a random reset suffix.
     * Functionality is based on code from Systemutvikling 2 project.
     *
     * @param length
     * @return
     */
    private String generateRandomAlphanumericString(int length) {
        int leftLimit = 48; // numeral '0'
        int rightLimit = 122; // letter 'z'
        Random random = new Random();

        return random.ints(leftLimit, rightLimit + 1)
            .filter(i -> (i <= 57 || i >= 65) && (i <= 90 || i >= 97))
            .limit(length)
            .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
            .toString();
    }

    public boolean isAdmin(String jwt){
        String email = jwtUtil.extractUsername(jwt.split(" ")[1]);
        Account account = accountRepository.findByEmail(email).orElse(null);
        if(account.isIs_admin()){
            return true;
        }else{
            return false;
        }
    }

    public boolean isSameUser(String jwt, String username){
        String email = jwtUtil.extractUsername(jwt.split(" ")[1]);
        return username.equalsIgnoreCase(email);
    }
}
