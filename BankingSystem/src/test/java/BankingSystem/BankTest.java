package BankingSystem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import BankingSystem.Loan.LoanStatus;
import java.util.List;
import java.time.LocalDate;

class BankTest {
	
	private Customer Ali;
	private Customer Korkor;
	private Bank bank;
	
	@BeforeEach
	public void setUp() {
		Ali = new Customer("Ali Refaat", "Ali_Refaat", "Fifth settlement", "30311270100058", "01024640120", "21p0105@eng.asu.edu.eg", "123456");
		Korkor = new Customer("Omar Korkor", "luca_korkor", "Nozha", "303090801000059", "01157758181", "21p0065@eng.asu.edu.eg", "112233");
		bank = new Bank("HSBC", "Fifth Settlement");
	}
	
	@Nested
	@DisplayName("Test Setting Bank Name and Bank Address")
	public class TestSettingBankNameAndAddress {
		@Test
		@DisplayName("Test setting the Bank Name")
		public void testSetBankName() {
			bank.setBankName("Qatar Bank");
			assertNotNull(bank.getBankName());
			assertEquals("Qatar Bank", bank.getBankName());
		}

		@Test
		@DisplayName("Test setting the Bank Name to an Empty String")
		public void testSetBankNameEmptyString() {
			bank.setBankName("");
			assertNull(bank.getBankName());
		}

		@Test
		@DisplayName("Test setting the Bank Name to Null")
		public void testSetBankNameNull() {
			bank.setBankName(null);
			assertNull(bank.getBankName());
		}
		
		@Test
		@DisplayName("Test setting the Bank Address")
		public void testSetBankAddress() {
			bank.setBankAddress("Fifth Settlement");
			assertNotNull(bank.getBankAddress());
			assertEquals("Fifth Settlement", bank.getBankAddress());
		}
		
		@Test
		@DisplayName("Test setting the Bank Address to an Empty String")
		public void testSetBankAddressEmptyString() {
			bank.setBankAddress("");
			assertNull(bank.getBankAddress());
		}
		
		@Test
		@DisplayName("Test setting the Bank Address to Null")
		public void testSetBankAddressNull() {
			bank.setBankAddress(null);
			assertNull(bank.getBankAddress());
		}
	}
	
	@Nested
	@DisplayName("Test Login and Authentication")
	public class TestLoginAndAuthentocation{
		@Test 
		@DisplayName("Test Valid Authentication")
		public void testValidAuthentication() {
			bank.Login(Ali.getCustomerId(), Ali.getPassword());
	        assertTrue(bank.authenticateUser(Ali.getCustomerId(), Ali.getPassword()));
		}
		
		@Test
		@DisplayName("Test Invalid Authentication")
		public void testInvalidAuthentication() {
			bank.Login(Ali.getCustomerId(), Ali.getPassword());
	        assertFalse(bank.authenticateUser("invalidCustomer", "invalidPassword"));
		}
		
		@Test
	    @DisplayName("Test Authentication with Null Values")
	    public void testAuthenticationWithNullValues() {
	        bank.Login(null, null);
	        assertFalse(bank.authenticateUser(null, null));
	    }
		
		@Test
	    @DisplayName("Test Empty Authentication Map")
	    public void testEmptyAuthenticationMap() {
	        assertFalse(bank.authenticateUser("nonExistingCustomer", "password"));
	    }
		
		@Test
	    @DisplayName("Test Performance of Authentication")
	    public void testPerformance() {
	        String customerId = "testCustomer";
	        String password = "testPassword";
	        int attempts = 100000;
	        for (int i = 0; i < attempts; i++) {
	            bank.Login(Ali.getCustomerId() + i, Ali.getPassword());
	        }

	        long startTime = System.currentTimeMillis();
	        for (int i = 0; i < attempts; i++) {
	            bank.authenticateUser(customerId + i, password);
	        }
	        long endTime = System.currentTimeMillis();
	        long executionTime = endTime - startTime;
	        System.out.println("Execution Time for " + attempts + " attempts: " + executionTime + " ms");
	        assertTrue(executionTime < 1000); // Adjust as needed
	    }
	}
	
	@Nested
    @DisplayName("Test Customer Operations")
    public class TestCustomerOperations {
		@Test
		@DisplayName("Add a new customer to the bank")
		public void testAddNewCustomer() {
		    bank.addCustomer(Ali);
		    assertTrue(bank.getCustomers().contains(Ali));
		}
		
		@Test
		@DisplayName("Add a customer with Empty String")
		public void testAddCustomerWithEmptyStrings() {
			Ali.setAddress("");
		    bank.addCustomer(Ali);
		    assertFalse(bank.getCustomers().contains(Ali));
		}

