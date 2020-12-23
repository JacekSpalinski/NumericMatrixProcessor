package processor;

import java.util.Locale;
import java.util.Scanner;

public class Calculations {

    Scanner scanner = new Scanner(System.in).useLocale(Locale.US);
    Matrix matrix1 = new Matrix();
    Matrix matrix2 = new Matrix();
    int choice;

    Calculations() {
        while (true) {
            System.out.print("1. Add matrices\n2. Multiply matrix by a constant\n3. Multiply matrices\n4. Transpose matrix\n" +
                    "5. Calculate a determinant\n6. Inverse matrix\n0. Exit\nYour choice: ");
            choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    matrix1.fillMatrix(" first");
                    matrix2.fillMatrix(" second");
                    // the condition for matrices addition is that they have to be the same size
                    if (matrix1.rows != matrix2.rows || matrix1.columns != matrix2.columns) {
                        System.out.println("The operation cannot be performed.\n");
                    } else {
                        printMatrix(addMatrix(matrix1, matrix2));
                    }
                    break;
                case 2:
                    matrix1.fillMatrix("");
                    System.out.print("Enter constant: ");
                    double constant = scanner.nextDouble();
                    printMatrix(multiplyByConstant(matrix1, constant));
                    break;
                case 3:
                    matrix1.fillMatrix(" first");
                    matrix2.fillMatrix(" second");
                    /*
                    the condition for matrices multiplication is that the number of columns in 1st matrix
                    must be equal number of rows in 2nd matrix
                    */
                    if (matrix1.columns != matrix2.rows) {
                        System.out.println("The operation cannot be performed.\n");
                    } else {
                        printMatrix(multiplyMatrix(matrix1, matrix2));
                    }
                    break;
                case 4:
                    printMatrix(transposeMatrix(matrix1));
                    break;
                case 5:
                    matrix1.fillMatrix("");
                    // the condition for finding the determinant of the matrix is that is has to be a square matrix
                    if (matrix1.rows != matrix1.columns) {
                        System.out.println("The operation cannot be performed.\n");
                    } else {
                        System.out.println("The result is:\n" + findDeterminant(matrix1.matrix));
                    }
                    break;
                case 6:
                    matrix1.fillMatrix("");
                    double determinant = findDeterminant(matrix1.matrix);
                    // you can only find inverse of a square matrix
                    if (matrix1.rows != matrix1.columns) {
                        System.out.println("The operation cannot be performed.\n");
                    } else if (determinant == 0) {
                        System.out.println("This matrix doesn't have an inverse.\n");
                    } else {
                        printMatrix(inverseMatrix(matrix1, determinant));
                    }
                    break;
                case 0:
                    System.exit(0);
            }
        }
    }

    public Matrix addMatrix(Matrix matrix1, Matrix matrix2) {
        Matrix resultMatrix = new Matrix(matrix1.rows, matrix1.columns);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix1.columns; j++) {
                resultMatrix.matrix[i][j] = matrix1.matrix[i][j] + matrix2.matrix[i][j];
            }
        }
        return resultMatrix;
    }

    public Matrix multiplyByConstant(Matrix matrix1, Double constant) {
        Matrix resultMatrix = new Matrix(matrix1.rows, matrix1.columns);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix1.columns; j++) {
                resultMatrix.matrix[i][j] = matrix1.matrix[i][j] * constant;
            }
        }
        return resultMatrix;
    }

    public Matrix multiplyMatrix(Matrix matrix1, Matrix matrix2) {
        // the result matrix will have number of rows of 1st matrix and number of columns of 2nd matrix
        Matrix resultMatrix = new Matrix(matrix1.rows, matrix2.columns);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix2.columns; j++) {
                double dotProduct = 0;
                /*
                we use first two loops to iterate through each cell in the result matrix,
                while the loop below is used to calculate dot product for those cells;
                */
                for (int k = 0; k < matrix1.columns; k++) {
                    dotProduct += matrix1.matrix[i][k] * matrix2.matrix[k][j];
                }
                resultMatrix.matrix[i][j] = dotProduct;
            }
        }
        return resultMatrix;
    }

    public Matrix transposeMatrix(Matrix matrix1) {
        System.out.print("\n1. Main diagonal\n2. Side diagonal\n3. Vertical line\n4. Horizontal line\nYour choice: ");
        int transposeChoice = scanner.nextInt();
        matrix1.fillMatrix("");
        switch (transposeChoice) {
            case 1:
                Matrix resultMatrix1 = new Matrix(matrix1.columns, matrix1.rows);
                for (int i = 0; i < matrix1.columns; i++) {
                    for (int j = 0; j < matrix1.rows; j++) {
                        resultMatrix1.matrix[i][j] = matrix1.matrix[j][i];
                    }
                }
                return resultMatrix1;
            case 2:
                Matrix resultMatrix2 = new Matrix(matrix1.columns, matrix1.rows);
                for (int i = 0; i < matrix1.columns; i++) {
                    for (int j = 0; j < matrix1.rows; j++) {
                        resultMatrix2.matrix[i][j] = matrix1.matrix[matrix1.columns - 1 - j][matrix1.rows - 1 - i];
                    }
                }
                return resultMatrix2;
            case 3:
                Matrix resultMatrix3 = new Matrix(matrix1.rows, matrix1.columns);
                for (int i = 0; i < matrix1.rows; i++) {
                    for (int j = 0; j < matrix1.columns; j++) {
                        resultMatrix3.matrix[i][j] = matrix1.matrix[i][matrix1.columns - 1 - j];
                    }
                }
                return resultMatrix3;
            case 4:
                Matrix resultMatrix4 = new Matrix(matrix1.rows, matrix1.columns);
                for (int i = 0; i < matrix1.rows; i++) {
                    for (int j = 0; j < matrix1.columns; j++) {
                        resultMatrix4.matrix[i][j] = matrix1.matrix[matrix1.rows - 1 - i][j];
                    }
                }
                return resultMatrix4;
        }
        return null;
    }

    public static double findDeterminant(double[][] matrix) {
        if (matrix.length == 2) {
            return matrix[0][0] * matrix[1][1] - matrix[0][1] * matrix[1][0];
        } else {
            double det = 0;
            for (int i = 0; i < matrix.length; i++) {
                // create minor of the matrix, that will go as input into recursive call of the function
                double[][] minor = new double[matrix.length - 1][matrix.length - 1];
                for (int j = 0; j < minor.length; j++) {
                    for (int k = 0; k < minor.length; k++) {
                            /*
                            if the column of minor corresponds to the column we cross out form main matrix,
                            we need to skip it, thus we add 1 to k;
                            for rows of the main matrix we use index j+1, as we cross out the top row
                             */
                        if (k >= i) {
                            minor[j][k] = matrix[j + 1][k + 1];
                        } else {
                            minor[j][k] = matrix[j + 1][k];
                        }
                    }
                }
                // determine if our terms have positive or negative sign (pattern goes +-+-+-...)
                if (i % 2 == 0) {
                    det += matrix[0][i] * findDeterminant(minor);
                } else {
                    det -= matrix[0][i] * findDeterminant(minor);
                }
            }
            return det;
        }
    }

    public Matrix inverseMatrix(Matrix matrix1, double determinant) {
        // first, find matrix of minors
        Matrix minors = new Matrix(matrix1.rows, matrix1.columns);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix1.columns; j++) {
                double[][] minor = new double[matrix1.rows - 1][matrix1.columns - 1];
                // we need to populate minor matrix, whose determinate will go into cell [i][j] in our matrix of minors
                for (int k = 0; k < minor.length; k++) {
                    for (int l = 0; l < minor.length; l++) {
                            /*
                            if the row or column of minor corresponds to the row or column we cross out form main matrix,
                            we need to skip it, thus we add 1 to k or l;
                             */
                        if (k >= i && l >= j) {
                            minor[k][l] = matrix1.matrix[k + 1][l + 1];
                        } else if (k >= i) {
                            minor[k][l] = matrix1.matrix[k + 1][l];
                        } else if (l >= j) {
                            minor[k][l] = matrix1.matrix[k][l + 1];
                        } else {
                            minor[k][l] = matrix1.matrix[k][l];
                        }
                    }
                }
                minors.matrix[i][j] = findDeterminant(minor);
            }
        }
        // second, find matrix of cofactors
        Matrix cofactors = new Matrix(matrix1.rows, matrix1.columns);
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix1.columns; j++) {
                // cells whose sum of coordinates is even will have positive signs
                if ((i + j) % 2 == 0) {
                    cofactors.matrix[i][j] = minors.matrix[i][j];
                } else {
                    cofactors.matrix[i][j] = (-1) * minors.matrix[i][j];
                }
            }
        }
        // now transpose matrix of cofactors
        Matrix transpose = new Matrix(matrix1.rows, matrix1.columns);
        for (int i = 0; i < matrix1.columns; i++) {
            for (int j = 0; j < matrix1.rows; j++) {
                transpose.matrix[i][j] = cofactors.matrix[j][i];
            }
        }
        return multiplyByConstant(transpose, 1 / determinant);
    }

    public void printMatrix(Matrix matrix1) {
        System.out.println("The result is:");
        for (int i = 0; i < matrix1.rows; i++) {
            for (int j = 0; j < matrix1.columns; j++) {
                // if the number is de facto an integer, print it as an integer
                if (matrix1.matrix[i][j] == (int) matrix1.matrix[i][j]) {
                    System.out.printf("%8d", (int) matrix1.matrix[i][j]);
                } else {
                    System.out.printf("%8.2f", matrix1.matrix[i][j]);
                }
            }
            System.out.println();
        }
        System.out.println();
    }
}