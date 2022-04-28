package com.example.view;

import pl.first.firstjava.SudokuBoard;

public class FieldAdapter {
    private SudokuBoard board;
    private int row;
    private int column;

    public FieldAdapter(SudokuBoard board, int x, int y) {
        this.board = board;
        this.row = x;
        this.column = y;
    }

    public void setFieldValue(int value) {
        board.set(row, column,value);
    }

    public int getFieldValue() {
        return board.get(row, column);
    }

}
