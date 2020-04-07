package CID.MultipleLR;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class MLRAgent extends Agent{
    MLRCalculator calculator;

    protected void setup(){
        System.out.println("Hi! I'm " + getLocalName() + ", the Multiple Lineal Regression Calculator.");
        addBehaviour(new MyOneShot());

        
    }

    private class MyOneShot extends OneShotBehaviour{
        public void action(){
            calculator = new MLRCalculator();
            calculator.DoRegression();
        }

        public int onEnd(){
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}