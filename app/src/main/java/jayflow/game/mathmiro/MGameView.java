package jayflow.game.mathmiro;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.os.Handler;
import android.os.Message;
import android.util.*;
import android.view.*;
import android.widget.TextView;

import java.util.MissingFormatArgumentException;

public class MGameView extends SurfaceView implements Callback {
	static GameThread mThread;                    // GameThread
	SurfaceHolder mHolder;                        // SurfaceHolder
	static Context mContext;                      // Context

	// 화면의 폭과 높이
	int width, height;
	int w_width, w_height;

	public int touch_flag = 0; // 터치시 1, 2, 3, 4(상,하,좌,우)

	// 계산 관련  배열및 카운터
	//public int calcul_cnt = 9;
	public int calcul_arr[] = new int[] {
			-1, -1, -1, -1, -1, -1, -1, -1, -1, -1
	};

	// 캐릭 터 좌표
	int character_x = 5;
	int character_y = 5;

	boolean init_map_flag = true;
	int result = 0; // 계산결과
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
					{0, 0, 12, 0, 0, 0, 0, 12, 0, 0},
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

	//-------------------------------------
	//  생성자
	//-------------------------------------
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

		bitmap_upload(w_width, w_height);

		// 캐릭터 폭, 높이
		cw = image_array[0].getWidth() / 2;
		ch = image_array[0].getHeight() / 2;

