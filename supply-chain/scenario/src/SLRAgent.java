import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
//imports para directory facilitator
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;

public class SLRAgent extends Agent{
    private SLR slr;
    double[] y = new double[]{651,762,856,1063,1190,1298,1421,1440,1518};//sales
    double[] x = new double[]{23,26,30,34,43,48,52,57,58};//month
    protected void setup(){        
        slr = new SLR();
        //addBehaviour(new MyOneShotRegression());
        // DescripciÃ³n del agente
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.setName(getAID());
        descripcion.addLanguages("Castellano");
 
        // Descripcion de un servicio que proporciona el Agente
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType("Data Analytics");
        servicio.setName("Linear Regression");
 
        // AÃ±ade dicho servicio a la lista de servicios de la descripciÃ³n del agente
        descripcion.addServices(servicio);       
 
        try {
            //Registrando el Agente con sus respectivo servicios
            DFService.register(this, descripcion);
        }
        catch (FIPAException e) {
            e.printStackTrace();
        }
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