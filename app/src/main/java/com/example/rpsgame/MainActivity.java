package com.example.rpsgame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imagePlayerChoice;
    private ImageView imageComputerChoice;
    private TextView textViewResult;
    private TextView textViewPlayerScore;
    private TextView textViewComputerScore;
    private Button buttonRock;
    private Button buttonPaper;
    private Button buttonScissors;
    private Button buttonReset;

    private MediaPlayer popSoundPlayer;

    private int playerScore = 0;
    private int computerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagePlayerChoice = findViewById(R.id.imagePlayerChoice);
        imageComputerChoice = findViewById(R.id.imageComputerChoice);
        textViewResult = findViewById(R.id.textViewResult);
        textViewPlayerScore = findViewById(R.id.textViewPlayerScore);
        textViewComputerScore = findViewById(R.id.textViewComputerScore);
        buttonRock = findViewById(R.id.buttonRock);
        buttonPaper = findViewById(R.id.buttonPaper);
        buttonScissors = findViewById(R.id.buttonScissors);
        buttonReset = findViewById(R.id.buttonReset);

        // Initialize MediaPlayer for pop sound effect
        popSoundPlayer = MediaPlayer.create(this, R.raw.pop);

        buttonRock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("rock");
                playPopSound();
            }
        });

        buttonPaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("paper");
                playPopSound();
            }
        });

        buttonScissors.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame("scissors");
                playPopSound();
            }
        });

        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resetScores();
            }
        });
    }

    private void playGame(String playerChoice) {
        String[] choices = {"rock", "paper", "scissors"};
        Random random = new Random();
        int computerIndex = random.nextInt(choices.length);
        String computerChoice = choices[computerIndex];

        setImage(imagePlayerChoice, playerChoice);
        setImage(imageComputerChoice, computerChoice);

        String result = determineWinner(playerChoice, computerChoice);
        textViewResult.setText(result);

        // Update scores
        if (result.equals("Player wins!")) {
            playerScore++;
        } else if (result.equals("Computer wins!")) {
            computerScore++;
        }

        textViewPlayerScore.setText("Player: " + playerScore);
        textViewComputerScore.setText("Computer: " + computerScore);
    }

    private String determineWinner(String playerChoice, String computerChoice) {
        if (playerChoice.equals(computerChoice)) {
            return "It's a tie!";
        } else if ((playerChoice.equals("rock") && computerChoice.equals("scissors")) ||
                (playerChoice.equals("paper") && computerChoice.equals("rock")) ||
                (playerChoice.equals("scissors") && computerChoice.equals("paper"))) {
            return "Player wins!";
        } else {
            return "Computer wins!";
        }
    }

    private void setImage(ImageView imageView, String choice) {
        switch (choice) {
            case "rock":
                imageView.setImageResource(R.drawable.rock);
                break;
            case "paper":
                imageView.setImageResource(R.drawable.paper);
                break;
            case "scissors":
                imageView.setImageResource(R.drawable.scissors);
                break;
            default:
                imageView.setImageResource(R.drawable.question_mark);
                break;
        }
    }

    // Play pop sound effect
    private void playPopSound() {
        if (popSoundPlayer != null) {
            popSoundPlayer.start();
        }
    }

    private void resetScores() {
        playerScore = 0;
        computerScore = 0;
        textViewPlayerScore.setText("Player: " + playerScore);
        textViewComputerScore.setText("Computer: " + computerScore);
        textViewResult.setText("");
        imagePlayerChoice.setImageResource(R.drawable.question_mark);
        imageComputerChoice.setImageResource(R.drawable.question_mark);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Make sure to release MediaPlayer resources when the app is stopped
        if (popSoundPlayer != null) {
            popSoundPlayer.release();
        }
    }
}
