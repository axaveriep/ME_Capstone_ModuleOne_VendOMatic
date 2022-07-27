package com.techelevator.Inventory;

import com.techelevator.Inventory.Item;
import org.junit.Assert;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static com.techelevator.Inventory.Item.itemList;

public class ItemTest {

    private static final File TEST_INPUT_FILE = new File("testvendingmachine.txt");

    @Test
    public void create_new_item_with_properties() {
        Item test = new Item("E1", "Test", 9.99, "tester");
        Assert.assertEquals("E1", test.getSlotLocation());
        Assert.assertEquals("Test", test.getName());
        Assert.assertEquals(9.99, test.getPrice(), 0);
        Assert.assertEquals("tester", test.getType());
    }

    @Test
    public void item_list_test() {
        List<Item> testList = itemList(TEST_INPUT_FILE);
        Assert.assertEquals("T1", testList.get(0).getSlotLocation());
        Assert.assertEquals(99.99, testList.get(5).getPrice(), 0);
        Assert.assertEquals("Test Gum", testList.get(2).getName());
        Assert.assertEquals("Candy", testList.get(4).getType());
    }

    @Test
    public void item_list_test_null() {
        List<Item> testList2 = itemList(null);
        Assert.assertTrue(testList2.isEmpty());

        /* Should print "Input file not found." */
    }
}
