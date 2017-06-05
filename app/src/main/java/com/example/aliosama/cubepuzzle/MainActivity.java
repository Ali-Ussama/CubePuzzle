package com.example.aliosama.cubepuzzle;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    ImageView[][] cells;
    TableRow Row;
    TableLayout root;
    TextView TVScore, TVCheating;
    Map<Integer, Integer> M ,checkImage;
    int[] arr = {0,R.drawable.pic1,R.drawable.pic2, R.drawable.pic3,R.drawable.pic4,R.drawable.pic5,R.drawable.pic6,R.drawable.pic7,R.drawable.pic8};
    int[][] index = new int[4][4];
    int randomnumber , ClickCount = 0,Score = 0;

    String Cheating ="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            M = new HashMap<>();
            checkImage = new HashMap<>();
            for(int i = 0 ; i < 8 ; i++)
                checkImage.put(arr[i+1],0);
            setRandomBoard();
            buildBoard();
            TVCheating.setText(Cheating);
            for(int i = 0 ; i < 4 ; i++){
                for (int j = 0 ; j < 4 ; j++){
                    final ImageView imageView = cells[i][j];
                    final int ImageResource = arr[index[i][j]];

                    imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                if(ClickCount < 1 && (imageView.getTag()).equals(R.drawable.defpic)) {
                                    imageView.setImageResource(ImageResource);
                                    imageView.setTag(ImageResource);
                                    if(checkImage.get(ImageResource) < 2)
                                        checkImage.put(ImageResource,checkImage.get(ImageResource) + 1);
                                        ClickCount++;
                                }else if (ClickCount >= 1){
                                    if(checkImage.get(ImageResource) == 0) {
                                        RestartActivity();
                                    }else if(checkImage.get(ImageResource) != 0 && (imageView.getTag()).equals(R.drawable.defpic)){
                                        imageView.setImageResource(ImageResource);
                                        imageView.setTag(ImageResource);
                                        ClickCount = 0;
                                        Score++;
                                        TVScore.setText(Integer.toString(Score));
                                    }
                                }
                                if(Score == 8) {
                                    Toast.makeText(MainActivity.this,"You Are The Master",Toast.LENGTH_LONG).show();
                                    RestartActivity();
                                }
                            }
                        });

                    if(ClickCount == 1) {
                        cells[i][j].setTag(ImageResource);
                    }
                }
            }
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void RestartActivity() {
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
    private void setRandomBoard(){

        for(int i = 1 ; i < arr.length; i++){
            M.put(arr[i], 0);
        }
        for(int i = 0 ; i < 4 ; i++){
            for(int j = 0 ; j < 4 ; j++){

                do{
                    randomnumber = (int) (Math.random()*9);
                    if(randomnumber > 0 && M.get(arr[randomnumber]) < 2)
                        break;
                }while(true);
                if(M.get(arr[randomnumber]) < 2){
                    index[i][j] = randomnumber;
                    M.put(arr[randomnumber], M.get(arr[randomnumber]) + 1);
                }
                Cheating += Integer.toString(randomnumber) + " ";
            }
            Cheating += "\n";
        }
    }
    private void buildBoard(){
        root = (TableLayout) findViewById(R.id.root);
        root.setStretchAllColumns(true);
        TVScore = (TextView) findViewById(R.id.ScoreTV);
        TVCheating = (TextView) findViewById(R.id.CheatingTV);
        cells = new ImageView[4][4];
        for(int i = 0 ; i < 4 ; i++){
            Row = new TableRow(MainActivity.this);
            root.addView(Row);
            for(int j = 0 ;j < 4; j++){
                cells[i][j] = new ImageView(MainActivity.this);
                cells[i][j].setImageResource(R.drawable.defpic);
                cells[i][j].setTag(R.drawable.defpic);
                Row.addView(cells[i][j]);
            }
        }
    }
}
