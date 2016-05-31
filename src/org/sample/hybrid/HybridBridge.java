package org.sample.hybrid;

import android.content.Context;
import android.content.Intent;

import com.ionicframework.cliplayandroid329722.ClipActivity;
import com.ionicframework.cliplayandroid329722.MainActivity;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

/**
 * Created by hschinsk on 6/18/15.
 */
public class HybridBridge extends CordovaPlugin {

    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        try {
            if (action.equals("showList")) {
                String urls = args.getString(0);
                String showTip = args.getString(1);

                if(cordova.getActivity().getLocalClassName().equals("ClipActivity")) return false;

                Context context = cordova.getActivity().getApplicationContext();
                Intent intent = new Intent(context, ClipActivity.class);
                intent.putExtra("urls", urls);
                intent.putExtra("showTip", showTip);
                cordova.startActivityForResult(this,intent,1);
                callbackContext.success();
                return true;
            }else if (action.equals("showAlert")){
                String title = args.getString(0);
                String desc = args.getString(1);
                boolean clean = args.getBoolean(2);
                ((MainActivity)cordova.getActivity()).showDialog(title, desc, clean);
                callbackContext.success();
                return true;
            }
            callbackContext.error("Invalid action");
            return false;
        } catch(Exception e) {
            System.err.println("Exception: " + e.getMessage());
            callbackContext.error(e.getMessage());
            return false;
        }
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
//        itemsList = data.getStringArrayListExtra("items");
    }
}
