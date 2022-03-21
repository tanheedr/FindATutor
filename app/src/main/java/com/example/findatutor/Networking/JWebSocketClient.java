package com.example.findatutor.Networking;

import android.util.Log;

import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_6455;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;

public class JWebSocketClient extends WebSocketClient {

    public JWebSocketClient(URI serverUri) {
        super(serverUri, new Draft_6455());
    }
    @Override
    public void onOpen(ServerHandshake handshakedata) { // When web socket connection open
        Log.d("JWebSocketClient", "onOpen()");
    }
    @Override
    public void onMessage(String message) { // When message received
        Log.d("JWebSocketClient", "onMessage()");
    }
    @Override
    public void onClose(int code, String reason, boolean remote) { // When connection disconnected
        Log.d("JWebSocketClient", "onClose(): " + code + " " + reason);
    }
    @Override
    public void onError(Exception ex) { // When connection error
        Log.d("JWebSocketClient", "onError(): " + ex);
    }
}
