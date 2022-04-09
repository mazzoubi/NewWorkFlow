package mazzoubi.ldjobs.com.newworkflow.Data.Users;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

public class UserInfo {
    public static UserModel getUser(Activity c){
        UserModel userModel = new UserModel();
        SharedPreferences sharedPreferences= c.getSharedPreferences("User", Context.MODE_PRIVATE);
        userModel.setAID(sharedPreferences.getString("AID" ,"") );
        userModel.setName(sharedPreferences.getString("Name" ,"") );
        userModel.setPhone(sharedPreferences.getString("Phone" ,"") );
        userModel.setId(sharedPreferences.getString("Id" ,"") );
        userModel.setToken(sharedPreferences.getString("Token" ,"") );
        userModel.setUsername(sharedPreferences.getString("Username" ,"") );
        userModel.setDebt(sharedPreferences.getString("Debt" ,"") );

        return userModel;
    }
}
