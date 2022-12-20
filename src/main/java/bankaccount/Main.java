package bankaccount;

import bankaccount.dto.TransactionCommand;
import bankaccount.domain.entities.Account;
import bankaccount.domain.entities.Transaction;
import bankaccount.dto.TransactionType;
import bankaccount.repository.AccountRepository;
import bankaccount.repository.AccountRepositoryDefault;
import bankaccount.service.AccountTransaction;
import bankaccount.service.AccountTransactionDefault;
import bankaccount.service.GetTransactionsHistory;
import bankaccount.service.GetTransactionsHistoryDefault;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author CISSE Abdoulaye 2022-09-22
 */
public class Main {

  public static void main(String[] args) {
    AccountRepository accountRepository = initializeAccountRepository();

    AccountTransaction accountTransaction = new AccountTransactionDefault(accountRepository);

    String accountNumber = "AC001";
    BigDecimal amount1 = BigDecimal.valueOf(100_000);
    TransactionType transactionType1 = TransactionType.DEPOSIT;
    LocalDateTime dateOperation1 = LocalDateTime.now();
    TransactionCommand transactionCommand1 = new TransactionCommand(accountNumber, dateOperation1, amount1, transactionType1);

    accountTransaction.doTransaction(transactionCommand1);


    BigDecimal amount2 = BigDecimal.valueOf(50_000);
    TransactionType transactionType2 = TransactionType.DEPOSIT;
    LocalDateTime dateOperation2 = LocalDateTime.now();
    TransactionCommand transactionCommand2 = new TransactionCommand(accountNumber, dateOperation2, amount2, transactionType2);

    accountTransaction.doTransaction(transactionCommand2);

    GetTransactionsHistory getTransactionsHistory = new GetTransactionsHistoryDefault(accountRepository);
    List<Transaction> transactions = getTransactionsHistory.get("AC001");

    System.out.println("date ||  amount ||  transaction Type ||  balance");
    transactions.forEach(transaction ->
      System.out.printf("%tD ||  %s ||  %s ||  %s %n",
        transaction.date(), transaction.amount().toString(), transaction.transactionType()
        , transaction.accountBalance().toString())
    );
  }

  private static AccountRepository initializeAccountRepository() {
    String accountNumber1 = "AC001";
    Account account1 = new Account(accountNumber1, BigDecimal.ZERO);

    String accountNumber2 = "AC002";
    Account account2 = new Account(accountNumber2, BigDecimal.ZERO);

    Set<Account> accountList = Set.of(account1, account2);
    return new AccountRepositoryDefault(accountList);
  }
}
