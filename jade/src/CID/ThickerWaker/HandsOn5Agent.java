package CID.ThickerWaker;

import jade.core.Agent;
import jade.core.behaviours.*;

public class HandsOn5Agent extends Agent{

    private int tickerCount;
    CurrencyExchangeInformer cei;
    protected void setup(){
        //tickerCount = 0;
        

       //Añadimos waker
        addBehaviour(new WakerBehaviour(this,3000){

            protected void handleElapsedTimeout(){
                //Cuando waker finalize, imprime mensaje y añade el ticker behaviour al agente
                System.out.println("Welcome! This agent conects to an API that provides realtime exchange rate from Bitcoin to USD.");
                
                cei = new CurrencyExchangeInformer();
                cei.GetCE();
                addBehaviour(new TickerBehaviour(myAgent,15000){

                    protected void onTick(){

                                                
                        cei.GetCE();
                        //tickerCount++;

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