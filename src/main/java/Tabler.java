import javax.swing.*;
import java.awt.*;

public class Tabler extends JFrame {

    static String conStr = "jdbc:postgresql://localhost:5432/DinoBase";
    static String login = "hay";
    static String pwd = "123";

    private JTextField nameField;
    private JTextField firstColumnField;
    private JTextField secondColumnField;
    private JButton createButt;
    private JLabel tableNameLabel;
    private JLabel column1Label;
    private JLabel column2Label;

    public Tabler(){
        setTitle("tabler");
        setVisible(true);
        setSize(333,333);
        setLocation(123,234);
        JPanel panel = new JPanel(new GridLayout(5, 3));
        panel.setSize(333,333);
        panel.add(firstColumnField);
        panel.add(secondColumnField);
        panel.add(nameField);
        panel.add(createButt);
    }

    public void start(){
        setTitle("tabler");
        setVisible(true);
        setSize(333,333);
        setLocation(123,234);
        JPanel panel = new JPanel(new GridLayout(3, 4));
        panel.setSize(333,333);
        panel.add(nameField);
        panel.add(tableNameLabel);
        panel.add(firstColumnField);
        panel.add(column1Label);
        panel.add(secondColumnField);
        panel.add(column2Label);
        panel.add(createButt);



        panel.setVisible(true);
        add(panel);
    }
    public static void main(String[] args) {
        Tabler tabler = new Tabler();
        tabler.start();
    }
}
