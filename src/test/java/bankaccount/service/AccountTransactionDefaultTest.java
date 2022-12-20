package bankaccount.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

import bankaccount.domain.entities.Account;
import bankaccount.dto.TransactionCommand;
import bankaccount.dto.TransactionType;
import bankaccount.repository.AccountRepository;
import bankaccount.service.exception.AccountNumberNotFound;
import bankaccount.service.exception.InsufficientBalanceException;
import bankaccount.service.exception.NegativeAccountException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author CISSE Abdoulaye 2022-12-15
 */
class AccountTransactionDefaultTest {

  private static final String accountNumber = "AC001";
  private static AccountRepository accountRepository;
  private static AccountTransactionDefault accountOperationDefault;

  @BeforeAll
  public static void setUp() {
    accountRepository = mock(AccountRepository.class);
    accountOperationDefault = new AccountTransactionDefault(accountRepository);
  }

  @Test
  public void doOperation_with_unknown_account() {

    //Given
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

  @Test
  public void transaction_withNegativeAmount() {

    //Given
    BigDecimal amount = BigDecimal.valueOf(-10_000);
    TransactionType transactionType = TransactionType.WITHDRAWAL;
    LocalDateTime dateOperation = LocalDateTime.now();
    TransactionCommand transactionCommand = new TransactionCommand(accountNumber, dateOperation, amount, transactionType);

    //then
    assertThrows(NegativeAccountException.class,
      () -> {
        accountOperationDefault.doTransaction(transactionCommand);
      });
  }

  @Test
  public void deposit() {

    //Given
    Account account = new Account(accountNumber, BigDecimal.ZERO);
    BigDecimal amount = BigDecimal.valueOf(10_000);
    TransactionType transactionType = TransactionType.DEPOSIT;
    LocalDateTime date = LocalDateTime.now();
    TransactionCommand transactionCommand = new TransactionCommand(accountNumber, date, amount, transactionType);

    //when
    Mockito.when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
    accountOperationDefault.doTransaction(transactionCommand);

    //then
    BigDecimal expectedBalance = BigDecimal.valueOf(10_000);
    BigDecimal actualBalance = account.getBalance();
    Assertions.assertEquals(expectedBalance, actualBalance);
  }

  @Test
  public void withdrawal_with_transaction_amount_inferrior_to_actual_account_balance() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account(accountNumber, initialBalance);
    BigDecimal amount = BigDecimal.valueOf(10_000);
    TransactionType transactionType = TransactionType.WITHDRAWAL;
    LocalDateTime dateOperation = LocalDateTime.now();
    TransactionCommand transactionCommand = new TransactionCommand(accountNumber, dateOperation, amount, transactionType);

    //when
    Mockito.when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
    accountOperationDefault.doTransaction(transactionCommand);

    //then
    BigDecimal expectedBalance = BigDecimal.valueOf(10_000);
    BigDecimal actualBalance = account.getBalance();
    Assertions.assertEquals(expectedBalance, actualBalance);
  }

  @Test
  public void withdrawal_with_transaction_amount_equal_to_actual_account_balance() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account(accountNumber, initialBalance);
    BigDecimal amount = BigDecimal.valueOf(20_000);
    TransactionType transactionType = TransactionType.WITHDRAWAL;
    LocalDateTime transactionDate = LocalDateTime.now();
    TransactionCommand transactionCommand = new TransactionCommand(accountNumber, transactionDate, amount, transactionType);

    //When
    Mockito.when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
    accountOperationDefault.doTransaction(transactionCommand);


    //then
    BigDecimal expectedBalance = BigDecimal.ZERO;
    BigDecimal actualBalance = account.getBalance();
    Assertions.assertEquals(expectedBalance, actualBalance);
  }

  @Test
  public void whihdraw_withInsufficientBalance() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account(accountNumber, initialBalance);
    BigDecimal amount = BigDecimal.valueOf(40_000);
    TransactionType transactionType = TransactionType.WITHDRAWAL;
    LocalDateTime dateOperation = LocalDateTime.now();
    TransactionCommand transactionCommand = new TransactionCommand(accountNumber, dateOperation, amount, transactionType);

    //then
    assertThrows(InsufficientBalanceException.class,
      () -> {
        accountOperationDefault.doTransaction(transactionCommand);
      });
  }


  @Test
  public void do_many_transaction() {
    //Given
    Account account = new Account(accountNumber, BigDecimal.ZERO);

    BigDecimal amount1 = BigDecimal.valueOf(40_000);
    TransactionType transactionType1 = TransactionType.DEPOSIT;
    LocalDateTime dateOperation1 = LocalDateTime.now();
    TransactionCommand transactionCommand1 = new TransactionCommand(accountNumber, dateOperation1,
      amount1, transactionType1);


    BigDecimal amount2 = BigDecimal.valueOf(20_000);
    TransactionType transactionType2 = TransactionType.WITHDRAWAL;
    LocalDateTime dateOperation2 = LocalDateTime.now();
    TransactionCommand transactionCommand2 = new TransactionCommand(accountNumber, dateOperation2,
      amount2, transactionType2);

    //When
    Mockito.when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
    accountOperationDefault.doTransaction(transactionCommand1);
    accountOperationDefault.doTransaction(transactionCommand2);


    //Then
    BigDecimal expectedBalance = BigDecimal.valueOf(20_000);
    BigDecimal actualBalance = account.getBalance();
    Assertions.assertEquals(expectedBalance, actualBalance);
  }

}