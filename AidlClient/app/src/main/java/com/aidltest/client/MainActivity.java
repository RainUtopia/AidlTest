package com.aidltest.client;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.aidltest.bean.User;
import com.aidltest.server.IRecognizeCallback;
import com.aidltest.server.IUserManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yangjh
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final String TAG = "MainActivity";
    private ExecutorService mThreadPool = Executors.newCachedThreadPool();
    private Button mBtnBind;
    private Button mBtnAdd;

    private IUserManager mIUserManager;
    private IRecognizeCallback.Stub callback = new IRecognizeCallback.Stub() {
        @Override
        public void onUserRecognized(User user) throws RemoteException {
            Log.d(TAG, "onUserRecognized user = " + user.toString());
        }
    };

    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mIUserManager = IUserManager.Stub.asInterface(service);
            Log.d(TAG, "RemoteService 连接成功");
            try {
                mIUserManager.setRecognizeCallback(callback);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.e(TAG, "RemoteService 断开");
            mIUserManager = null;
            unbindService(connection);
            bindRemoteService();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mBtnBind = findViewById(R.id.btn_bind);
        mBtnAdd = findViewById(R.id.btn_add);
        mBtnBind.setOnClickListener(this);
        mBtnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v == mBtnBind) {
            bindRemoteService();
        } else if (v == mBtnAdd) {
            if (mIUserManager == null) {
                return;
            }
            User user = new User();
            user.setName("Tony");
            user.setAge(27);
            user.setId("12abc52d");
            user.setImgPath("sdcard/tmp/img/12abc52d.jpg");
            try {
                mIUserManager.addUser(user);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    private void bindRemoteService() {
        mThreadPool.submit(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName("com.aidltest.server", "com.aidltest.server.service.RemoteService"));
                while (true) {
                    boolean isConnect = bindService(intent, connection, Context.BIND_AUTO_CREATE);
                    Log.i(TAG, "绑定远程服务，连接状态: " + isConnect);
                    if (isConnect && mIUserManager != null) {
                        break;
                    } else {
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }
}
