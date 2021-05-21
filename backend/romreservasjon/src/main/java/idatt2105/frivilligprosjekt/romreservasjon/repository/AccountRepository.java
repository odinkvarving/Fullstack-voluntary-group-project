package idatt2105.frivilligprosjekt.romreservasjon.repository;

import idatt2105.frivilligprosjekt.romreservasjon.model.Account;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends CrudRepository<Account, Integer> {

    /**
     * Custom method for finding an Account by email
     *
     * @param email the email to find Account by
     * @return the Account that was found
     */
    Optional<Account> findByEmail(String email);
}
