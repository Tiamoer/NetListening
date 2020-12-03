import javax.swing.text.AttributeSet;
import javax.swing.text.PlainDocument;

/**
 *  限制端口输入框输入内容的类
 */
public class NumberJTextField extends PlainDocument {
    NumberJTextField() {
        super();
    }
    public void insertString(int offset, String str, AttributeSet attr) throws javax.swing.text.BadLocationException {
        if (str == null) {
            return;
        }
        // 过滤非数字和超出5位的内容
        if (getLength()+str.length() <= 5) {
            char[] s = str.toCharArray();
            int length = 0;
            for (int i = 0; i < s.length; i++) {
                if ((s[i] >= '0') && (s[i] <= '9')) {
                    s[length++] = s[i];
                }
            }
            // 插入内容
            super.insertString(offset, new String(s, 0, length), attr);
        }
    }
}
