package com.example.sudo.zadatakpt.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.sudo.zadatakpt.R;
import com.example.sudo.zadatakpt.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private List<News> news;

    public NewsAdapter(List<News> news) {
        this.news = news;
    }

    @Override
    public int getCount() {
        return this.news.size();
    }

    @Override
    public Object getItem(int position) {
        return this.news.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NewsViewHolder newsViewHolder = null;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_news, parent, false);
            newsViewHolder = new NewsViewHolder(convertView);
            convertView.setTag(newsViewHolder);
        } else {
            newsViewHolder = (NewsViewHolder) convertView.getTag();
        }
        News news = this.news.get(position);
        newsViewHolder.tvTitle.setText(news.getTitle());
        newsViewHolder.tvDescription.setText(news.getDescription());

        Picasso.with(parent.getContext())
                .load(news.getUrlToImage())
                .fit()
                .centerCrop()
                .into(newsViewHolder.ivPicture);

        return convertView;
    }

    private class NewsViewHolder {
        TextView tvTitle, tvDescription;
        ImageView ivPicture;
        ListView lvList;

        public NewsViewHolder(View newsView) {
            this.tvTitle = (TextView) newsView.findViewById(R.id.tvtitle);
            this.tvDescription = (TextView) newsView.findViewById(R.id.tvdescription);
            this.ivPicture = (ImageView) newsView.findViewById(R.id.ivPicture);
            this.lvList = (ListView) newsView.findViewById(R.id.lvList);

        }
    }
}




