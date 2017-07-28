package comcesar1287.github.www.saudecard.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Partner;
import comcesar1287.github.www.saudecard.controller.fragment.PartnerFragment;
import comcesar1287.github.www.saudecard.controller.util.Utility;

public class PartnerCategoryActivity extends AppCompatActivity {

    String category;
    TextView tvTitleActionBar;

    ArrayList<Partner> partnersList;

    PartnerFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_category);

        Toolbar toolbar = (Toolbar) findViewById(R.id.category_toolbar);
        setSupportActionBar(toolbar);

        tvTitleActionBar = (TextView) findViewById(R.id.tv_title_action_bar);

        setupUI();
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

    public List<Partner> getPartnersList() {
        return partnersList;
    }

    private void setupUI() {

        partnersList = new ArrayList<>();

        Partner partner = new Partner();
        partner.setName("Dr. Euler Campos");
        partner.setAddress("Formiga");
        partner.setSubcategory("Angiologista");
        partner.setDiscount("10%");
        partner.setUrlLogo("http://imgur.com/KtwwIma.png");

        partnersList.add(partner);

        Partner partner1 = new Partner();
        partner1.setName("Dr. Euler Campos");
        partner1.setAddress("Formiga");
        partner1.setSubcategory("Angiologista");
        partner1.setDiscount("10%");
        partner1.setUrlLogo("http://imgur.com/KtwwIma.png");

        partnersList.add(partner1);

        frag = (PartnerFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new PartnerFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.partners_fragment_container, frag, "mainFrag");
            ft.commit();
        }

        category = getIntent().getStringExtra(Utility.CATEGORY);

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {

            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);

            switch (category) {
                case Utility.HEALTH:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_health));
                    break;
                case Utility.ALIMENTATION:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_alimentation));
                    break;
                case Utility.ANIMAL:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_animal));
                    break;
                case Utility.GRAPHIC_ART:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_graphic_art));
                    break;
                case Utility.AUTO:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_auto));
                    break;
                case Utility.BEAUTY:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_beauty));
                    break;
                case Utility.COMMERCE:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_commerce));
                    break;
                case Utility.CONSULTING:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_consulting));
                    break;
                case Utility.EDUCATION:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_education));
                    break;
                case Utility.ENTERTAINMENT:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_entertainment));
                    break;
                case Utility.SPORT:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_sport));
                    break;
                case Utility.HOME:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_home));
                    break;
                case Utility.TECH:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_tech));
                    break;
                case Utility.TOURISM:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_tourism));
                    break;
                case Utility.CLOTHING:
                    tvTitleActionBar.setText(getResources().getString(R.string.screen_clothing));
                    break;
            }
        }
    }
}
