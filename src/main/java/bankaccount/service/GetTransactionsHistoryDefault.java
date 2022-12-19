package bankaccount.service;

import bankaccount.model.entities.Account;
import bankaccount.model.entities.Transaction;
import bankaccount.repository.AccountRepository;
import bankaccount.service.exception.AccountNumberNotFound;
import java.util.List;

/**
 * <p>Implementation of {@link GetTransactionsHistory}. </p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class GetTransactionsHistoryDefault implements GetTransactionsHistory {

  private final AccountRepository accountRepository;

  public GetTransactionsHistoryDefault(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public List<Transaction> get(String accountNumber) {
    Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
      () -> new AccountNumberNotFound());
    return account.transactionsHistory();
  }
}
