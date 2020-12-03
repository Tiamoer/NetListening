import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerListening extends Thread implements component {

    protected static boolean isRun = false;
    protected static boolean startFlag = true;
    private int localPort;
    private ServerSocket serverSocket;
    private Socket socket;
    private String hostName;

    ServerListening(int localPort) {
        this.localPort = localPort;
    }

    @Override
    public void run() {
        try {
            System.out.println(localPort);
            serverSocket = new ServerSocket(localPort);
            Thread.sleep(100);
            statusText.append("正在监听端口"+localPort+"，等待客户机连接...\n");
            while (startFlag) {
                isRun = currentThread().isAlive();
                socket = serverSocket.accept();
                statusBar.setText("侦测到客户机已连接");
                hostName = new String(String.valueOf(socket.getInetAddress()));
                if (localPort == portFrame.getPortNum1())
                    portLamp1.setIcon(green);
                if (localPort == portFrame.getPortNum2())
                    portLamp2.setIcon(green);
                if (localPort == portFrame.getPortNum3())
                    portLamp3.setIcon(green);
                statusText.append("客户机"+hostName+"已连接端口：" + localPort + "\n");
                if (!socket.getKeepAlive()) {
                    statusText.append("客户机已断开连接!\n");
                    statusBar.setText("客户机已断开连接");
                    portLamp1.setIcon(red);
                    portLamp2.setIcon(red);
                    portLamp3.setIcon(red);
                    break;
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public int getLocalPort() {
        return localPort;
    }
}