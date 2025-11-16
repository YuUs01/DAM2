package UD2.PracticaHilosPares;

public class CuentaPares {

    //Clase de hilos mediante la herencia de thread
    static class MiHilo extends Thread {
        private int inicio;
        private int fin;
        private int numerosPares;

        public MiHilo(String nombre, int inicio, int fin, int numerosPares) {
            super(nombre);
            this.inicio = inicio;
            this.fin = fin;
            this.numerosPares = numerosPares;
        }

        @Override
        public void run() {
            int n = 0;
            for (int i = inicio; i <= fin; i++) {
                if (i % 2 == 0) {
                    n++;
                    System.out.println(i + " Es par");
                }
            }
        }
    }

    //Clase de hilos mediante la implementación de la interface Runnable
    static class MiHilo2 implements Runnable {
        private int inicio;
        private int fin;
        private int numerosPares;

        public MiHilo2(String nombre, int inicio, int fin, int numerosPares) {
            this.inicio = inicio;
            this.fin = fin;
            this.numerosPares = numerosPares;
        }

        @Override
        public void run() {
            int n = 0;
            for (int i = inicio; i <= fin; i++) {
                if (i % 2 == 0) {
                    n++;
                    System.out.println(i + " Es par");
                }
            }
        }
    }

    public static void main(String[] args) {
        int[] numerosPares = new int[5];
        int limite = 100_000_000;
        int rango = limite / 5;
        Thread h1 = new Thread(() -> {
            int n = 0;
            int fin = rango -1;
            int inicio = 1;
            for (int i = inicio; i <= fin; i++) {
                if (i % 2 == 0) {
                    n++;
                    //System.out.println(i + " Es par");
                }
            }
            numerosPares[0] = n;
        });
        Thread h2 = new Thread(() -> {
            int n = 0;
            int inicio = rango;
            int fin = 2 * rango - 1;
            for (int i = inicio; i <= fin; i++) {
                if (i % 2 == 0) {
                    n++;
                    //System.out.println(i + " Es par");
                }
            }
            numerosPares[1] = n;
        });
        Thread h3 = new Thread(() -> {
            int n = 0;
            int inicio = 2 * rango;
            int fin = 3 * rango -1;
            for (int i = inicio; i <= fin; i++) {
                if (i % 2 == 0) {
                    n++;
                    //System.out.println(i + " Es par");
                }
            }
            numerosPares[2] = n;
        });
        Thread h4 = new Thread(() -> {
            int n = 0;
            int inicio = 3 * rango;
            int fin = 4 * rango -1;
            for (int i = inicio; i <= fin; i++) {
                if (i % 2 == 0) {
                    n++;
                    //System.out.println(i + " Es par");
                }
            }
            numerosPares[3] = n;
        });
        Thread h5 = new Thread(() -> {
            int n = 0;
            int inicio = 4 * rango;
            int fin = limite;
            for (int i = inicio; i <= fin; i++) {
                if (i % 2 == 0) {
                    n++;
                    //System.out.println(i + " Es par");
                }
            }
            numerosPares[4] = n;
        });


        h1.start();
        h2.start();
        h3.start();
        h4.start();
        h5.start();

        try {
            h1.join();
            h2.join();
            h3.join();
            h4.join();
            h5.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        int totalPares = 0;
        for (int c: numerosPares) totalPares +=c;
        System.out.println("Total de números pares en " + limite + ": " + totalPares);
    }
}
