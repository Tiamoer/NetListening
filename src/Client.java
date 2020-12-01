import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;

/*
    测试软件是否正常监听的类
 */

public class Client {
    public static void main(String[] args) throws IOException {
        new A(InetAddress.getLocalHost(),8080).start();
        new A(InetAddress.getLocalHost(),1111).start();
        new A(InetAddress.getLocalHost(),9110).start();
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
