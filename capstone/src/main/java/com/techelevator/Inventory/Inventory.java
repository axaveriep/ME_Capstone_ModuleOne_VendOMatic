package com.techelevator.Inventory;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.techelevator.Inventory.Item.itemList;
import static com.techelevator.VendingMachine.dollars;

public class Inventory {

    //Vending Machine current stock
    private static Map<String, Integer> inventory = new HashMap<>();

    public static Map<String, Integer> getInventory() {
        return inventory;
    }

    /* Step Zero: Restock Vending Machine Inventory */
    public static void restockInventory(Map<String, Integer> inventory, List<Item> itemList, int maxItems) {
        try {
            for (Item item : itemList) {
                inventory.put(item.getSlotLocation(), maxItems);
            }
        } catch (NullPointerException e) {
            System.out.println("Inventory or Item List is invalid.");
        }
    }

    /* Step One: Display items */
    public static void displayItems(List<Item> itemList, Map<String, Integer> inventory) {
        try {
            System.out.println("\n" +
                    "Row A: Chips");
            for (Item item : itemList){
                if (item.getType().equalsIgnoreCase("chip")) {
                    System.out.println(item.getSlotLocation() + ": " + item.getName() + " | "
                            + dollars.format(item.getPrice()) + " | "
                            + inventory.get(item.getSlotLocation()) + " in stock.");
                }
            }
            System.out.println("\nRow B: Candy");
            for (Item item : itemList){
                if (item.getType().equalsIgnoreCase("candy")) {
                    System.out.println(item.getSlotLocation() + ": " + item.getName() + " | "
                            + dollars.format(item.getPrice()) + " | "
                            + inventory.get(item.getSlotLocation()) + " in stock.");
                }
            }
            System.out.println("\nRow C: Drinks");
            for (Item item : itemList){
                if (item.getType().equalsIgnoreCase("drink")) {
                    System.out.println(item.getSlotLocation() + ": " + item.getName() + " | "
                            + dollars.format(item.getPrice()) + " | "
                            + inventory.get(item.getSlotLocation()) + " in stock.");
                }
            }
            System.out.println("\nRow D: Gum");
            for (Item item : itemList){
                if (item.getType().equalsIgnoreCase("gum")) {
                    System.out.println(item.getSlotLocation() + ": " + item.getName() + " | "
                            + dollars.format(item.getPrice()) + " | "
                            + inventory.get(item.getSlotLocation()) + " in stock.");
                }
            }
        } catch (NullPointerException n) {
            System.out.println("Input file does not exist.");
        }
    }

}
