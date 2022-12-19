package bankaccount;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bankaccount.dto.TransactionCommand;
import bankaccount.model.entities.Account;
import bankaccount.model.entities.Transaction;
import bankaccount.model.exception.InsufficientBalanceException;
import bankaccount.model.exception.NegativeAccountException;
import bankaccount.model.valueobject.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * @author CISSE Abdoulaye 2022-09-20
 */
class AccountTest {

  private String accountNumber;

  @BeforeEach
  public void setUp() {
    accountNumber = "AC001";
  }

  @Test
  public void transaction_withNegativeAmount() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account(accountNumber, initialBalance);
    BigDecimal amount = BigDecimal.valueOf(-10_000);
    TransactionType transactionType = TransactionType.WITHDRAWAL;
    LocalDateTime dateOperation = LocalDateTime.now();
    TransactionCommand transactionCommand = new TransactionCommand(accountNumber, dateOperation, amount, transactionType);

    //then
    assertThrows(NegativeAccountException.class,
      () -> {
        account.executeOperation(transactionCommand);
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
    account.executeOperation(transactionCommand);

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
    account.executeOperation(transactionCommand);

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

    //when
    account.executeOperation(transactionCommand);

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
        account.executeOperation(transactionCommand);
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
    account.executeOperation(transactionCommand1);
    account.executeOperation(transactionCommand2);

    //Then
    BigDecimal expectedBalance = BigDecimal.valueOf(20_000);
    BigDecimal actualBalance = account.getBalance();
    Assertions.assertEquals(expectedBalance, actualBalance);
  }


  @Test
  public void transactions_history_with_no_Transactions_do() {
    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account(accountNumber, initialBalance);


    //then
    List<Transaction> transactions = account.transactionsHistory();
    assertTrue(transactions.isEmpty());
  }

  @Test
  public void transactions_history_with_at_least_one_Transactions_do() {
    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account(accountNumber, initialBalance);
    LocalDateTime firstDateOperation = LocalDateTime.now();
    TransactionCommand firstTransactionCommand = new TransactionCommand(accountNumber, firstDateOperation,
      BigDecimal.valueOf(30_000),
      TransactionType.DEPOSIT);
    ;

    LocalDateTime secondDateOperation = LocalDateTime.now();
    TransactionCommand secondTransactionCommand = new TransactionCommand(accountNumber, secondDateOperation,
      BigDecimal.valueOf(5_000),
      TransactionType.WITHDRAWAL);

    //When
    account.executeOperation(secondTransactionCommand);
    account.executeOperation(firstTransactionCommand);

    //then
    List<Transaction> operationsActual = account.transactionsHistory();

    Transaction firstTransaction = new Transaction(firstDateOperation, BigDecimal.valueOf(30_000),
      BigDecimal.valueOf(50_000), TransactionType.DEPOSIT);
    Transaction secondTransaction = new Transaction(secondDateOperation, BigDecimal.valueOf(5_000),
      BigDecimal.valueOf(45_000), TransactionType.WITHDRAWAL);
    List<Transaction> operationsExpected = new ArrayList<>();
    operationsExpected.add(firstTransaction);
    operationsExpected.add(secondTransaction);

    Assertions.assertEquals(operationsExpected, operationsActual);
  }

}
