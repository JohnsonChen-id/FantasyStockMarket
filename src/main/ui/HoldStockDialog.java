package ui;

import model.Account;
import model.Stock;

import javax.swing.*;
import java.awt.*;

/*
        This is the dialog for hold stock list dialog as a new pane shown for account
 */
public class HoldStockDialog extends JPanel {
    JTable table;

    //EFFECTS: calls a new dialog for new hold stock dialog for account
    public HoldStockDialog(Account myAccount) {
        super(new BorderLayout());
        JFrame frame = new JFrame("Hold Stock List");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel holdPanel = getHoldJPanel(myAccount);
        frame.add(holdPanel);
        frame.pack();
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: assembles most parts of hold stock market and pushes that to main constructor
    public JPanel getHoldJPanel(Account myAccount) {
        tableSetup(myAccount);
        JScrollPane tablePane = new JScrollPane(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel marketPanel = new JPanel();
        marketPanel.setLayout(new BoxLayout(marketPanel,BoxLayout.LINE_AXIS));
        JPanel marketContainer = new JPanel(new GridLayout(1,1));
        marketContainer.setBorder(BorderFactory.createTitledBorder("Hold Stock List"));
        marketContainer.add(tablePane);
        marketPanel.add(marketContainer);
        return marketPanel;
    }

    //EFFECTS: return a table for list of hold stocks to assemble for dialog
    private void tableSetup(Account myAccount) {
        String[] columnNames = {"Name", "Price","Hold Amount"};
        String[][] tableData = new String[myAccount.getHoldStock().size()][3];
        int i = 0;
        for (Stock stock : myAccount.getHoldStock()) {
            int thisLoop = i;
            tableData[thisLoop][0] = stock.getName();
            tableData[thisLoop][1] = String.valueOf(stock.getPrice());
            tableData[thisLoop][2] = String.valueOf(myAccount.getHold().get(stock));
            i++;
        }
        table = new JTable(tableData, columnNames);
    }


}