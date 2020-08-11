package uni.fvv.packageManager;

import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.taobao.weex.annotation.JSMethod;
import com.taobao.weex.bridge.JSCallback;
import com.taobao.weex.common.WXModule;

import java.util.Iterator;
import java.util.function.BiConsumer;

import uni.fvv.packageManager.util.PackageUtils;
import uni.fvv.packageManager.util.ShellUtils;

public class UniPackageManager extends WXModule {

    @RequiresApi(api = Build.VERSION_CODES.N)
    @JSMethod(uiThread = false)
    public void test(){

    }

    //检查root权限
    @JSMethod(uiThread = false)
    public boolean isRoot(){
        try{
            return ShellUtils.checkRootPermission();
        }catch (Exception e){
          return false;
        }
    }

    //安装
    @JSMethod(uiThread = true)
    public void install(String path,JSCallback jsCallback){
        int resultCode = PackageUtils.install(mWXSDKInstance.getContext(),path);
        JSONObject jsonObject = new JSONObject();
        try{
            if (resultCode != PackageUtils.INSTALL_SUCCEEDED) {
                jsonObject.put("state",false);
                jsCallback.invoke(jsonObject);
                return;
            }
            jsonObject.put("state",true);
            jsCallback.invoke(jsonObject);
        }catch (Exception e){

        }
    }

    //卸载
    @JSMethod(uiThread = true)
    public void uninstall(String packageName,JSCallback jsCallback){
        int resultCode = PackageUtils.uninstall(mWXSDKInstance.getContext(),packageName);
        JSONObject jsonObject = new JSONObject();
        try{
            if (resultCode != PackageUtils.DELETE_SUCCEEDED) {
                jsonObject.put("state",false);
                jsCallback.invoke(jsonObject);
                return;
            }
            jsonObject.put("state",true);
            jsCallback.invoke(jsonObject);
        }catch (Exception e){

        }
    }

    //获取版本号
    @JSMethod(uiThread = false)
    public int getAppVersionCode(){
        return PackageUtils.getAppVersionCode(mWXSDKInstance.getContext());
    }

    //打开app应用信息页面
    @JSMethod(uiThread = false)
    public void startInstalledAppDetails(String packageName){
        PackageUtils.startInstalledAppDetails(mWXSDKInstance.getContext(),packageName);
    }

    //获取当前最顶的app包名
    @JSMethod(uiThread = false)
    public String getTopPackageName(){
        return PackageUtils.getTopPackageName(mWXSDKInstance.getContext());
    }

    //检查app是否运行
    @JSMethod(uiThread = false)
    public Boolean isRunning(String packageName){
        return PackageUtils.isRunning(mWXSDKInstance.getContext(),packageName);
    }

    //检查app是否安装
    @JSMethod(uiThread = false)
    public Boolean checkPackageInstall(String packageName){
        return PackageUtils.checkPackageInstall(mWXSDKInstance.getContext(),packageName);
    }

    //打开app
    @JSMethod(uiThread = false)
    public void openApp(String packageName,JSONObject extra){
        PackageUtils.openApp(mWXSDKInstance.getContext(),packageName,extra);
    }

    //获取app列表
    @JSMethod(uiThread = true)
    public void getAppList(JSCallback jsCallback){
        JSONArray jsonArray = PackageUtils.getAppList(mWXSDKInstance.getContext());
        jsCallback.invoke(jsonArray);
    }


    //执行shell命令
    @JSMethod(uiThread = true)
    public void shell(JSONObject jsonObject,JSCallback jsCallback){

        String command =  SetValue(jsonObject,"command","");
        Boolean isRoot =  SetValue(jsonObject,"root",false);

        ShellUtils.CommandResult res =  ShellUtils.execCommand(command, isRoot,true);

        jsCallback.invoke(JSONObject.toJSON(res));
    }

    public String SetValue(JSONObject object,String key,String defaultValue){
        object = object == null?new JSONObject():object;
        return object.containsKey(key)?object.getString(key):defaultValue;
    }

    public Boolean SetValue(JSONObject object,String key,Boolean defaultValue){
        object = object == null?new JSONObject():object;
        return object.containsKey(key)?object.getBoolean(key):defaultValue;
    }
}
