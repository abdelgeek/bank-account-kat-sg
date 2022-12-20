package bankaccount.service;

import bankaccount.domain.entities.Transaction;
import java.util.List;

/**
 * <p>Service to get history of {@link Transaction} on a {@link bankaccount.domain.entities.Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface GetTransactionsHistory {

  List<Transaction> get(String accountNumber);
}
