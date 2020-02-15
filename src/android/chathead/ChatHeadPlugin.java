package io.ionic.chathead;

import android.content.Intent;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaInterface;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import io.ionic.chathead.impl.DemoHoverMenuService;
import io.ionic.chathead.overlay.OverlayPermission;

/**
 * This class echoes a string called from JavaScript.
 */
public class ChatHeadPlugin extends CordovaPlugin {
    private static final int REQUEST_CODE_HOVER_PERMISSION = 1000;

    private boolean mPermissionsRequested = false;

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("showChatHead")) {
            this.showChatHead(callbackContext);
            return true;
        }
        return false;
    }

    public void showChatHead(CallbackContext callbackContext) {
        if (!mPermissionsRequested && !OverlayPermission.hasRuntimePermissionToDrawOverlay(cordova.getActivity())) {
            @SuppressWarnings("NewApi")
            Intent myIntent = OverlayPermission.createIntentToRequestOverlayPermission(cordova.getActivity());
            cordova.getActivity().startActivityForResult(myIntent, REQUEST_CODE_HOVER_PERMISSION);
            callbackContext.success();
        } else {
            DemoHoverMenuService.showFloatingMenu(cordova.getActivity().getApplicationContext());
            callbackContext.success();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (REQUEST_CODE_HOVER_PERMISSION == requestCode) {
            mPermissionsRequested = true;
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }
}
