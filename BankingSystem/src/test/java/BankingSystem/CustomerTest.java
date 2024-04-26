package BankingSystem;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Test;

class CustomerTest {
	
	private Customer Ali;
	private Customer Korkor;
	private Customer Ezz;
	private Customer Karim;
	private Customer Bialy;

    @BeforeEach
    public void setUp() {
        Ali = new Customer("Ali Refaat", "Ali_Refaat", "Fifth settlement", "30311270100058", "01024640120", "21p0105@eng.asu.edu.eg", "123456");
        Korkor = new Customer("Omar Korkor", "luca_korkor", "Nozha", "303090801000059", "01157758181", "21p0065@eng.asu.edu.eg", "112233");
        Ezz = new Customer("Ezzeldin Alaa", "zazou21", "Nozha", "30302030100078", "01097001029", "21p0100@eng.asu.edu.eg", "123123");
        Karim = new Customer("Karim Sherif", "Kiwigram_OF", "Gesr Al-Seuz", "30304150100045", "01112653391", "21p0223@eng.asu.edu.eg", "122331");
        Bialy = new Customer("Mohamed Bialy", "momo_bialy", "Helioplis", "30306190100022", "01553034244", "21p0200@eng.asu.edu.eg", "321321");
    }

    @Nested
    @DisplayName("Updating Data (Setters)")
    public class TestSetters {
    	@Order(1)
    	@Test
    	@DisplayName("Test setName()")
    	public void testsetName() {
    		String newName = "ALi Mohamed Refaat";
    		Ali.setName(newName);
    		assertEquals(newName, Ali.getName());
    	}
    	
    	@Order(2)
    	@Test
    	@DisplayName("Test setPassword()")
    	public void testsetPassword() {
    		String newName = "AliRefaat_2003";
    		Ali.setPassword(newName);
    		assertEquals(newName, Ali.getPassword());
    	}
    	
    	@Order(3)
    	@Test
    	@DisplayName("Test setAddress()")
    	public void testsetAddress() {
    		String newAddress = "st.Taha Hussien AL Nozha";
    		Korkor.setAddress(newAddress);
    		assertNotEquals("Nozha", Korkor.getAddress());
    	}
    	
    	@Order(4)
    	@Test
    	@DisplayName("Test setNationalId()")
    	public void testsetNationalId() {
    		String newNatId = "30302030100033";
    		Ezz.setNationalId(newNatId);
    		assertEquals(newNatId, Ezz.getNationalId());
    	}
    	
    	@Order(5)
    	@Test
    	@DisplayName("Test setPhoneNumber()")
    	public void testsetPhoneNumber() {
    		String newPhoneNo = "01553034245";
    		Bialy.setPhoneNumber(newPhoneNo);
    		assertNotEquals("01553034244", Bialy.getPhoneNumber());
    	}
    	
    	@Order(6)
    	@Test
    	@DisplayName("Test setEmail()")
    	public void testsetEmail() {
    		String newEmail = "Kiwi@gmail.com";
    		Karim.setEmailAddress(newEmail);
    		assertEquals(newEmail, Karim.getEmail());
    	}
    }
    
    @Nested
    @DisplayName("Test Account Operations")
    public class testAccountOperations{
    	@Order(1)
    	@Test
    	@DisplayName("Test addAccount()")
    	public void testaddAccount() {
    		Account account1 = new Account("A12", "Checking", 5000);
    		Account account2 = new Account("A16", "Saving", 7000);
    		Ali.addAccount(account1);
    		Ali.addAccount(account2);
    		assertEquals(2, Ali.getAccounts().size());
    		assertEquals(account1, Ali.getAccounts().get(0));
    		assertEquals(account2, Ali.getAccounts().get(1));
    	}
    
    	@Order(2)
    	@Test
    	@DisplayName("Test removeAccount")
    	public void testremoveAccount() {
    		Account account1 = new Account("A27", "Saving", 4000);
    		Account account2 = new Account("A56", "Checking", 5000);
    		Ezz.addAccount(account1);
    		Ezz.addAccount(account2);
    		Ezz.removeAccount(account1);
    		assertEquals(1, Ezz.getAccounts().size());
    		assertEquals(account2, Ezz.getAccounts().get(0));
    	}
    	
