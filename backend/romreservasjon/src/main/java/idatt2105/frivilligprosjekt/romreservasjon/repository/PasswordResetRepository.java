package idatt2105.frivilligprosjekt.romreservasjon.repository;

import idatt2105.frivilligprosjekt.romreservasjon.model.PasswordReset;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PasswordResetRepository extends CrudRepository<PasswordReset, Integer> {
    Optional<PasswordReset> findBySuffix(String suffix);
}
