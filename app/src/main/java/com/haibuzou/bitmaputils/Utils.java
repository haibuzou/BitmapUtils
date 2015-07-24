package com.haibuzou.bitmaputils;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

/**
 * Created by Administrator on 2015/7/21.
 */
public class Utils {

    public static int caculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        //inSampleSize 默认值设置为1 不变化
        int inSampleSize = 1;
        //获取Image的实际的宽高
        int width = options.outWidth;
        int height = options.outHeight;

        //判断Image的宽或者高是否大于ImageView的宽或高
        if (width > reqWidth || height > reqHeight) {

            //计算inSampleSize的时候采取的是取inSampleSize的2的幂值()
            while ((width / inSampleSize) > reqWidth && (height / inSampleSize) > reqHeight) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;

    }

    public static Bitmap decodeScaleBitmapFromResource(Resources res,int resId, int reqWidth , int reqHeight){
        BitmapFactory.Options options = new BitmapFactory.Options();
        //inJustDecodeBounds 设置为true时，执行decodeResource操作不会生成bitmap占用内存
        //但是decodeResource以后可以通过options来获取图片的宽和高
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);
        //转为false生成图片，设置inSampleSize
        options.inJustDecodeBounds = false;
        options.inSampleSize = caculateInSampleSize(options,reqWidth,reqHeight);;
        return BitmapFactory.decodeResource(res,resId,options);
    }

    public static boolean cancelPotentialWork(int resId , ImageView imageView){
        final BitmapWorkerTask bitmapWorkerTask = getBitmapWorkerTask(imageView);
        if(bitmapWorkerTask != null){
            int bitmapData = bitmapWorkerTask.resId;
            //当前没有设置图片或者现在的图片与
            if(bitmapData == 0 || bitmapData != resId){
                bitmapWorkerTask.cancel(true);
            }else{
                //图片资源id不为0 并且新的资源id与之前的资源id相同，不作任何事情
                return false;
            }
        }
        //imageView上已经没有AsyncTask在执行
        return true;
    }

    public static BitmapWorkerTask getBitmapWorkerTask(ImageView imageView){
        if(imageView != null){
            Drawable drawable = imageView.getDrawable();
            if(drawable instanceof AsyncDrawable){
                AsyncDrawable asyncDrawable = (AsyncDrawable)drawable;
                return  asyncDrawable.getBitmapWorkerTask();
            }
        }
        return null;
    }
}

