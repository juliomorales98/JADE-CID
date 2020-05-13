import java.sql.*;

public class MySqlCon{
    private Connection con;
    
    
    public MySqlCon(){
        
    }

    public void GenerateConnection(){
        //Método que nos dará conexión en la base de datos.
		Connection auxCon = null;
		try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            auxCon = DriverManager.getConnection("jdbc:mysql://localhost:3306/SupplyChain?useTimezone=true&serverTimezone=UTC", "root", "");   
        }catch(ClassNotFoundException ex){
            System.out.println(ex);
        }catch (SQLException ex){
            System.out.println(ex);
		}		
        con = auxCon;
        if(con == null){
            System.out.println("Conexion is null after generating");
        }
    }

    public Connection GetConnection(){
        return con;
    }

   /*public static void main(String[] args) {
        GenerateConnection();
    }*/
}