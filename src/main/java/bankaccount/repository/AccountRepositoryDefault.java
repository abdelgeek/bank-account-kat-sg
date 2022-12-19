package bankaccount.repository;

import bankaccount.model.entities.Account;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

/**
 * <p>An account repository.
 *    Implementation of {@link AccountRepository} .</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class AccountRepositoryDefault implements AccountRepository {

  Set<Account> accountList;

  public AccountRepositoryDefault() {
    accountList = new LinkedHashSet<>();
  }

  public AccountRepositoryDefault(Set<Account> accountList) {
    this.accountList = accountList;
  }

  public Optional<Account> findByAccountNumber(String accountNumber) {
    return accountList.stream().filter(account -> account.getAccountNumber().equals(accountNumber)).findFirst();
  }
}
