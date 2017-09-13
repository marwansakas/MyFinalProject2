package com.example.hp1.myfinalproject;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.EmbossMaskFilter;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.lang.*;
import java.util.ArrayList;


public class PaintView extends View {

    public static int BRUSH_SIZE =10;//the brush size
    public static final int DEFAULT_COLOR= Color.BLACK;//the color of the brush
    public static final int DEFAULT_BG_COLOR=Color.WHITE;//the color of the background that we are drawing on it
    private static final float TOUCH_TOLERANCE=1;//the touch tolarence
    private float mx,my;//the x and y that are at the start of the touch
    private Path mpath;//the paths that we create while drawing
    private Paint mpaint;//the paint we are using
    private ArrayList<FingerPath> paths=new ArrayList<>();//an Array list of fingerpaths
    private int current_color;//our current color
    private int Background_color=DEFAULT_BG_COLOR;//our background color
    private int stroke_width;//our stroke width
    Bitmap mBitmap;//the bitmap we well be using

    private Canvas mCanvas;//the canvas we will be drawing on



    private Paint mBitmapPaint=new Paint(Paint.DITHER_FLAG);//to set the paint that will be used to paint on the bitmap


    public PaintView(Context context) {
        this(context,null);
    }//the first constructor

    public PaintView(Context context, @Nullable AttributeSet attrs) {//the paintview constractor that sets mpaint
        super(context, attrs);
        mpaint=new Paint();//initialize mpaint
        mpaint.setAntiAlias(true);//to set AntiAlias true to smooth the edges
        mpaint.setDither(true);//to set dither as true to simplify for colors that are higher precision for the device
        mpaint.setColor(DEFAULT_COLOR);//setting the color
        mpaint.setStyle(Paint.Style.STROKE);//setting the style
        mpaint.setStrokeJoin(Paint.Join.ROUND);//setting the stroke join
        mpaint.setStrokeCap(Paint.Cap.ROUND);//setting the stroke cap
        mpaint.setXfermode(null);
        mpaint.setAlpha(0xff);
    }

    public void init(DisplayMetrics metrics){
        int height=metrics.heightPixels;//to get the heights height pixels
        int width=metrics.widthPixels;//to get the heights width pixels
        mBitmap= Bitmap.createBitmap(width,height,Bitmap.Config.ARGB_8888);//to createe bitmap
        mCanvas=new Canvas(mBitmap);//to set canvas drawing on mbitmap

        current_color=DEFAULT_COLOR;//to set the current_color as the DEFAULT_COLOR
        stroke_width=BRUSH_SIZE;//to set the stroke_width as the BRUSH_SIZE
    }

    public void clear(){//an action to clear th paths on the canvas
        Background_color=DEFAULT_BG_COLOR;//to set Background_color back to DEFAULT_BG_COLOR
        paths.clear();//to clear paths
        invalidate();//to check the canvas again forever
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();//to save the matrix stack
        mCanvas.drawColor(Background_color);//the color on witch to draw on canvas
        for(FingerPath fp : paths)
        {
            mpaint.setColor(fp.color);
            mpaint.setStrokeWidth(fp.strokewidth);
            mpaint.setMaskFilter(null);
            mCanvas.drawPath(fp.path,mpaint);

        }
        canvas.drawBitmap(mBitmap,0,0,mBitmapPaint);//draw the bitmap from the beginning of the canvas
        canvas.restore();//to balance the save action
    }

    private void touchStart(float x,float y){
        mpath=new Path();//to make a new paint
        FingerPath fp=new FingerPath(current_color,stroke_width,mpath);//to make a new FingerPath
        paths.add(fp);//to add fp to the array
        mpath.reset();//to clear the lines of mpath
        mpath.moveTo(x,y);//to go to th position of x and y
        mx=x;// to set our initial mx equal to x
        my=y;// to set our initial my equal to y

    }

    private void touchmove(float x,float y){
        float dx= java.lang.Math.abs(x-mx);//the distance from mx to x
        float dy= java.lang.Math.abs(y-my);//the distance from my to y

        if(dx>=TOUCH_TOLERANCE||dy>=TOUCH_TOLERANCE) {//to check if both the x and y are bigger than or equal to the TOUCH_TOLERANCE
            mpath.quadTo(mx, my, (x + mx) / 2, (y + my) / 2);//to set the where the paths are moving to
            mx=x;//to change the mx to the current x
            my=y;//to change the my to the current y

        }
    }

    private void touchUp(){
        mpath.lineTo(mx,my);
    }//add a line from last point to mx and my

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x=event.getX();//get the x of now
        float y=event.getY();//get the y of now


        switch (event.getAction()){//to see where the users finger is going
            case MotionEvent.ACTION_DOWN://if down
                touchStart(x,y);//do touch on start
                invalidate();//repeat
                break;
            case MotionEvent.ACTION_MOVE:// if left or right
                touchmove(x,y);//start touchmove
                invalidate();//repeat
                break;
            case MotionEvent.ACTION_UP://if going up
                touchUp();//do touchup
                invalidate();//repeat
                break;
        }

        return true;
    }
}
