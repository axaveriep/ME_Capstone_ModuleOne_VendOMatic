package com.techelevator.Logs;

import org.junit.Assert;
import org.junit.Test;

import java.util.Scanner;

import static com.techelevator.Logs.VMLog.log;
import static com.techelevator.Logs.VMLog.vmLogFile;

public class VMLogTest {

    @Test
    public void log_file_test() {
        Assert.assertTrue(vmLogFile.exists());
    }

    @Test
    public void log_message_test() {
        boolean pass = false;
        log(null);
        log("TEST TEST TEST");
        try (Scanner scan = new Scanner(vmLogFile)) {
            while (scan.hasNextLine()) {
                String line = scan.nextLine();
                if (line.contains("TEST TEST TEST")) {
                    pass = true;
                }
            }
        } catch (Exception e) {
            pass = false;
        }
        Assert.assertTrue(pass);
    }

}
