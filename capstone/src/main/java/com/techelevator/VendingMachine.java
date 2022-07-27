package com.techelevator;

import com.techelevator.Inventory.Item;

import java.io.File;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import java.util.Map;
import java.util.Scanner;


import static com.techelevator.Inventory.Inventory.displayItems;
import static com.techelevator.Inventory.Item.itemList;
import static com.techelevator.Logs.SalesReport.addToReport;
import static com.techelevator.Logs.VMLog.log;

public class VendingMachine {

    static Scanner scan = new Scanner(System.in);

    //Customer Funds available
    private static double currentMoneyProvided;

    //Formatting
    public static NumberFormat dollars = NumberFormat.getCurrencyInstance();
    static DecimalFormat rounder = new DecimalFormat(".##");

    //<editor-fold desc="Coins - instance variables">
    static int dollarCoins;
    static int quarters;
    static int dimes;
    static int nickels;
    static int pennies;
    //</editor-fold>

    public static double getCurrentMoneyProvided() {
        return currentMoneyProvided;
    }
    public static void setCurrentMoneyProvided(double currentMoneyProvided) { VendingMachine.currentMoneyProvided = currentMoneyProvided; }

    /* Step Two: Purchase */
    /* Step Two Part One: Feed Money */
    public static void feedMoney() {

        label:
        while (getCurrentMoneyProvided() < 50.00) {
            System.out.println("\nPlease choose an amount to insert: \n" +
                    "[a] $1.00, [b] $2.00, [c] $5.00, [d] $10.00, [e] stop inserting money");
            String userInput = scan.nextLine();

            String moneyChoice = userInputToMoneyChoice(userInput);
            double addedFunds = 0;
            try {
                switch (moneyChoice) {
                    case "a":
                        addedFunds = 1;
                        break;
                    case "b":
                        addedFunds = 2;
                        break;
                    case "c":
                        addedFunds = 5;
                        break;
                    case "d":
                        addedFunds = 10;
                        break;
                    case "e":
                        break label;
                }
            } catch (Exception e) {
                System.out.println("\nAmount invalid. Select an amount to insert using a letter option or the value of the bill. \n" +
                        "This machine cannot accept coins or bills over $10.00.");
            }

            addMoneyToAvailableFunds(addedFunds);

            if (getCurrentMoneyProvided() > 0) {
                System.out.println("\nCurrent Funds Available: " + dollars.format(getCurrentMoneyProvided()));
            }
        }

        if (getCurrentMoneyProvided() >= 50.00) {
            System.out.println("\n" +
                    "That's enough! How expensive do you think snacks are? (⊙_⊙)？");
        }
    }

    public static String userInputToMoneyChoice(String userInput) {
        String moneyChoice = null;
        try {
            if (userInput.toLowerCase().contains("a") || userInput.equals("$1.00") || userInput.equals("$1") || ((userInput.contains("1.") || userInput.equals("1")) && Double.parseDouble(userInput)%1==0)) {
                moneyChoice = "a";
            } else if (userInput.toLowerCase().contains("b") || userInput.equals("$2.00") || userInput.equals("$2") || (userInput.contains("2") && Double.parseDouble(userInput)%1 ==0 && Double.parseDouble(userInput)<3)) {
                moneyChoice = "b";
            } else if (userInput.toLowerCase().contains("c") || userInput.equals("$5.00") || userInput.equals("$5") || (userInput.contains("5") && Double.parseDouble(userInput)%1 ==0 && Double.parseDouble(userInput)<6)) {
                moneyChoice = "c";
            } else if (userInput.toLowerCase().contains("d") || userInput.equals("$10.00") || userInput.equals("$10") || (userInput.contains("10") && Double.parseDouble(userInput)%1 ==0 && Double.parseDouble(userInput)<11)) {
                moneyChoice = "d";
            } else if (userInput.toLowerCase().contains("e") || userInput.contains("stop")) {
                moneyChoice = "e";
            }
        } catch (NumberFormatException e) {
            System.out.println("\nAmount invalid. Select an amount to insert using a letter option or the value of the bill.");
        } catch (NullPointerException n) {
            System.out.println("Invalid input.");
        }
        return moneyChoice;
    }

    public static void addMoneyToAvailableFunds(double addedFunds) {
        currentMoneyProvided = Double.parseDouble(rounder.format(currentMoneyProvided + addedFunds));
        log("FEED MONEY: " + dollars.format(addedFunds) + "\n" +
                "NEW BALANCE: " + dollars.format(getCurrentMoneyProvided()));
    }

