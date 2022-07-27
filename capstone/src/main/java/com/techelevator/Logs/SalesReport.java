package com.techelevator.Logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SalesReport {
    static File salesReport;
    static DateTimeFormatter reportTimeStamp = DateTimeFormatter.ofPattern("MM-dd-yy_HH-mm-ss");
    static Map<String, Integer> salesReportData = new HashMap<>();

    public static void addToReport(String itemName) {
        if(salesReportData.containsKey(itemName)) {
            salesReportData.put(itemName, salesReportData.get(itemName) + 1);
        } else salesReportData.put(itemName, 1);
    }

    public static void createSalesReport() {
        salesReport = new File("logs/" + reportTimeStamp.format(LocalDateTime.now()) + "_SalesReport.txt");
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(salesReport, true))) {
            writer.println("New Sales Report for: " + VMLog.timeStamp.format(LocalDateTime.now()));
            for (var i : salesReportData.entrySet()) {
                writer.println(i.getKey() + " | " + i.getValue());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sales Report Log file not found.");
        }
    }

    public static void createSalesReport(String message) {
        salesReport = new File("logs/" + reportTimeStamp.format(LocalDateTime.now()) + "_SalesReport.txt");
        try (PrintWriter writer = new PrintWriter(new FileOutputStream(salesReport, true))) {
            writer.println(message + "\n" + "New Sales Report for: " + VMLog.timeStamp.format(LocalDateTime.now()));
            for (var i : salesReportData.entrySet()) {
                writer.println(i.getKey() + "|" + i.getValue());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sales Report Log file not found.");
        }
    }

    public static void displaySalesReport() {
        try (Scanner scan = new Scanner(salesReport)) {
            while(scan.hasNextLine()) {
                System.out.println(scan.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Sales Report File not found");
        }
    }

}
