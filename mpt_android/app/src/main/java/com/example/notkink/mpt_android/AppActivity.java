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

import com.example.notkink.mpt_android.auth.Receipt;
import com.example.notkink.mpt_android.receipes.ReceipesAdapter;
import com.example.notkink.mpt_android.toast.Toaster;

import org.joda.time.DateTime;
import org.joda.time.Months;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

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

    private Toaster toaster = new Toaster();
    private TextView monthsLeft;


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
        ImageView imageView = findViewById(R.id.imageView);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.addNewBill);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AppActivity.this, addBill.class));
            }
        });

        findViewById(R.id.refresh).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //refresh recipes
                requestReceipts();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        requestReceipts();
    }

    private void requestReceipts() {
        System.out.println("requestReceipts");
        App.getApp()
                .getBillPleaseApiClient()
                .getReceipts()
                .enqueue(new Callback<List<Receipt>>() {
                    @Override
                    public void onResponse(Call<List<Receipt>> call, Response<List<Receipt>> response) {
                        final List<Receipt> recipes = response.body();
                        if (recipes == null) {
                            showToast("Failed to load receipts, try again later");
                            return;
                        }

                        if (recipes.isEmpty()) {
                            showToast("NO RECEIPTS YET!!!!");
                            return;
                        }

                        System.out.println("receipts size : " + recipes.size());
                        Context context = AppActivity.this;
                        listView.setAdapter(new ReceipesAdapter(recipes, context));
                        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @RequiresApi(api = Build.VERSION_CODES.O)
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {


                                Receipt receipt = recipes.get(i);
                                showToast(receipt + " clicked!");

                            }

                        });


                    }

                    @Override
                    public void onFailure(Call<List<Receipt>> call, Throwable t) {
                        String error = String.valueOf(t);
                        System.err.println("error: " + error);
                        showToast("Failed to load receipts: " + error);
                    }
                });
    }

    private void showToast(String message) {
        toaster.withContext(this).showToast(message);

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


        monthsLeft = findViewById(R.id.monthsLeft);
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
        String guranteeUnit = BillEntriesCointener.billEntries.get(i).getGuaranteeUnit().toString();

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

        int months = 0;

        calendarCurrentDate = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        purchaseDate = BillEntriesCointener.billEntries.get(i).getPurchaseDate();
        purchaseDate.add(Calendar.YEAR, periodofOfGuarantee);

        DateTime start = new DateTime(calendarCurrentDate.getTime());
        DateTime end = new DateTime(purchaseDate.getTime());
        System.out.println("przed przypisaniem month: " + months);
        System.out.println("start: " + start);
        System.out.println("end: " + end);
        months = Months.monthsBetween(start, end).getMonths();

        System.out.println("po przypisaniem month: " + months);
        purchaseDate.add(Calendar.YEAR, -periodofOfGuarantee);


        return String.valueOf(months);

    }

    public String calculateTheDate2(Calendar purchaseDate, int periodofOfGuarantee) {

        int months = 0;

        calendarCurrentDate = Calendar.getInstance();
        simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        purchaseDate.add(Calendar.YEAR, periodofOfGuarantee);

        DateTime start = new DateTime(calendarCurrentDate.getTime());
        DateTime end = new DateTime(purchaseDate.getTime());

        months = Months.monthsBetween(start, end).getMonths();

        purchaseDate.add(Calendar.YEAR, -periodofOfGuarantee);


        return String.valueOf(months);

    }


    public static void start(Context loginActivity) {
        Intent intent = new Intent(loginActivity, AppActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginActivity.startActivity(intent);
    }
}

