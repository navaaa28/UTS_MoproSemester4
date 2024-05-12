package com.example.uts_mopro;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private EditText idemail, idpassword, idbirthdate, idname, idaddress;

    private ImageView eyeIcon;
    private RadioButton idmale, idfemale;
    private Button idbutton;
    private DatePicker idbirthdatePicker;
    private String selectedDate = "";

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        idemail = findViewById(R.id.idemail);
        idpassword = findViewById(R.id.idpassword);
        idname = findViewById(R.id.idname);
        idmale = findViewById(R.id.idmale);
        idfemale = findViewById(R.id.idfemale);
        idbirthdate = findViewById(R.id.idbirthdate);
        idaddress = findViewById(R.id.idaddress);
        idbutton = findViewById(R.id.idbutton);
        idbirthdatePicker = findViewById(R.id.idbirthdatePicker);
        eyeIcon = findViewById(R.id.id_eye_icon);
        idpassword.setOnTouchListener(new View.OnTouchListener() {
            boolean isPasswordVisible = false;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    if (event.getRawX() >= (idpassword.getRight() - idpassword.getCompoundDrawables()[2].getBounds().width())) {
                        // Touch pada ikon mata
                        if (isPasswordVisible) {
                            // Sembunyikan password
                            idpassword.setInputType(129);
                            eyeIcon.setImageResource(R.drawable.mataoff);
                            isPasswordVisible = false;
                        } else {
                            // Tampilkan password
                            idpassword.setInputType(144);
                            eyeIcon.setImageResource(R.drawable.mataon);
                            isPasswordVisible = true;
                        }
                        // Pindahkan kursor ke akhir teks
                        idpassword.setSelection(idpassword.getText().length());
                        return true;
                    }
                }
                return false;
            }
        });


        idbirthdatePicker.init(Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH),
                new DatePicker.OnDateChangedListener() {
                    @Override
                    public void onDateChanged(DatePicker datePicker, int year, int monthOfYear, int dayOfMonth) {
                        // Simpan tanggal yang dipilih
                        selectedDate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                        // Tetapkan tanggal ke EditText idbirthdate
                        idbirthdate.setText(selectedDate);
                    }
                });

        idbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Ambil nilai dari semua input
                String email = idemail.getText().toString().trim();
                String password = idpassword.getText().toString().trim();
                String name = idname.getText().toString().trim();
                String birthdate = idbirthdate.getText().toString().trim();
                String address = idaddress.getText().toString().trim();
                String gender = idmale.isChecked() ? "Male" : (idfemale.isChecked() ? "Female" : "");

                // Validasi input
                if (TextUtils.isEmpty(email) || TextUtils.isEmpty(password) || TextUtils.isEmpty(name) ||
                        TextUtils.isEmpty(birthdate) || TextUtils.isEmpty(address) || TextUtils.isEmpty(email) ||
                        TextUtils.isEmpty(gender)) {
                    // Jika ada input yang kosong, tampilkan pesan kesalahan
                    Toast.makeText(MainActivity.this, "Please Fill All!!!.", Toast.LENGTH_SHORT).show();
                } else {
                    // Jika semua input sudah diisi, lakukan validasi tanggal
                    if (!isDateValid(birthdate)) {
                        // Jika tanggal tidak valid, tampilkan pesan kesalahan
                        Toast.makeText(MainActivity.this, "Birthdate must be 10 years ago from today.", Toast.LENGTH_SHORT).show();
                    } else {
                        // Jika semua input valid, proses data
                        // Buat intent untuk berpindah ke DetailActivity
                        Intent intent = new Intent(MainActivity.this, DashboardRegis.class);
                        intent.putExtra("EMAIL", email);
                        intent.putExtra("PASSWORD", password);
                        intent.putExtra("NAME", name);
                        intent.putExtra("GENDER", gender);
                        intent.putExtra("BIRTHDATE", birthdate);
                        intent.putExtra("ADDRESS", address);
                        // Kirim selectedDate sebagai ekstra intent
                        intent.putExtra("SELECTED_DATE", selectedDate);
                        startActivity(intent);
                    }
                }
            }
        });
    }

    // Metode untuk memeriksa apakah tanggal yang dipilih adalah 10 tahun yang lalu dari hari ini
    private boolean isDateValid(String selectedDate) {
        // Mendapatkan tanggal saat ini
        Calendar currentDate = Calendar.getInstance();
        // Mendapatkan tanggal 10 tahun yang lalu dari hari ini
        currentDate.add(Calendar.YEAR, -10);
        // Mendapatkan tanggal dari input birthdate
        String[] dateParts = selectedDate.split("/");
        int day = Integer.parseInt(dateParts[0]);
        int month = Integer.parseInt(dateParts[1]) - 1; // Calendar.MONTH dimulai dari 0
        int year = Integer.parseInt(dateParts[2]);

        // Mendapatkan tanggal dari input birthdate
        Calendar selectedDateCalendar = Calendar.getInstance();
        selectedDateCalendar.set(year, month, day);

        // Memeriksa apakah tanggal yang dipilih adalah 10 tahun yang lalu dari hari ini
        return selectedDateCalendar.before(currentDate);
    }
}
