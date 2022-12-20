package bankaccount.service;

import bankaccount.domain.entities.Account;
import bankaccount.dto.TransactionCommand;
import bankaccount.dto.TransactionType;
import bankaccount.repository.AccountRepository;
import bankaccount.service.exception.AccountNumberNotFound;
import bankaccount.service.exception.NegativeAccountException;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>Implementation of {@link AccountTransaction}. </p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class AccountTransactionDefault implements AccountTransaction {

  private final AccountRepository accountRepository;

  public AccountTransactionDefault(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public void doTransaction(TransactionCommand transactionCommand) {
    String accountNumber = transactionCommand.accountNumber();
    Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
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
