package bankaccount.service;

import bankaccount.domain.entities.Account;
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
