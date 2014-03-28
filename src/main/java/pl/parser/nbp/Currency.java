package pl.parser.nbp;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.util.ArrayList;

/**
 *
 * @author Michał Burmer
 */

public interface Currency {
  
  public CurrencyPojo obtainCurrencyFromFile(URL url, String currencyCode) throws Exception;
  
  public ArrayList<String> obtainNamesFilesFromRange(String startDate, String endDate, String type) throws MalformedURLException, IOException, IOException;
  
  public ArrayList<CurrencyPojo> obtainAllCurrencyFromDateRange(String startDate, String endDate, String type, String currencyCode) throws ParseException, MalformedURLException, Exception;
  
}
