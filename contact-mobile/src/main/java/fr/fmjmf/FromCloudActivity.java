package fr.fmjmf;

import java.util.Properties;

import com.google.android.gcm.GCMRegistrar;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import fr.fmjmf.commons.AssetsPropertyReader;
import fr.fmjmf.commons.MobileApplication;
import regid.network.CreateRegId;

/**
 * Mobile Android client : 1) it receives its GCM identifier, 2) it saves it
 * through the use of a shared static property of StaticClass //REST service
 * called RegIdService
 */
public class FromCloudActivity extends Activity {
	public static final String TAG = "fr.fmjmf";
	// The authority for the sync adapter's content provider
    public static final String AUTHORITY = "fmjmf.fr";
    // An account type, in the form of a domain name
    public static final String ACCOUNT_TYPE = "fmjmf.fr";
    // The account name
    public static final String ACCOUNT = "fmjmfaccount";
    // Instance fields
    protected static Account mAccount;
	private Button newContact;
	private Button publishRegIdBtn;
	private Properties properties;
	private TextView regNumber;
	private TextView info;

	/** Entry point */
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_from_cloud);
		regNumber = (TextView) findViewById(R.id.regId);
		info = (TextView) findViewById(R.id.info);
		publishRegIdBtn = (Button) findViewById(R.id.publisRegId);
		newContact = (Button) findViewById(R.id.newContact);
		StaticClass.setNewContact(newContact);
		StaticClass.setHandler(new Handler());
		StaticClass.setRegNumber(regNumber);
		StaticClass.setPublishRegIdBtn(publishRegIdBtn);
		StaticClass.setInfo(info);
		AssetsPropertyReader asr = new AssetsPropertyReader(MobileApplication.getContext());
		this.properties = asr.getProperties("config.properties");
		GCMRegistrar.checkDevice(this);
		GCMRegistrar.setRegisterOnServerLifespan(this, 1000);
		GCMRegistrar.checkManifest(this);
		// Create the dummy account
		Log.i(TAG, "creation compte");
        mAccount = createSyncAccount(this);
        Log.i(TAG, "apres creation compte");
	}

	@Override
	protected void onResume() {
		super.onResume();
		final String projectNumber = properties.getProperty("projectNumber");
		GCMRegistrar.register(this, projectNumber);
		GCMRegistrar.getRegistrationId(this);

		// // TODO: transform this part into an AsyncTask instance
		// // Start a backUrlService
		// Component component = new Component();
		// component.getServers().add(Protocol.HTTP, 8182);
		//
		// final Router router = new
		// Router(component.getContext().createChildContext());
		// router.attach("/reverse", BackupUrlService.class);
		// router.attach("/reverse/{backUrl}", BackupUrlService.class);
		// // Attach the sample application.
		// component.getDefaultHost().attach("/restlet", router);
		//
		// TextView info = (TextView)findViewById(R.id.info);
		// try {
		// component.start();
		// info.setText("Backup Service is started at "+component.toString());
		// } catch (Exception e) {
		// info.setText("Pb with backup service");
		// e.printStackTrace();
		// }
	}

	/**
	 * First step : the project called ServiceAnnuaireRegIdDB has to be launched
	 * before
	 */
	public void publisRegId(View v) {
		Log.i(TAG, "Publication du regId via un service REST");
		CreateRegId task = new CreateRegId(publishRegIdBtn, newContact);
		task.execute(StaticClass.getRegNumber().getText().toString());
	}

	/** Second step : the project called ExampleDB has to be launched before */
	public void newContact(View v) {
		Log.i(TAG, "Screen change");
		Intent intent = new Intent(getApplicationContext(), PublierContactActivity.class);
		startActivity(intent);
	}

	/**
	 * Create a new dummy account for the sync adapter
	 *
	 * @param context
	 *            The application context
	 */
	public static Account createSyncAccount(Context context) {
		// Create the account type and default account
		Account newAccount = new Account(ACCOUNT, ACCOUNT_TYPE);
		Log.i(TAG, "createSyncAccount apres newAccount");
		// Get an instance of the Android account manager
		AccountManager accountManager = (AccountManager) context.getSystemService(ACCOUNT_SERVICE);
		Log.i(TAG, "createSyncAccount apres accountManager");
		/*
		 * Add the account and account type, no password or user data If
		 * successful, return the Account object, otherwise report an error.
		 */
		if (accountManager.addAccountExplicitly(newAccount, null, null)) {
			/*
			 * If you don't set android:syncable="true" in in your <provider>
			 * element in the manifest, then call context.setIsSyncable(account,
			 * AUTHORITY, 1) here.
			 */
			Log.i(TAG, "createSyncAccount if");
		} else {
			/*
			 * The account exists or some other error occurred. Log this, report
			 * it, or handle it internally.
			 */
			Log.i(TAG, "createSyncAccount else");
		}
		return newAccount;
	}

	public static Account getAccount() {
		return mAccount;
	}
}