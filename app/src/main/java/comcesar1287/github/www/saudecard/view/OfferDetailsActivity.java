package comcesar1287.github.www.saudecard.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Offer;
import comcesar1287.github.www.saudecard.controller.fragment.MapViewFragment;
import comcesar1287.github.www.saudecard.controller.util.Utility;

public class OfferDetailsActivity extends AppCompatActivity {

    MapViewFragment frag;

    Offer offer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_offer_details);

        offer = (Offer) getIntent().getSerializableExtra(Utility.KEY_CONTENT_EXTRA_OFFER);

        setupUI();
    }

    public void setupUI(){

        frag = (MapViewFragment) getSupportFragmentManager().findFragmentByTag(Utility.KEY_MAP_FRAGMENT);
        if(frag == null) {
            frag = new MapViewFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Utility.KEY_CONTENT_EXTRA_OFFER, offer);
            frag.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.map_fragment_container, frag, Utility.KEY_MAP_FRAGMENT);
            ft.commit();
        }

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle(offer.getName());
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        ImageView iv_banner = (ImageView) findViewById(R.id.offer_details_banner);
        Glide.with(this).load(offer.getUrlBanner()).into(iv_banner);
        TextView tv_name = (TextView) findViewById(R.id.offer_details_name);
        tv_name.setText(offer.getName());
        TextView tv_description = (TextView) findViewById(R.id.offer_details_description);
        tv_description.setText(offer.getDescription());
        TextView tv_about = (TextView) findViewById(R.id.offer_details_about);
        tv_about.setText(offer.getAbout());
        TextView tv_address = (TextView) findViewById(R.id.offer_details_address);
        tv_address.setText(offer.getAddress());
        TextView tv_phone = (TextView) findViewById(R.id.offer_details_phone);
        tv_phone.setText(offer.getPhone());
    }
}