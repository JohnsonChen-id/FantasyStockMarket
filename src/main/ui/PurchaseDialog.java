package ui;

import model.Account;
import model.Stock;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/*
        This is the dialog for purchasing stocks from market stock list, which is called by button in main GUI.
 */
public class PurchaseDialog extends JPanel implements ActionListener {

    private JLabel label;
    private JTextField field;
    private String message = "Which Stock do you want to purchase?";
    private String amount = "How many do you want to purchase?";
    private JTextField amountField;
    Account thisAccount;

    //EFFECTS: calls for new purchase dialog and then prompts user to input which stock to buy in while showing the
    //         market stock list.
    public PurchaseDialog(Account myAccount) {

        super(new BorderLayout());
        thisAccount = myAccount;
        MarketListDialog mld = new MarketListDialog(myAccount);
        JFrame frame = new JFrame("Purchase Dialog");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel marketPanel = mld.getMarketJPanel(myAccount);
        JLabel amountLabel = getJLabel();
        Container contentPane = frame.getContentPane();
        contentPane.add(marketPanel,BorderLayout.CENTER);
        JPanel inputPane = new JPanel();
        JButton enterButton = new JButton("Enter");
        enterButton.setActionCommand("myButtonE");
        enterButton.addActionListener(this);
        inputPane.add(label);
        inputPane.add(field);
        inputPane.add(amountLabel);
        inputPane.add(amountField);
        inputPane.add(enterButton);
        contentPane.add(inputPane,BorderLayout.PAGE_END);
        frame.pack();
        frame.setVisible(true);



    }

    //EFFECTS: returns JLabels for the main function
    private JLabel getJLabel() {
        label = new JLabel(message);
        JLabel amountLabel = new JLabel(amount);
        field = new JTextField(10);
        amountField = new JTextField(10);
        return amountLabel;
    }

    //MODIFIES: Account
    //EFFECTS: setting ActionListeners for different buttons to function and call corresponding dialogs.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButtonE")) {
            String name = field.getText();
            int amount = Integer.parseInt(amountField.getText());
            Stock stock = thisAccount.getStockList().getStockByName(name);
            thisAccount.buyStock(stock,amount);
        }
    }
}
