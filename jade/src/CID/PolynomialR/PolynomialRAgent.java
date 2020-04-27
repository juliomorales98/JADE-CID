package CID.PolynomialR;

import jade.core.Agent;
import jade.core.behaviours.OneShotBehaviour;

public class PolynomialRAgent extends Agent{
    PolynomialRCalculator qR;

    protected void setup()
    {
        qR = new PolynomialRCalculator();
        addBehaviour(new Servicio());
    }

        private class Servicio extends OneShotBehaviour 
        {
            public void action() 
            {
                System.out.println("\t******Hands-on 8: Regresión polinomial como servicio******");
                System.out.println("\n***Conjunto de datos***");
                System.out.println("\t X \t\t Y");
                for(int i = 0; i < qR.nDatos; i++)
                    System.out.println("\t" + qR.datosX[i] + "\t\t" + qR.datosY[i]);

                System.out.println("\n***Matriz de coeficientes***");
                double[][] matriz = qR.Coeficientes();
                qR.ImprimirMatriz(matriz);
                
                System.out.println("\n***Matriz Gaussiana***");
                qR.GaussJordan(matriz);
                qR.ImprimirMatriz(matriz);

                System.out.println("\n***Ecuación Polinomial***\n");
                double[] betas = qR.CalcularBetas(matriz);
                System.out.print("Y = ");
                for(int i = 0; i < betas.length; i++)
                {
                    if(i == 0)
                        System.out.print(betas[i] + " B" + i);
                    else 
                        System.out.print(" + " + betas[i] + " B" + i);
                }

                System.out.println("\n\n***Cálculo del error***\n");
                double sr = qR.CalcularSR(betas);
                double st = qR.CalcularST();
                double r2 = qR.CalcularR2(st, sr);
                System.out.println("St = " + st);
                System.out.println("Sr = " + sr);
                System.out.println("Coeficiente de determinaión (r2) = " + r2);

                qR.DoRegression();
                System.out.println("\n\nPresione 'ctrl + C' para salir.");
            }

            public int onEnd() 
            {
                 myAgent.doDelete();
                 return super.onEnd();
            }
        }
}