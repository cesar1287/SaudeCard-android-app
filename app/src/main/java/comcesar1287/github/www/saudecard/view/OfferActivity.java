package comcesar1287.github.www.saudecard.view;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Offer;
import comcesar1287.github.www.saudecard.controller.firebase.FirebaseHelper;
import comcesar1287.github.www.saudecard.controller.fragment.OfferFragment;
import comcesar1287.github.www.saudecard.controller.util.Utility;

public class OfferActivity extends AppCompatActivity {

    List<Offer> offers;
    OfferFragment frag;

    private ProgressDialog dialog;

    Query offer;

    ValueEventListener valueEventListener;
    ValueEventListener singleValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer);

        Toolbar toolbar = (Toolbar) findViewById(R.id.offers_toolbar);
        setSupportActionBar(toolbar);

        setupUI();

        DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference();

        offer = mDatabase.child(FirebaseHelper.FIREBASE_DATABASE_OFFER).orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);

        dialog = ProgressDialog.show(this,"", this.getResources().getString(R.string.loading_offers_pls_wait), true, false);

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
    protected void onDestroy() {
        super.onDestroy();

        offer.removeEventListener(valueEventListener);
        offer.removeEventListener(singleValueEventListener);
    }

    public List<Offer> getPartnersList(){

        return offers;
    }

    public void loadList(){

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                offers = new ArrayList<>();

                Offer offer;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    offer = new Offer();
                    offer.setName((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_OFFER_NAME).getValue());
                    offer.setUrlBanner((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_OFFER_URL_BANNER).getValue());
                    offer.setDescription((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_OFFER_DESCRIPTION).getValue());
                    offer.setAddress((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_OFFER_ADDRESS).getValue());
                    offer.setPhone((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_OFFER_PHONE).getValue());
                    offer.setAbout((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_OFFER_ABOUT).getValue());
                    offer.setLatitude((Double) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_OFFER_LATITUDE).getValue());
                    offer.setLongitude((Double) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_OFFER_LONGITUDE).getValue());
                    offers.add(offer);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OfferActivity.this, R.string.error_loading_offers, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        singleValueEventListener = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                frag = (OfferFragment) getSupportFragmentManager().findFragmentByTag(Utility.KEY_MAIN_FRAGMENT);
                if(frag == null) {
                    frag = new OfferFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.offer_fragment_container, frag, Utility.KEY_MAIN_FRAGMENT);
                    ft.commit();
                }

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Toast.makeText(OfferActivity.this, R.string.error_loading_offers, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        offer.addValueEventListener(valueEventListener);

        offer.addListenerForSingleValueEvent(singleValueEventListener);
    }
}
