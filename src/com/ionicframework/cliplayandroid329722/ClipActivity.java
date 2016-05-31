package com.ionicframework.cliplayandroid329722;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipeline;
import com.ionicframework.cliplayandroid329722.adapters.FrescoAdapter;
import com.ionicframework.cliplayandroid329722.holders.FrescoHolder;
import com.ionicframework.cliplayandroid329722.instrumentation.InstrumentedDraweeView;
import com.yqritc.recyclerviewflexibledivider.HorizontalDividerItemDecoration;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xl on 16/5/19.
 */
public class ClipActivity extends Activity {

    private com.ionicframework.cliplayandroid329722.adapters.ImageListAdapter mCurrentAdapter;
    private RecyclerView mRecyclerView;

    private List<String> mImageUrls = new ArrayList<>();

    private String title;

    public boolean isAwayFromHere;

    private static final String TAG = "Cliplay";

    private static final int VERTICAL_ITEM_SPACE = 48;

    protected void onCreate(Bundle savedInstanceState) {

//        int maxM = (int) Runtime.getRuntime().maxMemory();

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActionBar bar = getActionBar();
        bar.setBackgroundDrawable(new ColorDrawable(Color.WHITE));
        bar.setDisplayShowHomeEnabled(false);
        bar.setDisplayUseLogoEnabled(false);
        bar.setTitle("");

        mRecyclerView = (RecyclerView) findViewById(R.id.image_grid);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, 1));
//        mRecyclerView.addItemDecoration(new DividerItemDecoration(this, R.layout.divider));
//        mRecyclerView.addItemDecoration(new VerticalSpaceItemDecoration(VERTICAL_ITEM_SPACE));

        mRecyclerView.addItemDecoration(
                new HorizontalDividerItemDecoration.Builder(this)
                        .color(Color.WHITE)
                        .sizeResId(R.dimen.divider)
//                        .positionInsideItem(true)
//                        .marginResId(R.dimen.leftmargin, R.dimen.rightmargin)
                        .build());

        FLog.setMinimumLoggingLevel(FLog.WARN);
        com.ionicframework.cliplayandroid329722.Drawables.init(getResources());

        if (savedInstanceState != null) {

        }

        mCurrentAdapter = null;

        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
            updateAutoPlay();
            super.onScrollStateChanged(recyclerView, newState);
            }
        });

        isAwayFromHere = false;

        String data = getIntent().getStringExtra("urls");
        setSourceAdapter(data);
    }

    @Override
    protected void onStart() {
        super.onStart();
//        FLog.w("Cliplay", "onStart");
        // The activity is about to become visible.

    }
    @Override
    protected void onResume() {
        super.onResume();
//        FLog.w("Cliplay", "onResume");

        if(isAwayFromHere) updateAutoPlay();
        // The activity has become visible (it is now "resumed").
    }
    @Override
    protected void onPause() {
        super.onPause();
//        FLog.w("Cliplay", "onPause");
        stopAllPlay();
        isAwayFromHere = true;
        // Another activity is taking focus (this activity is about to be "paused").
    }
//    @Override
//    protected void onStop() {
//        super.onStop();
//        FLog.w("Cliplay", "onStop");
//        // The activity is no longer visible (it is now "stopped")
//    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        ImagePipeline imagePipeline = Fresco.getImagePipeline();
        imagePipeline.clearMemoryCaches();
//        mRecyclerView = null;
//        resetAdapter();
//        imagePipeline.clearDiskCaches();
//        FLog.w("Cliplay", "onDestroy");
        // The activity is about to be destroyed.
    }

    public void updateAutoPlay() {
        GridLayoutManager layoutManager = ((GridLayoutManager)mRecyclerView.getLayoutManager());

        int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
        int firstCompletelyVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int lastCompletelyVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();
        int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();

        for(int i = firstVisiblePosition; i < lastVisiblePosition + 1; i++) {
            FrescoHolder vh = (FrescoHolder) mRecyclerView.findViewHolderForLayoutPosition(i);
            InstrumentedDraweeView view = vh.getImageView();
            Animatable animation = view.getController().getAnimatable();

            if (animation != null) {
                if (i < firstCompletelyVisiblePosition) {
                    animation.stop();
                } else if (i <= lastCompletelyVisiblePosition) {
                    if (!animation.isRunning()) {
                        animation.start();
                    }
                } else {
                    animation.stop();
                }
            }
        }
    }

    public void stopAllPlay() {
        GridLayoutManager layoutManager = ((GridLayoutManager)mRecyclerView.getLayoutManager());

        int firstCompletelyVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int lastCompletelyVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();

        if(firstCompletelyVisiblePosition == -1 || lastCompletelyVisiblePosition == -1) return;

        for(int i = firstCompletelyVisiblePosition; i <= lastCompletelyVisiblePosition; i++) {
            FrescoHolder vh = (FrescoHolder) mRecyclerView.findViewHolderForLayoutPosition(i);
            InstrumentedDraweeView view = vh.getImageView();
            Animatable animation = view.getController().getAnimatable();
            if (animation != null) {
                if (animation.isRunning()) {
                    animation.stop();
                }
            }
        }
    }

    public boolean isVisible(int position) {
        GridLayoutManager layoutManager = ((GridLayoutManager)mRecyclerView.getLayoutManager());

        int firstCompletelyVisiblePosition = layoutManager.findFirstCompletelyVisibleItemPosition();
        int lastCompletelyVisiblePosition = layoutManager.findLastCompletelyVisibleItemPosition();

        if(position >= firstCompletelyVisiblePosition && position <= lastCompletelyVisiblePosition) return true;

        return false;
    }

    public static int calcDesiredSize(Context context, int parentWidth, int parentHeight) {
        int orientation = context.getResources().getConfiguration().orientation;
        int desiredSize = (orientation == Configuration.ORIENTATION_LANDSCAPE) ?
                parentHeight / 2 : parentHeight / 3;
        return Math.min(desiredSize, parentWidth);
    }

    private void setSourceAdapter(String data) {
        mImageUrls.clear();
        loadNetworkUrls(data);
        setLoaderAdapter();
    }

    private void loadNetworkUrls(String string_of_json_array) {

        List<String> urls = new ArrayList<>();

        try{
            JSONObject dataJson = new JSONObject(string_of_json_array);
            String url = dataJson.getString("image");
//            title = dataJson.getString("")

            JSONArray array = new JSONArray(url);
            for (int i = 0; i < array.length(); i++) {
                String a = (String)array.get(i);
                urls.add(a);
            }
        }catch(JSONException e){
            e.printStackTrace();
        }

        mImageUrls = urls;

        updateAdapter(mImageUrls);
    }

    private void updateAdapter(List<String> urls) {
        if (mCurrentAdapter != null) {
            mCurrentAdapter.clear();
            if (urls != null) {
                for (String url : urls) {
                    mCurrentAdapter.addUrl(url);
                }
            }
            mCurrentAdapter.notifyDataSetChanged();
        }
    }

    private void setLoaderAdapter () {

        resetAdapter();

//        mPerfListener = new PerfListener();

        mCurrentAdapter = new FrescoAdapter(
                this,
//                mPerfListener,
                com.ionicframework.cliplayandroid329722.configs.imagepipeline.ImagePipelineConfigFactory.getOkHttpImagePipelineConfig(this)
        );

        mRecyclerView.setAdapter(mCurrentAdapter);

        updateAdapter(mImageUrls);
    }

    private void resetAdapter() {
        if (mCurrentAdapter != null) {
            mCurrentAdapter.shutDown();
            mCurrentAdapter = null;
            System.gc();
        }
    }
}
