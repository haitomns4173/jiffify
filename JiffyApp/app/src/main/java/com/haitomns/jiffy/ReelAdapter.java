package com.haitomns.jiffy;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ReelAdapter extends RecyclerView.Adapter<ReelAdapter.ReelViewHolder> {
    private List<Reel> reelList;
    private Context context;

    public ReelAdapter(Context context, List<Reel> reelList) {
        this.context = context;
        this.reelList = reelList;
    }

    @NonNull
    @Override
    public ReelViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.reel_item, parent, false);
        return new ReelViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReelViewHolder holder, int position) {
        Reel reel = reelList.get(position);
        holder.caption.setText(reel.getCaption());
        holder.videoView.setVideoURI(Uri.parse(reel.getVideoUrl()));
        holder.videoView.setOnPreparedListener(mp -> mp.setLooping(true)); // Loop video
        holder.videoView.start();
    }

    @Override
    public int getItemCount() {
        return reelList.size();
    }

    static class ReelViewHolder extends RecyclerView.ViewHolder {
        VideoView videoView;
        TextView caption;

        public ReelViewHolder(@NonNull View itemView) {
            super(itemView);
            videoView = itemView.findViewById(R.id.reelVideoView);
            caption = itemView.findViewById(R.id.reelCaption);
        }
    }
}

