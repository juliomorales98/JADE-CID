package CID.ThickerWaker;

import jade.core.Agent;
import jade.core.behaviours.*;

public class HandsOn5Agent extends Agent{

    private int tickerCount;
    private CurrencyExchangeInformer cei;

    protected void setup(){

        tickerCount = 0;
        
        addBehaviour(new WakerBehaviour(this,3000){

            protected void handleElapsedTimeout(){
                //Cuando waker finalize, imprime mensaje, crea objeto para los request,
                // hace un request con este y agrega el tickerbehaviour


                System.out.println("Welcome! This agent conects to an API that provides realtime exchange rate from Bitcoin to USD.");
                
                cei = new CurrencyExchangeInformer();
                cei.GetCE(tickerCount);

                addBehaviour(new TickerBehaviour(myAgent,15000){

                    protected void onTick(){

                        tickerCount++;                       
                        cei.GetCE(tickerCount);
                        

                        /*if(tickerCount == 1){
                            System.out.println("Gobai");
                            doDelete();
                        }*/
                        
                    }
                });
            }
        });
        
    }    

    
}