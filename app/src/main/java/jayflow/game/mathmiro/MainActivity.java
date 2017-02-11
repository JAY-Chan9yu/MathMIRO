package jayflow.game.mathmiro;

import java.io.IOException;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;

public class MainActivity extends Activity {
    MGameView mgameview;
    MediaPlayer mediaPlayer;
    static final int CLEARDIALOG = 0;
    private BackPressCloseHandler backPressCloseHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.main);

        mgameview = (MGameView)findViewById(R.id.mGameView);
        backPressCloseHandler = new BackPressCloseHandler(this);

        Intent intent = getIntent();
        int optionGame = 0;
        //optionGame = intent.getExtras().getInt("gameOption");
        int optionGame2 = intent.getIntExtra("optionGame", optionGame);

        //mgameview.gameOption = intent.getExtras().getInt("gameOption");
        mgameview.getGmeoption(optionGame2);
        Log.i("int화면", "main "+ optionGame2);

        /*mediaPlayer = new MediaPlayer();
        try
        {
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor = assetManager.openFd("a.ogg");
            mediaPlayer.setDataSource(
                    descriptor.getFileDescriptor(),
                    descriptor.getStartOffset(),
                    descriptor.getLength());
            mediaPlayer.prepare();
            mediaPlayer.setLooping(true);
        }
        catch(IOException e)
        {

            mediaPlayer = null;
        }*/
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        /*if(mediaPlayer!=null)
        {
            mediaPlayer.start();
        }*/
    }

    @Override
    protected void onPause()
    {
        super.onPause();
        if(mediaPlayer!=null)
        {
            mediaPlayer.pause();
            if(isFinishing())
            {
                mediaPlayer.stop();
                mediaPlayer.release();
            }
        }
    }

    @Override
    public void onBackPressed() {
        // TODO Auto-generated method stub
        backPressCloseHandler.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_dialog, menu);
        return true;
    }

    public void music_on() {
    	/*MediaPlayer myPlayer = MediaPlayer.create(this, R.raw.exok_run);
    	myPlayer.setVolume(0.2f, 0.2f);
    	myPlayer.setLooping(true);
    	myPlayer.start();
    	*/
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.nextgame:  	 // QuitGame
                mgameview.NextGame();        // ①
                break;
            case R.id.restart:      // PauseGame
                mgameview.RestartGame();
                break;
            case R.id.exit:      // PauseGame
                ActivityCompat.finishAffinity(this);
                System.runFinalizersOnExit(true);
                System.exit(0);
                break;
        }
        return true;
    }
}