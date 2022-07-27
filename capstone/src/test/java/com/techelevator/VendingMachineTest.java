package com.techelevator;

import com.techelevator.Inventory.Item;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static com.techelevator.VendingMachine.*;

public class VendingMachineTest {

    Item testChip = new Item("T1", "Test Chips", 1.10, "Chip");
    Item testCandy = new Item("T2", "Test Candy", 1.20, "Candy");
    Item testGum = new Item("T3", "Test Gum", 1.30, "Gum");
    Item testDrink = new Item("T4", "Test Drink", 1.40, "Drink");
    Item testItem5 = new Item("T5", "Tester", 1.50, "Candy");
    Item testExpensiveItem = new Item("T6", "Testy", 99.99, "Gum");
    public List<Item> testItemList = Arrays.asList(testCandy, testChip, testGum, testDrink, testItem5, testExpensiveItem);

    Map<String, Integer> testInventory = new HashMap<>();

    @Before
    public void create_test_inventory() {
        testInventory.put("T1", 5);
        testInventory.put("T2", 5);
        testInventory.put("T3", 5);
        testInventory.put("T4", 5);
        testInventory.put("T5", 5);
        testInventory.put("T6", 5);
    }

    @Test
    public void give_change_test(){
        giveChange(10.4);
        Assert.assertEquals(0, getCurrentMoneyProvided(), 0);
        Assert.assertEquals(10, dollarCoins, 0);
        Assert.assertEquals(1, quarters, 0);
        Assert.assertEquals(1, dimes, 0);
        Assert.assertEquals(1, nickels, 0);
        Assert.assertEquals(0, pennies, 0);

        giveChange(4.99);
        Assert.assertEquals(0, getCurrentMoneyProvided(), 0);
        Assert.assertEquals(4, dollarCoins, 0);
        Assert.assertEquals(3, quarters, 0);
        Assert.assertEquals(2, dimes, 0);
        Assert.assertEquals(0, nickels, 0);
        Assert.assertEquals(4, pennies, 0);
    }


    @Test
    public void get_selection_from_inventory_test() {
        setCurrentMoneyProvided(10);
        Item test1 = getSelectionFromInventory("T1", testItemList, testInventory);
        Assert.assertEquals("T1", test1.getSlotLocation());
        Assert.assertEquals(4, testInventory.get("T1"), 0);
    }

    @Test
    public void get_selection_from_inventory_test_invalid() {
        Item test2 = getSelectionFromInventory("E5", testItemList, testInventory);
        Assert.assertNull(test2);
    }

    @Test
    public void get_selection_from_inventory_test_out_of_stock() {
        testInventory.put("T5", 0);
        Item item3 = getSelectionFromInventory("T5", testItemList, testInventory);
        Assert.assertNull(item3);
        /* Should print "TESTER IS OUT OF STOCK" */

        setCurrentMoneyProvided(50.00);
        getSelectionFromInventory("T1", testItemList, testInventory);
        getSelectionFromInventory("T1", testItemList, testInventory);
        getSelectionFromInventory("T1", testItemList, testInventory);
        getSelectionFromInventory("T1", testItemList, testInventory);
        getSelectionFromInventory("T1", testItemList, testInventory);
        Item testChip6 = getSelectionFromInventory("T1", testItemList, testInventory);
        Assert.assertNull(testChip6);
        /* Should print "TEST CHIP IS OUT OF STOCK" */
    }

    @Test
    public void get_selection_test_update_current_money_provided() {
        setCurrentMoneyProvided(4.0);
        getSelectionFromInventory("T3", testItemList, testInventory);
        Assert.assertEquals(2.70, getCurrentMoneyProvided(), 0);
    }

    @Test
    public void get_selection_test_NSF() {
        setCurrentMoneyProvided(1.0);
        Item testSelection = getSelectionFromInventory("T6", testItemList, testInventory);
        Assert.assertEquals(1.0, getCurrentMoneyProvided(), 0);
        Assert.assertNull(testSelection);
        /* Should print "Not enough funds." */
    }

