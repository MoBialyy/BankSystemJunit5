package BankingSystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import BankingSystem.BankTest.TestCustomerOperations;
import BankingSystem.BankTest.TestLoginAndAuthentocation;
import BankingSystem.PaymentTest.TestSetPaymentStatus;
import BankingSystem.TransactionTest.GetTransactionDetails;
import BankingSystem.TransactionTest.InvalidTransactionDetails;
import BankingSystem.TransactionTest.ProcessTransactionScenarios;
import BankingSystem.TransactionTest.ProcessTransactions;

@Suite
@SelectClasses({ CustomerTest.class, GetTransactionDetails.class, InvalidTransactionDetails.class, PaymentTest.class,
		ProcessTransactions.class, ProcessTransactionScenarios.class, TestCustomerOperations.class,
		TestLoginAndAuthentocation.class, TestSetPaymentStatus.class, TransactionTest.class })
public class SecurityAndAuthorizationSuite {
// nothing
}
