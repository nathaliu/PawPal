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

    // Vues
    private ImageView userImageView;
    private EditText usernameEditText;
    private EditText petNameEditText;
    private EditText petRaceEditText;
    private EditText petPersonalityEditText;

    // Identifiant de la photo d'animal sélectionnée
    private int selectedPetPhotoId;

    // Méthode appelée lors de la création de la vue du fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialisation des vues
        usernameEditText = view.findViewById(R.id.usernameEditText);
        petNameEditText = view.findViewById(R.id.petNameEditText);

        // Gestionnaire de clic pour l'image utilisateur
        view.findViewById(R.id.userImage).setOnClickListener(v -> {
            selectedPetPhotoId = R.id.userImage;
            openImageSelectionDialog();
        });

        // Gestionnaires de clic pour les images d'animaux
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

    // Méthode pour ouvrir la boîte de dialogue de sélection d'image
    private void openImageSelectionDialog() {
        // Demander à l'utilisateur de choisir entre la galerie et l'appareil photo
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent pickGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent chooserIntent = Intent.createChooser(pickGalleryIntent, "Select Picture");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[] { takePictureIntent });

        startActivityForResult(chooserIntent, 0);
    }

    // Méthode appelée lorsque le résultat de l'activité de sélection d'image est retourné
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case 0:
                    // Si l'utilisateur a sélectionné une image d'animal depuis la galerie
                    if (data != null && data.getData() != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                            ImageView petPhotoImageView = getView().findViewById(selectedPetPhotoId);
                            petPhotoImageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                        // Si l'utilisateur a pris une photo de l'animal
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        ImageView petPhotoImageView = getView().findViewById(selectedPetPhotoId);
                        petPhotoImageView.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }
}
