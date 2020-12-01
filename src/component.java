import javax.swing.*;

interface component {
    final JTextArea statusText = new JTextArea();   //状态输出台
    final JLabel statusBar = new JLabel();  //状态栏
    final JScrollPane statusText_Scroll = new JScrollPane(statusText);  //状态输出台容器
    final ImageIcon red = new ImageIcon("image/red.png");
    final ImageIcon green = new ImageIcon("image/green.png");
    final JLabel portLamp1 = new JLabel();    //接口1的灯
    final JLabel portLamp2 = new JLabel();
    final JLabel portLamp3 = new JLabel();
    final JLabel infoLamp1 = new JLabel("端口1");   //接口1灯的文字标签
    final JLabel infoLamp2 = new JLabel("端口2");
    final JLabel infoLamp3 = new JLabel("端口3");
}