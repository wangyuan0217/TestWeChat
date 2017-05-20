package com.libo.testwechat;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.libo.testwechat.http.Apis;
import com.libo.testwechat.http.MyCallback;
import com.libo.testwechat.model.History;
import com.libo.testwechat.util.PreferenceUtil;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends Activity implements SwipeRefreshLayout.OnRefreshListener,
        BaseQuickAdapter.RequestLoadMoreListener {

    private SwipeRefreshLayout mSwipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private List<History> list_data = new ArrayList<>();
    private QuickAdapter mAdapter;

    private int currentPage = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipelayout);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler);

        mSwipeRefreshLayout.setOnRefreshListener(this);
        mSwipeRefreshLayout.setColorSchemeColors(ContextCompat.getColor(this, R.color.colorPrimary));

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(mAdapter = new QuickAdapter());

        mAdapter.setEnableLoadMore(true);
        mAdapter.setOnLoadMoreListener(this, mRecyclerView);

        getData();
    }

    private void getData() {
        String uid = PreferenceUtil.getInstance().getString(Constant.UID, "");
        Apis.getInstance().history(uid, currentPage, new MyCallback() {
            @Override
            public void responeData(String body, JSONObject json) {
                List<History> list = new Gson().fromJson(body, new TypeToken<List<History>>() {
                }.getType());
                if (currentPage == 1) {
                    list_data = list;
                    mAdapter.setNewData(list);
                } else {
                    list_data.addAll(list);
                    mAdapter.notifyDataSetChanged();
                }
                mSwipeRefreshLayout.setRefreshing(false);
                mAdapter.loadMoreComplete();

                if (list.size() < 10) {
                    mAdapter.loadMoreEnd();
                }
            }

            @Override
            public void responeDataFail(int responseStatus, String errMsg) {
                Toast.makeText(HistoryActivity.this, errMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }

    class QuickAdapter extends BaseQuickAdapter<History, BaseViewHolder> {
        public QuickAdapter() {
            super(R.layout.item_history, list_data);
        }

        @Override
        protected void convert(BaseViewHolder viewHolder, History item) {
            viewHolder.setText(R.id.perid, "第" + item.getPeriod() + "期")
                    .setText(R.id.result, "开奖结果：" + item.getResult())
                    .setText(R.id.time, item.getTime());
        }
    }

    public void back(View view) {
        finish();
    }

    @Override
    public void onRefresh() {
        currentPage = 1;
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getData();
            }
        }, 500);
    }

    @Override
    public void onLoadMoreRequested() {
        currentPage++;
        getData();
    }
}
