package BankingSystem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDateTime;

class TransactionTest {

	private Transaction transaction;
    private Account senderAccount;
    private Account receiverAccount;
    private Customer sender;
    private Customer receiver;
    private LocalDateTime Current = LocalDateTime.now();

    @BeforeEach
    public void setUp() {
        sender = new Customer("Sender", "Ana_Sender", "Helioplis", "2750101000154", "01024640120", "sender@example.com", "123456");
        receiver = new Customer("Receiver", "Ana_Receiver", "Haram", "15548451156", "01060775333", "receiver@example.com", "654321");
        senderAccount = new Account("A123", "Checking", 1000);
        receiverAccount = new Account("A456", "Savings", 500);
        transaction = new Transaction(1, "Deposit", 500, Current, "Initial deposit");
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
    }
    
    @Nested
    @DisplayName("Get Transaction Details")
    public class GetTransactionDetails {
        @Test
        @DisplayName("Test getting the transaction ID")
        public void getTransactionId() {
            assertEquals(1, transaction.getTransactionId());
        }

        @Test
        @DisplayName("Test getting the transaction type")
        public void getTransactionType() {
            assertEquals("Deposit", transaction.getTransactionType());
        }

        @Test
        @DisplayName("Test getting the amount")
        public void getAmount() {
            assertEquals(500, transaction.getAmount());
        }

        @Test
        @DisplayName("Test getting the date and time")
        public void getDateTime() {
            assertNotNull(transaction.getDateTime());
        }

        @Test
        @DisplayName("Test getting the details")
        public void getDetails() {
            assertEquals("Initial deposit", transaction.getDetails());
        }

        @Test
        @DisplayName("Test getting the status")
        public void getStatus() {
            assertEquals(Transaction.TransactionStatus.PENDING, transaction.getStatus());
        }
    }
    
