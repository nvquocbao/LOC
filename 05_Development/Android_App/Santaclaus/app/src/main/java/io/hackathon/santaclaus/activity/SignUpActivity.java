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
import net.gotev.uploadservice.ServerResponse;
import net.gotev.uploadservice.UploadInfo;
import net.gotev.uploadservice.UploadNotificationConfig;
import net.gotev.uploadservice.UploadStatusDelegate;

import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import io.hackathon.santaclaus.R;
import io.hackathon.santaclaus.model.Result;
import io.hackathon.santaclaus.model.User;
import io.hackathon.santaclaus.util.Constants;
import io.hackathon.santaclaus.task.CreateUserTask;
import io.hackathon.santaclaus.util.Utils;

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

    private String avatarPath;

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

//            uploadReceiver.setDelegate(this);
//            uploadReceiver.setUploadID(uploadId);

            //Creating a multi part request
            String s = new MultipartUploadRequest(this, uploadId, Constants.UPLOAD_URL)
                    .addFileToUpload(path, "image") //Adding file
                    .addParameter("name", name) //Adding text parameter to the request
                    .setNotificationConfig(new UploadNotificationConfig())
                    .setMaxRetries(2)
                    .setDelegate(new UploadStatusDelegate() {
                        @Override
                        public void onProgress(UploadInfo uploadInfo) {
                            // your code here
                        }

                        @Override
                        public void onError(UploadInfo uploadInfo, Exception exception) {
                            // your code here
                        }

                        @Override
                        public void onCompleted(UploadInfo uploadInfo, ServerResponse serverResponse) {
                            // your code here
                            // if you have mapped your server response to a POJO, you can easily get it:
                            // YourClass obj = new Gson().fromJson(serverResponse.getBodyAsString(), YourClass.class);
                            String json = serverResponse.getBodyAsString();
                            Gson gson = new Gson();
                            Type resultType = new TypeToken<Result>() {}.getType();
                            Result result = gson.fromJson(json, resultType);
                            avatarPath = (String) result.getReturnObject();
//                            String fileName = avatarPath.substring(avatarPath.lastIndexOf('/') + 1);
//                            TextView editTextNameView = (TextView) findViewById(R.id.editTextName);
//                            editTextNameView.setText(fileName);
                            Toast.makeText(getApplicationContext(), getString(R.string.signup_upload_msg),
                                    Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(UploadInfo uploadInfo) {
                            // your code here
                        }
                    })
                    .startUpload();//Starting the upload


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
        password = Utils.cryptWithMD5(password);
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
        user.setAvatarPath(avatarPath);
        user.setType(type);
        final Gson gson = new Gson();
        final String user_string = gson.toJson(user);

        // Call API
        String result_string = "";
        try {
            result_string = new CreateUserTask().execute(user_string).get();
        } catch (InterruptedException e) {
        } catch (ExecutionException e) {
        }
        Type resultType = new TypeToken<Result>() {}.getType();
        Result result = gson.fromJson(result_string, resultType);
        if (Constants.INSERT_RESULT_CODE_SUCCESS != result.getResultCode()) {
            return;
        }

        // Get generated userID
        Type userType = new TypeToken<User>() {}.getType();
        User resultUser = gson.fromJson(result.getReturnObject().toString(), userType);

        // Go to next page
        if (Constants.USER_TYPE_CHILD == type) {
            // Go to Message
            Intent intent = new Intent(this, MessageActivity.class);
            intent.putExtra("parentId", resultUser.getId()+"");
            intent.putExtra("parentName", resultUser.getName());
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
