/*
Class that saves the class into the sql database
*/
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;


public class SaveObject {

    public Object javaObject=null;

    public Object getJavaObject() {
        return javaObject;
    }

    public boolean DeleteAllFromTable(Connection con){
        PreparedStatement ps=null;
        String sql=null;
        sql="TRUNCATE TABLE History";
        try{
            ps=con.prepareStatement(sql);
            ps.executeUpdate();
            return true;
        }catch (SQLException e){
            System.out.println(e);
            return false;
        }
        
    }
    public void setJavaObject(Object javaObject) {
        this.javaObject = javaObject;
    }


    public  void saveObject(Connection con) throws Exception
    {
        try{
        Connection conn = con;/// get connection string;
        PreparedStatement ps=null;
        String sql=null;

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(bos);

        oos.writeObject(javaObject);
        oos.flush();
        oos.close();
        bos.close();

        byte[] data = bos.toByteArray();


        sql="INSERT INTO History (javaObject) values(?)";
        ps=conn.prepareStatement(sql);
        ps.setObject(1, data);
        ps.executeUpdate();

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }


    public List<History> getObject(Connection con) throws Exception
    {
        List<History> result=null;
        Connection conn = con;/// get connection string;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String sql=null;

        sql="SELECT * FROM History";

        ps=conn.prepareStatement(sql);

        rs=ps.executeQuery();

        while(rs.next())
        {
            ByteArrayInputStream bais;

            ObjectInputStream ins;

            if(result == null){
                result = new ArrayList<History>();
            }
            try {

                bais = new ByteArrayInputStream(rs.getBytes("javaObject"));

                ins = new ObjectInputStream(bais);

                History historyAux =(History)ins.readObject();

                //System.out.println("Object in value ::"+historyAux.GetDate());
                result.add(historyAux);
                ins.close();

            }
                catch (Exception e) {

                e.printStackTrace();
            }

        }

        return result;
    }
}