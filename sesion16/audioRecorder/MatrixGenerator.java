import java.util.Random;

public class MatrixGenerator {

    public static int[][] generateRandomMatrix(int rows, int columns) {
    int[][] matrix = new int[rows][columns];
    Random random = new Random();

    for (int i = 0; i < rows; i++){
        for (int j = 0; j < columns; j++) {
           // Números aleatorios entre 0 y 99
           matrix[i][j] = random.nextInt(1000); 
        }
    }


    System.out.println("Matriz resultante (multiply):");
    printMatrix(matrix);

    return matrix;

  

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
