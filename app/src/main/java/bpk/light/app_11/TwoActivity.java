package bpk.light.app_11;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.net.URL;
import com.bumptech.glide.Glide;

public class TwoActivity extends AppCompatActivity {
    LinearLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        lay = (LinearLayout) findViewById(R.id.lay);
        ImageView img1 = new ImageView(TwoActivity.this);
        ImageView img2 = new ImageView(TwoActivity.this);
        Glide.with(TwoActivity.this).load("http://www.picshare.ru/uploads/180130/K88iuera7Q.gif").into(img1);
        Glide.with(TwoActivity.this).load("http://www.picshare.ru/uploads/180130/7Lqubex01W.gif").into(img2);
        TableRow tableRow = new TableRow(TwoActivity.this);
        TableRow tableRow2 = new TableRow(TwoActivity.this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(160, 160));
        tableRow2.setLayoutParams(new TableRow.LayoutParams(160, 160));
        tableRow.addView(img1);
        tableRow2.addView(img2);
        lay.addView(tableRow);
        lay.addView(tableRow2);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TwoActivity.this,DrawActivity.class);
                intent.putExtra("PicNum", 1);
                startActivity(intent);
            }
        });
        img2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TwoActivity.this,DrawActivity.class);
                intent.putExtra("PicNum", 2);
                startActivity(intent);
            }
        });
    }
}
