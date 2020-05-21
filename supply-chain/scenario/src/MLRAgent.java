import java.util.List;
import java.util.Random;
//jade
import java.util.Date;
import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;
//imports para directory facilitator
import jade.core.AID;
import jade.domain.DFService;
import jade.domain.FIPAAgentManagement.*;
import jade.domain.FIPAException;
// CFP
import jade.core.behaviours.CyclicBehaviour;
import jade.core.behaviours.TickerBehaviour;
import jade.domain.DFService;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;

public class MLRAgent extends Agent {
    private MLR mlr;
    private MySqlCon con;
    private int noPredictions = 5;

    protected void setup() {
        mlr = new MLR(5);
        con = new MySqlCon();
        con.GenerateConnection();
        // addBehaviour(new MyOneShotRegression());
        // DescripciÃ³n del agente
        System.out.println("Creo mlr");
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.setName(getAID());
        descripcion.addLanguages("Castellano");

        // Descripcion de un servicio que proporciona el Agente
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType("MLR-Prediction");
        servicio.setName("Multiple Regression");

        // AÃ±ade dicho servicio a la lista de servicios de la descripciÃ³n del agente
        descripcion.addServices(servicio);

        try {
            // Registrando el Agente con sus respectivo servicios
            DFService.register(this, descripcion);
        } catch (FIPAException e) {
            e.printStackTrace();
        }

        if (con.GetConnection() == null) {
            System.out.println("Conexión con SQL fallida");
        } else {
            System.out.println("Conexión con SQL establecida");
        }

        addBehaviour(new MultiplePredictionRequest());

        addBehaviour(new MultiplePredictionDo());
    }

    // Behaviour which deals with processing predictions from manufacturers
    private class MultiplePredictionRequest extends CyclicBehaviour {
        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.CFP);
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                // recieved and processing cfp msg
                String title = msg.getContent();
                ACLMessage reply = msg.createReply();
                // Agent sends number of predictions as the content of the reply

                reply.setPerformative(ACLMessage.PROPOSE);
                reply.setContent(String.valueOf(noPredictions));

                myAgent.send(reply);
            } else {
                block();
            }
        }
    }

    // class which deals with completing predictions from manufacturer
    private class MultiplePredictionDo extends CyclicBehaviour {

        public void action() {
            MessageTemplate mt = MessageTemplate.MatchPerformative(ACLMessage.ACCEPT_PROPOSAL);
            ACLMessage msg = myAgent.receive(mt);

            if (msg != null) {
                // ACCEPT_PROPOSAL received from manufacturer
                String dataReceived = msg.getContent();
                ACLMessage reply = msg.createReply();

                // do regression and return results
                reply.setPerformative(ACLMessage.INFORM);

                double aux[] = new double[36];
                int contador = 0;
                String auxD = "";
                for (int i = 0; i < dataReceived.length(); i++) {
                    if (dataReceived.charAt(i) != '[') {
                        if (dataReceived.charAt(i) == ',' || dataReceived.charAt(i) == ']') {
                            aux[contador] = Double.parseDouble(auxD);
                            contador++;
                            auxD = "";
                        } else {
                            auxD += dataReceived.charAt(i);
                        }
                    }
                }
                
                 //System.out.println("AUX"); for(double d : aux){ System.out.println(d); }
                 
                double[][] data = new double[12][3];
                int counter = 0;
                int times = 0;
                for (double d : aux) {
                    data[counter][times] = d;
                    times++;
                    if (times == 3) {
                        times = 0;
                        counter++;
                    }
                }
                /*
                 * System.out.println("Data"); for(double[] d : data){ System.out.println(d[0]);
                 * System.out.println(d[1]); }
                 */

                reply.setContent(mlr.DoRegression(data));
                myAgent.send(reply);
            } else {
                block();
            }
        }
    }
}