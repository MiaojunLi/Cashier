/**
 * Example code for DirectoryDriver.
 * @author Miaojun Li, miaojunl
 */
public class FinalData {
    private String itemName;
    private float price;
    private float number;
    private float cost;
    
    public FinalData(String itemName, float price) {
        this.itemName = itemName;
        this.price = price;
        number = 0;
    }
    
    public String getItem() {
        return itemName;
    }
    
    public double getPrice() {
        return price;
    }
    
    public float getCost() {
        cost = number * price;
        return cost;
    }
    
    public void setNumber(float n) {
        this.number = n;
    }
    
// override toString method.
    public String toString() {
        return String
                .format("%.2f %s\t\t at $%.2f each\t\t$%.2f\n", number, itemName,price, cost);
    }

    
}
