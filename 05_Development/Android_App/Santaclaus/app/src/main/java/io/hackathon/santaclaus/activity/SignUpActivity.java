package io.hackathon.santaclaus.activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.gotev.uploadservice.MultipartUploadRequest;
import net.gotev.uploadservice.UploadNotificationConfig;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.model.Result;
import io.hackathon.santaclaus.model.User;
import io.hackathon.santaclaus.util.Constants;

public class SignUpActivity extends AppCompatActivity {

    private Button buttonChoose;
    private Button buttonUpload;
    private EditText editText;

    //Image request code
    private int PICK_IMAGE_REQUEST = 1;

    //storage permission code
    private static final int STORAGE_PERMISSION_CODE = 123;

    // Uri to store the image uri
    private Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        editText = (EditText) findViewById(R.id.editTextName);
        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);

        // Choose button
        buttonChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        // Upload button
        buttonUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadMultipart();
            }
        });
    }

    /*
    * This is the method responsible for image upload
    * We need the full image path and the name for the image in this method
    * */
    public void uploadMultipart() {
        //getting name for the image
        String name = editText.getText().toString().trim();

        //getting the actual path of the image
        String path = getPath(filePath);

        //Uploading code
        try {
            String uploadId = UUID.randomUUID().toString();

            //Creating a multi part request
            new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .startUpload(); //Starting the upload

        } catch (Exception exc) {
            Toast.makeText(this, exc.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            /*
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
            */
        }

    }

    //method to get the file path from uri
    public String getPath(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        String document_id = cursor.getString(0);
        document_id = document_id.substring(document_id.lastIndexOf(":") + 1);
        cursor.close();

        cursor = getContentResolver().query(
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null, MediaStore.Images.Media._ID + " = ? ", new String[]{document_id}, null);
        cursor.moveToFirst();
        String path = cursor.getString(cursor.getColumnIndex(MediaStore.Images.Media.DATA));
        cursor.close();

        return path;
    }

    //Requesting permission
    private void requestStoragePermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED)
            return;

        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
            //If the user has denied the permission previously your code will come to this block
            //Here you can explain why you need this permission
            //Explain here why you need this permission
        }
        //And finally ask for the permission
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
    }

    //This method will be called when the user will tap on allow or deny
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        //Checking the request code of our request
        if (requestCode == STORAGE_PERMISSION_CODE) {

            //If permission is granted
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Displaying a toast
                Toast.makeText(this, "Permission granted now you can read the storage", Toast.LENGTH_LONG).show();
            } else {
                //Displaying another toast if permission is not granted
                Toast.makeText(this, "Oops you just denied the permission", Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * Calculate age
     *
     * @param birthDate
     * @return
     */
    public static int getDiffYears(Date birthDate) {
        Date now = new Date();
        Calendar a = getCalendar(birthDate);
        Calendar b = getCalendar(now);
        int diff = b.get(Calendar.YEAR) - a.get(Calendar.YEAR);
        if (a.get(Calendar.MONTH) > b.get(Calendar.MONTH) ||
                (a.get(Calendar.MONTH) == b.get(Calendar.MONTH) && a.get(Calendar.DATE) > b.get(Calendar.DATE))) {
            diff--;
        }
        return diff;
    }

    /**
     * Get calendar
     *
     * @param date
     * @return
     */
    public static Calendar getCalendar(Date date) {
        Calendar cal = Calendar.getInstance(Locale.JAPAN);
        cal.setTime(date);
        return cal;
    }

    /**
     * Post json data to server
     *
     * @param url
     * @param json
     * @return
     */
    public String makePOSTRequest(String url, String json) {
        String response = "";

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            // 5. set json to StringEntity
            StringEntity se = new StringEntity(json);
            httpPost.setHeader("Accept", "application/json");
            httpPost.setHeader("Content-type", "application/json");
            // 6. set httpPost Entity
            httpPost.setEntity(se);
            HttpResponse httpResponse = httpClient.execute(httpPost);
            HttpEntity httpEntity = httpResponse.getEntity();
            response = EntityUtils.toString(httpEntity);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return response;

    }

    /**
     * Sign Up
     *
     * @param v
     */
    public void signup(View v) {
        // Insert user
        TextView emailView = (TextView) findViewById(R.id.email);
        TextView passwordView = (TextView) findViewById(R.id.password);
        TextView nameView = (TextView) findViewById(R.id.name);
        TextView birthdayView = (TextView) findViewById(R.id.birthday);
        TextView addressView = (TextView) findViewById(R.id.address);
        String email = emailView.getText().toString();
        String password = passwordView.getText().toString();
        String name = nameView.getText().toString();
        String address = addressView.getText().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date birthDate = null;
        String birthday = null;
        int age = 0;
        try {
            birthDate = sdf.parse(birthdayView.getText().toString());
            age = getDiffYears(birthDate);
            birthday = sdf.format(birthDate);
        } catch (ParseException e) {
        }
        int type = getType(age);
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        user.setName(name);
        user.setBirthday(birthday);
        user.setAddress(address);
        // TODO: upload image
//        user.setAvatarPath(filePath.toString());
        user.setAvatarPath(null);
        user.setType(type);
        final Gson gson = new Gson();
        final String user_string = gson.toJson(user);

        // Call API
        Thread thread = new Thread(new Runnable() {

            @Override
            public void run() {
                try  {
                    String result_string = makePOSTRequest(Constants.CREATE_USER_URL, user_string);
                    Type resultType = new TypeToken<Result>() {}.getType();
                    Result result = gson.fromJson(result_string, resultType);
                    if (Constants.INSERT_RESULT_CODE_SUCCESS != result.getResultCode()) {
                        return;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();

        // Go to next page
        if (Constants.USER_TYPE_CHILD == type) {
            // Go to Message
            Intent intent = new Intent(this, MessageActivity.class);
            startActivity(intent);
        } else if (Constants.USER_TYPE_PARENT == type) {
            // Go to Child
            Intent intent = new Intent(this, ChildActivity.class);
            startActivity(intent);
        }
    }

    /**
     * Get type belong to age
     *
     * @param age
     * @return
     */
    private int getType(int age) {
        int type;
        if (age < Constants.GROWN_UP_AGE) {
            type = Constants.USER_TYPE_CHILD;
        } else {
            type = Constants.USER_TYPE_PARENT;
        }
        return type;
    }
}
