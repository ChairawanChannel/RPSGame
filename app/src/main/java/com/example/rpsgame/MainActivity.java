package com.example.rpsgame;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.rpsgame.R;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    private ImageView imagePlayerChoice;
    private ImageView imageComputerChoice;
    private TextView textViewResult;
    private Button buttonRock;
    private Button buttonPaper;
    private Button buttonScissors;

    private MediaPlayer popSoundPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imagePlayerChoice = findViewById(R.id.imagePlayerChoice);
        imageComputerChoice = findViewById(R.id.imageComputerChoice);
        textViewResult = findViewById(R.id.textViewResult);
        buttonRock = findViewById(R.id.buttonRock);
        buttonPaper = findViewById(R.id.buttonPaper);
        buttonScissors = findViewById(R.id.buttonScissors);

        // Inisialisasi MediaPlayer untuk efek suara pop
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

    // Memainkan efek suara pop
    private void playPopSound() {
        if (popSoundPlayer != null) {
            popSoundPlayer.start();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Pastikan untuk melepaskan sumber daya MediaPlayer saat aplikasi dihentikan
        if (popSoundPlayer != null) {
            popSoundPlayer.release();
        }
    }
}
