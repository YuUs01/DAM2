package UD1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

public class CapturaDeSalida {
    public static void main(String[] args) {
        try{
            ProcessBuilder pb = new ProcessBuilder("ping", "www.google.com");
            Process proceso = pb.start();
            BufferedReader br = new BufferedReader(new InputStreamReader(proceso.getInputStream()));
            String linea;
            while ((linea = br.readLine())!= null){
                System.out.println("STDOUT " + linea);
            }
            int exitCode = proceso.waitFor();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
