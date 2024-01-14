package com.example.technosocialapp;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Enum {
    public static final String PRE_LOGIN = "SharedPreferencesLogin";
    public static final String NULL = "NULL";

    public static final int USED = 1;
    public static final String CHECK_USED = "used";
    public static final int NULL_INT = -1;
    public static final String USER_TABLE = "users";
    public static final String INFOR_USER_TABLE = "inforusers";
    public static final String STATUS_ONLINE_TABLE = "statusOnline";
    public static final String ID_USER = "id_user";
    public static final String NAME_USER = "name_user";
    public static final String IMAGE_USER = "image_user";
    public static final String GIOITINH = "GioiTinh";
    public static final String NAM = "Nam";
    public static final String NU = "Nữ";
    public static final String SINHVIEN = "Sinh viên";
    public static final String CONGNHAN = "Công nhân";
    public static final String DOANHNHAN = "Doanh nhân";
    public static final String NHANVIEN = "Nhân viên";
    public static final String GIAOVIEN = "Giáo viên";
    public static final String KHAC = "Khác";
    public static final FirebaseDatabase FIREBASE_DATABASE = FirebaseDatabase.getInstance();
    public static final DatabaseReference DATABASE_REFERENCE = FIREBASE_DATABASE.getReference();
//    Date currentTime = Calendar.getInstance().getTime();
//    Timestamp tsTemp = new Timestamp(currentTime.getTime());
//    Date date = new Date(tsTemp.getTime());
//    SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
//    thongBao(newFormat.format(date)+"");
}
