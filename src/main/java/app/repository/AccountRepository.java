package app.repository;

import app.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByNumber(String Number);

    @Query("SELECT a FROM Account a ORDER BY a.id DESC Limit 1")
    Account findMostRecentAccount();
}
