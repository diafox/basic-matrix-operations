import java.util.function.DoubleFunction;

public class MatrixSparseArray implements Matrix {

    private int rows;
    private int columns;
    private double[][] elements;
    private int nonZeroElementsUsed = 0;

    public MatrixSparseArray(int rows, int columns, int nonZero) {
        elements = new double[3][nonZero];
        this.rows = rows;
        this.columns = columns;
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
        for (int i = 0; i < elements[0].length; i++) {
            if (elements[0][i] == row && elements[1][i] == column) {
                return elements[2][i];
            }
        }
        return 0;
    }

    @Override
    public void setElement(int row, int column, double value) {
        if (value == 0) { return; }
        else {
            elements[0][nonZeroElementsUsed] = row;
            elements[1][nonZeroElementsUsed] = column;
            elements[2][nonZeroElementsUsed] = value;
            nonZeroElementsUsed++;
        }
    }

    @Override
    public Matrix map(DoubleFunction<Double> f) {
        MatrixSparseArray result = new MatrixSparseArray(getRows(), getColumns(), nonZeroElementsUsed);
        for (int i = 0; i < getRows(); i++) {
            for (int j = 0; j < getColumns(); j++) {
                Double val = getElement(i, j);
                result.setElement(i, j, f.apply(val));
            }
        }
        return result;
    }

    public int getNonZeroElementsUsed() {
        return  nonZeroElementsUsed;
    }
}