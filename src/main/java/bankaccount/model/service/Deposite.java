package bankaccount.model.service;

import bankaccount.model.entities.Account;
import java.math.BigDecimal;

/**
 * <p>Implementation to update an {@link Account} balance for a deposit transaction.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class Deposite implements UpdateBalance {

  @Override
  public BigDecimal execute(BigDecimal operationAmount, BigDecimal actualAccountBalance) {
    return actualAccountBalance.add(operationAmount);
  }
}
