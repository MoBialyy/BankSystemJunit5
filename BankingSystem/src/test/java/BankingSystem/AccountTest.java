package BankingSystem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import java.util.List;

class AccountTest {
	
	private Customer Ali;
	
	@BeforeEach
	public void CreateAccount() {
		Ali = new Customer("Ali Refaat", "Ali_Refaat", "Fifth settlement", "30311270100058", "01024640120", "21p0105@eng.asu.edu.eg", "123456");
	}
	
	@Test
	@DisplayName("Create Account")
    public void testCreateAccount() {
		Account Ali_Account = Account.createAccount(Ali, "A453", "Savings", 1000);
        assertNotNull(Ali_Account);
        assertEquals("A453", Ali_Account.getAccountId());
        assertEquals("Savings", Ali_Account.getAccountType());
        assertEquals(1000, Ali_Account.getBalance());
        assertTrue(Ali.getAccounts().contains(Ali_Account));
    }

	@Test
	@DisplayName("Create Account with negative Balance")
	public void testCreateNegBalanceAcc() {
		Account account = Account.createAccount(Ali, "A835", "Checking", -10000);
		assertFalse(Ali.getAccounts().contains(account));
	}
	
	@Test
	@DisplayName("Test Add Transaction")
    public void testAddTransaction() {
		Account Ali_Account = Account.createAccount(Ali, "A453", "Savings", 1000);
        Ali_Account.addTransaction("Deposit", 500);
        List<Transaction> transactions = Ali_Account.getTransactions();
        assertNotNull(transactions);
        assertEquals(1, transactions.size());
    }
	
	@Test
	@DisplayName("Test GetNextTransaction")
    public void testGetNextTransactionId() {
		Account Ali_Account = Account.createAccount(Ali, "A453", "Savings", 1000);
        assertEquals(1, Ali_Account.getNextTransactionId());
        Ali_Account.addTransaction("Deposit", 500);
        assertEquals(2, Ali_Account.getNextTransactionId());
    }

   @Nested 
   @DisplayName("Test Deposit")
   public class TestDeposit {
	   @Test
	   @DisplayName("Test Deposit With Valid Amount")
	   public void testDepositWithValidAmount(){
		   Account account = new Account("A123", "Checking", 1000);
		   assertTrue(account.deposit(500));
		   assertEquals(1500, account.getBalance());
	   }
	   
       @Test
	   @DisplayName("Test Deposit With Negative Amount")
       public void testDepositWithNegativeAmount() {
    	   Account account = new Account("A897", "Savings", 1000);
    	   assertFalse(account.deposit(-100));
    	   assertEquals(1000, account.getBalance());
       }

       @Test
	   @DisplayName("Test Deposit With Invalid Amount")
       public void testDepositWithInvalidAmount() {
    	   Account account = new Account("A672", "Savings", 1000);
    	   assertFalse(account.deposit(5100));
    	   assertEquals(1000, account.getBalance());
       }

       @Test
       @DisplayName("Test Deposit With Zero Amount")
       public void testDepositWithZeroAmount(){
    	   Account account = new Account("A787", "Savings", 1000);
    	   assertFalse(account.deposit(0));
    	   assertEquals(1000, account.getBalance());
       }
       
       @Test
       @DisplayName("Test Deposit With Fractions")
       public void testDepositWithFractions() {
    	   Account account = new Account("A678", "Savings", 1000);
    	   assertFalse(account.deposit(187.5));
    	   assertEquals(1000, account.getBalance());
       }
   }

   @Nested
   @DisplayName("Test Withdraw")
   public class TestWithdraw {
	   @Test
	   @DisplayName("Test Withdraw With Valid Amount")
	   public void testWithdrawWithValidAmount(){
		   Account account = new Account("A784", "Savings", 1000);
		   assertTrue(account.withdraw(500));
		   assertEquals(500, account.getBalance());
	   }

      @Test
      @DisplayName("Test Withdraw With Negative Amount")
      public void testWithdrawWithNegativeAmount() {
    	  Account account = new Account("A123", "Savings", 1000);
    	  assertFalse(account.withdraw(-500));
    	  assertEquals(1000, account.getBalance());
      }

