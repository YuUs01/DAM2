package primeraPractica;

public class EjecutarProceso {
    public static void main(String[] args) {
        try{
            Process proceso = Runtime.getRuntime().exec("notepad");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
