package com.example.findatutor.Activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.toolbox.StringRequest;
import com.example.findatutor.Adapters.ChatsAdapter;
import com.example.findatutor.Models.Chat;
import com.example.findatutor.Networking.ApiClient;
import com.example.findatutor.Networking.ApiInterface;
import com.example.findatutor.Networking.Constants;
import com.example.findatutor.R;
import com.example.findatutor.Singleton.MySingleton;
import com.example.findatutor.Singleton.SharedPreferenceManager;
import com.rengwuxian.materialedittext.MaterialEditText;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ChatActivity extends AppCompatActivity {

    /*
    Using the stored ID of recipient, it loads up the required information; profile picture, full name and
    recent messages between the user and recipient. The messages are received via an interface, whilst
    the rest of the information is gathered by a post request. The profile picture is decoded from base64.
    When sending a message, if the text box is not empty, the program trims the message and sends it to
    the database via a post request. The information provided is the user's id, the recipient's id and
    the message. The message is encrypted in sendMessage.php and the timestamp is added in operations.php
    */

    ImageView photo;
    TextView firstName, surname;
    MaterialEditText message;
    ImageButton send;
    Button newSession;
    private ProgressDialog progressDialog;
    private RecyclerView recyclerView;
    private List<Chat> chats;
    private ChatsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        photo = findViewById(R.id.chatPhoto);
        firstName = findViewById(R.id.chatFirstName);
        surname = findViewById(R.id.chatSurname);
        message = findViewById(R.id.chatMessage);
        send = findViewById(R.id.chatSend);
        newSession = findViewById(R.id.chatNewSession);

        recyclerView = findViewById(R.id.chatRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setCanceledOnTouchOutside(false);
        progressDialog.show();

        GetUserDataRequest();
        displayChat();

        final Handler handler = new Handler();
        final int delay = 1000; // 1000 milliseconds
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Log.d("TAG", "run: ");
                displayChat();
                handler.postDelayed(this, delay);
            }
        }, delay);

        send.setOnClickListener(v -> {
            String txtMessage = Objects.requireNonNull(message.getText()).toString();
            if(!TextUtils.isEmpty(txtMessage)){
                sendMessage(txtMessage.trim());
            }
            message.getText().clear();
        });

        newSession.setOnClickListener(v -> startActivity(new Intent(ChatActivity.this, CalendarWeeklyActivity.class)));

    }

    public void GetUserDataRequest() {
        String url = Constants.PARENT_PROFILE_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                String imgUrl = object.getString("Photo");
                String stringFirstName = object.getString("FirstName");
                String stringSurname = object.getString("Surname");

                byte[] decodedString = Base64.decode(imgUrl, Base64.DEFAULT);
                Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                photo.setImageBitmap(decodedByte);

                firstName.setText(stringFirstName);
                surname.setText(stringSurname);
                progressDialog.dismiss();

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }, error -> Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show()) {

            protected Map<String, String> getParams() {
                Map<String, String> param = new HashMap<>();
                param.put("ID", SharedPreferenceManager.getRecipientID());
                return param;
            }
        };

        MySingleton.getmInstance(ChatActivity.this).addToRequestQueue(request);
    }

    public void displayChat(){
        ApiInterface apiInterface = ApiClient.getApiClient().create(ApiInterface.class);
        Call<List<Chat>> call = apiInterface.getChat(SharedPreferenceManager.getID(), SharedPreferenceManager.getRecipientID());
        // Calls getChat function in ApiInterface with the user's id and the recipient's id as parameters
        call.enqueue(new Callback<List<Chat>>() {
            @Override
            public void onResponse(@NonNull Call<List<Chat>> call, @NonNull Response<List<Chat>> response) {
                chats = response.body();
                adapter = new ChatsAdapter(chats, ChatActivity.this);
                recyclerView.setAdapter(adapter);
                recyclerView.scrollToPosition(adapter.getItemCount() - 1);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(@NonNull Call<List<Chat>> call, @NonNull Throwable t) {
                Toast.makeText(ChatActivity.this, "Error on:" + t.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_chat, menu);
        displayChat();
        return super.onCreateOptionsMenu(menu);
    }

    private void sendMessage(String message){
        String url = Constants.SEND_MESSAGE_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            if (response.equals("Error Sending Message")){
                Toast.makeText(ChatActivity.this, response, Toast.LENGTH_SHORT).show();
            }
        }, error -> Toast.makeText(ChatActivity.this, error.toString(), Toast.LENGTH_SHORT).show()){
            @Override
            protected Map<String, String> getParams() {
                HashMap<String, String> param = new HashMap<>();
                param.put("SenderID", SharedPreferenceManager.getID()); // Key, value
                param.put("ReceiverID", SharedPreferenceManager.getRecipientID());
                param.put("Message", message);
                return param; // Posts variables (values) along with their keys in HashMap
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(ChatActivity.this).addToRequestQueue(request);
    }

}
