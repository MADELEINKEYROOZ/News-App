package com.example.newsapp.ui.album;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

import com.android.volley.toolbox.ImageLoader;
import com.example.newsapp.R;

import java.util.List;
import java.util.Objects;

class ViewPagerAdapter extends PagerAdapter {
    Context context;

    private final List<SliderUtils> sliderImg;
    ImageLoader imageLoader;

    LayoutInflater mLayoutInflater;

    public ViewPagerAdapter(Context context, List sliderImg) {
        this.sliderImg = sliderImg;
        this.context = context;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sliderImg.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.albumitem, container, false);
        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageViewMain);

        SliderUtils utils = sliderImg.get(position);

        imageLoader = CustomVolleyRequest.getInstance(context).getImageLoader();
        imageLoader.get(utils.getSliderImageUrl(), ImageLoader.getImageListener(imageView, R.mipmap.ic_launcher, android.R.drawable.ic_dialog_alert));

        Objects.requireNonNull(container).addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}