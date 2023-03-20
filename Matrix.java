import java.util.function.DoubleFunction;

public interface Matrix {
    int getRows();
    int getColumns();
    double getElement(int row, int column);
    void setElement(int row, int column, double value);
    Matrix map(DoubleFunction<Double> f);
}
