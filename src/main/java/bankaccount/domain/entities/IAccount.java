package bankaccount.domain.entities;


import bankaccount.dto.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * <p> Describing the contracts that an account must respect. </p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface IAccount {


  List<Transaction> transactionsHistory();

  void saveTransaction(LocalDateTime dateOperation, BigDecimal operationAmount,
                       BigDecimal newBalance, TransactionType transactionType);
}
