package br.com.mobiplus.remoteresources;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;

import br.com.mobiplus.remoteresources.util.PreferenceUtils;


/**
 * Created by luis.fernandez on 5/3/15.
 */
class RemoteString {

    private Context context;

    RemoteString(Context context) {
        this.context = context;
    }

    public CharSequence getString(@StringRes int stringId) {
        return this.getString(stringId, null);
    }

    public CharSequence getString(@StringRes int stringId, Object... formatArgs) {
        return this.getString(this.getResourceName(stringId), formatArgs);
    }

    public CharSequence getString(@NonNull String resName, Object... formatArgs) {
        return String.format(this.getString(resName).toString(), formatArgs);
    }

    public CharSequence getString(@NonNull String resName) {
        String value = PreferenceUtils.getStringPreference(context, resName, null);

        if (value == null) {
            int drawableResourceId = context.getResources().getIdentifier(resName, "string", context.getPackageName());
            value = context.getString(drawableResourceId);
        }

        return value;
    }

    private String getResourceName(@StringRes int stringId) {
        return context.getResources().getResourceEntryName(stringId);
    }
}
