package bankaccount.core.application.service;

import java.math.BigDecimal;

/**
 * <p>Abstraction to update an account balance for an operation./p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface CalculateNewBalance {

  BigDecimal execute(BigDecimal operationAmount, BigDecimal balance);
}
