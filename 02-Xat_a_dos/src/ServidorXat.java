import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.*;
import java.util.Scanner;

public class ServidorXat {
    static public final int PORT = 9999;
    static public final String HOST = "localhost";
    static public final String MSG_SORTIR = "sortir";

    private ServerSocket srvSocket;
    public Socket clientSocket;

    public ServidorXat(ServerSocket srvSocket) throws Exception {
        this.srvSocket = srvSocket;
    }

    public void iniciarServidor() {
        try {
            System.out.println("Servidor iniciat a " + HOST + ":" + PORT);
            clientSocket = srvSocket.accept();
            System.out.println("Client connectat: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void pararServidor() {
        try {
            if (clientSocket != null) clientSocket.close();
            if (srvSocket != null) srvSocket.close();
            System.out.println("Servidor aturat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getNom(ObjectInputStream ois, ObjectOutputStream oos) {
        try {
            oos.writeObject("Escriu el teu nom:");
            oos.flush();
            String nom = (String) ois.readObject();
            System.out.println("Nom rebut: " + nom);
            return nom;
        } catch (Exception e) {
            e.printStackTrace();
            return "Desconegut";
        }
    }

    public static void main(String[] args) throws Exception {
        ServidorXat serv = new ServidorXat(new ServerSocket(PORT));

        serv.iniciarServidor();
        
        ObjectOutputStream oos = new ObjectOutputStream(serv.clientSocket.getOutputStream());
        oos.flush();
        ObjectInputStream ois = new ObjectInputStream(serv.clientSocket.getInputStream());
        
        String nom = serv.getNom(ois, oos);
        
        System.out.println("Fil de xat creat.");
        FilServidorXat fil = new FilServidorXat(ois, nom);
        fil.start();
        
        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Missatge ('sortir' per tancar): ");
            String msg = sc.nextLine();
            oos.writeObject(msg);
            oos.flush();
            if (msg.equalsIgnoreCase(MSG_SORTIR)) {
                break;
            }
        }
        
        fil.join();
        serv.pararServidor();
        sc.close();
    }
}
