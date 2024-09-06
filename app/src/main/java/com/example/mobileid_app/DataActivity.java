package com.example.mobileid_app;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;


public class DataActivity extends AppCompatActivity {
    private static final String TAG = "DataActivity";
    private ImageView imageNotifications, imageDates, imageAdvertisements;
    private OkHttpClient client;
    LayoutInflater inflater;
    LinearLayout container;
    View popupLayout, otpLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        inflater = LayoutInflater.from(DataActivity.this);
        this.client = new OkHttpClient();
        String stringExtra = getIntent().getStringExtra("EXTRA_OTP_CODE");
        if (stringExtra != null) {
            showPopupNotification(stringExtra);
        }

        // Instantiate ViewPager2
        ViewPager2 pager = findViewById(R.id.pager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new NotificationsFragment());
        fragments.add(new DatesFragment());
        fragments.add(new Data_Fragment());

        // Find the ImageView by its id
        imageDates = findViewById(R.id.imageDates);
        imageNotifications = findViewById(R.id.imageNotifications);
        imageAdvertisements = findViewById(R.id.imageAdvertisement);

        imageNotifications.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the color when touched
                        imageNotifications.setColorFilter(Color.argb(255, 0, 0, 255)); // Red tint
                        pager.setCurrentItem(0);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Restore the original color when released
                        imageNotifications.clearColorFilter();
                        break;
                }
                return true;
            }
        });
        imageDates.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the color when touched
                        imageDates.setColorFilter(Color.argb(255, 0, 0, 255)); // Red tint
                        pager.setCurrentItem(1);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Restore the original color when released
                        imageDates.clearColorFilter();
                        break;
                }
                return true;
            }
        });
        imageAdvertisements.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        // Change the color when touched
                        imageAdvertisements.setColorFilter(Color.argb(255, 0, 0, 255)); // Red tint
                        pager.setCurrentItem(2);
                        break;
                    case MotionEvent.ACTION_UP:
                        // Restore the original color when released
                        imageAdvertisements.clearColorFilter();
                        break;
                }
                return true;
            }
        });
        // Create an instance of your adapter class
        MyPagerAdapter adapter = new MyPagerAdapter(getSupportFragmentManager(), getLifecycle(), this);

        // Set the adapter to the ViewPager2
        pager.setAdapter(adapter);
    }

    @SuppressLint("SetTextI18n")
    private void showPopupNotification(String otpCode) {

        // Create the TextView for the message
        TextView messageTextView = new TextView(this);
        messageTextView.setText("Your OTP code is: " + otpCode);
        messageTextView.setTextSize(20); // Set your desired text size here
        messageTextView.setTypeface(null, Typeface.BOLD); // Make the text bold
        messageTextView.setPadding(50, 50, 50, 50); // Optional: Add some padding to the TextView

        // Create the dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Your OTP code is: " + otpCode);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() { // from class: com.example.sahel_app.DataActivity.6
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

        // Customize the OK button
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(Color.parseColor("#FF6200EE")); // Set your desired color
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setTypeface(Typeface.DEFAULT_BOLD); // Set the text style
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setBackgroundColor(Color.parseColor("#FFBB86FC")); // Set the background color
        dialog.getButton(AlertDialog.BUTTON_POSITIVE).setPadding(20, 10, 20, 10); // Set padding
    }



    /* JADX INFO: Access modifiers changed from: private */
/*
    public void verifyOtpWithServer(String str, AlertDialog alertDialog) {
        this.client.newCall(new Request.Builder().url("http://kuwaithackers.dyndns.org:5000/verifyOtp").post(RequestBody.create("{\"otp\":\"" + str + "\"}", MediaType.parse("application/json; charset=utf-8"))).build()).enqueue(new AnonymousClass9(alertDialog));
    }
*/


}
