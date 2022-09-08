package com.example.firebase_chat_basic.view.fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Layout;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.DialogFragment;

import com.bumptech.glide.Glide;
import com.example.firebase_chat_basic.R;
import com.example.firebase_chat_basic.databinding.FragmentCustomVideoBinding;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.upstream.DataSource;

import java.util.Objects;


/**
 * [CustomVideoDialogFragment]
 */
public class CustomVideoDialogFragment extends DialogFragment {
    public FragmentCustomVideoBinding fragmentCustomVideoBinding;
    public String videoURL;
    public ExoPlayer exoPlayer;
    public DataSource.Factory factory;

    public CustomVideoDialogFragment(String url, ExoPlayer exoPlayer, DataSource.Factory factory) {
        this.videoURL = url;
        this.exoPlayer = exoPlayer;
        this.factory = factory;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        fragmentCustomVideoBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.fragment_custom_video, null, false);
        fragmentCustomVideoBinding.setCustomVideoFragment(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        MediaSource mediaSource = new ProgressiveMediaSource.Factory(factory)
                .createMediaSource(MediaItem.fromUri(videoURL));
        Log.d("mediaSource 확인", String.valueOf(mediaSource));
        exoPlayer.setMediaSource(mediaSource);
        fragmentCustomVideoBinding.customVideoPlayerView.setPlayer(exoPlayer);
        exoPlayer.prepare();
        builder.setView(fragmentCustomVideoBinding.getRoot());

        return builder.create();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        fragmentCustomVideoBinding = null;
    }
}

