package CID.lineal_regression;

public class SimpleLinealRegression{

    public void StartLinealRegression(){
        //Datos
        double[] y = new double[]{651,762,856,1063,1190,1298,1421,1440,1518};//sales
        double[] x = new double[]{23,26,30,34,43,48,52,57,58};//advertising
        double[] xC = new double[]{529,676,900,1156,1849,2304,2704,3249,3364};//x*x

        double b1 = calcB1(x,y,xC);
        double b0 = calcB0(x,y,b1);
        double promSum = calcPromedioSuma(x);

        System.out.println("\nB1 = " + b1);
        System.out.println("B0 = " + b0);
        System.out.println("Promedio de sumas = " + calcPromedioSuma(x));

        System.out.println("\nY 0-9: ");
        for(int i = 0; i < 9; i++){
            System.out.println("X= " + x[i] + ", Y = " + calcY(b0,b1,x[i])); 
        }

        /***************************************Predicciones************************************************/
        System.out.println("\nPredicciones: ");
        double[] predX = new double[10];
        predX[0] = x[8] + promSum;
        for(int i = 1; i < 10; i++){
            predX[i] = predX[i-1] + promSum;
        }
        for(int i = 0; i < 10; i++){
            System.out.println("X= " + predX[i] + ", Y = " + calcY(b0,b1,predX[i])); 
        }
    }

    public static double calcY(double a,double b, double x){
        return a + b*x;
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