package CID.ThickerWaker;

import jade.core.Agent;
import jade.core.behaviours.*;

public class HandsOn5Agent extends Agent{

    private int tickerCount;
    protected void setup(){
        tickerCount = 0;
        System.out.println("Hola! Soy el agente" + getLocalName());

       //Añadimos waker
        addBehaviour(new WakerBehaviour(this,5000){

            protected void handleElapsedTimeout(){
                //Cuando waker finalize, imprime mensaje y añade el ticker behaviour al agente
                System.out.println("Wenos días alegría");

                addBehaviour(new TickerBehaviour(myAgent,2500){

                    protected void onTick(){

                        System.out.println("You are running out if time");
                        tickerCount++;

                        if(tickerCount == 5){
                            System.out.println("Gobai");
                            doDelete();
                        }
                        
                    }
                });
            }
        });
        
    }    

    
}