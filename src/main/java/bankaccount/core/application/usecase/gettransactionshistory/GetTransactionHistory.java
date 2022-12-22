package bankaccount.core.application.usecase.gettransactionshistory;

import bankaccount.core.application.usecase.RequestFunction;
import bankaccount.core.domain.entity.Account;
import bankaccount.core.domain.entity.Transaction;
import bankaccount.core.application.port.secondaire.AccountPort;
import bankaccount.core.application.exception.AccountNumberNotFound;
import java.util.List;

/**
 * <p>Implementation of {@link RequestFunction}. </p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class GetTransactionHistory implements RequestFunction<List<Transaction>, String> {

  private final AccountPort accountPort;

  public GetTransactionHistory(AccountPort accountPort) {
    this.accountPort = accountPort;
  }

  @Override
  public List<Transaction> apply(String accountNumber) {
    Account account = accountPort.findByAccountNumber(accountNumber).orElseThrow(
      () -> new AccountNumberNotFound());
    return account.transactionsHistory();
  }
}
