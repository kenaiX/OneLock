package cc.kenai.onekeylock;


import android.app.ActivityManager;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceScreen;
import android.view.View;
import android.widget.ImageView;

import com.kenai.function.setting.XSetting;
import com.kenai.function.state.XState;

import java.util.List;

import cc.kenai.common.ad.KenaiTuiguang;
import cc.kenai.common.ad.LoadDialog;
import cc.kenai.common.stores.StoreUtil;

public class SettingActivity extends PreferenceActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (XState.get_isfirst(getBaseContext())) {
            LoadDialog.showDialog(SettingActivity.this);
        }

        addPreferencesFromResource(R.xml.onelock_main);
        load_button_tuijian();
        PreferenceScreen changeicon = (PreferenceScreen) findPreference("changeicon");
        changeicon.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                View icon = View.inflate(SettingActivity.this, R.layout.iconchange, null);
                ImageView image1 = (ImageView) icon.findViewById(R.id.imageButton1);
                ImageView image2 = (ImageView) icon.findViewById(R.id.imageButton2);
                ImageView image3 = (ImageView) icon.findViewById(R.id.imageButton3);

                image1.setImageResource(R.drawable.ic_launcher);
                image2.setImageResource(R.drawable.logo_1);
                image3.setImageResource(R.drawable.logo_2);

                image1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setIcon(0);
                    }
                });
                image2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setIcon(1);
                    }
                });
                image3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        setIcon(2);
                    }
                });

                AlertDialog.Builder builder = new AlertDialog.Builder(SettingActivity.this);
                builder.setView(icon);
                builder.create().show();
                return true;
            }
        });
    }

    final Class[] TEST = new Class[]{LockActivity.ICON1.class, LockActivity.ICON2.class, LockActivity.ICON3.class};

    private void setIcon(int icon) {
        Context ctx = getBaseContext();
        PackageManager pm = ctx.getPackageManager();
        for (int i = 0; i < TEST.length; i++) {
            if (i != icon) {
                ComponentName cn = new ComponentName(ctx, TEST[i]);
                pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);

            }
        }
        ComponentName cn = new ComponentName(ctx, TEST[icon]);
        pm.setComponentEnabledSetting(cn, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);


        ActivityManager am=(ActivityManager)ctx.getSystemService(Context.ACTIVITY_SERVICE);
        // Find launcher and kill it
        Intent i = new Intent(Intent.ACTION_MAIN);
        i.addCategory(Intent.CATEGORY_HOME);
        i.addCategory(Intent.CATEGORY_DEFAULT);
        List<ResolveInfo> resolves = pm.queryIntentActivities(i, 0);
        for (ResolveInfo res : resolves) {
            if (res.activityInfo != null) {
                am.killBackgroundProcesses(res.activityInfo.packageName);
            }
        }
        finish();
    }

    @Override
    public void onPause() {
        super.onPause();
    }


    private final void load_button_tuijian() {
        PreferenceScreen tuijian = (PreferenceScreen) findPreference("tuijian");
        tuijian.setOnPreferenceClickListener(new OnPreferenceClickListener() {
            public boolean onPreferenceClick(Preference preference) {
                KenaiTuiguang.show(SettingActivity.this);
                return true;
            }
        });
        StoreUtil.bindClick(this, findPreference("tuijian_melody"), "a1156e07ad7e4f1bba05014c88b3b98c");
        StoreUtil.bindClick(this, findPreference("tuijian_dream"), "dba0d8630ada406dbab446434568bd32");
        StoreUtil.bindClick(this, findPreference("zhichi"), "e9467040c32d4f3197a2134b66682226");

    }
}
