package com.jing.zhou.piechart.bean;

/**
 * Created by hbhd on 2017-08-21.
 * 饼状图数据的bean
 */

public class PieData {

    private String name; //数据的名称
    private float value; //数据的值

/*------------------我是华丽的分割线------------------*/

    private float percentage; //每个数据占总数的百分比
    private int color = 0;      // 数据在饼状图的颜色
    private float angle = 0;    //数据在图中所占的角度

    public PieData(String name, float value) {
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = value;
    }

    public float getPercentage() {
        return percentage;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }
}
