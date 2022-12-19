package bankaccount.repository;

import bankaccount.model.entities.Account;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 *  @author CISSE Abdoulaye 2022-12-15
 */
class AccountRepositoryDefaultTest {

  @Test
  void find_exist_account_number() {
    //Given
    String accountNumber1 = "AC001";
    Account account1 = new Account(accountNumber1, BigDecimal.ZERO);

    String accountNumber2 = "AC002";
    Account account2 = new Account(accountNumber2, BigDecimal.ZERO);

    Set<Account> accountList = Set.of(account1, account2);
    AccountRepositoryDefault accountRepositoryDefault = new AccountRepositoryDefault(accountList);

    //When
    Optional<Account> optionalAccount = accountRepositoryDefault.findByAccountNumber(accountNumber1);

    //Then
    Assertions.assertTrue(optionalAccount.isPresent());

    Account accountActual = optionalAccount.get();
    Assertions.assertEquals(account1,accountActual);
  }

  @Test
  void find_unKnown_account_number() {

    //Given
    String accountNumber1 = "AC001";
    Account account1 = new Account(accountNumber1, BigDecimal.ZERO);

    String accountNumber2 = "AC002";
    Account account2 = new Account(accountNumber2, BigDecimal.ZERO);

    Set<Account> accountList = Set.of(account1, account2);
    AccountRepositoryDefault accountRepositoryDefault = new AccountRepositoryDefault(accountList);

    //When
    Optional<Account> optionalAccount = accountRepositoryDefault.findByAccountNumber("AC003");

    //Then
    Assertions.assertTrue(optionalAccount.isEmpty());
  }
}