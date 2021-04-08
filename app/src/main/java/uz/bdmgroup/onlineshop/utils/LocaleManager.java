package uz.bdmgroup.onlineshop.utils;

import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.util.Log;

import com.orhanobut.hawk.Hawk;

import java.util.Locale;

public class LocaleManager {

    /**
     * set current pref locale
     */
    public static Context setLocale(Context mContext) {
        Log.d("prefs", getLanguagePref(mContext));
        return updateResources(mContext, getLanguagePref(mContext));
    }
    /**
     * Set new Locale with context
     */
    public static Context setNewLocale(Context mContext, String language) {
        setLanguagePref(mContext, language);
        return updateResources(mContext, language);
    }
    /**
     * Get saved Locale from SharedPreferences
     *
     * @param mContext current context
     * @return current locale key by default return english locale
     */
    public static String getLanguagePref(Context mContext) {
        return Hawk.get("pref_lang", "en");
    }
    /**
     * set pref key
     */
    private static void setLanguagePref(Context mContext, String localeKey) {
        Hawk.put("pref_lang", localeKey);
    }
    /**
     * update resource
     */
    private static Context updateResources(Context context, String language) {
        Locale locale = new Locale(language);
        Locale.setDefault(locale);

        Resources res = context.getResources();

        //Log.d("prefs", Locale.getDefault().toString() + " >>> " +  language + " >>> " + res.getString(R.string.label_cards));
        Configuration config = new Configuration(res.getConfiguration());
        if (Build.VERSION.SDK_INT >= 17) {
            config.setLocale(locale);
            context = context.createConfigurationContext(config);
            res.updateConfiguration(config, res.getDisplayMetrics());
        } else {
            config.locale = locale;
            res.updateConfiguration(config, res.getDisplayMetrics());
        }
        return context;
    }
    /**
     * get current locale
     */
    public static Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= 24 ? config.getLocales().get(0) : config.locale;
    }
}