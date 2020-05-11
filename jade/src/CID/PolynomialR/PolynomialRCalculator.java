package CID.PolynomialR;

import java.util.Scanner;

public class PolynomialRCalculator {
    double[] datosX;
    double[] datosY;
    int nDatos, orden;
    double cambiarPromedioX;
    double cambiarPromedioY;
    boolean resolvible;

    double b0;
    double b1;
    double b2;

    
    public PolynomialRCalculator()
    {
        datosX = new double[]{0, 1, 2, 3, 4, 5,6};
        datosY = new double[]{2.1, 7.7, 13.6, 27.2, 40.9, 61.1,60};
        nDatos = datosX.length;
        orden = 2;
        resolvible = true;
    }

    public void ImprimirMatriz(double[][] matriz)
    {
        
        for (int i = 0; i < matriz.length; i++) 
        {
          for (int j = 0; j < matriz[i].length; j++) 
          {
            System.out.print(matriz[i][j] + "\t\t");
          }
        System.out.println();
        }
    }

    public double Pow(double dato, int pot) 
    {
        double res = 1;
        for(int i = 0; i < pot; i++)
            res *= dato;
        return res;
    } 

    public double Sumatoria(double datos[]) 
    {
        double sumatoria = 0;
        for(int i = 0; i < datos.length; i++)
        {
            sumatoria += datos[i];   
        } 
        return sumatoria;
    }  

    public double Media(double[] datos)
    {   
        double promedio = 0;   
        for(int i = 0; i < datos.length; i++)
        {   
            promedio += datos[i];
        }
        return promedio / datos.length;
    }

    public double CalcularST()
    {
        double[] yTrazo = new double[nDatos];
        for(int i = 0; i < nDatos; i++)
        {
            yTrazo[i] = Pow(datosY[i] - Media(datosY), 2);
        }
        return Sumatoria(yTrazo);
    }

    public double CalcularSR(double[] betas)
    {
        double[] e = new double[nDatos];
        for(int i = 0; i < nDatos; i++)
        {
            double yCalculada = 0;
            for(int j = betas.length - 1; j >= 1; j--)
            {
                yCalculada += betas[j] * (Pow(datosX[i], j));
            }
            yCalculada += betas[0];
            e[i] = Pow(datosY[i] - yCalculada, 2);
        }
        return Sumatoria(e);
    }

    public double CalcularR2(double st, double sr) 
    {
        return (st - sr) / st;
    }

    public double[][] Coeficientes() 
    {
        
        double[][] matriz = new double[orden + 1][orden + 2];
        double[] s = new double[(2 * orden) + 1];        
	    double suma;

	    for(int k = 0; k < 2 * orden + 1; k++)
        {
            suma = 0;
            for(int i = 0; i < nDatos; i++)
                suma += Pow(datosX[i], k);
            s[k] = suma;
	    }
        
	    for(int k = 0; k < orden + 1; k++)
        {
		    suma=0;
		    for(int i = 0; i < nDatos; i++)
			    suma += Pow(datosX[i], k) * datosY[i];
		    matriz[k][orden + 1] = suma;
	    }  
        
	    for(int i = 0; i < orden + 1; i++)
        {
		    for(int j = 0; j < orden + 1; j++)
			    matriz[i][j] = s[i + j];
	    } 
        return matriz;
    }

    public void GaussJordan(double[][] matriz) 
    {
        int k = 0;
        int n = orden + 1;
        for(int j = 0; j < n - 1; j++)
        {
            for(int i = n - 1; i > k; i--)
            {
                if(Diagonal(matriz, n))
                    ReduccionAUno(matriz, n);
                ReduccionACero(matriz, i, j, n);
            } 
            k++;
        }
        for(int j = n - 1; j > 0; j--)
        {
            for(int i = 0; i < k; i++)
            {
                if(Diagonal(matriz, n))
                    ReduccionAUno(matriz, n);
                ReduccionACero(matriz, i, j, n);
            }
            k--;
        }
        for(int i = 0; i < n; i++)
        {
            if(matriz[i][i] == 0)
                resolvible = false;
        }
        if(resolvible && !Diagonal(matriz, n))
            ReduccionAUno(matriz, n);
    }

    public void ReduccionACero(double[][] matriz, int i, int j, int n) 
    {
        double[] ra = new double[n + 1];
        for(int k = 0; k < n + 1; k++)
        {
            ra[k] = ((matriz[i][k] * matriz[j][j]) - (matriz[j][k] * matriz[i][j]));   
        }
        for(int k = 0; k < n + 1; k++)
        {
            matriz[i][k] = ra[k];
        }
    }

    public void ReduccionAUno(double[][] matriz, int n) 
    {
        double aux;
        for(int i = 0; i < n; i++)
        {
            aux = matriz[i][i]; 
            for(int j = 0; j < n + 1; j++)
            {
                matriz[i][j] = (matriz[i][j] / aux);
            }
        }
    }

    public boolean Diagonal(double[][] matriz, int n) 
    {
        boolean res = true;
        for(int i = 0; i < n; i++)
        {
            if(matriz[i][i] != 1)
                res = false;
        }
        for(int i = 0; i < n; i++)
        {
            if(matriz[i][i] == 0)
                res = true;
        }
        return res;
    }

    public double[] CalcularBetas(double[][] matriz)
    {
        double[] vector = new double[orden + 1];
        for(int i = 0; i < orden + 1; i++)
        {
            vector[i] = matriz[i][matriz.length];
        }
        b0 = vector[0];
        b1 = vector[1];
        b2 = vector[2];
        return vector;
    }

    public double CalcularY(double _x)
    {
        return b0 + b1 * _x + b2 * Pow(_x, 2);
    }

    public void CalcularY(int nPredicciones)
    {   
        double newX = datosX[nDatos - 1] + 1;

        System.out.println("X\tY");
        for(int i = 0; i < nPredicciones; i++)
        {
            System.out.println(newX + "\t" + CalcularY(newX));
            newX += 1;
        }
    }

    public void DoPredictions()
    {
        Scanner reader = new Scanner(System.in);
        int numero = 0;
        do{
            System.out.println("\nPredicciones: ");
            numero = reader.nextInt();
            CalcularY(numero);
        }while(numero != 0);
    }

    public void DoRegression(){
        double[][] matriz;
        double[] betas;
        double sr;
        double st;
        double r2;


        
        System.out.println("Datos:");
        System.out.println("X\tY");
        for(int i = 0; i < nDatos; i++)
            System.out.println(datosX[i] + "\t" + datosY[i]);

        matriz = Coeficientes();
        GaussJordan(matriz);        
        betas = CalcularBetas(matriz);
        sr = CalcularSR(betas);
        st = CalcularST();
        r2 = CalcularR2(st, sr);

        System.out.println("\nBetas");  
        System.out.println("B0 = " + betas[0]);
        System.out.println("B1 = " + betas[1]);
        System.out.println("B2 = " + betas[2]);

        System.out.println("\nEcuación");  
        System.out.print("y' = ");
                for(int i = 0; i < betas.length; i++)
                {                  
                    
                    System.out.print(" + " + betas[i]);
                }

        System.out.println("\n\nError");        
        System.out.println("r2 = " + r2);
    }
    
}
