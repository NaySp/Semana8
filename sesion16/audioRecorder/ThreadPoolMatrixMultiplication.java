import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ThreadPoolMatrixMultiplication {
    
 public static void multiply(int[][] matrixA, int[][] matrixB, int numThreads){
    int rowsA = matrixA.length;
    int colsA = matrixA[0].length;
    int colsB = matrixB[0].length;

    int[][] resultMatrix = new int[rowsA][colsB];

    ExecutorService executor = Executors.newFixedThreadPool(numThreads);
    
    // Implementación de la multiplicación con ThreadPool
    for (int i = 0; i < rowsA; i++) {
        final int rowIdx = i;
        executor.execute(() -> {
            for (int j = 0; j < colsB; j++) {
                for (int k = 0; k < colsA; k++) {
                    resultMatrix[rowIdx][j] += matrixA[rowIdx][k] * matrixB[k][j];
                }
            }
        });
    }

    executor.shutdown();
    try {

        executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        }catch (InterruptedException e) {

        e.printStackTrace();

    }

      // Mostrar el resultado
      System.out.println("Matriz resultante (ThreadPool):");
      printMatrix(resultMatrix);

    }

    public static long measureExecutionTime(Runnable task) {
        long startTime = System.nanoTime();
        task.run();
        long endTime = System.nanoTime();
        
        return endTime - startTime;
    }

    public static void printMatrix(int[][] matrix) {
        for (int[] row : matrix) {
            for (int num : row) {
                System.out.print(num + " ");
            }
            System.out.println();
        }
    }



}