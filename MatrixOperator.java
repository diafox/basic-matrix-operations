public class MatrixOperator {

    public Matrix transpose(Matrix m) {

        Matrix result = m.createSameTypeMatrix();

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

        Matrix temporaryInverseMatrix = m.createSameTypeMatrix();
        Matrix temp = new MatrixArray(m.getRows(), m.getColumns());

        for (int row = 0; row < rowSize; row++) {
            for (int column = 0; column < rowSize; column++) {
                getCofactor(m, temp, row, column, rowSize);
                temporaryInverseMatrix.setElement(row, column, (Math.pow(-1, row + column)
                        * calculateDeterminant(temp, rowSize - 1)));
            }
        }

        Matrix finalInverseMatrix = m.createSameTypeMatrix();

        for (int i = 0; i < rowSize; i++) {
            for (int j = 0; j < m.getColumns(); j++) {
                finalInverseMatrix.setElement(i, j, (temporaryInverseMatrix.getElement(j, i) / determinantNumber));
            }
        }
        return finalInverseMatrix;
    }
}
