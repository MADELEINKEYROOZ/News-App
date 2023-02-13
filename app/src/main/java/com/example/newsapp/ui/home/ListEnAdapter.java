package com.example.newsapp.ui.home;

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

import java.util.ArrayList;
import java.util.List;

public class ListEnAdapter extends ArrayAdapter<EngNews> {
    Context context;
    private List<NewsImages> newsImg;
    ImageLoader imageLoader;

    public ListEnAdapter(Context context, ArrayList<EngNews> engNewsArrayList, List newsImg){
        super(context, R.layout.listenglish, engNewsArrayList);

        this.context = context;
        this.newsImg = newsImg;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        EngNews engNews = getItem(position);

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.listenglish, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.enThb);
        TextView titleTxt = convertView.findViewById(R.id.enTitle);
        TextView dateTxt = convertView.findViewById(R.id.enDate);

        NewsImages utils = newsImg.get(position);
        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(utils.getNewsThbUrl(), ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        titleTxt.setText(engNews.title);
        dateTxt.setText(engNews.date);

        return convertView;
    }
}
