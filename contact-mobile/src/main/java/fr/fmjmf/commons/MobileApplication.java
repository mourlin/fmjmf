package fr.fmjmf.commons;

import android.app.Application;
import android.content.Context;

public class MobileApplication extends Application {
	private static MobileApplication instance;

    public static MobileApplication getInstance() {
        return instance;
    }

    public static Context getContext(){
        return instance.getApplicationContext();
    }

    @Override
    public void onCreate() {
        instance = this;
        super.onCreate();
    }
}
