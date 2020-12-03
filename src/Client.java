import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/*
    测试软件是否正常监听的类
 */

public class Client {
    public static void main(String[] args) throws IOException {
        new A(InetAddress.getLocalHost(),3124).start();
        new A(InetAddress.getLocalHost(),5342).start();
        new A(InetAddress.getLocalHost(),9548).start();
    }
}

class A extends Thread {
    private final InetAddress host;
    private final int port;
    private Socket socket;

    A(InetAddress host, int port) {
        this.host = host;
        this.port = port;
    }
    @Override
    public void run() {
        try {
            socket = new Socket(host,port);
            socket.setKeepAlive(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