    @Test
    @DisplayName("Create Deposit Transaction")
    public void createDepositTransaction() {
        assertEquals(1, transaction.getTransactionId());
        assertEquals("Deposit", transaction.getTransactionType());
        assertEquals(500, transaction.getAmount());
        assertEquals("Initial deposit", transaction.getDetails());
        assertEquals(Transaction.TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    @DisplayName("Create Withdraw Transaction")
    public void createWithdrawTransaction() {
        transaction = new Transaction(2, "Withdraw", 200, Current, "Withdrawal");
        transaction.setSenderAccount(senderAccount);
        assertEquals(2, transaction.getTransactionId());
        assertEquals("Withdraw", transaction.getTransactionType());
        assertEquals(200, transaction.getAmount());
        assertEquals("Withdrawal", transaction.getDetails());
        assertEquals(Transaction.TransactionStatus.PENDING, transaction.getStatus());
    }

    @Test
    @DisplayName("Create Transfer Transaction")
    public void createTransferTransaction() {
        transaction = new Transaction(3, "Transfer", 300, Current, "Transfer to receiver");
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        assertEquals(3, transaction.getTransactionId());
        assertEquals("Transfer", transaction.getTransactionType());
        assertEquals(300, transaction.getAmount());
        assertEquals("Transfer to receiver", transaction.getDetails());
        assertEquals(Transaction.TransactionStatus.PENDING, transaction.getStatus());
    }
    
    @Nested
    @DisplayName("Create Transaction with Invalid Details")
    public class InvalidTransactionDetails {
        @Test
        @DisplayName("Test creating transaction with invalid type")
        public void createTransactionWithInvalidType() {
            transaction = new Transaction(4, "Invalid Type", 500, Current, "Invalid Type");
            assertFalse(transaction.isValidateType());
            assertFalse(transaction.isValidateTransaction());
        }

        @Test
        @DisplayName("Test creating transaction with negative amount")
        public void createTransactionWithNegativeAmount() {
            transaction = new Transaction(5, "Transfer", -500, Current, "Negative Amount");
            assertFalse(transaction.isValidateTransaction());
        }

        @Test
        @DisplayName("Test creating transaction with null sender or receiver account")
        public void createTransactionWithNullSenderAccount() {
            transaction = new Transaction(6, "Withdraw", 200, Current, "Null sender or receiver");
            transaction.setSenderAccount(null);
            assertFalse(transaction.isValidateTransaction());
        }
    }
    
    @Nested
    @DisplayName("Process Transactions Successfully")
    public class ProcessTransactions {
        @Test
        @DisplayName("Test processing a deposit transaction successfully")
        public void processDepositTransactionSuccessfully() {
            transaction = new Transaction(7, "Deposit", 500, Current, "Initial deposit");
            transaction.setSenderAccount(senderAccount);
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.COMPLETED, transaction.getStatus());
            assertEquals(1500, senderAccount.getBalance());
        }

        @Test
        @DisplayName("Test processing a withdraw transaction successfully")
        public void processWithdrawTransactionSuccessfully() {
            transaction = new Transaction(8, "Withdraw", 200, Current, "Withdrawal");
            transaction.setSenderAccount(senderAccount);
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.COMPLETED, transaction.getStatus());
            assertEquals(800, senderAccount.getBalance());
        }

        @Test
        @DisplayName("Test processing a transfer transaction successfully")
        public void processTransferTransactionSuccessfully() {
            transaction = new Transaction(9, "Transfer", 300, Current, "Transfer to receiver");
            transaction.setSenderAccount(senderAccount);
            transaction.setReceiverAccount(receiverAccount);
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.COMPLETED, transaction.getStatus());
            assertEquals(700, senderAccount.getBalance());
            assertEquals(800, receiverAccount.getBalance());
        }
    }
    
    @Test
    @DisplayName("Create Transaction with Future Date and Time")
    public void createTransactionWithFutureDateTime() {
        LocalDateTime futureDateTime = LocalDateTime.now().plusDays(1);
        transaction = new Transaction(10, "Deposit", 500, futureDateTime, "Initial deposit");
        transaction.setSenderAccount(senderAccount);
        assertEquals(10, transaction.getTransactionId());
        assertEquals("Deposit", transaction.getTransactionType());
        assertEquals(500, transaction.getAmount());
        assertEquals("Initial deposit", transaction.getDetails());
        assertEquals(Transaction.TransactionStatus.PENDING, transaction.getStatus());
    }
    
    @Test
    @DisplayName("Process Transfer Transaction Between Different Customers")
    public void processTransferBetweenDifferentCustomers() {
        transaction = new Transaction(11, "Transfer", 300, Current, "Transfer to receiver");
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        assertNotSame(sender, receiver);
        transaction.processTransaction();
        assertEquals(Transaction.TransactionStatus.COMPLETED, transaction.getStatus());
        assertEquals(700, senderAccount.getBalance());
        assertEquals(800, receiverAccount.getBalance());
    }
    
    @Test
    @DisplayName("Process Custom Transaction with Additional Validation Rules")
    public void processCustomTransactionWithValidationRules() {
        class CustomTransaction extends Transaction {
            public CustomTransaction(double TID, String type, double amount, LocalDateTime dateTime, String details) {
                super(TID, type, amount, dateTime, details);
            }

            @Override
            public boolean isValidateTransaction() {
                if (getAmount() > 1000) {
                    return false;
                }
                return super.isValidateTransaction();
            }
        }
        transaction = new CustomTransaction(12, "CustomType", 1500, Current, "Custom transaction");
        transaction.setSenderAccount(senderAccount);
        transaction.setReceiverAccount(receiverAccount);
        transaction.processTransaction();
        assertEquals(Transaction.TransactionStatus.FAILED, transaction.getStatus());
    }
    
    @Nested
    @DisplayName("Process Transaction Scenarios")
    public class ProcessTransactionScenarios {
    	@Test
        @DisplayName("Test processing a transaction with insufficient funds for a deposit")
        public void processTransactionDepositWithInsufficientFunds() {
            transaction = new Transaction(23, "Deposit", 6200, Current, "Withdrawal");
            transaction.setSenderAccount(senderAccount);
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.FAILED, transaction.getStatus());
        }
    	
        @Test
        @DisplayName("Test processing a transaction with insufficient funds for a withdraw")
        public void processTransactionWithdrawWithInsufficientFunds() {
            transaction = new Transaction(13, "Withdraw", 1200, Current, "Withdrawal");
            transaction.setSenderAccount(senderAccount);
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.FAILED, transaction.getStatus());
        }

        @Test
        @DisplayName("Test processing a transfer transaction where the sender and receiver accounts are the same")
        public void processTransferWithSameAccounts() {
            transaction = new Transaction(14, "Transfer", 300, Current, "Transfer to same account");
            transaction.setSenderAccount(senderAccount);
            transaction.setReceiverAccount(senderAccount);
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.FAILED, transaction.getStatus());
        }

        @Test
        @DisplayName("Test processing a transfer transaction where the receiver account does not exist")
        public void processTransferWithNonExistingReceiver() {
            transaction = new Transaction(15, "Transfer", 500, Current, "Transfer to non-existing receiver");
            transaction.setSenderAccount(senderAccount);
            // Receiver account is not set intentionally to simulate non-existing receiver
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.FAILED, transaction.getStatus());
        }

        @Test
        @DisplayName("Test processing a transfer transaction where the sender account does not exist")
        public void processTransferWithNonExistingSender() {
            transaction = new Transaction(16, "Transfer", 200, Current, "Transfer from non-existing sender");
            // Sender account is not set intentionally to simulate non-existing sender
            transaction.setReceiverAccount(receiverAccount);
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.FAILED, transaction.getStatus());
        }

        @Test
        @DisplayName("Test processing a transfer transaction where the sender account is null")
        public void processTransferWithNullSenderAccount() {
            transaction = new Transaction(17, "Transfer", 150, Current, "Transfer with null sender account");
            // Sender account is intentionally set to null
            transaction.setReceiverAccount(receiverAccount);
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.FAILED, transaction.getStatus());
        }

        @Test
        @DisplayName("Test processing a transfer transaction where the receiver account is null")
        public void processTransferWithNullReceiverAccount() {
            transaction = new Transaction(18, "Transfer", 200, Current, "Transfer with null receiver account");
            transaction.setSenderAccount(senderAccount);
            // Receiver account is intentionally set to null
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.FAILED, transaction.getStatus());
        }

        @Test
        @DisplayName("Test processing a transfer transaction where both sender and receiver accounts are null")
        public void processTransferWithNullAccountsBoth() {
            transaction = new Transaction(19, "Transfer", 250, Current, "Transfer with both null accounts");
            transaction.processTransaction();
            assertEquals(Transaction.TransactionStatus.FAILED, transaction.getStatus());
        }
    }
    
    @Test
    @DisplayName("Test string representation of Transaction")
    public void testTransactionToString() {
        String expected = "Transaction{" +
                "transactionId=" + transaction.getTransactionId() +
                ", type='" + transaction.getTransactionType() + '\'' +
                ", amount=" + transaction.getAmount() +
                ", dateTime=" + transaction.getDateTime() +
                ", details='" + transaction.getDetails() + '\'' +
                '}';
        assertEquals(expected, transaction.toString());
    }
    
    @AfterEach
    public void tearDown() {
    	sender = null;
        receiver = null;
        senderAccount = null;
        receiverAccount = null;
    }
    
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

}
