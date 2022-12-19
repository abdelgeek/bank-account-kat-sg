package bankaccount.model.entities;

import bankaccount.dto.TransactionCommand;
import bankaccount.model.exception.NegativeAccountException;
import bankaccount.model.service.Deposite;
import bankaccount.model.service.UpdateBalance;
import bankaccount.model.service.Withdrawal;
import bankaccount.model.valueobject.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>This class represent a account.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class Account implements IAccount {

  private final String accountNumber;
  private List<Transaction> transactions;
  private BigDecimal balance;

  public Account(String accountNumber, BigDecimal initialBalance) {
    this.accountNumber = accountNumber;
    this.balance = initialBalance;
    transactions = new ArrayList<>();
  }

  public String getAccountNumber() {
    return accountNumber;
  }

  public BigDecimal getBalance() {
    return balance;
  }

  @Override
  public void executeOperation(TransactionCommand transactionCommand) {
    BigDecimal operationAmount = transactionCommand.amount();
    checkAmountIsNegative(operationAmount);
    TransactionType transactionType = transactionCommand.transactionType();
    UpdateBalance operation = switch (transactionType) {
      case DEPOSIT -> new Deposite();
      case WITHDRAWAL -> new Withdrawal();
    };

    balance = operation.execute(operationAmount, balance);
    saveOperation(balance, transactionCommand);
  }

  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  @Override
  public List<Transaction> transactionsHistory() {
    return transactions;
  }

  private void checkAmountIsNegative(BigDecimal operationAmount) {
    boolean isAccountNegative =
      operationAmount.compareTo(BigDecimal.ZERO) < 0;
    if (isAccountNegative) {
      throw new NegativeAccountException();
    }
  }

  private void saveOperation(BigDecimal balance, TransactionCommand transactionCommand) {
    TransactionType transactionType = transactionCommand.transactionType();
    BigDecimal amount = transactionCommand.amount();
    LocalDateTime dateOperation = transactionCommand.date();
    Transaction transaction = new Transaction(dateOperation, amount, balance, transactionType);
    this.transactions.add(transaction);
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Account account = (Account) o;
    return accountNumber.equals(account.accountNumber);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accountNumber);
  }
}
