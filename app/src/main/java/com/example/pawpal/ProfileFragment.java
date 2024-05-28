package com.example.pawpal;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

public class ProfileFragment extends Fragment {

    // Déclaration de l'instance de DatabaseHelper
    private DatabaseHelper databaseHelper;
    // Vues
    private EditText usernameEditText;
    private EditText petNameEditText;
    private EditText speciesEditText;
    private EditText raceEditText;
    private EditText ageEditText;
    private EditText descriptionEditText;
    private Spinner genderSpinner;
    private Spinner sizeSpinner;

    // Identifiant de la photo d'animal sélectionnée
    private int selectedPhotoId;

    // Méthode appelée lors de la création de la vue du fragment
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialisation de l'instance de DatabaseHelper
        databaseHelper = new DatabaseHelper(getContext());

        // Initialisation des vues
        usernameEditText = view.findViewById(R.id.usernameEditText);
        petNameEditText = view.findViewById(R.id.petNameEditText);
        speciesEditText = view.findViewById(R.id.speciesEditText);
        raceEditText = view.findViewById(R.id.raceEditText);
        ageEditText = view.findViewById(R.id.ageEditText);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);
        genderSpinner = view.findViewById(R.id.genderSpinner);
        sizeSpinner = view.findViewById(R.id.sizeSpinner);

        // Gestionnaire de clic pour l'image utilisateur
        view.findViewById(R.id.userImage).setOnClickListener(v -> {
            selectedPhotoId = R.id.userImage;
            openImageSelectionDialog();
        });

        // Gestionnaire de clic pour les images d'animaux
        view.findViewById(R.id.petPhoto1).setOnClickListener(v -> {
            selectedPhotoId = R.id.petPhoto1;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto2).setOnClickListener(v -> {
            selectedPhotoId = R.id.petPhoto2;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto3).setOnClickListener(v -> {
            selectedPhotoId = R.id.petPhoto3;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto4).setOnClickListener(v -> {
            selectedPhotoId = R.id.petPhoto4;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto5).setOnClickListener(v -> {
            selectedPhotoId = R.id.petPhoto5;
            openImageSelectionDialog();
        });
        view.findViewById(R.id.petPhoto6).setOnClickListener(v -> {
            selectedPhotoId = R.id.petPhoto6;
            openImageSelectionDialog();
        });

        view.findViewById(R.id.saveButton).setOnClickListener(v -> saveProfileData());

        return view;
    }

    private void saveProfileData() {
        String username = usernameEditText.getText().toString();
        String petName = petNameEditText.getText().toString();
        String species = speciesEditText.getText().toString();
        String race = raceEditText.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        String age = ageEditText.getText().toString();
        String description = descriptionEditText.getText().toString();

        new SaveProfileTask().execute(username, petName, species, race, gender, age, description);
    }

    private class SaveProfileTask extends AsyncTask<String, Void, String> {
        private static final String URL = "jdbc:mysql://localhost:3306/pawpal";
        private static final String USER = "root";
        private static final String PASSWORD = "root";

        @Override
        protected String doInBackground(String... params) {
            String username = params[0];
            String petName = params[1];
            String species = params[2];
            String race = params[3];
            String gender = params[4];
            String age = params[5];
            String description = params[6];

            // Création d'une instance d'Utilisateur avec les données du formulaire
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setUsername(username);
            utilisateur.setPetName(petName);
            utilisateur.setSpecies(species);
            utilisateur.setRace(race);
            utilisateur.setGender(gender);
            utilisateur.setAge(Integer.parseInt(age)); // Convertir l'âge en entier
            utilisateur.setDescription(description);

            // Appel de la méthode addUtilisateur de DatabaseHelper pour ajouter l'utilisateur à la base de données
            databaseHelper.addUtilisateur(utilisateur);

            return "Données enregistrées avec succès";
        }

        @Override
        protected void onPostExecute(String result) {
            // Show a message to the user
            Toast.makeText(getContext(), result, Toast.LENGTH_LONG).show();
        }
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
                    // Si l'utilisateur a sélectionné une image depuis la galerie
                    if (data != null && data.getData() != null) {
                        Uri selectedImage = data.getData();
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                            ImageView photoImageView = getView().findViewById(selectedPhotoId);
                            photoImageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (data != null && data.getExtras() != null && data.getExtras().get("data") != null) {
                        // Si l'utilisateur a pris une photo à partir de la caméra
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        ImageView photoImageView = getView().findViewById(selectedPhotoId);
                        photoImageView.setImageBitmap(bitmap);
                    }
                    break;
            }
        }
    }
}
