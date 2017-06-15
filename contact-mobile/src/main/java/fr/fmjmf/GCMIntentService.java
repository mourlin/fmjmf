/*
 * Copyright 2012 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fr.fmjmf;

import java.net.MalformedURLException;
import java.net.URL;

import com.google.android.gcm.GCMBaseIntentService;

import android.app.IntentService;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;

/**
 * Receives information from the cloud Project name: gcm-hub-exchange Project
 * ID: 423649276134 API Key : AIzaSyDMO98kjWIAwbYA6Qjj1hgGLh17rG7aVdU
 * {@link IntentService} responsible for handling GCM messages.
 */
public class GCMIntentService extends GCMBaseIntentService {
	private static final String projectNumber = "423649276134";

	private static final String TAG = "fr.fmjmf";
	protected String backupUrl;

	public GCMIntentService() {
		super(projectNumber);
		backupUrl = "";
		Log.d(TAG, "GCMIntentService a backupUrl");
		System.out.println("GCMIntentService a backupUrl");
	}

	@Override
	protected void onError(Context arg0, String arg1) {
		Log.d(TAG, "onError on backupUrl");
	}

	@Override
	protected void onMessage(Context ctxt, Intent lIntentRecu) {
		backupUrl = lIntentRecu.getStringExtra("backup-url");
		Log.d("sync.persistent", "Received a backupUrl " + backupUrl);
		
		StaticClass.getHandler().post(new Runnable() {
			public void run() {
				StaticClass.getInfo().setText(backupUrl);
				SharedPreferences prefs = getSharedPreferences("bdPref", Context.MODE_PRIVATE);
	            SharedPreferences.Editor editor = prefs.edit();
	            editor.putString("backupUrl", backupUrl);
	            editor.commit();
				StaticClass.setBackupURL(backupUrl);
				Log.d("sync.persistent", "setBackupURL(" + backupUrl +")");
				StaticClass.getNewContact().setEnabled(true);
				StaticClass.getRegNumber().invalidate();
				// mise à jour la BD distante de contacts
				// Pass the settings flags by inserting them in a bundle
				try {
					URL url	= new URL(backupUrl);
					Bundle settingsBundle = new Bundle();
					settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_MANUAL, true);
					settingsBundle.putBoolean(ContentResolver.SYNC_EXTRAS_EXPEDITED, true);
					/*
					 * Request the sync for the default account, authority, and
					 * manual sync settings
					 */
					Log.i("sync.persistent", "ContentResolver.requestSync()");
					ContentResolver.requestSync(FromCloudActivity.getAccount(), "fmjmf.fr", settingsBundle);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		});
	}

	@Override
	protected void onRegistered(Context ctxt, String regId) {
		final String id	= regId;
		Log.d(TAG, "onRegistered a backupUrl");
		StaticClass.getHandler().post(new Runnable() {
			public void run() {
				StaticClass.getRegNumber().setText(id);
				StaticClass.getPublishRegIdBtn().setEnabled(true);
			}
		});
	}

	@Override
	protected void onUnregistered(Context ctxt, String regId) {
		Log.d(TAG, "onUnregistered a backupUrl");
	}
}
