package com.techelevator.view;

import com.techelevator.VendingMachine;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

import static com.techelevator.VendingMachine.dollars;
import static com.techelevator.VendingMachine.getCurrentMoneyProvided;


public class Menu {

	private PrintWriter out;
	private Scanner in;

	public Menu(InputStream input, OutputStream output) {
		this.out = new PrintWriter(output);
		this.in = new Scanner(input);
	}

	public Object getChoiceFromOptions(Object[] options) {
		Object choice = null;
		while (choice == null) {
			displayMenuOptions(options);
			choice = getChoiceFromUserInput(options);
		}
		return choice;
	}

	private Object getChoiceFromUserInput(Object[] options) {
		Object choice = null;
		String userInput = in.nextLine();
		if (options.length > 3 && userInput.toLowerCase().contains("report")) {
			choice = options[3];
		} else if (options.length > 4 && userInput.toLowerCase().contains("log")) {
			choice = options[4];
		} else {
			try {
				int selectedOption = Integer.valueOf(userInput);
				if (selectedOption > 0 && selectedOption <= options.length) {
					choice = options[selectedOption - 1];
				}
			} catch (NumberFormatException e) {
				e.getLocalizedMessage();
				// eat the exception, an error message will be displayed below since choice will be null
			}
		}
		if (choice == null) {
			out.println(System.lineSeparator() + "*** " + userInput + " is not a valid option ***" + System.lineSeparator());
		}
		return choice;
	}

	private void displayMenuOptions(Object[] options) {
		out.println();
		for (int i = 0; i < options.length; i++) {
			if (i > 2) {
				continue;
			}
			int optionNum = i + 1;
			out.println(optionNum + ") " + options[i]);
		}
		if (getCurrentMoneyProvided() != 0 && options[0] == "Feed Money") {
			out.println("\nCurrent Funds Available: " + dollars.format(getCurrentMoneyProvided()));
		}
		out.print(System.lineSeparator() + "Please choose an option >>> ");
		out.flush();
	}
}
