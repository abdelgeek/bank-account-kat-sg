package bankaccount.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import bankaccount.dto.TransactionCommand;
import bankaccount.model.entities.Account;
import bankaccount.model.valueobject.TransactionType;
import bankaccount.repository.AccountRepository;
import bankaccount.service.exception.AccountNumberNotFound;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author CISSE Abdoulaye 2022-12-15
 */
class AccountTransactionDefaultTest {

  private AccountRepository accountRepository;


  @Test
  public void doOperation_with_unknown_account() {

    //Given
    accountRepository = mock(AccountRepository.class);
    AccountTransactionDefault accountOperationDefault = new AccountTransactionDefault(accountRepository);

    String accountNumber = "AC001";
    BigDecimal amount = BigDecimal.valueOf(10_000);
    TransactionType transactionType = TransactionType.WITHDRAWAL;
    LocalDateTime dateOperation = LocalDateTime.now();
    TransactionCommand transactionCommand = new TransactionCommand(accountNumber, dateOperation, amount, transactionType);

    //When
    Mockito.when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

    //then
    assertThrows(AccountNumberNotFound.class,
      () -> {
        accountOperationDefault.doTransaction(transactionCommand);
      });
  }


  @Test
  public void doOperation_with_known_account() {

    //Given
    accountRepository = mock(AccountRepository.class);
    AccountTransactionDefault accountOperationDefault = new AccountTransactionDefault(accountRepository);

    String accountNumber = "AC001";
    Account account = new Account(accountNumber, BigDecimal.ZERO);

    BigDecimal amount = BigDecimal.valueOf(10_000);
    TransactionType transactionType = TransactionType.DEPOSIT;
    LocalDateTime dateOperation = LocalDateTime.now();
    TransactionCommand transactionCommand = new TransactionCommand(accountNumber, dateOperation, amount, transactionType);

    //When
    Mockito.when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
    accountOperationDefault.doTransaction(transactionCommand);

    //then
    assertEquals(amount, account.getBalance());
  }
}