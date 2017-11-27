package com.example.notkink.mpt_android;

import android.Manifest;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notkink.mpt_android.upload.ImageUploader;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import pub.devrel.easypermissions.EasyPermissions;

public class addBill extends AppCompatActivity {

    ImageView imageView;
    ImageView imgTakenPic;
    ImageView pickDate;
    ImageView upload;
    static final int REQUEST_IMAGE_CAPTURE=1;
    public static final int CAMERA_REQUEST_CODE = 10;
    private static final int ACTIVITY_START_CAMERA_APP = 0;
    private String mImageFileLocation = "";
    private String[] galleryPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};
    private Bitmap bitmap;
    private Button buttonAdd;
    private EditText year, shopName, billName;
    private EditText purchaseDate;
    private Spinner unitSpinner;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    private final static int PICK_IMAGE = 100;
    private Uri imgUri;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_add_bill);



        imageView = findViewById(R.id.openCamera);
        imgTakenPic = findViewById(R.id.takenPhoto);

        imageView.setOnClickListener(new imgTakePhotoClicker());
        pickDate = findViewById(R.id.addDate);
        year = findViewById(R.id.year);
        shopName = findViewById(R.id.shopNameFill);
        billName = findViewById(R.id.billNameFill);
        purchaseDate = findViewById(R.id.dateOfPurchaseFill); // pole do wpisanie daty z kalendarza
        unitSpinner = findViewById(R.id.spin);
        buttonAdd = findViewById(R.id.add);
        upload = findViewById(R.id.upload);

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openGallery();
            }
        });






        pickDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Calendar cal = Calendar.getInstance();
                int year = cal.get(Calendar.YEAR);
                int month = cal.get(Calendar.MONTH);
                int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog dialog = new DatePickerDialog( addBill.this, mDateSetListener,
                        year,month,dayOfMonth);
                dialog.show();
            }
        });
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int dayOfMonth) {
                month = month + 1;
                String date = dayOfMonth + "/" + month + "/" + year;
                purchaseDate.setText(date);

            }
        };

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    //Toast.makeText(addBill.this, "Uzupelnij dane", Toast.LENGTH_SHORT).show();

                    addItem(view);
                    NavUtils.navigateUpFromSameTask(addBill.this);
                    //new ImageUploader().uploadImage(toReducedImageSize(bitmap));


            }
        });




    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);

    }


    File createImageFile() throws IOException {

        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "IMAGE_" + timeStamp + "_";
        File storageDirectory = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);

        File image = File.createTempFile(imageFileName,".jpg", storageDirectory);
        mImageFileLocation = image.getAbsolutePath();

        return image;

    }
    void setReducedImageSize() {



        int targetImageViewWidth = imgTakenPic.getWidth();
        int targetImageViewHeight = imgTakenPic.getHeight();

        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        int cameraImageWidth = bmOptions.outWidth;
        int cameraImageHeight = bmOptions.outHeight;

        int scaleFactor = Math.min(cameraImageWidth/targetImageViewWidth, cameraImageHeight/targetImageViewHeight);
        bmOptions.inSampleSize = scaleFactor;
        bmOptions.inJustDecodeBounds = false;

        Bitmap photoReducedSize= BitmapFactory.decodeFile(mImageFileLocation, bmOptions);
        imgTakenPic.setImageBitmap(photoReducedSize);



    }


    public void takePhoto(View view) {

        Intent intent = new Intent();
        intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);

        File photoFile = null;
        try {
            photoFile = createImageFile();
        } catch (IOException e) {
            e.printStackTrace();
        }

       //intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));

       startActivityForResult(intent, ACTIVITY_START_CAMERA_APP);

    }

    private Bitmap toReducedImageSize(@Nullable Bitmap bitmap) {
        if (bitmap == null) {
            return null;
        }
        int targetWidth = imgTakenPic.getWidth();
        int targetHeight = imgTakenPic.getHeight();

        Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, targetWidth, targetHeight, false);

        return scaledBitmap;
    }




    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(resultCode == RESULT_OK && requestCode == PICK_IMAGE){
            imgUri = data.getData();
            imgTakenPic.setImageURI(imgUri);
        }
        if(requestCode == ACTIVITY_START_CAMERA_APP && resultCode == RESULT_OK) {
             //Toast.makeText(this, "Picture taken successfully", Toast.LENGTH_SHORT).show();
             //Bundle extras = data.getExtras();
             //Bitmap photoCapturedBitmap = (Bitmap) extras.get("data");
             //imgTakenPic.setImageBitmap(photoCapturedBitmap);
             //Bitmap photoCapturedBitmap = BitmapFactory.decodeFile(mImageFileLocation);
             //imgTakenPic.setImageBitmap(photoCapturedBitmap);

            if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
                /*Bitmap*/ bitmap = (Bitmap) data.getExtras().get("data");
                imgTakenPic.setImageBitmap(bitmap);
            } else {
                EasyPermissions.requestPermissions(this, "Access for storage",
                        101, galleryPermissions);
            }

//            if (EasyPermissions.hasPermissions(this, galleryPermissions)) {
//                setReducedImageSize();
//            } else {
//                EasyPermissions.requestPermissions(this, "Access for storage",
//                        101, galleryPermissions);
//            }




        }

    }

    public void addItem(View view) {

        GuaranteeUnits unit = null;
        switch(String.valueOf(unitSpinner.getSelectedItem())){
            case "Rok":
                unit = GuaranteeUnits.YEAR;
                break;
            case "Miesiac":
                unit = GuaranteeUnits.MONTH;
                break;
            case "Dzien":
                unit = GuaranteeUnits.DAY;
                break;
        }
        BillEntry be = new BillEntry();
        be.setGuaranteeDuration(Integer.parseInt(year.getText().toString()));
        be.setShopName(shopName.getText().toString());
        be.setBillName(billName.getText().toString());
        be.setGuaranteeUnit(unit);
        be.setPhoto(bitmap);
        BillEntriesCointener.billEntries.add(be);


    }




    class imgTakePhotoClicker implements View.OnClickListener {
        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public void onClick(View view) {
            if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                takePhoto(view);
            } else {
                String[] permissionRequested = {Manifest.permission.CAMERA};
                requestPermissions(permissionRequested, CAMERA_REQUEST_CODE);
            }
        }
    }
}

 /*   @Override
    public void onClick(View v) {
        if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
            invokeCamera();
        } else {
            String[] permissionRequested = {Manifest.permission.CAMERA};
            requestPermissions(permissionRequested, CAMERA_REQUEST_CODE);
        }*/

