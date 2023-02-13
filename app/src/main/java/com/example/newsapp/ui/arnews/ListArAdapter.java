package com.example.newsapp.ui.arnews;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.volley.toolbox.ImageLoader;
import com.example.newsapp.R;
import com.example.newsapp.ui.album.CustomVolleyRequest;
import com.example.newsapp.ui.home.NewsImages;

import java.util.ArrayList;
import java.util.List;


public class ListArAdapter extends ArrayAdapter<ArNews> {
    Context context;
    private List<NewsImages> newsImg;
    ImageLoader imageLoader;

    public ListArAdapter(Context context, ArrayList<ArNews> arNewsArrayList, List newsImg){
        super(context, R.layout.listarabic, arNewsArrayList);

        this.context = context;
        this.newsImg = newsImg;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ArNews arNews = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listarabic, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.arThb);
        TextView titleTxt = convertView.findViewById(R.id.arTitle);
        TextView dateTxt = convertView.findViewById(R.id.arDate);

        NewsImages utils = newsImg.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(utils.getNewsThbUrl(), ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        titleTxt.setText(arNews.title);
        dateTxt.setText(arNews.date);

        return convertView;
    }
}
