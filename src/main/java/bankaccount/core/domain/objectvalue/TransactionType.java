package bankaccount.core.domain.objectvalue;

import bankaccount.core.domain.entity.Account;
import bankaccount.core.domain.entity.Transaction;

/**
 * <p>Type of {@link Transaction}  possible on a {@link Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public enum TransactionType {
  DEPOSIT,
  WITHDRAWAL
}
