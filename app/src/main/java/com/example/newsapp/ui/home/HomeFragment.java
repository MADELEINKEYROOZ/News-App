package com.example.newsapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.example.newsapp.databinding.FragmentHomeBinding;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements AdapterView.OnItemClickListener {

    private FragmentHomeBinding binding;

    List<NewsImages> newsImg;

    ArrayList<String> title = new ArrayList<>();
    ArrayList<String> date = new ArrayList<>();
    ArrayList<String> description = new ArrayList<>();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        newsImg = new ArrayList<>();
        getEnNewsList();

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void getEnNewsList(){
        String myJSONStr = loadJSONFromAsset("englishnews.json");

        try{
            JSONObject rootJSONObject = new JSONObject(myJSONStr);

            JSONArray ennewsJSONArray = rootJSONObject.getJSONArray("ennews");
            ArrayList<EngNews> engNewsArrayList = new ArrayList<>();

            for (int i = 0; i < ennewsJSONArray.length(); i++){
                NewsImages newsimages = new NewsImages();

                JSONObject jsonObject = ennewsJSONArray.getJSONObject(i);
                newsimages.setNewsThbUrl(jsonObject.getString("thumbnail"));
                newsimages.setNewsImageUrl(jsonObject.getString("image"));

                newsImg.add(newsimages);

                title.add(jsonObject.getString("title"));
                date.add(jsonObject.getString("date"));
                description.add(jsonObject.getString("description"));

                EngNews engNews = new EngNews(jsonObject.getString("title"), jsonObject.getString("date"), jsonObject.getString("description"));
                engNewsArrayList.add(engNews);
            }

            ListEnAdapter listEnAdapter = new ListEnAdapter(getActivity(), engNewsArrayList, newsImg);
            binding.lstEn.setAdapter(listEnAdapter);
            binding.lstEn.setClickable(true);
            binding.lstEn.setOnItemClickListener(this);

        } catch (JSONException e){
            e.printStackTrace();
        }
    }

    public String
    loadJSONFromAsset(String fileName){
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
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
        Intent i = new Intent(getActivity(), EnglishDetailsActivity.class);
        i.putExtra("title", title.get(position));
        i.putExtra("date", date.get(position));
        i.putExtra("description", description.get(position));
        i.putExtra("image", newsImg.get(position).getNewsImageUrl());
        i.putExtra("position", position);

        startActivity(i);
    }
}