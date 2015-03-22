package com.gpplworx.mopac.mopac;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class SearchListAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<SearchResults> listData;
    private LayoutInflater layoutInflater;

    public SearchListAdapter(Context context, ArrayList<SearchResults> listData) {
        this.context = context;
        this.listData = listData;
    }

    @Override
    public int getCount() {
        return listData.size();
    }

    @Override
    public Object getItem(int position) {
        return listData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if(convertView == null){
            layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.search_item,null);
            holder = new ViewHolder();
            holder.title = (TextView) convertView.findViewById(R.id.title);
            holder.author = (TextView) convertView.findViewById(R.id.author);
            holder.book = (ImageView) convertView.findViewById(R.id.book);
            holder.journal = (ImageView) convertView.findViewById(R.id.journal);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.title.setText(listData.get(position).get_title());
        holder.author.setText(listData.get(position).get_author());

        String type = listData.get(position).get_type().toString();
        if(type.equals("book")){
            holder.book.setVisibility(View.VISIBLE);
        }else{
            holder.journal.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    static class ViewHolder{
        TextView title, author;
        ImageView book, journal;
    }
}
