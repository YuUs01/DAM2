package UD1;

import java.beans.PropertyEditor;
import java.util.concurrent.TimeUnit;

public class PracticaUD1 {
    public static void main(String[] args) {
        try {
            ProcessBuilder pb = new ProcessBuilder("Notepad");
            ProcessBuilder pb1 = new ProcessBuilder("\"C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe\"");

            for (int i =0; i<4; i++){
                Process proceso = pb.start();
                Process proceso1 = pb1.start();
                Thread.sleep(1000);
                Process proceso2 = Runtime.getRuntime().exec("explorer");
            }
            Thread.sleep(5000);

            //Process apagar = Runtime.getRuntime().exec("shutdown /s");
            System.out.println("Aquí se apagaría el dispositivo");
        } catch (Exception e) {
             e.printStackTrace();
        }
    }
}