    	@Order(3)
    	@Test
    	@DisplayName("Test removeAcountById")
    	public void testremoveAccountById() {
    		Account account1 = new Account("A38", "Saving", 4000);
    		Account account2 = new Account("A98", "Checking", 5000);
    		Ali.addAccount(account1);
    		Ali.addAccount(account2);
    		Ali.removeAccountById("A38");
    		assertEquals(1, Ali.getAccounts().size());
    		assertEquals(account2, Ali.getAccounts().get(0));
    	}
    	
    	@Order(4)
    	@Test
    	@DisplayName("Test GetTotalBalance()")
    	public void testGetTotalBalance() {
    		Account account1 = new Account("A78", "Checking", 1000);
    		Account account2 = new Account("A54", "Saving", 2000);
    		Karim.addAccount(account1);
    		Karim.addAccount(account2);
    		assertEquals(3000, Karim.getTotalBalance());
    	}
    }
    
    @Nested
    @DisplayName("Test Invalid Data")
    public class testInvalidData {
    	@Order(1)
    	@Test
    	@DisplayName("Test a short Phone number")
    	public void testPhoneNo() {
    		String newPhoneNo = "0102464012";
    		Ali.setPhoneNumber(newPhoneNo);
    		assertNotEquals("0102464012", Ali.getPhoneNumber());
    	}
    	
    	@Order(2)
    	@Test
    	@DisplayName("Test Too Long Phone number")
    	public void testPhNumber() {
    		String newPhone = "0102464012010";
    		Ali.setPhoneNumber(newPhone);
    		assertEquals("01024640120", Ali.getPhoneNumber());
    		assertNotEquals("0102464012010", Ali.getPhoneNumber());
    	}
    	
    	@Order(3)
    	@Test
    	@DisplayName("Test Wrong National Id")
    	public void testWrongNationalId() {
    		String longNationalId = "30311270100058154";
    		String shortNationalId = "303112701000";
    		Ali.setNationalId(longNationalId);
    		Ali.setNationalId(shortNationalId);
    		assertNotEquals("30311270100058154", Ali.getNationalId());
    		assertNotEquals("303112701000", Ali.getNationalId());
    	}
    	
    	@Order(4)
    	@Test
    	@DisplayName("Test an Email doesn't Contain @")
    	public void testEmail() {
    		String newEmail = "alirefaatgmail.com";
    		Ali.setEmailAddress(newEmail);
    		assertNotEquals("alirefaatgamil.com", Ali.getEmail());
    	}
    	
    	@Order(5)
    	@Test
    	@DisplayName("Test an Empty Input")
    	public void testEmptyInput() {
    		Ali.setNationalId("");
    		assertNotEquals("", Ali.getNationalId());
    	}
    	
    	@Order(6)
    	@Test
    	@DisplayName("Test Special Character Name")
    	public void testSpecialChar() {
    		String newName = "Karim@Sherif";
    		Karim.setName(newName);
    		assertNotEquals(newName, Karim.getName());
    	}
    	
    	@Order(7)
    	@Test
    	@DisplayName("Test Pasword Boundries")
    	public void testPasswordBoundries() {
    		String newName = "Ali";
    		Ali.setPassword(newName);
    		assertNotEquals(newName, Ali.getPassword());
    	}
    	
    	@Order(8)
    	@Test
    	@DisplayName("Test Remove Non Existing Account")
    	public void testRemoveNonExistingAcc() {
    		Account account1 = new Account("A12", "Checking", 5000);
    		Account account2 = new Account("A16", "Saving", 7000);
    		Ali.addAccount(account1);
    		Ali.addAccount(account2);
    		Ali.removeAccountById("A17");
    		assertEquals(2, Ali.getAccounts().size());
    	}
    }
    
    @Test
    @DisplayName("Test string representation of Customer")
    public void testCustomerToString() {
    	String Expected = "Customer{" +
                "Name='" + Ali.getName() + '\'' +
                ", Address='" + Ali.getAddress() + '\'' +
                ", NationalId='" + Ali.getNationalId() + '\'' +
                ", CustomerId='" + Ali.getCustomerId() + '\'' +
                ", EmailAddress=" + Ali.getEmail() +
                ", PhoneNumber='" + Ali.getPhoneNumber() + '\'' +
                ", Accounts='" + Ali.getAccounts() + '\'' +
                '}';
    	assertEquals(Expected, Ali.toString());
    }
    
    @AfterEach
    public void tearDown() {
    	Ali = null;
    	Korkor = null;
    	Karim = null;
    	Ezz = null;
    	Bialy = null;
    }
    
//	@Test
//	void test() {
//		fail("Not yet implemented");
//	}

}
