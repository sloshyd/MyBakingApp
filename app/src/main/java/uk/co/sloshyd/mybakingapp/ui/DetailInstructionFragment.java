package uk.co.sloshyd.mybakingapp.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.media.session.MediaButtonReceiver;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import uk.co.sloshyd.mybakingapp.R;
import uk.co.sloshyd.mybakingapp.data.InstructionsData;


/**
 * Created by Darren on 29/01/2018.
 */

public class DetailInstructionFragment extends Fragment implements ExoPlayer.EventListener {
    public static final String TAG = DetailInstructionFragment.class.getSimpleName();
    private ArrayList<InstructionsData> mData;//data object containing information for fragment
    private SimpleExoPlayerView mExoPlayerView;
    private SimpleExoPlayer mExoPlayer;
    private TextView mDescription;
    private ImageView mThumbnail;
    private static MediaSessionCompat mMediaSession;// made static so it can be accessed by inner class
    private PlaybackStateCompat.Builder mStateBuilder;
    private View.OnClickListener mNavigationClickListener;
    private ImageView mNavigateBack;
    private ImageView mNavigateForward;
    private int mPosition;//holds position in the arraylist of the current ingredient
    private boolean mTwoPanes;
    private DetailInstructionFragment mDetailInstructionFragment;


    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeMediaSession();


    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        releasePlayer();
        mMediaSession.setActive(false);// destroy the MediaSession

    }

    public void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();

        }
        mExoPlayer = null;// important because you CANNOT reuse instance of player
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.detail_instruction_fragment, container, false);
        mDetailInstructionFragment = new DetailInstructionFragment();
        mData = getArguments().getParcelableArrayList("instruction");
        mPosition = getArguments().getInt("position");
        mTwoPanes = getArguments().getBoolean("twopanes");
        Log.i(TAG, "Detail Instruction Fragment" + mTwoPanes);
        mThumbnail = rootView.findViewById(R.id.detail_instruction_iv_thumbnail);
        mNavigateBack = rootView.findViewById(R.id.detail_instruction_iv_navigation_back);
        mNavigateBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mPosition >= 0 && mPosition < mData.size())
                    if (mTwoPanes) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detail_instruction_fragment_container, mDetailInstructionFragment)
                                  .commit();

                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.ingredients_fragment_container, mDetailInstructionFragment)
                                .commit();
                    }


                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("instruction", mData);
                int newPosition;
                if (mPosition == 0) {
                    newPosition = 0;
                } else {
                    newPosition = mPosition - 1;
                }
                bundle.putInt("position", newPosition);
                bundle.putBoolean("twopanes", mTwoPanes);
                mDetailInstructionFragment.setArguments(bundle);
                mDetailInstructionFragment.setArguments(bundle);
            }

        });
        mNavigateForward = rootView.findViewById(R.id.detail_instruction_iv_navigation_forward);
        mNavigateForward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mPosition >= 0 && mPosition < mData.size())
                    if (mTwoPanes) {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.detail_instruction_fragment_container, mDetailInstructionFragment)
                                .commit();

                    } else {
                        getActivity().getSupportFragmentManager().beginTransaction()
                                .replace(R.id.ingredients_fragment_container, mDetailInstructionFragment)
                                .commit();
                    }


                Bundle bundle = new Bundle();
                bundle.putParcelableArrayList("instruction", mData);
                int newPosition;
                if (mPosition >= mData.size()) {
                    newPosition = mData.size();
                } else {
                    newPosition = mPosition + 1;
                }
                bundle.putInt("position", newPosition);
                bundle.putBoolean("twopanes", mTwoPanes);
                mDetailInstructionFragment.setArguments(bundle);
                mDetailInstructionFragment.setArguments(bundle);
            }

        });
