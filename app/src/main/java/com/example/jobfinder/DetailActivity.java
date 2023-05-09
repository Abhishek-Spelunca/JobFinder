package com.example.jobfinder;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class DetailActivity extends AppCompatActivity {

    TextView detailDesc,detailTitle,detailCompany,detailType;
    ImageView detailImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        detailDesc=findViewById(R.id.detailDesc);
        detailImage=findViewById(R.id.detailImage);
        detailTitle=findViewById(R.id.detailTitle);
        detailCompany=findViewById(R.id.detailCompany);
        detailType=findViewById(R.id.detailType);

        Bundle bundle=getIntent().getExtras();
        if(bundle!=null){
            detailDesc.setText(bundle.getString("Description"));
            detailType.setText(bundle.getString("Type"));
            detailCompany.setText(bundle.getString("Company"));
            detailTitle.setText(bundle.getString("Title"));
            Glide.with(this).load(bundle.getString("Image")).into(detailImage);

        }
    }
}