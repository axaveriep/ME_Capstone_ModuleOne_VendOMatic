package com.techelevator;

import com.techelevator.Inventory.Item;
import com.techelevator.view.Menu;

import java.io.File;
import java.util.List;


import static com.techelevator.Inventory.Inventory.*;
import static com.techelevator.Inventory.Item.itemList;
import static com.techelevator.Logs.VMLog.displayLog;
import static com.techelevator.Logs.VMLog.log;
import static com.techelevator.VendingMachine.*;
import static com.techelevator.Logs.SalesReport.*;

public class VendingMachineCLI {

	// MAIN MENU including hidden options
	private static final String MAIN_MENU_OPTION_DISPLAY_ITEMS = "Display Vending Machine Items";
	private static final String MAIN_MENU_OPTION_PURCHASE = "Purchase";
	private static final String MAIN_MENU_OPTION_EXIT = "Exit";
	private static final String MAIN_MENU_OPTION_DISPLAY_SALES_REPORT = "Display Sales Report";
	private static final String MAIN_MENU_OPTION_DISPLAY_LOG = "Display Log";
	private static final String[] MAIN_MENU_OPTIONS = { MAIN_MENU_OPTION_DISPLAY_ITEMS, MAIN_MENU_OPTION_PURCHASE, MAIN_MENU_OPTION_EXIT, MAIN_MENU_OPTION_DISPLAY_SALES_REPORT, MAIN_MENU_OPTION_DISPLAY_LOG };

	//PURCHASE MENU
	private static final String PURCHASE_MENU_OPTION_FEED_MONEY = "Feed Money";
	private static final String PURCHASE_MENU_OPTION_SELECT_ITEM = "Select Item";
	private static final String PURCHASE_MENU_OPTION_FINISH_TRANSACTION = "Finish Transaction";
	private static final String[] PURCHASE_MENU_OPTIONS = {PURCHASE_MENU_OPTION_FEED_MONEY, PURCHASE_MENU_OPTION_SELECT_ITEM, PURCHASE_MENU_OPTION_FINISH_TRANSACTION};

	// SPECIFICS for this Machine
	private static final File VENDING_MACHINE_INPUT_FILE = new File("vendingmachine.csv");
	private static final List<Item> VENDING_MACHINE_ITEM_LIST = itemList(VENDING_MACHINE_INPUT_FILE);
	private static final int MAX_STOCK_PER_ITEM = 5;

	private Menu menu;

	public VendingMachineCLI(Menu menu) {
		this.menu = menu;
	}

	public void run()  {

		restockInventory(getInventory(), VENDING_MACHINE_ITEM_LIST, MAX_STOCK_PER_ITEM);
		log("Vending Machine Open\n");
		System.out.println("\n" +
				" _       ____  _     __    ___   _      ____ \n" +
				"\\ \\    /| |_  | |   / /`  / / \\ | |\\/| | |_  \n" +
				" \\_\\/\\/ |_|__ |_|__ \\_\\_, \\_\\_/ |_|  | |_|__ \n" +
				"\n" +
				"          TO ASHLEY'S SNACK 'N' GO \n" +
				"       BEST HUNGER HACK FOR YOUR DOUGH!");

		while (true) {
			String choice = (String) menu.getChoiceFromOptions(MAIN_MENU_OPTIONS);

			if (choice.equals(MAIN_MENU_OPTION_DISPLAY_ITEMS)) {
				displayItems(VENDING_MACHINE_ITEM_LIST, getInventory());
			} else if (choice.equals(MAIN_MENU_OPTION_PURCHASE)) {
				 //do purchase
				label:
				while (true) {
					String purchaseChoice = (String) menu.getChoiceFromOptions(PURCHASE_MENU_OPTIONS);
					switch (purchaseChoice) {
						case PURCHASE_MENU_OPTION_FEED_MONEY:
							feedMoney();
							break;
						case PURCHASE_MENU_OPTION_SELECT_ITEM:
							selectItem(VENDING_MACHINE_ITEM_LIST, getInventory());
							break;
						case PURCHASE_MENU_OPTION_FINISH_TRANSACTION:
							giveChange(getCurrentMoneyProvided());
							break label;
					}
				}

			} else if (choice.equals(MAIN_MENU_OPTION_DISPLAY_SALES_REPORT)) {
				createSalesReport();
				displaySalesReport();
			} else if (choice.equals(MAIN_MENU_OPTION_DISPLAY_LOG)) {
				displayLog();
			} else if (choice.equals(MAIN_MENU_OPTION_EXIT)){
				createSalesReport("End Of Day");
				System.out.println("\nThank you for visiting Ashley's Snack 'N' Go! \n" +
						"\nHave a great day! (ﾉ◕ヮ◕)ﾉ*:･ﾟ✧");
				log("Vending Machine Closed \n" +
						"-----------------------------\n");
				break;
			}
		}
	}

	public static void main(String[] args) {
		Menu menu = new Menu(System.in, System.out);
		VendingMachineCLI cli = new VendingMachineCLI(menu);
		cli.run();


	}
}
