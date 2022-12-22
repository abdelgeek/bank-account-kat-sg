package bankaccount.core.application.usecase;

import bankaccount.core.domain.entity.Account;
import bankaccount.core.domain.entity.Transaction;

/**
 * <p>Service to get history of {@link Transaction} on a {@link Account}.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface RequestFunction<R, T> {

  R apply(T t);
}
