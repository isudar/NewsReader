package com.example.sudo.zadatakpt.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.sudo.zadatakpt.R;
import com.example.sudo.zadatakpt.models.News;
import com.squareup.picasso.Picasso;

/**
 * Created by Ivan on 22.03.2018..
 */

public class ScreenSliderFragment extends Fragment {

    private static final String KEY_DESCRIPTION = "description";
    private static final String KEY_IMAGE_URL = "imageUrl";

    // Store instance variables
    private String description;
    private String imageUrl;

    // newInstance constructor for creating fragment with arguments
    public static ScreenSliderFragment newInstance(News news) {
        ScreenSliderFragment fragmentFirst = new ScreenSliderFragment();
        Bundle args = new Bundle();
        args.putString(KEY_DESCRIPTION, news.getDescription());
        args.putString(KEY_IMAGE_URL, news.getUrlToImage());
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    // Store instance variables based on arguments passed
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        description = getArguments().getString(KEY_DESCRIPTION);
        imageUrl = getArguments().getString(KEY_IMAGE_URL);
    }

    // Inflate the view for the fragment based on layout XML
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_slider, container, false);
        TextView tvDescription = (TextView) view.findViewById(R.id.tv_description);
        ImageView ivContent = (ImageView) view.findViewById(R.id.iv_slideContent);
        tvDescription.setText(description);
        Picasso.with(getContext())
                .load(imageUrl)
                .fit()
                .centerCrop()
                .into(ivContent);
        return view;
    }


}
