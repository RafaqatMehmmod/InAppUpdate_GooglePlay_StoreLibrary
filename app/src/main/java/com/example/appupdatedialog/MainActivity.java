package com.example.appupdatedialog;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.play.core.appupdate.AppUpdateInfo;
import com.google.android.play.core.appupdate.AppUpdateManager;
import com.google.android.play.core.appupdate.AppUpdateManagerFactory;
import com.google.android.play.core.install.InstallStateUpdatedListener;
import com.google.android.play.core.install.model.AppUpdateType;
import com.google.android.play.core.install.model.InstallStatus;
import com.google.android.play.core.install.model.UpdateAvailability;
import com.google.android.play.core.tasks.OnSuccessListener;
import com.google.android.play.core.tasks.Task;

public class MainActivity extends AppCompatActivity {

    private static int UPDATE_CODE=22;
    AppUpdateManager appUpdateManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        inAppUpdate();
    }


    private void inAppUpdate() {
        appUpdateManager= AppUpdateManagerFactory.create(this);
        Task<AppUpdateInfo> task=appUpdateManager.getAppUpdateInfo();

        task.addOnSuccessListener(new OnSuccessListener<AppUpdateInfo>() {
            @Override
            public void onSuccess(AppUpdateInfo appUpdateInfo) {

                //Update aye ha to is ka inder ay
                // 1.Flexible mean user ko option hotee ha no thanks ke r update ke
                // 2.Immediate mean user ko lazmee app update kaenee ha
                if (appUpdateInfo.updateAvailability()== UpdateAvailability.UPDATE_AVAILABLE
                        && appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE))
                {
                    //get Result
                    try {
                        appUpdateManager.startUpdateFlowForResult(appUpdateInfo,AppUpdateType.FLEXIBLE,MainActivity.this,UPDATE_CODE);
                    } catch (IntentSender.SendIntentException e) {
                        e.printStackTrace();
                        Log.i("TAG", "onSuccess: "+e.getMessage());
                    }
                }
            }
        });
        appUpdateManager.registerListener(listener);
    }

    InstallStateUpdatedListener listener= installState ->
    {
        if (installState.installStatus()== InstallStatus.DOWNLOADED)
        {
            popUp();
        }
    };

    private void popUp() {
        Snackbar snackbar=Snackbar.make
                (findViewById(android.R.id.content),
                        "App Update almost done",Snackbar.LENGTH_INDEFINITE);


        snackbar.setAction("Reload",view->appUpdateManager.completeUpdate());
        snackbar.setActionTextColor(getResources().getColor(R.color.appthemeblue_color));
        snackbar.show();




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //For Update Method ........Restart App..........
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == UPDATE_CODE) {
            if (resultCode != RESULT_OK) {

            }
        }
    }

}
