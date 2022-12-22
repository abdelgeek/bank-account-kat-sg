package bankaccount.infrastructure.repository;

import bankaccount.core.application.port.secondaire.AccountPort;
import bankaccount.core.domain.entity.Account;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <p>An account repository.
 *    Implementation of {@link AccountPort} .</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class AccountRepository implements AccountPort {

  Set<Account> accountList;

  public AccountRepository() {
    accountList = new LinkedHashSet<>();
  }

  public AccountRepository(Set<Account> accountList) {
    this.accountList = accountList;
  }

  public Optional<Account> findByAccountNumber(String accountNumber) {
    return accountList.stream().filter(account -> account.getAccountNumber().equals(accountNumber)).findFirst();
  }
}
