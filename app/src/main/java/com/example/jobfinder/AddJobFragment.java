package com.example.jobfinder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Objects;


public class AddJobFragment extends Fragment {

    ImageView uploadImage;
    Button saveButton;
    EditText uploadTitle,uploadCompany,uploadType,uploadDesc;
    String imageURL;
    Uri uri;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v=inflater.inflate(R.layout.fragment_add_job, container, false);
        uploadImage=v.findViewById(R.id.uploadLogo);
        uploadTitle=v.findViewById(R.id.upload_title);
        uploadType=v.findViewById(R.id.upload_jobType);
        uploadCompany=v.findViewById(R.id.upload_company);
        uploadDesc=v.findViewById(R.id.upload_jobDesc);

        saveButton=v.findViewById(R.id.upload_btn);

        ActivityResultLauncher<Intent> activityResultLauncher=registerForActivityResult(
                new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
                    @Override
                    public void onActivityResult(ActivityResult result) {
                        if(result.getResultCode() == Activity.RESULT_OK){
                            Intent data=result.getData();
                            assert data != null;
                            uri=data.getData();
                            uploadImage.setImageURI(uri);

                        }else {
                            Toast.makeText(getActivity(), "Image not Selected", Toast.LENGTH_SHORT).show();
                        }
                    }
                }
        );

        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker=new Intent(Intent.ACTION_PICK);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        return v;
    }
    public void saveData(){
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("Jobs")
                .child(uri.getLastPathSegment());

        AlertDialog.Builder builder=new AlertDialog.Builder(requireContext());
        builder.setCancelable(false);
        builder.setView(R.layout.progress_layout);
        AlertDialog dialog=builder.show();

        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Task<Uri> uriTask=taskSnapshot.getStorage().getDownloadUrl();
                while (!uriTask.isComplete());
                Uri urlImage=uriTask.getResult();
                imageURL=urlImage.toString();
                uploadData();
                dialog.dismiss();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                dialog.dismiss();
            }
        });
    }

    public void uploadData(){
        String title=uploadTitle.getText().toString();
        String company=uploadCompany.getText().toString();
        String type=uploadType.getText().toString();
        String desc=uploadDesc.getText().toString();

        DataClass dataClass=new DataClass(title,company,type,desc,imageURL);

        FirebaseDatabase.getInstance().getReference("Jobs").child(title).setValue(dataClass)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            startActivity(new Intent(getActivity(),MainActivity.class));
                            Toast.makeText(requireContext(), "Saved", Toast.LENGTH_SHORT).show();
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(requireContext(), e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
    }


}