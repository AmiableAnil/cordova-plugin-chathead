package io.ionic.chathead;

import android.content.Intent;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.json.JSONArray;
import org.json.JSONException;

import io.ionic.chathead.impl.DemoHoverMenuService;
import io.ionic.chathead.impl.DevconData;
import io.ionic.chathead.overlay.OverlayPermission;

/**
 * This class echoes a string called from JavaScript.
 */
public class ChatHeadPlugin extends CordovaPlugin {
    private static final int REQUEST_CODE_HOVER_PERMISSION = 1000;

    private boolean mPermissionsRequested = false;
    private int isPermissionPageOpened = 0;
    private DevconData dData;
    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("showChatHead")) {
            this.showChatHead(args, callbackContext);
            return true;
        }
        return false;
    }

    public void showChatHead(JSONArray args, CallbackContext callbackContext) throws JSONException {
        String identifier = args.getString(1);
        String did = args.getString(2);
        String profileId = args.getString(3);
        String studentId = args.getString(4);
        String stallId = args.getString(5);
        String ideaId = args.getString(6);
        String sid = args.getString(7);
        String type = args.getString(8);
        dData = new DevconData(identifier);
        dData.setDid(did);
        dData.setProfileId(profileId);
        dData.setStudentId(studentId);
        dData.setStallId(stallId);
        dData.setIdeaId(ideaId);
        dData.setSid(sid);
        dData.setType(type);
        if (!mPermissionsRequested && !OverlayPermission.hasRuntimePermissionToDrawOverlay(cordova.getActivity())) {
            @SuppressWarnings("NewApi")
            Intent myIntent = OverlayPermission.createIntentToRequestOverlayPermission(cordova.getActivity());
            isPermissionPageOpened = -1;
            cordova.getActivity().startActivityForResult(myIntent, REQUEST_CODE_HOVER_PERMISSION);
            callbackContext.success();
        } else {
            DemoHoverMenuService.showFloatingMenu(cordova.getActivity().getApplicationContext(), dData);
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

    @Override
    public void onResume(boolean multitasking) {
        super.onResume(multitasking);
        if(isPermissionPageOpened == -1){
            DemoHoverMenuService.showFloatingMenu(cordova.getActivity().getApplicationContext(), dData);
            isPermissionPageOpened = 0;
        }
    }
}
