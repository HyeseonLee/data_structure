package cse2010.homework1;

import java.util.Arrays;
import java.util.Comparator;

public class ArraySparseMatrix implements SparseMatrix {

    public static final int DEFAULT_CAPACITY = 1024;

    private int rowCount;
    private int columnCount;
    private int elemCount; //elements 갯수
    private Entry[] elements = new Entry[0];

    public ArraySparseMatrix(int rowCount, int columnCount, int capacity) {
        elements = new Entry[capacity];
        this.rowCount = rowCount;
        this.columnCount = columnCount;
        this.elemCount = 0;
    }

    public ArraySparseMatrix(int rowCount, int columnCount) {
        this(rowCount, columnCount, DEFAULT_CAPACITY);
    }

    /*
     * This routine simulates reading from files using two-dimensional matrix.
     */
    public static SparseMatrix create(double[][] aMatrix, int rowCount, int columnCount, int elemCount) {
        ArraySparseMatrix matrix = new ArraySparseMatrix(rowCount, columnCount, elemCount);

        int nonZeroCount = 0;
        for (int i = 0; i < aMatrix.length; i++)
            for (int j = 0; j < aMatrix[i].length; j++) {
                if (Double.compare(aMatrix[i][j], 0.0) != 0) {
                    matrix.put(new Entry(i, j, aMatrix[i][j])); //0이 아닌 Entry를 Entry[] elements에 put
                    nonZeroCount++; //0이 아닌 요소의 숫자++
                }
            }

            if(nonZeroCount != elemCount) {
                throw new IllegalStateException("Non zero count doesn't match");
            }
            // if (nonZeroCount != elemCount) throw IllegalStateException saying 
            // "Non zero count doesn't match"
        

        return matrix;
    }

    private void put(Entry entry) {
        elements[elemCount++] = entry;
    } //Entry[] elements에 차곡차곡 넣고, elemCount++까지!

    @Override
    public SparseMatrix transpose() {
        int this_rowCount=this.getRowCount();
        int this_colCount=this.getColumnCount();
        int this_elemCount=this.getElemCount();

        double[][] trans = new double[this_colCount][this_rowCount];
        for(int i=0; i<this_elemCount; i++) {
            trans[this.elements[i].getCol()][this.elements[i].getRow()] = this.elements[i].getValue();
        }

        SparseMatrix trans_sm = create(trans, this_colCount, this_rowCount, this_elemCount);
        return trans_sm;
    }

    @Override
    public SparseMatrix add(SparseMatrix other) {
        if (this.rowCount != other.getRowCount() || this.columnCount != other.getColumnCount())
            throw new IllegalArgumentException("Matrix size doesn't match");

        ArraySparseMatrix other_asm = (ArraySparseMatrix) other; //SparseMatrix other를 ArraySparseMatrix로 바꿔야한다.

        int this_rowCount=this.getRowCount();
        int this_colCount=this.getColumnCount();
        int this_elemCount=this.getElemCount();
        int other_elemCount = other_asm.getElemCount();
        int check=0;


        double[][] add = new double[this_rowCount][this_colCount];

        for(int i=0; i<this_elemCount; i++) {
            add[this.elements[i].getRow()][this.elements[i].getCol()] = this.elements[i].getValue();
        }
        for(int i=0; i<other_elemCount; i++) {
            add[other_asm.elements[i].getRow()][other_asm.elements[i].getCol()] += other_asm.elements[i].getValue();
        }

        for(int i=0; i<this_rowCount; i++){
            for(int j=0; j<this_colCount; j++){
                if(add[i][j]!=0) {
                    check++;
                }
            }
        }

        SparseMatrix add_sm = create(add, this_rowCount, this_colCount, check);
        return add_sm;
    }

    public Entry[] getElements() {
        return elements;
    }

    @Override
    public SparseMatrix multiply(SparseMatrix aMatrix) {
        throw new IllegalStateException("Not implemented");
    }

    @Override
    public int getRowCount() {
        return rowCount;
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public int getElemCount() {
        return elemCount;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof ArraySparseMatrix)) return false;
        ArraySparseMatrix other = (ArraySparseMatrix) obj;

        if (rowCount != other.rowCount || columnCount != other.columnCount || elemCount != other.elemCount)
            return false;

        for (int i = 0; i < elements.length; i++) {
            if (!elements[i].equals(other.elements[i])) return false;
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(rowCount + "\t" + columnCount + "\t" + elemCount + "\n");
        for (int i = 0; i < elemCount; i ++)
            builder.append(elements[i] + "\n");

        return builder.toString();
    }
}
