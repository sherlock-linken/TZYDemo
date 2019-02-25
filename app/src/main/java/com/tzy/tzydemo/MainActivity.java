package com.tzy.tzydemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tzy.tzydemo.spanhtml.SpanAndHtmlActivity;

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<NameClassEntity> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList.add(new NameClassEntity("spanAndHtml", SpanAndHtmlActivity.class));

        /**
         * 现在代码版本是2.1已经开发完啦！哈哈哈
         * 2.1又改动了东西啊
         * 我在base这边改了一点全局性的东西
         * 1010101010101010101
         * 现在代码版本是2.2的从2.1版本一半开始的M2任务代码
         * 1101
         * 2929292929292929292
         * 3838383838383838383
         * 这里2.1加了些需求
         * 4747474747474747474
         * 5656565656565656565
         * 6565这里是2.1改的bug6565656
         * 565654407665656565656565
         * 12131搜索
         * 6565656565656565656
         * 7474747474747474747
         * 83838382929
         * 0101010101010101010
         * */

        RecyclerView demoList = findViewById(R.id.rcy_demo_list);
        DataAdapter adapter = new DataAdapter();
        demoList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        demoList.setAdapter(adapter);

    }


    private class NameClassEntity {
        public String name;
        public Intent intent;

        public NameClassEntity(String name, Class clazz) {
            this.name = name;
            this.intent = new Intent(MainActivity.this, clazz);
        }
    }

    private class DataAdapter extends RecyclerView.Adapter<DemoItemViewHolder> {

        @NonNull
        @Override
        public DemoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DemoItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull DemoItemViewHolder holder, final int position) {

            final String data = dataList.get(position).name;

            holder.title.setText(data);

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(dataList.get(position).intent);
                }
            });


        }

        @Override
        public int getItemCount() {
            return dataList.size();
        }
    }


    private class DemoItemViewHolder extends RecyclerView.ViewHolder {

        public TextView title;


        public DemoItemViewHolder(View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.tv_title);

        }
    }

}
