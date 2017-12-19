package com.example.notkink.mpt_android;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.notkink.mpt_android.auth.AuthClient;
import com.example.notkink.mpt_android.auth.Receipt;
import com.example.notkink.mpt_android.upload.ImageUploader;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BillViewActivity extends AppCompatActivity {

    private ImageView imageView, deleteIcon, editIcon;
    private TextView nameOfTheShop, guaranteeDate, timeLeft, billName, currentDate, expirationDateText, expirationDate, backSpace ;
    private Calendar calendarCurrentDate;
    private SimpleDateFormat simpleDateFormat;
    private String currentDateString;

    private AuthClient authClient = new AuthClient();

    public void getReceiptDetail(String id) {

        authClient.getRecipes(id).enqueue(new Callback<Receipt>() {
            @Override
            public void onResponse(Call<Receipt> call, Response<Receipt> response) {

            }

            @Override
            public void onFailure(Call<Receipt>call, Throwable t) {

            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill_view);
        imageView = findViewById(R.id.bill_view);
        nameOfTheShop = findViewById(R.id.nameOfTheShop);
        guaranteeDate = findViewById(R.id.guaranteeDate);
        timeLeft = findViewById(R.id.timeLeft);
        billName = findViewById(R.id.bill_name);
        guaranteeDate = findViewById(R.id.guaranteeDate);
        currentDate = findViewById(R.id.timeLeft);
        expirationDateText = findViewById(R.id.exirationDateText);
        expirationDate = findViewById(R.id.expirateDate);
        backSpace = findViewById(R.id.back_button);
        deleteIcon = findViewById(R.id.delete);
        editIcon  = findViewById(R.id.edit);
        calendarCurrentDate = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        currentDateString = simpleDateFormat.format(calendarCurrentDate.getTime());


        byte[] byteArray = getIntent().getByteArrayExtra("billPhoto");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        imageView.setImageBitmap(bmp);

        Bundle extras = getIntent().getExtras();
        String shopName = extras.getString("nameOfTheShop");

        //String date = extras.getString("dateOfPurchase");
        String dateOfPurchase = extras.getString("dateOfPurchaseString");

        String calculatedDays = extras.getString("dateCalculated");
        int timeOfGurantee = extras.getInt("timeOfGurantee");
        String guranteeUnit = extras.getString("guranteeUnit");
        String nameOftheBill = extras.getString("billName");

        Receipt receipt = (Receipt) extras.getSerializable(Receipt.TAG);
        if (receipt != null) {
            getReceiptDetail(receipt.id);
        }


        nameOfTheShop.setText(shopName);
        billName.setText(nameOftheBill);
        guaranteeDate.setText(dateOfPurchase);
        String timeleftString = String.valueOf(timeOfGurantee) + " " + guranteeUnit;
        timeLeft.setText(timeleftString);
        expirationDateText.setText("Twoja gwarancja wygaśnie za ");
        expirationDate.setText(calculatedDays + " miesiące");


        //currentDate.setText(currentDateString);

        //To do
        //Przeslac date nie w spotaci stringu ale w calendar object, odjac date
        //Update: nie przesylac danych w postaci Calendar tlyko w klasie addbill albo appacitivty obliczyc roznice pomiedzy
        //    datami zapisac do string i przeslac

        deleteIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                imageView.setImageDrawable(null);
            }

        });

        editIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(BillViewActivity.this, EditBillActivity.class));
            }

        });

        backSpace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                NavUtils.navigateUpFromSameTask(BillViewActivity.this);
            }

        });




    }
}
