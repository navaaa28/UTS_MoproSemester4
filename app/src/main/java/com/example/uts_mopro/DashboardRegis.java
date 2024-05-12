package com.example.uts_mopro;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class DashboardRegis extends AppCompatActivity {
    private TextView idwelcome, idemail, idgender, idpassword, idbirthdatePicker, idaddressText, idShare;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);

        // Inisialisasi UI
        idwelcome = findViewById(R.id.idwelcome);
        idemail = findViewById(R.id.idemail);
        idgender = findViewById(R.id.idgender);
        idpassword = findViewById(R.id.idpassword);
        idbirthdatePicker = findViewById(R.id.idbirthdatePicker);
        idaddressText = findViewById(R.id.idaddressText);
        idShare = findViewById(R.id.idShare); // ID TextView untuk tombol share

        // Menerima data dari Intent
        Intent intent = getIntent();
        if (intent != null) {
            String name = intent.getStringExtra("NAME");
            String email = intent.getStringExtra("EMAIL");
            String gender = intent.getStringExtra("GENDER");
            String password = intent.getStringExtra("PASSWORD");
            String birthdate = intent.getStringExtra("BIRTHDATE");
            String address = intent.getStringExtra("ADDRESS");

            // Menampilkan data pada TextView
            idwelcome.setText("Welcome, " + name);
            idemail.setText(email);
            idgender.setText(gender);
            idpassword.setText(password);
            idbirthdatePicker.setText(birthdate);
            idaddressText.setText(address);

            // Menambahkan onClickListener ke TextView email
            idemail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent implicit untuk membuka aplikasi email
                    Intent emailIntent = new Intent(Intent.ACTION_SENDTO);
                    emailIntent.setData(Uri.parse("mailto:" + email));
                    emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
                    emailIntent.putExtra(Intent.EXTRA_TEXT, "Body");
                    if (emailIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(emailIntent);
                    }
                }
            });

            // Menambahkan onClickListener ke TextView address
            idaddressText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Intent implicit untuk membuka Google Maps
                    Uri gmmIntentUri = Uri.parse("geo:0,0?q=" + Uri.encode(address));
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
                        startActivity(mapIntent);
                    }
                }
            });

            // Menambahkan onClickListener ke TextView share
            idShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Membuat pesan yang ingin dibagikan
                    String pesannich = "Hello! I am sharing my information:\n" +
                            "Name: " + name + "\n" +
                            "Email: " + email + "\n" +
                            "Gender: " + gender + "\n" +
                            "Birthdate: " + birthdate + "\n" +
                            "Address: " + address;

                    // Intent untuk berbagi teks
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, "My Information"); // Subjek pesan
                    shareIntent.putExtra(Intent.EXTRA_TEXT, pesannich); // Isi pesan

                    // Menampilkan dialog berbagi
                    startActivity(Intent.createChooser(shareIntent, "Share via"));
                }
            });
        }
    }
}
