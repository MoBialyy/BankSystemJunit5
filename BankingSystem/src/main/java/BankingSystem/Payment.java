package BankingSystem;

import java.time.LocalDate;


public class Payment {
    private LocalDate PaymentDate;
    private double PrincipalAmount;
    private double InterestAmount;
    private static PaymentStatus paymentStatus;
    
    public enum PaymentStatus {
        PENDING, PAID, MISSED, LATE, PARTIAL, DEFAULT, ERROR
    }

    public Payment(LocalDate paymentDate, double principalAmount, double interestAmount) {
        PaymentDate = paymentDate;
        PrincipalAmount = principalAmount;
        InterestAmount = interestAmount;
        paymentStatus = PaymentStatus.PENDING;
    }
    
    public LocalDate getPaymentDate() {
        return PaymentDate;
    }
    
    public double getPrincipalAmount() {
        return PrincipalAmount;
    }
    
    public double getInterestAmount() {
        return InterestAmount;
    }

    public double getTotalPaymentAmount() {
        return PrincipalAmount + InterestAmount;
    }
    
    public void setPaymentStatus(PaymentStatus status) {
    	paymentStatus = status;
    }
    
    public PaymentStatus getPaymentStatus() {
    	return paymentStatus;
    }
    
    public static Payment createPayment(LocalDate paymentDate, double principalAmount, double interestAmount) {
    	if (paymentDate == null) {
    		System.out.println("Error... Invalid Payment Date");
			return null;
		} else if (principalAmount < 0) {
			System.out.println("Error... Invalid Principal Amount");
			return null;
		} else if (interestAmount < 0) {
			System.out.println("Error... Invalid Interest Amount");
			return null;
		}
		else {
			Payment payment = new Payment(paymentDate, principalAmount, interestAmount);
			paymentStatus = PaymentStatus.PENDING;
			return payment;
		}
    }
    
    public void setPaymentStatus(Payment payment) {
        if (payment.getPaymentStatus() == PaymentStatus.PENDING) {
            if (payment.getPaymentDate().isAfter(LocalDate.now())) {
                paymentStatus = PaymentStatus.PENDING;
            } else {
                paymentStatus = PaymentStatus.LATE;
            }
        }
        else {
            paymentStatus = payment.getPaymentStatus();
        }
        System.out.println("Payment status: " + paymentStatus);
    }
    
    public void applyPayment(Loan loan, LocalDate paymentDate, double principalAmount, double interestAmount) {
    	if (loan != null) {
    		Payment payment = new Payment(paymentDate, principalAmount, interestAmount);
    		
    		if (loan.makePayment(payment)) {
    			if (loan.getLoanStatus() == Loan.LoanStatus.PAID_OFF) {
    				paymentStatus = PaymentStatus.PAID;
    			}
    			else {
    				paymentStatus = PaymentStatus.PARTIAL;
    			}
    			System.out.println("Payment status: " + paymentStatus);
    		} 
    		else {
    			System.out.println("Error applying payment to loan");
    			paymentStatus = PaymentStatus.ERROR;
    		}	
        }
    	else {
    		System.out.println("Error... Invalid loan");
    	}
    }

    public String toString() {
        return "Payment{" +
                "paymentDate=" + PaymentDate +
                ", principalAmount=" + PrincipalAmount +
                ", interestAmount=" + InterestAmount +
                ", totalPaymentAmount=" + getTotalPaymentAmount() +
                '}';
    }
}
