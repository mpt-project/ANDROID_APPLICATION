package com.example.notkink.mpt_android;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.text.format.DateUtils;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.notkink.mpt_android.auth.AuthClient;
import com.example.notkink.mpt_android.auth.Receipt;
import com.example.notkink.mpt_android.receipes.ReceipesAdapter;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import org.joda.time.DateTime;
import org.joda.time.Months;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class AppActivity extends ListActivity {


    public static final int CAMERA_REQUEST_CODE = 10;
    ImageView imageView;
    private Button addPictureButton;
    static final int REQUEST_IMAGE_CAPTURE = 1;
    private Calendar calendarCurrentDate;
    private SimpleDateFormat simpleDateFormat;
    private ListView listView;
    private AuthClient authClient = new AuthClient();


    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_app);
        listView = getListView();
        //requestReceipts();
        ImageView imageView = findViewById(R.id.imageView);


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addNewBill);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppActivity.this, addBill.class));
            }
        });

    }

    private void requestReceipts() {

        authClient.getRecipes().enqueue(new Callback<List<Receipt>>() {
            @Override
            public void onResponse(Call<List<Receipt>> call, Response<List<Receipt>> response) {
                final List<Receipt> recipes = response.body();
                Context context = AppActivity.this;
                listView.setAdapter(new ReceipesAdapter(recipes, context));
                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                        //Receipt receipt = recipes.get(i);
                        onListItemClicked2(i);
                    }

                });
            }

            @Override
            public void onFailure(Call<List<Receipt>> call, Throwable t) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        //fillOptions();
       listView.setAdapter(new CustomArrayAdapter(BillEntriesCointener.billEntries, this));
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                onListItemClicked2(i);
           }

        });
       findViewById(R.id.noEntriesMessage).setVisibility(View.GONE);
       if (BillEntriesCointener.billEntries.isEmpty()) {
           listView.setVisibility(View.GONE);
           findViewById(R.id.noEntriesMessage).setVisibility(View.VISIBLE);
        }


    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void onListItemClicked2(int i) {


        //System.out.println("Clicked position "+i);
        Intent intent = new Intent(AppActivity.this, BillViewActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //zmieniec quality//


        //String date = BillEntriesCointener.billEntries.get(i).getDate();
        //String purchaseDate = receipt.expire;
        BillEntriesCointener.billEntries.get(i).getPhoto().compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar purchaseDate = BillEntriesCointener.billEntries.get(i).getPurchaseDate();
        String nameOfTheShop = BillEntriesCointener.billEntries.get(i).getShopName();
        String purchaseDateString = simpleDateFormat.format(purchaseDate.getTime());
        String billName = BillEntriesCointener.billEntries.get(i).getBillName();
        int periodOfGuarantee = BillEntriesCointener.billEntries.get(i).getGuaranteeDuration();
        //intent.putExtra("dateOfPurchase", receipt.expire);
        String guranteeUnit = BillEntriesCointener.billEntries.get(i).getGuaranteeUnit().toString();
        //intent.putExtra("billName", receipt.name);
        intent.putExtra("nameOfTheShop", nameOfTheShop);
        intent.putExtra("billPhoto", byteArray);
        intent.putExtra("dateOfPurchase", purchaseDate.toString());
        intent.putExtra("dateOfPurchaseString", purchaseDateString);
        intent.putExtra("billName", billName);




        String dateCalculated = calculateTheDate(purchaseDate, i, periodOfGuarantee);
        intent.putExtra("dateCalculated", dateCalculated);
        intent.putExtra("timeOfGurantee", periodOfGuarantee);
        intent.putExtra("guranteeUnit", guranteeUnit);


        startActivity(intent);
    }
    /*private void onListItemClicked(Receipt receipt) {


        //System.out.println("Clicked position "+i);
        Intent intent = new Intent(AppActivity.this, BillViewActivity.class);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        //zmieniec quality//
        String nameOfTheShop = receipt.shop;


        //String date = BillEntriesCointener.billEntries.get(i).getDate();
        //String purchaseDate = receipt.expire;
        BillEntriesCointener.billEntries.get(i).getPhoto().compress(Bitmap.CompressFormat.JPEG, 10, stream);
        byte[] byteArray = stream.toByteArray();
        String purchaseDate = BillEntriesCointener.billEntries.get(i).getPurchaseDate();

        //intent.putExtra("dateOfPurchase", receipt.expire);

        //intent.putExtra("billName", receipt.name);
        intent.putExtra("nameOfTheShop", nameOfTheShop);
        intent.putExtra("billPhoto", byteArray);
        intent.putExtra("dateOfPurchase", purchaseDate);

        intent.putExtra(Receipt.TAG, receipt);


        //   calculateTheDate(purchaseDate, i, 2);


        startActivity(intent);
    }*/

    static String getRelativeDate(long millis) {
        CharSequence timesAgo = DateUtils.getRelativeTimeSpanString(millis);
        return timesAgo.toString();
    }


    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imageView.setImageBitmap(imageBitmap);
        }
        super.onActivityResult(requestCode, resultCode, data);
        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
        imageView.setImageBitmap(bitmap);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public String calculateTheDate(Calendar purchaseDate, int i, int periodofOfGuarantee) {
        // trzeba wziasc liczbe dni i podzielic na miesiace i na lata
        //TODO
   /*     long calculatedDays = 2;
        int months = 0;*/
        calendarCurrentDate = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = simpleDateFormat.format(calendarCurrentDate.getTime());
        System.out.println(currentDate);
        purchaseDate = BillEntriesCointener.billEntries.get(i).getPurchaseDate();

        purchaseDate.add(Calendar.YEAR, periodofOfGuarantee);
        String dateOfPurchasePlusGuarantee = simpleDateFormat.format(purchaseDate.getTime());



        /*Date d1 = null;
        Date d2 = null;*/
        DateTime start = new DateTime(calendarCurrentDate.getTime());
        DateTime end= new DateTime(purchaseDate.getTime());
        int months;
        months = Months.monthsBetween(start, end).getMonths();


/*        try {
            d1 = simpleDateFormat.parse(currentDate);
            d2 = simpleDateFormat.parse(dateOfPurchasePlusGuarantee);
            System.out.println(d1.toString() + "\n" + d2.toString());

            long diff = d2.getTime() - d1.getTime();

            int months = Months.monthBetween(d1, d2).getMonths();
            //calculatedDays = diff / (30 * 24 * 60 * 60 * 1000);





        } catch (Exception e) {
            e.printStackTrace();
        }*/

        return String.valueOf(months);
    }


}