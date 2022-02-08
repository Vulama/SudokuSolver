package com.example.sudokusolver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Board gameBoard;
    private Solver gameBoardSolver;

    private Button solveBTN;
    private TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gameBoard = findViewById(R.id.board);
        gameBoardSolver = gameBoard.getSolver();
        solveBTN = findViewById(R.id.button10);
        textView = findViewById(R.id.textView);
    }

    public void BTNOnePress(View view){
        gameBoardSolver.setNumberPos(1);
        gameBoard.invalidate();
    }
    public void BTNTwoPress(View view){
        gameBoardSolver.setNumberPos(2);
        gameBoard.invalidate();
    }
    public void BTNThreePress(View view){
        gameBoardSolver.setNumberPos(3);
        gameBoard.invalidate();
    }
    public void BTNFourPress(View view){
        gameBoardSolver.setNumberPos(4);
        gameBoard.invalidate();
    }
    public void BTNFivePress(View view){
        gameBoardSolver.setNumberPos(5);
        gameBoard.invalidate();
    }
    public void BTNSixPress(View view){
        gameBoardSolver.setNumberPos(6);
        gameBoard.invalidate();
    }
    public void BTNSevenPress(View view){
        gameBoardSolver.setNumberPos(7);
        gameBoard.invalidate();
    }
    public void BTNEightPress(View view){
        gameBoardSolver.setNumberPos(8);
        gameBoard.invalidate();
    }
    public void BTNNinePress(View view){
        gameBoardSolver.setNumberPos(9);
        gameBoard.invalidate();
    }

    public void solve(View view){
        if(solveBTN.getText().toString().equals(getString(R.string.solve))){
            solveBTN.setText(getString(R.string.clear));

            gameBoardSolver.getEmptyBoxIndexs();

            SolveBoardThread sbt = new SolveBoardThread();
            new Thread(sbt).start();

            gameBoard.invalidate();
        }else{
            textView.setText("");
            solveBTN.setText(getString(R.string.solve));
            gameBoardSolver.resetBoard();
            gameBoard.invalidate();
        }
    }

    class SolveBoardThread implements Runnable{
        @Override
        public void run(){
            if(gameBoardSolver.solve(gameBoard)){
                runOnUiThread(new Runnable() {
                    public void run() {
                        textView.setText("Sudoku uspješno riješen");
                    }
                });
            }else{
                runOnUiThread(new Runnable() {
                    public void run() {
                        textView.setText("Sudoku nije moguće riješiti");
                    }
                });
            }
        }
    }
}