package com.probotisop.cordova.plugin.ethernetValidator;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.net.NetworkRequest;
import android.util.Log;

import org.apache.cordova.CallbackContext;
import org.apache.cordova.CordovaPlugin;
import org.apache.cordova.PluginResult;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class EthValidator extends CordovaPlugin {

    private static final String TAG = "EthValidator";
    private ConnectivityManager connectivityManager;
    private CallbackContext networkChangeCallback;

    @Override
    public void initialize(CordovaInterface cordova, CordovaWebView webView) {
        super.initialize(cordova, webView);
        connectivityManager = (ConnectivityManager) cordova.getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
    }

    @Override
    public boolean execute(String action, JSONArray args, CallbackContext callbackContext) throws JSONException {
        if (action.equals("startNetworkMonitoring")) {
            this.networkChangeCallback = callbackContext;
            startNetworkMonitoring();
            return true;
        }
        return false;
    }

    private void startNetworkMonitoring() {
        NetworkRequest networkRequest = new NetworkRequest.Builder()
                .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                .build();

        connectivityManager.registerNetworkCallback(networkRequest, new ConnectivityManager.NetworkCallback() {
            @Override
            public void onAvailable(Network network) {
                super.onAvailable(network);
                checkAndSendNetworkStatus();
            }

            @Override
            public void onLost(Network network) {
                super.onLost(network);
                checkAndSendNetworkStatus();
            }

            @Override
            public void onCapabilitiesChanged(Network network, NetworkCapabilities networkCapabilities) {
                super.onCapabilitiesChanged(network, networkCapabilities);
                checkAndSendNetworkStatus();
            }
        });
    }

    private void checkAndSendNetworkStatus() {
        cordova.getThreadPool().execute(new Runnable() {
            public void run() {
                boolean isEthernetConnected = false;
                boolean isEthernetValidated = false;

                Network[] allNetworks = connectivityManager.getAllNetworks();
                for (Network network : allNetworks) {
                    NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                    if (networkCapabilities != null && networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                        isEthernetConnected = true;
                        isEthernetValidated = networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED);
                        break;
                    }
                }

                JSONObject result = new JSONObject();
                try {
                    result.put("isEthernetConnected", isEthernetConnected);
                    result.put("isEthernetValidated", isEthernetValidated);
                } catch (JSONException e) {
                    Log.e(TAG, "Error creating JSON result", e);
                }

                PluginResult pluginResult = new PluginResult(PluginResult.Status.OK, result);
                pluginResult.setKeepCallback(true);
                networkChangeCallback.sendPluginResult(pluginResult);
            }
        });
    }
}