import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
/**
 * Example code for DirectoryDriver.
 * @author Miaojun Li, miaojunl
 */
public class FinalGUI extends JFrame{
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    /**
     * enum ErrorMsg Message shown if error happen 
     */
    static enum ErrorMsg {
        NEWITEMNAMEMISSING,
        PRICEMISSING,
        AMOUNTMISSING,
        ITEMPURCHASEDMISSING,
        MONEYMISSING,
        AMOUNTINT, 
        AMOUNTTOOSMALL,
        PRICENOTANUMBER,
        PRICETOOSMALL,
        MONEYNOTANUMBER,
        NOTENOUGHCASH,
        ITEMEXIT,
        NOTHISITEM;
        public String toString() {
            switch (this) { 
                case MONEYMISSING:
                    return "*Error: Please enter how much money you pay! *\n";
                case PRICEMISSING:
                    return "*Error: The price of the new item is required! *\n";
                case  ITEMPURCHASEDMISSING:
                    return "*Error: The name of purchased item is required! *\n";              
                case NEWITEMNAMEMISSING:
                    return "*Error: The name of the new item is required! *\n";
                case AMOUNTMISSING:
                    return "*Error: Purchased quantity is required! *\n";
                case AMOUNTINT:
                    return "* Error:The quantity must be an valid number *\n";
                case PRICENOTANUMBER:
                    return "* Error:The price the item should be a valid number *\n ";
                case PRICETOOSMALL:
                    return "*Error:The price is too small,should be larger than 0 *\n";
                case MONEYNOTANUMBER:
                    return "*Error:The cash tendered is not a valid number*\n";
                case AMOUNTTOOSMALL:
                    return "*Error:The quantity should be larger than 0*\n";
                case NOTENOUGHCASH:
                    return "*Error:The cash is not enough to check out*\n";
                case ITEMEXIT:
                    return "*Error:The item is already in sale*\n";
                case NOTHISITEM:
                    return "*Error:The item is not in the stroe*\n";
                default:
                    return "";
            }
        }
    }
    // JPanel
    private JPanel mainPane;
    private JPanel addToStorePane;
    private JPanel addToCustomerPane;
    private JPanel cheakOutPane;
    private JScrollPane resultPane;
    
    // JTextField.
    private JTextField textNewItem;
    private JTextField textPrice;
    private JTextField textAmount;
    private JTextField textPay;
    private JTextField textPurchasedItem;
    // JTextArea
    private JTextArea textDisplay;
    // JButton
    private JButton addToStroeBtn;
    private JButton addToBillBtn;
    private JButton checkOutBtn;
    private JButton clearBtn;
  
    // hashmap for store
    HashMap<String, FinalData> store;

    boolean isInPurchasedView = false;
    float total = 0;
    
    public FinalGUI() {
       
        super("Java Cash Register");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        
        mainPane = new JPanel();
        getContentPane().add(mainPane);
        
        addToStorePaneIni();
        addToCoustomerPaneIni();
        checkOutPaneIni();
        resultPaneIni();
        mainPaneIni();
  
        store = new HashMap<String, FinalData>();
        setSize(720, 500);
        setVisible(true);
        
    }
    
    public void addToStorePaneIni() {
        addToStorePane = new JPanel();
        textNewItem = new JTextField(5);
        textPrice = new JTextField(5);
        addToStorePane.add(new JLabel("Name of New Item to Sell: "));
        addToStorePane.add(textNewItem);
        addToStorePane.add(new JLabel("Price: "));
        addToStorePane.add(textPrice);
        
        addToStroeBtn = new JButton("Add Item to Store");
        addToStroeBtn.addActionListener(new ButtonActionListener());
        
        addToStorePane.add(addToStroeBtn);
    }
    
    public void addToCoustomerPaneIni() {
        addToCustomerPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        
        textAmount = new JTextField(5);        
        addToCustomerPane.add(new JLabel("Quantity Being Purchased: "));
        addToCustomerPane.add(textAmount);
        
        textPurchasedItem = new JTextField(5);
        addToCustomerPane.add(new JLabel("Item Name: "));
        addToCustomerPane.add(textPurchasedItem);
        
        
        addToBillBtn = new JButton("Add Item to Customer Bill");
        addToBillBtn.addActionListener(new ButtonActionListener());
   
        addToCustomerPane.add(addToBillBtn);
    }
    
