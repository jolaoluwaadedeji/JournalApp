package com.example.jola.journalapp;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    List<Entry> entries;
    public  UserAdapter(List<Entry> entries){
        this.entries = entries;
    }
    @Override
    public UserAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_row,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserAdapter.ViewHolder holder, int position) {
        holder.title.setText(entries.get(position).getTitle());
        String content = (entries.get(position).getContent());
        holder.content.setText(content.length() <= 20 ? content
                :content.substring(0,20)+"...");
        holder.lastModifiedDate.setText(String.format("Modified: %s"
                ,entries.get(position).getLastModifiedDate()));
    }

    @Override
    public int getItemCount() {
        return entries.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public TextView title;
        public  TextView content;
        public TextView lastModifiedDate;
        public ViewHolder(View itemView){
            super(itemView);
            title = (TextView) itemView.findViewById(R.id.data);
            content = (TextView) itemView.findViewById(R.id.content);
            lastModifiedDate = (TextView) itemView.findViewById(R.id.last_modified_date);

        }
    }
}
