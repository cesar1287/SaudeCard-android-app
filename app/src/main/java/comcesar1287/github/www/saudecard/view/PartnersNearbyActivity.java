package comcesar1287.github.www.saudecard.view;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Partner;
import comcesar1287.github.www.saudecard.controller.firebase.FirebaseHelper;


public class PartnersNearbyActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback, LocationListener {

    List<Partner> partners = new ArrayList<>();
    List<Partner> partners_aux = new ArrayList<>();

    private ProgressDialog dialog;

    private static final int LOCATION_PERMISSION_REQUEST_CODE = 1;

    Query partner;

    ValueEventListener valueEventListener;
    ValueEventListener singleValueEventListener;

    String name_partner, tag_partner, search_name;

    MapView mMapView;
    private GoogleMap googleMap;

    Bundle savedInstanceState;

    boolean showed = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.savedInstanceState = savedInstanceState;

        setContentView(R.layout.activity_partners_nearby);

        Toolbar toolbar = (Toolbar) findViewById(R.id.nearby_toolbar);
        setSupportActionBar(toolbar);

        setupUI();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        partner = mDatabase.child(FirebaseHelper.FIREBASE_DATABASE_PARTNERS_LOCALIZATION);

        dialog = ProgressDialog.show(this,"", this.getResources().getString(R.string.loading_partners_pls_wait), true, false);

        loadList();
    }

    private void setupUI() {

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {

            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);

        //Carrega o arquivo de menu.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search, menu);

        //Pega o Componente.
        SearchView mSearchView = (SearchView) menu.findItem(R.id.search)
                .getActionView();
        //Define um texto de ajuda:
        mSearchView.setQueryHint("Buscar");

        // exemplos de utilização:
        mSearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                partners = new ArrayList<>(partners_aux);

                if(newText.length()!=0){
                    for (Iterator<Partner> i = partners.iterator(); i.hasNext();) {
                        Partner partner = i.next();
                        name_partner = partner.getName().toLowerCase();
                        tag_partner = partner.getTag().toLowerCase();
                        search_name = String.valueOf(newText).toLowerCase();
                        if (!name_partner.contains(search_name) && !tag_partner.contains(search_name)) {
                            i.remove();
                        }
                    }

                    drawMarkers();

                }else{

                    drawMarkers();
                }

                return false;
            }
        });

        return true;
    }

    public void loadList(){

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                Partner p;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    p = new Partner();
                    p.setName((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_NAME).getValue());
                    p.setLatitude((Double) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_LATITUDE).getValue());
                    p.setLongitude((Double) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_LONGITUDE).getValue());
                    p.setTag((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_TAG).getValue());
                    partners.add(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PartnersNearbyActivity.this, R.string.error_loading_partners, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        singleValueEventListener = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                partners_aux = new ArrayList<>(partners);

                dialog.dismiss();

                setupMap();

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(PartnersNearbyActivity.this, R.string.error_loading_partners, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        partner.addValueEventListener(valueEventListener);

        partner.addListenerForSingleValueEvent(singleValueEventListener);
    }

    public void setupMap(){

        mMapView = (MapView) findViewById(R.id.mapView);
        mMapView.onCreate(savedInstanceState);

        mMapView.onResume(); // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(getApplicationContext());
        } catch (Exception e) {
            e.printStackTrace();
        }

        mMapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(GoogleMap mMap) {
                googleMap = mMap;

                googleMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

                enableMyLocation();
            }
        });
    }

    private void enableMyLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            // Permission to access the location is missing.
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    LOCATION_PERMISSION_REQUEST_CODE);
        } else if (googleMap != null) {
            // Access to the location has been granted to the app.
            googleMap.setMyLocationEnabled(true);

            // Getting LocationManager object from System Service LOCATION_SERVICE
            LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            // Creating a criteria object to retrieve provider
            Criteria criteria = new Criteria();

            // Getting the name of the best provider
            String provider = locationManager.getBestProvider(criteria, true);

            // Getting Current Location
            Location location = locationManager.getLastKnownLocation(provider);

            if (location != null) {
                onLocationChanged(location);
            }
            locationManager.requestLocationUpdates(provider, 20000, 0, this);
        }
    }

    public void drawMarkers(){

        googleMap.clear();

        assert partners != null;
        for (Partner p: partners){
            // For dropping a marker at a point on the Map
            LatLng address = new LatLng(p.getLatitude(), p.getLongitude());
            googleMap.addMarker(new MarkerOptions().position(address).title(p.getName()).snippet(p.getName()));
        }
    }

    public void setupUI(LatLng latLng){

        drawMarkers();

        // For zooming automatically to the location of the marker
        CameraPosition cameraPosition = new CameraPosition.Builder().target(latLng).zoom(14.45f).build();
        googleMap.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        partner.removeEventListener(valueEventListener);
        partner.removeEventListener(singleValueEventListener);
    }

    @Override
    public void onLocationChanged(Location location) {

        if(showed) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            setupUI(latLng);

            showed = false;
        }
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}