    public void checkOutPaneIni() {
        cheakOutPane = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 0));
        cheakOutPane.add(new JLabel("Cash Tendered:" ));
        
        textPay = new JTextField(5);
        cheakOutPane.add(textPay);
        
        checkOutBtn = new JButton("Pay");
        checkOutBtn.addActionListener(new ButtonActionListener());
        clearBtn = new JButton("Clear Text Area");
        clearBtn.addActionListener(new ButtonActionListener());
        
        cheakOutPane.add(checkOutBtn);
        cheakOutPane.add(clearBtn);
    }
    
    public void resultPaneIni() {
       
        textDisplay = new JTextArea(15, 60);
        textDisplay.setText("");
        Font font = new Font(Font.MONOSPACED, Font.PLAIN, 12);
        textDisplay.setFont(font);
        textDisplay.setEditable(false);    
        resultPane = new JScrollPane(textDisplay);
        //resultPane.add(textDisplay);
    }
    
    public void mainPaneIni() {
        BoxLayout boxLayout = new BoxLayout(mainPane, BoxLayout.Y_AXIS);
        mainPane.setLayout(boxLayout);
        mainPane.add(addToStorePane);
        mainPane.add(addToCustomerPane);
        mainPane.add(cheakOutPane);
        mainPane.add(resultPane);
    }
    
    private class ButtonActionListener implements ActionListener {
        
        @Override
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == addToStroeBtn) {
                if(fillInCheak1()) {
                    String name = textNewItem.getText();
                    float price = Float.parseFloat(textPrice.getText());
                    System.out.println(price);
                    
                    store.put(name, new FinalData(name, price));
                    System.out.println(name);
                    
                    String output = String
                    .format("New item for sale: %s\t\t at $%.2f\n", name, price);
                    if(textDisplay.getText().contains("*")) textDisplay.setText("");
                    if(isInPurchasedView) textDisplay.setText("");
                    textDisplay.append(output);
                    isInPurchasedView = false;
                    textNewItem.setText("");
                    textPrice.setText("");
                }
            }
            if (e.getSource() == addToBillBtn) {
               if(fillInCheak2()) {
                   String name = textPurchasedItem.getText();
                   float number = Float.parseFloat(textAmount.getText());
                   
                   FinalData finalData = store.get(name);
                   finalData.setNumber(number);
                   
                   if(!isInPurchasedView){
                       isInPurchasedView = true;
                       textDisplay.setText("");
                   }
                   
                   textDisplay.append(finalData.toString());
                   total = total + finalData.getCost();
                   textAmount.setText("");
                   textPurchasedItem.setText("");
               }
            }
            if (e.getSource() == checkOutBtn) {
                if(!isInPurchasedView) {
                    return;
                }
                if(fillInCheak3()) {
                    String str = textPay.getText().trim();
                    float cash = Float.parseFloat(str);
                    float remain = cash - total;
                    textDisplay.append("\n");
                    textDisplay.append(String.format("\t\t\tTotal\t\t$%.02f\n",total));
                    textDisplay.append(String.format("\t\t\tCash Tendered\t$%.02f\n",cash));
                    textDisplay.append(String.format("\t\t\tChange\t\t$%.02f\n",remain));
                    total = 0;
                    isInPurchasedView =true;
                }
            }
            if(e.getSource() == clearBtn) {
                clearTextField();
                isInPurchasedView = false;
            }
        }
    }
    
    public void clearTextField() {
        textDisplay.setText("");
        textPay.setText("");
        textAmount.setText("");
        textPrice.setText("");
        textNewItem.setText("");
        textPurchasedItem.setText("");
    }
    
    public boolean fillInCheak1() {
        // miss item name
        if (textNewItem.getText().trim().length() == 0) {
            textDisplay.setText(ErrorMsg.NEWITEMNAMEMISSING.toString());
            return false;
        }
        // miss price
        if (textPrice.getText().trim().length() == 0) {
            textDisplay.setText(ErrorMsg.PRICEMISSING.toString());
            return false;
        }
        
        if (store.containsKey(textNewItem.getText().trim())) {
            textDisplay.setText(ErrorMsg.ITEMEXIT.toString());
            return false;
        }
        // check valid price
        try {
            String str = textPrice.getText();
            float price = Float.parseFloat(str);
            if (price <= 0) {
                textDisplay.setText(ErrorMsg.PRICETOOSMALL.toString());
                return false;
            }
        } catch (Exception e) {
            textDisplay.setText(ErrorMsg.PRICENOTANUMBER.toString());
            return false;
        }
        return true;
    }
    
    public boolean fillInCheak2() {
        // miss name
        if (textPurchasedItem.getText().trim().length() == 0) {
            textDisplay.setText(ErrorMsg.ITEMPURCHASEDMISSING.toString());
            return false;
        }
        // miss quantity
        if (textAmount.getText().trim().length() == 0) {
            textDisplay.setText(ErrorMsg.AMOUNTMISSING.toString());
            return false;
        }
        
        String name = textPurchasedItem.getText().trim();
        // item doesn't exit
        if (!store.containsKey(name)) {
            textDisplay.setText(ErrorMsg.NOTHISITEM.toString());
            return false;
        }
        
        // check valid amount
        try {
            String str = textAmount.getText();
            float amount = Float.parseFloat(str);
            if (amount <= 0) {
                textDisplay.setText(ErrorMsg.AMOUNTTOOSMALL.toString());
                return false;
            }
        } catch (Exception e) {
            textDisplay.setText(ErrorMsg.AMOUNTINT.toString());
            return false;
        }
        return true;
    }
    
    public boolean fillInCheak3() {
        // miss money
        if (textPay.getText().trim().length() == 0) {
            textDisplay.setText(ErrorMsg.MONEYMISSING.toString());
            return false;
        }

        try {
            // not enough cash
            String str = textPay.getText().trim();
            float cash = Float.parseFloat(str);
            if (cash < total) {
                textDisplay.append(ErrorMsg.NOTENOUGHCASH.toString());
                textDisplay.append("Must be at least:" + total + "\n");
                return false;                      
            }
        } catch (Exception e) {
            textDisplay.setText(ErrorMsg.MONEYNOTANUMBER.toString());
            return false;
        }
        return true;
    }
    
    
    public static void main(String[] args) {
        new FinalGUI();
    }
    
}
