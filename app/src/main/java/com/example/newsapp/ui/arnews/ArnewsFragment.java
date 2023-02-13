package com.example.newsapp.ui.arnews;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newsapp.R;
import com.example.newsapp.databinding.FragmentArnewsBinding;
import com.example.newsapp.ui.home.NewsImages;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class ArnewsFragment extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentArnewsBinding binding;

    List<NewsImages> newsImg;

    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentArnewsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        newsImg = new ArrayList<>();
        getArNewsList();

        return root;
    }

    private void getArNewsList(){
        String myJSONStr = loadJSONFromAsset("arabicnews.json");

        try{
            JSONObject rootJSONObject = new JSONObject(myJSONStr);

            JSONArray arnewsJSONArray = rootJSONObject.getJSONArray("arnews");
            ArrayList<ArNews> arNewsArrayList = new ArrayList<>();

            for (int i = 0; i < arnewsJSONArray.length(); i++){
                NewsImages newsimages = new NewsImages();

                JSONObject jsonObject = arnewsJSONArray.getJSONObject(i);

                newsimages.setNewsThbUrl(jsonObject.getString("thumbnail"));
                newsimages.setNewsImageUrl(jsonObject.getString("image"));

                newsImg.add(newsimages);

                title.add(jsonObject.getString("title"));
                date.add(jsonObject.getString("date"));
                description.add(jsonObject.getString("description"));

                ArNews arNews = new ArNews(jsonObject.getString("title"), jsonObject.getString("date"), jsonObject.getString("description"));
                arNewsArrayList.add(arNews);
            }

            ListArAdapter listArAdapter = new ListArAdapter(getActivity(), arNewsArrayList, newsImg);
            binding.lstAr.setAdapter(listArAdapter);
            binding.lstAr.setClickable(true);
            binding.lstAr.setOnItemClickListener(this);

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

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent i = new Intent(getActivity(), ArabicDetailsActivity.class);
        i.putExtra("title", title.get(position));
        i.putExtra("date", date.get(position));
        i.putExtra("description", description.get(position));
        i.putExtra("image", newsImg.get(position).getNewsImageUrl());
        i.putExtra("position", position);

        startActivity(i);
    }
}