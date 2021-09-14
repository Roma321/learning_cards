package com.example.learningcards.classes;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * @author fonclub <sfonclub@gmail.com>
 * @created 06.04.2017.
 */

public class SwipeTouchListener implements View.OnTouchListener {

    private static final String LOG_TAG = "SwipeTouchListener";
    private static int MIN_DISTANCE;
    private float downX;
    private float downY;

    public SwipeTouchListener(Activity _activity) {
        DisplayMetrics dm = _activity.getResources().getDisplayMetrics();
        MIN_DISTANCE = (int) (120.0f * dm.densityDpi / 160.0f + 0.5);
    }

    private void onRightToLeftSwipe() {
        Log.i(LOG_TAG, "Справа налево!");
    }

    private void onLeftToRightSwipe() {
        Log.i(LOG_TAG, "Слева направо!");


    }

    private void onTopToBottomSwipe() {
        Log.i(LOG_TAG, "Сверху вниз!");
    }

    private void onBottomToTopSwipe() {
        Log.i(LOG_TAG, "Снизу вверх!");
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouch(View v, MotionEvent event) {
        Log.e("I", "work");
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: {
                downX = event.getX();
                downY = event.getY();
                return true;
            }
            case MotionEvent.ACTION_UP: {
                float upX = event.getX();
                float upY = event.getY();

                float deltaX = downX - upX;
                float deltaY = downY - upY;

                // горизонтальный свайп
                if (Math.abs(deltaX) > MIN_DISTANCE) { // если дистанция не меньше минимальной
                    // слева направо
                    if (deltaX < 0) {
                        this.onLeftToRightSwipe();
                        return true;
                    }
                    //справа налево
                    if (deltaX > 0) {
                        this.onRightToLeftSwipe();
                        return true;
                    }
                }

                // вертикальный свайп
                if (Math.abs(deltaY) > MIN_DISTANCE) { //если дистанция не меньше минимальной
                    // сверху вниз
                    if (deltaY < 0) {
                        this.onTopToBottomSwipe();
                        return true;
                    }
                    // снизу вверх
                    if (deltaY > 0) {
                        this.onBottomToTopSwipe();
                        return true;
                    }
                }

                return false;
            }
        }
        return false;
    }
}