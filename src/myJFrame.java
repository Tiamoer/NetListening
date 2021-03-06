
import javax.swing.*;
import javax.swing.border.EtchedBorder;
import javax.swing.text.Caret;
import javax.swing.text.DefaultCaret;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;

/**
 *  主窗口
 */

public class myJFrame extends JFrame implements menu, component {

    public myJFrame() throws IOException {
        //  菜单栏
        jMenuBar.setBackground(new Color(255, 255, 255));
        option.setMnemonic(KeyEvent.VK_O);
        jMenuBar.add(option);
        set.setMnemonic(KeyEvent.VK_S);
        jMenuBar.add(set);
        about.setMnemonic(KeyEvent.VK_A);
        jMenuBar.add(about);
        start.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, InputEvent.CTRL_MASK));  //设置助记符
        option.add(start);
        stop.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_MASK));
        option.add(stop);
        option.addSeparator();
        exit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_MASK));
        option.add(exit);
        port.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_MASK));
        set.add(port);
        set.add(statusBar_V);
        about.add(aboutMe);
        //  状态灯
        portLamp1.setBounds(150, 80, 50, 50);
        infoLamp1.setBounds(150, 150, 50, 50);
        infoLamp1.setHorizontalAlignment(SwingConstants.CENTER);    //文字水平居中
        portLamp1.setIcon(red);
        portLamp2.setBounds(250, 80, 50, 50);
        infoLamp2.setBounds(250, 150, 50, 50);
        infoLamp2.setHorizontalAlignment(SwingConstants.CENTER);
        portLamp2.setIcon(red);
        portLamp3.setBounds(350, 80, 50, 50);
        infoLamp3.setBounds(350, 150, 50, 50);
        infoLamp3.setHorizontalAlignment(SwingConstants.CENTER);
        portLamp3.setIcon(red);
        //  状态栏
        statusBar.setText("暂无监听任务");
        statusBar.setBorder(BorderFactory.createEtchedBorder(EtchedBorder.RAISED));
        statusBar.setBackground(new Color(255, 255, 255));
        statusBar.setBounds(0, 420, 600, 23);
        //  状态输出窗口
        statusText_Scroll.setBounds(0, 303, 600, 120);
        DefaultCaret caret = (DefaultCaret) statusText.getCaret();
        caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
        statusText.setEditable(false);
        statusText.setBounds(0, 303, 600, 120);
        statusText.setBackground(new Color(0, 0, 0));
        statusText.setForeground(Color.GREEN);
        statusText.setLineWrap(true);
        statusText.setFont(new Font("宋体", Font.PLAIN, 12));
        statusText.setText("****************\n" +
                           "*    WELCOME   *\n" +
                           "****************\n");
        //  窗口
        JFrame jFrame = new JFrame();
        jFrame.setTitle("Network Listening --Tiamoer@outlook.com");
        jFrame.setSize(600, 500);
        jFrame.setLayout(null);
        jFrame.setLocationRelativeTo(null);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jFrame.setJMenuBar(jMenuBar);
        jFrame.add(statusBar);
        jFrame.add(statusText_Scroll);
        jFrame.add(portLamp1);
        jFrame.add(portLamp2);
        jFrame.add(portLamp3);
        jFrame.add(infoLamp1);
        jFrame.add(infoLamp2);
        jFrame.add(infoLamp3);
        jFrame.setVisible(true);
        //  开始按钮点击事件
        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  软件启动后直接点击开始监听，直接报错
                if (new ServerListening(0).getLocalPort() == 0 && portFrame.returnFlag) {
                    JOptionPane.showMessageDialog(jFrame, "请先配置监听端口", "启动失败", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //  如果监听程序正在运行，再次点击会报错
                if (ServerListening.isRun && ServerListening.startFlag) {
                    JOptionPane.showMessageDialog(jFrame, "监听程序正在运行，无需再次点击", "操作失误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                //  当线程处于暂停状态时，再次点击开始监听按钮就会启动线程
                if (!ServerListening.startFlag) {
                    ServerListening.startFlag = true;
                    statusText.append("["+simpleDateFormat.format(new Date())+"]  "+"端口" + portFrame.getPortNum1() + "已启动\n" +
                            "["+simpleDateFormat.format(new Date())+"]  "+"端口" + portFrame.getPortNum2() + "已启动\n" +
                            "["+simpleDateFormat.format(new Date())+"]  "+"端口" + portFrame.getPortNum3() + "已启动\n");
                    statusBar.setText("正在监听端口：" + portFrame.getPortNum1()
                            + "," + portFrame.getPortNum2() + "," + portFrame.getPortNum3());
                    return;
                }
                //  启动监听
                if (ServerListening.startFlag) {
                    new ServerListening(portFrame.getPortNum1()).start();
                    new ServerListening(portFrame.getPortNum2()).start();
                    new ServerListening(portFrame.getPortNum3()).start();
                    statusBar.setText("正在监听端口：" + portFrame.getPortNum1()
                            + "," + portFrame.getPortNum2() + "," + portFrame.getPortNum3());
                } else {
                    JOptionPane.showMessageDialog(jFrame, "请确认端口配置是否正确或查看端口是否被占用", "启动失败", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        stop.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //  停止监听服务
                if (!ServerListening.isRun) {    //检测线程是否开始
                    JOptionPane.showMessageDialog(jFrame, "监听程序未开始！", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                if (!ServerListening.startFlag) {    //判断线程是否结束，已结束，提示错误，未结束，继续执行暂停线程操作
                    JOptionPane.showMessageDialog(jFrame, "监听程序已结束！无需再次停止", "错误", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                ServerListening.startFlag = false;
                portLamp1.setIcon(red);
                portLamp2.setIcon(red);
                portLamp3.setIcon(red);
                statusBar.setText("停止监听");
                statusText.append("["+simpleDateFormat.format(new Date())+"]  "+"端口监听程序已停止！\n");
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (ServerListening.startFlag)
                    JOptionPane.showMessageDialog(jFrame, "请停止监听后再退出程序", "提醒", JOptionPane.ERROR_MESSAGE);
                else System.exit(0);
            }
        });
        port.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    if (portFrame.startFlag) {
                        JOptionPane.showMessageDialog(jFrame, "请退出程序后重新打开程序配置端口", "错误", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                    new portFrame();
                } catch (ParseException parseException) {
                    parseException.printStackTrace();
                }
            }
        });
        statusBar_V.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (statusBar_V.getState()) {
                    statusBar.setVisible(true);
                    jFrame.setSize(600, 500);
                } else {
                    statusBar.setVisible(false);
                    jFrame.setSize(600, 480);
                }
            }
        });
        aboutMe.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //  关于我界面
                class About extends JFrame {
                    final JTextArea jTextArea = new JTextArea();
                    About() {
                        JFrame pFrame = new JFrame("关于我");
                        pFrame.setSize(300, 200);
                        pFrame.setLocationRelativeTo(null);
                        pFrame.setResizable(false);
                        jTextArea.setEditable(false);
                        jTextArea.append("作者：杨鲜银\n" +
                                "学号：20201030311\n" +
                                "程序名称：NetWorkListening\n" +
                                "邮箱：Tiamoer@outlook.com");
                        pFrame.add(jTextArea);
                        pFrame.setVisible(true);
                    }
                }
                new About();
            }
        });
    }

    public static void main(String[] args) throws IOException {
        new myJFrame();
    }
}
