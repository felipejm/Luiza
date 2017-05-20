package br.com.luizalabs.utils;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.v4.content.ContextCompat;

public class DrawableHelper {

    public static Drawable fromName(Context context, String drawable, @DrawableRes int drawableDefault) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier(drawable, "drawable", context.getPackageName());
        resourceId = resourceId <= 0 ? drawableDefault : resourceId;
        return ContextCompat.getDrawable(context, resourceId);
    }

}