    @Test
    public void dispense_selection_test_invalid_selection() {
        Item testItemX1 = null;
        String invalidOutput = dispenseSelection(testItemX1, "X1");
        String invalidExpected = "\n" + "X1" + " is not a valid selection.";
        Assert.assertEquals(invalidExpected, invalidOutput);

        Item testItemX2 = new Item("X2", "Test X2", 0.99, "Test");
        String invalidOutput2 = dispenseSelection(testItemX2, "X2");
        String invalidExpected2 = "\n" + "X2" + " is not a valid selection.";
        Assert.assertEquals(invalidExpected2, invalidOutput2);

    }

    @Test
    public void dispense_selection_test_print_correct_message() {
        String chipOutput = dispenseSelection(testChip, "T1");
        String chipExpected = "\nDispensing " + "Test Chips" + "\n" + "Crunch Crunch, Yum!";
        Assert.assertEquals(chipExpected, chipOutput);

        String candyOutput = dispenseSelection(testCandy, "T2");
        String candyExpected = "\nDispensing " + "Test Candy" + "\n" + "Munch Munch, Yum!";
        Assert.assertEquals(candyExpected, candyOutput);

        String gumOutput = dispenseSelection(testGum, "T3");
        String gumExpected = "\nDispensing " + "Test Gum" + "\n" + "Chew Chew, Yum!";
        Assert.assertEquals(gumExpected, gumOutput);

        String drinkOutput = dispenseSelection(testDrink, "T4");
        String drinkExpected = "\nDispensing " + "Test Drink"+ "\n" + "Glug Glug, Yum!";
        Assert.assertEquals(drinkExpected, drinkOutput);

    }

    @Test
    public void money_choice_by_letters_test() {
        String testChoice1 = userInputToMoneyChoice("a");
        String testChoice2 = userInputToMoneyChoice("B");
        String testChoice3 = userInputToMoneyChoice("[C]");
        String testChoice4 = userInputToMoneyChoice("d.");
        String testChoice5 = userInputToMoneyChoice("stop");
        String testChoice6 = userInputToMoneyChoice("");
        String testChoice7 = userInputToMoneyChoice(null);

        Assert.assertEquals("a", testChoice1);
        Assert.assertEquals("b", testChoice2);
        Assert.assertEquals("c", testChoice3);
        Assert.assertEquals("d", testChoice4);
        Assert.assertEquals("e", testChoice5);
        Assert.assertNull(testChoice6);
        Assert.assertNull(testChoice7);

        /* Should print "Invalid input." */
    }

    @Test
    public void money_choice_by_amounts_test() {
        String testChoice1 = userInputToMoneyChoice("1");
        String testChoice2 = userInputToMoneyChoice("$2");
        String testChoice3 = userInputToMoneyChoice("$5.00");
        String testChoice4 = userInputToMoneyChoice("10.00");
        String testChoice5 = userInputToMoneyChoice("");
        String testChoice6 = userInputToMoneyChoice("100");
        String testChoice7 = userInputToMoneyChoice("$20.00");
        String testChoice8 = userInputToMoneyChoice("1.25");

        Assert.assertEquals("a", testChoice1);
        Assert.assertEquals("b", testChoice2);
        Assert.assertEquals("c", testChoice3);
        Assert.assertEquals("d", testChoice4);
        Assert.assertNull(testChoice5);
        Assert.assertNull(testChoice6);
        Assert.assertNull(testChoice7);
        Assert.assertNull(testChoice8);

        // Should print "Amount invalid. Select and amount to insert using a letter option or the value of the bill."
    }

    @Test
    public void add_money_to_available_funds_test() {
        setCurrentMoneyProvided(0.0);
        addMoneyToAvailableFunds(5.00);
        Assert.assertEquals(5, getCurrentMoneyProvided(), 0);

        addMoneyToAvailableFunds(2.00);
        Assert.assertEquals(7, getCurrentMoneyProvided(), 0);
    }
}