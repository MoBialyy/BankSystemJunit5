package BankingSystem;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Loan {
	private String LoanId;
	private Customer Borrower;
	private double Amount;
	private double interestRate;
	private int Term;
	private LocalDate startDate;
	private LocalDate endDate;
	private String LoanType;
    private double outstandingBalance;
    List<Payment> paymentSchedule;
    private LoanStatus loanStatus;
    
    public enum LoanStatus {
        ACTIVE, PAID_OFF, DEFAULT
    }
	
	public Loan(String LID, Customer borrower, double amount, double intRate, int months, LocalDate sDate, LocalDate eDate, String Type) {
		LoanId = LID;
		Borrower = borrower;
		Amount = amount;
		interestRate = intRate;
		Term = months;
		startDate = sDate;
		endDate = eDate;
		LoanType = Type;
		paymentSchedule = new ArrayList<>();
		loanStatus = LoanStatus.ACTIVE;
	}
	
	public String getLoanId() {
	    return LoanId;
	}

	public Customer getBorrower() {
	    return Borrower;
	}

	public double getAmount() {
		return Amount;
	}

	public double getInterestRate() {
		return interestRate;
	}

	public int getTerm() {
	    return Term;
	}

	public LocalDate getStartDate() {
	    return startDate;
	}

	public LocalDate getEndDate() {
	    return endDate;
	}

	public String getLoanType() {
	    return LoanType;
	}
	
	public double getOutstandingBalance() {
	    return outstandingBalance;
	}
	
	public void setOutstandingBalance(double amount) {
	    outstandingBalance = amount;
	}
	
	public void setLoanStatus(LoanStatus status) {
	    loanStatus = status;
	}
	
	public LoanStatus getLoanStatus() {
	    return loanStatus;
	}
	
	public boolean isValidLoan() {
	    if (this.Amount <= 0) {
	    	System.out.println("Error... Amount cannot be Negative");
	        return false;
	    }
	    else if (this.interestRate <= 0) {
	    	System.out.println("Error... Interest cannot be Zero or Negative");
	        return false;
	    }
	    else if (this.Term <= 0) {
	    	System.out.println("Error... Term cannot be Negative");
	        return false;
	    }
	    else if (this.startDate == null || endDate == null) {
	    	System.out.println("Error... There must be Starting Date and Ending Date");
	        return false;
	    }
	    else if (this.endDate.isBefore(startDate)) {
	    	System.out.println("Error... Starting Date cannot be before Ending Date");
	        return false;
	    }
	    else if (this.Borrower == null) {
	    	System.out.println("Error... There must be a Borrower");
	        return false;
	    }
	    else if (this.LoanType == null || LoanType.isEmpty()) {
	    	System.out.println("Error... There must be a Loan Type");
	        return false;
	    }
	    
	    else {
	        return true;
	    }
	}
	
	public static Loan CreateLoan(String LID, Customer borrower, double amount, double intRate, int months, LocalDate sDate, LocalDate eDate, String Type) {
	    Loan newLoan = new Loan(LID, borrower, amount, intRate, months, sDate, eDate, Type);
	    if (!newLoan.isValidLoan()) {
	        return null;
	    } 
	    else {
	        return newLoan;
	    }
	}
	
	public double calculateInterest() {
		double totalInterest = Amount * interestRate * Term / 12.0;
		return totalInterest;
    }
	
	public double calculateTotalRepayment() {
		double totalRepayment = Amount + calculateInterest();
		return totalRepayment;
    }
	
	public List<Payment> generatePaymentSchedule() {
        double remainingBalance = outstandingBalance;
       	LocalDate currentDate = startDate;

       	while (currentDate.isBefore(endDate.minusMonths(1))) {
       		double interest = remainingBalance * interestRate / 12.0;
       		double principal = calculateTotalRepayment() - interest;
       		
        	if (remainingBalance < principal) {
       			principal = remainingBalance;
       			interest = calculateTotalRepayment() - principal;
       		}

       		Payment payment = new Payment(currentDate, principal, interest);
           	paymentSchedule.add(payment);

           	remainingBalance -= principal;

           	currentDate = currentDate.plusMonths(1);
       	}
       	Payment finalPayment = new Payment(endDate, remainingBalance, remainingBalance * interestRate / 12.0);
        paymentSchedule.add(finalPayment);
    	return paymentSchedule;
	}
	
	public List<Payment> getPaymentSchedule(){
		return paymentSchedule;
	}
	
	public LoanStatus checkLoanStatus(LocalDate currentDate) {
	    if (outstandingBalance <= 0) {
	        return LoanStatus.PAID_OFF;
	    } else if (currentDate.isAfter(endDate)) {
	        return LoanStatus.DEFAULT;
	    } else {
	        return LoanStatus.ACTIVE;
	    }
	}
	
	public boolean makePayment(Payment payment) {
	    if (!isValidPayment(payment)) {
	    	loanStatus = LoanStatus.DEFAULT;
	        return false;
	    }
	    else {
	    	outstandingBalance -= payment.getPrincipalAmount();
	    	if (outstandingBalance == 0) {
	    		loanStatus = LoanStatus.PAID_OFF;
	    		return true;
	    	}
	    	else {
	    		if (payment.getPrincipalAmount() < outstandingBalance) {
	    			return true;
	    		}
	    		else {
	    			return false;
	    		}
	    	}
	    }
	}
	
	private boolean isValidPayment(Payment payment) {
	    if (payment == null) {
	        System.out.println("Error... Payment cannot be NULL");
	        return false;
	    }

	    if (outstandingBalance < 0) {
	        System.out.println("Error... Outstanding Balance cannot be Negative");
	        return false;
	    }

	    if (loanStatus == LoanStatus.DEFAULT || loanStatus == LoanStatus.PAID_OFF) {
	        System.out.println("Error... Payment is Already Paid or Missed");
	        return false;
	    }

	    LocalDate paymentDate = payment.getPaymentDate();
	    if (paymentDate == null) {
	        System.out.println("Error... Payment Date cannot be NULL");
	        return false;
	    } else if (paymentDate.isBefore(startDate)) {
	        System.out.println("Error... Payment Date is Early");
	        return false;
	    } else if (paymentDate.isAfter(endDate)){
	        System.out.println("Error... Payment Date is Late");
	        return false;
	    }

	    double paymentAmount = payment.getPrincipalAmount();
	    if (paymentAmount < 0) {
	        System.out.println("Error... Payment Amount cannot be Negative");
	        return false;
	    } else if (paymentAmount == 0) {
	    	System.out.println("Error... no Payment has been done");
	    	return false;
	    } else if (paymentAmount > outstandingBalance) {
	        System.out.println("Error... Payment Amount cannot Exceed Outstanding Balance");
	        return false;
	    }

	    double interest = payment.getInterestAmount();
	    if (interest < 0) {
	        System.out.println("Error... Payment Interest cannot be Negative");
	        return false;
	    }

	    return true;
	}
	
	public String toString() {
	    return "Loan{" +
	            "LoanId='" + LoanId + '\'' +
	            ", Borrower='" + Borrower.getName() + '\'' +
	            ", Amount=" + Amount +
	            ", InterestRate=" + interestRate +
	            ", Term=" + Term +
	            ", StartDate=" + startDate +
	            ", EndDate=" + endDate +
	            ", LoanType='" + LoanType + '\'' +
	            ", LoanStatus=" + loanStatus +
	            '}';
	}
}

