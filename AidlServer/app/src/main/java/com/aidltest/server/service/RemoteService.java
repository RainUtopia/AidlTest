package com.aidltest.server.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;

import com.aidltest.bean.User;
import com.aidltest.server.IRecognizeCallback;
import com.aidltest.server.IUserManager;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yangjh
 */
public class RemoteService extends Service {
    private final String TAG = "RemoteService";
    private IRecognizeCallback mCallback;

    public RemoteService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        IUserManager.Stub stub = new IUserManager.Stub() {
            @Override
            public boolean addUser(User user) throws RemoteException {
                Log.d(TAG, "调用了addUser ：" + user.toString());

                mCallback.onUserRecognized(user);  //回调到客户端
                return false;
            }

            @Override
            public boolean removeUser(String userId) throws RemoteException {
                Log.d(TAG, "调用了removeUser：" + userId);
                return false;
            }

            @Override
            public List<User> getUserList() throws RemoteException {
                return null;
            }

            @Override
            public void setRecognizeCallback(IRecognizeCallback callback) throws RemoteException {
                Log.d(TAG, "调用了setRecognizeCallback");
                mCallback = callback;
            }

            @Override
            public void removeRecognizeCallback(IRecognizeCallback callback) throws RemoteException {

            }
        };

        return stub;
    }
}
