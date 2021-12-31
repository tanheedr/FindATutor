package com.example.findatutor.Activities;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
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
import com.bumptech.glide.Glide;
import com.example.findatutor.Models.Chat;
import com.example.findatutor.Models.Constants;
import com.example.findatutor.Networking.ApiClient;
import com.example.findatutor.Networking.ApiInterface;
import com.example.findatutor.Adapters.ChatsAdapter;
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

    ImageView photo;
    TextView firstName, surname;
    MaterialEditText message;
    ImageButton send;
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

        recyclerView = findViewById(R.id.chatRecyclerView);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        GetUserDataRequest();
        displayChat();

        send.setOnClickListener(v -> {
            String txtMessage = Objects.requireNonNull(message.getText()).toString();
            if(!TextUtils.isEmpty(txtMessage)){
                sendMessage(txtMessage.trim());
            }
            message.getText().clear();
            displayChat();
        });

        for(;;){
            displayChat();
        }
    }

    public void GetUserDataRequest() {
        String url = Constants.PARENT_PROFILE_URL;
        StringRequest request = new StringRequest(Request.Method.POST, url, response -> {
            try {
                JSONObject object = new JSONObject(response);
                String imgUrl = object.getString("Photo");
                String stringFirstName = object.getString("FirstName");
                String stringSurname = object.getString("Surname");
                Glide.with(ChatActivity.this).load(imgUrl).into(photo);

                firstName.setText(stringFirstName);
                surname.setText(stringSurname);

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
        displayChat();
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
                param.put("SenderID", SharedPreferenceManager.getID());
                param.put("ReceiverID", SharedPreferenceManager.getRecipientID());
                param.put("Message", message);
                return param;
            }
        };
        request.setRetryPolicy(new DefaultRetryPolicy(30000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        MySingleton.getmInstance(ChatActivity.this).addToRequestQueue(request);
    }
}
