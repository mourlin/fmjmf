package fr.fmjmf;

import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

public class StaticClass {
	private static Button newContact;
	private static Handler handler;
	private static String backupURL;
	private static TextView regNumber;
	private static Button publishRegIdBtn;
	private static TextView info;
	private static TextView contactInfo;

	public static Button getNewContact() {
		return newContact;
	}

	public static void setNewContact(Button newContact) {
		StaticClass.newContact = newContact;
	}

	public static Handler getHandler() {
		return handler;
	}

	public static void setHandler(Handler handler) {
		StaticClass.handler = handler;
	}

	public static String getBackupURL() {
		return backupURL;
	}

	public static void setBackupURL(String url) {
		// TODO definir le context
		
		StaticClass.backupURL = url;
		Log.d("sync.persistent", "setBackupURL(" + backupURL + ")");
	}

	public static TextView getRegNumber() {
		return regNumber;
	}

	public static void setRegNumber(TextView regNumber) {
		StaticClass.regNumber = regNumber;
	}

	public static Button getPublishRegIdBtn() {
		return publishRegIdBtn;
	}

	public static void setPublishRegIdBtn(Button publishRegIdBtn) {
		StaticClass.publishRegIdBtn = publishRegIdBtn;
	}

	public static TextView getInfo() {
		return info;
	}

	public static void setInfo(TextView info) {
		StaticClass.info = info;
	}

	public static TextView getContactInfo() {
		return contactInfo;
	}

	public static void setContactInfo(TextView contactInfo) {
		StaticClass.contactInfo = contactInfo;
	}
}
