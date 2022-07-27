package com.techelevator.Logs;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;

public class VMLog {
    static File vmLogFile;
    static DateTimeFormatter timeStamp = DateTimeFormatter.ofPattern("E, MM-dd-yy, hh:mm:ss a");

    public static void log(String message)  {
            vmLogFile = new File("logs/Log.txt");
            try (PrintWriter writer = new PrintWriter(new FileOutputStream(vmLogFile, true))) {
                writer.println(timeStamp.format(LocalDateTime.now()) + ": " + message);
            } catch (FileNotFoundException e) {
                System.out.println("Vending Machine Transaction Log file not found.");
            }
    }

    public static void displayLog()  {
        try (Scanner scan = new Scanner(vmLogFile)) {
            while (scan.hasNextLine()){
                System.out.println(scan.nextLine());
            }
        } catch (Exception e) {
            System.out.println("Vending Machine Transaction Log File not found.");
        }
    }
}
