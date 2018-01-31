package bpk.light.app_11;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.Touch;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import static android.util.DisplayMetrics.DENSITY_DEFAULT;
import static android.util.DisplayMetrics.DENSITY_DEVICE_STABLE;
import static android.util.DisplayMetrics.DENSITY_HIGH;
import static android.util.DisplayMetrics.DENSITY_LOW;
import static android.util.DisplayMetrics.DENSITY_MEDIUM;

public class DrawActivity extends Activity {
    Context context;
    //int width, height;
    String LL="LightLog";
    Bitmap dest;
    int pn;
    Bitmap bmOriginal;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
        WindowManager.LayoutParams.FLAG_FULLSCREEN);
        Intent intent = getIntent();
        pn =intent.getIntExtra("PicNum",1);
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        Log.d(LL,String.format("display %s x %s",size.x,size.y));
        switch(pn) {
            case 1: {
                Glide.
                        with(getApplicationContext())
                        .load("http://www.picshare.ru/uploads/180130/K88iuera7Q.gif")
                        .asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        bmOriginal = resource;
                        setContentView(new DrawView(context));
                        Log.d(LL, "Loaded!");
                    }
                });
            }
            case 2 :{
                Glide.
                        with(getApplicationContext())
                        .load("http://www.picshare.ru/uploads/180130/7Lqubex01W.gif")
                        .asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        bmOriginal = resource;
                        setContentView(new DrawView(context));
                        Log.d(LL, "Loaded!");
                    }
                });
            }
        }
    }

    class DrawView extends View {
        Bitmap bigduck;
        int x,y,paint;

        public DrawView(Context context) {
            super(context);
            paint = Color.argb(255, 149, 66, 14);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDensity = DENSITY_HIGH;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            if (bmOriginal != null) {
                Log.d(LL, String.format("bitmap = %s, width = %s, height = %s, mimetype = %s",
                        bmOriginal, options.outWidth,
                        options.outHeight,
                        options.outMimeType));
                dest = Bitmap.createBitmap(
                        bmOriginal.getWidth(),
                        bmOriginal.getHeight(),
                        bmOriginal.getConfig());
                for (int x = 0; x < bmOriginal.getWidth(); x++) {
                    for (int y = 0; y < bmOriginal.getHeight(); y++) {
                        int pixel = bmOriginal.getPixel(x, y);
                        int alfaPixel = Color.alpha(pixel);
                        int rColor = Color.red(pixel);
                        int gColor = Color.green(pixel);
                        int bColor = Color.blue(pixel);
                        Log.d(LL, "X " + x + " Y " + y + "   alfa " + alfaPixel + " r " + rColor + " g " + gColor + " b " + bColor);
                        int newPixel = Color.argb(alfaPixel, rColor, gColor, bColor);
                        dest.setPixel(x, y, newPixel);
                    }
                }
                bigduck = Bitmap.createScaledBitmap(bmOriginal, bmOriginal.getWidth() * 30, bmOriginal.getHeight() * 30, false);
            }else Log.d(LL,"Null bitmap");
        }
       /* @Override
        public boolean onTouchEvent(MotionEvent  event) {
            Log.d(LL, "Coords: x=" + event.getX() + ",y=" + event.getY());
            x = (int)event.getX();
            y = (int)event.getY();
            if(x>0&&x<=30&&y>510&&y<=540) {
                paint = Color.argb(255, 149, 66, 14);
                Log.d(LL,String.format("Set brown x  = %s y = %s",x,y));
            }
            else if(x>60&&x<=90&&y>510&&y<=540){
                paint = Color.argb(255,254,231,27);
                Log.d(LL,String.format("Set yellow x  = %s y = %s",x,y));
            }
            else if(x>120&&x<=150&&y>510&&y<=540){
                paint = Color.argb(255,255,167,117);
                Log.d(LL,String.format("Set pink x  = %s y = %s",x,y));
            }
            else if(x>180&&x<=210&&y>510&&y<=540){
                paint = Color.argb(255,186,229,160);
                Log.d(LL,String.format("Set LightGreen x  = %s y = %s",x,y));
            }
            else if(x>240&&x<=270&&y>510&&y<=540){
                paint = Color.argb(255,109,154,85);
                Log.d(LL,String.format("Set Green x  = %s y = %s",x,y));
            }
            else if(x>300&&x<=330&&y>510&&y<=540){
                paint = Color.argb(255,196,148,22);
                Log.d(LL,String.format("Set LightBrown x  = %s y = %s",x,y));
            }
            else if(x>360&&x<=390&&y>510&&y<=540){
                paint = Color.argb(255,255,255,255);
                Log.d(LL,String.format("Set Wight x  = %s y = %s",x,y));
            }
            else if(y>540);
            else {
                Log.d(LL,String.format("Non set x  = %s y = %s",x,y));
                dest.setPixel(x/30, y/30, paint);
                bigduck = Bitmap.createScaledBitmap(dest, dest.getWidth() * 30, dest.getHeight() * 30, false);
                invalidate();
            }
            return true;
        }*/

        protected void onDraw(Canvas canvas){
            String code="";
            canvas.drawBitmap(bigduck,0,0,null);
            Paint p = new Paint();
            Rect rect = new Rect(360,510,390,540);
            for(int x = 0; x < bmOriginal.getWidth(); x++){
                for(int y = 0; y < bmOriginal.getHeight(); y++){
                    int pixel = bmOriginal.getPixel(x,y);
                    //int alfaPixel = Color.alpha(pixel);
                    int rColor = Color.red(pixel);
                    int gColor = Color.green(pixel);
                    int bColor = Color.blue(pixel);
                    if(rColor==255&&gColor==255&&bColor==255)code="0";
                    else if(rColor==78&&gColor==78&&bColor==78)code="1";
                    else if(rColor==144&&gColor==144&&bColor==144)code="2";
                    else if(rColor==183&&gColor==183&&bColor==183)code="3";
                    else if(rColor==189&&gColor==189&&bColor==189)code="4";
                    else if(rColor==119&&gColor==119&&bColor==119)code="5";
                    else if(rColor==110&&gColor==110&&bColor==110)code="6";
                    else if(rColor==149&&gColor==66&&bColor==14)code="1";
                    else if(rColor==254&&gColor==231&&bColor==27)code="2";
                    else if(rColor==255&&gColor==167&&bColor==117)code="3";
                    else if(rColor==186&&gColor==229&&bColor==160)code="4";
                    else if(rColor==109&&gColor==154&&bColor==85)code="5";
                    else if(rColor==196&&gColor==148&&bColor==22)code="6";
                    p.setColor(Color.argb(150,0,0,0));
                    p.setTextSize(29);
                    canvas.drawText(code,x*30,y*30+30,p);
                    p.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(rect, p);
                }
            }

        }
    }
}
