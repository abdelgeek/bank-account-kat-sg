package bankaccount.core.application.usecase.gettransactionshistory;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import bankaccount.core.application.exception.AccountNumberNotFound;
import bankaccount.core.application.port.secondaire.AccountPort;
import bankaccount.core.application.usecase.RequestFunction;
import bankaccount.core.domain.entity.Account;
import bankaccount.core.domain.entity.Transaction;
import bankaccount.core.domain.objectvalue.TransactionType;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;


/**
 * @author CISSE Abdoulaye 2022-12-15
 */
class GetTransactionHistoryDefaultTest {

  private static AccountPort accountPort;
  private static RequestFunction<List<Transaction>, String> requestFunction;
  private final String accountNumber = "AC001";

  @BeforeAll
  private static void setUp() {
    accountPort = mock(AccountPort.class);
    requestFunction = new GetTransactionHistory(accountPort);
  }

  @Test
  public void get_transactions_history_with_unknown_account() {

    //When
    Mockito.when(accountPort.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

    //then
    assertThrows(AccountNumberNotFound.class,
      () -> requestFunction.apply(accountNumber));
  }

  @Test
  public void get_transactions_history_with_known_account() {

    //Given

    Account account = new Account(accountNumber, BigDecimal.valueOf(40_000));

    Transaction transaction1 = new Transaction(LocalDateTime.now(),
      BigDecimal.valueOf(50_000), BigDecimal.valueOf(50_000), TransactionType.DEPOSIT);
    Transaction transaction2 = new Transaction(LocalDateTime.now(),
      BigDecimal.valueOf(10_000), BigDecimal.valueOf(40_000), TransactionType.WITHDRAWAL);
    List<Transaction> transactions = List.of(transaction1, transaction2);
    account.setTransactions(transactions);

    //When
    Mockito.when(accountPort.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
    List<Transaction> transactionsActual = requestFunction.apply(accountNumber);

    //then
    assertEquals(2, transactionsActual.size());
    assertEquals(transactions, transactionsActual);
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