package jayflow.game.mathmiro;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.Toast;

/*********************************** 시작화면 **************************************/
public class InitActivity extends Activity {
    int optionGame; // 게임선택

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 타이틀바를 없애기
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_init);

        ImageButton StartButton = (ImageButton) findViewById(R.id.StartButton);
        ImageButton OptionButton = (ImageButton) findViewById(R.id.OptionButton);
        ImageButton ExitButton = (ImageButton) findViewById(R.id.ExitButton);


        StartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(InitActivity.this, MainActivity.class);
                // 인텐트로 값넘기기(사용X)
                intent.putExtra("optionGame", optionGame); // 라디오 버튼에서 선택한 게임 종류값 전달
                startActivity(intent);
            }
        });

        OptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final CharSequence[] items = {"수학미로찾기", "색깔미로찾기", "동물미로찾기(준비중)"};
                AlertDialog.Builder builder = new AlertDialog.Builder(InitActivity.this);
                builder.setTitle("게임선택");

                builder.setNegativeButton("확 인", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                builder.setSingleChoiceItems(items, -1, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item) {
                        Toast.makeText(getApplicationContext(), items[item], Toast.LENGTH_SHORT).show();
                        optionGame = item;
                    }
                });

                AlertDialog alert = builder.create();
                //alert.getWindow().setBackgroundDrawable(new ColorDrawable(0xff434343));
                alert.show();

                /*Dialog dialog = new Dialog(InitActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.option_dialog);
                WindowManager.LayoutParams parms = dialog.getWindow().getAttributes();

                // params로 건드리는 width와 height는 기기마다 상대적이다
                parms.width = 1000;
                parms.height = 1000;
                dialog.getWindow().setAttributes(parms);
                dialog.show();

                RadioGroup ColGroup = (RadioGroup)findViewById(R.id.radioGroup);
                ColGroup.setOnCheckedChangeListener((RadioGroup.OnCheckedChangeListener)InitActivity.this);
*/
                //Intent intent = new Intent(InitActivity.this, MainActivity.class);
                //intent.putExtra("text",String.valueOf(editText.getText())); // 인텐트로 값넘기기(사용X)
                //startActivity(intent);
            }
        });
    }
}