		@Test
		@DisplayName("Remove an existing customer from the bank")
		public void testRemoveExistingCustomer() {
		    bank.addCustomer(Ali);
		    bank.removeCustomer(Ali);
		    assertFalse(bank.getCustomers().contains(Ali));
		}
		
		@Test
		@DisplayName("Remove a non-existing customer from the bank")
		public void testRemoveNonExistingCustomer() {
		    boolean removed = bank.removeCustomer(null);
		    assertFalse(removed);
		}
		
		@Test
		@DisplayName("Modify details of an existing customer")
		public void testModifyDetailsOfExistingCustomer() {
		    bank.addCustomer(Ali);
		    Ali.setEmailAddress("newemail@gmail.com");
		    Ali.setPhoneNumber("01024640124");
		    assertEquals("newemail@gmail.com", Ali.getEmail());
		    assertEquals("01024640124", Ali.getPhoneNumber());
		}
		
		@Test
		@DisplayName("Search for customers by a valid name")
		public void testSearchCustomersByValidName() {
		    bank.addCustomer(Ali);
		    bank.addCustomer(Korkor);
		    List<Customer> foundCustomers = bank.searchCustomersByName("Ali refaat");
		    assertEquals(1, foundCustomers.size());
		    assertEquals("Ali Refaat", foundCustomers.get(0).getName());
		}
		
		@Test
		@DisplayName("Search for customers by a non-existing name")
		public void testSearchCustomersByNonExistingName() {
		    List<Customer> foundCustomers = bank.searchCustomersByName("Nonexistent Name");
		    assertTrue(foundCustomers.isEmpty());
		}
		
		@Test
		@DisplayName("Search for customers by an Empty Name")
		public void testSearchCustomersByEmptyName() {
		    List<Customer> foundCustomers = bank.searchCustomersByName("");
		    assertTrue(foundCustomers.isEmpty());
		}
		
		@Test
		@DisplayName("Search for customers by a name with case sensitivity")
		public void testSearchCustomersByCaseSensitiveName() {
			bank.addCustomer(Ali);
		    bank.addCustomer(Korkor);
		    List<Customer> foundCustomers = bank.searchCustomersByName("ali refaat");
		    assertEquals(1, foundCustomers.size());
		    assertEquals("Ali Refaat", foundCustomers.get(0).getName());
		}
		@Test
		@DisplayName("Search for a customer by a valid ID")
		public void testSearchCustomerByValidID() {
		    bank.addCustomer(Ali);
		    Customer foundCustomer = bank.searchCustomerById(Ali.getCustomerId());
		    assertEquals(Ali, foundCustomer);
		}
		
		@Test
		@DisplayName("Search for a customer by a non-existing ID")
		public void testSearchCustomerByNonExistingID() {
		    Customer foundCustomer = bank.searchCustomerById("Nonexistent ID");
		    assertNull(foundCustomer);
		}
		
		@Test
		@DisplayName("Search for a customer by an empty ID (edge case)")
		public void testSearchCustomerByEmptyID() {
		    Customer foundCustomer = bank.searchCustomerById("");
		    assertNull(foundCustomer);
		}
	}
	
	
	@Nested
    @DisplayName("Test Account Operations")
    public class TestAccountOperations {
		@Test
		@DisplayName("Open an account for an existing customer")
		public void testOpenAccountForExistingCustomer() {
		    bank.addCustomer(Ali);
		    Account account = bank.openAccount(Ali, "A0356", "Savings", 1000);
		    assertNotNull(account);
		}
		
		@Test
		@DisplayName("Open an account for a non-existing customer")
		public void testOpenAccountForNonExistingCustomer() {
			Account account = bank.openAccount(null, "A0356", "Savings", 1000);
		    assertNull(account);
		}
		
		@Test
		@DisplayName("Close an existing account")
		public void testCloseExistingAccount() {
		    bank.addCustomer(Ali);
		    Account account = bank.openAccount(Ali, "A0356", "Savings", 1000);
		    boolean closed = bank.closeAccount(Ali, account);
		    assertTrue(closed);
		}

		@Test
		@DisplayName("Close a non-existing account")
		public void testCloseNonExistingAccount() {
		    boolean closed = bank.closeAccount(Ali, null);
		    assertFalse(closed);
		}

		@Test
		@DisplayName("Transfer funds between two accounts successfully")
		public void testTransferFundsBetweenAccountsSuccessfully() {
		    bank.addCustomer(Ali);
		    bank.addCustomer(Korkor);
		    
		    Account senderAccount = bank.openAccount(Ali, "A0456", "Savings", 1000);
		    Account receiverAccount = bank.openAccount(Korkor, "A0476", "Savings", 1000);

		    boolean transferSuccess = bank.transfer(senderAccount, receiverAccount, 500);

		    assertTrue(transferSuccess);
		    assertEquals(500, senderAccount.getBalance());
		    assertEquals(1500, receiverAccount.getBalance());
		}
		
