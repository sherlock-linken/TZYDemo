package com.tzy.tzydemo;

import android.app.Activity;
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

import java.util.ArrayList;

public class MainActivity extends Activity {

    private ArrayList<String> dataList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        dataList.add("sherlock");
        dataList.add("linken");

        /**
         * 现在代码版本是2.2的从2.1一半版本开始的M1任务
         * 1010101010101010101
         * 2929292929292929292
         * 3838383838383838383
         * 4747474747474747474
         * 5656565656565656565
         * 95596送到
         * 6565656565656565656
         * 7474747474747474747
         * 8383838383838383838
         * 9292929292929292929
         * 0101010101010101010
         * */

        RecyclerView demoList = findViewById(R.id.rcy_demo_list);
        DataAdapter adapter = new DataAdapter();
        demoList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        demoList.setAdapter(adapter);

    }


    private class DataAdapter extends RecyclerView.Adapter<DemoItemViewHolder> {

        @NonNull
        @Override
        public DemoItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            return new DemoItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_demo_list, parent, false));
        }

        @Override
        public void onBindViewHolder(@NonNull DemoItemViewHolder holder, int position) {

            String data = dataList.get(position);

            holder.title.setText(data);


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
