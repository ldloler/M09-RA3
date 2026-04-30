import java.io.ObjectInputStream;

public class FilServidorXat extends Thread {
    private ObjectInputStream ois;
    private String nom;

    public FilServidorXat(ObjectInputStream ois, String nom) {
        this.ois = ois;
        this.nom = nom;
    }

    @Override
    public void run() {
        System.out.println("Fil de " + nom + " iniciat");
        try {
            while (true) {
                String missatge = (String) ois.readObject();
                System.out.println("Rebut: " + missatge);
                if (missatge.equalsIgnoreCase(ServidorXat.MSG_SORTIR)) {
                    break;
                }
            }
        } catch (Exception e) {
            // S'ignoren els errors de tancament per part del client
        } finally {
            System.out.println("Fil de xat finalitzat.");
        }
    }
}
