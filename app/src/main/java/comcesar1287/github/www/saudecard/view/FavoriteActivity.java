package comcesar1287.github.www.saudecard.view;

import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Partner;
import comcesar1287.github.www.saudecard.controller.fragment.PartnerFragment;
import comcesar1287.github.www.saudecard.controller.util.Utility;
import comcesar1287.github.www.saudecard.model.SaudeCardDAO;

public class FavoriteActivity extends AppCompatActivity {

    List<Partner> partners = new ArrayList<>();
    PartnerFragment frag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        Toolbar toolbar = (Toolbar) findViewById(R.id.favorite_toolbar);
        setSupportActionBar(toolbar);

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

    @Override
    protected void onResume() {
        super.onResume();

        refreshList();
    }

    public List<Partner> getPartnersList() {

        loadlist();

        return partners;
    }

    private void loadlist() {

        SaudeCardDAO dao = new SaudeCardDAO(getApplicationContext());

        String id_user = FirebaseAuth.getInstance().getCurrentUser().getUid();

        partners = dao.getFavorites(id_user);

        dao.close();
    }

    public void refreshList(){
        frag.mList.clear();
        frag.mList.addAll(getPartnersList());
        frag.adapter.notifyDataSetChanged();
    }

    private void setupUI() {

        ActionBar actionBar = getSupportActionBar();

        if(actionBar!=null) {

            actionBar.setTitle("");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        frag = (PartnerFragment) getSupportFragmentManager().findFragmentByTag(Utility.KEY_MAIN_FRAGMENT);
        if(frag == null) {
            frag = new PartnerFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.favorite_fragment_container, frag, Utility.KEY_MAIN_FRAGMENT);
            ft.commit();
        }
    }
}
