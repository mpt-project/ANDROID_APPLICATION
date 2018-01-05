package com.example.notkink.mpt_android;

import android.graphics.Bitmap;
import android.media.Image;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Notkink on 20.11.2017.
 */

public class BillEntry {
    private String shopName;
    private String billName;
    private Bitmap photo;
    private Calendar purchaseDate;
    private String date;
    private int guaranteeDuration;
    private GuaranteeUnits guaranteeUnit;
    private String year;
    private String day;
    private String month;
    private String billEntryMonthsLeft;

    public String getShopName() {
        return shopName;
    }
    public String getBillName() {return billName;}

    public void setBillName(String billName){this.billName = billName;}

    public void setShopName(String shopName) {this.shopName = shopName;
    }

    public Bitmap getPhoto() {
        return photo;
    }

    public void setPhoto(Bitmap photo) {
        this.photo = photo;
    }

    public Calendar getPurchaseDate() {
        return purchaseDate;
    }
    public void setPurchaseDate(Calendar purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public int getGuaranteeDuration() {
        return guaranteeDuration;
    }

    public void setGuaranteeDuration(int guaranteeDuration) {
        this.guaranteeDuration = guaranteeDuration;
    }

    public GuaranteeUnits getGuaranteeUnit() {
        return guaranteeUnit;
    }

    public void setGuaranteeUnit(GuaranteeUnits guaranteeUnit) {
        this.guaranteeUnit = guaranteeUnit;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public String getMonth() {
        return month;
    }

    public void setMonth(String month) {
        this.month = month;
    }


    public String getBillEntryMonthsLeft() {
        return billEntryMonthsLeft;
    }

    public void setBillEntryMonthsLeft(String billEntryMonthsLeft) {
        this.billEntryMonthsLeft = billEntryMonthsLeft;
    }
}
