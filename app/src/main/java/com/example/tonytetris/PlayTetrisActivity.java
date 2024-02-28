package com.example.tonytetris;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.Random;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;

public class PlayTetrisActivity extends AppCompatActivity {

    //TODO statically defining now but will grab from an option later
    public static int cols = 10;
    public static int rows = 20;
    TextView name;
    public GridLayout gridLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_tetris);
        Intent intent = getIntent();
        String userName = intent.getStringExtra("USER_NAME");

        //setting name on top
        name = findViewById(R.id.playTetrisName);
        name.setText(userName);
        name.setTextColor(getResources().getColor(R.color.white));
        name.setTextSize(35);

        //setting grid
        gridLayout = findViewById(R.id.playTetrisGrid);
        gridLayout.setColumnCount(cols);
        gridLayout.setRowCount(rows);

        float cWeight = 1f/cols;
        float rWeight = 1f/rows;
        //populating grid with default images
        runOnUiThread( () -> {
            for (int row = 0; row < rows; row++) {
                for (int col = 0; col < cols; col++) {
                    ImageView imageView = new ImageView(this);
                    imageView.setLayoutParams(new GridLayout.LayoutParams());
                    imageView.setPadding(1, 1, 1, 1);
                    ((GridLayout.LayoutParams) imageView.getLayoutParams()).columnSpec = GridLayout.spec(col, 1, cWeight);
                    ((GridLayout.LayoutParams) imageView.getLayoutParams()).rowSpec = GridLayout.spec(row, 1, rWeight);
                    imageView.setImageResource(R.drawable.emptyblock);
                    imageView.setImageAlpha(254);
                    gridLayout.addView(imageView);
                }//end inner for
            }//end for
        });
        try {
            BeginGame beginGame = new BeginGame(this);
            Thread gameThread =new Thread(beginGame);
            gameThread.start();
        } catch (Exception ex) {
            Log.i("BEGINGAMETHREAD",ex.getStackTrace().toString());
        }
    }//end on create

    protected class BeginGame implements Runnable {
        Context context;

        public BeginGame(Context context){
            this.context = context;
        }//end constructor
        @Override
        public void run() {
            double pieceHeight;
            AtomicBoolean fail = new AtomicBoolean(false);
            boolean pieceMoving = false;
            int mid = (int) Math.floor((double) cols/2 );
            Random random = new Random();
            double[] piecePos = new double[4];
            ImageView imageView1,imageView2,imageView3,imageView4;
            AtomicInteger vPos = new AtomicInteger(0);

            while(!fail.get()) {
                if(!pieceMoving) {
                    int pieceNumber = random.nextInt(7);

                    //initalise section image views
                    imageView1 = (ImageView) gridLayout.getChildAt(mid-2);
                    imageView2 = (ImageView) gridLayout.getChildAt(mid-1);
                    imageView3 = (ImageView) gridLayout.getChildAt(mid);
                    imageView4 = (ImageView) gridLayout.getChildAt(mid+1);

                    switch (pieceNumber) {
                        case 0://I block
                            imageView1 = (ImageView) gridLayout.getChildAt(mid-2);
                            imageView2 = (ImageView) gridLayout.getChildAt(mid-1);
                            imageView3 = (ImageView) gridLayout.getChildAt(mid);
                            imageView4 = (ImageView) gridLayout.getChildAt(mid+1);
                            piecePos[0]=(double)(mid-2)/10;
                            piecePos[1]=(double)(mid-1)/10;
                            piecePos[2]=(double)(mid)/10;
                            piecePos[3]=(double)(mid+1)/10;
                            break;

                        case 1: //L Piece
                            imageView1 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid-1);
                            imageView2 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid);
                            imageView3 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid+1);
                            imageView4 = (ImageView) gridLayout.getChildAt(mid+1);
                            piecePos[0]=( (double) ( (gridLayout.getColumnCount()*2)-mid-1) /10);
                            piecePos[1]=( (double) ( (gridLayout.getColumnCount()*2)-mid)   /10);
                            piecePos[2]=( (double) ( (gridLayout.getColumnCount()*2)-mid+1)  /10);
                            piecePos[3]=(double)(mid+1)/10;
                            break;

                        case 2: //J Piece
                            imageView1 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid-1);
                            imageView2 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid);
                            imageView3 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid+1);
                            imageView4 = (ImageView) gridLayout.getChildAt(mid-1);
                            piecePos[0]=( (double) ( (gridLayout.getColumnCount()*2)-mid-1) /10);
                            piecePos[1]=( (double) ( (gridLayout.getColumnCount()*2)-mid)   /10);
                            piecePos[2]=( (double) ( (gridLayout.getColumnCount()*2)-mid+1)  /10);
                            piecePos[3]=(double)(mid-1)/10;
                            break;

                        case 3: //O Piece
                            imageView1 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid-1);
                            imageView2 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid);
                            imageView3 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*1)-mid-1);
                            imageView4 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*1)-mid);
                            piecePos[0]=( (double) ( (gridLayout.getColumnCount()*2)-mid-1) /10);
                            piecePos[1]=( (double) ( (gridLayout.getColumnCount()*2)-mid)   /10);
                            piecePos[2]=( (double) ( (gridLayout.getColumnCount()*1)-mid-1) /10);
                            piecePos[3]=( (double) ( (gridLayout.getColumnCount()*1)-mid)   /10);
                            break;

                        case 4: //T Piece
                            imageView1 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid-1);
                            imageView2 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid);
                            imageView3 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid+1);
                            imageView4 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*1)-mid);
                            piecePos[0]=( (double) ( (gridLayout.getColumnCount()*2)-mid-1) /10);
                            piecePos[1]=( (double) ( (gridLayout.getColumnCount()*2)-mid)   /10);
                            piecePos[2]=( (double) ( (gridLayout.getColumnCount()*2)-mid+1) /10);
                            piecePos[3]=( (double) ( (gridLayout.getColumnCount()*1)-mid)   /10);
                            break;

                        case 5: //Z Piece
                            imageView1 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid-1);
                            imageView2 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid);
                            imageView3 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*1)-mid);
                            imageView4 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*1)-mid+1);
                            piecePos[0]=( (double) ( (gridLayout.getColumnCount()*2)-mid-1) /10);
                            piecePos[1]=( (double) ( (gridLayout.getColumnCount()*2)-mid)   /10);
                            piecePos[2]=( (double) ( (gridLayout.getColumnCount()*1)-mid) /10);
                            piecePos[3]=( (double) ( (gridLayout.getColumnCount()*1)-mid+1)   /10);
                            break;

                        case 6: //S Piece
                            imageView1 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid+1);
                            imageView2 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*2)-mid);
                            imageView3 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*1)-mid);
                            imageView4 = (ImageView) gridLayout.getChildAt((gridLayout.getColumnCount()*1)-mid-1);
                            piecePos[0]=( (double) ( (gridLayout.getColumnCount()*2)-mid+1) /10);
                            piecePos[1]=( (double) ( (gridLayout.getColumnCount()*2)-mid)   /10);
                            piecePos[2]=( (double) ( (gridLayout.getColumnCount()*1)-mid) /10);
                            piecePos[3]=( (double) ( (gridLayout.getColumnCount()*1)-mid-1)   /10);
                            break;
                    }//end switch
                    Log.i("STARTPIECEPOS",(String.valueOf(piecePos[0])));
                    Log.i("STARTPIECEPOS1",(String.valueOf(piecePos[1])));
                    Log.i("STARTPIECEPOS2",(String.valueOf(piecePos[2])));
                    Log.i("STARTPIECEPOS3",(String.valueOf(piecePos[3])));

                    ImageView img1 = imageView1;
                    ImageView img2 = imageView2;
                    ImageView img3 = imageView3;
                    ImageView img4 = imageView4;

                    runOnUiThread( () -> {
                                img1.setImageResource(R.drawable.greenblock);
                                img2.setImageResource(R.drawable.greenblock);
                                img3.setImageResource(R.drawable.greenblock);
                                img4.setImageResource(R.drawable.greenblock);

                                img1.setImageAlpha(255);
                                img2.setImageAlpha(255);
                                img3.setImageAlpha(255);
                                img4.setImageAlpha(255);
                            });
                    vPos.set((int)Math.floor(piecePos[0]));
                    pieceMoving = true;

                }//end if(!pieceMoving)
                while(pieceMoving) {
                    //for every row of the grid -the piece height
                    pieceHeight = Math.ceil(piecePos[0]);
                    try {
                        for (int row = 0; row < rows - pieceHeight && pieceMoving; row++) {
                            //delay between row movement
                            Thread.sleep(250);
                            if (pieceBelow(piecePos)) {
                                //stop moving the piece because there is a piece below
                                //check all pieces to see if a the top of the board
                                for(int i=0;i<4;i++){
                                    if( ((int)Math.floor(piecePos[i]) ) < 1 ){
                                        Log.i("GAME FAIL", ":" + piecePos[i]);
                                        runOnUiThread( () ->{Toast.makeText(context,"YOU FAILED!", Toast.LENGTH_SHORT).show();});
                                        fail.set(true);
                                    }//end if
                                }//end for
                                pieceMoving = false;
                            }//end if
                            else {
                                //for each section of the piece
                                for (int i = 0; i < 4; i++) {
                                    final int hPos = (int) (piecePos[i] * 10) % 10;//col num
                                    vPos.set((int) (Math.floor(piecePos[i])));//row num

                                    if (vPos.get() < rows) {
                                        ImageView newImageView = (ImageView) gridLayout.getChildAt(hPos + (gridLayout.getColumnCount() * (vPos.get() + 1)));
                                        ImageView oldImageView = (ImageView) gridLayout.getChildAt(hPos + (gridLayout.getColumnCount() * vPos.get()));
                                        runOnUiThread( () -> {
                                            newImageView.setImageResource(R.drawable.greenblock);
                                            newImageView.setImageAlpha(255);

                                            oldImageView.setImageResource(R.drawable.emptyblock);
                                            oldImageView.setImageAlpha(254);
                                        });
                                    }//end if (vPos.get() < rows)
                                }//end for(int i=0;i<4;i++)
                                piecePos[0]++;
                                piecePos[1]++;
                                piecePos[2]++;
                                piecePos[3]++;
                            }//end if else
                        }//end for=rows-pieceHeight
                        pieceMoving = false;//piece at bottom of board
                    }catch(Exception e){
                        Log.i("ERROR PieceMoving", Arrays.toString(e.getStackTrace()));
                    }
                }//end if(pieceMoving)
            }//end while(!fail)
        }//end run
    }//end beginGame

    public boolean pieceBelow(double[] piecePos) {
        //for all sections of the piece
        for(int i = 0;i<4; i++){
            //for all the pieces
                ImageView pieceBelow = (ImageView) gridLayout.getChildAt( (int)((piecePos[i]*10) + gridLayout.getColumnCount()) );
                Log.i("BELOW","PiecePos "+i +" :" + piecePos[i]*10 + ": " + (piecePos[i]*10 + gridLayout.getColumnCount()) );
                //check is 255 and not one of the other piece
                if( (pieceBelow.getImageAlpha() == 255)&&( !( (((int)((piecePos[i]*10) + gridLayout.getColumnCount())) ==piecePos[0]*10) || (((int)((piecePos[i]*10) + gridLayout.getColumnCount())) ==piecePos[1]*10)||(((int)((piecePos[i]*10) + gridLayout.getColumnCount())) ==piecePos[2]*10)||(((int)((piecePos[i]*10) + gridLayout.getColumnCount())) ==piecePos[3]*10) )) ){
                    return true;
                }
        }//end for
        return false;
    }//end pieceBelow
}//end PlayTetrisActivity
