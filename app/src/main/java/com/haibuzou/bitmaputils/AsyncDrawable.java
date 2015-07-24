package com.haibuzou.bitmaputils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;

import java.lang.ref.WeakReference;

/**
 * Created by Administrator on 2015/7/23.
 */
public class AsyncDrawable extends BitmapDrawable{

    private WeakReference<BitmapWorkerTask> bitmapWorkerTaskWeakReference;

    //将BitmapWorkerTask与BitmapDrawable绑定
    public AsyncDrawable(Resources res, Bitmap bitmap,BitmapWorkerTask bitmapWorkerTask) {
        super(res, bitmap);
        bitmapWorkerTaskWeakReference = new WeakReference<BitmapWorkerTask>(bitmapWorkerTask);

    }

    //提供获取该BitmapDrawable的 BitmapWorkerTask的方法
    public BitmapWorkerTask getBitmapWorkerTask(){
        return  bitmapWorkerTaskWeakReference.get();
    }
}
