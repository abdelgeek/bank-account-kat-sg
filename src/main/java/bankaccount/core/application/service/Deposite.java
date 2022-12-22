package bankaccount.core.application.service;

import bankaccount.core.domain.entity.Account;
import java.math.BigDecimal;

/**
 * <p>Implementation to update an {@link Account} balance for a deposit transaction.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class Deposite implements CalculateNewBalance {

  @Override
  public BigDecimal execute(BigDecimal operationAmount, BigDecimal actualAccountBalance) {
    return actualAccountBalance.add(operationAmount);
  }
}
