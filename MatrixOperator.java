import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MatrixOperator {

    Scanner input;
    public MatrixOperator(Scanner input) {
        this.input = input;
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

    private Matrix convertToLinkedListMatrix(MatrixArray m) {
        Matrix temporary = new MatrixLinkedList(m.getRows(), m.getColumns());
        for (int row = 0; row < m.getRows(); row++) {
            for (int column = 0; column < m.getColumns(); column++) {
                temporary.setElement(row, column, m.getElement(row, column));
            }
        }
        return temporary;
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

    private Matrix createSameTypeMatrix(Matrix m) {
        Matrix result;
        if (m instanceof MatrixArray) {
            result = new MatrixArray(m.getRows(), m.getColumns());
        } else if (m instanceof MatrixLinkedList) {
            result = new MatrixLinkedList(m.getRows(), m.getColumns());
        } else if (m instanceof MatrixSparseArray) {
            result = new MatrixSparseArray(m.getRows(), m.getColumns(), ((MatrixSparseArray) m).nonZeroElementsUsed);
        } else {
            throw new IllegalArgumentException("ERROR");
        }
        return result;
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

    public Matrix transpose(Matrix m) {

        Matrix result = createSameTypeMatrix(m);

        for (int row = 0; row < m.getRows(); ++row) {
            for (int col = 0; col < m.getColumns(); ++col) {
                result.setElement(col, row, m.getElement(row, col));
            }
        }

        return result;
    }

    public Matrix addMatrices(Matrix firstM, Matrix secondM) {
        Matrix result = new MatrixArray(firstM.getRows(), secondM.getColumns());

        if (firstM.getRows() != secondM.getRows() && firstM.getColumns() != secondM.getColumns()) {
            System.out.println();
            System.out.println("ERROR");
        } else {
            for (int row = 0; row < firstM.getRows(); row++) {
                for (int column = 0; column < firstM.getColumns(); column++) {
                    Double sum = firstM.getElement(row, column) + secondM.getElement(row, column);
                    result.setElement(row, column, sum);
                }
            }
        }
        return result;
    }

    public Matrix scalarMultiplication(Matrix m, double scalar) {

        Matrix result;

        result = m.map(value -> value * scalar);
        return result;
    }

    public double calculateDeterminant(Matrix m, int rowsSize) {
        Matrix temp = new MatrixArray(m.getRows(), m.getColumns());

        double determinant = 0;

        if (rowsSize == 1) {
            return m.getElement(0, 0);
        }

        int sign = 1;
        for (int row = 0; row < rowsSize; row++) {
            getCofactor(m, temp, 0, row, rowsSize);

            determinant += sign * m.getElement(0, row)
                    * calculateDeterminant(temp, rowsSize - 1);

            sign = -sign;
        }
        return determinant;
    }
    private void getCofactor(Matrix m, Matrix temp, int oldRow, int oldColumn, int rowsOfFirstMatrix) {
        int i = 0, j = 0;
        for (int row = 0; row < rowsOfFirstMatrix; row++) {
            for (int column = 0; column < rowsOfFirstMatrix; column++) {
                if (row != oldRow && column != oldColumn) {
                    temp.setElement(i, j++, m.getElement(row, column));
                    if (j == rowsOfFirstMatrix - 1) {
                        j = 0;
                        i++;
                    }
                }
            }
        }
    }

    public Matrix multiplyMatrices(Matrix firstM, Matrix secondM) {
        Matrix multiplyingMatrices = new MatrixArray(firstM.getRows(), secondM.getColumns());
        for (int row = 0; row < multiplyingMatrices.getRows(); row++) {
            for (int column = 0; column < multiplyingMatrices.getColumns(); column++) {
                multiplyingMatrices.setElement(row, column, cellMultiplication(firstM, secondM, row, column));
            }
        }
        return multiplyingMatrices;
    }
    private double cellMultiplication(Matrix firstM, Matrix secondM, int row, int column) {
        double cell = 0;
        for (int i = 0; i < secondM.getRows(); i++) {
            cell += firstM.getElement(row, i) * secondM.getElement(i, column);
        }
        return cell;
    }

    public Matrix inverseMatrix(Matrix m) {
        int rowSize = m.getRows();

        double determinantNumber = calculateDeterminant(m, rowSize);

        Matrix temporaryInverseMatrix = createSameTypeMatrix(m);
        Matrix temp = new MatrixArray(m.getRows(), m.getColumns());

        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < rowSize; column++) {
                getCofactor(m, temp, row, column, rowSize);
                temporaryInverseMatrix.setElement(row, column, (Math.pow(-1, row + column)
                        * calculateDeterminant(temp, rowSize - 1)));
            }
        }

        Matrix finalInverseMatrix = createSameTypeMatrix(m);

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < m.getColumns(); j++) {
                finalInverseMatrix.setElement(i, j, (temporaryInverseMatrix.getElement(j, i) / determinantNumber));
            }
        }
        return finalInverseMatrix;
    }
}
