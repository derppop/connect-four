import java.io.IOException;
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
    private CFourInfo gameInfo;
    int playersTurn = 1;

    Server(Consumer<Serializable> call, int port) {
        callback = call; // DO NOT UNDERSTAND
        server = new TheServer();
        server.start();
        this.port = port;
        gameInfo = new CFourInfo();
        gameInfo.setStatus("Waiting for second player");
    }

    public class TheServer extends Thread { // Instance of this class will sit in its own thread accepting clients
        public void run() {
            try(ServerSocket mySocket = new ServerSocket(port);) {
                System.out.println("Server is waiting for a client!");

                while (true) {
                    ClientThread client = new ClientThread(mySocket.accept(), count);
                    callback.accept("A Client has connected: Client #" + count);
                    clients.add(client);
                    if (count == 1) {
                        gameInfo.setPlayer1(true); // there is one player
                    } else if (count == 2) {
                        gameInfo.setPlayer2(true); // there is two players
                    } else {
                        System.out.println("More than two players, dont know what to do");
                    }
                    client.start();
                    count++;
                }
            } catch(Exception e) {
                callback.accept("Server"); // DO NOT UNDERSTAND
            }
        }
    }

    // this function is broken
    public void updateGameInfo(ClientThread t) {
        if (gameInfo.isPlayer1() && gameInfo.isPlayer2()) {
            if (playersTurn == 1) { // tell player 1 it's their turn and player two it's not
                gameInfo.status = "Player one's turn";
                if(t.count == 1) {
                    gameInfo.setTurn(true);
                } else if (t.count == 2) {
                    gameInfo.setTurn(false);
                }
            } else if (playersTurn == 2) { // tell player 1 it's not their turn and player two that it is
                gameInfo.status = "Player two's turn";
                if(t.count == 1) {
                    gameInfo.setTurn(false);
                } else if (t.count == 2) {
                    gameInfo.setTurn(true);
                }
            }
            System.out.println(gameInfo.getPlayerNum());
        }
        gameInfo.setPlayerNum(t.count);
    }

    public class ClientThread extends Thread {
        Socket connection; // socket to client
        int count;
        ObjectInputStream in;
        ObjectOutputStream out;

        ClientThread(Socket connection, int count){
            this.connection = connection;
            this.count = count;
        }

        public void updateClients() {

            for(int i = 0; i < clients.size(); i++) {
                ClientThread t = clients.get(i);
                updateGameInfo(t);

                try{
                    t.out.writeObject(gameInfo);
                }
                catch (Exception e) {}
            }
        }

        public void run() {
            try {
                in = new ObjectInputStream(connection.getInputStream());
                out = new ObjectOutputStream(connection.getOutputStream());
                connection.setTcpNoDelay(true);
            }
            catch(Exception e) {
                System.out.println("Streams not open");
            }

            updateClients();

            while(true) {
                try {
                    CFourInfo data = (CFourInfo) in.readObject();
                    callback.accept(data.getRecentMove());
                    gameInfo = data;
                    updateClients();

                }
                catch(Exception e) {
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    clients.remove(this);
                    break;
                }
            }
        }
    }

    public int getNumOfClients() {
        return count-1;
    }

    public int getPort() {
        return port;
    }
}
