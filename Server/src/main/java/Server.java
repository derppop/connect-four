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
    }

    public class TheServer extends Thread { // Instance of this class will sit in its own thread accepting clients
        public void run() {
            try(ServerSocket mySocket = new ServerSocket(port);) {
                System.out.println("Server is waiting for a client!");

                while (true) {
                    ClientThread client = new ClientThread(mySocket.accept(), count);
                    callback.accept("A Client has connected: Client #" + count);
                    clients.add(client);
                    if ((count & 1) == 1) { // count is odd, player 1
                        gameInfo.setStatus("Waiting for second player");
                        gameInfo.setPlayer1(true);
                    } else { // count is even, player 2
                        gameInfo.setStatus("Player one's turn");
                        gameInfo.setPlayer2(true);
                    }
                    client.start();
                    count++;
                }
            } catch(Exception e) {
                callback.accept("Server");
            }
        }
    }

    // FUNCTION MIGHT BE BROKEN
    public void updateGameInfo(ClientThread t) {
        if (gameInfo.isPlayer1() && gameInfo.isPlayer2()) { // there are two players
            gameInfo.setStatus("Test");
//            System.out.println("Sending message to player " + t.count);
//            if ((t.count & 1) == 1) { // odd, player 1
//                if (playersTurn == 1) { // it is player 1's turn
//                    gameInfo.setStatus("Player one's turn");
////                    System.out.println("Player 1's turn");
//                    gameInfo.setTurn(true);
////                    System.out.println("Turn is" + gameInfo.isTurn());
//                } else {
//                    gameInfo.setStatus("Player two's turn");
////                    System.out.println("Player 2's turn");
//                    gameInfo.setTurn(false);
////                    System.out.println("Turn is" + gameInfo.isTurn());
//                }
//            } else { // even, player 2
//                if (playersTurn == 2) { // it is player 2's turn
//                    gameInfo.setStatus("Player two's turn");
////                    System.out.println("Player 2's turn");
//                    gameInfo.setTurn(true);
////                    System.out.println("Turn is" + gameInfo.isTurn());
//                } else {
//                    gameInfo.setStatus("Player one's turn");
////                    System.out.println("Player 1's turn");
//                    gameInfo.setTurn(false);
////                    System.out.println("Turn is" + gameInfo.isTurn());
//                }
//            }
        } else {
            System.out.println("Not enough players");
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
                    System.out.println("Sending status to player " + (i + 1) + ": " + gameInfo.getStatus());
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

                    if (playersTurn == 1) { // move turn to next player
                        playersTurn = 2;
                    } else {
                        playersTurn = 1;
                    }
                }
                catch(Exception e) {
                    callback.accept("OOOOPPs...Something wrong with the socket from client: " + count + "....closing down!");
                    clients.remove(this);
                    break;
                }
            }
        }
    }
}
