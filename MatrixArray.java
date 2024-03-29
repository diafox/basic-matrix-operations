import java.util.function.DoubleFunction;

public class MatrixArray implements Matrix {
    private double[][] matrix;

    private int rows;
    private int columns;

    public MatrixArray(int rowsSize, int columnsSize) {
        matrix = new double[rowsSize][columnsSize];
        rows = rowsSize;
        columns = columnsSize;
    }
    public MatrixArray(double[][] m) {
        matrix = m;
        rows = m.length;
        columns = m[0].length;
    }

    @Override
    public int getRows() {
        return rows;
    }
    @Override
    public int getColumns() {
        return columns;
    }
    @Override
    public double getElement(int row, int column) {
        return matrix[row][column];
    }
    @Override
    public void setElement(int row, int column, double value) {
        matrix[row][column] = value;
    }
    @Override
    public Matrix map(DoubleFunction<Double> f) {
        MatrixArray result = new MatrixArray(rows, columns);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                result.setElement(i, j, f.apply(matrix[i][j]));
            }
        }
        return result;
    }
    @Override
    public Matrix createSameTypeMatrix() {
        return new MatrixArray(getRows(), getColumns());
    };

}

