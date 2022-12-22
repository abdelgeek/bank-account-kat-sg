package bankaccount.core.application.usecase;

/**
 * <p>Service to make an account Transaction.</p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public interface RequestConsumer<T> {
  void execute(T t);
}
