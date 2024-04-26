package BankingSystem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.time.LocalDate;

public class Bank {
    private String bankName;
    private String bankAddress;
    private Map<String, String> authentication; 
    private List<Customer> customers;
    private List<Loan> loans;
    private List<Transaction> transactions;

    public Bank(String bankName, String bankAddress) {
        this.bankName = bankName;
        this.bankAddress = bankAddress;
        this.authentication = new HashMap<>();
        this.customers = new ArrayList<>();
        this.loans = new ArrayList<>();
        this.transactions = new ArrayList<>();
    }
    
    public void setBankName(String bankName) {
    	if (bankName == null) {
    		System.out.println("Error... Bank Name cannot be Null");
    		this.bankName = null;
    	}
    	else if (bankName.isEmpty()) {
    		System.out.println("Error... Bank Name cannot be Empty");
    		this.bankName = null;
    	}
    	else {
    		this.bankName = bankName;
    	}
    }
    
    public String getBankName() {
    	return bankName;
    }

    public void setBankAddress(String bankAddress) {
    	if (bankAddress == null) {
    		System.out.println("Error... Bank Address cannot be Null");
    		this.bankAddress = null;
    	}
    	else if (bankAddress.isEmpty()) {
    		System.out.println("Error... Bank Address cannot be Empty");
    		this.bankAddress = null;
    	}
    	else {
    		this.bankAddress = bankAddress;
    	}
    }
    
    public String getBankAddress() {
    	return bankAddress;
    }
    
    public void Login(String customerId, String password) {
        authentication.put(customerId, password);
    }

    public boolean authenticateUser(String customerId, String password) {
        String storedPassword = authentication.get(customerId);
        if (storedPassword != null) {
            if (storedPassword.equals(password)) {
                return true;
            }
        }
        return false;
    }
    
    public void addCustomer(Customer customer) {
    	if (customer.getAddress() == null || customer.getCustomerId() == null || customer.getEmail() == null
    		|| customer.getName() == null || customer.getNationalId() == null || customer.getPassword() == null
    		|| customer.getPhoneNumber() == null) {
    	}
    	else {
    		customers.add(customer);
    	}
    }
    
    public boolean removeCustomer(Customer customer) {
    	if (customer == null) {
    		return false;
    	}
    	else {
    		customers.remove(customer);
    		return true;
    	}
    }
    
    public void modifyCustomerDetails(Customer customer, String name, String address,String nationalId, String phoneNumber, String emailAddress) {
        customer.setName(name);
        customer.setAddress(address);
        customer.setNationalId(nationalId);
        customer.setPhoneNumber(phoneNumber);
        customer.setEmailAddress(emailAddress);
    }

    public List<Customer> searchCustomersByName(String name) {
        List<Customer> foundCustomers = new ArrayList<>();
        for (Customer customer : customers) {
            if (customer.getName().equalsIgnoreCase(name)) {
                foundCustomers.add(customer);
            }
        }
        return foundCustomers;
    }

    public Customer searchCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getCustomerId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
    
    public List<Customer> getCustomers() {
    	return customers;
    }
    
    public List<Loan> getLoans(){
    	return loans;
    }
    
    public List<Transaction> getTransactions(){
    	return transactions;
    }
    
    public Account openAccount(Customer customer, String accountId, String accountType, double initialBalance) {
        if (customer == null || accountId.isEmpty() || accountId == null
        		|| accountType == null || accountType.isEmpty() 
        		|| initialBalance <= 0) {
        	System.out.println("Failed to open account.");
        	return null;
        } 
        else {
        	Account account = Account.createAccount(customer, accountId, accountType, initialBalance);
            customer.addAccount(account);
            return account;
        }
    }

    public boolean closeAccount(Customer customer, Account account) {
    	if (customer == null || account == null) {
    		System.out.println("Failed to close account.");
    		return false;
    	}
    	else {
    		customer.removeAccount(account);
    		return true;
    	}
    }

    public boolean transfer(Account senderAccount, Account receiverAccount, double amount) {
    	if (senderAccount == null || receiverAccount == null || amount <= 0) {
    		System.out.println("Failed to Transfer.");
    		return false;
    	}
    	else {
    		senderAccount.TransferFunds(senderAccount, receiverAccount, amount);
    		return true;
    	}
    }

    public double getAccountBalance(Customer customer, Account account) {
        return account.getBalance();
    }

    public List<Transaction> getTransactionHistory(Customer customer, Account account) {
    	if (customer == null || account == null) {
    		System.out.println("Failed to Get Transactions.");
    		return null;
    	}
    	else {
    		return account.getTransactions();
    	}
    }

    public void manageRepaymentSchedule(Loan loan) {
        List<Payment> paymentSchedule = loan.generatePaymentSchedule();

        LocalDate currentDate = LocalDate.now();

        for (Payment payment : paymentSchedule) {
            LocalDate paymentDate = payment.getPaymentDate();

            if (currentDate.isAfter(paymentDate)) {
                if (payment.getPaymentStatus() == Payment.PaymentStatus.PENDING) {
                    sendReminderToCustomer(loan.getBorrower(), payment);
                }
                else if (payment.getPaymentStatus() == Payment.PaymentStatus.MISSED) {
                    loan.setLoanStatus(Loan.LoanStatus.DEFAULT);
                }
            }
        }
    }
    
    public boolean sendReminderToCustomer(Customer customer, Payment payment) {
    	if (customer.getEmail() == null || customer.getPhoneNumber() == null
    			|| customer.getEmail().isEmpty() || customer.getPhoneNumber().isEmpty()) {
    		System.out.println("There is no PhoneNumber or Email");
    		return false;
    	}
    	else {
    		sendEmailReminder(customer.getEmail(), payment);
    		sendSMSReminder(customer.getPhoneNumber(), payment);
    		return true;
    	}
    }

    private void sendEmailReminder(String email, Payment payment) {
        System.out.println("Email reminder sent to " + email + ": Your payment of $" + payment.getTotalPaymentAmount() + " is due on " + payment.getPaymentDate());
    }

    private void sendSMSReminder(String phoneNumber, Payment payment) {
        System.out.println("SMS reminder sent to " + phoneNumber + ": Your payment of $" + payment.getTotalPaymentAmount() + " is due on " + payment.getPaymentDate());
    }
    
    public void addLoan(Loan loan) {
    	loans.add(loan);
    }
    
    public void trackOutstandingLoanBalancesAndStatuses() {
        for (Loan loan : loans) {
            double outstandingBalance = loan.getOutstandingBalance();
            Loan.LoanStatus status = loan.getLoanStatus();
            System.out.println("Loan ID: " + loan.getLoanId() + ", Outstanding Balance: " + outstandingBalance + ", Status: " + status);
        }
    }
    
    public String toString() {
    	return "Bank{" +
                "BankName='" + bankName + '\'' +
                ", BankAddress='" + bankAddress + '\'' +
                ", Customers='" + customers + '\'' +
                ", Loans='" + loans + '\'' +
                ", Transactions=" + transactions +
                '}';
    }
}