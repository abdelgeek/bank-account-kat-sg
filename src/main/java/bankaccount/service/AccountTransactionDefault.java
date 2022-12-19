package bankaccount.service;

import bankaccount.dto.TransactionCommand;
import bankaccount.model.entities.Account;
import bankaccount.repository.AccountRepository;
import bankaccount.service.exception.AccountNumberNotFound;

/**
 * <p>Implementation of {@link AccountTransaction}. </p>
 *
 * @author CISSE Abdoulaye 2022-12-15
 */
public class AccountTransactionDefault implements AccountTransaction {

  private final AccountRepository accountRepository;

  public AccountTransactionDefault(AccountRepository accountRepository) {
    this.accountRepository = accountRepository;
  }

  @Override
  public void doTransaction(TransactionCommand transactionCommand) {
    String accountNumber = transactionCommand.accountNumber();
    Account account = accountRepository.findByAccountNumber(accountNumber).orElseThrow(
      () -> new AccountNumberNotFound());
    account.executeOperation(transactionCommand);
  }
}