//set up media player
        mExoPlayerView = rootView.findViewById(R.id.detail_instructions_fragment_media_player);
        if (mData.get(mPosition).getmVideoUrl().isEmpty())

        {
            if (!mData.get(mPosition).getmThumbnail().isEmpty()) {
                //TODO - load thumbnail
            } else {
                //TODO - implement a custom image
            }
        }
        if (!mData.get(mPosition).getmVideoUrl().isEmpty())

        {
            Uri uri = Uri.parse(mData.get(mPosition).getmVideoUrl());
            initializePlayer(uri);
        }
        if (!mData.get(mPosition).

                getmThumbnail().isEmpty())

        {

            Picasso.with(getContext()).load(mData.get(mPosition).getmThumbnail()).into(mThumbnail);//picasso handles incorrect URL or invalid return types
        }

        mDescription = rootView.findViewById(R.id.detail_instruction_tv_description);
        mDescription.setText(mData.get(mPosition).

                getmDescription());

        return rootView;
    }


    public void initializePlayer(Uri sampleUri) {
        if (mExoPlayer == null) {
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);
            mExoPlayerView.setPlayer(mExoPlayer);


            //prepare media
            String userAgent = Util.getUserAgent(getContext(), "MyBakingApp");
            MediaSource mediaSource = new ExtractorMediaSource(sampleUri,
                    new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);//start playing as soon as ready

        }
    }

    @Override
    public void onTimelineChanged(Timeline timeline, Object manifest) {

    }

    @Override
    public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {

    }

    @Override
    public void onLoadingChanged(boolean isLoading) {

    }

    //use method to pass device state to MediaService
    @Override
    public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
        if ((playbackState == ExoPlayer.STATE_READY) && playWhenReady) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PLAYING,
                    mExoPlayer.getCurrentPosition(), 1f);
        } else if ((playbackState == ExoPlayer.STATE_READY)) {
            mStateBuilder.setState(PlaybackStateCompat.STATE_PAUSED,
                    mExoPlayer.getCurrentPosition(), 1f);
        }
        mMediaSession.setPlaybackState(mStateBuilder.build());

    }

    @Override
    public void onPlayerError(ExoPlaybackException error) {

    }

    @Override
    public void onPositionDiscontinuity() {

    }

    //set up MediaSession to handle interaction with other apps or devices.  Need to keep
    //in sync with
    public void initializeMediaSession() {
        //create MediaSession
        mMediaSession = new MediaSessionCompat(getContext(), TAG);
        //set Flags of features to support. MediaButtons are callbacks from external apps or devices TransportControl

        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS |
                        MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS
        );
        //Set MediaButtonReceiver and set to null - This will stop MediaButton from starting
        //MediaSession which is desired behavior in this case.
        mMediaSession.setMediaButtonReceiver(null);
        //Set available actions this is done by creating a PlaybackStateCompt.Builder()
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                        PlaybackStateCompat.ACTION_PLAY |
                                PlaybackStateCompat.ACTION_PAUSE |
                                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS |
                                PlaybackStateCompat.ACTION_PLAY_PAUSE);

        mMediaSession.setPlaybackState(mStateBuilder.build());
        //Set Object to control callbacks mySessionCallbacks is an inner class that implements the appropriate callbacks
        mMediaSession.setCallback(new MySessionCallback());
        //Start the MediaSession - remember to stop MediaSession in on Destroy
        mMediaSession.setActive(true);
    }


    private class MySessionCallback extends MediaSessionCompat.Callback {
        @Override
        public void onPlay() {
            mExoPlayer.setPlayWhenReady(true);//Control the ExoPlayer from within these methods
        }

        @Override
        public void onPause() {
            mExoPlayer.setPlayWhenReady(false);
        }

        @Override
        public void onSkipToPrevious() {
            mExoPlayer.seekTo(0);
        }
    }

    //set up BroadcastReceiver to deal with input from external devices or apps - remember to add to Manifest
    public static class MediaReceiver extends BroadcastReceiver {

        public MediaReceiver() {
        }

        @Override
        public void onReceive(Context context, Intent intent) {
            MediaButtonReceiver.handleIntent(mMediaSession, intent);
        }
    }


}