		@Test
		@DisplayName("Transfer funds from/to a non-existing account")
		public void testTransferFundsFromNonExistingAccount() {
		    Account ExistingAccount = new Account("Nonexistent Account", "1234567890", 1000);
		    
		    boolean transferSuccess = bank.transfer(ExistingAccount, null, 500);

		    assertFalse(transferSuccess);
		}

		@Test
		@DisplayName("Transfer funds with a negative amount")
		public void testTransferFundsWithNegativeAmount() {
			bank.addCustomer(Ali);
		    bank.addCustomer(Korkor);
		    
		    Account senderAccount = bank.openAccount(Ali, "A0456", "Savings", 1000);
		    Account receiverAccount = bank.openAccount(Korkor, "A0476", "Savings", 1000);

		    boolean transferSuccess = bank.transfer(senderAccount, receiverAccount, -500);
		    
		    assertFalse(transferSuccess);
		}

		@Test
		@DisplayName("Transfer funds with a zero amount (edge case)")
		public void testTransferFundsWithZeroAmount() {
			bank.addCustomer(Ali);
		    bank.addCustomer(Korkor);
		    
		    Account senderAccount = bank.openAccount(Ali, "A0456", "Savings", 1000);
		    Account receiverAccount = bank.openAccount(Korkor, "A0476", "Savings", 1000);

		    boolean transferSuccess = bank.transfer(senderAccount, receiverAccount, 0);
		    
		    assertFalse(transferSuccess);
		}

		@Test
		@DisplayName("Get transaction history of an existing account")
		public void testGetTransactionHistoryOfExistingAccount() {
		    bank.addCustomer(Ali);

		    Account account = bank.openAccount(Ali, "A0356", "Savings", 1000);
		    
		    account.deposit(1000);
		    
		    account.withdraw(500);

		    List<Transaction> transactionHistory = bank.getTransactionHistory(Ali, account);

		    assertEquals(2, transactionHistory.size());
		    assertEquals("Deposit", transactionHistory.get(0).getTransactionType());
		    assertEquals(1000, transactionHistory.get(0).getAmount());
		    assertEquals("Withdraw", transactionHistory.get(1).getTransactionType());
		    assertEquals(500, transactionHistory.get(1).getAmount());
		}

		@Test
		@DisplayName("Get transaction history of a non-existing account")
		public void testGetTransactionHistoryOfNonExistingAccount() {
		    List<Transaction> transactionHistory = bank.getTransactionHistory(Ali, null);

		    assertNull(transactionHistory);
		}

		@Test
		@DisplayName("Get transaction history of an account with no transactions")
		public void testGetTransactionHistoryOfAccountWithNoTransactions() {
		    bank.addCustomer(Ali);

		    Account account = bank.openAccount(Ali, "A0673", "Savings", 1000);

		    List<Transaction> transactionHistory = bank.getTransactionHistory(Ali, account);

		    assertTrue(transactionHistory.isEmpty());
		}
	}
	
	@Nested
    @DisplayName("Test Loan Management and Reminder")
    public class TestLoanManagementAndReminder {
		@Test
		@DisplayName("Manage repayment schedule for an active loan")
		public void testManageRepaymentScheduleForActiveLoan() {
			Loan loan = Loan.CreateLoan("L001", Ali, 10000, 0.05, 12, LocalDate.now(), LocalDate.now().plusMonths(12), "Personal");
			loan.setOutstandingBalance(5000);
			Payment payment = Payment.createPayment(LocalDate.now(), 1000, 200);
		    loan.makePayment(payment);
		    loan.setLoanStatus(Loan.LoanStatus.ACTIVE);
		    
		    bank.manageRepaymentSchedule(loan);

		    assertNotNull(loan.getPaymentSchedule());
		}

		@Test
		@DisplayName("Manage repayment schedule for a loan with pending payments")
		public void testManageRepaymentScheduleForLoanWithPendingPayments() {
			Loan loan = Loan.CreateLoan("L001", Ali, 10000, 0.05, 12, LocalDate.now(), LocalDate.now().plusMonths(12), "Personal");
			loan.setOutstandingBalance(5000);
			Payment payment = Payment.createPayment(LocalDate.now(), 1000, 200);
			payment.setPaymentStatus(Payment.PaymentStatus.PENDING);
		    loan.makePayment(payment);
		    
		    bank.manageRepaymentSchedule(loan);

		    assertNotNull(loan.getPaymentSchedule());
		}

