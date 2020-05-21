import javax.naming.InitialContext;
import java.util.Scanner;

public class MLR{
    private static double[][] dataSet;

    private static double n; 
    private static int noPredictions;
    private static double sumX1;
    private static double sumX2;
    private static double sumY;
    private static double sumX1Y;
    private static double sumX2Y;       
    private static double sumX1X2;
    private static  double sumPowX1;
    private static double sumPowX2;
    
    private static double determinante;

    private static double b0;
    private static double b1;
    private static double b2;

    private static double changeAverageX1;
    private static double changeAverageX2;

    public MLR(int _noP){
        noPredictions = _noP;
    }

    private static void PrintData(double[][] m){
        System.out.println("x1\tx2\ty");
        for(int i = 0; i < m.length; i++){
            for(int j = 0; j < m[0].length; j++){
                System.out.print(Math.round(m[i][j]));
                System.out.print("\t");
            }
            System.out.println();
        }
    }

    private static void PrintData(double[] m){
        for(int i = 0; i < m.length; i++){
            System.out.println("| " + m[i]);    
        }
    }

    private static double Sumatory(int index){
        double res = 0;
        for(int i = 0; i < dataSet.length; i++){
            res += dataSet[i][index];
        }

        return res;
    }

    private static double Sumatory(int index1, int index2){
        double res = 0;
        for(int i = 0; i < dataSet.length; i++){
            res += dataSet[i][index1] * dataSet[i][index2];
        }

        return res;
    }

    private static double SumatoryPow(int index){
        double res = 0;
        for(int i = 0; i < dataSet.length; i++){
            res += Math.pow(dataSet[i][index], 2);
        }

        return res;
    }

    private static double Average(int index){
        return Sumatory(index) / dataSet.length;
    }

    private static double ChangeAverage(int index){
        double res = 0;

        for(int i = 0; i < dataSet.length-1; i++){
            res += dataSet[i+1][index] - dataSet[i][index];
        }

        return res / dataSet.length;
    }

    private static void InitializeVariables(){
        n = (double) dataSet.length; 
        
        sumX1 = Sumatory(0);
        sumX2 = Sumatory(1);
        sumY = Sumatory(2);
        sumX1Y = Sumatory(0,2);
        sumX2Y = Sumatory(1,2);       
        sumX1X2 = Sumatory(0,1);
        sumPowX1 = SumatoryPow(0);
        sumPowX2 = SumatoryPow(1);

        determinante = CalculateDetermin();

        b1 = CalculateB1();
        b2 = CalculateB2();
        b0 = CalculateB0();

        changeAverageX1 = ChangeAverage(0);
        changeAverageX2 = ChangeAverage(1);
        
    }

    private static double CalculateDetermin(){
        return (n*sumPowX1*sumPowX2 + sumX1*sumX1X2*sumX2 + sumX2*sumX1*sumX1X2)-
                (sumX2*sumPowX1*sumX2 + n*sumX1X2*sumX1X2 + sumPowX2*sumX1*sumX1);

        
    }

    private static double CalculateB0(){
        //promedioY - b1*promedioX1 - b2*promedioX2
        return Average(2) - CalculateB1()*Average(0) - CalculateB2()*Average(1);
    }

    private static double CalculateB1(){
        return ( (n*sumX1Y*sumPowX2 + sumX1*sumX2Y*sumX2 + sumX2*sumY*sumX1X2) - 
                (sumX2*sumX1Y*sumX2 + n*sumX2Y*sumX1X2 + sumPowX2*sumY*sumX1)
                ) / determinante;
    }

    private static double CalculateB2(){
        return ( (n*sumPowX1*sumX2Y + sumX1*sumX1X2*sumY + sumX2*sumX1*sumX1Y) - 
                (sumY*sumX2*sumPowX1 + n*sumX1Y*sumX1X2 + sumX2Y*sumX1*sumX1)
                ) / determinante;
    }

    private static void CalculateY(){
        for(int i = 0; i < dataSet.length; i++){            
            //b0 + (b1*x1) + (b2*x2)
            System.out.println(b0 + b1*dataSet[i][0] + b2*dataSet[i][1]);  
        }
    }

    private static double CalculateY(double _x1, double _x2){
        return b0 + b1*_x1 + b2*_x2;
    }

    private static String CalculateY(int times){
        double newX1 = dataSet[dataSet.length-1][0] + changeAverageX1;
        double newX2 = dataSet[dataSet.length-1][1] + changeAverageX2;
        String result = "";
        for(int i = 0; i < times; i++){
            result += String.valueOf(Math.round(newX1)) + "\t" + String.valueOf(Math.round(newX2)) + "\t" + String.valueOf(Math.round(CalculateY(newX1, newX2)) + "\n");
            newX1 += changeAverageX1;
            newX2 += changeAverageX2;
        }
        

        return result;
    }

    public static String DoRegression(double[][] _data){        
        dataSet = _data;
        InitializeVariables();
        return CalculateY(noPredictions);
        
    }
}