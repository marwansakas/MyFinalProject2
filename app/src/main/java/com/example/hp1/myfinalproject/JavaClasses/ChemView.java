package com.example.hp1.myfinalproject.JavaClasses;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.hp1.myfinalproject.R;

public class ChemView extends View
{
    Rect rect;
    String[] str;
    int num;

    public int getNum() {
        return num;
    }

    public ChemView(Context context, int num) {
        super(context);
        this.num=num;
    }

    public ChemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        Paint boxColor=new Paint();
        String[] colors=getResources().getStringArray(R.array.element_colors);
        int color=Color.parseColor(colors[num]);
        boxColor.setColor(color);
        canvas.drawPaint(boxColor);
        Paint paintSy = new Paint(),paintN=new Paint(),paintRect=new Paint();
        str=getResources().getStringArray(R.array.element_symbol);
        rect=new Rect(0,0,canvas.getWidth(),canvas.getHeight());
        paintRect.setColor(Color.BLACK);
        paintRect.setStyle(Paint.Style.STROKE);
        canvas.drawRect(rect,paintRect);
        paintSy.setColor(Color.BLACK);
        paintSy.setTextSize(80);
        int xPos = canvas.getWidth()/2 -52;
        int yPos = canvas.getHeight() / 2 +30;
        canvas.drawText(str[num], xPos, yPos, paintSy);
        paintN.setColor(Color.BLACK);
        int fontSize = 40;
        paintN.setTextSize(fontSize);
        paintN.setTextAlign(Paint.Align.LEFT);
        int temp=num+1;
        canvas.drawText(""+temp, 0, 0+paintN.getTextSize(), paintN);
    }
}
