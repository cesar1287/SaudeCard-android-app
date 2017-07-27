package comcesar1287.github.www.saudecard.view;

import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Categorie;
import comcesar1287.github.www.saudecard.controller.fragment.CategorieFragment;
import comcesar1287.github.www.saudecard.controller.util.Utility;

public class MainActivity extends AppCompatActivity {

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    Handler handler;
    Runnable myRunnable;

    CategorieFragment frag;

    ArrayList<Categorie> categoriesList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        verifyUserIsLogged();

        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void setupUI() {

        Categorie health = new Categorie();
        Uri uriHealth = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0014_saude);
        health.setIcon(uriHealth);

        categoriesList.add(health);

        Categorie alimentation = new Categorie();
        Uri uriAlimentation = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0015_alimentacao);
        alimentation.setIcon(uriAlimentation);

        categoriesList.add(alimentation);

        Categorie animal = new Categorie();
        Uri uriAnimal = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0028_animal);
        animal.setIcon(uriAnimal);

        categoriesList.add(animal);

        Categorie graphicArt = new Categorie();
        Uri uriGraphicArt = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0021_arte_grafica);
        graphicArt.setIcon(uriGraphicArt);

        categoriesList.add(graphicArt);

        Categorie auto = new Categorie();
        Uri uriAuto = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0018_automotivos);
        auto.setIcon(uriAuto);

        categoriesList.add(auto);

        Categorie beauty = new Categorie();
        Uri uriBeauty = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0026_beleza);
        beauty.setIcon(uriBeauty);

        categoriesList.add(beauty);

        Categorie commerce = new Categorie();
        Uri uriCommerce = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0017_comercio);
        commerce.setIcon(uriCommerce);

        categoriesList.add(commerce);

        Categorie consulting = new Categorie();
        Uri uriConsulting = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0024_consultoria);
        consulting.setIcon(uriConsulting);

        categoriesList.add(consulting);

        Categorie education = new Categorie();
        Uri uriEducation = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0019_educacao);
        education.setIcon(uriEducation);

        categoriesList.add(education);

        Categorie laser = new Categorie();
        Uri uriLaser = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0027_laser);
        laser.setIcon(uriLaser);

        categoriesList.add(laser);

        Categorie sport = new Categorie();
        Uri uriSport = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0023_esportes);
        sport.setIcon(uriSport);

        categoriesList.add(sport);

        Categorie home = new Categorie();
        Uri uriHome = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0022_lar_construcao);
        home.setIcon(uriHome);

        categoriesList.add(home);

        Categorie tech = new Categorie();
        Uri uriTech = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0020_tecnologia);
        tech.setIcon(uriTech);

        categoriesList.add(tech);

        Categorie tourism = new Categorie();
        Uri uriTourism = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0025_turismo);
        tourism.setIcon(uriTourism);

        categoriesList.add(tourism);

        Categorie clothing = new Categorie();
        Uri uriClothing = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0016_vestuario);
        clothing.setIcon(uriClothing);

        categoriesList.add(clothing);

        frag = (CategorieFragment) getSupportFragmentManager().findFragmentByTag("mainFrag");
        if(frag == null) {
            frag = new CategorieFragment();
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.replace(R.id.categories_fragment_container, frag, "mainFrag");
            ft.commit();
        }
    }

    public List<Categorie> getCategoriesList() {
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
}
