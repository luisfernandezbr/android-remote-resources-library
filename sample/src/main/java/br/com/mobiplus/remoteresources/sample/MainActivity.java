package br.com.mobiplus.remoteresources.sample;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.widget.TextView;

import br.com.mobiplus.remoteresources.RemoteTextView;

public class MainActivity extends ActionBarActivity {

    RemoteTextView mRemoteTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRemoteTextView = RemoteTextView.with(getApplicationContext());

        TextView textView = (TextView) findViewById(R.id.textTitle);
        mRemoteTextView.setText(textView, R.string.arrl_text_title, 30, 60);
        //textView.setText(getString(R.string.ars_text_title, 30, 60));

        TextView textDescription = (TextView) findViewById(R.id.textDescription);
        mRemoteTextView.setText(textDescription, R.string.arrl_text_description);
        //textDescription.setText(R.string.ars_text_description);
    }
}
