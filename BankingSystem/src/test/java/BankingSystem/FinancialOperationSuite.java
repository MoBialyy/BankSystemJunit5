package BankingSystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import BankingSystem.AccountTest.TestDeposit;
import BankingSystem.AccountTest.TestTransferFunds;
import BankingSystem.AccountTest.TestWithdraw;
import BankingSystem.BankTest.TestAccountOperations;
import BankingSystem.BankTest.TestLoanManagementAndReminder;
import BankingSystem.LoanTest.LoanCalculationTests;
import BankingSystem.LoanTest.LoanPaymentTests;
import BankingSystem.PaymentTest.TestApplyPaymet;
import BankingSystem.TransactionTest.ProcessTransactions;

@Suite
@SelectClasses({ AccountTest.class, BankTest.class, CustomerTest.class, LoanCalculationTests.class,
		LoanPaymentTests.class, LoanTest.class, PaymentTest.class, ProcessTransactions.class,
		TestAccountOperations.class, TestApplyPaymet.class, TestDeposit.class, TestLoanManagementAndReminder.class,
		TestTransferFunds.class, TestWithdraw.class, TransactionTest.class })
public class FinancialOperationSuite {
// nothing
}

//this runs all the tests