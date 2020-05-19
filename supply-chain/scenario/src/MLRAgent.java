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

public class MLRAgent extends Agent{
    private MLR mlr;
    private MySqlCon con;
    protected void setup(){        
        mlr = new MLR(5);
        con = new MySqlCon();
        con.GenerateConnection();
        //addBehaviour(new MyOneShotRegression());
        // DescripciÃ³n del agente
        System.out.println("Creo mlr");
        DFAgentDescription descripcion = new DFAgentDescription();
        descripcion.setName(getAID());
        descripcion.addLanguages("Castellano");
 
        // Descripcion de un servicio que proporciona el Agente
        ServiceDescription servicio = new ServiceDescription();
        servicio.setType("Data Analytics");
        servicio.setName("Multiple Regression");
 
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
                    double[][] x = new double[aux.size()][3];
                    int counter = 0;
                    System.out.println("Mes\tTipo\tPiezas\tTotal");
                    for(History h : aux){
                        System.out.println(h.ToString());
                        x[counter][0] = h.GetDate().getMonth()+1;
                        x[counter][1] = h.GetPrice();
                        x[counter][2] = h.getNoPieces();
                        counter++;
                    }
                    mlr.DoRegression(x);
                }catch(Exception e){
                    System.out.println(e);
                }
            }

            
            
        }
    }
}