package com.chips.dataclientactions;

import java.util.Observable;

import android.widget.Toast;

import com.chips.dataclient.DataPushClient;
import com.chips.dataclient.XMLDataClient;

public class PushClientToastOnFailureAction implements OnUpdateAction {
    public PushClientToastOnFailureAction(Toast toast) {
        failureToast = toast;
    }

    @Override
    public void doUpdateAction(Observable dataClient, Object data,
            XMLDataClient client) {
        if (!((DataPushClient) client).lastCompletedPushSuccessful()) {
            failureToast.show();
        }
    }

    private Toast failureToast;
}
