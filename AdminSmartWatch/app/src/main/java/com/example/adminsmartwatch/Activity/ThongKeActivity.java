package com.example.adminsmartwatch.Activity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.adminsmartwatch.Object.ThongKe;
import com.example.adminsmartwatch.R;
import com.example.adminsmartwatch.Until.Server;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.github.mikephil.charting.utils.ColorTemplate.MATERIAL_COLORS;

public class ThongKeActivity extends AppCompatActivity {

    ArrayList<ThongKe> listThongKe;
    EditText edtDate;
    BarChart barChart;
    ArrayList<BarEntry> list;
    Button btnThongKe;
    int year;
    int month;
    Animation scaleUp,scaleDown;

    private void  addControls(){
        edtDate = findViewById(R.id.editTextDate);
        barChart = findViewById(R.id.barChart);
        listThongKe = new ArrayList<>();
        list = new ArrayList<>();
        btnThongKe = findViewById(R.id.buttonThongKe);
        scaleUp= AnimationUtils.loadAnimation(this, R.anim.scale1);
        scaleDown= AnimationUtils.loadAnimation(this, R.anim.scale);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bar_chart);

        addControls();
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChonNgay();
            }
        });

        btnThongKe.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                readData();
                if(event.getAction()==MotionEvent.ACTION_UP){
                    btnThongKe.startAnimation(scaleUp);
                }else if(event.getAction()==MotionEvent.ACTION_DOWN){
                    btnThongKe.startAnimation(scaleDown);
                }
                return true;
            }

        });
    }

    private void duLieuThongKe(int thang,int nam) {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        String url = Server.thongKe + "/" + thang + "-" + nam;
//        System.out.println("Tháng:"+thang+"  Năm:"+nam);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Request.Method.GET,
                url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            listThongKe.clear();
                            // Khởi tạo biến toàn cục của sản phẩm
                            String tensanpham = "";
                            int tongsoluong = 0;
                            for (int i = 0; i < response.length(); i++) {
                                try {
                                    JSONObject jsonObject = response.getJSONObject(i);
                                    tensanpham = jsonObject.getString("tensp");
                                    tongsoluong = jsonObject.getInt("tongsoluong");
                                    listThongKe.add(new ThongKe(tensanpham,tongsoluong));
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                System.out.println("============Lỗi rồi===============" + error);

            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap headers = new HashMap();
                headers.put("Authorization", DangNhapActivity.token);
                return headers;
            }
        };
        requestQueue.add(jsonArrayRequest);
    }

    private void readData() {
        list.clear();
        duLieuThongKe(month,year);
        final ArrayList<String> xAxisLabel = new ArrayList<>();
        for(int i = 0;i< listThongKe.size();i++){
            xAxisLabel.add(listThongKe.get(i).getTensanpham());
        }
        XAxis xAxis = barChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM_INSIDE);
        ValueFormatter formatter = new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return xAxisLabel.get((int) value);
            }
        };

        xAxis.setGranularity(1f); // minimum axis-step (interval) is 1
        xAxis.setValueFormatter(formatter);


        for(int i = 0;i< listThongKe.size();i++){
            list.add(new BarEntry(i,listThongKe.get(i).getTongsoluong()));
        }
        BarDataSet barDataSet = new BarDataSet(list,"SẢN PHẨM BÁN CHẠY");
        barDataSet.setColors(MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        barDataSet.setValueTextSize(16f);

        BarData barData = new BarData(barDataSet);
        barChart.setFitBars(true);
        barChart.setData(barData);
        barChart.getDescription().setText("");
        barChart.animateY(2000);
    }

    private void ChonNgay() {
        Calendar calendar = Calendar.getInstance();
        int dayNow = calendar.get(Calendar.DATE);
        int monthNow = calendar.get(Calendar.MONTH);
        int yearNow = calendar.get(Calendar.YEAR);
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                edtDate.setText("Tháng "+ (i1 + 1) + " Năm "+ i);
                year = i;
                month = i1+1;
            }
        },yearNow,monthNow,dayNow);
        datePickerDialog.show();
    }
}
