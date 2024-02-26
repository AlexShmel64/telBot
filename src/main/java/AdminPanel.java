import org.telegram.telegrambots.meta.api.objects.Message;

import javax.swing.*;
import java.awt.event.*;
import java.util.Map;

public class AdminPanel extends JDialog {
    private JPanel contentPane;
    private JButton buttonExit;
    private JButton buttonCancel;
    private JPanel panel1;
    private JButton startButton;
    private JButton stopButton;
    private JLabel Label1;
    private JLabel Label2;
    private JButton ListOfUsers;
    private JTable table1;
    JFrame frameWindow;
    BotThread botThread;

    public AdminPanel() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonExit);

        buttonExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        // call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

        // call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onStart();
            }
        });
        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onStop();
            }
        });
        stopButton.setEnabled(false);
        ListOfUsers.setEnabled(false);

        ListOfUsers.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onListOfUsers();
            }
        });
    }

    private void onOK() {
        // add your code here
        dispose();
    }

    private void onCancel() {
        // add your code here if necessary
        dispose();
    }
    private void onStart(){
        startButton.setEnabled(false);
        stopButton.setEnabled(true);
        ListOfUsers.setEnabled(true);
        botThread = new BotThread();
        botThread.start();
        Label1.setText("Бот запущен");
        Label2.setText("");
    }
    private void onStop(){
        botThread.stopRun();
        startButton.setEnabled(true);
        stopButton.setEnabled(false);
        ListOfUsers.setEnabled(false);
        Label1.setText("");
        Label2.setText("Бот остановлен");
    }
    private void onListOfUsers(){
        String[] columnNames = {"ID пользователя", "Имя", "Фамилия"};
        String[][] data = new String[botThread.bot.messages.size()][3];
        int i = 0;
        botThread.bot.users.forEach((key, value) -> {
            data[i][0] = key.toString();
            data[i][1] = value.userFirstName;
            data[i][2] = value.userLastName;
        });
        table1 = new JTable(data, columnNames);
        table1.setBounds(30, 40, 200, 300);
        frameWindow = new JFrame();
        frameWindow.setTitle("Список пользователей");
        JScrollPane sp = new JScrollPane(table1);
        frameWindow.add(sp);
        frameWindow.setSize(500, 200);
        frameWindow.setVisible(true);
    }

    public static void main(String[] args) {
        AdminPanel dialog = new AdminPanel();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }

}
