package com.main;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.*;
import java.awt.event.*;
import java.text.ParseException;

class portFrame extends JFrame implements component {
    private static int portNum1,portNum2,portNum3;
    portFrame() throws ParseException {
        JFrame pFrame = new JFrame("端口配置");
        JLabel port1 = new JLabel("端口1", SwingConstants.CENTER);
        JLabel port2 = new JLabel("端口2", SwingConstants.CENTER);
        JLabel port3 = new JLabel("端口3", SwingConstants.CENTER);
        MaskFormatter maskFormatter = new MaskFormatter("####");
        JFormattedTextField port1_text = new JFormattedTextField(maskFormatter);
        port1_text.setFocusLostBehavior(JFormattedTextField.COMMIT);
        JFormattedTextField port2_text = new JFormattedTextField(maskFormatter);
        port2_text.setFocusLostBehavior(JFormattedTextField.COMMIT);
        JFormattedTextField port3_text = new JFormattedTextField(maskFormatter);
        port3_text.setFocusLostBehavior(JFormattedTextField.COMMIT);
        JButton submit = new JButton("确认");
        Container con = pFrame.getContentPane();
        pFrame.setSize(200, 150);
        pFrame.setLocationRelativeTo(null);
        pFrame.setResizable(false);
        pFrame.setLayout(new GridLayout(4, 2, 10, 5));
        con.add(port1);
        con.add(port1_text);
        con.add(port2);
        con.add(port2_text);
        con.add(port3);
        con.add(port3_text);
        con.add(submit);
        submit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    portNum1 = Integer.parseInt((String) port1_text.getValue());
                    portNum2 = Integer.parseInt((String) port2_text.getValue());
                    portNum3 = Integer.parseInt((String) port3_text.getValue());
                    if(myJFrame.isPortOK(portNum1) && myJFrame.isPortOK(portNum2) && myJFrame.isPortOK(portNum3)) {
                        JOptionPane.showMessageDialog(pFrame,"配置成功！","完成",JOptionPane.INFORMATION_MESSAGE);
                        statusText.append("端口已配置完成：\n"+
                                "端口1："+portNum1+
                                "\n端口2："+portNum2+
                                "\n端口3："+portNum3+"\n");
                        infoLamp1.setText("Port"+portNum1);
                        infoLamp2.setText("Port"+portNum2);
                        infoLamp3.setText("Port"+portNum3);
                        pFrame.setVisible(false);
                    }
                    else if (!myJFrame.isPortOK(portNum1))
                        JOptionPane.showMessageDialog(pFrame,"端口1被占用，请重新选择！","错误",JOptionPane.ERROR_MESSAGE);
                    else if (!myJFrame.isPortOK(portNum2))
                        JOptionPane.showMessageDialog(pFrame,"端口2被占用，请重新选择！","错误",JOptionPane.ERROR_MESSAGE);
                    else if (!myJFrame.isPortOK(portNum3))
                        JOptionPane.showMessageDialog(pFrame,"端口3被占用，请重新选择！","错误",JOptionPane.ERROR_MESSAGE);
                } catch (Exception numError) {
                    JOptionPane.showMessageDialog(pFrame,"请输入正确的四位端口号","Error",JOptionPane.ERROR_MESSAGE);
                    port1_text.setText("");
                    port2_text.setText("");
                    port3_text.setText("");
                }
            }
        });
        pFrame.setVisible(true);
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
}