package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private MailService mailService;

    /**
     * Method for finding all Accounts registered in the database
     *
     * @return a collection of all Accounts registered in the database
     */
    public List<Account> findAll() {
        Iterable<Account> itAccounts = accountRepository.findAll();
        List<Account> accounts = new ArrayList<>();

        itAccounts.forEach(accounts::add);

        return accounts;
    }

    /**
     * Method for finding an account by email
     *
     * @param email
     * @return
     */
    public Account findByEmail(String email){
        Optional<Account> account = accountRepository.findByEmail(email);
        return account.orElse(null);
    }

    /**
     * Registers a new account. The account must have a unique email
     * address, i.e. one which is not associated with an already registered account
     *
     * @param account the new Account to be registered
     * @return true if the Account was registered, false if an Account with the given email already exists
     */
    public boolean saveAccount(Account account) {
        Optional<Account> acc = accountRepository.findById(account.getId());
        if (acc.isPresent()) {
            logger.info("Error! Could not create account, ID already exists");
            return false;
        } else {
            account.setPassword(passwordEncoder.encode(account.getPassword()));
            accountRepository.save(account);
            return true;
        }
    }

    /**
     * Method for updating a specific Account
     *
     * @param id the id of the account (not used)
     * @param account the updated version of the Account with the specified ID
     * @return the Account that was updated
     */
    public Account updateAccount(int id, Account account) {
        try {
            return accountRepository.save(account);
        } catch (DataAccessException e) {
            logger.info("Could not update account");
        }
        return null;
    }

    /**
     * Method for deleting an Account with the specific ID from the database
     * If no Account with the given ID exists, nothing happens
     *
     * @param id the id of the account to delete.
     */
    public void deleteAccount(int id) {
        try {
            this.accountRepository.deleteById(id);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            logger.error("null was passed as an argument while trying to delete account");
        }
    }

    /**
     * Sends a password reset link to the provided mail address if it exists in the database.
     *
     * @param mailToReset the mail to reset the password to.
     */
    public void generatePasswordReset(String mailToReset) {
        try {
            Account foundAccount = accountRepository.findByEmail(mailToReset).orElseThrow(NoSuchElementException::new);

            String randomUrlSuffix = "";
            //boolean newSuffix = false;
            //while (!newSuffix) {
                randomUrlSuffix = generateRandomAlphanumericString(75);
                //if (!this.passwordResetRepository.findByResetUrlSuffix(randomUrlSuffix).isPresent()) {
                    //newSuffix = true;
                //}
            //}

            //PasswordReset passwordReset = new PasswordReset(foundAccount.getId(), randomUrlSuffix, LocalDateTime.now());
            //passwordResetRepository.save(passwordReset);
            mailService.sendPasswordResetMail(mailToReset, randomUrlSuffix);
            logger.info("Generated password reset entity/mail for email: " + mailToReset);
        } catch (NoSuchElementException nee) {
            logger.info("A password reset was requested for a user that does not exist in the database: " + mailToReset);
        }
    }

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

    /**
     * Goes trough the repo and deletes the entities that is past expiration time.
     */
    /*private void updatePasswordResetRepo() {
        logger.info("refreshing passwordreset repo");
        final int TIME_LIMIT = 30;
        Set<PasswordReset> resetsToDelete = new HashSet<>();
        LocalDateTime now = LocalDateTime.now();
        this.passwordResetRepository.findAll().forEach(passwordReset -> {
            if (ChronoUnit.MINUTES.between(passwordReset.getTimeProduced(), now) > TIME_LIMIT) {
                resetsToDelete.add(passwordReset);
            }
        });
        this.passwordResetRepository.deleteAll(resetsToDelete);
    }*/
}
