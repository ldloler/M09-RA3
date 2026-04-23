import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class Servidor {

    static public final int PORT = 7777;
    static public final InetAddress HOST;

    static {
        try {
            HOST = InetAddress.getByName("localhost");
        } catch (java.net.UnknownHostException e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    private ServerSocket srvSocket;
    private Socket clientSocket;

    public Servidor(ServerSocket srvSocket) throws Exception {
        this.srvSocket = srvSocket;
        System.out.println("Servidor en marxa a " + HOST + ":" + PORT);
    }

    public void connecta() {
        try {
            System.out.println("Esperant connexions a " + HOST + ":" + PORT);
            clientSocket = srvSocket.accept();
            System.out.println("Client connectat: " + clientSocket.getInetAddress());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void repDades() throws IOException {
        String msg;
        BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        while ((msg = in.readLine()) != null) {
            System.out.println("Rebut: " + msg);
        }
    }

    public void tanca() {
        try {
            clientSocket.close();
            srvSocket.close();
            System.out.println("Servidor tancat.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        Servidor serv = new Servidor(new ServerSocket(PORT));

        serv.connecta();
        serv.repDades();
        serv.tanca();
    }
}