		@Test
		@DisplayName("Manage repayment schedule for a loan with missed payments")
		public void testManageRepaymentScheduleForLoanWithMissedPayments() {	
			Loan loan = Loan.CreateLoan("L001", Ali, 10000, 0.05, 12, LocalDate.now(), LocalDate.now().plusMonths(12), "Personal");
			loan.setOutstandingBalance(5000);
			Payment payment = Payment.createPayment(LocalDate.now(), 1000, 200);
			payment.setPaymentStatus(Payment.PaymentStatus.MISSED);
		    loan.makePayment(payment);
		    
		    bank.manageRepaymentSchedule(loan);

		    assertNotNull(loan.getPaymentSchedule());
		}

		@Test
		@DisplayName("Manage repayment schedule for a loan with no payments")
		public void testManageRepaymentScheduleForLoanWithNoPayments() {
			Loan loan = Loan.CreateLoan("L001", Ali, 10000, 0.05, 12, LocalDate.now(), LocalDate.now().plusMonths(12), "Personal");
			loan.setOutstandingBalance(5000);
			Payment payment = Payment.createPayment(LocalDate.now(), 1000, 200);
			payment.setPaymentStatus(Payment.PaymentStatus.ERROR);
		    loan.makePayment(payment);
		    
		    bank.manageRepaymentSchedule(loan);

		    assertNotNull(loan.getPaymentSchedule());
		}

		@Test
		@DisplayName("Send reminder to a customer via email")
		public void testSendReminderToCustomerViaEmail() {
			Payment payment = Payment.createPayment(LocalDate.now(), 1000, 200);
			boolean reminder = bank.sendReminderToCustomer(Ali, payment);
			assertTrue(reminder);
		}
		
		@Test
		@DisplayName("Send reminder to a customer with empty email")
		public void testSendReminderToCustomerWithEmptyEmail() {
			Payment payment = Payment.createPayment(LocalDate.now(), 1000, 200);
			Ali.setEmailAddress("");
			boolean reminder = bank.sendReminderToCustomer(Ali, payment);
		    assertFalse(reminder);
		}
		
		@Test
		@DisplayName("Send reminder to a customer with empty phone number")
		public void testSendReminderToCustomerWithEmptyPhoneNumber() {
			Payment payment = Payment.createPayment(LocalDate.now(), 1000, 200);
			Ali.setPhoneNumber("");
			boolean reminder = bank.sendReminderToCustomer(Ali, payment);
		    assertFalse(reminder);
		}
		
		@Test
        @DisplayName("Track outstanding loan balances and statuses for active loans")
		public void testTrackOutstandingLoanBalancesAndStatusesForActiveLoans() {
			Loan loan = Loan.CreateLoan("L001", Ali, 10000, 0.05, 12, LocalDate.now(), LocalDate.now().plusMonths(12), "Personal");
			loan.setOutstandingBalance(5000);
			loan.setLoanStatus(LoanStatus.ACTIVE);
			bank.addLoan(loan);
			bank.trackOutstandingLoanBalancesAndStatuses();
			assertEquals(Loan.LoanStatus.ACTIVE, loan.getLoanStatus());
        }
		
		@Test
        @DisplayName("Track outstanding loan balances and statuses for defaulted loans")
		public void testTrackOutstandingLoanBalancesAndStatusesForDefaultedLoans() {
			Loan loan = Loan.CreateLoan("L002", Ali, 10000, 0.05, 12, LocalDate.now(), LocalDate.now().plusMonths(12), "Personal");
			loan.setOutstandingBalance(5000);
			loan.setLoanStatus(LoanStatus.DEFAULT);
			bank.addLoan(loan);
			bank.trackOutstandingLoanBalancesAndStatuses();
			assertEquals(Loan.LoanStatus.DEFAULT, loan.getLoanStatus());
        }
		
		@Test
        @DisplayName("Track outstanding loan balances and statuses for paid off loans")
		public void testTrackOutstandingLoanBalancesAndStatusesForPaidOFFLoans() {
			Loan loan = Loan.CreateLoan("L003", Ali, 10000, 0.05, 12, LocalDate.now(), LocalDate.now().plusMonths(12), "Personal");
			loan.setOutstandingBalance(5000);
			loan.setLoanStatus(LoanStatus.PAID_OFF);
			bank.addLoan(loan);
			bank.trackOutstandingLoanBalancesAndStatuses();
			assertEquals(Loan.LoanStatus.PAID_OFF, loan.getLoanStatus());
        }
	}
	
	@Test
	@DisplayName("Test string representation of Bank")
	public void testToString() {
		String Expected = "Bank{" +
                "BankName='" + bank.getBankName() + '\'' +
                ", BankAddress='" + bank.getBankAddress() + '\'' +
                ", Customers='" + bank.getCustomers() + '\'' +
                ", Loans='" + bank.getLoans() + '\'' +
                ", Transactions=" + bank.getTransactions() +
                '}';
		assertEquals(Expected, bank.toString());
	   }

	@AfterEach
	public void tearDown() {
		Ali = null;
		Korkor = null;
		bank = null;
	}
	
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

}
