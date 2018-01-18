package bpk.light.app_11;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;

public class DrawActivity extends AppCompatActivity {
ImageView image1;
    int width, height;
    String LL="LightLog";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_draw);
        image1 = (ImageView) findViewById(R.id.image1);
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 1;
        Bitmap bmOriginal = BitmapFactory.decodeResource(getResources(), R.drawable.pixduck,options);
        float  dency = getResources().getDisplayMetrics().density;
        //width = bmOriginal.getWidth();
        //height = bmOriginal.getHeight();
        //int[] bitMass = new int[width*height];
        //bmOriginal.getPixels(bitMass,0,width,0,0,width,height);
        Bitmap dest = Bitmap.createBitmap(
               bmOriginal.getWidth(), bmOriginal.getHeight(), bmOriginal.getConfig());
        Log.d(LL,"Width "+bmOriginal.getWidth()+" Height "+bmOriginal.getHeight()+" dency "+dency);
        for(int x = 0; x < bmOriginal.getWidth(); x++){
            for(int y = 0; y < bmOriginal.getHeight(); y++){
                int pixel = bmOriginal.getPixel(x,y);
                int alfaPixel = Color.alpha(pixel);
                int rColor = Color.red(pixel);
                int gColor = Color.green(pixel);
                int bColor = Color.blue(pixel);
                Log.d(LL,"X "+x+" Y "+y+ "   alfa "+alfaPixel+" r "+rColor+" g "+gColor+" b "+bColor);
                int newPixel =Color.argb(alfaPixel,rColor,gColor,bColor);
                dest.setPixel(x,y,newPixel);
            }
        }
        int newPixel = Color.argb(255,0,0,0);
        dest.setPixel(0,0,newPixel);
        Bitmap bigduck = Bitmap.createScaledBitmap(dest,dest.getWidth()*8,dest.getHeight()*8,false);
        image1.setImageBitmap(bigduck);
    }
}
