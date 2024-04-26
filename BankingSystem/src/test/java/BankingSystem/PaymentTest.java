package BankingSystem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.time.LocalDate;

class PaymentTest {
	
	private Loan loan;
	private Payment payment;
	private Customer borrower;
	private LocalDate currDate;
	
	@BeforeEach
	public void setUp() {
		loan = new Loan("L001", borrower, 10000, 0.05, 12, LocalDate.of(2024, 1, 1), LocalDate.of(2025, 1, 1), "Debt consolidation loan");
		borrower = new Customer("Mohamed Bialy", "momo_bialy", "Helioplis", "30306190100022", "01553034244", "21p0200@eng.asu.edu.eg", "321321");
		currDate = LocalDate.now();
		payment = Payment.createPayment(currDate, 1000, 200);
	}
	
	@Test
	@DisplayName("Test Create Payment With Valid Data")
	public void testCreatePayment() {
		assertNotNull(payment);
		assertEquals(currDate, payment.getPaymentDate());
		assertEquals(1000, payment.getPrincipalAmount());
		assertEquals(200, payment.getInterestAmount());
	}
	
	@Nested
	@DisplayName("Test Create Payment With Invalid Data")
	public class TestCreateInvalidPaymentData {
		@Test
		@DisplayName("Test Negative principal amount")
		public void testNegativePrincipalAmount() {
			Payment payment = Payment.createPayment(currDate, -1000, 200);
			assertNull(payment);
		}
		
		@Test
		@DisplayName("Test Negative interest amount")
		public void testNegativeInterestAmount() {
			Payment payment = Payment.createPayment(currDate, 1000, -200);
			assertNull(payment);
		}
		
		@Test
		@DisplayName("Test Null payment date")
		public void testNullPaymentDate() {
			LocalDate paymentDate = null;
			Payment payment = Payment.createPayment(paymentDate, 1000, 200);
			assertNull(payment);
		}
	}
	
	@Nested
	@DisplayName("Test Setting Payment Status")
	public class TestSetPaymentStatus {
		@Test
		@DisplayName("Test Future payment date")
		public void testFuturePaymentDate() {
			LocalDate paymentDate = LocalDate.now().plusMonths(1); 
			Payment payment = new Payment(paymentDate, 1000, 200);
			payment.setPaymentStatus(payment);
			assertEquals(Payment.PaymentStatus.PENDING, payment.getPaymentStatus());
		}
		
		@Test
        @DisplayName("Test Past payment date")
        public void testPastPaymentDate() {
            LocalDate paymentDate = LocalDate.now().minusMonths(1);
            Payment payment = new Payment(paymentDate, 1000, 200);
            payment.setPaymentStatus(payment);
            assertEquals(Payment.PaymentStatus.LATE, payment.getPaymentStatus());
        }
		
		@Test
		@DisplayName("Test set Payment Status")
		public void testSetPaymentStatus() {
            Payment payment = new Payment(currDate, 1000, 200);
            payment.setPaymentStatus(Payment.PaymentStatus.MISSED);
            payment.setPaymentStatus(payment);
            assertEquals(Payment.PaymentStatus.MISSED, payment.getPaymentStatus());
		}
	}
	
	@Nested
	@DisplayName("Test Apply Payment With Different Scenarios")
	public class TestApplyPaymet {
		@Test 
		@DisplayName("Applying Valid Payment")
		public void testValidPayment() {
			loan.setOutstandingBalance(1000);
			payment.applyPayment(loan, currDate, 1000, 200);
			assertEquals(Payment.PaymentStatus.PAID, payment.getPaymentStatus());
		}
		
		@Test
		@DisplayName("Test Loan Null Check")
		public void testNullLoan() {
			Loan loan = null;
			Payment payment = Payment.createPayment(currDate, 1000, 200);
			payment.applyPayment(loan, currDate, 1000, 200);
			assertEquals(Payment.PaymentStatus.PENDING, payment.getPaymentStatus());
		}
		
		@Test
		@DisplayName("Valid Partial Payment: Apply a payment that covers only part of the outstanding balance.")
	    public void testValidPartialPayment() {
			Payment payment = Payment.createPayment(LocalDate.now(), 1000, 200);
	        loan.setOutstandingBalance(20000);
	        payment.applyPayment(loan, LocalDate.now(), 1000, 200);
	        assertEquals(Payment.PaymentStatus.PARTIAL, payment.getPaymentStatus());
	    }
		
		@Test
		@DisplayName("Test the Payment Amount Exceeding the Outstanding Balance")
		public void testPaymentAmountExceedingOutstandigBalance() {
	        loan.setOutstandingBalance(20000);
	        payment.applyPayment(loan, LocalDate.now(), 30000, 200);
	        assertEquals(Payment.PaymentStatus.ERROR, payment.getPaymentStatus());
		}
	}
	
	@Test
    @DisplayName("Test string representation of Payment")
    public void testToString() {
		String Expected = "Payment{" +
                "paymentDate=" + payment.getPaymentDate() +
                ", principalAmount=" + payment.getPrincipalAmount() +
                ", interestAmount=" + payment.getInterestAmount() +
                ", totalPaymentAmount=" + payment.getTotalPaymentAmount() +
                '}';
		assertEquals(Expected, payment.toString());
	}
	
	@AfterEach
	public void tearDown() {
		loan = null;
		borrower = null;
		payment = null;
		currDate = null;
	}
	
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

}
