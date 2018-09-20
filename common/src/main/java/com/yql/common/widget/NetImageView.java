package com.yql.common.widget;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.widget.ImageView;

import java.lang.ref.SoftReference;
import java.net.URL;
import java.util.HashMap;

/**
 * 加载网络图片的imageview
 * Created by lingxiaoming on 2017/9/15 0015.
 */

public class NetImageView extends ImageView {


    private static HashMap<String, SoftReference<Drawable>> mImageCache = new HashMap<>();
    private String mImageUrl;

    public NetImageView(Context context) {
        super(context);
    }

    public NetImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public void loadImage(String url, int placeholdID) {
        mImageUrl = url;
        if (mImageCache.containsKey(url)) {
            SoftReference<Drawable> softReference = mImageCache.get(url);
            Drawable drawable = softReference.get();
            if (drawable != null) {
                setImageDrawable(drawable);
                return;
            }
        }

        setImageResource(placeholdID);

        final Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                Drawable drawable = (Drawable) msg.obj;
                setImageDrawable(drawable);
            }

        };

        new Thread() {
            @Override
            public void run() {
                Drawable drawable = loadImageFromUrl(mImageUrl);
                mImageCache.put(mImageUrl, new SoftReference<>(drawable));
                Message message = handler.obtainMessage();
                message.obj = drawable;
                handler.sendMessage(message);
            }
        }.start();
    }

    private Drawable loadImageFromUrl(String imageUrl) {
        try {
            return Drawable.createFromStream(new URL(imageUrl).openStream(), "src");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
