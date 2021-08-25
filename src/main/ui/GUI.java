package ui;

import model.Account;
import persistence.JsonReader;
import persistence.JsonWriter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.IOException;

/*
    This is the main ui page for all GUI. This interface allows user to make changes to their
    account and play with the game. The user now can buy in, sell according to the market price and when finished,
    press next day to progress in the time line, Save and Load progress.
    Note that it would generate a new dialog for each button pressed and the initial page is not hidden nor updated.
*/
public class GUI extends JFrame implements ActionListener {

    private JLabel label;
    private static int day = 0;
    private String message = "Hello user! this is day " + day + " of your stock market.";
    private JLabel outputLabel;

    JPanel buttonPane;

    Account myAccount = new Account(0);
    private static final String JSON_STORE = "./data/accountStore.json";

    private JsonWriter jsonWriter;
    private JsonReader jsonReader;


    //EFFECTS: this GUI main interface calls to activate buttons held in a button pane, constructs the layout and
    //         sets the label messages for output.
    public GUI() {
        super("StockMarket");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JPanel listPane = new JPanel();
        listPane.setLayout(new BoxLayout(listPane,BoxLayout.PAGE_AXIS));
        label = new JLabel(message);
        outputLabel = new JLabel(" ");
        listPane.add(label);
        listPane.add(Box.createRigidArea(new Dimension(0,10)));
        listPane.add(addIcon());
        listPane.add(Box.createRigidArea(new Dimension(0,10)));
        buttonInitialize();
        Container contentPane = getContentPane();
        contentPane.add(listPane,BorderLayout.PAGE_START);
        contentPane.add(buttonPane,BorderLayout.CENTER);
        contentPane.add(outputLabel,BorderLayout.PAGE_END);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
        setResizable(false);
        jsonWriter = new JsonWriter(JSON_STORE);
        jsonReader = new JsonReader(JSON_STORE);
    }

    //MODIFIES: this
    //EFFECTS: Constructs different buttons to setup in a buttonPane, adding them to GUI as well as setting
    //         ActionListeners for them.
    private void buttonInitialize() {
        buttonPane = new JPanel();
        buttonPane.setLayout(new BoxLayout(buttonPane,BoxLayout.LINE_AXIS));
        buttonPane.setBorder(BorderFactory.createEmptyBorder(0,10,10,10));
        JButton btnA = new JButton("account");
        JButton btnP = new JButton("purchase");
        JButton btnS = new JButton("sell");
        JButton market = new JButton("market");
        btnA.setActionCommand("myButtonA");
        btnA.addActionListener(this);
        btnP.setActionCommand("myButtonP");
        btnP.addActionListener(this);
        btnS.setActionCommand("myButtonS");
        btnS.addActionListener(this);
        market.setActionCommand("myButtonM");
        market.addActionListener(this);
        buttonPane.add(btnA);
        buttonPane.add(btnP);
        buttonPane.add(btnS);
        buttonPane.add(market);
        saveLoadInitialize();
    }

    //MODIFIES: this
    //EFFECTS: a helper function of buttonInitialize() to help setup the next day button, save and load buttons
    public void saveLoadInitialize() {
        JButton nextDay = new JButton("next day");
        nextDay.setActionCommand("myButtonND");
        nextDay.addActionListener(this);
        buttonPane.add(nextDay);

        JButton btnSave = new JButton("Save");
        JButton btnLoad = new JButton("Load");
        btnSave.setActionCommand("Save");
        btnSave.addActionListener(this);
        btnLoad.setActionCommand("Load");
        btnLoad.addActionListener(this);
        buttonPane.add(btnSave);
        buttonPane.add(btnLoad);
    }

    //MODIFIES: this
    //EFFECTS: adds an icon to a new JPanel and returns it.
    public JPanel addIcon() {
        ImageIcon icon = new ImageIcon("data/OIP.gif");
        BufferedImage resizedImg = new BufferedImage(96,96,BufferedImage.TYPE_INT_RGB);
        Graphics2D g2 = resizedImg.createGraphics();
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2.drawImage(icon.getImage(),0,0,96,96,null);
        g2.dispose();
        ImageIcon iconResized = new ImageIcon(resizedImg);
        JLabel iconLabel = new JLabel(iconResized);
        JPanel imagePanel = new JPanel();
        imagePanel.add(iconLabel,BorderLayout.CENTER);
        return imagePanel;
    }





    //EFFECTS: setting ActionListeners for different buttons to function and call corresponding dialogs.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("myButtonA")) {
            new AccountDialog(myAccount);
        }
        if (e.getActionCommand().equals("myButtonP")) {
            new PurchaseDialog(myAccount);
        }
        if (e.getActionCommand().equals("myButtonS")) {
            new SellDialog(myAccount);
        }
        if (e.getActionCommand().equals("myButtonM")) {
            new MarketListDialog(myAccount);
        }
        if (e.getActionCommand().equals("myButtonND")) {
            myAccount.getStockList().changeAll();
            day++;
            label.setText("Hello user! this is day " + day + " of your stock market.");
        }
        if (e.getActionCommand().equals("Save")) {
            saveProgress(myAccount);
        }
        if (e.getActionCommand().equals("Load")) {
            loadProgress();
        }
    }

    //MODIFIES: json file
    //EFFECTS: read from account and then write using jsonWriter to a json file, with a label indicating if successful
    private void saveProgress(Account myAccount) {
        try {
            jsonWriter.open();
            jsonWriter.write(myAccount);
            jsonWriter.close();
            System.out.println("Saved to " + JSON_STORE);
            outputLabel.setText("Save finished. ");
        } catch (FileNotFoundException e) {
            System.out.println("Unable to write to file: " + JSON_STORE);
            outputLabel.setText("Save failed. ");
        }
    }

    //MODIFIES: account
    //EFFECTS: read from json file and write to the account information, with a label indicating if successful
    private void loadProgress() {
        try {
            myAccount = jsonReader.read();
            outputLabel.setText("Loading completed. ");
            System.out.println("Loaded from " + JSON_STORE);
        } catch (IOException e) {
            System.out.println("Unable to read from file: " + JSON_STORE);
            outputLabel.setText("Loading failed. ");
        }
    }



    //EFFECTS: calls for GUI and runs the project
    public static void main(String[] args) {
        new GUI();
    }
}
