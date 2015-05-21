package br.com.mobiplus.remoteresources;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.widget.TextView;

/**
 * Created by luis.fernandez on 5/6/15.
 */
public class RemoteTextView {

    private Context context;
    private RemoteString remoteString;

    RemoteTextView(Context context) {
        this.context = context;
        this.remoteString = new RemoteString(context);
    }

    public void setText(@NonNull TextView textView, @StringRes int stringId) {
        textView.setText(remoteString.getString(stringId));
    }

    public void setText(@NonNull TextView textView, @StringRes int stringId, Object... formatArgs) {
        textView.setText(remoteString.getString(stringId, formatArgs));
    }

    public CharSequence getString(@StringRes int stringId) {
        return remoteString.getString(stringId, null);
    }

    public CharSequence getString(@StringRes int stringId, Object... formatArgs) {
        return remoteString.getString(stringId, formatArgs);
    }

    static volatile RemoteTextView singleton = null;

    public static RemoteTextView with(Context context) {
        if (singleton == null) {
            synchronized (RemoteTextView.class) {
                if (singleton == null) {
                    singleton = new Builder(context).build();
                }
            }
        }
        return singleton;
    }

    public static class Builder {
        private final Context context;

        public Builder(Context context) {
            if (context == null) {
                throw new IllegalArgumentException("Context must not be null.");
            }
            this.context = context.getApplicationContext();
        }

        public RemoteTextView build() {
            return new RemoteTextView(this.context);
        }
    }

}
