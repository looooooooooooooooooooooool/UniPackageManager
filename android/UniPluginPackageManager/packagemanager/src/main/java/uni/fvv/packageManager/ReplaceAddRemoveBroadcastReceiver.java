package uni.fvv.packageManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import java.util.Objects;

public class ReplaceAddRemoveBroadcastReceiver extends BroadcastReceiver {
    public static final String UPDATE_ACTION = "android.intent.action.PACKAGE_REPLACED";

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onReceive(Context context, Intent intent) {

        if (intent.getAction().equals(UPDATE_ACTION)) {
            String packageName = Objects.requireNonNull(intent.getData()).getEncodedSchemeSpecificPart();
            Log.i("fvvInstall",packageName);
            if (packageName.equals(context.getPackageName())) {

                Log.i("fvvInstall", "更新安装成功 -> " + packageName);

                // 重新启动APP
                Intent intentToStart = context.getPackageManager().getLaunchIntentForPackage(packageName);
                context.startActivity(intentToStart);
            }
        }
    }
}
