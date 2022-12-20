package bankaccount.service;

import bankaccount.domain.entities.Account;
import bankaccount.service.exception.InsufficientBalanceException;
import java.math.BigDecimal;

/**
 * <p>Implementation to update an {@link Account} balance for a withdrawal transaction.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class Withdrawal implements CalculateNewBalance {

  @Override
  public BigDecimal execute(BigDecimal operationAmount, BigDecimal actualAccountBalance) {
    boolean isAccountBalanceInsufficient =
      actualAccountBalance.compareTo(operationAmount) < 0;
    if (isAccountBalanceInsufficient) {
      throw new InsufficientBalanceException();
    }
    return actualAccountBalance.subtract(operationAmount);
  }
}
