package com.haibuzou.bitmaputils;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/7/21.
 */
public class BitmapWorkerTask extends AsyncTask<Integer,Void,Bitmap>{

    //声明一个弱引用的ImageView
    private WeakReference<ImageView> imageViewWeakReference;
    public int resId = 0;
    private Context context;

    public BitmapWorkerTask(ImageView imageView,Context context) {
        //弱引用来保证imageview会被快速的回收
        imageViewWeakReference = new WeakReference<ImageView>(imageView);
        this.context = context;
    }

    //在后台进行生成bitmap的操作
    @Override
    protected Bitmap doInBackground(Integer... params) {
        resId = params[0];
        return Utils.decodeScaleBitmapFromResource(context.getResources(),resId,100,100);
    }

    //完成后判断imageview是否还在，然后set bitmap
    @Override
    protected void onPostExecute(Bitmap bitmap) {

        if(isCancelled()){
            bitmap = null;
        }

        if(imageViewWeakReference != null && bitmap != null){
            ImageView imageView = imageViewWeakReference.get();
            BitmapWorkerTask bitmapWorkerTask = Utils.getBitmapWorkerTask(imageView);
            if(this == bitmapWorkerTask && imageView != null){
                imageView.setImageBitmap(bitmap);
            }
        }
    }
}
