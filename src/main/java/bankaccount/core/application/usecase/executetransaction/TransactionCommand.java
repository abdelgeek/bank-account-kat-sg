package bankaccount.core.application.usecase.executetransaction;

import bankaccount.core.domain.entity.Account;
import bankaccount.core.domain.entity.Transaction;
import bankaccount.core.domain.objectvalue.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>Describing  data necessary to  perform an {@link Transaction}
 * on a {@link Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public record TransactionCommand(String accountNumber, LocalDateTime date, BigDecimal amount,
                                 TransactionType transactionType) {

}



