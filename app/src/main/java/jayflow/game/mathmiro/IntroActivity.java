package jayflow.game.mathmiro;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

/*********************************** 인트로 관련 액티비티 *****************************************/
public class IntroActivity extends Activity {
    private Handler handler;

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            // 인텐트 생성(현재 액티비티, 새로 실행할 액티비티)
            Intent intent = new Intent(IntroActivity.this, InitActivity.class);
            startActivity(intent);
            finish();
            //overridePendingTransition 이란 함수를 이용하여 fade in,out 효과를줌. 순서가 중요
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 인트로 화면이므로 타이틀바를 없앤다.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_intro);
        init();
        // 2초뒤에 화면 넘어감
        handler.postDelayed(runnable, 2000);
    }

    public void init() {
        handler = new Handler();
    }

    // 인트로 중에 뒤로가기를 누를 경우 핸들러를 끊어버려 아무일 없게 만드는 부분
    // 미설정시 인트로 중 뒤로가기를 누르면 인트로 후에 홈화면이 나옴.
    @Override
    public void onBackPressed(){
        super.onBackPressed();
        handler.removeCallbacks(runnable);
    }
}
/*
protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_intro);

    // 액션 바 감추기
    ActionBar actionBar = getSupportActionBar();
    actionBar.hide();

    // 2초 후 인트로 액티비티 제거
    Handler handler = new Handler();
    handler.postDelayed(new Runnable() {
        @Override
        public void run() {
            Intent intent = new Intent(IntroActivity.this, MainActivity.class);
            startActivity(intent);

            finish();
        }
    }, 2000);
}
 */