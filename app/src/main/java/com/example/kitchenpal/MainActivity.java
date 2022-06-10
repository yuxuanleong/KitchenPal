package com.example.kitchenpal;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    private TextView tvUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //TEST FOR HOME PAGE
        Intent intent = new Intent(this, HomePage.class);
        startActivity(intent);
        finish();

//        //MAIN
//        tvUsername = findViewById(R.id.showUsername);
//
//        FirebaseAuth mAuth = FirebaseAuth.getInstance();
//        if (mAuth.getCurrentUser() == null) {
//            Intent intent = new Intent(this, Login.class);
//            startActivity(intent);
//            finish();
//            return;
//        }
//
//        Button logoutBtn = findViewById(R.id.logoutButton);
//        logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                logoutUser();
//            }
//        });
//
//        FirebaseDatabase db = FirebaseDatabase.getInstance();
//        DatabaseReference ref = db.getReference("Users").child(mAuth.getCurrentUser().getUid());
//        ref.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                User user = snapshot.getValue(User.class);
//                if (user != null) {
//                    tvUsername.setText(user.username);
//                }
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }
//
//    private void logoutUser() {
//        FirebaseAuth.getInstance().signOut();
//        Intent intent = new Intent(this, Login.class);
//        startActivity(intent);
//        finish();
    }
}