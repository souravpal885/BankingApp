package bank.com;

import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

class MinimumBalanceException extends Exception {
	public MinimumBalanceException(String message) {
		super(message);
	}
}

public class BankAccount {
	private static final Logger LOGGER = Logger.getLogger(BankAccount.class.getName());

	private int accountNumber;
	private String accountHolderName;
	private double balance;
	private ArrayList<String> transactions;
	private final double MINIMUM_BALANCE = 1000.0;

	// Constructor to initialize account details
	public BankAccount(int accountNumber, String accountHolderName, double balance) {
		this.accountNumber = accountNumber;
		this.accountHolderName = accountHolderName;
		this.balance = balance;
		this.transactions = new ArrayList<String>();
	}

	// Method to deposit money into the account
	public void deposit(double amount) {
		balance += amount;
		String transaction = "Deposit: +" + amount + " on " + new Date() + " by " + accountHolderName + " ("
				+ accountNumber + ")";
		transactions.add(transaction);
		LOGGER.log(Level.INFO, amount + " deposited successfully. New balance: " + balance);
	}

	// Method to withdraw money from the account
	public void withdraw(double amount) throws MinimumBalanceException {
		if (balance - amount < MINIMUM_BALANCE) {
			String message = "Insufficient balance. Minimum balance required: " + MINIMUM_BALANCE;
			LOGGER.log(Level.WARNING, message);
			throw new MinimumBalanceException(message);
		} else {
			balance -= amount;
			String transaction = "Withdrawal: -" + amount + " on " + new Date() + " by " + accountHolderName + " ("
					+ accountNumber + ")";
			transactions.add(transaction);
			LOGGER.log(Level.INFO, amount + " withdrawn successfully. New balance: " + balance);
		}
	}

	// Method to get the mini statement for the account
	public void getMiniStatement() {
		LOGGER.log(Level.INFO, "Mini statement for account: " + accountNumber);
		for (String transaction : transactions) {
			LOGGER.log(Level.INFO, transaction);
		}
		LOGGER.log(Level.INFO, "Current balance: " + balance);
	}

	// Main method to run the banking app
	public static void main(String[] args) {
		Scanner scanner = new Scanner(System.in);

		// Get account details from user input
		LOGGER.log(Level.INFO, "Enter account number:");
		int accountNumber = scanner.nextInt();
		scanner.nextLine(); // consume the new line character
		LOGGER.log(Level.INFO, "Enter account holder name:");
		String accountHolderName = scanner.nextLine();
		while (!accountHolderName.matches("[a-zA-Z ]+")) {
			LOGGER.log(Level.WARNING,
					"Invalid input. Account holder name must contain only alphabetic characters and spaces. Please try again:");
			accountHolderName = scanner.nextLine();
		}
		LOGGER.log(Level.INFO, "Enter initial balance:");
		double balance = scanner.nextDouble();
		// double initialDeposit = scanner.nextDouble();
		while (balance < 5000)

		{
			LOGGER.info("Invalid amount. Please enter a minimum of 5000:");
			balance = scanner.nextDouble();
		}

		// Create a new bank account object
		BankAccount account = new BankAccount(accountNumber, accountHolderName, balance);

		// Handle user input for banking transactions
		while (true) {
			LOGGER.log(Level.INFO, "\nSelect an option:");
			LOGGER.log(Level.INFO, "1. Deposit");
			LOGGER.log(Level.INFO, "2. Withdraw");
			LOGGER.log(Level.INFO, "3. Mini statement");
			LOGGER.log(Level.INFO, "4. Exit");
			int choice = scanner.nextInt();

			switch (choice) {
			case 1:
				LOGGER.info("Enter amount to deposit:");
				System.out.println("Enter amount to deposit:");
				double depositAmount = scanner.nextDouble();
				account.deposit(depositAmount);
				break;
			case 2:
				LOGGER.info("Enter amount to withdraw:");
				System.out.println("Enter amount to withdraw:");
				double withdrawalAmount = scanner.nextDouble();
				try {
					account.withdraw(withdrawalAmount);
				} catch (MinimumBalanceException e) {
					System.out.println(e.getMessage());
				}
				break;
			case 3:
				LOGGER.info("View your mini statement:");
				System.out.println("View your mini statement:");
				account.getMiniStatement();
				break;
			case 4:
				LOGGER.info("Exit:");
				System.out.println("Exit:");
				System.exit(0);
			default:
				LOGGER.info("Invalid:");
				System.out.println("Invalid:");
				System.out.println("Invalid choice. Try again.");
			}
		}
	}
}