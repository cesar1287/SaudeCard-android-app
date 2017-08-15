package comcesar1287.github.www.saudecard.view;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Partner;
import comcesar1287.github.www.saudecard.controller.firebase.FirebaseHelper;
import comcesar1287.github.www.saudecard.controller.fragment.MapViewFragment;
import comcesar1287.github.www.saudecard.controller.util.Utility;
import comcesar1287.github.www.saudecard.model.SaudeCardDAO;
import es.dmoral.toasty.Toasty;


public class PartnerDetailsActivity extends AppCompatActivity {

    MapViewFragment frag;

    Partner partner;

    SaudeCardDAO dao;
    String id_user, db, child;

    Query partner_notification;

    ValueEventListener valueEventListener;
    ValueEventListener singleValueEventListener;

    ImageView banner;

    TextView tvTitleToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_partner_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.partner_details_toolbar);
        setSupportActionBar(toolbar);

        tvTitleToolbar = (TextView) findViewById(R.id.tv_title_action_bar);

        dao = new SaudeCardDAO(getApplicationContext());
        id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        db = getIntent().getStringExtra(FirebaseHelper.FIREBASE_NOTIFICATION_DATABASE);
        child = getIntent().getStringExtra(FirebaseHelper.FIREBASE_NOTIFICATION_CHILD);

        partner = (Partner) getIntent().getSerializableExtra(Utility.KEY_CONTENT_EXTRA_PARTNER);

        setupUI();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if(partner_notification!=null) {
            partner_notification.removeEventListener(valueEventListener);
            partner_notification.removeEventListener(singleValueEventListener);
        }
    }

    public void setupUI(){

        frag = (MapViewFragment) getSupportFragmentManager().findFragmentByTag(Utility.KEY_MAP_FRAGMENT);
        if(frag == null) {
            frag = new MapViewFragment();
            Bundle bundle = new Bundle();
            bundle.putSerializable(Utility.KEY_CONTENT_EXTRA_PARTNER, partner);
            frag.setArguments(bundle);
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.map_fragment_container, frag, Utility.KEY_MAP_FRAGMENT);
            ft.commit();
        }

        if(getSupportActionBar()!=null) {
            getSupportActionBar().setTitle("");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            tvTitleToolbar.setText(partner.getName());
        }

        TextView tv_name = (TextView) findViewById(R.id.partner_details_name);
        tv_name.setText(partner.getName());
        TextView tv_description = (TextView) findViewById(R.id.partner_details_description);
        tv_description.setText(partner.getDescription());
        TextView tv_address = (TextView) findViewById(R.id.partner_details_address);
        tv_address.setText(partner.getAddress());
        TextView tv_phone = (TextView) findViewById(R.id.partner_details_phone);
        tv_phone.setText(partner.getPhone());
        TextView tv_site = (TextView) findViewById(R.id.partner_details_site);
        tv_site.setText(partner.getSite());

        banner = (ImageView) findViewById(R.id.partner_details_banner);

        switch (partner.getCategory()) {
            case Utility.EXAM:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0010_exames, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0010_exames);
                }
                break;
            case Utility.DENTISTRY:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0009_odonto, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0009_odonto);
                }
                break;
            case Utility.HEALTH:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0015_saude, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0015_saude);
                }
                break;
            case Utility.ALIMENTATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0014_alimentacao, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0014_alimentacao);
                }
                break;
            case Utility.ANIMAL:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0007_animal, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0007_animal);
                }
                break;
            case Utility.GRAPHIC_ART:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0010_arte_grafica, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0010_arte_grafica);
                }
                break;
            case Utility.AUTO:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0009_automotive, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0009_automotive);
                }
                break;
            case Utility.BEAUTY:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0008_beleza, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0008_beleza);
                }
                break;
            case Utility.COMMERCE:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0012_comercio, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0012_comercio);
                }
                break;
            case Utility.CONSULTING:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0011_consultoria, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0011_consultoria);
                }
                break;
            case Utility.EDUCATION:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0002_educacao, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0002_educacao);
                }
                break;
            case Utility.ENTERTAINMENT:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0013_entretenimento, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0013_entretenimento);
                }
                break;
            case Utility.SPORT:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0003_esportes, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0003_esportes);
                }
                break;
            case Utility.HOME:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0004_lar_construcao, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0004_lar_construcao);
                }
                break;
            case Utility.TECH:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0006_tecnologia, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0006_tecnologia);
                }
                break;
            case Utility.TOURISM:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0014_turismo, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0014_turismo);
                }
                break;
            case Utility.CLOTHING:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    banner.setImageDrawable(getResources().getDrawable(R.drawable.banner_0005_vestuario, getApplicationContext().getTheme()));
                } else {
                    banner.setImageResource(R.drawable.banner_0005_vestuario);
                }
                break;
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        switch (id){
            case android.R.id.home:
                finish();
                break;
            case R.id.action_fav:

                if(dao.isFavorite(id_user, partner.getUrlLogo())){
                    dao.delete(id_user, partner.getUrlLogo());
                    item.setIcon(R.drawable.ic_heart_outline_white_48dp);
                    Toasty.error(this, getResources().getString(R.string.message_partner_removed_favorities), Toast.LENGTH_SHORT, true).show();
                }else {
                    dao.insertFavorite(id_user, partner);
                    item.setIcon(R.drawable.ic_heart_white_48dp);
                    Toasty.success(this, getResources().getString(R.string.message_partner_added_favorities), Toast.LENGTH_SHORT, true).show();
                }
                dao.close();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.favorite, menu);

        if(partner!=null) {
            if (dao.isFavorite(id_user, partner.getUrlLogo())) {
                menu.getItem(0).setIcon(R.drawable.ic_heart_white_48dp);
            } else {
                menu.getItem(0).setIcon(R.drawable.ic_heart_outline_white_48dp);
            }
        }
        return true;
    }
}