    /* Step Two Part Two: Select Item */
    public static void selectItem(List<Item> itemList, Map<String, Integer> inventory) {
        displayItems(itemList, inventory);
        System.out.println("\nPlease select an item by entering the item code: ");
        String userInput = scan.nextLine();
        Item selection = getSelectionFromInventory(userInput, itemList, inventory);
        System.out.println(dispenseSelection(selection, userInput));
    }

    public static Item getSelectionFromInventory (String userInput, List<Item> itemList, Map<String, Integer> inventory) {
        Item selection = null;
        try {
            for (Item item : itemList){
                if (userInput.equalsIgnoreCase(item.getSlotLocation())){
                    if (inventory.get(item.getSlotLocation()) > 0 ) {
                        selection = item;
                        if (currentMoneyProvided >= selection.getPrice()){
                            /* Update Available Funds */
                            currentMoneyProvided = Double.parseDouble(rounder.format(currentMoneyProvided - selection.getPrice()));
                            /* Remove from Inventory */
                            inventory.put(selection.getSlotLocation(), inventory.get(selection.getSlotLocation())-1);
                            /* Add to Sales Report */
                            addToReport(selection.getName());
                            /* Add to Transaction Log */
                            log("DISPENSED: " + selection.getSlotLocation() + " " + selection.getName() + " " + dollars.format(selection.getPrice()) +
                                    "\nNEW BALANCE: " + dollars.format(getCurrentMoneyProvided()));
                        } else {
                            System.out.println("\nNot enough funds.");
                            selection = null;
                        }
                    } else {
                        System.out.println("\n" + item.getName().toUpperCase() + " IS OUT OF STOCK.");
                        break;
                    }
                }
            }
        } catch (NumberFormatException | NullPointerException e) {
            e.getLocalizedMessage();
        }
        return selection;
    }

    public static String dispenseSelection(Item selection, String userInput) {
        String output = null;
        try {
            if (selection != null) {
                    if (selection.getType().equalsIgnoreCase("chip")) {
                        output = "\nDispensing " + selection.getName() + "\n" +
                                "Crunch Crunch, Yum!";
                    } else if (selection.getType().equalsIgnoreCase("candy")) {
                        output = "\nDispensing " + selection.getName() + "\n" +
                                "Munch Munch, Yum!";
                    } else if (selection.getType().equalsIgnoreCase("gum")) {
                        output = "\nDispensing " + selection.getName() + "\n" +
                                "Chew Chew, Yum!";
                    } else if (selection.getType().equalsIgnoreCase("drink")) {
                        output = "\nDispensing " + selection.getName() + "\n" +
                                "Glug Glug, Yum!";
                    } else output = "\n" + userInput.toUpperCase() + " is not a valid selection.";
            } else output = "\n" + userInput.toUpperCase() + " is not a valid selection.";
        } catch (NullPointerException n) {
            System.out.println("Invalid input.");
        }
        return output;
    }

    /* Step Two Part Three: Finish Transaction */
    public static void giveChange(double balance) {
        if (balance > 0) {
            log("GIVE CHANGE: " + dollars.format(balance) + "\n" +
                    "NEW BALANCE: $0.00");
            int currentMoneyProvidedInCents = (int) (Double.parseDouble(rounder.format(balance*100)));
            dollarCoins = currentMoneyProvidedInCents/100;
            int change = currentMoneyProvidedInCents%100;
            quarters = change/25;
            change = currentMoneyProvidedInCents%25;
            dimes = change/10;
            change = change%10;
            nickels = change/5;
            change = change%5;
            pennies = change;

            System.out.println("\nYour change is: " + dollars.format(balance));
            if (dollarCoins>0){
                System.out.println(dollars.format(dollarCoins) + " in dollar coins. (" + dollarCoins + ")");
            }
            if (quarters>0){
                System.out.println(dollars.format(quarters*.25) + " in quarters. (" + quarters + ")");
            }
            if (dimes>0){
                System.out.println(dollars.format(dimes*.10) + " in dimes. (" + dimes + ")");
            }
            if (nickels>0){
                System.out.println(dollars.format(nickels*.05) + " in nickels. (" + nickels + ")");
            }
            if (pennies>0){
                System.out.println(dollars.format(pennies*.01) + " in pennies. (" + pennies + ")");
            }
             balance -= balance;
            setCurrentMoneyProvided(balance);
        }
    }

}
