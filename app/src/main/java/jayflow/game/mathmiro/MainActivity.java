package jayflow.game.mathmiro;

import java.io.IOException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
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
        menu.add(0, 1, 0, "다음 스테이지");
        menu.add(0, 2, 0, "다시시작");
        menu.add(0, 3, 0, "나가기");
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
            case 1:  	 // QuitGame
                //mgameview.NextGame();        // ①
                break;
            case 2:      // PauseGame
                //mgameview.RestartGame();
                break;
            case 3:      // PauseGame
                System.exit(0);
                break;
        }
        return true;
    }
}