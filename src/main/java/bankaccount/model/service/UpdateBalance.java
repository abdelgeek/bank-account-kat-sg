package bankaccount.model.service;

import java.math.BigDecimal;

/**
 * <p>Abstraction to update an account balance for an operation./p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface UpdateBalance {

  BigDecimal execute(BigDecimal operationAmount, BigDecimal balance);
}
