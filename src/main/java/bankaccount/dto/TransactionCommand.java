package bankaccount.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>Describing  data necessary to  perform an {@link bankaccount.domain.entities.Transaction}
 * on a {@link bankaccount.domain.entities.Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public record TransactionCommand(String accountNumber, LocalDateTime date, BigDecimal amount,
                                 TransactionType transactionType) {

}



