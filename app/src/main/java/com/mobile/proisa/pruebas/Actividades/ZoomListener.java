package com.mobile.proisa.pruebas.Actividades;

import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;

public class ZoomListener implements View.OnTouchListener, GestureDetector.OnDoubleTapListener {
    private static final float MAX_SCALE = 4.0f;
    private static final float MIN_SCALE = 1.0f;
    private static final float MAX_POSITION_POSITIVE = 150.0f;
    private static final float MAX_POSITION_NEGATIVE = -150.0f;
    private static final long DELAY_FOR_RESET = 500; //half minute
    private float oldSpacing = 1.0f;
    private float positionX, positionY;
    private float initialX, initialY;
    private float lastDistanceX, lastDistanceY;
    private boolean zoom;
    private View mView;
    private GestureDetector gestureDetector;


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
                /*positionX = view.getX();
                positionY = view.getY();


                if(isOverLimit(positionX) && isOverLimit(positionY)){
                    view.animate().x(positionX + lastDistanceX).y(positionY + lastDistanceY)
                            .setInterpolator(new LinearInterpolator()).setDuration(0);
                }else if(isOverLimit(positionX)){
                    view.animate().x(lastDistanceX).setInterpolator(new LinearInterpolator());
                }else if(isOverLimit(positionY)){
                    view.animate().x( lastDistanceX).setInterpolator(new LinearInterpolator());
                }else{
                    view.animate().x(positionX + lastDistanceX).y(positionY + lastDistanceY)
                            .setInterpolator(new LinearInterpolator()).setDuration(0);
                }*/

                //Log.d("zoomListener", String.format("x: %f(%s), y: %f(%s)", positionX, isOverLimit(positionX), positionY, isOverLimit(positionY)));
                setZoom(false);
                break;

            case MotionEvent.ACTION_DOWN:
                initialX = motionEvent.getX();
                initialY = motionEvent.getY();
                setZoom(false);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                oldSpacing = spacing(motionEvent);
                setZoom(true);

               // Log.d("zoomListener", String.format("oldSpacing is: %f",oldSpacing));
                break;

            case MotionEvent.ACTION_MOVE:
                positionX = view.getX();
                positionY = view.getY();


                float distanceX = motionEvent.getX() - initialX;
                float distanceY = motionEvent.getY() - initialY;


                //distanceX = isOverLimit(distanceX) ? lastDistanceX : distanceX;
                //distanceY = isOverLimit(distanceY) ? lastDistanceY : distanceY;

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


                    if(view.getScaleX() > MIN_SCALE){
                        //view.setX(positionX + distanceX);
                        //view.setY(positionY + distanceY);
                        view.animate().x(positionX + distanceX).y(positionY + distanceY)
                                .setInterpolator(new LinearInterpolator()).setDuration(0);
                    }else{
                        //view.setX(0.0f);
                        //view.setY(0.0f);

                        view.animate().x(0.0f).y(0.0f).setInterpolator(new LinearInterpolator()).setDuration(0);
                    }


                }else if(view.getScaleX() > MIN_SCALE){
                    //view.setX(positionX + distanceX);
                    //view.setY(positionY + distanceY);
                    view.animate().x(positionX + distanceX).y(positionY + distanceY)
                            .setInterpolator(new LinearInterpolator()).setDuration(0);
                }


                lastDistanceX = distanceX;
                lastDistanceY = distanceY;
                //Log.d("zoomListener", String.format("x: %f, y: %f", positionX, positionY));
                //Log.d("zoomListener", String.format("dx: %f, dy: %f", distanceX, distanceY));
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

    private float limitPosition(float value){
       if(value > MAX_POSITION_POSITIVE){
            value = MAX_POSITION_POSITIVE;
        }else if(value < MAX_POSITION_NEGATIVE){
            value = MAX_POSITION_POSITIVE;
        }
        return value;
    }


    private boolean isOverLimit(float value){
        if(value > MAX_POSITION_POSITIVE || value < MAX_POSITION_NEGATIVE){
            return true;
        }
        return false;
    }

    @Override
    public boolean onSingleTapConfirmed(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onDoubleTap(MotionEvent motionEvent) {
        mView.animate()
                .scaleX(MIN_SCALE).scaleY(MIN_SCALE)
                .x(0.0f).y(0.0f)
                .setInterpolator(new LinearInterpolator())
                .setDuration(DELAY_FOR_RESET);
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
