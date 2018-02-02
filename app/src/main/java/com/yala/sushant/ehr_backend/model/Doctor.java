package com.yala.sushant.ehr_backend.model;

/**
 * Created by sushant on 2/2/18.
 */

public class Doctor {


    String email;
    String fullname;
    String digitalId;
    String phoneNo;


    public Doctor(String email, String fullname, String digitalId, String phoneNo) {
        this.email = email;
        this.fullname = fullname;
        this.digitalId = digitalId;
        this.phoneNo = phoneNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getDigitalId() {
        return digitalId;
    }

    public void setDigitalId(String digitalId) {
        this.digitalId = digitalId;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }
}
