package ui;

import model.Account;
import model.Stock;

import javax.swing.*;
import java.awt.*;

/*
        This is the dialog for market list dialog as a new pane shown for the whole market list
 */
public class MarketListDialog extends JPanel {
    JTable table;


    //EFFECTS: calls a new dialog for new market list dialog showing all stocks
    public MarketListDialog(Account myAccount) {
        super(new BorderLayout());
        JFrame frame = new JFrame("Market Stock List");
        frame.setDefaultCloseOperation(JFrame.HIDE_ON_CLOSE);
        JPanel marketPanel = getMarketJPanel(myAccount);
        frame.add(marketPanel);
        frame.pack();
        frame.setVisible(true);
    }

    //MODIFIES: this
    //EFFECTS: assembles most parts of market panel and pushes that to main constructor
    public JPanel getMarketJPanel(Account myAccount) {
        marketListTable(myAccount);
        JScrollPane tablePane = new JScrollPane(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JPanel marketPanel = new JPanel();
        marketPanel.setLayout(new BoxLayout(marketPanel,BoxLayout.LINE_AXIS));
        JPanel marketContainer = new JPanel(new GridLayout(1,1));
        marketContainer.setBorder(BorderFactory.createTitledBorder("Market"));
        marketContainer.add(tablePane);
        marketPanel.add(marketContainer);
        return marketPanel;
    }

    //EFFECTS: return a table for list of market to assemble for dialog
    public void marketListTable(Account myAccount) {
        String[] columnNames = {"Name", "Price"};
        String[][] tableData = new String[myAccount.getStockList().getStocks().size()][2];

        for (int i = 1; i <= myAccount.getStockList().getStocks().size(); i++) {
            Stock stock = myAccount.getStockList().getStocks().get(i - 1);
            tableData[i - 1][0] = stock.getName();
            tableData[i - 1][1] = String.valueOf(stock.getPrice());
        }
        table = new JTable(tableData, columnNames);

    }


}