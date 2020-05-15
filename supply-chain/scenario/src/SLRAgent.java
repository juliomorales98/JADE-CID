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

public class SLRAgent extends Agent{
    private SLR slr;
    private MySqlCon con;
    double[] y = new double[]{651,762,856,1063,1190,1298,1421,1440,1518};//sales
    double[] x = new double[]{23,26,30,34,43,48,52,57,58};//month

    protected void setup(){        
        slr = new SLR(5);
        con = new MySqlCon();
        con.GenerateConnection();
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

        if(con.GetConnection() == null){
            System.out.println("Conexión con SQL fallida");
        }else{
            System.out.println("Conexión con SQL establecida");          
            
            
            SaveObject so = new SaveObject();
            
            //Eliminamos lo que estaba en la tabla anteriormente
            if(so.DeleteAllFromTable(con.GetConnection())){
                //Insertamos 12 meses
                for(int i = 0; i < 12; i++){
                    int _pieces = (i+1)*10;
                    Random myRandom = new Random();

                    History myHistory = new History(i, (int)myRandom.nextInt(3), null, 3000*_pieces+myRandom.nextInt(999), _pieces+(int)myRandom.nextInt(9));
                    
                    so.setJavaObject((Object)myHistory);
                    try{
                        so.saveObject(con.GetConnection());
                    }catch(Exception e){
                        System.out.println(e);
                    }
                    
                }
                try{
                    List<History> aux = so.getObject(con.GetConnection());
                    double[] x = new double[aux.size()];
                    double[] y = new double[aux.size()];
                    int counter = 0;
                    System.out.println("Mes\tTipo\tPiezas\tTotal");
                    for(History h : aux){
                        System.out.println(h.ToString());
                        x[counter] = h.GetDate().getMonth()+1;
                        y[counter] = h.getNoPieces();
                        counter++;
                    }
                    slr.DoRegression(x, y);
                }catch(Exception e){
                    System.out.println(e);
                }
            }

            
            
        }
    }

    private class MyOneShotRegression extends OneShotBehaviour{
        public void action(){
            System.out.println("Start regression");
            slr.DoRegression(x,y);
        }

        public int onEnd(){
            System.out.println("Bye bye");
            myAgent.doDelete();
            return super.onEnd();
        }
    }
}