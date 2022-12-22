package bankaccount;

import bankaccount.core.application.port.secondaire.AccountPort;
import bankaccount.core.application.usecase.RequestConsumer;
import bankaccount.core.application.usecase.RequestFunction;
import bankaccount.core.application.usecase.executetransaction.ExecuteTransactionUseCase;
import bankaccount.core.application.usecase.executetransaction.TransactionCommand;
import bankaccount.core.application.usecase.gettransactionshistory.GetTransactionHistory;
import bankaccount.core.domain.entity.Account;
import bankaccount.core.domain.entity.Transaction;
import bankaccount.core.domain.objectvalue.TransactionType;
import bankaccount.infrastructure.repository.AccountRepository;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

/**
 * @author CISSE Abdoulaye 2022-09-22
 */
public class Main {

  public static void main(String[] args) {
    AccountPort accountPort = initializeAccountRepository();

    RequestConsumer<TransactionCommand> requestConsumer = new ExecuteTransactionUseCase(accountPort);

    String accountNumber = "AC001";
    BigDecimal amount1 = BigDecimal.valueOf(100_000);
    TransactionType transactionType1 = TransactionType.DEPOSIT;
    LocalDateTime dateOperation1 = LocalDateTime.now();
    TransactionCommand transactionCommand1 = new TransactionCommand(accountNumber, dateOperation1, amount1, transactionType1);

    requestConsumer.execute(transactionCommand1);


    BigDecimal amount2 = BigDecimal.valueOf(50_000);
    TransactionType transactionType2 = TransactionType.DEPOSIT;
    LocalDateTime dateOperation2 = LocalDateTime.now();
    TransactionCommand transactionCommand2 = new TransactionCommand(accountNumber, dateOperation2, amount2, transactionType2);

    requestConsumer.execute(transactionCommand2);

    RequestFunction<List<Transaction>, String> requestFunction = new GetTransactionHistory(accountPort);
    List<Transaction> transactions = requestFunction.apply("AC001");

    System.out.println("date ||  amount ||  transaction Type ||  balance");
    transactions.forEach(transaction ->
      System.out.printf("%tD ||  %s ||  %s ||  %s %n",
        transaction.getDate(), transaction.getAmount().toString(), transaction.getTransactionType()
        , transaction.getAccountBalance().toString())
    );
  }

  private static AccountPort initializeAccountRepository() {
    String accountNumber1 = "AC001";
    Account account1 = new Account(accountNumber1, BigDecimal.ZERO);

    String accountNumber2 = "AC002";
    Account account2 = new Account(accountNumber2, BigDecimal.ZERO);

    Set<Account> accountList = Set.of(account1, account2);
    return new AccountRepository(accountList);
  }
}
