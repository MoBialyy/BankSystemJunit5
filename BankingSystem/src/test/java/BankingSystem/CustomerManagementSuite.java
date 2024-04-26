package BankingSystem;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

import BankingSystem.BankTest.TestCustomerOperations;
import BankingSystem.BankTest.TestLoginAndAuthentocation;

@Suite
@SelectClasses({ CustomerTest.class, TestCustomerOperations.class, TestLoginAndAuthentocation.class})
public class CustomerManagementSuite {
// nothing
}
