package bankaccount.service;

import bankaccount.model.entities.Transaction;
import java.util.List;

/**
 * <p>Service to get history of {@link Transaction} on a {@link bankaccount.model.entities.Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface GetTransactionsHistory {

  List<Transaction> get(String accountNumber);
}
