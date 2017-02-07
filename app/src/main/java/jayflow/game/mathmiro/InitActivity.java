package jayflow.game.mathmiro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;

/*************************** 시작화면 **************************************/
public class InitActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 타이틀바를 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 앱상단에 아이콘 추가
        getSupportActionBar().setIcon(R.drawable.icon);

        setContentView(R.layout.activity_init);
        ImageButton StartButton = (ImageButton) findViewById(R.id.StartButton);

        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitActivity.this, MainActivity.class);
                //intent.putExtra("text",String.valueOf(editText.getText())); // 인텐트로 값넘기기(사용X)
                startActivity(intent);
            }
        });
    }
}