package BankingSystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import BankingSystem.AccountTest.TestDeposit;
import BankingSystem.AccountTest.TestTransferFunds;
import BankingSystem.AccountTest.TestWithdraw;
import BankingSystem.BankTest.TestAccountOperations;

@Suite
@SelectClasses({ AccountTest.class, CustomerTest.class, TestAccountOperations.class, TestDeposit.class,
		TestTransferFunds.class, TestWithdraw.class, TransactionTest.class })
public class AccountManagementSuite {
// nothing
}
