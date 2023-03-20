import java.util.function.DoubleFunction;

public class MatrixLinkedList implements Matrix {

    private class Node {
        int row;
        int column;
        double value;
        Node next;
    }

    private Node head;
    public int rows;
    public int columns;
    public int[] size;


    public MatrixLinkedList(int rowsSize, int columnsSize) {
        size = new int[] {rowsSize, columnsSize};

        rows = rowsSize;
        columns = columnsSize;
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
        Node current = head;
        while (current != null) {
            if (current.row == row && current.column == column) {
                return current.value;
            }
            current = current.next;
        }
        return 0.0;
    }
    @Override
    public void setElement(int row, int column, double value) {
        if (value == 0.0) {
            return;
        }
        Node current = head;
        Node previous = null;
        while (current != null && current.column < column) {
            previous = current;
            current = current.next;
        }
        while (current != null && current.row < row) {
            previous = current;
            current = current.next;
        }
        if (current != null && current.row == row && current.column == column) {
            current.value = value;
        } else {
            Node newNode = new Node();
            newNode.row = row;
            newNode.column = column;
            newNode.value = value;
            newNode.next = current;
            if (previous == null) {
                head = newNode;
            } else {
                previous.next = newNode;
            }
        }
    }
    @Override
    public Matrix map(DoubleFunction<Double> f) {
        MatrixLinkedList result = new MatrixLinkedList(rows, columns);
        Node current = head;
        while (current != null) {
            double value = f.apply(current.value);
            result.setElement(current.row, current.column, value);
            current = current.next;
        }
        return result;
    }

}

