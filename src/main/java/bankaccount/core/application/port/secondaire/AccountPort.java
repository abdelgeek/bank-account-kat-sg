package bankaccount.core.application.port.secondaire;

import bankaccount.core.domain.entity.Account;
import java.util.Optional;

/**
 * <p>Abstraction to manage a repository of {@link Account}</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface AccountPort {
  Optional<Account> findByAccountNumber(String accountNumber);
}
