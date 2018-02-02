package com.yala.sushant.ehr_backend;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by sushant on 2/3/18.
 */

public class UtilClass {

    public String getTime(long serverTime) {
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-yyyy   HH:mm");
        return sfd.format(new Date(serverTime));
    }

}
