package handson8.quadraticRegression;

import java.util.Scanner;

public class quadraticRegression
{
    double[] datosX;
    double[] datosY;
    int nDatos, orden;
    double cambiarPromedioX;
    double cambiarPromedioY;
    boolean resolvible;

    double b0;
    double b1;
    double b2;

    public quadraticRegression()
    {
        datosX = new double[]{0, 1, 2, 3, 4, 5};
        datosY = new double[]{2.1, 7.7, 13.6, 27.2, 40.9, 61.1};
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

    public double Pow(double dato, int pot) // Función que eleva un dato a n potencia
    {
        double res = 1;
        for(int i = 0; i < pot; i++)
            res *= dato;
        return res;
    } 

    public double Sumatoria(double datos[]) //Función que realiza la sumatoria de un conjunto de datos
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

    public double CalcularR2(double st, double sr) // Coeficiente de determinaión
    {
        return (st - sr) / st;
    }

    public double[][] Coeficientes() // Función que determina los coeficientes de cada elemento en la matriz
    {
        // Este método es para una matriz dinámica de n orden
        // orden es el grado del polinomio que estamos buscando
        double[][] matriz = new double[orden + 1][orden + 2];
        // 2 * orden + 1 Es la potencia máxima que alcanza X
        // s son los valores de X a la n potencia
        double[] s = new double[(2 * orden) + 1];
	    double suma;
	    for(int k = 0; k < 2 * orden + 1; k++)
        {
            suma = 0;
            for(int i = 0; i < nDatos; i++)
                suma += Pow(datosX[i], k);
            s[k] = suma;
	    }
        // Determina los resultados de cada fila en la matriz
	    for(int k = 0; k < orden + 1; k++)
        {
		    suma=0;
		    for(int i = 0; i < nDatos; i++)
			    suma += Pow(datosX[i], k) * datosY[i];
		    matriz[k][orden + 1] = suma;
	    }  
        // Asigna los coeficientes calculados a la matriz
	    for(int i = 0; i < orden + 1; i++)
        {
		    for(int j = 0; j < orden + 1; j++)
			    matriz[i][j] = s[i + j];
	    } 
        return matriz;
    }

    public void GaussJordan(double[][] matriz) // Método GaussJordan para resolver la matriz
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

    public void ReduccionACero(double[][] matriz, int i, int j, int n) // Reduce los elementos NO DIAGONALES a cero
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

    public void ReduccionAUno(double[][] matriz, int n) // Reduce los elementos DIAGONALES a uno
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

    public boolean Diagonal(double[][] matriz, int n) // Determina si la matriz está resuelta por medio de su diagonal
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
        for(int i = 0; i < nPredicciones; i++)
        {
            System.out.println(i + 1 + ".-\t" + newX + "\t " + CalcularY(newX));
            newX += 1;
        }
    }

    public void DoRegression()
    {
        Scanner reader = new Scanner(System.in);
        int numero = 0;
        do{
            System.out.println("\nIngresa el número de predicciones que deseas hacer:\nPresiona '0' para salir...\t ");
            numero = reader.nextInt();
            CalcularY(numero);
        }while(numero != 0);
    }
}
