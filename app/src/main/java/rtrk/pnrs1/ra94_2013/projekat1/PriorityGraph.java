package rtrk.pnrs1.ra94_2013.projekat1;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.View;

public class PriorityGraph extends View{
    private RectF rect = new RectF();
    private RectF rect1 = new RectF();
    private Paint paint = new Paint();
    private int prior = 0;
    private int perc = 0;
    private float percToDraw = 0;
    private boolean toDraw = true;

    public PriorityGraph(Context context, AttributeSet attrs){
        super(context, attrs);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
    }

    public void setPercToDraw(float perc){                                //  Sets the max value to be drawn.
        this.percToDraw = perc;
    }

    public void setPriority(int prior) {                                //  Sets the 'priority' of graph. Used to set graph colors and graph title to corresponding ones.
        this.prior = prior;
    }

    public int getCurrentPerc()                                         //  Return the value of field 'perc'. This value represents the current percentage of circle that is drawn.
    {                                                                   //  Used to animate counting from '0' to max value.
        return perc;
    }

    public void refreshPerc()                                           //  Updates the percentage of circle that is drawn. Used to animate 'filling' the circle from '0' to max value.
    {                                                                   //  When the percentage reaches max value toDraw flag is set to false to stop the animation.
        if(this.percToDraw != 0) {
            this.perc++;

            if (this.perc == percToDraw)
                toDraw = false;

            invalidate();
        }                                                               //  Invalidates the whole view. At some point in the future onDraw method will be called which will set new view. Necessary for
    }                                                                   //  creating an animated view because it forces onDraw method to be called multiple times, each time with different view set.

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        rect.set(0,0, getWidth(), getHeight());                                             //  Used to draw animated part of circle.
        rect1.set(getWidth()/20, getHeight()/20, getWidth()*19/20, getHeight()*19/20);      //  Used to draw inner (not animated) part of circle .

        switch (prior){
            case 1:
                paint.setColor(ContextCompat.getColor(getContext(), R.color.greenButtonE));
                canvas.drawArc(rect, -180, 3.6f * perc, true, paint);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.greenButtonD));
                canvas.drawArc(rect, 3.6f * perc - 180, 360 - 3.6f * perc, true, paint);
                canvas.drawArc(rect1,0, 360, true, paint);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.graphTitle));
                paint.setTextSize(40);
                canvas.drawText("Nizak prioritet", getWidth()/2 - paint.measureText("Nizak prioritet")/2, getHeight()*3/4, paint);
                break;
            case 2:
                paint.setColor(ContextCompat.getColor(getContext(), R.color.yellowButtonE));
                canvas.drawArc(rect, -180, 3.6f * perc, true, paint);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.yellowButtonD));
                canvas.drawArc(rect, 3.6f * perc - 180, 360 - 3.6f * perc, true, paint);
                canvas.drawArc(rect1,0, 360, true, paint);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.graphTitle));
                paint.setTextSize(40);
                canvas.drawText("Srednji prioritet", getWidth()/2 - paint.measureText("Srednji prioritet")/2, getHeight()*3/4, paint);
                break;
            case 3:
                paint.setColor(ContextCompat.getColor(getContext(), R.color.redButtonE));
                canvas.drawArc(rect, -180, 3.6f * perc, true, paint);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.redButtonD));
                canvas.drawArc(rect, 3.6f * perc - 180, 360 - 3.6f * perc, true, paint);
                canvas.drawArc(rect1,0, 360, true, paint);
                paint.setColor(ContextCompat.getColor(getContext(), R.color.graphTitle));
                paint.setTextSize(40);
                canvas.drawText("Visok prioritet", getWidth()/2 - paint.measureText("Visok prioritet")/2, getHeight()*3/4, paint);
        }

        paint.setColor(ContextCompat.getColor(getContext(), R.color.graphTitle));

        paint.setTextSize(80);
        canvas.drawText(String.valueOf(getCurrentPerc()) + "%",getWidth()/2 - 75,getHeight()/3 + 50,paint);

        if (toDraw) {
            refreshPerc();          //  Used to increase the percentage of circle to be drawn.
        }
    }
}
