package pl.parser.nbp;

/**
 *
 * @author Michał Burmer
 */
public class CurrencyPojo {
  
  private String type;
  private double purchaseRate;
  private double sellingRate;

  public CurrencyPojo() {
  }
  
  public CurrencyPojo(String type, double purchaseRate, double sellingRate) {
    this.type = type;
    this.purchaseRate = purchaseRate;
    this.sellingRate = sellingRate;
  }

  public String getCode() {
    return type;
  }

  public void setCode(String type) {
    this.type = type;
  }

  public double getPurchaseRate() {
    return purchaseRate;
  }

  public void setPurchaseRate(double purchaseRate) {
    this.purchaseRate = purchaseRate;
  }

  public double getSellingRate() {
    return sellingRate;
  }

  public void setSellingRate(double sellingRate) {
    this.sellingRate = sellingRate;
  }
  
}
