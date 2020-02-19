/*
 * Copyright 2016 Google Inc. All Rights Reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.ionic.chathead.impl;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.support.annotation.NonNull;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import org.sunbird.app.R;

import io.ionic.chathead.Content;

/**
 * A Hover menu screen that does not take up the entire screen.
 */
public class NonFullscreenContent implements Content {

    private final Context mContext;
    private View mContent;
    private DevconData devconData;

    public NonFullscreenContent(@NonNull Context context, DevconData devconData) {
        this.mContext = context.getApplicationContext();
        this.devconData = devconData;

    }

    @NonNull
    @Override
    public View getView() {
        if (null == mContent) {
            mContent = LayoutInflater.from(mContext).inflate(R.layout.content_non_fullscreen, null);
            Log.i("DevconData", devconData.toString());
            final WebView webView = (WebView) mContent.findViewById(R.id.webview);
            webView.setBackgroundColor(0);
            final FrameLayout frameLayout = (FrameLayout) mContent.findViewById(R.id.layout_frame);
//            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
//                    (int) (getDeviceHeight() * 2.18)));
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    (int) (getDeviceHeight() * 2.18)));
            String queryParams = "identifier="+devconData.getIdentifier()+"&did="+devconData.getDid()+"&profileId="+devconData.getProfileId()
                    +"&studentId="+devconData.getStudentId()+"&stallId="+devconData.getStallId()+"&ideaId="+devconData.getIdeaId()+"&sid="+devconData.getSid()+"&type="+devconData.getType()
                    +"&profileUrl="+devconData.getProfileUrl()+"&name="+devconData.getName();
            Log.i("queryParams", queryParams);
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);
            webView.loadUrl("file:///android_asset/index.html?"+queryParams);

//
//            webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//            webSettings.setLoadWithOverviewMode(true);
//            webView.setWebChromeClient(new WebChromeClient());
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
//                webView.setWebContentsDebuggingEnabled(true);
//            }
        }
        return mContent;
    }

    @Override
    public boolean isFullscreen() {
        return false;
    }

    @Override
    public void onShown() {
        // No-op.
    }

    @Override
    public void onHidden() {
        // No-op.
    }

    public int getDeviceHeight() {
        Display display = ((WindowManager) (mContext.getSystemService(Context.WINDOW_SERVICE))).getDefaultDisplay();
        DisplayMetrics outMetrics = new DisplayMetrics();
        display.getMetrics(outMetrics);

        float density = mContext.getResources().getDisplayMetrics().density;
        float dpHeight = outMetrics.heightPixels / density;
        return (int) dpHeight;
    }
}
