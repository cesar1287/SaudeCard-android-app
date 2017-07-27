package comcesar1287.github.www.saudecard.view;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.widget.TextView;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.util.Utility;

public class CategoryActivity extends AppCompatActivity {

    String category;
    TextView tvTitleActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);

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

    private void setupUI() {

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
