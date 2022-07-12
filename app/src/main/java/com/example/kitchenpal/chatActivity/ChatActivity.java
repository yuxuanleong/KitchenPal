package com.example.kitchenpal.chatActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kitchenpal.R;
import com.google.firebase.database.core.view.View;

import de.hdodenhof.circleimageview.CircleImageView;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView chatRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);

        final ImageButton backBtn = findViewById(R.id.chat_back_button);
        final CircleImageView profilePic = findViewById(R.id.profilePic);
        final TextView userName = findViewById(R.id.username);
        final TextView status = findViewById(R.id.status);
        final EditText chatInput = findViewById(R.id.chat_input);
        final ImageView sendBtn = findViewById(R.id.sendBtn);

        chatRecyclerView = findViewById(R.id.chatRecyclerView);

        //get data from messages adapter class
        final String getName = getIntent().getStringExtra("username");
        final String getProfilepic = getIntent().getStringExtra("profilePic");
    }
}