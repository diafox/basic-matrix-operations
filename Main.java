/*
Program pre prácu so základnými maticovými operáciami
Diana Líšková, 3. ročník Bc.
Zimný semester 2022/23
Programování v jazyce Java - NPRG013
 */

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
    private final static Scanner input = new Scanner(System.in);
    private static int inputOption;
    private static Matrix primaryMatrix;
    private static Matrix secondaryMatrix;

    private static double scalar;

    public static void main(String[] args) {

        boolean gameOn = true;
        System.out.println("Welcome to matrix operator, your assistant with matrix operations!");

        while (gameOn) {

            System.out.println("Choose option with operation you want me to help with :) Write your option as a single integer.");
            System.out.println("Are you new here? I recommend the tutorial option.");
            System.out.println();
            System.out.println("1) Add matrices");
            System.out.println("2) Multiply matrix by a constant");
            System.out.println("3) Multiply matrices");
            System.out.println("4) Transpose matrix");
            System.out.println("5) Calculate a determinant");
            System.out.println("6) Inverse matrix");
            System.out.println("7) Tutorial");
            System.out.println("0) Exit");
            System.out.print("Option of your choice: ");

            MatrixOperator mop = new MatrixOperator(input);


            try {
                inputOption = input.nextInt();
            } catch (InputMismatchException e) {
                System.out.println("\nYour input wasn't in form of an integer how I asked of you. Come on, you can do better.");
                continue;
            }

            switch (inputOption) {
                case 1:
                    System.out.println("\nFIRST MATRIX");
                    primaryMatrix = mop.fillMatrix();
                    System.out.println("\nSECOND MATRIX");
                    secondaryMatrix = mop.fillMatrix();

                    if (primaryMatrix.getRows() != secondaryMatrix.getRows() | primaryMatrix.getColumns() != secondaryMatrix.getColumns()) {
                        System.out.println("Matrices are not the same size and thus cannot be added.");
                        break;
                    }
                    System.out.println("\nThe addition result is:");
                    mop.printMatrix(mop.addMatrices(primaryMatrix, secondaryMatrix));
                    break;

                case 2:
                    primaryMatrix = mop.fillMatrix();
                    System.out.print("Enter multiplication number: ");
                    scalar = input.nextDouble();
                    System.out.println("The multiplication result is:");
                    mop.printMatrix(mop.scalarMultiplication(primaryMatrix, scalar));
                    break;

                case 3:
                    primaryMatrix = mop.fillMatrix();
                    secondaryMatrix = mop.fillMatrix();
                    if (primaryMatrix.getColumns() != secondaryMatrix.getRows()) {
                        System.out.println("The number of columns in the first matrix should be equal to the number of rows in the second.");
                        break;
                    }
                    System.out.println("The multiplication result is:");
                    mop.printMatrix(mop.multiplyMatrices(primaryMatrix, secondaryMatrix));
                    break;

                case 4:
                    primaryMatrix = mop.fillMatrix();
                    mop.printMatrix(mop.transpose(primaryMatrix));
                    break;

                case 5:
                    primaryMatrix = mop.fillMatrix();
                    if (primaryMatrix.getRows() != primaryMatrix.getColumns()) {
                        System.out.println("This matrix is not square. Non-square matrices do not have determinants.");
                        break;
                    }
                    System.out.print("The result is: ");
                    System.out.println(mop.calculateDeterminant(primaryMatrix, primaryMatrix.getRows()));
                    System.out.println();
                    break;

                case 6:
                    primaryMatrix = mop.fillMatrix();
                    if (primaryMatrix.getRows() != primaryMatrix.getColumns()) {
                        System.out.println("This matrix is not square and thus cannot be inverted.");
                        break;
                    } else if (mop.calculateDeterminant(primaryMatrix, primaryMatrix.getRows()) == 0) {
                        System.out.println("This matrix is singular and thus cannot be inverted.");
                        break;
                    }
                    System.out.println("The result is:");
                    mop.printMatrix(mop.inverseMatrix(primaryMatrix));
                    break;

                case 7:
                    presentation(mop);
                    break;

                case 0:
                    gameOn = false;
                    break;

                default:
                    System.out.println("Sorry. Wrong option provided");
            }
            System.out.println();
        }
    }

    public static void presentation(MatrixOperator mop) {
        System.out.println("\nHello there! You chose option presentation. Now let me show you how I operate.");
        System.out.println("Press \"ENTER\" every time you want me to continue.");
        promptEnterKey();
        System.out.println("Let's start with basic examples. Suppose we have identity matrix:");

        Matrix example1 = generateIdentityMatrix(5, 5);
        mop.printMatrix(example1);
        System.out.println("\nThis matrix is represented as a linked list, so that it is more effective to work with it.");
        promptEnterKey();

        System.out.println("What can I do? Well, I can multiply it with scalar. For example, 81.65.");
        Matrix example1scalar = mop.scalarMultiplication(example1, 81.65 );
        System.out.println("We get:");
        mop.printMatrix(example1scalar);
        System.out.println("This matrix is still in linked list representation. Operations - except for adding and multiplying two matrices - will preserve the representation.");
        promptEnterKey();

        System.out.println("\nNow let's have another matrix:");
        double[][] filling = {  {-2,4,1,6,7},
                                {2,6.6,0,4,14.2},
                                {2.7,0,1,2,9},
                                {1,4.4,8.7,1,0},
                                {2,2,2,2,2} };
        MatrixArray example2 = new MatrixArray(filling);
        mop.printMatrix(example2);

        System.out.println("\nWe want to add this matrix to the previous one. And we are lucky that they are the same size! Because I struggle with adding two matrices of different sizes.");
        System.out.println("We get:");
        Matrix addedExamples = mop.addMatrices(example1scalar, example2);
        mop.printMatrix(addedExamples);
        promptEnterKey();

        System.out.println("Now this one is represented as 2D array, because it's not sparse anymore. We can check it by calling my isSparse method:");
        System.out.println("Mirror mirror on the wall, is this matrix sparse at all?");
        System.out.println(mop.isSparse(addedExamples));
        promptEnterKey();

        System.out.println("\nI'm also pro at calculating determinants. Watch me!");
        System.out.print("79,65 times 88,25 times 82... haha, just kidding, it's ");
        DecimalFormat df = new DecimalFormat("0.##");
        System.out.println(df.format(mop.calculateDeterminant(addedExamples, addedExamples.getRows())));
        promptEnterKey();

        System.out.println("\nWhat happens if we transpose mentioned matrix? This:");
        mop.printMatrix(mop.transpose(addedExamples));
        promptEnterKey();

        System.out.println("\nEeny meeny miny moe, what next shall we do? Oh, I know. Let's inverse the transposed matrix! Sounds like fun.");
        Matrix inversed = mop.inverseMatrix(mop.transpose(addedExamples));
        mop.printMatrix(inversed);
        System.out.println("Just beautiful. But don't forget I can only inverse square matrices or matrices which don't have determinant equal to 0.");
        promptEnterKey();

        System.out.println("\nFor now I showed you several operations with both linked list and 2D array representation. But I have one more representation which you can use.");
        System.out.println("I present you - THE SPARSE MATRIX representation. Efficient for Z-form matrices.");

        double[][] filling2 = { {14.7, 1, 2.2, 3},
                                {0, 0, 5.67, 0},
                                {0, 9.6, 0, 0},
                                {15.6, 14.19, 13.2, 12.12} };
        Matrix temp = new MatrixArray(filling2);
        Matrix example3 = mop.convertToSparseArrayMatrix((MatrixArray) temp, 10);

        System.out.println("This matrix:");
        mop.printMatrix(example3);
        System.out.println("is in the so called Z-form.");
        promptEnterKey();

        System.out.println("\nNow we will multiply it with the matrix");
        double[][] filling3 = { {2, 4.4},
                {0, 6},
                {3.5, 0},
                {12.21, 2.03} };
        temp = new MatrixArray(filling3);
        Matrix example4 = mop.convertToSparseArrayMatrix((MatrixArray) temp, 6);
        mop.printMatrix(example4);
        System.out.println("which is also in the Z-form.");

        System.out.println("\nWe get:");
        Matrix example5 = mop.multiplyMatrices(example3,example4);
        mop.printMatrix(example5);
        System.out.println("\nSee, I can only multiply two matrices where the number of columns of the first one is same as number of rows of the second one.");
        System.out.println("If the numbers don't match, I will check it and let you know, so that you can try it one more time.");
        promptEnterKey();

        System.out.println("I can also calculate its determinant. It's ...");
        if (example5.getRows() != example5.getColumns()) {
            System.out.println("It seems impossible. Do you know why?");
        }
        promptEnterKey();

        System.out.println("Yes! it's because it's not square.");
        promptEnterKey();

        System.out.println("I believe I showed you a lot of valuable processes I can do. Now it's time for you to try.");
        System.out.println("If you stumble upon something worth improving, don't hesitate to contact me on email address of my creator: diusaliskova@gmail.com ");

        System.out.println("\nPress \"ENTER\" one last time to return to main menu and try the options for yourself!");
        promptEnterKey();
    }

    public static Matrix generateIdentityMatrix(int rows, int cols) {
        MatrixLinkedList result = new MatrixLinkedList(rows, cols);
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (i == j) {
                    result.setElement(i, j, 1);
                }
            }
        }
        return result;
    }

    public static void promptEnterKey(){
        try {
            int read = System.in.read(new byte[2]);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
