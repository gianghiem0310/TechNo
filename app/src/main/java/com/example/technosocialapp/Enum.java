package com.example.technosocialapp;

import androidx.annotation.NonNull;

import com.example.technosocialapp.model.Post;
import com.example.technosocialapp.model.StatusOnline;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;
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
    public static final String STATUS_ONLINE_TABLE = "statusOnlines";
    public static final String POST_TABLE = "posts";
    public static final String IMAGE_POST_TABLE = "imagePosts";
    public static final String AUTHENTICATION_ADD_TABLE = "authenticationAdds";
    public static final String FRIEND_TABLE = "friends";
    public static final String LIKE_TABLE = "likes";

    public static final String CONVERSATION_TABLE = "conversations";
    public static final String MESSAGE_TABLE = "messages";
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
    public static final String ID_USER_VIEW = "id_user_view";
    public static final FirebaseDatabase FIREBASE_DATABASE = FirebaseDatabase.getInstance();
    public static final DatabaseReference DATABASE_REFERENCE = FIREBASE_DATABASE.getReference();
//    Date currentTime = Calendar.getInstance().getTime();
//    Timestamp tsTemp = new Timestamp(currentTime.getTime());
//    Date date = new Date(tsTemp.getTime());
//    SimpleDateFormat newFormat = new SimpleDateFormat("dd/MM/yyyy");
//    thongBao(newFormat.format(date)+"");
    public static void turnOnAndOffStatus(boolean status, long idUser){
    Date currentTime = Calendar.getInstance().getTime();
    Timestamp tsTemp = new Timestamp(currentTime.getTime());
    long currentDate = tsTemp.getTime();
    StatusOnline statusOnline = new StatusOnline(idUser,status,currentDate);
    DATABASE_REFERENCE.child(Enum.STATUS_ONLINE_TABLE).child(idUser+"").setValue(statusOnline).addOnSuccessListener(new OnSuccessListener<Void>() {
        @Override
        public void onSuccess(Void unused) {

        }
    }).addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception e) {

        }
    });
    }
    public static ArrayList<Post> hamSapXepPost(ArrayList<Post> list1, boolean check){
        ArrayList<Post> list = list1;
        if(list.size()>1){
            if(check==false){
                Post temp = list.get(0);
                for (int i = 0 ; i < list.size() - 1; i++) {
                    for (int j = i + 1; j < list.size(); j++) {
                        if (list.get(i).getDate() < list.get(j).getDate()) {
                            temp = list.get(j);
                            list.set(j,list.get(i));
                            list.set(i,temp);
                        }
                    }
                }
            }
        }
        return list;
    }
    public static void swap(Post post1, Post post2){
        Post temp = post1;
        post1 = post2;
        post2 = temp;
    }
    public static final int RELATIONSHIP_ADDFRIEND = 0;
    public static final int RELATIONSHIP_BANBE = 1;
    public static final int RELATIONSHIP_FOLLOWING = 2;
    public static final int RELATIONSHIP_DAGUI = 3;
    public static final int RELATIONSHIP_DONGY = 4;

}
