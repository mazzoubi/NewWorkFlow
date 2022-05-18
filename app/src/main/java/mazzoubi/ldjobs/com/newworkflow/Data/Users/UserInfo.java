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
        userModel.setType(sharedPreferences.getString("Type" ,"") );

        return userModel;
    }


    public static void logout(Activity c){

        SharedPreferences.Editor editor = c.getSharedPreferences("User", Context.MODE_PRIVATE).edit();
        editor.putString("AID","");
        editor.putString("Name","");
        editor.putString("Phone","");
        editor.putString("Id","");
        editor.putString("Token","");
        editor.putString("Username","");
        editor.putString("Debt","");
        editor.putString("Type","");
        editor.apply();

        c.finish();
    }
}
