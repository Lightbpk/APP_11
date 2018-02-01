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

import android.os.Bundle;

import android.util.Log;
import android.view.Display;

import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;


import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;


import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static android.util.DisplayMetrics.DENSITY_HIGH;


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
                        .load("http://www.picshare.ru/uploads/180201/n4SNu8p6pc.bmp")
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
                        .load("http://www.picshare.ru/uploads/180201/dNvCCPn466.bmp")
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
        int x,y,paint,tempColor;
        int[] colorPix;

        public DrawView(Context context) {
            super(context);
            paint = Color.argb(255, 149, 66, 14);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDensity = DENSITY_HIGH;
            options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            colorPix = new int[259];
            //List<Integer> colorNum = new ArrayList<>();
            if (bmOriginal != null) {
                Log.d(LL, String.format("bitmap = %s, width = %s, height = %s, mimetype = %s",
                        bmOriginal, options.outWidth,
                        options.outHeight,
                        options.outMimeType));
                dest = Bitmap.createBitmap(
                        bmOriginal.getWidth(),
                        bmOriginal.getHeight(),
                        bmOriginal.getConfig());
                int c =0;
                for (int x = 0; x < bmOriginal.getWidth(); x++) {
                    for (int y = 0; y < bmOriginal.getHeight(); y++) {
                        int pixel = bmOriginal.getPixel(x, y);
                        int alfaPixel = Color.alpha(pixel);
                        int rColor = Color.red(pixel);
                        int gColor = Color.green(pixel);
                        int bColor = Color.blue(pixel);
                        Log.d(LL, "X " + x + " Y " + y + "   alfa " + alfaPixel + " r " + rColor + " g " + gColor + " b " + bColor);
                        int newPixel = Color.argb(alfaPixel, rColor, gColor, bColor);
                        dest.setPixel(x, y, Color.argb(255,255,255,255));
                        for(int j =0; j < colorPix.length;j++){
                            if(colorPix[j]!= newPixel){
                                colorPix[c]=newPixel;
                                //colorNum.add(newPixel);
                                Log.d(LL, String.format("C = %s newpix = %s",c, colorPix[c]));
                                c++;
                                break;
                            }
                        }
                    }
                }
                int[] pixCode = new int[colorPix.length];
                pixCode =colorPix;
                Arrays.sort(pixCode);
                int n=0;
                int varcolor =0;
                while(n <(pixCode.length-1)) {
                    if (pixCode[n]==0){
                        varcolor = n-1;
                        break;
                    }
                    //Log.d(LL, String.format("ColorRGB = %s index %s",colorPix[n],n));
                    n++;
                }

                //Поменять bmOriginal на dest для обесцвецивания
                bigduck = Bitmap.createScaledBitmap(bmOriginal, bmOriginal.getWidth() * 30, bmOriginal.getHeight() * 30, false);
            }else Log.d(LL,"Null bitmap");
        }
        @Override
        public boolean onTouchEvent(MotionEvent event) {
            Log.d(LL, "Coords: x=" + event.getX() + ",y=" + event.getY());
            x = (int)event.getX();
            y = (int)event.getY();
                Log.d(LL,String.format("Non set x  = %s y = %s",x,y));
                dest.setPixel(x/30, y/30, paint);
                bigduck = Bitmap.createScaledBitmap(dest, dest.getWidth() * 30, dest.getHeight() * 30, false);
                invalidate();

            return true;
        }

        protected void onDraw(Canvas canvas){
            String code="";
            canvas.drawBitmap(bigduck,0,0,null);
            Paint p = new Paint();

            for(int x = 0; x < bmOriginal.getWidth(); x++){
                for(int y = 0; y < bmOriginal.getHeight(); y++){
                    int pixel = bmOriginal.getPixel(x,y);
                    Rect rect = new Rect(x*30,y*30,(x*30)+30,(y*30)+30);
                    for(int i=0; i<colorPix.length-1;i++ ){
                        if(pixel == colorPix[i]&&pixel!=0){
                            code = ""+i;
                            break;
                        }
                    }
                    /*if(rColor==255&&gColor==255&&bColor==255)code="0";
                    else if(rColor==16&&gColor==118&&bColor==183)code="1";
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
                    else if(rColor==196&&gColor==148&&bColor==22)code="6";*/
                    p.setColor(Color.argb(150,0,0,0));
                    p.setTextSize(10);
                    canvas.drawText(code,x*30,y*30+30,p);
                    p.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(rect, p);
                }
            }

        }
    }
}
