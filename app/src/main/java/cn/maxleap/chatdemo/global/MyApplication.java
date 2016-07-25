package cn.maxleap.chatdemo.global;

import android.app.Application;

import com.maxleap.GetCallback;
import com.maxleap.MLDataManager;
import com.maxleap.MLObject;
import com.maxleap.MLQQUtils;
import com.maxleap.MLWeiboUtils;
import com.maxleap.MaxLeap;
import com.maxleap.exception.MLException;
public class MyApplication extends Application{


    private  String appId="577dfbb9169e7d00010850c2";
    private  String clientId="NUlLTExlMFluWFdadXdrOEF5MXNSQQ";
    private  String qqId="1105558294";
    private  String weiboId="2328234403";
    private  String weiboSecretId="5c2086f282fc9128180362f385f5af6f";

    @Override
    public void onCreate() {
        super.onCreate();

        MaxLeap.initialize(this,appId,clientId,MaxLeap.REGION_CN);
        MLQQUtils.initialize(qqId);
        MLWeiboUtils.initialize(weiboId,weiboSecretId);
        MLDataManager.fetchInBackground(MLObject.createWithoutData("foo", "bar"),
                new GetCallback<MLObject>() {
                    @Override
                    public void done(MLObject mlObject, MLException e) {
                        if (e != null && e.getCode() == MLException.INVALID_OBJECT_ID) {
                            System.out.println("MaxLeap:"+ "SDK 成功连接到你的云端应用！");
                        } else {
                            System.out.println("code"+e.getCode());
                            System.out.println("MaxLeap"+ "应用访问凭证不正确，请检查。");
                        }
                    }
                });

    }
}
