package com.example.pawpal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;

public class ProfileFragment extends Fragment {

    private static final int REQUEST_IMAGE_GALLERY_USER = 100;
    private static final int REQUEST_IMAGE_CAPTURE_PET = 101;
    private static final int REQUEST_IMAGE_GALLERY_PET = 102;

    private ImageView userImageView;
    private EditText usernameEditText;
    private EditText petNameEditText;
    private EditText petRaceEditText;
    private EditText petPersonalityEditText;

    // Identifiant de l'image sélectionnée
    private int selectedPetPhotoId;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        userImageView = view.findViewById(R.id.userImageView);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        petNameEditText = view.findViewById(R.id.petNameEditText);
        petRaceEditText = view.findViewById(R.id.petRaceEditText);
        petPersonalityEditText = view.findViewById(R.id.petPersonalityEditText);

        userImageView.setOnClickListener(v -> openImageGallery(REQUEST_IMAGE_GALLERY_USER));

        // GridView des photos d'animaux
        view.findViewById(R.id.petPhoto1).setOnClickListener(v -> {
            selectedPetPhotoId = R.id.petPhoto1;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto2).setOnClickListener(v -> {
            selectedPetPhotoId = R.id.petPhoto2;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto3).setOnClickListener(v -> {
            selectedPetPhotoId = R.id.petPhoto3;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto4).setOnClickListener(v -> {
            selectedPetPhotoId = R.id.petPhoto4;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto5).setOnClickListener(v -> {
            selectedPetPhotoId = R.id.petPhoto5;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto6).setOnClickListener(v -> {
            selectedPetPhotoId = R.id.petPhoto6;
            openImageSelectionDialog();
        });

        return view;
    }

    private void openImageGallery(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, requestCode);
    }

    private void openImageSelectionDialog() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), REQUEST_IMAGE_GALLERY_PET);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_GALLERY_USER) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        userImageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            } else if (requestCode == REQUEST_IMAGE_GALLERY_PET) {
                if (data != null && data.getData() != null) {
                    Uri selectedImage = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                        ImageView petPhotoImageView = getView().findViewById(selectedPetPhotoId);
                        petPhotoImageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
