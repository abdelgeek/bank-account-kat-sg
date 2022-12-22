package bankaccount.core.domain.entity;

import bankaccount.core.domain.objectvalue.TransactionType;
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
  private BigDecimal balance;
  private List<Transaction> transactions;

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


  public void setTransactions(List<Transaction> transactions) {
    this.transactions = transactions;
  }

  @Override
  public List<Transaction> transactionsHistory() {
    return transactions;
  }

  @Override
  public void saveTransaction(LocalDateTime dateOperation, BigDecimal operationAmount,
                              BigDecimal newBalance, TransactionType transactionType) {
    Transaction transaction = new Transaction(dateOperation, operationAmount, newBalance, transactionType);
    transactions.add(transaction);
    this.balance = newBalance;
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
