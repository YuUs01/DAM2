package UD2;

public class MiSegundoHilo {
    public static void main(String[] args) {
        Thread thread = new Thread(() -> System.out.println("soy un hilo") );

        thread.start();
    }
}
