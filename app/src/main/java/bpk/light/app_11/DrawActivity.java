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


import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

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
        ExecutorService exec = Executors.newCachedThreadPool();
        switch(pn) {
            case 1:
                {
                    exec.execute(new GetBitmapFromURL("http://www.imgup.ru/images_small2/2b3rx712455.bmp"));
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    setContentView(new DrawView(context));
                    break;
                /*try {
                    URL url = new URL("http://www.imgup.ru/images_small2/2b3rx712455.bmp");
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setDoInput(true);
                    connection.connect();
                    InputStream input = connection.getInputStream();
                    bmOriginal = BitmapFactory.decodeStream(input);
                    setContentView(new DrawView(context));
                } catch (IOException e) {
                    Log.d(LL,"Err Load");

                }
                break;
                Glide.
                        with(getApplicationContext())
                        .load("http://www.imgup.ru/images_small2/2b3rx712455.bmp")
                        .asBitmap().into(new SimpleTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                        bmOriginal = resource;
                        setContentView(new DrawView(context));
                        Log.d(LL, "Loaded! config "+bmOriginal.getConfig());
                    }
                });
                break;*/
            }
            case 2 :{
                exec.execute(new GetBitmapFromURL("http://www.imgup.ru/images_small2/2b3sx1215151115.bmp"));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                setContentView(new DrawView(context));
                break;

            }
        }
    }

    public class GetBitmapFromURL implements Runnable {
        String src;
        GetBitmapFromURL(String src){
            this.src = src;
        }

        @Override
        public void run() {
           try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            bmOriginal = BitmapFactory.decodeStream(input);
            Log.d(LL,"Load "+src+" complite");
        } catch (IOException e) {
            // Log exception
               Log.d(LL,"Load err");

        }
        }

    }

    class DrawView extends View {
        Bitmap bigduck;
        int x,y,paint,actColor = Color.argb(255,0,0,0) ,cv;
        int[] colorPix, rxmax, rxmin ,rymax, rymin;
        public DrawView(Context context) {
            super(context);
            paint = Color.argb(255, 149, 66, 14);
            BitmapFactory.Options options = new BitmapFactory.Options();
            options.inDensity = DENSITY_HIGH;
            //options.inPreferredConfig = Bitmap.Config.ARGB_8888;
            colorPix = new int[300];
            Arrays.fill(colorPix,0);
            //List<Integer> colorNum = new ArrayList<>();
            Log.d(LL,""+Arrays.toString(colorPix));
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
                        //Log.d(LL, "X " + x + " Y " + y + "   alfa " + alfaPixel + " r " + rColor + " g " + gColor + " b " + bColor);
                        int newPixel = Color.argb(alfaPixel, rColor, gColor, bColor);
                        dest.setPixel(x, y, Color.argb(255,255,255,255));
                        int c =0;
                        for(int j =0; j < colorPix.length;j++){
                                if(colorPix[j]==0){
                                    c = j;
                                    cv = c;
                                    colorPix[c]=newPixel;
                                    //colorNum.add(newPixel);
                                    Log.d(LL, String.format("add new pixel in colorpix (%s) newpix = %s j = %s",c, colorPix[c],j));
                                    break;
                                }else if(colorPix[j]==newPixel){
                                    break;
                                }
                        }
                    }
                }
                /*int[] pixCode = new int[colorPix.length];
                pixCode =colorPix;
                Arrays.sort(pixCode);
                int m=0;
                while(m<pixCode.length-1){
                    if(pixCode[m]==0)break;
                    m++;
                }
                Log.d(LL,"m "+m);
                Arrays.copyOfRange(pixCode,0,m);
                Log.d(LL,"pixcode lenth "+pixCode.length);
                int n=0;
                int varcolor =0;
                while(n <(pixCode.length-1)) {
                    if (pixCode[n]==0){
                        varcolor = n-1;
                        break;
                    }
                    //Log.d(LL, String.format("ColorRGB = %s index %s",colorPix[n],n));
                    n++;
                }*/

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
                try {
                    dest.setPixel(x / 30, y / 30, actColor);
                }catch (Exception e){

                }
                    for (int i = 0; i < cv; i++) {
                        if (x > rxmin[i] && x < rxmax[i] && y > rymin[i] && y < rymax[i]) {
                            actColor = colorPix[i];
                        }
                    }
            bigduck = Bitmap.createScaledBitmap(dest, dest.getWidth() * 30, dest.getHeight() * 30, false);
            invalidate();

            return true;
        }

        protected void onDraw(Canvas canvas){
            String code="";
            canvas.drawBitmap(bigduck,0,0,null);
            Paint p = new Paint();
            Log.d(LL,"C "+cv);
            for(int x = 0; x < bmOriginal.getWidth(); x++){
                for(int y = 0; y < bmOriginal.getHeight(); y++){
                    int pixel = bmOriginal.getPixel(x,y);
                    Rect rect = new Rect(x*30,y*30,(x*30)+30,(y*30)+30);
                    for(int i=0; i<colorPix.length;i++ ){
                        if(pixel == colorPix[i]&&pixel!=0){
                            code = ""+i;
                            break;
                        }
                        else{
                            code = "";
                        }
                    }

                    p.setColor(Color.argb(255,0,0,0));
                    p.setTextSize(25);
                    canvas.drawText(code,x*30,y*30+30,p);
                    p.setStyle(Paint.Style.STROKE);
                    canvas.drawRect(rect, p);
                }
            }

            int xs= 0;
            int ymax = bmOriginal.getHeight();
            ymax = (ymax*30)+30;
            rxmin = new int[cv];
            rxmax = new int[cv];
            rymin = new int[cv];
            rymax = new int[cv];
            for(int i=0 ; i<cv;i++){
                xs =xs+30;
                Rect rect = new Rect(xs, ymax,xs+30,ymax+30);
                rxmin [i]= xs;
                rxmax [i]= xs+30;
                rymin [i]= ymax;
                rymax [i]= ymax+30;
                Paint p2 = new Paint();
                p2.setColor(colorPix[i]);
                p.setStyle(Paint.Style.FILL_AND_STROKE);
                canvas.drawRect(rect, p2);
                canvas.drawText(""+i,xs,ymax+30,p);
            }
        }
    }
}
