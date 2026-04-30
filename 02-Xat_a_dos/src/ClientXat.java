import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class ClientXat {
    private Socket socket;
    private ObjectOutputStream oos;
    private ObjectInputStream ois;
    
    public void connecta() {
        try {
            socket = new Socket(ServidorXat.HOST, ServidorXat.PORT);
            System.out.println("Client connectat a " + ServidorXat.HOST + ":" + ServidorXat.PORT);
            
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.flush();
            ois = new ObjectInputStream(socket.getInputStream());
            System.out.println("Flux d'entrada i sortida creat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void enviarMissatge(String msg) {
        try {
            System.out.println("Enviant missatge: " + msg);
            oos.writeObject(msg);
            oos.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tancarClient() {
        try {
            System.out.println("Tancant client...");
            if (oos != null) oos.close();
            if (ois != null) ois.close();
            if (socket != null) socket.close();
            System.out.println("Client tancat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ObjectInputStream getOis() {
        return ois;
    }

    public static void main(String[] args) throws Exception {
        ClientXat client = new ClientXat();
        client.connecta();

        FilLectorCX fil = new FilLectorCX(client.getOis());
        fil.start();

        Scanner sc = new Scanner(System.in);
        while (true) {
            System.out.print("Missatge ('sortir' per tancar): ");
            String msg = sc.nextLine();
            client.enviarMissatge(msg);
            if (msg.equalsIgnoreCase(ServidorXat.MSG_SORTIR)) {
                break;
            }
        }
        
        sc.close();
        fil.join();
        client.tancarClient();
    }
}
