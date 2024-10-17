package com.probotisop.plugin;

import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.CallbackContext;
import org.json.JSONArray;
import org.json.JSONException;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Handler;
import android.os.Looper;

public class EthernetValidator extends CordovaPlugin {

    private static final String ACTION_VALIDATE_ETHERNET = "validateEthernet";
    private static final int VALIDATION_TIMEOUT_MS = 5000; // 5 seconds timeout

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (ACTION_VALIDATE_ETHERNET.equals(action)) {
            validateEthernet(callbackContext);
            return true;
        }
        return false;
    }

    private void validateEthernet(final CallbackContext callbackContext) {
        cordova.getThreadPool().execute(new Runnable() {
            @Override
            public void run() {
                try {
                    ConnectivityManager connectivityManager = (ConnectivityManager) cordova.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
                    if (connectivityManager == null) {
                        callbackContext.error("ConnectivityManager is null");
                        return;
                    }

                    Network[] networks = connectivityManager.getAllNetworks();
                    for (Network network : networks) {
                        NetworkCapabilities capabilities = connectivityManager.getNetworkCapabilities(network);
                        if (capabilities != null && 
                            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) &&
                            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                            callbackContext.success("Ethernet connection is valid and validated");
                            return;
                        }
                    }
                    callbackContext.error("No valid and validated Ethernet connection found");
                } catch (Exception e) {
                    callbackContext.error("Error validating Ethernet: " + e.getMessage());
                }
            }
        });
    }
}
