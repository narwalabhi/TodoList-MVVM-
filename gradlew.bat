package com.example.canvas;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;

import androidx.annotation.Nullable;

public class CustomView extends View {

    private Bitmap mImage;
    private float imageX, imageY;
    private String TAG = "CustomView";

    public CustomView(Context context) {
        super(context);
        init(null);
    }

    public CustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    private void init(AttributeSet set){
        mImage = BitmapFactory.decodeResource(getResources(), R.drawable.car);
        getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                getViewTreeObserver().removeOnGlobalLayoutListener(this);
                mImage = getResizedBitmap(mImage,400, 500);
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        imageX = (getWidth() - mImage.getWidth()) >> 1;
        imageY = (getHeight() - mImage.getHeight()) >> 1;
        canvas.drawBitmap(mImage, imageX , imageY, null);
    }

    private Bitmap getResizedBitmap(Bitmap bitmap, float height, float width){
        Matrix matrix = new Matrix();
        RectF src = new RectF(0, 0, bitmap.getHeight(), bitmap.getWidth());
        RectF dest = new RectF(0, 0, width, height);
        matrix.setRectToRect(src, dest, Matrix.ScaleToFit.CENTER);
        return Bitmap.createBitmap(bitmap, 0 , 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: " + getX());
                imageX = getX()+10;
                invalidate();
                return true;