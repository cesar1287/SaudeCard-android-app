package comcesar1287.github.www.saudecard.view;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

import comcesar1287.github.www.saudecard.R;
import comcesar1287.github.www.saudecard.controller.domain.Category;
import comcesar1287.github.www.saudecard.controller.domain.Partner;
import comcesar1287.github.www.saudecard.controller.fragment.CategoryFragment;
import comcesar1287.github.www.saudecard.controller.util.Utility;
import comcesar1287.github.www.saudecard.model.SaudeCardDAO;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private FirebaseAuth.AuthStateListener mAuthListener;
    private FirebaseAuth mAuth;

    Handler handler;
    Runnable myRunnable;

    CategoryFragment frag;

    ArrayList<Category> categoriesList = new ArrayList<>();

    EditText etSearch;
    ImageButton ibFavorite, ibDiary, ibNearby, ibFlame, ibAccount;

    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();

        verifyUserIsLogged();

        setContentView(R.layout.activity_main);

        setupUI();
    }

    private void insertFirstExecFavorites() {
        SaudeCardDAO saudeCardDAO = new SaudeCardDAO(this);

        Partner funerariaAlianca = insertFunerariaAlianca();
        saudeCardDAO.insertFavorite(mAuth.getCurrentUser().getUid(), funerariaAlianca);

        Partner labSaoLuiz = insertLabSaoLuiz();
        saudeCardDAO.insertFavorite(mAuth.getCurrentUser().getUid(), labSaoLuiz);

        Partner drogariasEconomize = insertDrogariasEconomize();
        saudeCardDAO.insertFavorite(mAuth.getCurrentUser().getUid(), drogariasEconomize);

        Partner poupeMaisFarma = insertPoupeMaisFarma();
        saudeCardDAO.insertFavorite(mAuth.getCurrentUser().getUid(), poupeMaisFarma);

        fillSharedPreferencesFirstExec();
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        switch (id){
            case R.id.menu_search:
                etSearch.clearFocus();
                showDisabledFeatureAlert();
                break;
            case R.id.menu_favorite:
                startActivity(new Intent(this, FavoriteActivity.class));
                break;
            case R.id.menu_diary:
                showDisabledFeatureAlert();
                break;
            case R.id.menu_nearby:
                startActivity(new Intent(this, PartnersNearbyActivity.class));
                break;
            case R.id.menu_flame:
                startActivity(new Intent(MainActivity.this, OfferActivity.class));
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

    private void fillSharedPreferencesFirstExec() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(Utility.FAVORITE_FIRST_TIME, Utility.DONE);
        editor.apply();
    }

    private Partner insertLabSaoLuiz() {
        Partner labSaoLuiz = new Partner();
        labSaoLuiz.setCategory("exame");
        labSaoLuiz.setSubcategory("Exames Laboratoriais");
        labSaoLuiz.setFavorite(true);
        labSaoLuiz.setAddress("Rua Dr. Teixeira Soares, 433, Centro, Formiga - MG");
        labSaoLuiz.setDescription("Missão\n Atuar com excelência na prestação de serviços de apoio diagnóstico, nas áreas de Análises Clínicas e Patologia Clínica, oferecendo aos nossos Clientes Qualidade, Segurança, Confiabilidade e Utilidade Diagnóstica, atendendo e superando suas expectativas.\n\nVisão \nAtuar no mercado como referência na prestação de serviços de Apoio Diagnóstico, visando crescimento, baseando-se na Ética, Qualidade e na Inovação Tecnológica.\n\nDescontos de acordo com a tabela. \n\nSL Center / Matriz\n(37) 3321-1020\nRua Dr. Teixeira Soares, 433, Centro, Formiga - MG.\n\nUnidade Med Center\n(37) 3321-0457\nRua Dr. Teixeira Soares, 284, Sala 01, Centro, Formiga - MG.\n\nUnidade Quinzinho\n(37) 9.8411-8742\nRua Neném Belo, 420, Quinzinho, Formiga - MG.\n\nUnidade Água Vermelha\n(37) 9.8410-8955\nRua Nossa Sra. Abadia, 391, Água Vermelha, Formiga - MG.\n\nUnidade Ouro Negro\n(37) 9.9805-0473\nAv. Abílio Machado, 1046, Ouro Negro, Formiga - MG.\n\nUnidade Córrego Fundo 1\n(37) 9.9907-1062\nRua José Gonçalves Fonseca, 140, Centro, Córrego Fundo - MG.\n\nUnidade Córrego Fundo 2\n(37) 9.9919-4677\nRua Germana C. Guimarães, 43 B, Centro, Córrego Fundo - MG.\n\nUnidade Cana Verde\n(37) 9.9906-3031\nPraça Cristóvão Cipriano, 90, Centro, Cana Verde - MG.\n\nUnidade Pains\n(37) 3323-1418\nRua Padre José Venâncio, 838, Centro, Pains - MG.\n\nUnidade Pimenta\n(37) 3324-1615\nRua Totonho Costa, 233, Centro, Pimenta - MG.\n\nUnidade Arcos\n(37) 3351-5213\nRua Jarbas Ferreira Pires, 274, Centro, Arcos - MG.\n\nUnidade Sto Antonio do Monte\n(37) 3281-5227\nRua Cel. José Luis G. Sobrinho, 53, N. Sra. de Fátima, Samonte - MG.");
        labSaoLuiz.setDiscount("tab.");
        labSaoLuiz.setLatitude(-20.4600173);
        labSaoLuiz.setLongitude(-45.4251749);
        labSaoLuiz.setName("Laboratório São Luiz");
        labSaoLuiz.setPhone("(37) 3321-1020");
        labSaoLuiz.setSite("www.labsaoluiz.com.br\n\nwww.facebook.com/saoluizanalisesclinicas");
        labSaoLuiz.setUrlLogo("http://imgur.com/tzLvzbO.png");

        return labSaoLuiz;
    }

    private Partner insertFunerariaAlianca() {
        Partner funerariaAlianca = new Partner();
        funerariaAlianca.setCategory("comercio");
        funerariaAlianca.setSubcategory("Funerária");
        funerariaAlianca.setFavorite(true);
        funerariaAlianca.setAddress("Rua João Pedrosa, 43, Quinzinho, Formiga - MG");
        funerariaAlianca.setDescription("Serviço funerário e cemitério. \n\nPlano Funerário GRATUITO para titular e dependentes*.");
        funerariaAlianca.setDiscount("Grat.");
        funerariaAlianca.setLatitude(-20.4701857);
        funerariaAlianca.setLongitude(-45.4285032);
        funerariaAlianca.setName("Funerária Aliança");
        funerariaAlianca.setPhone("(37) 3321-1279");
        funerariaAlianca.setSite("www.facebook.com/funerariaalinca");
        funerariaAlianca.setUrlLogo("http://imgur.com/MybNVbT.png");

        return funerariaAlianca;
    }

    private Partner insertPoupeMaisFarma() {
        Partner poupeMaisFarma = new Partner();
        poupeMaisFarma.setCategory("saude");
        poupeMaisFarma.setSubcategory("Farmácia");
        poupeMaisFarma.setFavorite(true);
        poupeMaisFarma.setAddress("Praça Cristóvão Faria, 594, Centro, Formiga - MG");
        poupeMaisFarma.setDescription("Poupe + Farma - Aqui você economiza mais!\n\nDescontos de acordo com a tabela.\n\nPoupe Mais Farma - Abílio Machado\n(37) 3321-5806\nAvenida Abílio Machado, 429, S. C. de Jesus, Formiga - MG.\n\nPoupe Mais Farma - Avenida Brasil\n(37) 3321-3076\nAvenida Brasil, 40, Vila Nimartele, Formiga - MG.\n\nDrogão Poupe Mais Farma\n(37) 3321-2631\nPraça Cristóvão Faria, 594, Centro, Formiga - MG.\n\nPoupe Mais Farma - Sagrado Coração de Jesus\n(37) 3322-1272\nPraça Florencio Rodrigues, 17, S. C. de Jesus, Formiga - MG.\n\nPoupe Mais Farma - Centro\n(37) 3322-1590\nRua Bernardes de Faria, 181, Centro, Formiga - MG.\n\nPoupe Mais Farma - Vila Ferreira\n(37) 3321-6797\nRua dos Expedicionários, 362 A, Vila Ferreira, Formiga - MG.");
        poupeMaisFarma.setDiscount("tab.");
        poupeMaisFarma.setLatitude(-20.45969146);
        poupeMaisFarma.setLongitude(-45.42537689);
        poupeMaisFarma.setName("Poupe + Farma");
        poupeMaisFarma.setPhone("(37) 3321-2631");
        poupeMaisFarma.setUrlLogo("http://imgur.com/XdXrRAh.png");

        return poupeMaisFarma;
    }

    private Partner insertDrogariasEconomize() {
        Partner drogariasEconomize = new Partner();
        drogariasEconomize.setCategory("saude");
        drogariasEconomize.setSubcategory("Drogaria");
        drogariasEconomize.setFavorite(true);
        drogariasEconomize.setAddress("Rua Lassance Cunha, 492, Quinzinho, Formiga - MG");
        drogariasEconomize.setDescription("Medicamentos Mega Baratos. A Drogaria Economize já oferece medicamentos com até 95% de descontos. Para você economizar de verdade. \n\nDescontos de acordo com a tabela.\n\nEconomize - Centro\n(37) 3322-2477\nRua Bernardes de Faria, 137, Centro, Formiga - MG.\n\nEconomize - Quinzinho\n(37) 3321-7805\nRua Lassance Cunha, 492, Quinzinho, Formiga - MG.\n\nEconomize - Arcos\n(37) 3351-3868\nRua 25 de Dezembro, 735, Centro, Arcos - MG.\n\nEconomize - Córrego Fundo\n(37) 3322-9104\nPraça Vigário João Ivo, 18, Centro, Córrego Fundo - MG.\n\nEconomize - Pains\n(37) 3323-2286\nRua Coronel José Ferreira, 356, Loja 02, Centro, Pains - MG.");
        drogariasEconomize.setDiscount("tab.");
        drogariasEconomize.setLatitude(-20.4704757);
        drogariasEconomize.setLongitude(-45.4281238);
        drogariasEconomize.setName("Drogarias Economize");
        drogariasEconomize.setPhone("(37) 3321-2477");
        drogariasEconomize.setUrlLogo("http://imgur.com/M1fWopf.png");

        return drogariasEconomize;
    }

    private void setupUI() {

        etSearch = (EditText) findViewById(R.id.menu_search);
        etSearch.setInputType(InputType.TYPE_NULL);
        etSearch.setOnClickListener(this);

        ibFavorite = (ImageButton) findViewById(R.id.menu_favorite);
        ibFavorite.setOnClickListener(this);

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

        Category exam = new Category();
        Uri uriExam = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0029_exames);
        exam.setIcon(uriExam);
        exam.setName(Utility.EXAM);

        categoriesList.add(exam);

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

        Category dentistry = new Category();
        Uri uriDentistry = Uri.parse(Utility.URI_PACKAGE + R.drawable.menu_0028_odontologia);
        dentistry.setIcon(uriDentistry);
        dentistry.setName(Utility.DENTISTRY);

        categoriesList.add(dentistry);

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

        final AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Esta função estará disponível em breve.");
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });
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
                }else{
                    sharedPreferences = getSharedPreferences(Utility.FAVORITE_SHARED_PREF_NAME, MODE_PRIVATE);
                    String result = sharedPreferences.getString(Utility.FAVORITE_FIRST_TIME,"");
                    if(result.equals("")){
                        insertFirstExecFavorites();
                    }
                }
            }
        };
    }
}
