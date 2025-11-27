package UD1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class ExecuteNetworkInfoProcess {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("cmd", "/c", "ipconfig");
            Process p = pb.start();
            int exitCode = p.waitFor();

            BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            if (exitCode == 0) {
                System.out.println("Process finished successfully.");
            } else {
                System.out.println("Process failed with exit code " + exitCode);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
