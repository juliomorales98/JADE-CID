package CID.MultipleLR;


public class MLRCalculator{
    private static double[][] dataSet = {
        {41.9, 29.1, 251.3},
        {43.4, 29.3, 251.3},
        {43.9, 29.5, 248.3},
        {44.5, 29.7, 267.5},
        {47.3, 29.9, 273.0},
        {47.5, 30.3, 276.5},
        {47.9, 30.5, 270.3},
        {50.2, 30.7, 274.9},
        {52.8, 30.8, 285.0},
        {53.2, 30.9, 290.0},
        {56.7, 31.5, 297.0},
        {57.0, 31.7, 302.5},
        {63.5, 31.9, 304.5},
        {65.3, 32.0, 309.3},
        {71.1, 32.1, 321.7},
        {77.0, 32.5, 330.7},
        {77.8, 32.9, 349.0}
    };

    private static double n; 
        
    private static double sumX1;
    private static double sumX2;
    private static double sumY;
    private static double sumX1Y;
    private static double sumX2Y;       
    private static double sumX1X2;
    private static  double sumPowX1;
    private static double sumPowX2;
    
    private static double determinante;

    private static double b0 = -153.51;
    private static double b1 = 1.24;
    private static double b2 = 12.08;

    private static void PrintData(){
        for(int i = 0; i < dataSet.length; i++){
            for(int j = 0; j < dataSet[0].length; j++){
                System.out.print("| " + dataSet[i][j]);
            }
            System.out.println();
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
        
    }

    private static double CalculateDetermin(){
        return (n*sumPowX1*sumPowX2 + 2*sumX1*sumX1X2*sumX2)-
                (2*sumX2*sumPowX1 + n*sumX1X2*sumX1X2 + 2*sumX1*sumPowX2);
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
                (sumY*sumX2*sumPowX1 + n*sumX1Y*sumX1X2 + sumX2Y*2*sumX1)
                ) / determinante;
    }

    private static void CalculateY(){
        for(int i = 0; i < dataSet.length; i++){            
            //b0 + (b1*x1) + (b2*x2)
            System.out.println(b0 + b1*dataSet[i][0] + b2*dataSet[i][1]);  
        }
    }

    public static void main(String[] args){
        System.out.println("Data Set:");
        PrintData();
        System.out.println("Y'");
        CalculateY();
        InitializeVariables();
        System.out.println("b1 = " + CalculateB1());
        System.out.println("b2 = " + CalculateB2());
        System.out.println("b0 = " + CalculateB0());
        b1 = CalculateB1();
        b2 = CalculateB2();
        b0 = CalculateB0();
        CalculateY();
        
    }
}