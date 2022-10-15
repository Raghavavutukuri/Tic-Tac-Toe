package com.example.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    TextView playerOneScore, playerTwoScore, playerStatus,playerTurn;
    Button buttons[] = new Button[9];
    Button resetGame;

    int playerOneCount, playerTwoCount, routeCount;
    boolean activePlayer;

    //p1 => 0
    //p2 => 1
    //empty => 2
    int gameState[] = {2, 2, 2, 2, 2, 2, 2, 2, 2};
    int[][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},//rows
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},//columns
            {0, 4, 8}, {2, 4, 6}//cross
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        playerOneScore = (TextView) findViewById(R.id.playerOneScore);
        playerTwoScore = (TextView) findViewById(R.id.playerTwoScore);
        playerStatus = (TextView) findViewById(R.id.playerStatus);
        playerTurn = (TextView) findViewById(R.id.playerTurn);

        resetGame = (Button) findViewById(R.id.resetGame);
        for (int i = 0; i < buttons.length; i++) {
            String buttonId = "btn_" + i;
            int resourceId = getResources().getIdentifier(buttonId, "id", getPackageName());
            buttons[i] = (Button) findViewById(resourceId);
            buttons[i].setOnClickListener(this);
        }
        routeCount = 0;
        playerOneCount = 0;
        playerTwoCount = 0;

        activePlayer = true;
    }

    @Override
    public void onClick(View view) {
        Log.i("test", "button is clicked!");
        if (!((Button) view).getText().equals("")) {
            return;
        }

        String buttonId = view.getResources().getResourceName(view.getId()); //btn_0
        int gameStatePointer = Integer.parseInt(buttonId.substring(buttonId.length() - 1, buttonId.length())); //0

        if (activePlayer) {
            ((Button) view).setText("X");
            ((Button)view).setTextColor(getApplication().getResources().getColor(android.R.color.holo_orange_light));
            gameState[gameStatePointer] = 0;
        } else {
            ((Button) view).setText("O");
            ((Button)view).setTextColor(getApplication().getResources().getColor(R.color.teal_200));
            gameState[gameStatePointer] = 1;
        }
        routeCount++;
        if(activePlayer)
        {
            playerTurn.setText("Player Two Turn");
        }
        else{
            playerTurn.setText("Player One Turn");
        }
        if (checkWinner()) {
            if (activePlayer) {
                playerOneCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player One Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            } else {
                playerTwoCount++;
                updatePlayerScore();
                Toast.makeText(this, "Player Two Won!", Toast.LENGTH_SHORT).show();
                playAgain();
            }
        } else if (routeCount == 9) {
            playAgain();
            Toast.makeText(this, "No Player Won!", Toast.LENGTH_SHORT).show();
        } else {
            activePlayer = !activePlayer;
        }
        if(playerOneCount>playerTwoCount)
        {
            playerStatus.setText("Player One is Winning!");
        }
        else if(playerTwoCount>playerOneCount)
        {
            playerStatus.setText("Player Two is winning!");
        }
        else
        {
            playerStatus.setText("");
        }

        resetGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                playAgain();
                playerOneCount = 0;
                playerTwoCount = 0;
                playerStatus.setText("");
                updatePlayerScore();
            }
        });
    }

    public boolean checkWinner() {
        boolean winnerResult = false;
        for (int[] winninPosition : winningPositions) {
            if (gameState[winninPosition[0]] == gameState[winninPosition[1]] &&
                    gameState[winninPosition[1]] == gameState[winninPosition[2]] &&
                    gameState[winninPosition[0]] != 2) {
                winnerResult = true;
            }
        }
        return winnerResult;
    }

    public void updatePlayerScore() {
        playerOneScore.setText(Integer.toString(playerOneCount));
        playerTwoScore.setText(Integer.toString(playerTwoCount));
    }

    public void playAgain() {
        routeCount = 0;
        activePlayer = true;
        for (int i = 0; i < 9; i++) {
            gameState[i] = 2;
            buttons[i].setText("");
        }
        playerTurn.setText("Player One Turn");
    }
}