package com.techelevator.Inventory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Item {
    private String slotLocation;
    private String name;
    private double price;
    private String type;

    //<editor-fold desc="Getters and Setters">
    public String getSlotLocation() {
        return slotLocation;
    }

    public void setSlotLocation(String slotLocation) {
        this.slotLocation = slotLocation;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    //</editor-fold>

    public Item (String slotLocation, String name, double price, String type) {
        this.slotLocation = slotLocation;
        this.name = name;
        this.price = price;
        this.type = type;
    }
    public Item() {}

    public static List<Item> itemList(File inputFile) {
        List<Item> list = new ArrayList<>();
        try (Scanner scan = new Scanner(inputFile)) {
            while (scan.hasNextLine()){
                String line = scan.nextLine();
                String[] arr = line.replace("|", ",").split(",");
                Item item = new Item();
                item.setSlotLocation(arr[0]);
                item.setName(arr[1]);
                item.setPrice(Double.parseDouble(arr[2]));
                item.setType(arr[3]);
                list.add(item);
            }
        } catch (FileNotFoundException | NullPointerException e) {
            System.out.println("Input file not found.");
        }
        return list;
    }

}
