package com.techelevator.Inventory;

import junit.framework.TestCase;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techelevator.Inventory.Inventory.displayItems;
import static com.techelevator.Inventory.Inventory.restockInventory;

public class InventoryTest {
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
        testInventory.put("T1", 0);
        testInventory.put("T2", 0);
        testInventory.put("T3", 0);
        testInventory.put("T4", 0);
        testInventory.put("T5", 0);
        testInventory.put("T6", 0);

    }

    @Test
    public void restock_test(){
        restockInventory(testInventory, testItemList, 5);
        for (var i : testInventory.entrySet()) {
            Assert.assertEquals(5, i.getValue(), 0);
        }

        Map<String, Integer> testinv = null;
        List<Item> testlist = null;
        restockInventory(testinv, testlist, 5);
        /* Should print "Inventory or Item List is invalid. */
    }

    @Test
    public void display_items_test() {
        displayItems(testItemList, testInventory);
    }

    @Test
    public void display_items_null_input() {
        displayItems(null, testInventory);
        /* Should stop after Row A: and print "Input file does not exist." */
    }


}