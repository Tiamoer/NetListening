import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListening extends Thread implements component, Runnable {
    static boolean startFlag = true;
    private ServerSocket serverSocket;
    private Socket socket;
    private int localPort;
    static boolean isRun;

    @Override
    public void run() {
        while (startFlag) {
            try {
                isRun = this.isAlive();
                serverSocket = new ServerSocket(localPort);
                statusText.append("端口(" + localPort + ")监听已启动\n");
                localPort = serverSocket.getLocalPort();
                socket = serverSocket.accept();
                if (localPort == portFrame.getPortNum1())
                    portLamp1.setIcon(green);
                if (localPort == portFrame.getPortNum2())
                    portLamp2.setIcon(green);
                if (localPort == portFrame.getPortNum3())
                    portLamp3.setIcon(green);
                statusText.append("客户机已连接" + "端口：" + localPort + "\n");
            }catch (Exception ignored) {}
        }
        try {
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public int getLocalPort() {
        return localPort;
    }

    public void setLocalPort(int localPort) {
        this.localPort = localPort;
    }
}