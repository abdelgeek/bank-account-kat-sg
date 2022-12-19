package bankaccount.model.entities;


import bankaccount.dto.TransactionCommand;
import java.util.List;

/**
 * <p> Describing the contracts that an account must respect. </p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface IAccount {

  void executeOperation(TransactionCommand transactionCommand);

  List<Transaction> transactionsHistory();

}
