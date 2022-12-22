package bankaccount.core.application.usecase.executetransaction;

import bankaccount.core.application.exception.AccountNumberNotFound;
import bankaccount.core.application.exception.NegativeAccountException;
import bankaccount.core.application.port.secondaire.AccountPort;
import bankaccount.core.application.service.CalculateNewBalance;
import bankaccount.core.application.service.Deposite;
import bankaccount.core.application.service.Withdrawal;
import bankaccount.core.application.usecase.RequestConsumer;
import bankaccount.core.domain.entity.Account;
import bankaccount.core.domain.objectvalue.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>Implementation of {@link RequestConsumer}. </p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class ExecuteTransactionUseCase implements RequestConsumer<TransactionCommand> {
  private final AccountPort accountPort;

  public ExecuteTransactionUseCase(AccountPort accountPort) {
    this.accountPort = accountPort;
  }

  @Override
  public void execute(TransactionCommand transactionCommand) {
    String accountNumber = transactionCommand.accountNumber();
    Account account = accountPort.findByAccountNumber(accountNumber).orElseThrow(
      () -> new AccountNumberNotFound());

    BigDecimal actualBalance = account.getBalance();
    BigDecimal operationAmount = transactionCommand.amount();
    checkAmountIsNegative(operationAmount);

    TransactionType transactionType = transactionCommand.transactionType();
    CalculateNewBalance calculateNewBalance = switch (transactionType) {
      case DEPOSIT -> new Deposite();
      case WITHDRAWAL -> new Withdrawal();
    };
    BigDecimal newBalance = calculateNewBalance.execute(operationAmount, actualBalance);
    LocalDateTime dateOperation = transactionCommand.date();
    account.saveTransaction(dateOperation, operationAmount, newBalance, transactionType);
  }

  private void checkAmountIsNegative(BigDecimal operationAmount) {
    boolean isAccountNegative =
      operationAmount.compareTo(BigDecimal.ZERO) < 0;
    if (isAccountNegative) {
      throw new NegativeAccountException();
    }
  }

}
