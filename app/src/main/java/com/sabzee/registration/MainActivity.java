package com.sabzee.registration;


import android.Manifest;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import de.hdodenhof.circleimageview.CircleImageView;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private final AppCompatActivity activity = MainActivity.this;
    private static final String TAG = MainActivity.class.getSimpleName();

    private static final int INTENT_REQUEST_CODE = 100;

    private static final int INTENT_REQUEST_CODE1 = 101;

    private static final int INTENT_REQUEST_CODE2 = 102;

    public static final String URL = "http://sabzishoppee.in/Vendor/index.php/";

    private static final int PERMISSION_REQUEST_CODE = 100;
    private Button mBtImageSelect;
    private Button mBtImageShow;
    private ProgressBar mProgressBar;
    private String mImageUrl = "";
    private TextInputLayout textInputLayoutFirmName;
    private TextInputLayout textInputLayoutUserName;
    private TextInputLayout textInputLayoutAddress;
    private TextInputLayout textInputLayoutArea;
    private TextInputLayout textInputLayoutCity;
    private TextInputLayout textInputLayoutMobileNum;
    private TextInputLayout textInputLayoutConfirmEmail;

    private TextInputEditText textInputEditFirmName;
    private TextInputEditText textInputEditTextUserName;
    private TextInputEditText textInputEditTextAddress;
    private TextInputEditText textInputEditTextArea;
    private TextInputEditText textInputEditTextCity;
    private TextInputEditText textInputEditTextMobileNum;
    private TextInputEditText textInputEditTextConfirmEmail;

    private TextInputEditText textInputEditTextConfirmPincode;


    private Button appCompatButtonRegister;


    private CircleImageView profileImageView;
    private CircleImageView profileImageView1;
    private CircleImageView profileImageView2;
    File filePath;
    File filePath1;
    File filePath2;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initViews();


        requestPermission();


        initListeners();

    }



    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            Toast.makeText(MainActivity.this, "Write External Storage permission allows us to save files. Please allow this permission in App Settings.", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_REQUEST_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
                case PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Log.e("value", "Permission Granted, Now you can use local drive .");
            } else {
                Log.e("value", "Permission Denied, You cannot use local drive .");
            }
            break;
        }
    }

    /**
     * This method is to initialize views
     */
    private void initViews() {


        textInputLayoutFirmName = (TextInputLayout) findViewById(R.id.textInputLayoutFirmName);
        textInputLayoutUserName = (TextInputLayout) findViewById(R.id.textInputLayoutUsername);
        textInputLayoutAddress =  (TextInputLayout)findViewById(R.id.textInputLayoutAddress);
        textInputLayoutArea =  (TextInputLayout)findViewById(R.id.textInputLayoutArea);
        textInputLayoutCity =  (TextInputLayout)findViewById(R.id.textInputLayoutCity);
        textInputLayoutMobileNum =  (TextInputLayout)findViewById(R.id.textInputLayoutMobileNum);
        textInputLayoutConfirmEmail =  (TextInputLayout)findViewById(R.id.textInputLayoutConfirmEmail);

        textInputEditFirmName = (TextInputEditText) findViewById(R.id.textInputEditTextFirmName);
        textInputEditTextUserName = (TextInputEditText)findViewById(R.id.textInputEditTextUsername);
        textInputEditTextAddress = (TextInputEditText) findViewById(R.id.textInputEditTextAddress);
        textInputEditTextArea =(TextInputEditText) findViewById(R.id.textInputEditTextArea);
        textInputEditTextCity = (TextInputEditText)findViewById(R.id.textInputEditTextCity);
        textInputEditTextMobileNum = (TextInputEditText)findViewById(R.id.textInputEditTextMobileNum);
        textInputEditTextConfirmEmail = (TextInputEditText)findViewById(R.id.textInputEditTextConfirmEmail);
        textInputEditTextConfirmPincode=(TextInputEditText)findViewById(R.id.textInputEditTextpincode);

        profileImageView = (CircleImageView) findViewById(R.id.profileImageView);
        profileImageView1 = (CircleImageView)findViewById(R.id.profileImageView1);
        profileImageView2 = (CircleImageView)findViewById(R.id.profileImageView2);

        appCompatButtonRegister = (Button) findViewById(R.id.appCompatButtonRegister);
        mProgressBar = (ProgressBar) findViewById(R.id.progress);


    }

    /**
     * This method is to initialize listeners
     */
    private void initListeners() {
        appCompatButtonRegister.setOnClickListener(this);

        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);


                }
                callGallery(INTENT_REQUEST_CODE);
            }
        });
        profileImageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                }
                callGallery(INTENT_REQUEST_CODE1);

            }
        });
        profileImageView2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 0);

                }
                callGallery(INTENT_REQUEST_CODE2);

            }
        });


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.appCompatButtonRegister:



                if(TextUtils.isEmpty(textInputEditFirmName.getText())){

                    Toast.makeText(getApplicationContext(),"Enter your Firm Name",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(textInputEditTextUserName.getText())){

                    Toast.makeText(getApplicationContext(),"Enter your User Name",Toast.LENGTH_SHORT).show();
                    return;
                }



                if(TextUtils.isEmpty(textInputEditTextMobileNum.getText())){

                    Toast.makeText(getApplicationContext(),"Enter your mobileno",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(textInputEditTextConfirmEmail.getText())){

                    Toast.makeText(getApplicationContext(),"Enter your Email Id",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(textInputEditTextArea.getText())){

                    Toast.makeText(getApplicationContext(),"Enter your Area",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(textInputEditTextAddress.getText())){

                    Toast.makeText(getApplicationContext(),"Enter your Address",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(TextUtils.isEmpty(textInputEditTextCity.getText())){

                    Toast.makeText(getApplicationContext(),"Enter your City",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(textInputEditTextConfirmPincode.getText())){

                    Toast.makeText(getApplicationContext(),"Enter your Pincode",Toast.LENGTH_SHORT).show();
                    return;
                }



                if(filePath==null){

                    Toast.makeText(getApplicationContext(),"Please Upload User Image",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(filePath1==null){

                    Toast.makeText(getApplicationContext(),"Please Upload Shop Image",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(filePath2==null){

                    Toast.makeText(getApplicationContext(),"Please Upload Proof Image",Toast.LENGTH_SHORT).show();
                    return;
                }



                 uploadImage();

               break;

            case R.id.profileImageView:
               callGallery(INTENT_REQUEST_CODE);
                break;
            case R.id.profileImageView1:
                callGallery(INTENT_REQUEST_CODE1);
                break;
            case R.id.profileImageView2:
                callGallery(INTENT_REQUEST_CODE2);
                break;



        }
    }

    private void callGallery(int requestcode) {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);

        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, requestcode);
        }




    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == INTENT_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {





                try {

                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");


                    Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);


                    Log.e(TAG,""+tempUri);
                    filePath = new File(getRealPathFromURI(tempUri));
                    Log.e(TAG,""+filePath);

                    profileImageView.setImageURI(tempUri);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        else if(requestCode==INTENT_REQUEST_CODE1){
            if (resultCode == RESULT_OK) {


                try {

                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");


                    Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);


                    Log.e(TAG,""+tempUri);
                    filePath1 = new File(getRealPathFromURI(tempUri));
                    Log.e(TAG,""+filePath1);

                    profileImageView1.setImageURI(tempUri);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

        }
        else if(requestCode==INTENT_REQUEST_CODE2){
            if (resultCode == RESULT_OK) {


                try {

                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");


                    Uri tempUri = getImageUri(getApplicationContext(), imageBitmap);


                    Log.e(TAG,""+tempUri);
                    filePath2 = new File(getRealPathFromURI(tempUri));
                    Log.e(TAG,""+filePath2);

                    profileImageView2.setImageURI(tempUri);



                } catch (Exception e) {
                    e.printStackTrace();
                }
            }


        }

    }
    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }



    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
        private void uploadImage() {

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            RetrofitInterface retrofitInterface = retrofit.create(RetrofitInterface.class);


            RequestBody requestFile = RequestBody.create(MediaType.parse("image/jpeg"), filePath);
            RequestBody requestFile1 = RequestBody.create(MediaType.parse("image/jpeg"), filePath1);
            RequestBody requestFile2 = RequestBody.create(MediaType.parse("image/jpeg"), filePath2);


            MultipartBody.Part img1 = MultipartBody.Part.createFormData("app_owner_img", "image.jpg", requestFile);
            MultipartBody.Part img2 = MultipartBody.Part.createFormData("app_Store_img", "image1.jpg", requestFile1);
            MultipartBody.Part img3 = MultipartBody.Part.createFormData("app_doc_img", "image2.jpg", requestFile2);



            String firmname = textInputEditFirmName.getText().toString().trim();
            String username = textInputEditTextUserName.getText().toString().trim();
            String addres = textInputEditTextAddress.getText().toString().trim();
            String area = textInputEditTextArea.getText().toString().trim();
            String cityy = textInputEditTextCity.getText().toString().trim();
            String mobilenum = textInputEditTextMobileNum.getText().toString().trim();
            String email = textInputEditTextConfirmEmail.getText().toString().trim();
            String pincode = textInputEditTextConfirmPincode.getText().toString().trim();





            Call<Response> call = retrofitInterface.uploadImage(
                    RequestBody.create(MediaType.parse("text/plain"), firmname),
                    RequestBody.create(MediaType.parse("text/plain"), username),
                    RequestBody.create(MediaType.parse("text/plain"), email),
                    RequestBody.create(MediaType.parse("text/plain"), mobilenum),
                    RequestBody.create(MediaType.parse("text/plain"), addres),
                    RequestBody.create(MediaType.parse("text/plain"), area),
                    RequestBody.create(MediaType.parse("text/plain"), cityy),
                    RequestBody.create(MediaType.parse("text/plain"), pincode)
                    , img1, img2, img3);
            mProgressBar.setVisibility(View.VISIBLE);
            call.enqueue(new Callback<Response>() {
                @Override
                public void onResponse(Call<Response> call, retrofit2.Response<Response> response) {

                    mProgressBar.setVisibility(View.GONE);

                    if (response.isSuccessful()) {

                        Response responseBody = response.body();





                         textInputEditFirmName.getText().clear();

                        textInputEditTextUserName.getText().clear();
                        textInputEditTextAddress.getText().clear();
                        textInputEditTextArea.getText().clear();
                        textInputEditTextCity.getText().clear();
                        textInputEditTextMobileNum.getText().clear();
                        textInputEditTextConfirmEmail.getText().clear();

                        textInputEditTextConfirmPincode.getText().clear();
                        profileImageView.setImageResource(android.R.color.transparent);

                        profileImageView1.setImageResource(android.R.color.transparent);

                        profileImageView2.setImageResource(android.R.color.transparent);

                        Toast.makeText(getApplicationContext(),responseBody.getMessage(),Toast.LENGTH_LONG).show();

}
                }

                @Override
                public void onFailure(Call<Response> call, Throwable t) {
                    Toast.makeText(getApplicationContext(),t.getMessage().toString(),Toast.LENGTH_LONG).show();
                    mProgressBar.setVisibility(View.GONE);
                    Log.d(TAG, "onFailure: " + t.getLocalizedMessage());
                }
            });
        }

}
