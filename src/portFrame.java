import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.ServerSocket;
import java.text.ParseException;
import java.util.Date;

/**
 *  端口配置窗口，以及端口是否被占用的检测
 */

class portFrame extends JFrame implements component {

    protected static boolean startFlag = false; //判断是否为第一次正确配置端口，第一次配置成功为true，失败为false
    private static int portNum1, portNum2, portNum3;    //保存设置的端口号的变量
    protected static boolean returnFlag = true;    //判断端口是否配置的标记

    final JFrame pFrame = new JFrame("端口配置");
    final JLabel port1 = new JLabel("端口1:");
    final JLabel port2 = new JLabel("端口2:");
    final JLabel port3 = new JLabel("端口3:");
    final JTextField port1_text = new JTextField();
    final JTextField port2_text = new JTextField();
    final JTextField port3_text = new JTextField();
    final JButton submit = new JButton("确认");
    final Container con = pFrame.getContentPane();

    portFrame() throws ParseException {

        //  设置端口输入框限制，只能输入1-5位的数字
        port1_text.setDocument(new NumberJTextField());
        port2_text.setDocument(new NumberJTextField());
        port3_text.setDocument(new NumberJTextField());

        pFrame.setSize(275, 200);
        pFrame.setLocationRelativeTo(null);
        pFrame.setResizable(false);
        pFrame.setLayout(null);
        con.add(port1);
        port1.setBounds(70, 20, 50, 20);
        con.add(port1_text);
        port1_text.setBounds(120, 20, 70, 20);
        con.add(port2);
        port2.setBounds(70, 50, 50, 20);
        con.add(port2_text);
        port2_text.setBounds(120, 50, 70, 20);
        con.add(port3);
        port3.setBounds(70, 80, 50, 20);
        con.add(port3_text);
        port3_text.setBounds(120, 80, 70, 20);
        con.add(submit);
        submit.setBounds(100, 130, 70, 20);

        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                portNum1 = formatText(port1_text.getText(), 1);
                portNum2 = formatText(port2_text.getText(), 2);
                portNum3 = formatText(port3_text.getText(), 3);
                if (portNum1 != 0 && portNum2 != 0 && portNum3 != 0) {
                    try {
                        isPortOK(portNum1);
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(pFrame, "端口1被占用，请重新选择！", "端口占用", JOptionPane.ERROR_MESSAGE);
                        port1_text.setText("");
                        ioException.printStackTrace();
                        return;
                    }
                    try {
                        isPortOK(portNum2);
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(pFrame, "端口2被占用，请重新选择！", "端口占用", JOptionPane.ERROR_MESSAGE);
                        port2_text.setText("");
                        ioException.printStackTrace();
                        return;
                    }
                    try {
                        isPortOK(portNum3);
                    } catch (IOException ioException) {
                        JOptionPane.showMessageDialog(pFrame, "端口3被占用，请重新选择！", "端口占用", JOptionPane.ERROR_MESSAGE);
                        port3_text.setText("");
                        ioException.printStackTrace();
                        return;
                    }
                    returnFlag = false;
                    JOptionPane.showMessageDialog(pFrame, "配置成功！", "完成", JOptionPane.INFORMATION_MESSAGE);
                    statusText.append("["+simpleDateFormat.format(new Date())+"]  "+"端口已配置完成：\n" +
                            "["+simpleDateFormat.format(new Date())+"]  "+"端口1：" + portNum1 +
                            "\n["+simpleDateFormat.format(new Date())+"]  "+"端口2：" + portNum2 +
                            "\n["+simpleDateFormat.format(new Date())+"]  "+"端口3：" + portNum3 + "\n");
                    infoLamp1.setText("Port" + portNum1);
                    infoLamp2.setText("Port" + portNum2);
                    infoLamp3.setText("Port" + portNum3);
                    startFlag = true;
                    pFrame.setVisible(false);
                } else {
                    JOptionPane.showMessageDialog(pFrame, "请输入需要监听的端口！", "错误", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        pFrame.setVisible(true);
    }

    //  判断输入的端口号是否符合的方法
    private int formatText(String str, int n) {
        int temp;
        if (str.equals(""))
            return 0;
        try {
            temp = Integer.parseInt(str);
            if (temp >= 0 && temp <= 65535) {
                return temp;
            }
        } catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(pFrame, "端口" + n + "只能输入0-65535范围内的端口号", "输入错误", JOptionPane.ERROR_MESSAGE);
            switch (n) {
                case 1 -> port1_text.setText("");
                case 2 -> port2_text.setText("");
                case 3 -> port3_text.setText("");
            }
        }
        return 0;
    }

    //  检测端口是否占用的方法
    private static boolean isPortOK(int localPort) throws IOException {
            ServerSocket serverSocket = null;
            serverSocket = new ServerSocket(localPort);
            if (serverSocket != null) {
                serverSocket.close();
                return true;
            }
            return false;
    }

    public static int getPortNum1() {
        return portNum1;
    }

    public static int getPortNum2() {
        return portNum2;
    }

    public static int getPortNum3() {
        return portNum3;
    }

    public static void setPortNum1(int portNum1) {
        portFrame.portNum1 = portNum1;
    }

    public static void setPortNum2(int portNum2) {
        portFrame.portNum2 = portNum2;
    }

    public static void setPortNum3(int portNum3) {
        portFrame.portNum3 = portNum3;
    }
}