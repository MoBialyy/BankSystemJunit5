package BankingSystem;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Customer {
	private String Name;
	private String Password;
	private String Address;
	private String NationalId;
	private String CustomerId;
	private String EmailAddress;
	private String PhoneNumber;
	private List<Account> Accounts;
	
	Pattern pattern = Pattern.compile("[^a-zA-Z0-9\\s]");
	
	public Customer(String name, String password, String address, String NID, String PhoneNo, String emaiAddress, String CID){
		Name = name;
		Password = password;
		Address = address;
		NationalId = NID;
		PhoneNumber = PhoneNo;
		EmailAddress = emaiAddress;
		CustomerId = CID;
		Accounts = new ArrayList<>();
	}
	
	public String getName() {
		return Name;
	}
	
	public void setName(String name) {
		if (name.isEmpty()) {
			System.out.println("Error... Invalid (Empty Input).");
			this.Name = null;
		}
		else {
			Matcher matcher = pattern.matcher(name);
			if (matcher.find()) {
				System.out.println("Error... Name cannot has special character.");
				this.Name = null;
			}
			else {
				this.Name = name;
			}
		}
	}
	
	public String getPassword() {
		return Password;
	}
	
	public void setPassword(String password) {
		if (password.isEmpty()) {
			System.out.println("Error... Invalid (Empty Input).");
			this.Password = null;
		}
		else {
			if (password.length() < 8 || password.length() > 20) {
				System.out.println("Error... Password Must Be Between 8 to 20 Character.");
				this.Password = null;
			}
			else {
				this.Password = password;
			}
		}
	}
	
	public String getAddress() {
		return Address;
	}
	
	public void setAddress(String address) {
		if (address.isEmpty()) {
			System.out.println("Error... Invalid (Empty Input).");
			this.Address = null;
		}
		else {
			this.Address = address;
		}
	}
	
	public String getNationalId() {
		return NationalId;
	}
	
	public void setNationalId(String nationalId) {
	    if (nationalId.isEmpty()) {
	    	System.out.println("Error... Invalid (Empty Input).");
	    	this.NationalId = null;
	    }
	    else {
	    	if (nationalId.length() != 14) {
		        System.out.println("Error... National Id must be a 14 characters long.");
		        this.NationalId = null;
		    }
		    else {
		        this.NationalId = nationalId;
		    }
	    }
	}
	
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	
	public void setPhoneNumber(String phoneNumber) {
		if (phoneNumber.isEmpty()) {
			System.out.println("Error... Invalid (Empty Input).");
			this.PhoneNumber = null;
		}
		else {
			if (phoneNumber.length() < 11) {
		        System.out.println("Error... Phone number must be a 11 characters long.");
		        this.PhoneNumber = null;
		    }
		    else {
		        this.PhoneNumber = phoneNumber.substring(0, 11);
		    }
		}
	}
	
	public String getEmail() {
		return EmailAddress;
	}
	
	public void setEmailAddress(String email) {
	    if (email.isEmpty()) {
	    	System.out.println("Error... Invalid (Empty Input).");
	    	this.EmailAddress = null;
	    }
	    else {
	    	if (!email.contains("@")) {
		        System.out.println("Error... Invalid Email Address (No @ symbol).");
		        this.EmailAddress = null;
		    } 
		    else {
		        this.EmailAddress = email;
		    }
	    }
	}
	
	public String getCustomerId() {
		return CustomerId;
	}
	
	public void addAccount(Account account) {
        Accounts.add(account);
	}
	
	public void removeAccount(Account account) {
        Accounts.remove(account);
    }
	
	public void removeAccountById(String accountId) {
		boolean result = true;
		for (Account account : Accounts) {
			if (account.getAccountId() == accountId) {
				Accounts.remove(account);
			}
			else {
				result = false;
			}
		}
		if (!result) {
			System.out.println("Error... This Account is not Found.");
		}
	}
	
	public double getTotalBalance() {
        double totalBalance = 0.0;
        for (Account account : Accounts) {
        	totalBalance += account.getBalance();
        }
        return totalBalance;
    }
	
	public List<Account> getAccounts() {
        return Accounts;
    }
	
    public String toString() {
        return "Customer{" +
                "Name='" + Name + '\'' +
                ", Address='" + Address + '\'' +
                ", NationalId='" + NationalId + '\'' +
                ", CustomerId='" + CustomerId + '\'' +
                ", EmailAddress=" + EmailAddress +
                ", PhoneNumber='" + PhoneNumber + '\'' +
                ", Accounts='" + Accounts + '\'' +
                '}';
    }
}
