package com.mobile.proisa.pruebas.Actividades;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;

public class ZoomListener implements View.OnTouchListener, GestureDetector.OnDoubleTapListener {
    private static final float MAX_SCALE = 5.0f;
    private static final float MIN_SCALE = 1.0f;
    private boolean zoom;
    private float oldSpacing = 1.0f;
    private float positionX, positionY;
    private float initialX, initialY;
    private View mView;

    private GestureDetector gestureDetector;

    int numberOfTaps = 0;
    long lastTapTimeMs = 0;
    long touchDownMs = 0;


    public ZoomListener() {
        initialX = 0.0f;
        initialY = 0.0f;
    }

    public ZoomListener(GestureDetector gestureDetector) {
        this();
        this.gestureDetector = gestureDetector;
        this.gestureDetector.setOnDoubleTapListener(this);
    }

    @Override
    public boolean onTouch(View view, MotionEvent motionEvent) {
        float scale;
        mView = view;

        switch (motionEvent.getAction() & motionEvent.getActionMasked()){
            case MotionEvent.ACTION_UP:
                setZoom(false);
                break;

            case MotionEvent.ACTION_DOWN:
                touchDownMs = System.currentTimeMillis();
                initialX = motionEvent.getX();
                initialY = motionEvent.getY();
                setZoom(false);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                oldSpacing = spacing(motionEvent);
                setZoom(true);

                Log.d("zoomListener", String.format("oldSpacing is: %f",oldSpacing));
                break;

            case MotionEvent.ACTION_MOVE:

                positionX = view.getX();
                positionY = view.getY();

                float distanceX = motionEvent.getX() - initialX;
                float distanceY = motionEvent.getY() - initialY;

                if(isZoom()){
                    float spacing = spacing(motionEvent);
                    scale = spacing / oldSpacing * view.getScaleX();

                   if(scale > MIN_SCALE && scale < MAX_SCALE){
                        view.setScaleX(scale);
                        view.setScaleY(scale);
                   }else if (scale > MAX_SCALE){
                       view.setScaleX(MAX_SCALE);
                       view.setScaleY(MAX_SCALE);
                   }else{
                       view.setScaleX(MIN_SCALE);
                       view.setScaleY(MIN_SCALE);
                   }

                    Log.d("zoomListener", String.format("scale: %f",scale));
                //}else if(motionEvent.getPointerCount() == 1){

                    if(view.getScaleX() > MIN_SCALE){
                        view.setX(positionX + distanceX);
                        view.setY(positionY + distanceY);
                    }else{
                        view.setX(0.0f);
                        view.setY(0.0f);
                    }

                    Log.d("zoomListener", String.format("x: %f, y: %f", positionX, positionY));
                }else if(view.getScaleX() > MIN_SCALE){
                    view.setX(positionX + distanceX);
                    view.setY(positionY + distanceY);
                }
                break;

            default:
                setZoom(false);
        }

        view.invalidate();

        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(motionEvent);
        }

        return true;
    }

    public boolean isZoom() {
        return zoom;
    }

    public void setZoom(boolean zoom) {
        this.zoom = zoom;
    }

    private float spacing(MotionEvent motionEvent){
        float x = motionEvent.getX(0) - motionEvent.getX(1);
        float y = motionEvent.getY(0) - motionEvent.getY(1);

        return (float) Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        Log.d("doubleTap", "true");

        mView.setScaleX(MIN_SCALE);
        mView.setScaleY(MIN_SCALE);

        mView.setX(0.0f);
        mView.setY(0.0f);
        return true;
    }

    @Override
    public boolean onDoubleTapEvent(MotionEvent motionEvent) {
        return false;
    }


    private static class MyGestureDetector extends GestureDetector.SimpleOnGestureListener{

        @Override
        public boolean onDoubleTap(MotionEvent e) {
            Log.d("doubleTap", "true");
            return true;
        }
    }

}