      @Test
      @DisplayName("Test Withdraw Exceeding Balance")
      public void testWithdrawlExceedingBalance() {
    	  Account account = new Account("A367", "Savings", 1000);
	  	  assertFalse(account.withdraw(1100));
	  	  assertEquals(1000, account.getBalance());
      }

      @Test
      @DisplayName("Test Withdraw With Zero Amount")
      public void testWithdrawWithZeroAmount() {
    	  Account account = new Account("A756", "Savings", 1000);
    	  assertFalse(account.withdraw(0));
    	  assertEquals(1000, account.getBalance());
      }
      
      @Test
      @DisplayName("Test Withdraw With Fractions")
      public void testWithdrawWithFractions() {
    	  Account account = new Account("A745", "Savings", 1000);
    	  assertFalse(account.withdraw(78.5));
    	  assertEquals(1000, account.getBalance());
      }
   }

   @Nested
   @DisplayName("Test TransferFunds")
   public class TestTransferFunds {
	   @Test
	   @DisplayName("Test Transfer Funds With Valid Amount")
	   public void testTransferFundsWithValidAMount() {
		   Account senderAccount = new Account("A342", "Savings", 1000);
		   Account receiverAccount = new Account("A409", "Savings", 500);
		   assertTrue(senderAccount.TransferFunds(senderAccount, receiverAccount, 500));
		   assertEquals(500, senderAccount.getBalance());
		   assertEquals(1000, receiverAccount.getBalance());
	   }

	   @Test
	   @DisplayName("Test Transfer Funds With Negative Amount")
	   public void testTransferFundsWithNegativeAmount() {
		   Account senderAccount = new Account("A123", "Savings", 1000);
		   Account receiverAccount = new Account("A456", "Savings", 500);
		   assertFalse(senderAccount.TransferFunds(senderAccount, receiverAccount, -500));
	   }

	   @Test
	   @DisplayName("Test Transfer To The Same Account")
	   public void testTransferFundsSameAccount() {
		   Account account = new Account("A845", "Savings", 1000);
		   assertFalse(account.TransferFunds(account, account, 500));
		   assertEquals(1000, account.getBalance());
	   }

	   @Test
	   @DisplayName("Test Tansfer Funds With Zero Amount")
	   public void testTansferFundsWithZeroAmount() {
		   Account senderAccount = new Account("A321", "Savings", 1000);
		   Account receiverAccount = new Account("A489", "Savings", 500);
		   assertFalse(senderAccount.TransferFunds(senderAccount, receiverAccount, 0));
		   assertEquals(1000, senderAccount.getBalance());
		   assertEquals(500, receiverAccount.getBalance());
	   }
	   
	   @Test
	   @DisplayName("Test transferring funds to/from a non-existent account")
	   public void testTransferNonExistentAccount() {
		   Account senderAccount = new Account("A389", "Checkings", 1000);
		   Account receiverAccount = Account.createAccount(Ali, "A489", "Savings", 500);
		   Ali.removeAccount(receiverAccount);
		   assertFalse(senderAccount.TransferFunds(senderAccount, null, 300));
		   assertEquals(1000, senderAccount.getBalance());
	   }
	   
	   @Test
	   @DisplayName("Test Transfer Funds with Insufficient funds")
	   public void testTransferFundsInsufficientfunds() {
		   Account senderAccount = new Account("A357", "Checkings", 1000);
		   Account receiverAccount = Account.createAccount(Ali, "A159", "Savings", 500);
		   assertFalse(senderAccount.TransferFunds(senderAccount, receiverAccount, 1100));
	   }
   }

   @Test
   @DisplayName("Test string representation of Account")
   public void testToString() {
	   Account account = new Account("A198", "Checking", 190000);
	   String Expected = "Account{" +
			   "AccountId=" + account.getAccountId() +
			   ", AccountType='" + account.getAccountType() + '\'' +
			   ", balance=" + account.getBalance() +
			   '}';
	   assertEquals(Expected, account.toString());
   }
   
   @AfterEach
   public void tearDown() {
	   Ali = null;
   }

//	  @Test
//	  void test() {
//		  fail("Not yet implemented");
//	  }

}
