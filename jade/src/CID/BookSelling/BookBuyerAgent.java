/*****************************************************************
JADE - Java Agent DEvelopment Framework is a framework to develop 
multi-agent systems in compliance with the FIPA specifications.
Copyright (C) 2000 CSELT S.p.A. 

GNU Lesser General Public License

This library is free software; you can redistribute it and/or
modify it under the terms of the GNU Lesser General Public
License as published by the Free Software Foundation, 
version 2.1 of the License. 

This library is distributed in the hope that it will be useful,
but WITHOUT ANY WARRANTY; without even the implied warranty of
MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
Lesser General Public License for more details.

You should have received a copy of the GNU Lesser General Public
License along with this library; if not, write to the
Free Software Foundation, Inc., 59 Temple Place - Suite 330,
Boston, MA  02111-1307, USA.
 *****************************************************************/

package CID.BookSelling;

import jade.core.Agent;
import jade.core.AID;
import jade.core.behaviours.*;
import jade.lang.acl.ACLMessage;
import jade.lang.acl.MessageTemplate;
import jade.domain.DFService;
import jade.domain.FIPAException;
import jade.domain.FIPAAgentManagement.DFAgentDescription;
import jade.domain.FIPAAgentManagement.ServiceDescription;
import java.sql.*;//para sqls

public class BookBuyerAgent extends Agent {
	// The title of the book to buy
	private String targetBookTitle;
	// The list of known seller agents
	private AID[] sellerAgents;

	//Variables para crud
	private PreparedStatement ps;
	private ResultSet rs;

	// Put agent initializations here
	protected void setup() {
		// Printout a welcome message
		System.out.println("Hallo! Buyer-agent "+getAID().getName()+" is ready.");

		// Get the title of the book to buy as a start-up argument
		Object[] args = getArguments();
		if (args != null && args.length > 0) {
			targetBookTitle = (String) args[0];
			System.out.println("Target book is "+targetBookTitle);

			// Add a TickerBehaviour that schedules a request to seller agents every minute
			addBehaviour(new TickerBehaviour(this, 5000) {//Reducimos el ticker
				protected void onTick() {
					System.out.println("Trying to buy "+targetBookTitle);
					// Update the list of seller agents
					DFAgentDescription template = new DFAgentDescription();
					ServiceDescription sd = new ServiceDescription();
					sd.setType("book-selling");
					template.addServices(sd);
					try {
						DFAgentDescription[] result = DFService.search(myAgent, template); 
						System.out.println("Found the following seller agents:");
						sellerAgents = new AID[result.length];
						for (int i = 0; i < result.length; ++i) {
							sellerAgents[i] = result[i].getName();
							System.out.println(sellerAgents[i].getName());
						}
					}
					catch (FIPAException fe) {
						fe.printStackTrace();
					}

					// Perform the request
					myAgent.addBehaviour(new RequestPerformer());
				}
			} );
		}
		else {
			// Make the agent terminate
			System.out.println("No target book title specified");
			doDelete();
		}
	}

	private static Connection GetConnection(){
		//Llamamos este método para que nos de una conexión a la base de datos.
		Connection con = null;
		
		try{
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CID", "root", "");

		}catch(ClassNotFoundException ex){
			System.out.println(ex);
		}catch(SQLException ex){
			System.out.println(ex);
		}

		return con;
	}

	// Put agent clean-up operations here
	protected void takeDown() {
		// Printout a dismissal message
		System.out.println("Buyer-agent "+getAID().getName()+" terminating.");
	}

	/**
	   Inner class RequestPerformer.
	   This is the behaviour used by Book-buyer agents to request seller 
	   agents the target book.
	 */
	private class RequestPerformer extends Behaviour {
		private AID bestSeller; // The agent who provides the best offer 
		private int bestPrice;  // The best offered price
		private int repliesCnt = 0; // The counter of replies from seller agents
		private MessageTemplate mt; // The template to receive replies
		private int step = 0;

		public void action() {
			//targetBookTitle
			Connection con = null;
				try{
					con = GetConnection();
					

					ps = con.prepareStatement("SELECT * from CID.catalogue WHERE title = ?");
					ps.setString(1,targetBookTitle);

					rs = ps.executeQuery();

					//Primero buscamos si el libro existe
					if(rs.next()){
						//El libro ya está agregado
						int idLibro = rs.getInt("id");
						int cantidadLibro = rs.getInt("inventory");

						System.out.println("Libro encontrado");

						//Validamos si tiene en inventario
						if(cantidadLibro == 0){
							System.out.println("Libro no tiene disponibles");
							con.close();
							return;
						}

						//Si sí tiene inventario le restamos uno
						ps = con.prepareStatement("UPDATE CID.catalogue SET inventory = ? WHERE id = ?");
						ps.setString(1,Integer.toString(cantidadLibro-1));
						ps.setInt(2,idLibro);

						int res = ps.executeUpdate();

						if(res > 0){
							//Si no da error, cerramos conexión y matamos al agente.
							System.out.println("Compró " + targetBookTitle);
							con.close();
							myAgent.doDelete();
							
						}else{
							System.out.println("Error al comprar");
						}
					}else{
						System.out.println("Libro no existe");
					}

					con.close();
				}catch(Exception e){
					System.out.println(e);
				}
			       
		}

		public boolean done() {
			if (step == 2 && bestSeller == null) {
				System.out.println("Attempt failed: "+targetBookTitle+" not available for sale");
			}
			return ((step == 2 && bestSeller == null) || step == 4);
		}
	}  // End of inner class RequestPerformer
}
