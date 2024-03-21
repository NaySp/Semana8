public class Main {
    public static void main(String[] args) {
        int[][] matrixA = {{1, 2}, {3, 4}};
        int[][] matrixB = {{5, 6}, {7, 8}};
        int numThreads = 2;

        long sequentialTime = SequentialMatrixMultiplication.measureExecutionTime(() -> {
            SequentialMatrixMultiplication.multiply(matrixA, matrixB);
        });

        long threadPoolTime = ThreadPoolMatrixMultiplication.measureExecutionTime(() -> {
            ThreadPoolMatrixMultiplication.multiply(matrixA, matrixB, numThreads);
        });

        System.out.println("Tiempo de ejecución secuencial: " + sequentialTime + " nanosegundos");
        System.out.println("Tiempo de ejecución con ThreadPool: " + threadPoolTime + " nanosegundos");
    }
}
