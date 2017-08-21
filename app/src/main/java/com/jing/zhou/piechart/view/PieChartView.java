package com.jing.zhou.piechart.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

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

    public PieChartView(Context context) {
        this(context, null);
    }

    public PieChartView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public PieChartView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //设置绘画模式
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
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
            canvas.drawArc(rect, currentStartAngle, pie.getAngle(), true, mPaint);
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

}
