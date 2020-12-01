import javax.swing.*;
import java.awt.event.KeyEvent;

public interface menu {
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
