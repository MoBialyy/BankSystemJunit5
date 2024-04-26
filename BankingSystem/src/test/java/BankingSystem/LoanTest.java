package BankingSystem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.util.List;
import java.time.LocalDate;

class LoanTest {

	private Customer borrower;
    private LocalDate startDate;
    private LocalDate endDate;

    @BeforeEach
    public void setUp() {
        borrower = new Customer("Korkor", "luca_korkor", "nozha", "30309080100081", "01157758181", "21p0065@eng.asu.edu.eg", "123456");
        startDate = LocalDate.of(2023, 1, 1);
        endDate = LocalDate.of(2024, 1, 1);
    }
    
    @Test
    @DisplayName("Test Loan Getters")
    public void testLoanGetters() {
        Loan loan = new Loan("L001", borrower, 10000, 0.05, 12, startDate, endDate, "Debt consolidation loan");

        assertEquals("L001", loan.getLoanId());
        assertEquals(borrower, loan.getBorrower());
        assertEquals(10000, loan.getAmount());
        assertEquals(0.05, loan.getInterestRate());
        assertEquals(12, loan.getTerm());
        assertEquals(startDate, loan.getStartDate());
        assertEquals(endDate, loan.getEndDate());
        assertEquals("Debt consolidation loan", loan.getLoanType());
        assertEquals(Loan.LoanStatus.ACTIVE, loan.getLoanStatus());
    }

    @Nested
    @DisplayName("Loan Creation Tests")
    public class LoanCreationTests {

        @Test
        @DisplayName("Test creating a loan with valid parameters")
        public void testValidLoanCreation() {
            Loan loan = Loan.CreateLoan("L002", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            assertNotNull(loan);
        }

        @Test
        @DisplayName("Test creating a loan with a negative loan amount")
        public void testNegativeLoanAmount() {
        	Loan loan = Loan.CreateLoan("L003", borrower, -10000, 0.05, 12, startDate, endDate, "Mortgage");
        	assertNull(loan);
        }

        @Test
        @DisplayName("Test creating a loan with a negative interest rate")
        public void testNegativeInterestRate() {
        	Loan loan = Loan.CreateLoan("L004", borrower, 10000, -0.05, 12, startDate, endDate, "Home equity loan");
        	assertNull(loan);
        }

        @Test
        @DisplayName("Test creating a loan with a negative term")
        public void testNegativeTerm() {
        	Loan loan = Loan.CreateLoan("L005", borrower, 10000, 0.05, -12, startDate, endDate, "Auto loan");
        	assertNull(loan);
        }
        
        @Test
        @DisplayName("Test creating for Null Start Date and End Date")
        public void testNullStartDateAndEndDate() {
        	LocalDate NullStartDate = null;
        	LocalDate NullEndDate = null;
        	Loan loan = Loan.CreateLoan("L024", borrower, 1000, 0.05, 12, NullStartDate, NullEndDate, "Auto Loan");
            assertNull(loan);
        }
        
        @Test
        @DisplayName("Test creating a loan with null Borrower")
        public void testNullBorrower() {
        	Loan loan = Loan.CreateLoan("L025", null, 1000, 0.05, 12, startDate, endDate, "Mortage");
        	assertNull(loan);
        }
        
        @Test
        @DisplayName("Test creating a loan with null Type")
        public void testNullType() {
        	Loan loan = Loan.CreateLoan("L026", borrower, 1000, 0.05, 12, startDate, endDate, null);
        	assertNull(loan);
        }
        
        @Test
        @DisplayName("Test creating a loan with Empty Type")
        public void testEmptyType() {
        	Loan loan = Loan.CreateLoan("L026", borrower, 1000, 0.05, 12, startDate, endDate, "");
        	assertNull(loan);
        }

        @Test
        @DisplayName("Test creating a loan with a start date after the end date")
        public void testStartDateAfterEndDate() {
        	startDate = LocalDate.of(2024, 1, 1);
            endDate = LocalDate.of(2023, 1, 1);
        	Loan loan = Loan.CreateLoan("L006", borrower, 10000, 0.05, 12, startDate, endDate, "Credit builder loan");
        	assertNull(loan);
        }

        @Test
        @DisplayName("Test creating a loan with a start date in the past and an end date in the future")
        public void testStartDateInPastAndEndDateInFuture() {
        	startDate = LocalDate.of(2024, 1, 1);
            endDate = LocalDate.of(2025, 1, 1);
            Loan loan = Loan.CreateLoan("L007", borrower, 10000, 0.05, 12, startDate, endDate, "Student loan");
            assertNotNull(loan);
        }
    }
    
    @Nested
    @DisplayName("Loan Calculation Tests")
    public class LoanCalculationTests {

