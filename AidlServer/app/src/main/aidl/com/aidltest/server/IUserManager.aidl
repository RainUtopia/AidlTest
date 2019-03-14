// IUserManager.aidl
package com.aidltest.server;

import com.aidltest.bean.User;
import com.aidltest.server.IRecognizeCallback;

interface IUserManager {
    boolean addUser(inout User user);
    boolean removeUser(String userId);
    List<User> getUserList();
    void setRecognizeCallback(IRecognizeCallback callback);
    void removeRecognizeCallback(IRecognizeCallback callback);
}
