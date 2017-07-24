package com.fykj.yzy.beanmovie.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.Toast;

import com.fykj.yzy.beanmovie.Adapter.HistoryAdapter;
import com.fykj.yzy.beanmovie.DB.DBManage;
import com.fykj.yzy.beanmovie.R;
import com.fykj.yzy.beanmovie.bean.HistoryBean;

import java.util.ArrayList;
import java.util.Calendar;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    ArrayList<HistoryBean> historyData;
    HistoryAdapter adapter;
    private DBManage db;


    public Handler handler=new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(Message msg) {
            switch (msg.what){
                case 1:
                    String searchString=msg.getData().getString("searchString");
                    Log.d(TAG, "handleMessage: "+searchString);
                    searchEdit.setText(searchString);
            }
            return false;
        }
    });



    @BindView(R.id.search_edit_search)
    EditText searchEdit;
    @BindView(R.id.search_history)
    RecyclerView historyView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        EidtFocus();
        db=DBManage.getDBManage(getApplicationContext());

        getHistoryData();
        adapter=new HistoryAdapter(this,historyData,handler);
        LinearLayoutManager manager=new LinearLayoutManager(this);
        historyView.setAdapter(adapter);
        historyView.setLayoutManager(manager);


    }

    private void getHistoryData() {
        historyData=new ArrayList<HistoryBean>();
        historyData=db.listHistory();

    }

    private void EidtFocus() {
        //设置输入框 获得焦点弹出软键盘
        searchEdit.setFocusable(true);
        searchEdit.setFocusableInTouchMode(true);
        searchEdit.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
    }

    @OnClick(R.id.search_text_cancel)
    void clickEnter(){
        String searchString=searchEdit.getText()+"";
        Log.d(TAG, "clickEnter: "+searchString);
        if (searchString==""){
            Toast.makeText(this,"请输入你要搜索的内容",Toast.LENGTH_LONG).show();
        }else {
            HistoryBean bean = new HistoryBean(searchString, getDayAndMouth());
            db.insertHistory(bean);

            Intent intent = new Intent(this, SearchResultActivity.class);
            intent.putExtra("searchString", searchString);
            startActivity(intent);
        }
    }

    private String getDayAndMouth() {
        Calendar c = Calendar.getInstance();
        int d = c.get(Calendar.DAY_OF_MONTH);
        int m = c.get(Calendar.MONTH);
        String result=new String(m+"月"+d+"日");
        return result;
    }

    @OnClick(R.id.search_text_removeAll)
    void clickRemoveAll(){
        historyData.clear();
        adapter.notifyDataSetChanged();
        db.removeAllHistory();

    }

}
