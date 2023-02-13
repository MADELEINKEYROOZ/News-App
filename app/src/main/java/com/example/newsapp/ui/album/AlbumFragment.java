package com.example.newsapp.ui.album;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newsapp.R;
import com.example.newsapp.databinding.FragmentAlbumBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class AlbumFragment extends Fragment {

    private FragmentAlbumBinding binding;

    ViewPager mViewPager;

    LinearLayout sliderDotspanel;
    private int dotscount;
    private ImageView[] dots;

    List<SliderUtils> sliderImg;

    ViewPagerAdapter mViewPagerAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentAlbumBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        sliderImg = new ArrayList<>();
        sliderDotspanel = (LinearLayout) root.findViewById(R.id.SliderDots);

        getAlbumList();

        return root;
    }

    private void getAlbumList(){
        String myJSONStr = loadJSONFromAsset("album.json");

        try{
            JSONObject rootJSONObject = new JSONObject(myJSONStr);
            JSONArray albumJSONArray = rootJSONObject.getJSONArray("album");

            for (int i = 0; i < albumJSONArray.length(); i++){
                SliderUtils sliderUtils = new SliderUtils();
                JSONObject jsonObject = albumJSONArray.getJSONObject(i);
                sliderUtils.setSliderImageUrl(jsonObject.getString("image"));

                sliderImg.add(sliderUtils);
            }

            mViewPager = binding.viewPagerAlbum;
            mViewPagerAdapter = new ViewPagerAdapter(getActivity(), sliderImg);
            mViewPager.setAdapter(mViewPagerAdapter);

            dotscount = mViewPagerAdapter.getCount();
            dots = new ImageView[dotscount];
            for(int i = 0; i < dotscount; i++){
                dots[i] = new ImageView(getActivity());
                dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.inactive_dot));
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                params.setMargins(8, 0, 8, 0);
                sliderDotspanel.addView(dots[i], params);
            }
            dots[0].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));


            mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                }

                @Override
                public void onPageSelected(int position) {

                    for(int i = 0; i< dotscount; i++){
                        dots[i].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.inactive_dot));
                    }

                    dots[position].setImageDrawable(ContextCompat.getDrawable(getActivity(), R.drawable.active_dot));

                }
                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String loadJSONFromAsset(String fileName){
        String json;
        try{
            InputStream is = getActivity().getAssets().open(fileName);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            json = new String(buffer, StandardCharsets.UTF_8);
        } catch (IOException ex){
            ex.printStackTrace();
            return null;
        }
        return json;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}