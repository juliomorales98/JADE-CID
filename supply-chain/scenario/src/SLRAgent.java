import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class SLRAgent extends Agent{
    private SLR slr;
    double[] y = new double[]{651,762,856,1063,1190,1298,1421,1440,1518};//sales
    double[] x = new double[]{23,26,30,34,43,48,52,57,58};//month
    protected void setup(){        
        slr = new SLR();
        addBehaviour(new MyOneShotRegression());
    }

    private class MyOneShotRegression extends OneShotBehaviour{
        public void action(){
            System.out.println("Start regression");
            slr.StartLinealRegression(x,y);
        }

        public int onEnd(){
            System.out.println("Bye bye");
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}