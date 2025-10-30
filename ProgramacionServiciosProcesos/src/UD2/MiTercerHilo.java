package UD2;

public class MiTercerHilo implements Runnable{
    @Override
    public void run() {
        System.out.println("hola, soy tu tercer thread");
    }

    static void main() {
        Thread thread = new Thread();
        thread.start();
    }
}
