import java.util.Locale;
import java.util.Scanner;

// class used for constructing matrices that we will perform operations on
public class Matrix {

    Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    int rows;
    int columns;
    double[][] matrix;

    // default constructor that will be used for matrices created by the user
    Matrix() {
    }

    // constructor specifying # of rows and columns, used in some of the methods
    Matrix(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        matrix = new double[rows][columns];
    }

    // prompts user for input that populates the matrix
    public void fillMatrix (String matrixCounter) {
        System.out.printf("Enter size of%s matrix: ", matrixCounter);
        rows = scanner.nextInt();
        columns = scanner.nextInt();
        matrix = new double[rows][columns];
        System.out.printf("Enter%s matrix:\n", matrixCounter);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                matrix[i][j] = scanner.nextDouble();
            }
        }
    }
}
