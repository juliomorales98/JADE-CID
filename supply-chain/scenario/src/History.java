import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Calendar;

public class History implements Serializable{
    private Date date;
    private String type;//type of configuration
    private List<String> configuration;
    private double price;
    
    public History(int _month, String _type, List<String> _configuration, double _price){
        this.date = new Date();
        /*Calendar cal = Calendar.getInstance();
        cal.setTime(this.date);
        cal.add(Calendar.MONTH, _month);*/
        this.date.setMonth(_month);
        this.type = _type;
        this.configuration = _configuration;
        this.price = _price;
    }

    public Date GetDate(){        
        return this.date;
    }
    public String GetType(){
        return this.type;
    }

    public List<String> GetConfiguration(){
        return this.configuration;
    }

    public double GetPrice(){
        return this.price;
    }
}