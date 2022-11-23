import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.util.function.Consumer;



public class Client extends Thread{

    Socket socketClient;

    ObjectOutputStream out;
    ObjectInputStream in;

    CFourInfo gameInfo;

    String ipAddress;

    int port;

    int playerNum;

    private Consumer<Serializable> callback;

    Client(Consumer<Serializable> call, String ipAddress, int port){
        callback = call;
        this.ipAddress = ipAddress;
        this.port = port;

    }

    public void run() {

        try {
            socketClient= new Socket(ipAddress,port);
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);
        }

        catch(Exception e) {}

        while(true) {
            try {
                CFourInfo tempGameInfo = (CFourInfo) in.readObject();
                gameInfo = tempGameInfo;
                playerNum = gameInfo.getPlayerNum();
                System.out.println("On player " + playerNum + ", received status: " + tempGameInfo.getStatus());

                callback.accept(tempGameInfo);
            }
            catch(Exception e) {
                System.out.println("ERROR RECEIVING VALUE");
            }
        }

    }

    public void send(CFourInfo data) {

        try {
            out.writeObject(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public CFourInfo getGameInfo() {
        return gameInfo;
    }

    public int getPlayerNum() {
        return playerNum;
    }
}
