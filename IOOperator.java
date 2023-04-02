import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class IOOperator {
    Scanner input;

    public IOOperator(Scanner input) {
        this.input = input;
    }

    public Matrix fillMatrix() {
        Matrix filledMatrix = null;

        System.out.print("Enter number of rows: ");
        int rowLength = input.nextInt();
        while (rowLength < 0) {
            System.out.print("You joker! :) Now try one more time and give me some non-negative number : ");
            rowLength = input.nextInt();
        }

        System.out.print("Enter number of columns: ");
        int columnLength = input.nextInt();
        while (columnLength < 0) {
            System.out.print("Negative number of columns is not really possible :) Try one more time: ");
            columnLength = input.nextInt();
        }

        System.out.println("Which matrix representation do you want to use?");
        System.out.println("1) Two dimensional array for classic matrices");
        System.out.println("2) Two dimensional array for sparse matrices, great for Z-form matrices");
        System.out.println("3) Linked list for effective manipulation with sparse matrices");
        System.out.println("4) Don't know if your matrix is sparse? Don't worry, I'll figure out and choose the right representation for you!");
        int density = input.nextInt();
        boolean densityProvided = false;
        int nonZero = 0;

        while (!densityProvided) {
            switch (density) {
                case 1:
                case 2:
                case 4:
                    filledMatrix = new MatrixArray(rowLength, columnLength);
                    densityProvided = true;
                    break;
                case 3:
                    filledMatrix = new MatrixLinkedList(rowLength, columnLength);
                    densityProvided = true;
                    break;
                default:
                    System.out.println("Sorry. Wrong option provided. Try again: ");
                    density = input.nextInt();
            }
        }

        System.out.println("Enter matrix elements, one by one from left to right, from up to down: ");
        for (int row = 0; row < rowLength; row++) {
            for (int column = 0; column < columnLength; column++) {
                double value = 0;
                boolean isValid = false;

                while (!isValid) {
                    try {
                        value = input.nextDouble();
                        isValid = true;
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Please enter a valid double value.");
                        input.nextLine();
                    }
                }
                filledMatrix.setElement(row, column, value);
                if (value != 0) { nonZero++; }

            }
        }

        if (density == 2) {
            return convertToSparseArrayMatrix((MatrixArray) filledMatrix, nonZero);
        }

        if (density == 4) {
            if (isSparse(filledMatrix)) {
                return convertToLinkedListMatrix((MatrixArray) filledMatrix);
            }
        }

        return filledMatrix;
    }

    public Matrix convertToSparseArrayMatrix(MatrixArray m, int nonZero) {
        Matrix temporary = new MatrixSparseArray(m.getRows(), m.getColumns(), nonZero);
        for (int row = 0; row < m.getRows(); row++) {
            for (int column = 0; column < m.getColumns(); column++) {
                double value = m.getElement(row, column);
                temporary.setElement(row, column, value);
            }
        }
        return temporary;
    }

    private Matrix convertToLinkedListMatrix(MatrixArray m) {
        Matrix temporary = new MatrixLinkedList(m.getRows(), m.getColumns());
        for (int row = 0; row < m.getRows(); row++) {
            for (int column = 0; column < m.getColumns(); column++) {
                temporary.setElement(row, column, m.getElement(row, column));
            }
        }
        return temporary;
    }

    public boolean isSparse(Matrix m) {
        int nonZeroElements = 0;
        int rows = m.getRows();
        int columns = m.getColumns();
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                if (m.getElement(i, j) != 0) {
                    nonZeroElements++;
                }
            }
        }
        return nonZeroElements <= rows * columns * 0.1;
    }

    public void printMatrix(Matrix m) {
        DecimalFormat df = new DecimalFormat("0.##");
        for (int i = 0; i < m.getRows(); i++) {
            for (int j = 0; j < m.getColumns(); j++) {
                System.out.print(df.format(m.getElement(i, j)) + "   ");
            }
            System.out.println();
        }
    }
}
