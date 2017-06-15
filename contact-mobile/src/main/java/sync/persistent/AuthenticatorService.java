package sync.persistent;

import android.accounts.Account;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

/**
 * A bound Service that instantiates the authenticator when started.
 */
public class AuthenticatorService extends Service {
	private static final String TAG = "sync.persistent";
	private static final String ACCOUNT_TYPE = "fmjmf.fr";
	public static final String ACCOUNT_NAME = "mobile-client";

	private Authenticator mAuthenticator;

	@Override
	public void onCreate() {
		Log.i(TAG, "Service created");
		mAuthenticator = new Authenticator(this);
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "Service destroyed");
	}

	/*
	 * When the system binds to this Service to make the RPC call return the
	 * authenticator's IBinder.
	 */
	@Override
	public IBinder onBind(Intent intent) {
		return mAuthenticator.getIBinder();
	}

	public static Account GetAccount() {
		final String accountName = ACCOUNT_NAME;
		return new Account(accountName, ACCOUNT_TYPE);
	}
}
