package bankaccount.dto;

import bankaccount.domain.entities.Account;

/**
 * <p>Type of {@link bankaccount.domain.entities.Transaction}  possible on a {@link Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public enum TransactionType {
  DEPOSIT,
  WITHDRAWAL
}
