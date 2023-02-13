package com.example.newsapp.ui.arnews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.android.volley.toolbox.ImageLoader;
import com.example.newsapp.R;
import com.example.newsapp.databinding.ArnewsdetailsBinding;
import com.example.newsapp.ui.album.CustomVolleyRequest;

public class ArabicDetailsActivity extends AppCompatActivity {
    ArnewsdetailsBinding binding;

    ImageLoader imageLoader;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ArnewsdetailsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Intent intent = this.getIntent();

        if (intent != null){
            String title = intent.getStringExtra("title");
            String date = intent.getStringExtra("date");
            String description = intent.getStringExtra("description");
            String urlImg = intent.getStringExtra("image");

            imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
            imageLoader.get(urlImg, ImageLoader.getImageListener(binding.DetailsImg, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

            binding.DetailsTit.setText(title);
            binding.DetailsDate.setText(date);
            binding.DetailsDesc.setText(description);
        }
    }
}