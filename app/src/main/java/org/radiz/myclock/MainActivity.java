package org.radiz.myclock;

import android.app.Activity;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends Activity {
    @BindView(R.id.hour1) ImageView hour1;
    @BindView(R.id.hour2) ImageView hour2;
    @BindView(R.id.min1) ImageView min1;
    @BindView(R.id.min2) ImageView min2;
    @BindView(R.id.colon) ImageView colon;

    TypedArray typedArray;

    SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    String time;
    int isColon = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // 상태 바 없애기
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        // 어플리케이션 화면 꺼짐 방지
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        typedArray = getResources().obtainTypedArray(R.array.numbers);

        runTime();

    }

    public void drawTime() {

        time = sdf.format(new Date(System.currentTimeMillis()));

//        Glide.with(getApplicationContext()).load(R.drawable.seven).into(hour1);

        hour1.setImageResource(typedArray.getResourceId(Integer.parseInt(time.substring(0, 1)), -1));
        hour2.setImageResource(typedArray.getResourceId(Integer.parseInt(time.substring(1, 2)), -1));
        min1.setImageResource(typedArray.getResourceId(Integer.parseInt(time.substring(3, 4)), -1));
        min2.setImageResource(typedArray.getResourceId(Integer.parseInt(time.substring(4, 5)), -1));

        if(isColon == 0) {
            colon.setImageDrawable(getResources().getDrawable(R.drawable.colon));
            isColon = 1;
        } else {
            colon.setImageDrawable(getResources().getDrawable(R.drawable.blank));
            isColon = 0;
        }

    }

    public void runTime() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        drawTime();
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        System.out.println(e);
                    }
                }
            }
        });
        thread.start();
    }
}