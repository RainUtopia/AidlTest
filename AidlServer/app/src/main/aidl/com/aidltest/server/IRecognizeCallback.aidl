// IRecognizeCallback.aidl
package com.aidltest.server;
import com.aidltest.bean.User;

interface IRecognizeCallback {
    void onUserRecognized(inout User user);
}
