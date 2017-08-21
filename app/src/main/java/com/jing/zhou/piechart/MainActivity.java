package com.jing.zhou.piechart;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.jing.zhou.piechart.bean.PieData;
import com.jing.zhou.piechart.view.PieChartView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private PieChartView pieChartView;
    private ArrayList<PieData> mDatas = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pieChartView = (PieChartView) findViewById(R.id.pie_chart);
        pieChartView.setmStartAngle(180);
        int[] colors = {0xFFFF0000, 0xFF00FF00, 0xFF0000FF};
        pieChartView.setmColors(colors);
        for (int i = 0; i < 5; i++) {
            switch (i) {
                case 0:
                    mDatas.add(new PieData("语文",100));
                    break;
                case 1:
                    mDatas.add(new PieData("数学",200));
                    break;
                case 2:
                    mDatas.add(new PieData("英语",150));
                    break;
                case 3:
                    mDatas.add(new PieData("物理",100));
                    break;
                case 4:
                    mDatas.add(new PieData("化学",120));
                    break;
            }
        }
        pieChartView.setmData(mDatas);
    }
}
