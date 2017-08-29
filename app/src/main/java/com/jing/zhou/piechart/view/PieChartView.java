package com.jing.zhou.piechart.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import com.jing.zhou.piechart.R;
import com.jing.zhou.piechart.bean.PieData;

import java.util.ArrayList;

/**
 * Created by hbhd on 2017-08-21.
 * 饼状图的view
 */

public class PieChartView extends View {

    // 默认的颜色
    private int[] mColors = {0xFFCCFF00, 0xFF6495ED, 0xFFE32636, 0xFF800000, 0xFF808000, 0xFFFF8C69, 0xFF808080,
            0xFFE6B800, 0xFF7CFC00};
    // 饼状图从哪里开始绘制
    private float mStartAngle = 0;
    // 需要的数据
    private ArrayList<PieData> mData;
    // 宽高
    private int mWidth, mHeight;
    // 画笔
    private Paint mPaint = new Paint();
    private float value;
    private boolean hasDividingLine;//是否需要分割线
    private boolean showSign;//是否需要标识
    private int dividingLineColor;//分割线颜色
    private float dividingLineAngle;//分割线的角度

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig(context, attrs);
    }

    private void initConfig(Context context, AttributeSet attrs) {
        //设置绘画模式
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.PieChartView);
        hasDividingLine = typedArray.getBoolean(R.styleable.PieChartView_dividing_line, false);
        showSign = typedArray.getBoolean(R.styleable.PieChartView_show_sign, true);
        dividingLineColor = typedArray.getColor(R.styleable.PieChartView_dividing_line_color, Color.WHITE);
        dividingLineAngle = typedArray.getDimension(R.styleable.PieChartView_dividing_line_angle, 1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = w;
        mHeight = h;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mData == null) {
            return;
        }
        float currentStartAngle = mStartAngle;                    // 当前起始角度
        canvas.translate(mWidth / 2, mHeight / 2);                // 将画布坐标原点移动到中心位置
        float r = (float) (Math.min(mWidth, mHeight) / 2 * 0.8);  // 饼状图半径
        RectF rect = new RectF(-r, -r, r, r);                     // 饼状图绘制区域

        for (int i = 0; i < mData.size(); i++) {
            PieData pie = mData.get(i);
            mPaint.setColor(pie.getColor());
            canvas.drawArc(rect, currentStartAngle, pie.getAngle() - 1, true, mPaint);
            drawLineAndText(r, currentStartAngle, pie.getAngle(), mPaint, canvas,pie.getValue(),pie.getName());
            currentStartAngle += pie.getAngle();

        }

    }

    /**
     * 设置数据的方法
     *
     * @param data
     */
    public void setmData(ArrayList<PieData> data) {
        this.mData = data;
        initData(mData);
        invalidate();
    }

    /**
     * 设置颜色的方法
     *
     * @param mColors
     */
    public void setmColors(int[] mColors) {
        this.mColors = mColors;
        invalidate();
    }


    /**
     * 设置从哪儿开始绘画
     *
     * @param startAngle
     */
    public void setmStartAngle(int startAngle) {
        this.mStartAngle = startAngle;
        invalidate();
    }

    /**
     * 初始化数据的方法
     *
     * @param data
     */
    private void initData(ArrayList<PieData> data) {
        if (data == null || data.size() == 0) {
            return;
        }
        float sumValue = 0;
        for (int i = 0; i < data.size(); i++) {
            PieData pieData = data.get(i);
            sumValue += pieData.getValue();
            int color = i % mColors.length;
            pieData.setColor(mColors[color]);
        }

        float sumAngle = 0;
        for (int i = 0; i < data.size(); i++) {
            PieData pieData = data.get(i);
            float percentage = pieData.getValue() / sumValue;
            pieData.setPercentage(percentage);
            pieData.setAngle(percentage * 360);
            sumAngle += percentage * 360;
        }

    }

    /**
     * 划指示线还有标记百分比和名字等
     */
    private void drawLineAndText(float radius, float stopAngle, float angle, Paint paint, Canvas canvas,float value,String name) {
        //确定直线开始的坐标
        float startX = (float) (radius * (Math.cos(Math.toRadians(stopAngle + angle / 2))));
        float startY = (float) (radius * (Math.sin(Math.toRadians(stopAngle + angle / 2))));
        float stopX = (float) ((radius + 50) * (Math.cos(Math.toRadians(stopAngle + angle / 2))));
        float stopY = (float) ((radius + 50) * (Math.sin(Math.toRadians(stopAngle + angle / 2))));
        paint.setStrokeWidth(5);
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        paint.setTextSize(50);
        if (stopAngle+angle/2>90){
            canvas.drawText(name,stopX-50,stopY-50,paint);
        }
        canvas.drawText(name, stopX, stopY, paint);

    }

}
