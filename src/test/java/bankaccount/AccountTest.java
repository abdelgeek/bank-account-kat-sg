package bankaccount;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import bankaccount.dto.TransactionCommand;
import bankaccount.domain.entities.Account;
import bankaccount.domain.entities.Transaction;
import bankaccount.service.exception.InsufficientBalanceException;
import bankaccount.service.exception.NegativeAccountException;
import bankaccount.dto.TransactionType;
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
  public void save_transaction() {

    //Given
    BigDecimal initialBalance = BigDecimal.valueOf(20_000);
    Account account = new Account(accountNumber, initialBalance);
    BigDecimal amount = BigDecimal.valueOf(20_000);
    TransactionType transactionType = TransactionType.WITHDRAWAL;
    LocalDateTime transactionDate = LocalDateTime.now();

    //when
    account.saveTransaction(transactionDate,amount, BigDecimal.ZERO,transactionType);

    //then
    BigDecimal expectedBalance = BigDecimal.ZERO;
    BigDecimal actualBalance = account.getBalance();
    Assertions.assertEquals(expectedBalance, actualBalance);
  }




  @Test
  public void transactions_history() {
    //Given
    Account account = new Account(accountNumber, BigDecimal.ZERO);

    BigDecimal amount1 = BigDecimal.valueOf(40_000);
    TransactionType transactionType1 = TransactionType.DEPOSIT;
    LocalDateTime dateOperation1 = LocalDateTime.now();
    account.saveTransaction(dateOperation1,amount1,amount1,transactionType1);


    BigDecimal amount2 = BigDecimal.valueOf(20_000);
    TransactionType transactionType2 = TransactionType.WITHDRAWAL;
    LocalDateTime dateOperation2 = LocalDateTime.now();
    TransactionCommand transactionCommand2 = new TransactionCommand(accountNumber, dateOperation2,
      amount2, transactionType2);
    account.saveTransaction(dateOperation2,amount2,amount2,transactionType2);

    //When

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
}
