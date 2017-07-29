package comcesar1287.github.www.saudecard.view;

import android.app.ProgressDialog;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;
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
import comcesar1287.github.www.saudecard.controller.domain.Partner;
import comcesar1287.github.www.saudecard.controller.firebase.FirebaseHelper;
import comcesar1287.github.www.saudecard.controller.fragment.PartnerFragment;
import comcesar1287.github.www.saudecard.controller.util.Utility;

public class PartnerCategoryActivity extends AppCompatActivity {

    String category;
    TextView tvTitleActionBar;

    ArrayList<Partner> partnersList;

    PartnerFragment frag;

    private ProgressDialog dialog;

    private DatabaseReference mDatabase;
    Query partner;

    ValueEventListener valueEventListener;
    ValueEventListener singleValueEventListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);

        tvTitleActionBar = (TextView) findViewById(R.id.tv_title_action_bar);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        dialog = ProgressDialog.show(this,"", this.getResources().getString(R.string.loading_partners_pls_wait), true, false);

        setupUI();

        loadList();
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

    public void loadList(){

        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                partnersList = new ArrayList<>();

                Partner p;
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    p = new Partner();
                    p.setSubcategory((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_SUBCATEGORY).getValue());
                    p.setName((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_NAME).getValue());
                    p.setUrlLogo((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_URL_LOGO).getValue());
                    p.setDescription((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_DESCRIPTION).getValue());
                    p.setAddress((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_ADDRESS).getValue());
                    p.setPhone((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_PHONE).getValue());
                    p.setSite((String)postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_SITE).getValue());
                    p.setLatitude((Double) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_LATITUDE).getValue());
                    p.setLongitude((Double) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_LONGITUDE).getValue());
                    p.setDiscount((String) postSnapshot.child(FirebaseHelper.FIREBASE_DATABASE_PARTNER_DISCOUNT).getValue());
                    p.setCategory(category);

                    partnersList.add(p);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(PartnerCategoryActivity.this, R.string.error_loading_partners, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        singleValueEventListener = new ValueEventListener() {
            public void onDataChange(DataSnapshot dataSnapshot) {

                frag = (PartnerFragment) getSupportFragmentManager().findFragmentByTag(Utility.KEY_MAIN_FRAGMENT);
                if(frag == null) {
                    frag = new PartnerFragment();
                    FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                    ft.replace(R.id.partners_fragment_container, frag, Utility.KEY_MAIN_FRAGMENT);
                    ft.commit();
                }

                dialog.dismiss();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                dialog.dismiss();
                Toast.makeText(PartnerCategoryActivity.this, R.string.error_loading_partners, Toast.LENGTH_LONG).show();
                finish();
            }
        };

        partner.addValueEventListener(valueEventListener);

        partner.addListenerForSingleValueEvent(singleValueEventListener);
    }

    public List<Partner> getPartnersList() {
        return partnersList;
    }

    private void setupUI() {

        category = getIntent().getStringExtra(Utility.CATEGORY);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {

            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);

            switch (category) {
                case Utility.HEALTH:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_health));
                    partner = mDatabase
                            .child(Utility.HEALTH)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.ALIMENTATION:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_alimentation));
                    partner = mDatabase
                            .child(Utility.ALIMENTATION)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.ANIMAL:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_animal));
                    partner = mDatabase
                            .child(Utility.ANIMAL)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.GRAPHIC_ART:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_graphic_art));
                    partner = mDatabase
                            .child(Utility.GRAPHIC_ART)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.AUTO:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_auto));
                    partner = mDatabase
                            .child(Utility.AUTO)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.BEAUTY:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_beauty));
                    partner = mDatabase
                            .child(Utility.BEAUTY)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.COMMERCE:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_commerce));
                    partner = mDatabase
                            .child(Utility.COMMERCE)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.CONSULTING:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_consulting));
                    partner = mDatabase
                            .child(Utility.CONSULTING)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.EDUCATION:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_education));
                    partner = mDatabase
                            .child(Utility.EDUCATION)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.ENTERTAINMENT:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_entertainment));
                    partner = mDatabase
                            .child(Utility.ENTERTAINMENT)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.SPORT:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_sport));
                    partner = mDatabase
                            .child(Utility.SPORT)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.HOME:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_home));
                    partner = mDatabase
                            .child(Utility.HOME)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.TECH:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_tech));
                    partner = mDatabase
                            .child(Utility.TECH)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.TOURISM:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_tourism));
                    partner = mDatabase
                            .child(Utility.TOURISM)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
                case Utility.CLOTHING:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_clothing));
                    partner = mDatabase
                            .child(Utility.CLOTHING)
                            .orderByChild(FirebaseHelper.FIREBASE_DATABASE_ORDERBY);
                    break;
            }
        }
    }
}
