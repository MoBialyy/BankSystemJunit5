package BankingSystem;

import java.time.LocalDateTime;

public class Transaction {
	private double TransactionId;
	private String TransactionType;
	private double Amount;
	private LocalDateTime DateTime;
	private String Details;
    private Account senderAccount;
    private Account receiverAccount;
    private TransactionStatus status;

    public enum TransactionStatus {
        PENDING, COMPLETED, FAILED
    }
    
	
	public Transaction(double TID, String type, double amount, LocalDateTime dateTime, String details) {
		TransactionId = TID;
        TransactionType = type;
        Amount = amount;
        DateTime = dateTime;
        Details = details;
        status = TransactionStatus.PENDING;
    }

	public double getTransactionId() {
        return TransactionId;
    }

    public String getTransactionType() {
        return TransactionType;
    }

    public double getAmount() {
        return Amount;
    }

    public LocalDateTime getDateTime() {
        return DateTime;
    }
    
    public String getDetails() {
        return Details;
    }

    public void setDetails(String details) {
        this.Details = details;
    }
    
    public TransactionStatus getStatus() {
    	return status;
    }

	public void setStatus(TransactionStatus status) {
		this.status = status;
	}

    public Account getSenderAccount() {
        return senderAccount;
    }

    public void setSenderAccount(Account senderAccount) {
        this.senderAccount = senderAccount;
    }

    public Account getReceiverAccount() {
        return receiverAccount;
    }

    public void setReceiverAccount(Account receiverAccount) {
        this.receiverAccount = receiverAccount;
    }
    
    public boolean isValidateType() {
        return TransactionType.equals("Deposit") || TransactionType.equals("Withdraw") || TransactionType.equals("Transfer");
    }
    
    public boolean isValidateTransaction() {
        if (!isValidateType()) {
            return false;
        }

        else if (Amount <= 0) {
            return false;
        }

        else if (senderAccount == null) {
            return false;
        }
        
        else if (TransactionType.equals("Transfer") && receiverAccount == null) {
            return false;
        }
        
        return true;
    }
    
    public void processTransaction() {
        if (isValidateTransaction()) {
            if (TransactionType.equals("Deposit")) {
                if (senderAccount.deposit(Amount)) {
                	setStatus(TransactionStatus.COMPLETED);
                }
                else {
                	setStatus(TransactionStatus.FAILED);
                }
            } 
            else if (TransactionType.equals("Withdraw")) {
                if (senderAccount.withdraw(Amount)) {
                	setStatus(TransactionStatus.COMPLETED);
                }
                else {
                	setStatus(TransactionStatus.FAILED);
                }
            } 
            else if (TransactionType.equals("Transfer")) {
                if (senderAccount.TransferFunds(senderAccount, receiverAccount, Amount)) {
                	setStatus(TransactionStatus.COMPLETED);
                }
                else {
                	setStatus(TransactionStatus.FAILED);
                }
            }
        } 
        else {
            setStatus(TransactionStatus.FAILED);
        }
    }
    
    public String toString() {
        return "Transaction{" +
                "transactionId=" + TransactionId +
                ", type='" + TransactionType + '\'' +
                ", amount=" + Amount +
                ", dateTime=" + DateTime +
                ", details='" + Details + '\'' +
                '}';
    }
}
