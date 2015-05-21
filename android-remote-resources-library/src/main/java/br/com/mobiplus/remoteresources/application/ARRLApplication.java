package br.com.mobiplus.remoteresources.application;

import android.app.Application;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;

import com.parse.FindCallback;
import com.parse.Parse;
import com.parse.ParseException;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import br.com.mobiplus.remoteresources.util.PreferenceUtils;


/**
 * Created by luis.fernandez on 5/3/15.
 */
public class ARRLApplication {
    private static final String TAG = "ARRLApplication";

    private static final String METADATA_PARSE_APPLICATION_ID = "arrl_parse_applicationId";
    private static final String METADATA_PARSE_CLIENT_KEY = "arrl_parse_clientKey";

    private static final String PARSE_TABLE_NAME = "RemoteString";

    public static void delegate(@NonNull final Application application)  {
        String applicationId;
        String clientKey;

        Bundle bundle = getBundle(application);

        if (bundle == null) {
            //TODO see the docs
            throw new RuntimeException("You must config meta-data tags on your AndroidManifest.xml. See the docs at http://adskasd");
        }

        applicationId = bundle.getString(METADATA_PARSE_APPLICATION_ID);
        clientKey = bundle.getString(METADATA_PARSE_CLIENT_KEY);

        if (TextUtils.isEmpty(applicationId)) {
            throw new RuntimeException("You must config meta-data tag 'arrl_parse_applicationId' on your AndroidManifest.xml");
        }

        if (TextUtils.isEmpty(clientKey)) {
            throw new RuntimeException("You must config meta-data tag 'arrl_parse_clientKey' on your AndroidManifest.xml");
        }

        initParse(application, applicationId, clientKey);
    }

    private static Bundle getBundle(@NonNull Application application) {
        PackageManager packageManager = application.getPackageManager();
        String packageName = application.getPackageName();
        ApplicationInfo applicationInfo = null;

        try {
            applicationInfo = packageManager.getApplicationInfo(packageName, PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return applicationInfo.metaData;
    }

    private static void initParse(@NonNull final Application application, String applicationId, String clientKey) {
        Parse.enableLocalDatastore(application);
        Parse.initialize(application, applicationId, clientKey);

        ParseQuery query = new ParseQuery(PARSE_TABLE_NAME);
        query.findInBackground(new FindCallback() {
            @Override
            public void done(List list, ParseException e) {

            }

            @Override
            public void done(Object o, Throwable throwable) {

                ArrayList<ParseObject> list = (ArrayList<ParseObject>) o;

                for (ParseObject object : list) {
                    //if (BuildConfig.DEBUG) {
                        Log.d(TAG, String.format("RRL: key: %s, value: %s", object.get("key"), object.get("value")));
                    //}
                    PreferenceUtils.savePreference(application, object.getString("key"), object.getString("value"));
                }
            }
        });
    }
}
