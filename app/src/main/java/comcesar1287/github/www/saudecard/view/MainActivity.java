package comcesar1287.github.www.saudecard.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Category;
import comcesar1287.github.www.saudecard.controller.fragment.CategoryFragment;
import comcesar1287.github.www.saudecard.controller.util.Utility;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    Handler handler;
    Runnable myRunnable;

    CategoryFragment frag;

    ArrayList<Category> categoriesList = new ArrayList<>();

    EditText etSearch;
    ImageButton ibBuy, ibDiary, ibNearby, ibFlame, ibAccount;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        verifyUserIsLogged();

        setContentView(R.layout.activity_main);

        setupUI();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.menu_search:
                etSearch.clearFocus();
                showDisabledFeatureAlert();
                break;
            case R.id.menu_buy:
                showDisabledFeatureAlert();
                break;
            case R.id.menu_diary:
                showDisabledFeatureAlert();
                break;
            case R.id.menu_nearby:
                showDisabledFeatureAlert();
                break;
            case R.id.menu_flame:
                showDisabledFeatureAlert();
                break;
            case R.id.menu_account:
                showDisabledFeatureAlert();
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(handler != null) {
            handler.removeCallbacks(myRunnable);
        }
    }

    private void setupUI() {

        etSearch = (EditText) findViewById(R.id.menu_search);
        etSearch.setInputType(InputType.TYPE_NULL);
        etSearch.setOnClickListener(this);

        ibBuy = (ImageButton) findViewById(R.id.menu_buy);
        ibBuy.setOnClickListener(this);

        ibDiary = (ImageButton) findViewById(R.id.menu_diary);
        ibDiary.setOnClickListener(this);

        ibNearby = (ImageButton) findViewById(R.id.menu_nearby);
        ibNearby.setOnClickListener(this);

        ibFlame = (ImageButton) findViewById(R.id.menu_flame);
        ibFlame.setOnClickListener(this);

        ibAccount = (ImageButton) findViewById(R.id.menu_account);
        ibAccount.setOnClickListener(this);

        Category health = new Category();
        Uri uriHealth = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0014_saude);
        health.setIcon(uriHealth);
        health.setName(Utility.HEALTH);

        categoriesList.add(health);

        Category alimentation = new Category();
        Uri uriAlimentation = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0015_alimentacao);
        alimentation.setIcon(uriAlimentation);
        alimentation.setName(Utility.ALIMENTATION);

        categoriesList.add(alimentation);

        Category animal = new Category();
        Uri uriAnimal = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0028_animal);
        animal.setIcon(uriAnimal);
        animal.setName(Utility.ANIMAL);

        categoriesList.add(animal);

        Category graphicArt = new Category();
        Uri uriGraphicArt = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0021_arte_grafica);
        graphicArt.setIcon(uriGraphicArt);
        graphicArt.setName(Utility.GRAPHIC_ART);

        categoriesList.add(graphicArt);

        Category auto = new Category();
        Uri uriAuto = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0018_automotivos);
        auto.setIcon(uriAuto);
        auto.setName(Utility.AUTO);

        categoriesList.add(auto);

        Category beauty = new Category();
        Uri uriBeauty = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0026_beleza);
        beauty.setIcon(uriBeauty);
        beauty.setName(Utility.BEAUTY);

        categoriesList.add(beauty);

        Category commerce = new Category();
        Uri uriCommerce = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0017_comercio);
        commerce.setIcon(uriCommerce);
        commerce.setName(Utility.COMMERCE);

        categoriesList.add(commerce);

        Category consulting = new Category();
        Uri uriConsulting = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0024_consultoria);
        consulting.setIcon(uriConsulting);
        consulting.setName(Utility.CONSULTING);

        categoriesList.add(consulting);

        Category education = new Category();
        Uri uriEducation = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0019_educacao);
        education.setIcon(uriEducation);
        education.setName(Utility.EDUCATION);

        categoriesList.add(education);

        Category entertaiment = new Category();
        Uri uriEntertaiment = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0027_laser);
        entertaiment.setIcon(uriEntertaiment);
        entertaiment.setName(Utility.ENTERTAINMENT);

        categoriesList.add(entertaiment);

        Category sport = new Category();
        Uri uriSport = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0023_esportes);
        sport.setIcon(uriSport);
        sport.setName(Utility.SPORT);

        categoriesList.add(sport);

        Category home = new Category();
        Uri uriHome = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0022_lar_construcao);
        home.setIcon(uriHome);
        home.setName(Utility.HOME);

        categoriesList.add(home);

        Category tech = new Category();
        Uri uriTech = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0020_tecnologia);
        tech.setIcon(uriTech);
        tech.setName(Utility.TECH);

        categoriesList.add(tech);

        Category tourism = new Category();
        Uri uriTourism = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0025_turismo);
        tourism.setIcon(uriTourism);
        tourism.setName(Utility.TOURISM);

        categoriesList.add(tourism);

        Category clothing = new Category();
        Uri uriClothing = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0016_vestuario);
        clothing.setIcon(uriClothing);
        clothing.setName(Utility.CLOTHING);

        categoriesList.add(clothing);

        frag = (CategoryFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new CategoryFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.categories_fragment_container, frag, "mainFrag");
            ft.commit();
        }
    }

    public void showDisabledFeatureAlert(){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta função estará disponível em breve.");
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public List<Category> getCategoriesList() {
        return categoriesList;
    }

    public void verifyUserIsLogged(){

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // User is signed out
                    startActivity(new Intent(MainActivity.this, SignWithActivity.class));
                    finish();
                }
            }
        };
    }
}
