package br.com.mobiplus.remoteresources.sample.application;

import android.app.Application;

import br.com.mobiplus.remoteresources.application.ARRLApplication;


/**
 * Created by luis.fernandez on 5/3/15.
 */
public class AppApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        ARRLApplication.delegate(this);
    }
}
