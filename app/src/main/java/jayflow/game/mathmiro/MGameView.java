package jayflow.game.mathmiro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.os.Handler;
import android.os.Message;
import android.util.*;
import android.view.*;
import android.widget.TextView;

import java.util.Random;

public class MGameView extends SurfaceView implements Callback {
    static GameThread mThread;                    // GameThread
    SurfaceHolder mHolder;                        // SurfaceHolder
    static Context mContext;                      // Context

    public static int gameOption = 0; // 게임종류 선택 변수

    // 화면의 폭과 높이
    int width, height;
    int w_width, w_height;

    public int touch_flag = 0; // 터치시 1, 2, 3, 4(상,하,좌,우)

    // 계산 관련 배열
    public int calcul_arr[] = new int[] {-1, -1, -1, -1, -1, -1, -1, -1, -1, -1};

    // 색깔미로찾기 용 랜덤 어레이
    int []numRandomeArray = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8, 9};

    // 캐릭 터 좌표
    int character_x = 5;
    int character_y = 5;

    boolean init_map_flag = true;
    int result = 0; // 계산결과
    int displayResult = 0;
    int stage_num = 0; // 스테이지 넘버
    int stage_clear_num = 0; // 스테이지 클리어 넘버
    // Game map
    int[][][] map = new int[][][]{
                    {{10, 10, 10, 10, 10, 10, 10, 10, 10, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 1, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 0, 0, 0, 0, 0, 0, 0, 0, 10},
                    {10, 10, 10, 10, 10, 10, 10, 10, 10, 10}} ,

                    {{0, 0, 12, 0, 0, 0, 0, 12, 0, 0},
                    {0, 0, 12, 0, 0, 0, 0, 12, 0, 0},
                    {0, 0, 12, 0, 0, 0, 0, 12, 0, 0},
                    {0, 0, 12, 0, 0, 0, 0, 12, 0, 0},
                    {0, 0, 12, 12, 12, 12,12, 12, 0, 0},
                    {0, 0, 12, 0, 0, 13, 0, 12, 0, 0},
                    {0, 0, 12, 0, 0, 1, 0, 12, 0, 0},
                    {0, 0, 12, 0, 0, 0, 0, 12, 0, 0},
                    {0, 0, 12, 0, 0, 0, 0, 12, 0, 0},
                    {0, 0, 12, 0, 0, 0, 0, 12, 0, 0}},

                    {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {0, 0, 0, 0, 3, 3, 0, 0, 0, 0},
                    {0, 0, 0, 0, 3, 3, 0, 0, 0, 0},
                    {0, 0, 0, 0, 3, 3, 0, 0, 0, 0},
                    {0, 0, 0, 0, 3, 3, 0, 0, 0, 0},
                    {0, 0, 0, 0, 3, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 3, 3, 0, 0, 0, 0},
                    {0, 0, 0, 13, 13, 13, 13, 0, 0, 0},
                    {0, 0, 5, 5, 5, 5, 5, 5, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},

                    {{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                    {7, 7, 0, 0, 0, 0, 0, 0, 0, 7},
                    {7, 7, 0, 0, 0, 11, 0, 0, 0, 7},
                    {7, 7, 0, 0, 11, 10, 11, 0, 0, 7},
                    {7, 7, 0, 11, 1, 2, 3, 11, 0, 7},
                    {7, 7, 0, 11, 1, 2, 3, 11, 0, 7},
                    {7, 7, 0, 0, 11, 10, 11, 0, 0,7},
                    {7, 7, 0, 0, 0, 11, 0, 0, 0, 7},
                    {7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                    {7, 7, 7, 7, 7, 7, 7, 7, 7, 7}},

                    {{7, 7, 7, 7, 7, 7, 7, 7, 7, 7},
                    {6, 6, 6, 6, 6, 6, 6, 6, 6, 6},
                    {5, 5, 5, 5, 5, 5, 5, 5, 5, 5},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {3, 3, 3, 3, 3, 3, 3, 3, 3, 3},
                    {2, 2, 2, 2, 2, 2, 2, 2, 2, 2},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1}},

                    {{0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0},
                    {0, 0, 0, 0, 1, 1, 0, 0, 0, 0}},

                    {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 11, 11, 11, 11, 0, 0, 0},
                    {0, 0, 0, 11, 10, 10, 11, 0, 0, 0},
                    {0, 0, 0, 11, 10, 10, 11, 0, 0, 0},
                    {0, 0, 0, 11, 10, 10, 11, 0, 0, 0},
                    {0, 0, 0, 11, 11, 11, 11, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}},

                    {{0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                    {0, 0, 0, 0, 0, 0, 0, 0, 0, 0}}
    };

    // 캐릭터 폭, 높이
    int cw, ch;
    Bitmap image_array[] = new Bitmap[17];

    /************************************** 생성자 ******************************************/
    public MGameView(Context context, AttributeSet attrs) {
        super(context, attrs);

        // SurfaceHolder.Callback을 설정함으로써 Surface가 생성/소멸되었음을 알 수 있다.
        SurfaceHolder holder = getHolder();
        holder.addCallback(this);

        mHolder = holder;                               // holder와 Context 보존
        mContext = context;
        mThread = new GameThread(holder, context);      // Thread 만들기

        Display display = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay();

        width = display.getWidth();   // 화면의 가로폭
        height = display.getHeight();  // 화면의 세로폭

        //모든 화면에 최적화 시키기위해
        w_width = width / 10;
        w_height = height / 13;

/*        bitmap_upload(w_width, w_height);

        // 캐릭터 폭, 높이
        cw = image_array[0].getWidth() / 2;
        ch = image_array[0].getHeight() / 2;
        */

        setFocusable(true);     // View가 Focus받기
    }

    public void getGmeoption(int a) {
        gameOption = a;
        Log.i("test", "test" + gameOption);
    }

    public void getRandomNum() {
        int ran = 0;    //랜덤값을 받을 변수를 만듭니다.
        boolean cheak;    // 비교하기 위해 boolean형 변수를 만듭니다.
        Random r = new Random();    // Random형 객체를 만듭니다.

        for (int i = 0; i < numRandomeArray.length; i++) {    // 배열의 크기만큼 for문을 돌립니다.
            ran = r.nextInt(10);
            cheak = true;    // i문이 돌 때마다 cheak를 true로 만듭니다.
            for (int j = 0; j < i; j++) {    //if문 때문에 j를 i값만큼 돌립니다.
                if (numRandomeArray[j] == ran) {
                    // arr배열의 방은 다 비어있는 상태이고 위에서 nextInt를 해야 하나씩 들어갑니다.
                    // 그러므로 i의 값만큼 배열에  들어가있는 것입니다.
                    // for문을 돌리면서  방금 받은 random값과 배열에 들어있는 값들을 비교하여 같은게 있으면
                    i--;    // i의 값을 하나 줄여 한 번 더 돌게 합니다.
                    cheak = false;    // 목적과는 다르게 같은 값이 나왔으므로 cheak를 false로 만듭니다.
                }
            }

            if (cheak) numRandomeArray[i] = (int)ran;    // ran에 받은 값을 arr[i]방에 넣습니다.
        }
    }

    /****************************************** Resource 업로드 함수 *****************************************************/
    void bitmap_upload(int w_width, int w_height) {
        if(gameOption == 0) {
            int[] res = new int[]{R.drawable.zero, R.drawable.one, R.drawable.two, R.drawable.tree, R.drawable.four, R.drawable.five,
                    R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.plus, R.drawable.minus,
                    R.drawable.mul, R.drawable.divi, R.drawable.hunter, R.drawable.banner};
            for(int i = 0; i < 16; i ++) {
                image_array[i] = BitmapFactory.decodeResource(getResources(), res[i]);
            }
        } else if(gameOption == 1) {
            int[] res = new int[]{R.drawable.redb, R.drawable.orangeb, R.drawable.yellowb, R.drawable.greenb, R.drawable.blueb, R.drawable.puppleb,
                    R.drawable.mintb, R.drawable.skyblueb, R.drawable.grayb, R.drawable.pinkb, R.drawable.c1, R.drawable.c2, R.drawable.c3, R.drawable.c4,
                    R.drawable.hunter, R.drawable.banner};
            getRandomNum();
            for(int i = 0; i < 16; i ++) {
                if(i < 10) image_array[i] = BitmapFactory.decodeResource(getResources(), res[numRandomeArray[i]]);
                else image_array[i] = BitmapFactory.decodeResource(getResources(), res[i]);
            }
        }

        // 화면 크기에 맞게 비트맵 크기 변경
        for(int i = 0; i < 15; i++) {
            image_array[i] = Bitmap.createScaledBitmap(image_array[i], w_width, w_height, true);
        }
        image_array[15] = Bitmap.createScaledBitmap(image_array[15], w_width * 10, w_height * 3, true);
    }

    /******************  SurfaceView가 생성될 때 실행되는 부분******************/
    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        mThread.start(); // Thread 실행

    }

    /******************  SurfaceView가 바뀔 때 실행되는 부분 *******************/
    @Override
    public void surfaceChanged(SurfaceHolder arg0, int format, int width, int height) {

    }

    /******************  SurfaceView가 해제될 때 실행되는 부분 ******************/
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        //StopGame();
        boolean done = true;
        while (done) {
            try {
                mThread.join();                        // 스레드가 현재 step을 끝낼 때 까지 대기
                done = false;
            } catch (InterruptedException e) {         // 인터럽트 신호가 오면?
                // 그 신호 무시 - 아무것도 않음
            }
        } // while
    } // surfaceDestroyed

    //  스레드 완전 정지
    public static void StopGame() { mThread.StopThread(); }
    //  스레드 일시 정지
    public static void PauseGame() { mThread.PauseNResume(true); }
    //  스레드 재기동
    public static void ResumeGame() { mThread.PauseNResume(false); }

    /****************** 게임 초기화 함수 ****************/
    public void RestartGame() {
        mThread.StopThread();  // 스레드 중지

        // 현재의 스레드를 비우고 다시 생성
        mThread = null;
        mThread = new GameThread(mHolder, mContext);
        mThread.start();

        character_x = 5;
        character_y = 5;

        for(int j = 0; j < 10; j ++) calcul_arr[j] = -1;

        result = 0;
        init_map_flag = true;
    }

    /************************************** 다음 스테이지 *****************************************/
    public void NextGame() {
        if(stage_num < 7) {
            mThread.StopThread();  // 스레드 중지

            // 현재의 스레드를 비우고 다시 생성
            mThread = null;
            mThread = new GameThread(mHolder, mContext);
            mThread.start();

            character_x = 5;
            character_y = 5;
            result = 0;

            for(int j = 0; j < 10; j ++) calcul_arr[j] = -1;

            stage_num ++;
            init_map_flag = true;
        }
    }

    /************************************** 핸들러 관련 *******************************************/
    public Handler handler = new Handler(){ //핸들러를 통해 다이얼로그를 불러준다
        public void handleMessage(Message msg){
            if(msg.what==0){
                LayoutInflater factory = LayoutInflater.from(mContext);
                final View textEntryView = factory.inflate(R.layout.custom_dialog, null);

                new AlertDialog.Builder(mContext)
                        .setPositiveButton("Next", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                character_x = 5;
                                character_y = 5;
                                for(int j = 0; j < 10; j ++) {
                                    calcul_arr[j] = -1;
                                }
                                result = 0;
                                init_map_flag = true;
                                stage_clear_num = -1;
                                if(stage_num < 7) stage_num ++;
                            }
                        }).setView(textEntryView)
                        .show();
            } else if(msg.what==1){
                LayoutInflater factory = LayoutInflater.from(mContext);
                final View textEntryView = factory.inflate(R.layout.mission_dialog, null);

                TextView tv = (TextView)textEntryView.findViewById(R.id.missionText);
                tv.setText("    다음 숫자를 만드시오 : " + stage_clear_num);

                new AlertDialog.Builder(mContext)
                        .setView(textEntryView)
                        .setPositiveButton("확 인", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        }).setView(textEntryView)
                        .show();
            }
        }
    };

    /************************************* Thread *********************************************/
    class GameThread extends Thread {
        boolean isWait = true;   // Thread 제어용
        private boolean isLoop = false;	   // Thread 제어 용
        Context gcontext;

        // GameThread 생성자
        public GameThread(SurfaceHolder holder, Context context) {
            isLoop = true;
            gcontext = context;
            result = 0;
        }

        /********************** 문제를 만들어 내는 함수 *********************/
        // 그냥 무작정 랜덤으로 문제를 내게 된다면, 맵에서 만들 수 없는 숫자들이 문제로 나온다.
        // 이런경우를 없애기 위하여 캐릭터의 위치로부터 랜덤으로 10번 이동하여 나온 숫자를 문제로 낸다.
        // 이렇게 할 경우 적어도 1가지의 경우의 수(정답을 만들수 있는 길)가 나오게 된다.
        public void makeQuiz() {
            int[] check_array = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
            int check_x, check_y; // = 의 좌표
            int c_result = 0;
            int c_num = 0;
            int c_cal = 0;
            int direction = 0; // 이동방향
            double rand_d = 0;

            check_x = character_x;
            check_y = character_y;

            if(map[stage_num][check_y][check_x] < 10) check_array[9] =  map[stage_num][check_y][check_x];
            else check_array[9] =  0;

            for(int i = 0; i < 11; i ++) {
                rand_d = Math.random();
                direction = (int)(rand_d * 4); // 0 ~ 3

                if(direction == 0) check_y -= 1; // 상
                else if(direction == 1) check_x += 1; // 우
                else if(direction == 2) check_y += 1; // 하
                else if(direction == 3) check_x -= 1; // 좌

                // 9보다 크면 기호 10보다 작으면 숫자
                if((map[stage_num][check_y][check_x] > 9 && check_array[9] < 10 && check_array[9] > -1) ||
                        (map[stage_num][check_y][check_x] < 10 && check_array[9] > 9 )) {
                    // 한칸씩 값을 넘겨주면서 새로운 값을 저장
                    for(int j = 0; j < 9; j ++) { check_array[j] = check_array[j + 1]; }
                    check_array[9] = map[stage_num][check_y][check_x];

                    // 숫자에서 숫자로 이동, 사칙연산에서 사칙연산으로의 이동은 배열에 연속적으로 저장하는게 아니라
                    // 같은자리에 저장(숫자->사칙연산, 사칙연산->숫자일 경우만 배열의 다음 칸에 저장
                } else if((map[stage_num][check_y][check_x] < 10 && check_array[9] < 10 && check_array[9] > -1) ||
                        (map[stage_num][check_y][check_x] > 9 && check_array[9] > 9)) {
                    check_array[9] = map[stage_num][check_y][check_x];
                }
            }
            //result_check(c_cal, c_num, c_result, check_array);
            for(int i = 0; i < 10; i ++) {
                // -1(비워있는 상태)이 아니면
                if(check_array[i] != -1 ) {
                    if(check_array[i] < 10) { // 배열의 값이 숫자일 경우
                        if(c_result == 0)	c_result = check_array[i];
                        else c_num = check_array[i];

                        // 모든 사칙연산은 순서대로 실행(곱셈이나 나눗셈 먼저 실행되는 알고리즘 X)
                        if(c_cal == 10) c_result = c_result + c_num;
                        else if(c_cal == 11) c_result = c_result - c_num;
                        else if(c_cal == 12) c_result = c_result * c_num;
                        else if(c_cal == 13) c_result = (int)((double)c_result / c_num);
                    } else { // 배열의 값이 사칙연산일 경우
                        if(check_array[i] == 10) c_cal = 10;
                        else if(check_array[i] == 11) c_cal = 11;
                        else if(check_array[i] == 12) c_cal = 12;
                        else if(check_array[i] == 13) c_cal = 13;
                    }
                }
            }
            stage_clear_num = c_result;

            // 0일 경우 result와 초기값이 매치되서 버그가 생김(다음스테이지 눌렀을 경우 바로 그다음스테이 넘어가는 버그)
            if(stage_clear_num == 0) {
                rand_d = Math.random();
                direction = (int)(rand_d * 9); // 1 ~ 10
                stage_clear_num = direction + 1;
            }
            handler.sendEmptyMessage(1);
        }


        /************************** 게임이 실행되는 함수(or 이미지출력) *********************************/
        public void Draw_Game(Canvas canvas) {
            // 처음에만 랜덤으로 숫자 및 기호 배열에 넣어주기 위해
            if(init_map_flag == true) {
                //랜덤으로 수학기호 및 숫자맵 생성
                for(int i = 0; i < 10; i ++) {
                    for(int j = 0; j < 10; j ++) {
                        if(map[stage_num][i][j] == 0) {
                            double rand = Math.random();
                            map[stage_num][i][j] = (int)(rand * 14);
                        }
                    }
                }
                makeQuiz();
            }
            init_map_flag = false;

            // 현재값, 미션값 출력하기 위한 텍스트 paint
            Paint textPaint = new Paint();
            textPaint.setTextSize(75);
            textPaint.setColor(Color.WHITE);
            textPaint.setTypeface(Typeface.create((String)null, Typeface.BOLD));
            textPaint.setAntiAlias(true);
            textPaint.setStyle(Paint.Style.FILL);

            // try,catch 해줘야  NullPointerException 에러가 나지 않는다.
            try{

                // 상단 배너(?) 출력
                canvas.drawBitmap(image_array[15], null, new Rect(0, 0, (w_width * 9) + w_width, (w_height * 2) + w_height), null);
                canvas.drawText("미션 값 : " + stage_clear_num ,w_width - 30, w_height - 30, textPaint);
                canvas.drawText("현재 값 : " + displayResult ,w_width - 40, (w_height * 3) - 50, textPaint);


                // 캐릭터의 시작위치에 대한 값 저장
                if(calcul_arr[9] == -1 && map[stage_num][character_y][character_x] < 10) calcul_arr[9] = map[stage_num][character_y][character_x];

                // 9보다 크면 기호 10보다 작으면 숫자
                if((map[stage_num][character_y][character_x] > 9 && calcul_arr[9] < 10 && calcul_arr[9] > -1) ||
                        (map[stage_num][character_y][character_x] < 10 && calcul_arr[9] > 9 )) {
                    // 첫번째 칸이 비어있을때만
                    for(int i = 0; i < 9; i ++) calcul_arr[i] = calcul_arr[i + 1];
                    calcul_arr[9] = map[stage_num][character_y][character_x];
                } else if((map[stage_num][character_y][character_x] < 10 && calcul_arr[9] < 10 && calcul_arr[9] > -1) ||
                        (map[stage_num][character_y][character_x] > 9 && calcul_arr[9] > 9)) {
                    calcul_arr[9] = map[stage_num][character_y][character_x];
                }

                // 현재 배열에 저장된 값 출력(계산할 값)
                for(int i = 0; i < 10; i ++) {
                    if(calcul_arr[i] > -1) canvas.drawBitmap(image_array[calcul_arr[i]], null, new Rect(w_width * i, w_height, (w_width * i) + w_width, w_height * 2), null);
                }

                // 세로화면 +3 해주는 이유는 화면 상단에 계산화면(이미지) 띄우기 위하여
                for(int i = 0; i < 10; i ++) {
                    for(int j = 0; j < 10; j ++) {
                        if(map[stage_num][i][j] < 14) {
                            canvas.drawBitmap(image_array[map[stage_num][i][j]], null, new Rect(w_width * j, (w_height * (i + 3)), (w_width * j) + w_width, (w_height * (i + 3)) + w_height), null);
                        }
                    }
                }

                // 캐릭터 출력
                canvas.drawBitmap(image_array[14], null, new Rect(w_width * character_x, w_height * (character_y + 3), (w_width * character_x) + w_width, (w_height * (character_y + 3)) + w_height), null);
            } catch (Exception e) {
                // TODO: handle exception
            }
        }

        /**** 게임 클리어 확인 함수 ****/
        int game_clear() {
            int calcul = 0;
            int num = 0;
            //적어도 한번이상 계산되기 위하여
            if(calcul_arr[7] != -1 && calcul_arr[8] != -1 && calcul_arr[9] != -1 ) {
                //result_check(calcul, num, result, calcul_arr);
                for(int i = 0; i < 10; i ++) {
                    // -1(비워있는 상태)이 아니면
                    if(calcul_arr[i] != -1 ) {
                        if(calcul_arr[i] < 10) {
                            if(result == 0)	result = calcul_arr[i];
                            else num = calcul_arr[i];

                            if(calcul == 10) result = result + num;
                            else if(calcul == 11) result = result - num;
                            else if(calcul == 12) result = result * num;
                            else if(calcul == 13) result = (int)((double)result / num);
                        } else {
                            if(calcul_arr[i] == 10) calcul = 10;
                            else if(calcul_arr[i] == 11) calcul = 11;
                            else if(calcul_arr[i] == 12) calcul = 12;
                            else if(calcul_arr[i] == 13) calcul = 13;
                        }
                    }
                }
            }
            displayResult = result;

            if(result == stage_clear_num) {
                stage_clear_num = -1;
                result = 0;
                return 1;
            }
            result = 0;
            return 0;
        }

        /*
        // 고민 필요
        void result_check(int a_calcul, int a_num, int a_result,  int cArr[]) {
            for (int i = 0; i < 10; i++) {
                // -1(비워있는 상태)이 아니면
                if (cArr[i] != -1) {
                    if (cArr[i] < 10) {
                        if (a_result == 0) a_result = cArr[i];
                        else a_num = cArr[i];

                        if (a_calcul == 10) a_result = a_result + a_num;
                        else if (a_calcul == 11) a_result = a_result - a_num;
                        else if (a_calcul == 12) a_result = a_result * a_num;
                        else if (a_calcul == 13) a_result = (int) ((double) a_result / a_num);

                    } else {
                        if (cArr[i] == 10) a_calcul = 10;
                        else if (cArr[i] == 11) a_calcul = 11;
                        else if (cArr[i] == 12) a_calcul = 12;
                        else if (cArr[i] == 13) a_calcul = 13;
                    }
                }
            }
        }*/

        /********************************* 스레드 본체 ***********************************/
        public void run() {
            Canvas canvas = null;
            //서페이스 뷰에서 인텐트 사용하여 액티비티로 넘어가는 방법
            //Intent intent = new Intent(mContext.getApplicationContext(), MainActivity.class);

            bitmap_upload(w_width, w_height);
            // 캐릭터 폭, 높이
            cw = image_array[0].getWidth() / 2;
            ch = image_array[0].getHeight() / 2;

            while (isLoop) {
                canvas = mHolder.lockCanvas();
                try {
                    synchronized (mHolder) {
                        Draw_Game(canvas);
                        if(game_clear() == 1) handler.sendEmptyMessage(0);
                    } // sync
                } finally {
                    if (canvas != null) mHolder.unlockCanvasAndPost(canvas);
                } // try
            } // while
        } // run

        /******* 스레드 완전 정지 ********/
        public void StopThread() {
            isLoop = false;
            synchronized (this) {
                this.notify();                   // 스레드에 통지
            }
        } // surfaceDestroyed

        /******* 스레드 일시정지/ 재가동 *******/
        public void PauseNResume(boolean value) {
            isWait = value;
            //isLoop = value;
            synchronized (this) {
                this.notify();               // 스레드에 통지
            }
        }
    } // GameThread 끝


    /******************* Touch 관련 이벤트 *************************/
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            int x = (int) event.getX();           // 클릭한 위치 조사
            int y = (int) event.getY();

            synchronized (mHolder) {
                if(x > (width - width / 4)) {
                    if(character_x < 9 && map[stage_num][character_y][character_x + 1] != 51 ) character_x += 1;
                } else if(x < width / 4) {
                    if(character_x > 0 && map[stage_num][character_y][character_x - 1] != 51) character_x -= 1;
                } else if(y > (height - height / 4)) {
                    if(character_y < 9 && map[stage_num][character_y + 1][character_x] != 51) character_y += 1;
                } else if(y < (character_y + 4) * w_height) {
                    if(character_y > 0 && map[stage_num][character_y - 1][character_x] != 51 ) character_y -= 1;
                }
            }
        } // if
        return false;
    } // touch
}