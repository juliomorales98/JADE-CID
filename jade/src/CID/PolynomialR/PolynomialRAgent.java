package CID.PolynomialR;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class PolynomialRAgent extends Agent{
    PolynomialRCalculator qR;

    protected void setup()
    {
        
        addBehaviour(new Servicio());
    }

        private class Servicio extends OneShotBehaviour 
        {
            public void action() 
            {               
                qR = new PolynomialRCalculator();
                System.out.println("Polynomial Regression");
                qR.DoRegression();
                qR.DoPredictions();
            }

            public int onEnd() 
            {
                 myAgent.doDelete();
                 return super.onEnd();
            }
        }
}