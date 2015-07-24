package com.haibuzou.bitmaputils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    private Bitmap defaultBitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        defaultBitmap = BitmapFactory.decodeResource(getResources(),R.mipmap.ic_launcher);
    }

    public void loadBitmap(ImageView imageview,int resId){
        if(Utils.cancelPotentialWork(resId,imageview)) {
            BitmapWorkerTask task = new BitmapWorkerTask(imageview, this);
            AsyncDrawable asyncDrawable = new AsyncDrawable(getResources(),defaultBitmap,task);
            imageview.setImageDrawable(asyncDrawable);
            task.execute(resId);
        }
    }
}