        @Test
        @DisplayName("Test calculating interest for the loan")
        void testInterestCalculation() {
        	Loan loan = Loan.CreateLoan("L008", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            double expectedInterest = 500; // 10000 * 0.05 * (12/12) = 500
            assertEquals(expectedInterest, loan.calculateInterest());
        }

        @Test
        @DisplayName("Test calculating total repayment for the loan")
        public void testTotalRepaymentCalculation() {
        	Loan loan = Loan.CreateLoan("L009", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            double expectedTotalRepayment = 10500.0; // 10000 + 500 (interest)
            assertEquals(expectedTotalRepayment, loan.calculateTotalRepayment());
        }
    }
	
    @Test
    @DisplayName("Test generating a payment schedule for the loan")
    public void testPaymentScheduleGeneration() {
    	Loan loan = Loan.CreateLoan("L010", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");

        List<Payment> paymentSchedule = loan.generatePaymentSchedule();
        assertNotNull(paymentSchedule);
        assertEquals(12, paymentSchedule.size());

        for (Payment payment : paymentSchedule) {
            assertTrue(payment.getPrincipalAmount() >= 0);
            assertTrue(payment.getInterestAmount() >= 0);
        }
        Payment lastPayment = paymentSchedule.get(paymentSchedule.size() - 1);
        assertEquals(0, lastPayment.getPrincipalAmount());
    }
    
    @Nested
    @DisplayName("Loan Status Tests")
    public class LoanStatusTests {

        @Test
        @DisplayName("Test checking the loan status when the outstanding balance is zero")
        public void testLoanStatusWithZeroOutstandingBalance() {
        	Loan loan = Loan.CreateLoan("L011", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(0);
            assertEquals(Loan.LoanStatus.PAID_OFF, loan.checkLoanStatus(startDate));
        }

        @Test
        @DisplayName("Test checking the loan status when the outstanding balance is positive and before the end date")
        public void testLoanStatusWithPositiveOutstandingBalanceBeforeEndDate() {
        	Loan loan = Loan.CreateLoan("L012", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(5000);
            LocalDate currentDate = LocalDate.of(2022, 6, 1);
            assertEquals(Loan.LoanStatus.ACTIVE, loan.checkLoanStatus(currentDate));
        }

        @Test
        @DisplayName("Test checking the loan status when the outstanding balance is positive and after the end date")
        public void testLoanStatusWithPositiveOutstandingBalanceAfterEndDate() {
        	Loan loan = Loan.CreateLoan("L013", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(5000);
            LocalDate currentDate = LocalDate.of(2024, 2, 1);
            assertEquals(Loan.LoanStatus.DEFAULT, loan.checkLoanStatus(currentDate));
        }
    }
    
    @Nested
    @DisplayName("Loan Payment Tests")
    public class LoanPaymentTests {

        @Test
        @DisplayName("Test making a payment that fully pays off the loan")
        public void testFullPayment() {
        	Loan loan = Loan.CreateLoan("L014", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(10000);
            Payment payment = new Payment(startDate, 10000, 0.05);
            loan.makePayment(payment);
            assertEquals(Loan.LoanStatus.PAID_OFF, loan.getLoanStatus(), "Loan status should be PAID_OFF");
            assertEquals(0, loan.getOutstandingBalance());
        }

        @Test
        @DisplayName("Test making a payment that partially pays off the loan")
        public void testPartialPayment() {
        	Loan loan = Loan.CreateLoan("L015", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(10000);
            Payment payment = new Payment(startDate, 5000, 0.05);
            loan.makePayment(payment);
            assertEquals(5000, loan.getOutstandingBalance());
        }

        @Test
        @DisplayName("Test making a payment with a negative payment amount")
        public void testNegativePaymentAmount() {
        	Loan loan = Loan.CreateLoan("L016", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(10000);
            Payment payment = new Payment(startDate, -10000, 0.05);
            loan.makePayment(payment);
            assertNotEquals(11000, loan.getOutstandingBalance());
        }
        
        @Test
        @DisplayName("Test making a payment with a Null payment amount")
        public void testNullPaymentAmount() {
        	Loan loan = Loan.CreateLoan("L017", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(10000);
            assertFalse(loan.makePayment(null));
        }

        @Test
        @DisplayName("Test making a payment with a zero payment amount")
        public void testZeroPaymentAmount() {
        	Loan loan = Loan.CreateLoan("L017", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(10000);
            Payment payment = new Payment(startDate, 0, 0.05);
            loan.makePayment(payment);
            assertEquals(10000, loan.getOutstandingBalance());
        }
        
        @Test
        @DisplayName("Test making a payment date is null")
        public void testNullPaymentDate() {
        	Loan loan = Loan.CreateLoan("L028", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(loan.calculateTotalRepayment());
            Payment payment = new Payment(null, 1000, 0.05);
            loan.makePayment(payment);
            assertFalse(loan.makePayment(payment));
        }
        
        @Test
        @DisplayName("Test making a payment date is before loan start date")
        public void testEarlyPaymentDate() {
        	Loan loan = Loan.CreateLoan("L028", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(loan.calculateTotalRepayment());
            LocalDate EarlyDate = startDate.minusMonths(1);
            Payment payment = new Payment(EarlyDate, 1000, 0.05);
            loan.makePayment(payment);
            assertFalse(loan.makePayment(payment));
        }
        
        @Test
        @DisplayName("Test making a payment date is after loan end date")
        public void testLatePaymentDate() {
        	Loan loan = Loan.CreateLoan("L028", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(loan.calculateTotalRepayment());
            LocalDate LateDate = endDate.plusMonths(1);
            Payment payment = new Payment(LateDate, 1000, 0.05);
            loan.makePayment(payment);
            assertFalse(loan.makePayment(payment));
        }

        @Test
        @DisplayName("Test making multiple payments until the loan is paid off")
        public void testMultiplePaymentsUntilPaidOff() {
        	Loan loan = Loan.CreateLoan("L018", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(10000);
            Payment payment1 = new Payment(startDate, 5000, 0.05);
            loan.makePayment(payment1);
            assertEquals(5000, loan.getOutstandingBalance(), "Outstanding balance should be 5000 after first payment");
            Payment payment2 = new Payment(startDate, 5000, 0.05);
            loan.makePayment(payment2);
            assertEquals(0, loan.getOutstandingBalance());
            assertEquals(Loan.LoanStatus.PAID_OFF, loan.getLoanStatus());
        }

        @Test
        @DisplayName("Test making a payment that exceeds the outstanding balance")
        public void testPaymentExceedsBalance() {
        	Loan loan = Loan.CreateLoan("L019", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(5000);
            Payment payment = new Payment(startDate, 6000, 0.05);
            loan.makePayment(payment);
            assertEquals(5000, loan.getOutstandingBalance());
        }

        @Test
        @DisplayName("Test making a payment when the loan has already been paid off")
        public void testPaymentWhenLoanPaidOff() {
        	Loan loan = Loan.CreateLoan("L020", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(0);
            loan.setLoanStatus(Loan.LoanStatus.PAID_OFF);
            Payment payment = new Payment(startDate, 6000, 0.05);
            loan.makePayment(payment);
            assertEquals(6000, payment.getPrincipalAmount());
            assertEquals(Loan.LoanStatus.DEFAULT, loan.getLoanStatus());
        }

        @Test
        @DisplayName("Test making a payment when the loan is already in default")
        public void testPaymentWhenLoanInDefault() {
        	Loan loan = Loan.CreateLoan("L021", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(5000);
            loan.setLoanStatus(Loan.LoanStatus.DEFAULT);
            Payment payment = new Payment(startDate, 5000, 0.05);
            loan.makePayment(payment);
            assertEquals(5000, loan.getOutstandingBalance());
            assertEquals(Loan.LoanStatus.DEFAULT, loan.getLoanStatus());
        }

        @Test
        @DisplayName("Test making a payment when the loan has a negative outstanding balance")
        public void testPaymentWithNegativeOutstandingBalance() {
        	Loan loan = Loan.CreateLoan("L022", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
            loan.setOutstandingBalance(-5000);
            Payment payment = new Payment(startDate, 5000, 0.05);
            loan.makePayment(payment);
            assertEquals(Loan.LoanStatus.DEFAULT, loan.getLoanStatus());
        }
        
        @Test
        @DisplayName("Test making a payment with negative interest")
        public void testNegIntPayAmount() {
        	Loan loan = Loan.CreateLoan("L022", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
        	loan.setOutstandingBalance(loan.calculateTotalRepayment());
        	Payment payment = new Payment(startDate, 5000, -0.05);
        	assertFalse(loan.makePayment(payment));
        }
    }
    
    @Test
    @DisplayName("Test string representation of Loan")
    public void testToString() {
    	Loan loan = Loan.CreateLoan("L023", borrower, 10000, 0.05, 12, startDate, endDate, "Personal");
    	String Expected = "Loan{" +
	            "LoanId='" + loan.getLoanId() + '\'' +
	            ", Borrower='" + borrower.getName() + '\'' +
	            ", Amount=" + loan.getAmount() +
	            ", InterestRate=" + loan.getInterestRate() +
	            ", Term=" + loan.getTerm() +
	            ", StartDate=" + loan.getStartDate() +
	            ", EndDate=" + loan.getEndDate() +
	            ", LoanType='" + loan.getLoanType() + '\'' +
	            ", LoanStatus=" + loan.getLoanStatus() +
	            '}';
    	assertEquals(Expected, loan.toString());
    }
    
    @AfterEach
    void tearDown() {
    	borrower = null;
        startDate = null;
        endDate = null;
    }
    
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

}
