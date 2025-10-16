package UD1;
/*
Escribe un programa en Java que utilice la clase Runtime y el método exec() para
lanzar una aplicación externa sencilla, como el Bloc de Notas en Windows o el editor
gedit en Linux.
Comprueba que el proceso se abre de manera independiente al programa Java y que,
al cerrarlo, el programa Java continúa su ejecución normal
 */
public class EjecutarProceso {
    public static void main(String[] args) {
        try{
            Process proceso = Runtime.getRuntime().exec("notepad");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
