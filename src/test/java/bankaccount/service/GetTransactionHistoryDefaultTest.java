package bankaccount.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;

import bankaccount.domain.entities.Account;
import bankaccount.domain.entities.Transaction;
import bankaccount.dto.TransactionType;
import bankaccount.repository.AccountRepository;
import bankaccount.service.exception.AccountNumberNotFound;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

/**
 * @author CISSE Abdoulaye 2022-12-15
 */
class GetTransactionHistoryDefaultTest {

  private final String accountNumber = "AC001";
  private AccountRepository accountRepository;

  @Test
  public void get_transactions_history_with_unknown_account() {

    //Given
    accountRepository = mock(AccountRepository.class);
    GetTransactionsHistory getTransactionsHistory = new GetTransactionsHistoryDefault(accountRepository);

    //When
    Mockito.when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.empty());

    //then
    assertThrows(AccountNumberNotFound.class,
      () -> {
        getTransactionsHistory.get(accountNumber);
      });
  }

  @Test
  public void get_transactions_history_with_known_account() {

    //Given
    accountRepository = mock(AccountRepository.class);
    GetTransactionsHistory getTransactionsHistory = new GetTransactionsHistoryDefault(accountRepository);

    Account account = new Account(accountNumber, BigDecimal.valueOf(40_000));

    Transaction transaction1 = new Transaction(LocalDateTime.now(),
      BigDecimal.valueOf(50_000), BigDecimal.valueOf(50_000), TransactionType.DEPOSIT);
    Transaction transaction2 = new Transaction(LocalDateTime.now(),
      BigDecimal.valueOf(10_000), BigDecimal.valueOf(40_000), TransactionType.WITHDRAWAL);
    List<Transaction> transactions = List.of(transaction1, transaction2);
    account.setTransactions(transactions);

    //When
    Mockito.when(accountRepository.findByAccountNumber(accountNumber)).thenReturn(Optional.of(account));
    List<Transaction> transactionsActual = getTransactionsHistory.get(accountNumber);

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