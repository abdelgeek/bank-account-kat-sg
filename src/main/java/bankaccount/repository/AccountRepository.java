package bankaccount.repository;

import bankaccount.domain.entities.Account;
import java.util.Optional;

/**
 * <p>Abstraction to manage a repository of {@link Account}</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface AccountRepository {
  Optional<Account> findByAccountNumber(String accountNumber);
}
