package com.example.pawpal;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsFragment extends Fragment implements OnMapReadyCallback {

    // Variable pour stocker la référence à l'objet GoogleMap
    private GoogleMap mMap;

    // Constructeur par défaut de la classe MapsFragment
    public MapsFragment() {
        // Constructeur vide
    }

    // Méthode appelée lors de la création de la vue du fragment
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflater le layout pour ce fragment
        return inflater.inflate(R.layout.fragment_map, container, false);
    }

    // Méthode appelée lorsque la vue du fragment est créée
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        // Récupérer le SupportMapFragment et demander la carte asynchrone
        SupportMapFragment mapFragment = (SupportMapFragment) getChildFragmentManager()
                .findFragmentById(R.id.map);
        if (mapFragment != null) {
            mapFragment.getMapAsync(this);
        }
    }

    // Méthode appelée lorsque la carte est prête à être utilisée
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        // Ajouter un marqueur à la position par défaut et déplacer la caméra
        LatLng defaultLocation = new LatLng(-34, 151);
        mMap.addMarker(new MarkerOptions().position(defaultLocation).title("Marker in Default Location"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(defaultLocation));
    }
}