		setFocusable(true);     // View가 Focus받기
	}

	//-------------------------------------
	//        resource 업로드 함수
	//-------------------------------------
	void bitmap_upload(int w_width, int w_height) {
		int []res = new int[] {R.drawable.zero, R.drawable.one, R.drawable.two, R.drawable.tree, R.drawable.four, R.drawable.five,
								R.drawable.six, R.drawable.seven, R.drawable.eight, R.drawable.nine, R.drawable.plus, R.drawable.minus,
								R.drawable.mul,	R.drawable.divi, R.drawable.hunter, R.drawable.hunter, R.drawable.banner};

		for(int i = 0; i < 17; i ++) {
			image_array[i] = BitmapFactory.decodeResource(getResources(), res[i]);
		}

		// 화면 크기에 맞게 비트맵 크기 변경
		for(int i = 0; i < 16; i++) {
			image_array[i] = Bitmap.createScaledBitmap(image_array[i], w_width, w_height, true);
		}
		image_array[16] = Bitmap.createScaledBitmap(image_array[16], w_width * 10, w_height * 3, true);
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
				mThread.join();                            // 스레드가 현재 step을 끝낼 때 까지 대기
				done = false;
			} catch (InterruptedException e) {         // 인터럽트 신호가 오면?
				// 그 신호 무시 - 아무것도 않음
			}
		} // while
	} // surfaceDestroyed

	//  스레드 완전 정지
	public static void StopGame() { mThread.StopThread(); }
	//  스레드 일시 정지
	public static void PauseGame() {
		mThread.PauseNResume(true);
	}
	//  스레드 재기동
	public static void ResumeGame() {
		mThread.PauseNResume(false);
	}

	/****************** 게임 초기화 함수 ****************/
	public void RestartGame() {
		mThread.StopThread();  // 스레드 중지

		// 현재의 스레드를 비우고 다시 생성
		mThread = null;
		mThread = new GameThread(mHolder, mContext);
		mThread.start();

		character_x = 5;
		character_y = 5;
		for(int j = 0; j < 10; j ++) {
			calcul_arr[j] = -1;
		}
		result = 0;
		init_map_flag = true;
	}

	//-------------------------------------
	//  next stage
	//-------------------------------------
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
			for(int j = 0; j < 10; j ++) {
				calcul_arr[j] = -1;
			}
			stage_num ++;
			init_map_flag = true;
		}
	}

	/**************************** 핸들러 관련 *******************************************/
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

		/**** 문제를 만들어 내는 함수 ****/
		// 그냥 무작정 랜덤으로 문제를 내게 된다면, 맵에서 만들 수 없는 숫자들이 문제로 나온다.
		// 이런경우를 없애기 위하여 캐릭터의 위치로부터 랜덤으로 10번 이동하여 나온 숫자를 문제로 낸다.
		// 이렇게 할 경우 적어도 1가지의 경우의 수(정답을 만들수 있는 길)가 나오게 된다.
		public void makeQuiz() {
			int[] check_array = new int[] { -1, -1, -1, -1, -1, -1, -1, -1, -1, -1};
			int check_x, check_y; // = 의 좌표

			check_x = character_x;
			check_y = character_y;

			if(map[stage_num][check_y][check_x] < 10) check_array[9] =  map[stage_num][check_y][check_x];
			else check_array[9] =  0;

			int direction = 0; // 이동방향
			double rand_d = 0;

			for(int i = 0; i < 11; i ++) {
				rand_d = Math.random();
				direction = (int)(rand_d * 4); // 0 ~ 3

				if(direction == 0) { // 상
					check_y -= 1;
				} else if(direction == 1) { // 우
					check_x += 1;
				} else if(direction == 2) { // 하
					check_y += 1;
				} else if(direction == 3) { // 좌
					check_x -= 1;
				}

				// 9보다 크면 기호 10보다 작으면 숫자
				if((map[stage_num][check_y][check_x] > 9 && check_array[9] < 10 && check_array[9] > -1) ||
						(map[stage_num][check_y][check_x] < 10 && check_array[9] > 9 )) {

					for(int j = 0; j < 9; j ++) {
						check_array[j] = check_array[j + 1];
					}
					check_array[9] = map[stage_num][check_y][check_x];

				} else if((map[stage_num][check_y][check_x] < 10 && check_array[9] < 10 && check_array[9] > -1) ||
						(map[stage_num][check_y][check_x] > 9 && check_array[9] > 9)) {

					check_array[9] = map[stage_num][check_y][check_x];
				}
			}

			int c_result = 0;
			int c_num = 0;
			int c_cal = 0;
			for(int i = 0; i < 10; i ++) {
				// =이나 -1(비워있는 상태) 가 아니면
				if(check_array[i] != -1 ) {
					if(check_array[i] < 10) {
						if(c_result == 0)	c_result = check_array[i];
						else c_num = check_array[i];

						if(c_cal == 10) {
							c_result = c_result + c_num;
						} else if(c_cal == 11) {
							c_result = c_result - c_num;
						} else if(c_cal == 12) {
							c_result = c_result * c_num;
						} else if(c_cal == 13) {
							c_result = (int)((double)c_result / c_num);
						}
					} else {
						if(check_array[i] == 10) {
							c_cal = 10;
						} else if(check_array[i] == 11) {
							c_cal = 11;
						} else if(check_array[i] == 12) {
							c_cal = 12;
						} else if(check_array[i] == 13) {
							c_cal = 13;
						}
					}
				}
			}

			Log.v("MainActivity", "최종결과0 :  " + check_array[0]);
			Log.v("MainActivity", "최종결과 1:  " + check_array[1]);
			Log.v("MainActivity", "최종결과 2:  " + check_array[2]);
			Log.v("MainActivity", "최종결과 3:  " + check_array[3]);
			Log.v("MainActivity", "최종결과 4:  " + check_array[4]);
			Log.v("MainActivity", "최종결과 5:  " + check_array[5]);
			Log.v("MainActivity", "최종결과 6:  " + check_array[6]);
			Log.v("MainActivity", "최종결과 7:  " + check_array[7]);
			Log.v("MainActivity", "최종결과 8:  " + check_array[8]);
			Log.v("MainActivity", "최종결과 9:  " + check_array[9]);
			Log.v("MainActivity", "result  " + c_result);
			stage_clear_num = c_result;

			// 0일 경우 result와 초기값이 매치되서 버그가 생김(다음스테이지 눌렀을 경우 바로 그다음스테이 넘어가는 버그)
			if(stage_clear_num == 0) {
				rand_d = Math.random();
				direction = (int)(rand_d * 9); // 1 ~ 10
				stage_clear_num = direction + 1;
			}
			handler.sendEmptyMessage(1);
		}


		/**** 게임 스테이지 시작시 맵 그리는 함수 ****/
		public void Draw_Map(Canvas canvas) {
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
			// 처음에만 랜덤으로 숫자 및 기호 배열에 넣어주기 위해

			init_map_flag = false;

			// try,catch 해줘야  NullPointerException 에러가 나지 않는다.
			try{
				// 계산 화면 출력
				canvas.drawBitmap(image_array[16], null, new Rect(0, 0, (w_width * 9) + w_width, (w_height * 2) + w_height), null);
				if(calcul_arr[9] == -1 && map[stage_num][character_y][character_x] < 10) {
					calcul_arr[9] = map[stage_num][character_y][character_x];
				}

				// 9보다 크면 기호 10보다 작으면 숫자

				if((map[stage_num][character_y][character_x] > 9 && calcul_arr[9] < 10 && calcul_arr[9] > -1) ||
						(map[stage_num][character_y][character_x] < 10 && calcul_arr[9] > 9 )) {

					// 첫번째 칸이 비어있을때만
					//if(calcul_arr[0] == -1) {
					for(int i = 0; i < 9; i ++) {
						calcul_arr[i] = calcul_arr[i + 1];
					}
					calcul_arr[9] = map[stage_num][character_y][character_x];
					//}
				} else if((map[stage_num][character_y][character_x] < 10 && calcul_arr[9] < 10 && calcul_arr[9] > -1) ||
						(map[stage_num][character_y][character_x] > 9 && calcul_arr[9] > 9)) {
					calcul_arr[9] = map[stage_num][character_y][character_x];
				}

				for(int i = 0; i < 10; i ++) {
					if(calcul_arr[i] > -1) {
						canvas.drawBitmap(image_array[calcul_arr[i]], null, new Rect(w_width * i, w_height, (w_width * i) + w_width, w_height * 2), null);
					}
				}


				// 세로화면 +3 해주는 이유는 화면 상단에 계산화면(이미지) 띄우기 위하여
				for(int i = 0; i < 10; i ++) {
					for(int j = 0; j < 10; j ++) {
						if(map[stage_num][i][j] < 50) {
							// 랜덤으로 숫자와 기호를 입력해준다.
							canvas.drawBitmap(image_array[map[stage_num][i][j]], null, new Rect(w_width * j, (w_height * (i + 3)), (w_width * j) + w_width, (w_height * (i + 3)) + w_height), null);
						} else if(map[stage_num][i][j] == 51){
							canvas.drawBitmap(image_array[15], null, new Rect(w_width * j, w_height * (i + 3), (w_width * j) + w_width, (w_height * (i + 3)) + w_height), null);
						} else if(map[stage_num][i][j] == 52){ // 결과
							canvas.drawBitmap(image_array[17], null, new Rect(w_width * j, w_height * (i + 3), (w_width * j) + w_width, (w_height * (i + 3)) + w_height), null);
						} else if(map[stage_num][i][j] == 5){ // 결과
							canvas.drawBitmap(image_array[5], null, new Rect(w_width * j, w_height * (i + 3), (w_width * j) + w_width, (w_height * (i + 3)) + w_height), null);
						}
					}
				}
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
				for(int i = 0; i < 10; i ++) {
					// =이나 -1(비워있는 상태) 가 아니면
					if(calcul_arr[i] != 17 && calcul_arr[i] != -1 ) {
						if(calcul_arr[i] < 10) {
							if(result == 0)	result = calcul_arr[i];
							else num = calcul_arr[i];

							if(calcul == 10) {
								result = result + num;
							} else if(calcul == 11) {
								result = result - num;
							} else if(calcul == 12) {
								result = result * num;
							} else if(calcul == 13) {
								result = (int)((double)result / num);
							}
						} else {
							if(calcul_arr[i] == 10) {
								calcul = 10;
							} else if(calcul_arr[i] == 11) {
								calcul = 11;
							} else if(calcul_arr[i] == 12) {
								calcul = 12;
							} else if(calcul_arr[i] == 13) {
								calcul = 13;
							}
						}
					}
				}
			}
			if(result == stage_clear_num) {
				stage_clear_num = -1;
				result = 0;
				return 1;
			}
			result = 0;
			return 0;
		}

		/********************************* 스레드 본체 ***********************************/
		public void run() {
			Canvas canvas = null;
			//서페이스 뷰에서 인텐트 사용하여 액티비티로 넘어간느 방법
			//Intent intent = new Intent(mContext.getApplicationContext(), MainActivity.class);

			while (isLoop) {
				canvas = mHolder.lockCanvas();

				try {
					synchronized (mHolder) {
						Draw_Map(canvas);
						if(game_clear() == 1) {
							handler.sendEmptyMessage(0);
						}

					} // sync
				} finally {
					if (canvas != null)
						mHolder.unlockCanvasAndPost(canvas);
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
			//Move_Character();

			synchronized (mHolder) {
				if(x > (width - width / 4)) {
					if(character_x < 9) {
						if(map[stage_num][character_y][character_x + 1] != 51 ) character_x += 1;
					}
				} else if(x < width / 4) {
					if(character_x > 0) {
						if(map[stage_num][character_y][character_x - 1] != 51) character_x -= 1;
					}
				} else if(y > (height - height / 4)) {
					if(character_y < 9) {
						if(map[stage_num][character_y + 1][character_x] != 51) character_y += 1;
					}
				} else if(y < (character_y + 4) * w_height) {
					if(character_y > 0 ) {
						if(map[stage_num][character_y - 1][character_x] != 51 ) character_y -= 1;
					}
				}
			}
		} // if
		return false;
	} // touch
}
