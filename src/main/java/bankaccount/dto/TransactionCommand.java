package bankaccount.dto;

import bankaccount.model.valueobject.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>Describing  data necessary to  perform an {@link bankaccount.model.entities.Transaction}
 * on a {@link bankaccount.model.entities.Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public record TransactionCommand(String accountNumber, LocalDateTime date, BigDecimal amount,
                                 TransactionType transactionType) {
}



