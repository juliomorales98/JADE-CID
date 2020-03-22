package CID.ThickerWaker;

import java.net.HttpURLConnection;
import java.io.BufferedReader;
import java.net.URL;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.io.IOException;

public class CurrencyExchangeInformer{
    private String alphaVantageKey = "XLMVRJ9VOBZPM5J3";
    private String urlApi = "https://www.alphavantage.co/query?function=CURRENCY_EXCHANGE_RATE&from_currency=BTC&to_currency=USD&apikey=";
    private static HttpURLConnection connection;

    public void GetCE(){
        //Method java.net.HttpURLConnection
        try {
            int status;
            BufferedReader reader;
            String line;
            StringBuffer responseContent = new StringBuffer();

            URL url = new URL(urlApi+alphaVantageKey);
            connection = (HttpURLConnection) url.openConnection();

            connection.setRequestMethod("GET");
            connection.setConnectTimeout(5000);
            connection.setReadTimeout(5000);

            status = connection.getResponseCode();
            if(status > 299){
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }

                reader.close();
                
            }else{
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    
                    responseContent.append(line);
                }

                reader.close();
            }

            System.out.println("\n");
            System.out.println(responseContent.toString());

        } catch (MalformedURLException e) {
            
        } catch (IOException e){

        }
        
    }
    
    
}