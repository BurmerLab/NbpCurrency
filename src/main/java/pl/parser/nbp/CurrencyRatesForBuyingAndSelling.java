package pl.parser.nbp;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 *
 * @author Michał Burmer
 */

public class CurrencyRatesForBuyingAndSelling implements Currency {
  
  public double obtainAvgPurchaseRate(ArrayList<CurrencyPojo> currencys){
      
    DescriptiveStatistics purchaseRate = new DescriptiveStatistics();

    for(CurrencyPojo currency: currencys){
      purchaseRate.addValue(currency.getPurchaseRate());
    }

    return purchaseRate.getMean();
  }
    
  public double obtainStandardDeviation(ArrayList<CurrencyPojo> currencys){
    DescriptiveStatistics sellingRate = new DescriptiveStatistics();

    for(CurrencyPojo currency: currencys){
      sellingRate.addValue(currency.getSellingRate());
    }

    return sellingRate.getStandardDeviation();
  }

  private String getFileName(String currenctFileName){
    StringBuilder fileName = new StringBuilder();
    return fileName
            .append("http://www.nbp.pl/kursy/xml/")
            .append(currenctFileName)
            .append(".xml")
            .toString();
  }  

  public ArrayList<CurrencyPojo> obtainAllCurrencyFromDateRange(String startDate, String endDate, String type, String currencyCode)
          throws ParseException, MalformedURLException, Exception{
    
    ArrayList<CurrencyPojo> allCurrency = new ArrayList<CurrencyPojo>();
    ArrayList<String> currencyFileNames = new ArrayList<String>();

    currencyFileNames = obtainNamesFilesFromRange(startDate, endDate, type);
    
    for(String currenctFileName : currencyFileNames){
      String urlAddress = getFileName(currenctFileName);

      URL url = new URL(urlAddress);
      allCurrency.add(obtainCurrencyFromFile(url, currencyCode));
    }
    return allCurrency;
  }

  public static String changeDateToOutputMode(String inputDate) throws ParseException{
    SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd");
    SimpleDateFormat outputFormat = new SimpleDateFormat("yyMMdd");

    inputDate = outputFormat.format(inputFormat.parse(inputDate));
    return inputDate;
  }

  public ArrayList<String> obtainNamesFilesFromRange(String startDate, String endDate, String type) 
          throws MalformedURLException, IOException, IOException{
    
    final URL URL_ADDRESS = new URL("http://www.nbp.pl/kursy/xml/dir.txt");

    ArrayList<String> fileNames = new ArrayList<String>();

    BufferedReader in = new BufferedReader(new InputStreamReader(URL_ADDRESS.openStream()));
    String line, lineType, lineDate;

    int start = Integer.parseInt(startDate);
    int end = Integer.parseInt(endDate);
    boolean flag = false;

    while ((line = in.readLine()) != null) {
      lineType = line.substring(0, 1);
      lineDate = line.substring(5, line.length());

      int dateInThisLine = Integer.parseInt(lineDate);
      
      if(flag && lineType.endsWith(type)){
        fileNames.add(line);
      }

      if(dateInThisLine == start && lineType.equals(type)){
        fileNames.add(line);
        flag=true;
        
      }else if(dateInThisLine == end){
        flag=false;
      }

    }
    in.close();
  return fileNames;
  }

  public CurrencyPojo obtainCurrencyFromFile(URL url, String currencyCode) throws Exception{

    URLConnection connection = url.openConnection();

    Document doc = parseXML(connection.getInputStream());
    NodeList descNodes = doc.getElementsByTagName("pozycja");

    CurrencyPojo currency = new CurrencyPojo();
    for (int temp = 0; temp < descNodes.getLength(); temp++) {

      Node nNode = descNodes.item(temp);

      if (nNode.getNodeType() == Node.ELEMENT_NODE) {

        Element eElement = (Element) nNode;
        String type = eElement.getElementsByTagName("kod_waluty").item(0).getTextContent();

        if(type.equals(currencyCode)){

          NumberFormat format = NumberFormat.getInstance();
          Number eachPurchaseRate = format.parse(eElement.getElementsByTagName("kurs_kupna").item(0).getTextContent());
          Number eachSellingRate = format.parse(eElement.getElementsByTagName("kurs_sprzedazy").item(0).getTextContent());

          currency.setCode(type);
          currency.setPurchaseRate(eachPurchaseRate.doubleValue());
          currency.setSellingRate(eachSellingRate.doubleValue());

//          System.out.println("added: url:"+ url.getPath() + " CODE: " + currencyCode + " purchase: " + currency.getPurchaseRate() + " sell: " + currency.getSellingRate());
          break;
        }
      }
    }
    return currency;
  }

  public Document parseXML(InputStream stream) throws Exception {

    DocumentBuilderFactory objDocumentBuilderFactory = null;
    DocumentBuilder objDocumentBuilder = null;
    Document doc = null;

    try{
      objDocumentBuilderFactory = DocumentBuilderFactory.newInstance();
      objDocumentBuilderFactory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);
      objDocumentBuilder = objDocumentBuilderFactory.newDocumentBuilder();

      doc = objDocumentBuilder.parse(stream);
    }
    catch(Exception ex){
        throw ex;
    }       
    return doc;
  }
}
