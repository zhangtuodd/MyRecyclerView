package com.example.seele.myrecyclerview;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.seele.myrecyclerview.helper.SelectPicUtil;
import com.example.seele.myrecyclerview.helper.SimpleItemTouchHelperCallback;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private RecyclerView recyclerView;
    private Button picture,edit,camare;
    private List<poju> list = new ArrayList();
    private ItemTouchHelper mItemTouchHelper;
    private poju poju1;
    private RecycAdapter adapter;
    private LinearLayoutManager linearLayoutManager;
    public Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        recyclerView = (RecyclerView) findViewById(R.id.recyc);
        adapter = new RecycAdapter(this);
        camare = (Button) findViewById(R.id.camara);
        camare.setOnClickListener(this);
        edit = (Button) findViewById(R.id.edit);
        picture = (Button) findViewById(R.id.picture);
        edit.setOnClickListener(this);
        picture.setOnClickListener(this);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        //关联ItemTouchHelper和RecyclerView
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(adapter);
        mItemTouchHelper = new ItemTouchHelper(callback);
        mItemTouchHelper.attachToRecyclerView(recyclerView);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //输出图片800*400大小，选择图片时的裁剪比例是2:1

        bitmap = SelectPicUtil.onActivityResult(this, requestCode, resultCode, data, 1000, 800, 1, 1);
        if(bitmap != null){
            Log.i("tag","-----bitmap != null--");

            poju1 = new poju();
            poju1.type = 100;
            poju1.bitmap = bitmap;
            list.add(poju1);
            adapter.addData(list,linearLayoutManager);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.edit:
                poju1 = new poju();
                poju1.type = 200;
                list.add(poju1);
                adapter.addData(list,linearLayoutManager);
                break;
            case R.id.picture:
                SelectPicUtil.getByAlbum(this);
                break;
            case R.id.camara:
                SelectPicUtil.getByCamera(this);
                break;
        }
    }


}
