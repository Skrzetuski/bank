package pl.abelo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.abelo.model.Account;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Account findByEmail(String email);
}
