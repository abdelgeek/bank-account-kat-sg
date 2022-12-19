package bankaccount.model.entities;

import bankaccount.model.valueobject.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * <p>Represents a transaction on a {@link Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public record Transaction(
  LocalDateTime date, BigDecimal amount, BigDecimal accountBalance, TransactionType transactionType) {

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Transaction that = (Transaction) o;
    return Objects.equals(date, that.date) && Objects.equals(amount, that.amount) && Objects.equals(accountBalance, that.accountBalance) && transactionType == that.transactionType;
  }

  @Override
  public int hashCode() {
    return Objects.hash(date, amount, accountBalance, transactionType);
  }
}
