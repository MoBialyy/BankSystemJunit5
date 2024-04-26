package BankingSystem;

import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

public class Account {

	private String AccountId;
	private String AccountType;
	private double Balance;
	private List<Transaction> Transactions;
	private LocalDateTime CurrentDateTime = LocalDateTime.now();
	
	public Account(String AID, String AccType, double balance) {
		AccountId = AID;
		AccountType = AccType;
		Balance = balance;
		Transactions = new ArrayList<>();
	}
	
	public String getAccountId() {
		return AccountId;
	}
	
	public String getAccountType() {
		return AccountType;
	}
	
	public double getBalance() {
		return Balance;
	}
	
	public void setBalance(double balance) {
		this.Balance = balance;
	}
	
	public List<Transaction> getTransactions() {
		return Transactions;
	}
	
	public static Account createAccount(Customer owner, String AID, String accountType, double initialBalance) {
		if (initialBalance < 0) {
			return null;
		}
		else {
			Account account = new Account(AID, accountType, initialBalance);
			owner.addAccount(account);
			return account;
		}
	}
	
	public void addTransaction(String type, double amount) {
        Transactions.add(new Transaction(getNextTransactionId(), type, amount, CurrentDateTime,""));
    }
    
    public int getNextTransactionId() {
        return Transactions.size() + 1;
    }
    
    public boolean deposit(double amount) {
        if (amount == Math.floor(amount) && !Double.isInfinite(amount) && !Double.isNaN(amount)) {
            if (amount > 0 && amount < 5000) {
                Balance += amount;
                addTransaction("Deposit", amount);
                return true;
            } else {
                System.out.println("Error... Invalid deposit amount.");
                return false;
            }
        } 
        else {
            System.out.println("Error... Deposit amount must be a whole number.");
            return false;
        }
    }

    public boolean withdraw(double amount) {
        if (amount == Math.floor(amount) && !Double.isInfinite(amount) && !Double.isNaN(amount)) {
            if (amount > 0 && amount <= Balance) {
                Balance -= amount;
                addTransaction("Withdraw", amount);
                return true;
            } 
            else {
                System.out.println("Error... Invalid withdrawal amount.");
                return false;
            }
        } else {
            System.out.println("Error... Withdrawal amount must be a whole number.");
            return false;
        }
    }

	public boolean TransferFunds(Account senderAccount, Account receiverAccount, double amount) {
        if (amount <= 0) {
            System.out.println("Error... Invalid transfer amount.");
            return false;
        }
        
        else if (senderAccount == receiverAccount) {
            System.out.println("Error... Cannot transfer funds to the same account.");
            return false;
        }
        
        else if (amount > senderAccount.getBalance()) {
            System.out.println("Error... Insufficient funds in sender account.");
            return false;
        }
        
        else if (senderAccount == null || receiverAccount == null) {
        	System.out.println("Error... There is no Account to Transfer to.");
        	return false;
        }
        else {
        	senderAccount.setBalance(senderAccount.getBalance() - amount);
        	senderAccount.addTransaction("Transfer to Account #" + receiverAccount.getAccountId(), amount);
        	receiverAccount.setBalance(receiverAccount.getBalance() + amount);
        	receiverAccount.addTransaction("Transfer from Account #" + senderAccount.getAccountId(), amount);
        	System.out.println("Funds transferred successfully.");
        	return true;
        }
    }

	public String toString() {
        return "Account{" +
                "AccountId=" + AccountId +
                ", AccountType='" + AccountType + '\'' +
                ", balance=" + Balance +
                '}';
    }
}
