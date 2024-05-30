package com.example.pawpal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.os.AsyncTask;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ProfileFragment extends Fragment {

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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        // Initialisation des vues
        usernameEditText = view.findViewById(R.id.usernameEditText);
        petNameEditText = view.findViewById(R.id.petNameEditText);
        speciesEditText = view.findViewById(R.id.speciesEditText);
        raceEditText = view.findViewById(R.id.raceEditText);
        ageEditText = view.findViewById(R.id.ageEditText);
        genderSpinner = view.findViewById(R.id.genderSpinner);
        sizeSpinner = view.findViewById(R.id.sizeSpinner);
        descriptionEditText = view.findViewById(R.id.descriptionEditText);

        // Gestionnaire de clic pour l'image utilisateur
        view.findViewById(R.id.userImage).setOnClickListener(v -> {
            selectedPhotoId = R.id.userImage;
            openImageSelectionDialog();
        });

        // Gestionnaire de clic pour les images d'animaux
        int[] petPhotoIds = {R.id.petPhoto1, R.id.petPhoto2, R.id.petPhoto3, R.id.petPhoto4, R.id.petPhoto5, R.id.petPhoto6};
        for (int id : petPhotoIds) {
            view.findViewById(id).setOnClickListener(v -> {
                selectedPhotoId = id;
                openImageSelectionDialog();
            });
        }

        // Récupérer les données existantes depuis la base de données
        retrieveData();

        // Ajouter un bouton de sauvegarde et gérer son clic
        view.findViewById(R.id.saveButton).setOnClickListener(v -> saveData());

        return view;
    }

    private void openImageSelectionDialog() {
        // Demander à l'utilisateur de choisir entre la galerie et l'appareil photo
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        Intent pickGalleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

        Intent chooserIntent = Intent.createChooser(pickGalleryIntent, "Select Picture");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{takePictureIntent});

        startActivityForResult(chooserIntent, 0);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 0) {
                if (data != null) {
                    Uri selectedImage = data.getData();
                    if (selectedImage != null) {
                        try {
                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                            ImageView photoImageView = getView().findViewById(selectedPhotoId);
                            photoImageView.setImageBitmap(bitmap);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (data.getExtras() != null && data.getExtras().get("data") != null) {
                        Bitmap bitmap = (Bitmap) data.getExtras().get("data");
                        ImageView photoImageView = getView().findViewById(selectedPhotoId);
                        photoImageView.setImageBitmap(bitmap);
                    }
                }
            }
        }
    }

    public void saveData() {
        if (!isNetworkAvailable()) {
            Toast.makeText(getActivity(), "Pas de connexion Internet", Toast.LENGTH_SHORT).show();
            return;
        }

        String username = usernameEditText.getText().toString();
        String petName = petNameEditText.getText().toString();
        String species = speciesEditText.getText().toString();
        String race = raceEditText.getText().toString();
        String gender = genderSpinner.getSelectedItem().toString();
        int age = Integer.parseInt(ageEditText.getText().toString());
        String size = sizeSpinner.getSelectedItem().toString();
        String description = descriptionEditText.getText().toString();

        new SaveDataTask(username, petName, species, race, gender, age, size, description).execute();
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }



    private void retrieveData() {
        try (Connection conn = DatabaseHelper.getConnection()) {
            String query = "SELECT * FROM utilisateurs WHERE id = ?";
            PreparedStatement statement = conn.prepareStatement(query);
            statement.setInt(1, 1); // Remplacez 1 par l'identifiant approprié
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                usernameEditText.setText(resultSet.getString("username"));
                petNameEditText.setText(resultSet.getString("petName"));
                speciesEditText.setText(resultSet.getString("species"));
                raceEditText.setText(resultSet.getString("race"));
                ageEditText.setText(resultSet.getString("age"));
                descriptionEditText.setText(resultSet.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private class SaveDataTask extends AsyncTask<Void, Void, Boolean> {
        private String username;
        private String petName;
        private String species;
        private String race;
        private String gender;
        private int age;
        private String size;
        private String description;

        public SaveDataTask(String username, String petName, String species, String race, String gender, int age, String size, String description) {
            this.username = username;
            this.petName = petName;
            this.species = species;
            this.race = race;
            this.gender = gender;
            this.age = age;
            this.size = size;
            this.description = description;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            try (Connection connection = DatabaseHelper.getConnection()) {
                if (connection != null) {
                    String query = "INSERT INTO utilisateurs (username, petName, species, race, gender, age, size, description) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
                    PreparedStatement preparedStatement = connection.prepareStatement(query);
                    preparedStatement.setString(1, username);
                    preparedStatement.setString(2, petName);
                    preparedStatement.setString(3, species);
                    preparedStatement.setString(4, race);
                    preparedStatement.setString(5, gender);
                    preparedStatement.setInt(6, age);
                    preparedStatement.setString(7, size);
                    preparedStatement.setString(8, description);
                    preparedStatement.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return false;
        }

        @Override
        protected void onPostExecute(Boolean success) {
            if (success) {
                Toast.makeText(getActivity(), "Données enregistrées avec succès", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getActivity(), "Échec de l'enregistrement des données", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
