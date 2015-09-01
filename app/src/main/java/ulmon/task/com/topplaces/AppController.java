package ulmon.task.com.topplaces;

import android.app.Application;
import android.content.Context;
import android.text.TextUtils;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

public class AppController extends Application {


    private static AppController sInstance;

    @Override
    public void onCreate(){
        super.onCreate();
        sInstance = this;
    }

    public static synchronized AppController getInstance() {
        return sInstance;
    }
    public static Context getAppContext(){
        return sInstance.getApplicationContext();
    }









}
