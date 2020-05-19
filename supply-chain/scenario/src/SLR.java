public class SLR {
    private double[] month;
    private double[] sales;
    private int noPredictions;
    public SLR(int _noP){
        noPredictions = _noP;
        System.out.println("SLR calculator ready");
    }
    public String DoRegression(double[][] _data){ 
        this.month = new double[_data.length];
        this.sales = new double[_data.length];
        int counter = 0;
        for(double[] d : _data){
            month[counter] = d[0];
            sales[counter] = d[1];            
            counter++;
        }
                     
        double b1 = calcB1(month,sales,calcPow(month));
        double b0 = calcB0(month,sales,b1);
        double promSum = calcPromedioSuma(month);

        //System.out.println("\nB1 = " + b1);
        //System.out.println("B0 = " + b0);
        //System.out.println("Promedio de sumas = " + calcPromedioSuma(month));

        /*System.out.println("\nY 0-"+String.valueOf(month.length-1));
        for(int i = 0; i < month.length; i++){
            System.out.println("X= " + (int)month[i] + ", Y = " + calcY(b0,b1,month[i])); 
        }*/

        /***************************************Predicciones************************************************/
        //System.out.println("\nPredicciones: ");
        double[] predX = new double[5];
        predX[0] = month[month.length-1] + promSum;
        String result = "";
        for(int i = 1; i < noPredictions; i++){
            predX[i] = predX[i-1] + promSum;
        }
        for(int i = 0; i < noPredictions; i++){
            result += "\nX= " + (int)predX[i] + ", Y = " + calcY(b0,b1,predX[i]); 
        }

        return result;
    }

    private static double[] calcPow(double[] x){
        double[] result = new double[x.length];
        for(int i = 0; i < x.length; i++){
            result[i] = Math.pow(x[i], 2);
        }
        return result;
    }

    public static double calcY(double a,double b, double x){
        return Math.round(a + b*x);
    }

    public static double calcPromedioSuma(double[] valores){
        double[] sumas = new double[valores.length-1];

        for (int i = 0; i < sumas.length; i++) {
            sumas[i] = valores[i+1] - valores[i];
        }

        return calcMedia(sumas);
    }

    public static double calcMedia(double[] valores) {
        double retVal = 0;
        for (int i = 0; i < valores.length; i++) {
            retVal += valores[i];
        }
        return retVal / valores.length;
    }

    public static double calcSum(double[] valores){
        double retVal = 0;
        for (int i = 0; i < valores.length; i++) {
            retVal += valores[i];
        }
        return retVal;
    }

    public static double calcSum(double[] valoresX, double[] valoresY){
        double retVal = 0;
        for (int i = 0; i < valoresX.length; i++) {
            retVal += valoresX[i] * valoresY[i];
        }
        return retVal;
    }
        
    public static double calcB1(double[] valoresX, double[] valoresY, double[] valoresXC){
        int n = valoresX.length;        

        return ( n*calcSum(valoresX,valoresY) - ( calcSum(valoresX) * calcSum(valoresY) ) )
        / ( n * calcSum(valoresXC) - ( calcSum(valoresX) * calcSum(valoresX) ) );
    }
 
    public static double calcB0(double[] valoresX, double[] valoresY, double b1){

        return calcMedia(valoresY) - ( b1 * calcMedia(valoresX) );


    }
}