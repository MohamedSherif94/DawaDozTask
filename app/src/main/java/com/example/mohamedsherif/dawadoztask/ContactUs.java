package com.example.mohamedsherif.dawadoztask;

import android.content.Intent;
import android.net.Uri;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class ContactUs extends AppCompatActivity {

    private ImageView mCallImageView;
    private TextInputLayout mSubjectTextInputLayout;
    private TextInputLayout mBodyTextInputLayout;
    private Button mSendButtton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);
        getSupportActionBar().setTitle("Contact Us");

        bindViews();

        mCallImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callPhone();
            }
        });

        mSendButtton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subject = mSubjectTextInputLayout.getEditText().getText().toString().trim();
                String body = mBodyTextInputLayout.getEditText().getText().toString().trim();
                if ( !TextUtils.isEmpty(subject) && !TextUtils.isEmpty(body)){
                    sendEmailMessage();
                }else {
                    Toast.makeText(ContactUs.this, "Please insert subject and body of the email.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void bindViews() {
        mCallImageView = findViewById(R.id.contact_us_call_image_view);
        mSubjectTextInputLayout = findViewById(R.id.contact_us_subject_text_input_layout);
        mBodyTextInputLayout = findViewById(R.id.contact_us_body_text_input_layout);
        mSendButtton = findViewById(R.id.contact_us_send_button);
    }

    private void callPhone() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:+201099242186"));
        startActivity(intent);
    }

    private void sendEmailMessage() {
        Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts(
                "mailto","mohmed.said.abdelkader@gmail.com", null));
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, mSubjectTextInputLayout.getEditText().getText().toString().trim());
        emailIntent.putExtra(Intent.EXTRA_TEXT, mBodyTextInputLayout.getEditText().getText().toString().trim());
        startActivity(Intent.createChooser(emailIntent, "Send email..."));
    }
}
