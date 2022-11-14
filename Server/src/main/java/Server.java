import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.function.Consumer;

public class Server {
    int count = 1;
    int port;
    ArrayList<ClientThread> clients = new ArrayList<ClientThread>(); // stores clients in order to keep track of them
    TheServer server;
    private Consumer<Serializable> callback; // DO NOT UNDERSTAND

    Server(Consumer<Serializable> call, int port) {
        callback = call; // DO NOT UNDERSTAND
        server = new TheServer();
        server.start();
        this.port = port;
    }

    public class TheServer extends Thread { // Instance of this class will sit in its own thread accepting clients
        public void run() {
            try(ServerSocket mySocket = new ServerSocket(port);) {
                System.out.println("Server is waiting for a client!");

                while (true) {
                    ClientThread client = new ClientThread(mySocket.accept(), count);
                    callback.accept("A Client has connected: Client #" + count); // DO NOT UNDERSTAND
                    clients.add(client);
                    client.start();
                    count++;
                }
            } catch(Exception e) {
                callback.accept("Server"); // DO NOT UNDERSTAND
            }
        }
    }

    public class ClientThread extends Thread {
        Socket connection; // socket to client
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket connection, int count) {
            this.connection = connection;
            this.count = count;
        }
    }
}
