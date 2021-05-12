package idatt2105.frivilligprosjekt.romreservasjon.service;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import idatt2105.frivilligprosjekt.romreservasjon.repository.AccountRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AccountService {

    private static final Logger logger = LoggerFactory.getLogger(AccountService.class);

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

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
     * Registers a new account. The account must have a unique email
     * address, i.e. one which is not associated with an already registered account
     *
     * @param account the new Account to be registered
     * @return true if the Account was registered, false if an Account with the given email already exists
     */
    public boolean saveAccount(Account account) {
        Optional<Account> acc = accountRepository.findById(account.getAccount_id());
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
}
