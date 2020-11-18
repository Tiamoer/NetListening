package com.main;

import javax.swing.*;
import javax.swing.border.EtchedBorder;
import java.awt.*;
import java.awt.event.*;
import java.net.ServerSocket;
import java.text.ParseException;

/*
    Question：直接点击开始监听会监听0端口
 */

interface menu {
    final JMenuBar jMenuBar = new JMenuBar();
    final JMenu option = new JMenu("选项(O)");
    final JMenuItem start = new JMenuItem("开始监听(A)", KeyEvent.VK_A);
    // final JMenuItem run = new JMenuItem("继续监听(C)", KeyEvent.VK_C);
    final JMenuItem stop = new JMenuItem("停止监听(S)", KeyEvent.VK_S);
    final JMenuItem exit = new JMenuItem("退出系统(Q)", KeyEvent.VK_Q);
    final JMenu set = new JMenu("监听配置(S)");
    final JMenuItem port = new JMenuItem("端口设置(P)", KeyEvent.VK_P);
    final JCheckBoxMenuItem statusBar_V = new JCheckBoxMenuItem("状态栏", true);
    final JMenu about = new JMenu("关于(A)");
    final JMenuItem aboutMe = new JMenuItem("关于我");
}

interface component {
    final JTextArea statusText = new JTextArea();   //状态输出台
    final JLabel statusBar = new JLabel();  //状态栏
    final JScrollPane statusText_Scroll = new JScrollPane(statusText);  //状态输出台容器
    ImageIcon red = new ImageIcon("src/com/main/red.png");
    ImageIcon green = new ImageIcon("src/com/main/green.png");
    JLabel portLamp1 = new JLabel();    //接口1的灯
    JLabel portLamp2 = new JLabel();
    JLabel portLamp3 = new JLabel();
    JLabel infoLamp1 = new JLabel("端口1");   //接口1灯的文字标签
    JLabel infoLamp2 = new JLabel("端口2");
    JLabel infoLamp3 = new JLabel("端口3");
}

public class myJFrame extends JFrame implements menu, component {

    final ServerListening serverListening1 = new ServerListening();
    final ServerListening serverListening2 = new ServerListening();
    final ServerListening serverListening3 = new ServerListening();
    static boolean stopFlag = false;    //判断线程是否停止的标志

    public myJFrame() {
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
        // option.add(run);
        // run.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_C, InputEvent.CTRL_MASK));
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
        statusText_Scroll.setBounds(0, 320, 600, 103);
        statusText.setEditable(false);
        statusText.setBounds(0, 320, 600, 103);
        statusText.setBackground(new Color(0, 0, 0));
        statusText.setForeground(Color.GREEN);
        statusText.setLineWrap(true);
        statusText.setFont(new Font("宋体", Font.PLAIN, 12));
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
        //  菜单点击事件
        start.addActionListener(new ActionListener() {
//            boolean index = false;  // 判断是否为第一次点击开始运行
            @Override
            public void actionPerformed(ActionEvent e) {
                //  启动监听服务
                if (!ServerListening.startFlag && stopFlag) {
                    ServerListening.startFlag = true;
                    statusBar.setText("正在监听端口：" + serverListening1.getLocalPort()
                            + "," + serverListening2.getLocalPort() + "," + serverListening3.getLocalPort());
                    stopFlag = false;
                } else if (isPortOK(serverListening1.getLocalPort()) && isPortOK(serverListening2.getLocalPort()) && isPortOK(serverListening3.getLocalPort()) && ServerListening.startFlag) {
                    serverListening1.setLocalPort(portFrame.getPortNum1());
                    serverListening1.start();
                    serverListening2.setLocalPort(portFrame.getPortNum2());
                    serverListening2.start();
                    serverListening3.setLocalPort(portFrame.getPortNum3());
                    serverListening3.start();
                    statusBar.setText("正在监听端口：" + serverListening1.getLocalPort()
                            + "," + serverListening2.getLocalPort() + "," + serverListening3.getLocalPort());
                    stopFlag = false;
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
                   JOptionPane.showMessageDialog(jFrame,"监听程序未开始！","错误",JOptionPane.ERROR_MESSAGE);
               } else if (stopFlag){    //判断线程是否结束，已结束，提示错误，未结束，继续执行暂停线程操作
                   JOptionPane.showMessageDialog(jFrame,"监听程序已结束！无需再次停止","错误",JOptionPane.ERROR_MESSAGE);
               } else {
                   ServerListening.startFlag = false;
                   portLamp1.setIcon(red);
                   portLamp2.setIcon(red);
                   portLamp3.setIcon(red);
                   statusBar.setText("停止监听");
                   statusText.append("端口监听程序已停止！\n");
                   stopFlag = true;
               }
            }
        });
        exit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.exit(0);
            }
        });
        port.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
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

    //  端口检测方法
    public static boolean isPortOK(int localPort) {
        boolean flag = false;
        try {
            ServerSocket serverSocket = new ServerSocket(localPort);
            flag = true;
        } catch (Exception ignored) {
        }
        return flag;
    }

    public static void main(String[] args) {
        new myJFrame();
    }
}
