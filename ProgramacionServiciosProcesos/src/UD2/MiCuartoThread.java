package UD2;

public class MiCuartoThread {
    static void main() {

        Runnable r = new Runnable() {
            @Override
            public void run() {
                System.out.println("Hola, soy tu cuarto thread");
            }
        };
        r.run();
    }
}
