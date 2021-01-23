package com.akashdileep.tictactoe;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //1:red, 2:yellow
    int player;

    //if it is false game will be stopped
    boolean gameActive;

    //used to maintain which state is visited by which player
    int[] gridState={0,0,0,0,0,0,0,0,0};

    //combinations of winning state
    int [][] winningState={ {0,1,2},
                            {3,4,5},
                            {6,7,8},
                            {0,3,6},
                            {1,4,7},
                            {2,5,8},
                            {0,4,8},
                            {2,4,6}};

    //initializes all global variables
    public void initialize() {
        player=1;
        gameActive=true;
        for(int i=0;i<gridState.length;i++)
            gridState[i]=0;
        androidx.gridlayout.widget.GridLayout gridLayout=(androidx.gridlayout.widget.GridLayout)findViewById(R.id.gridLayout);
        for(int i=0; i<gridLayout.getChildCount(); i++){
            ImageView counter=(ImageView) gridLayout.getChildAt(i);
            counter.setImageDrawable(null);
        }
    }

    //track and place right colour
    public void setImage(ImageView counter) {
       if(player==1){
           player=2;
           counter.setImageResource(R.drawable.red);
       } else {
           player=1;
           counter.setImageResource(R.drawable.yellow);
       }
        counter.setTranslationY(-1500);
        counter.animate().translationYBy(1500).rotationBy(1800).setDuration(300);
    }

    //check whether anyone won the match
    public boolean winnerCheck() {
        for(int[] winning:winningState) {
            if(gridState[winning[0]]==gridState[winning[1]] && gridState[winning[1]]==gridState[winning[2]] && gridState[winning[0]]!=0)
                return true;
        }
        return false;
    }

    //delay before displaying winning/draw message and play again button
    public void delayInDisplay(TextView winnerTextView,Button playAgainButton) {
        CountDownTimer countDownTimer=new CountDownTimer(180,180) {
            @Override
            public void onTick(long l) {

            }

            @Override
            public void onFinish() {
                winnerTextView.setVisibility(View.VISIBLE);
                playAgainButton.setVisibility(View.VISIBLE);
            }
        }.start();
    }

    //display who won the match
    public void winnerDisplay(TextView winnerTextView,Button playAgainButton) {
        String result=" has Won!";
        if(player==2)
            result="Red "+result;
        else
            result="Yellow "+result;
        winnerTextView.setText(result);
        delayInDisplay(winnerTextView,playAgainButton);
    }

    //check whether match is a draw
    public boolean draw() {
        for(int i=0;i<gridState.length;i++) {
            if(gridState[i]==0)
                return false;
        }
        return true;
    }

    //display draw message
    public void drawDisplay(TextView winnerTextView,Button playAgainButton) {
        winnerTextView.setText("This match is a Draw!");
        delayInDisplay(winnerTextView,playAgainButton);
    }

    //game starts from here
    public void playGame(View view) {
        ImageView counter=(ImageView)view;
        int tag=Integer.parseInt(counter.getTag().toString());
        if(gridState[tag]==0 && gameActive) {
            gridState[tag]=player;
            setImage(counter);
            if(winnerCheck()) {
                gameActive=false;
                TextView winnerTextView=(TextView)findViewById(R.id.winnerTextView);
                Button playAgainButton=(Button)findViewById(R.id.playAgainButton);
                winnerDisplay(winnerTextView,playAgainButton);
            } else if(draw()) {
                gameActive=false;
                TextView winnerTextView=(TextView)findViewById(R.id.winnerTextView);
                Button playAgainButton=(Button)findViewById(R.id.playAgainButton);
                drawDisplay(winnerTextView,playAgainButton);
            }
        }
    }

    //restart the game
    public void restartGame(View view) {
        TextView winnerTextView=(TextView)findViewById(R.id.winnerTextView);
        Button playAgainButton=(Button)findViewById(R.id.playAgainButton);
        winnerTextView.setVisibility(View.INVISIBLE);
        playAgainButton.setVisibility(View.INVISIBLE);
        initialize();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initialize();
    }
}