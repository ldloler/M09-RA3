import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.Socket;

public class Client {
    static private final int PORT = Servidor.PORT;
    static private final InetAddress HOST = Servidor.HOST;

    private Socket socket;
    private PrintWriter out;

    public Client(Socket clientSocket) {
        this.socket = clientSocket;
    }

    public void connecta() {
        try {
            socket.connect(new InetSocketAddress(HOST, PORT));
            out = new PrintWriter(socket.getOutputStream(), true);
            System.out.println("Connectat a servidor en " + HOST + ":" + PORT);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void tanca() {
        out.close();
        try {
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void envia(String msg) {
        out.println(msg);
        System.out.println("Enviant al servidor: " + msg);
    }

    public static void main(String[] args) throws Exception {
        Client client = new Client(new Socket());
        client.connecta();
        client.envia("Prova d'enviament 1");
        client.envia("Prova d'enviament 2");
        client.envia("Adeu!");

        System.out.println("Prem enter per tancar el client");
        System.in.read();
        client.tanca();
    }
}
