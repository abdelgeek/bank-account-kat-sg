package bankaccount.service;

import bankaccount.dto.TransactionCommand;

/**
 * <p>Service to make an account Transaction.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface AccountTransaction {
  void doTransaction(TransactionCommand transactionCommand);
}
