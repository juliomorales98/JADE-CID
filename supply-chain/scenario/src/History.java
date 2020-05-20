import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class History implements Serializable{
    private Date date;
    private String type;//type of configuration
    private String configuration;
    private double price;
    private int noPieces;
    
    public History(int _month, int _type, String _configuration, double _price, int _pieces){
        this.date = new Date();        
        this.date.setMonth(_month);
        switch(_type){
            case 1:this.type = "Server";
                break;
            case 2:this.type = "Desktop";
                break;
            case 3:this.type = "Laptop";
                break;
            default:this.type ="Laptop";
            
        }
        
        this.configuration = _configuration;
        this.price = _price;
        this.noPieces = _pieces;
    }

    public Date GetDate(){        
        return this.date;
    }
    public String GetType(){
        return this.type;
    }

    public String GetConfiguration(){
        return this.configuration;
    }

    public double GetPrice(){
        return this.price;
    }

    public int getNoPieces(){
        return this.noPieces;
    }

    public String ToString(){
        return String.valueOf(this.date.getMonth()) + "," +
        this.type + "," +
        this.configuration + "," +
        String.valueOf(this.price) + "," +
        String.valueOf(this.noPieces);
    }
}