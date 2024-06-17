package com.example.technosocialapp.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Looper;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.ConstraintSet;

import com.bumptech.glide.Glide;
import com.example.technosocialapp.Enum;
import com.example.technosocialapp.R;
import com.example.technosocialapp.activity.HomeActivity;
import com.example.technosocialapp.model.ImagePost;
import com.example.technosocialapp.model.Post;
import com.example.technosocialapp.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPostFragment extends AbstractFragment{


    CircleImageView avatar;
    Button btnPost;
    TextView name;
    Spinner spinner;
    EditText content;
    ImageView imgPost;
    LinearLayout chooseFileImage;
    SharedPreferences sharedPreferences;
    ProgressBar progressBar;

    String name_shared;
    String avatar_shared;
    long idUser= -1;

    ConstraintLayout constrain_body;
    private int MY_REQUEST_CODE = 1;
    private Uri mUri;
    private int type = 0;
    private DatabaseReference databaseReference;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    private Post post;
    private boolean trangThaiDang = true;
    private long idNew;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = null;
        view = getLayoutInflater().inflate(R.layout.fragment_add_post, container, false);

        sharedPreferences = getContext().getSharedPreferences(Enum.PRE_LOGIN, Context.MODE_PRIVATE);
        databaseReference = Enum.DATABASE_REFERENCE;
        anhXa(view);
        progessBar(true);
        getDuLieuShared();
        setDuLieu();
        setDuLieuSpinner();
        setFirst();
        setSuKien();
        databaseReference.child(Enum.POST_TABLE).child(idUser+"").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                 idNew = snapshot.getChildrenCount();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        return view;
    }@Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == MY_REQUEST_CODE) {
            //Khi nguoi dung cho phep
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            }
        }

    }
    private ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {

                    if (result.getResultCode() == Activity.RESULT_OK) {
                        Intent data = result.getData();
                        if (data == null) {
                            return;
                        }
                        //Du anh lieu tu gallery
                        Uri uri = data.getData();
                        mUri = uri;
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContext().getContentResolver(), uri);
                            imgPost.setImageBitmap(bitmap);
                            updateView();
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            }
    );

    @Override
    public void onResume() {
        super.onResume();
        HomeActivity.title.setText("Đăng bài");
    }

    private void updateView(){
        constrain_body.addView(imgPost);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constrain_body);
        constraintSet.connect(R.id.menu_post,ConstraintSet.TOP,R.id.img_post,ConstraintSet.BOTTOM);
        constraintSet.applyTo(constrain_body);
    }
    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        mActivityResultLauncher.launch(Intent.createChooser(intent, "Seletect Picture"));
    }
    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }
    private void onClickRequestPermission() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            openGallery();
            return;
        }
        else if (Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU) {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                String[] permission = {Manifest.permission.READ_EXTERNAL_STORAGE};
                requestPermissions(permission, MY_REQUEST_CODE);
            }
        } else {
            if (getActivity().checkSelfPermission(Manifest.permission.READ_MEDIA_IMAGES) == PackageManager.PERMISSION_GRANTED) {
                openGallery();
            } else {
                String[] permission = {Manifest.permission.READ_MEDIA_IMAGES};
                requestPermissions(permission, MY_REQUEST_CODE);
            }
        }
    }
    private void setSuKien(){

        chooseFileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickRequestPermission();
            }
        });
        btnPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progessBar(true);
                kiemTraRong();
                ketQua();
            }
        });
    }
    private void checkFirebase(){

        new android.os.Handler(Looper.getMainLooper()).postDelayed(
                new Runnable() {
                    public void run() {
                        postBaiViet(idNew);
                    }
                },
                3000);
    }
    private void postBaiViet(long idNew){
        Date currentTime = Calendar.getInstance().getTime();
        Timestamp tsTemp = new Timestamp(currentTime.getTime());
        post = new Post(idNew,idUser,content.getText().toString().trim(),-1,0,tsTemp.getTime(),0,0);
        if(mUri ==  null){
            if(trangThaiDang==true) {
                databaseReference.child(Enum.POST_TABLE).child(idUser+"").child(post.getId()+"").setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        trangThaiDang = false;
                        progessBar(false);
                        thongBao("Upload thành công!");
                        content.setText("");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progessBar(false);
                        thongBao("Lỗi hệ thống!");
                    }
                });
            }
        }else{
            if(trangThaiDang == true) {
                databaseReference.child(Enum.POST_TABLE).child(idUser+"").child(post.getId()+"").setValue(post).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        tienHanhUpAnh();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progessBar(false);
                        thongBao("Lỗi hệ thống!");
                    }
                });
            }
        }
    }
    private void tienHanhUpAnh(){
        Date currentTime = Calendar.getInstance().getTime();
        Timestamp tsTemp = new Timestamp(currentTime.getTime());
        StorageReference fileRef = reference.child(System.currentTimeMillis()+"."+getFileExtension(mUri));
        fileRef.putFile(mUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        ImagePost imagePost = new ImagePost(post.getId(),uri.toString());
                        databaseReference.child(Enum.IMAGE_POST_TABLE).child(post.getId()+"").setValue(imagePost).addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                trangThaiDang = false;
                                progessBar(false);
                                thongBao("Bài viết đăng thành công!");
                                content.setText("");
                                setFirst();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                progessBar(false);
                                thongBao("Lỗi hệ thống up ảnh!");
                            }
                        });
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        progessBar(false);
                        thongBao("Lỗi hệ thống up ảnh!");
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progessBar(false);
                thongBao("Lỗi hệ thống up ảnh!");
            }
        });
    }
    private void ketQua(){
        if(type == 0){
            checkFirebase();
        }else if(type == 1){
            progessBar(false);
            thongBao("Hãy chia sẻ nội dung gì đó đi nè!");
        }
    }
    private void kiemTraRong(){
        type = 0;
        if(content.getText().toString().trim().isEmpty()){
            type = 1;
        }
    }
    private void setFirst(){
        constrain_body.removeView(imgPost);
        ConstraintSet constraintSet = new ConstraintSet();
        constraintSet.clone(constrain_body);
        constraintSet.connect(R.id.menu_post,ConstraintSet.TOP,R.id.content,ConstraintSet.BOTTOM);
        constraintSet.applyTo(constrain_body);
    }
    private void setDuLieuSpinner(){
        ArrayList arrayList = new ArrayList();
        arrayList.add("Công khai");
        arrayList.add("Riêng tư");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item,arrayList);
        spinner.setAdapter(adapter);
    }
    private void setDuLieu (){
        name.setText(name_shared);
        if(!avatar_shared.equals(Enum.NULL)) {
            Glide.with(getActivity().getLayoutInflater().getContext()).load(avatar_shared).into(avatar);
        }
        else {
            avatar.setImageDrawable(getActivity().getResources().getDrawable(R.drawable.avt_mac_dinh, getActivity().getTheme()));
        }
        progessBar(false);
    }
    private void getDuLieuShared(){
        name_shared = sharedPreferences.getString(Enum.NAME_USER,Enum.NULL);
        avatar_shared = sharedPreferences.getString(Enum.IMAGE_USER,Enum.NULL);
        idUser = sharedPreferences.getLong(Enum.ID_USER,Enum.NULL_INT);
    }
    private void progessBar(boolean check){
        if(check==true){
            progressBar.setVisibility(View.VISIBLE);
        }else{
            progressBar.setVisibility(View.INVISIBLE);
        }
    }
    private void thongBao(String mes) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(mes).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create();
        builder.show();
    }
    private void anhXa(View fragment){


        avatar = fragment.findViewById(R.id.avt_post);
        btnPost = fragment.findViewById(R.id.post);
        name = fragment.findViewById(R.id.name_post);
        spinner = fragment.findViewById(R.id.spinner);
        content = fragment.findViewById(R.id.content);
        imgPost = fragment.findViewById(R.id.img_post);
        chooseFileImage = fragment.findViewById(R.id.chooseFileImage);
        progressBar = fragment.findViewById(R.id.progessbar);
        constrain_body = fragment.findViewById(R.id.body_add_post);
    }
}
