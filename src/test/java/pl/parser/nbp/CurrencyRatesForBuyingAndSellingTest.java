package pl.parser.nbp;

import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import junit.framework.TestCase;
import org.w3c.dom.Document;

/**
 *
 * @author Michał Burmer
 */
public class CurrencyRatesForBuyingAndSellingTest extends TestCase {
  
  public CurrencyRatesForBuyingAndSellingTest(String testName) {
    super(testName);
  }
  
  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }
  
  @Override
  protected void tearDown() throws Exception {
    super.tearDown();
  }
  public ArrayList<CurrencyPojo> initialization() throws ParseException, MalformedURLException, Exception{
    
    String type = "c";
    String currencyCode = "EUR";
    String inputStartDate = "2013-01-28";
    String inputEndDate = "2013-01-31";
    
    CurrencyRatesForBuyingAndSelling currencyRatesForBuyingAndSelling = new CurrencyRatesForBuyingAndSelling();
    
    String startDate = CurrencyRatesForBuyingAndSelling.changeDateToOutputMode(inputStartDate);
    String endDate = CurrencyRatesForBuyingAndSelling.changeDateToOutputMode(inputEndDate);
    
    ArrayList<CurrencyPojo> currencys = currencyRatesForBuyingAndSelling.obtainAllCurrencyFromDateRange(startDate, endDate, type, currencyCode);
    return currencys;
  }
  
  public void testObtainAvgPurchaseRate() throws ParseException, MalformedURLException, Exception {
    ArrayList<CurrencyPojo> currencys = initialization();
    
    CurrencyRatesForBuyingAndSelling instance = new CurrencyRatesForBuyingAndSelling();
    double result = instance.obtainAvgPurchaseRate(currencys);
    
    assertNotNull(result);
    assertEquals(4.150525, result);
  }

  public void testObtainStandardDeviation() throws ParseException, MalformedURLException, Exception {
    
    ArrayList<CurrencyPojo> currencys = initialization();
    
    CurrencyRatesForBuyingAndSelling instance = new CurrencyRatesForBuyingAndSelling();
    double result = instance.obtainStandardDeviation(currencys);
    
    assertNotNull(result);
    assertEquals(0.014407260900902389, result);
  }
  
}
