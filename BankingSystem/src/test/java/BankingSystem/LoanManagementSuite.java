package BankingSystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import BankingSystem.BankTest.TestLoanManagementAndReminder;
import BankingSystem.LoanTest.LoanCalculationTests;
import BankingSystem.LoanTest.LoanCreationTests;
import BankingSystem.LoanTest.LoanPaymentTests;
import BankingSystem.LoanTest.LoanStatusTests;
import BankingSystem.PaymentTest.TestApplyPaymet;
import BankingSystem.PaymentTest.TestSetPaymentStatus;

@Suite
@SelectClasses({ LoanCalculationTests.class, LoanCreationTests.class, LoanPaymentTests.class, LoanStatusTests.class,
		LoanTest.class, PaymentTest.class, TestApplyPaymet.class, TestLoanManagementAndReminder.class,
		TestSetPaymentStatus.class })
public class LoanManagementSuite {
// nothing
}
