package com.example.tic_tac_game;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.AppCompatTextView;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private AppCompatButton[][] buttons= new AppCompatButton[3][3];

    private boolean player1Turn= true;
    private int roundCount;
    private int player1Points;
    private int player2Points;

    private AppCompatTextView player1TextView;
    private AppCompatTextView player2TextView;
    private AppCompatButton resetButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findSection();
    }

    private void findSection() {
        player1TextView= findViewById(R.id.player1TextViewId);
        player2TextView= findViewById(R.id.player2TextViewId);

        for (int i=0;i<3;i++){

            for(int j=0;j<3;j++){

                String buttonId= "button_"+i+j;
                int resID= getResources().getIdentifier(buttonId,"id",getPackageName());
                buttons[i][j]= findViewById(resID);
                buttons[i][j].setOnClickListener(this);
            }

        }
        resetButton= findViewById(R.id.resetButtonId);
        resetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetGame();
            }
        });
    }

    private void resetGame() {
        player1Points=0;
        player2Points=0;
        updatePointText();
        resetBoard();
    }

    @Override
    public void onClick(View v) {

        if(!((AppCompatButton) v).getText().toString().equals("")){
            return;
        }

        if(player1Turn){
            ((AppCompatButton)v).setText("X");
        }
        else {
            ((AppCompatButton)v).setText("O");
        }
        roundCount++;

        if(checkWin()){
            if(player1Turn){
                player1Wins();
            }
            else {
                
                player2Wins();
            }
        }
        else if(roundCount==9){
            draw();

        }
        else {
            player1Turn= !player1Turn;
        }
    }

    private void draw() {

        Toast.makeText(this, "Draw !", Toast.LENGTH_SHORT).show();
        resetBoard();
    }

    private void player2Wins() {
        player2Points++;
        Toast.makeText(this, "Player 2 Wins!", Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();

    }

    private void player1Wins() {
        player1Points++;
        Toast.makeText(this, "Player 1 Wins!", Toast.LENGTH_SHORT).show();
        updatePointText();
        resetBoard();

    }

    private void resetBoard() {
        for (int i=0;i<3;i++){

            for(int j=0;j<3;j++) {
                buttons[i][j].setText("");
            }
        }
        roundCount=0;
        player1Turn= true;
    }

    private void updatePointText() {
        player1TextView.setText("Player 1: "+player1Points);
        player2TextView.setText("Player 2: "+player2Points);
    }

    private boolean checkWin(){
        String[][] field= new String[3][3];

        for (int i=0;i<3;i++){

            for(int j=0;j<3;j++) {

                field[i][j]= buttons[i][j].getText().toString();
            }
        }

        for (int i=0;i<3;i++){

            if(field[i][0].equals(field[i][1]) && field[i][0].equals(field[i][2])
            && !field[i][0].equals("")){
                return true;
            }
        }
        for (int i=0;i<3;i++){

            if(field[0][i].equals(field[1][i]) && field[0][i].equals(field[2][i])
                    && !field[0][i].equals("")){
                return true;
            }
        }

        if(field[0][0].equals(field[1][1]) && field[0][0].equals(field[2][2])
                && !field[0][0].equals("")){
            return true;
        }

        if(field[0][2].equals(field[1][1]) && field[0][2].equals(field[2][0])
                && !field[0][2].equals("")){
            return true;
        }
        return false;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("roundCount",roundCount);
        outState.putInt("player1Point",player1Points);
        outState.putInt("player2Point",player2Points);
        outState.putBoolean("player1Turn",player1Turn);

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        roundCount= savedInstanceState.getInt("roundCount");
        player1Points= savedInstanceState.getInt("player1Point");
        player2Points= savedInstanceState.getInt("player2Point");
        player1Turn= savedInstanceState.getBoolean("player1Turn");
    }
}
