import java.io.ObjectInputStream;

public class FilLectorCX extends Thread {
    private ObjectInputStream ois;

    public FilLectorCX(ObjectInputStream ois) {
        this.ois = ois;
    }

    @Override
    public void run() {
        System.out.println("Fil de lectura iniciat");
        try {
            while (true) {
                String missatge = (String) ois.readObject();
                System.out.println("Rebut: " + missatge);
                if (missatge.equalsIgnoreCase(ServidorXat.MSG_SORTIR)) {
                    break;
                }
            }
        } catch (Exception e) {
            System.out.println("El servidor ha tancat la connexió.");
        }
    }
}
