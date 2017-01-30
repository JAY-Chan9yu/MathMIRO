package jayflow.game.mathmiro;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;

/*************************** 시작화면 **************************************/
public class InitActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_init);
        Button StartButton = (Button) findViewById(R.id.StartButton);

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