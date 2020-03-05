package CID.lineal_regression;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class SimpleLinealRegressionAgent extends Agent{

    protected void setup(){
        System.out.println("\n\n---------------------------------------");
        System.out.println("Hola! soy " + getLocalName());
        addBehaviour(new MyOneShotBehaviour());
    }

    private class MyOneShotBehaviour extends OneShotBehaviour {

        public void action() {
            SimpleLinealRegression slr = new SimpleLinealRegression();
            slr.StartLinealRegression();
            //System.out.println("Agent's action method executed");
        } 
        
        public int onEnd() {
            myAgent.doDelete();
            return super.onEnd();
        } 
    }

}