package pl.parser.nbp;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;

/**
 *
 * @author Michał Burmer
 */
public class MainClass {
  
  public static void main(String[] args){
      try {
        String type = "c";

        String currencyCode = args[0];
        String inputStartDate = args[1];
        String inputEndDate = args[2];
        
//        String currencyCode = "EUR";
//        String inputStartDate = "2013-01-28";
//        String inputEndDate = "2013-01-31";
        
        String startDate = CurrencyRatesForBuyingAndSelling.changeDateToOutputMode(inputStartDate);
        String endDate = CurrencyRatesForBuyingAndSelling.changeDateToOutputMode(inputEndDate);
        
        CurrencyRatesForBuyingAndSelling currencyRatesForBuyingAndSelling = new CurrencyRatesForBuyingAndSelling();
        
        ArrayList<CurrencyPojo> currencys = new ArrayList<CurrencyPojo>();
        
        currencys = currencyRatesForBuyingAndSelling.obtainAllCurrencyFromDateRange(startDate, endDate, type, currencyCode);
        
        double averagePurchaseRate = currencyRatesForBuyingAndSelling.obtainAvgPurchaseRate(currencys);
        double standardDeviation = currencyRatesForBuyingAndSelling.obtainStandardDeviation(currencys);
        
        NumberFormat formatter = new DecimalFormat("#0.0000");
        System.out.println(formatter.format(averagePurchaseRate));
        System.out.println(formatter.format(standardDeviation));
        
        
      } catch (Exception e) {
          e.printStackTrace();
      }
    }
}
