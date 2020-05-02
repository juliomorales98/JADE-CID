import java.io.File;
import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentController;
import jade.wrapper.ContainerController;

public class Main {
   public static void main(String[] args) {
     
    // Setup the JADE environment
     Profile myProfile = new ProfileImpl();
     Runtime myRuntime = Runtime.instance();
     ContainerController myContainer = myRuntime.createMainContainer(myProfile);
     try {
	// Start the agent controller, which is itself an agent (rma)
	AgentController rma = myContainer.createNewAgent("rma", "jade.tools.rma.rma", null);
	rma.start();

	String[] manufacturer = 
              {"/Users/tony.stark/ProgrammingX/jade/scenarios/supply-chain/scenario/manufacturer.csv"};
	String[] supplier1 = 
              {"/Users/tony.stark/ProgrammingX/jade/scenarios/supply-chain/scenario/supplier1.csv"};
	String[] supplier2 = 
              {"/Users/tony.stark/ProgrammingX/jade/scenarios/supply-chain/scenario/supplier2.csv"};
		

	// Parameter for number of customers
	int c =30;
			
			
	for (int i = 0 ; i < c; i++) {
	   AgentController myCustomer = myContainer.createNewAgent("Customer"+i, Customer.class.getCanonicalName(), null);
	   myCustomer.start();
	}

	AgentController myManufacturer = myContainer.createNewAgent("Manufacturer", Manufacturer.class.getCanonicalName(), manufacturer);
	myManufacturer.start();
			
	AgentController mySupplier1 = myContainer.createNewAgent("Supplier", Supplier.class.getCanonicalName(), supplier1);
	mySupplier1.start();
			
	AgentController mySupplier2 = myContainer.createNewAgent("Supplier2", Supplier.class.getCanonicalName(), supplier2);
	mySupplier2.start();
			

      } catch(Exception e) {
			System.out.println("Exception starting agent: " + e.toString());
      }
   }
}
