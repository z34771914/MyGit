package com.zhou.shudu01;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by Administrator on 14-4-20.
 */
public class MyView extends View{

    private float width;     //单元格宽度
    private float height;    //单元格高度

    private int selectedX;
    private int selectedY;

    private Game game=new Game();
    public MyView(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {

        Paint BackgroundPaint=new Paint();
        BackgroundPaint.setColor(getResources().getColor(R.color.shudu_background));
        canvas.drawRect(0,0,getWidth(),getHeight(),BackgroundPaint);

        Paint DarkPaint= new Paint();
        DarkPaint.setColor(getResources().getColor(R.color.shudu_dark));

        Paint HilitePaint= new Paint();
        HilitePaint.setColor(getResources().getColor(R.color.shudu_hilite));

        Paint LightPaint= new Paint();
        LightPaint.setColor(getResources().getColor(R.color.shudu_light));

        for (int i=0; i<9; i++){
            //横线
            canvas.drawLine(0,i*height,getWidth(),i*height,LightPaint);
            canvas.drawLine(0,i*height+1,getWidth(),i*height+1,HilitePaint);
            //竖线
            canvas.drawLine(i*width,0,i*width,getHeight(),LightPaint);
            canvas.drawLine(i*width+1,0,i*width+1,getHeight(),HilitePaint);
        }
        for (int i = 0; i < 9; i++) {
            if(i%3 !=0){continue;}
            canvas.drawLine(0,i*height,getWidth(),i*height,DarkPaint);
            canvas.drawLine(0,i*height+1,getWidth(),i*height+1,HilitePaint);
            canvas.drawLine(i*width,0,i*width,getHeight(),DarkPaint);
            canvas.drawLine(i*width+1,0,i*width+1,getHeight(),HilitePaint);
        }

        Paint numberPaint = new Paint();
        numberPaint.setColor(Color.BLACK);
        numberPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        numberPaint.setTextSize(height * 0.75f);
        numberPaint.setTextAlign(Paint.Align.CENTER);

        Paint.FontMetrics fm=numberPaint.getFontMetrics();
        float x=width/2;
        float y=height/2 - (fm.ascent + fm.descent)/2;
        for (int i=0; i<9; i++) {
            for (int j = 0; j < 9; j++) {
                canvas.drawText(game.getTileString(i, j), i * width + x, (j * height) + y, numberPaint);
            }
        }
        super.onDraw(canvas);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        this.height=h/9f;
        this.width=w/9f;
        super.onSizeChanged(w, h, oldw, oldh);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction()!=MotionEvent.ACTION_DOWN) {
            return super.onTouchEvent(event);
        }

        selectedX=(int)(event.getX()/width);
        selectedY=(int)(event.getY()/height);

        int used[]=game.getUsedTitlesByCoor(selectedX,selectedY);
        KeyDialog keyDialog=new KeyDialog(getContext(),used,this);
        keyDialog.show();

        return true;
    }

    public void setSelectedTile(int tile){
        if(game.setTileIfValid(selectedX,selectedY,tile)){
            invalidate();
        }
    }
}
