package bankaccount.core.domain.entity;

import bankaccount.core.domain.objectvalue.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * <p>Represents a transaction on a {@link Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class Transaction {

  private UUID id;
  private LocalDateTime date;
  private BigDecimal amount;
  private BigDecimal accountBalance;
  private TransactionType transactionType;

  public Transaction() {
    this.id = UUID.randomUUID();
  }

  public Transaction(LocalDateTime date,
                     BigDecimal amount,
                     BigDecimal accountBalance,
                     TransactionType transactionType) {
    this.id = UUID.randomUUID();
    this.date = date;
    this.amount = amount;
    this.accountBalance = accountBalance;
    this.transactionType = transactionType;
  }

  public UUID getId() {
    return id;
  }

  public void setId(UUID id) {
    this.id = id;
  }

  public LocalDateTime getDate() {
    return date;
  }

  public void setDate(LocalDateTime date) {
    this.date = date;
  }

  public BigDecimal getAmount() {
    return amount;
  }

  public void setAmount(BigDecimal amount) {
    this.amount = amount;
  }

  public BigDecimal getAccountBalance() {
    return accountBalance;
  }

  public void setAccountBalance(BigDecimal accountBalance) {
    this.accountBalance = accountBalance;
  }

  public TransactionType getTransactionType() {
    return transactionType;
  }

  public void setTransactionType(TransactionType transactionType) {
    this.transactionType = transactionType;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return Objects.equals(date, that.date)
      && Objects.equals(amount, that.amount)
      && Objects.equals(accountBalance, that.accountBalance)
      && transactionType == that.transactionType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, amount, accountBalance, transactionType);
  }
}
