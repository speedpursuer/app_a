package com.ionicframework.cliplayandroid329722.Push;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.android.pushservice.PushMessageReceiver;
import com.ionicframework.cliplayandroid329722.ClipActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by xl on 16/5/30.
 */
public class MyPushMessageReceiver extends PushMessageReceiver {
    @Override
    public void onBind(Context context, int errorCode, String appid,
                       String userId, String channelId, String requestId) {
        String responseString = "onBind errorCode=" + errorCode + " appid="
                + appid + " userId=" + userId + " channelId=" + channelId
                + " requestId=" + requestId;
        Log.d(TAG, responseString);

        if (errorCode == 0) {
            Log.d(TAG, "绑定成功");
        }
    }

    @Override
    public void onUnbind(Context context, int i, String s) {

    }

    @Override
    public void onSetTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onDelTags(Context context, int i, List<String> list, List<String> list1, String s) {

    }

    @Override
    public void onListTags(Context context, int i, List<String> list, String s) {

    }

    @Override
    public void onMessage(Context context, String s, String s1) {

    }

    @Override
    public void onNotificationClicked(final Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "通知点击 onNotificationClicked title=\"" + title + "\" description=\""
                + description + "\" customContent=" + customContentString;
        Log.d(TAG, notifyString);

//        if(context.getClass().equals("ClipActivity")) return;

//        Context context = cordova.getActivity().getApplicationContext();
//        Intent intent = new Intent(context.getApplicationContext(), ClipActivity.class);
////        intent.putExtra("urls", "a");
////        intent.putExtra("showTip", "b");
//        context.getApplicationContext().startActivity(intent);

        if (!TextUtils.isEmpty(customContentString)) {
            JSONObject customJson = null;
            try {
                customJson = new JSONObject(customContentString);
                String push_id = null;
                if (!customJson.isNull("push_id")) {
                    push_id = customJson.getString("push_id");
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

            OkHttpClient client = new OkHttpClient();

            Request request = new Request.Builder()
                    .url("http://121.40.197.226:4985/cliplay_prod/post_player_kevin_durant_move_dribble")
                    .build();

            client.newCall(request).enqueue(new Callback() {
                @Override public void onFailure(Call call, IOException e) {
                    e.printStackTrace();
                }

                @Override public void onResponse(Call call, Response response) throws IOException {

                    String responseBody = response.body().string();

                    Intent intent = new Intent();
                    intent.setClass(context.getApplicationContext(), ClipActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    intent.putExtra("urls", responseBody);
                    context.getApplicationContext().startActivity(intent);

//                    JSONObject json = null;
//                    try {
//                        json = new JSONObject(responseBody);
//
//
//                        if (!json.isNull("image")) {
//
//                        }
//                    } catch (JSONException e) {
//                        // TODO Auto-generated catch block
//                        e.printStackTrace();
//                    }


//                    try (ResponseBody responseBody = response.body()) {
//                        if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
//
//                        Headers responseHeaders = response.headers();
//                        for (int i = 0, size = responseHeaders.size(); i < size; i++) {
//                            System.out.println(responseHeaders.name(i) + ": " + responseHeaders.value(i));
//                        }
//
//                        System.out.println(responseBody.string());
//                    }
                }
            });

        }
    }

    @Override
    public void onNotificationArrived(Context context, String title,
                                      String description, String customContentString) {
        String notifyString = "通知到达 onNotificationArrived  title=\"" + title
                + "\" description=\"" + description + "\" customContent="
                + customContentString;
        Log.d(TAG, notifyString);
    }
}
