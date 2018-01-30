package bpk.light.app_11;

import android.content.Intent;
import android.support.constraint.ConstraintLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TableRow;

import java.net.URL;
import com.bumptech.glide.Glide;

public class TwoActivity extends AppCompatActivity {
    ConstraintLayout lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two);
        lay = (ConstraintLayout)findViewById(R.id.lay);
        ImageView img1 = new ImageView(TwoActivity.this);
        Glide.with(TwoActivity.this).load("http://www.picshare.ru/uploads/180130/K88iuera7Q.gif").into(img1);
        TableRow tableRow = new TableRow(TwoActivity.this);
        tableRow.setLayoutParams(new TableRow.LayoutParams(150, 150));
        tableRow.addView(img1);
        lay.addView(tableRow);
        img1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(TwoActivity.this,DrawActivity.class);
                startActivity(intent);
            }
        });
    }
}
