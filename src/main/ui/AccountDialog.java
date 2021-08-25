package ui;

import model.Account;
import model.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
        This is the dialog shown for account, as button in GUI for account calls for. It can let user add new stocks,
        check for balance as well as check hold stock page.
 */
public class AccountDialog extends JFrame implements ActionListener {


    private JLabel label;
    private JLabel balanceLabel;
    private JTextField field;
    JPanel buttonPane;
    private String message = "Hello user! this is your account page. ";
    private Account thisAccount;

    //EFFECTS: this dialog sets up the dialog for this, as well as calling for initializing buttons on this pane.
    public AccountDialog(Account account) {


        super("Account Dialog");
        thisAccount = account;
        setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane,BoxLayout.PAGE_AXIS));
        label = new JLabel(message);
        field = new JTextField(10);
        String messageBalance = "Your Balance is now: " + account.getBalance();
        balanceLabel = new JLabel(messageBalance);
        listPane.add(label);
        listPane.add(balanceLabel);
        listPane.add(Box.createRigidArea(new Dimension(0,5)));
        buttonInitialize();
        Container contentPane = getContentPane();
        contentPane.add(listPane,BorderLayout.PAGE_START);
        contentPane.add(buttonPane,BorderLayout.CENTER);
        contentPane.add(field,BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
    }

    //MODIFIES: this
    //EFFECTS: adds buttons to the account dialog as well as addressing action listeners for them.
    private void buttonInitialize() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        JButton btnA = new JButton("balance update");
        JButton btnP = new JButton("check buy-in stock value");
        JButton btnS = new JButton("add new stock to market");
        btnA.setActionCommand("myButtonA");
        btnA.addActionListener(this);
        btnP.setActionCommand("myButtonP");
        btnP.addActionListener(this);
        btnS.setActionCommand("myButtonS");
        btnS.addActionListener(this);
        buttonPane.add(btnA);
        buttonPane.add(btnP);
        buttonPane.add(btnS);
    }


    //EFFECTS: setting ActionListeners for different buttons to function and call corresponding dialogs.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButtonA")) {
            balanceLabel.setText("Your Balance is now: " + thisAccount.getBalance());
        }
        if (e.getActionCommand().equals("myButtonP")) {
            new HoldStockDialog(thisAccount);
        }
        if (e.getActionCommand().equals("myButtonS")) {
            String name = field.getText();
            Stock addStock = new Stock(name);
            thisAccount.addStockToStockList(addStock);
        }
    }
}
