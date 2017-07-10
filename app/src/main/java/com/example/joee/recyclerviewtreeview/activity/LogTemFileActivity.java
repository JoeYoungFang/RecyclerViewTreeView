package com.example.joee.recyclerviewtreeview.activity;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.joee.recyclerviewtreeview.R;
import com.example.joee.recyclerviewtreeview.adapter.BaseRecyclerViewAdapter;
import com.example.joee.recyclerviewtreeview.adapter.ViewHolder;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by joee on 2016/9/23.
 */
public class LogTemFileActivity extends Activity {
    private RecyclerView mRecyclerView;
    private List<File> mTemFiles;
    public static final String TMP = ".tmp";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = (RecyclerView) findViewById(R.id.mRecyclerView);
        mTemFiles = new ArrayList<>();
        scanTemFile();
        Toast.makeText(LogTemFileActivity.this, "scan success", Toast.LENGTH_SHORT).show();
        mRecyclerView.setAdapter(new BaseRecyclerViewAdapter<File>(LogTemFileActivity.this, mTemFiles, R.layout.layout_item) {
            @Override
            public void bindView(ViewHolder holder, File file, int position) {
                holder.setText(R.id.text_item, file.getName());
            }
        });
        mRecyclerView.setLayoutManager(new LinearLayoutManager(LogTemFileActivity.this));
    }

    public void scanTemFile() {
        File file = new File(getRootPath());
        File[] files = file.listFiles();
        if (files == null)
            return;
        for (File f : files) {
            traversalFile(f);
        }
    }

    public void traversalFile(File file) {
        if (file.isFile()) {
            if (file.getName().endsWith(TMP)) {
                mTemFiles.add(file);
            }
        } else if (file.isDirectory()) {
            File[] files = file.listFiles();
            for (File f : files) {
                traversalFile(f);
            }
        }
    }

    public String getRootPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";
    }
}
