import java.sql.*;

public class MySqlCon{
    private Connection con;
    
    public MySqlCon(){
        //Método que nos dará conexión en la base de datos.
		Connection auxCon = null;
		try{
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/CID", "root", "");
            System.out.println("Connection Succes");   
        }catch(ClassNotFoundException ex){
            System.out.println(ex);
        }catch (SQLException ex){
            System.out.println(ex);
		}		
		con = auxCon;
    }

    public Connection GetConnection(){
        return con;
    }
}