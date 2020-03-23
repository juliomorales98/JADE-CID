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

    public void GetCE(int counter){
       
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

            System.out.println("\n" + counter);

            //Tiene que dar un valor en el rango de 200
            if(status > 299){
                //Imprimimos el error
                reader = new BufferedReader(new InputStreamReader(connection.getErrorStream()));
                while((line = reader.readLine()) != null){
                    responseContent.append(line);
                }
                
                System.out.println("Código: " + status);
                System.out.println(responseContent.toString());
                reader.close();
                
            }else{
                //Imprimimos resultado de api

                //Obtenemos resultado
                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                while((line = reader.readLine()) != null){
                    
                    responseContent.append(line);
                }

                
                //Quitamos caracteres { } , "
                //Vamos imprimiendo línea por línea

                String auxLine = responseContent.toString();
                String aux = "";

                for(int i = 0; i < auxLine.length(); i++){

                    if(auxLine.charAt(i) != '{' && auxLine.charAt(i) != '}' && auxLine.charAt(i) != 34){

                        if(auxLine.charAt(i) == ','){
                            System.out.println(aux);
                            aux = "";

                        }else{
                            aux += auxLine.charAt(i);
                        }
                    }
                }
                reader.close();
            }

        } catch (MalformedURLException e) {
            System.out.println(e);
        } catch (IOException e){
            System.out.println(e);
        }
        
    }
    
    
}