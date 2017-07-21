package comcesar1287.github.www.saudecard.controller.firebase;

import com.google.firebase.database.DatabaseReference;

import comcesar1287.github.www.saudecard.controller.domain.User;

public class FirebaseHelper {

    public static final String FIREBASE_DATABASE_ORDERBY = "city";
    public static final String FIREBASE_DATABASE_ORDERBY_TITLE = "title";
    public static final String FIREBASE_DATABASE_OFFER = "offer";
    public static final String FIREBASE_DATABASE_USERS = "users";
    public static final String FIREBASE_DATABASE_PARTNERS_LOCALIZATION = "parceiros_localizacao";
    public static final String FIREBASE_DATABASE_ACTIVE_HEALTH = "saude_ativa";

    public static final String FIREBASE_DATABASE_FOOD = "alimentacao";
    public static final String FIREBASE_DATABASE_ART_DESIGN = "arte_design";
    public static final String FIREBASE_DATABASE_AUTO = "auto";
    public static final String FIREBASE_DATABASE_BEAUTY = "beleza";
    public static final String FIREBASE_DATABASE_CONSULTING = "consultoria";
    public static final String FIREBASE_DATABASE_EDUCATION = "educacao";
    public static final String FIREBASE_DATABASE_SPORT = "esporte";
    public static final String FIREBASE_DATABASE_CLOTHING = "vestuario";
    public static final String FIREBASE_DATABASE_TOURISM = "turismo";
    public static final String FIREBASE_DATABASE_TECH = "tecnologia";
    public static final String FIREBASE_DATABASE_HEALTH = "saude";
    public static final String FIREBASE_DATABASE_HOME = "lar_construcao";
    public static final String FIREBASE_DATABASE_ENTERTAINMENT = "entretenimento";
    public static final String FIREBASE_DATABASE_WORLD_ANIMAL = "mundo_animal";
    public static final String FIREBASE_DATABASE_TRADE = "comercio";
    public static final String FIREBASE_DATABASE_SERVICES = "servicos";

    public static final String FIREBASE_DATABASE_PARTNER_SUBCATEGORY = "subcategory";
    public static final String FIREBASE_DATABASE_PARTNER_NAME = "name";
    public static final String FIREBASE_DATABASE_PARTNER_URL_LOGO = "url_logo";
    public static final String FIREBASE_DATABASE_PARTNER_DESCRIPTION = "description";
    public static final String FIREBASE_DATABASE_PARTNER_ADDRESS = "address";
    public static final String FIREBASE_DATABASE_PARTNER_PHONE = "phone";
    public static final String FIREBASE_DATABASE_PARTNER_SITE = "site";
    public static final String FIREBASE_DATABASE_PARTNER_LATITUDE = "latitude";
    public static final String FIREBASE_DATABASE_PARTNER_LONGITUDE = "longitude";
    public static final String FIREBASE_DATABASE_PARTNER_ID_USER = "id_user";
    public static final String FIREBASE_DATABASE_PARTNER_TAG = "tag";

    public static final String FIREBASE_DATABASE_OFFER_NAME = "name";
    public static final String FIREBASE_DATABASE_OFFER_URL_BANNER = "url_banner";
    public static final String FIREBASE_DATABASE_OFFER_DESCRIPTION = "description";
    public static final String FIREBASE_DATABASE_OFFER_ADDRESS = "address";
    public static final String FIREBASE_DATABASE_OFFER_PHONE = "phone";
    public static final String FIREBASE_DATABASE_OFFER_ABOUT = "about";
    public static final String FIREBASE_DATABASE_OFFER_LATITUDE = "latitude";
    public static final String FIREBASE_DATABASE_OFFER_LONGITUDE = "longitude";

    public static final String FIREBASE_DATABASE_ACTIVE_HEALTH_TITLE = "title";
    public static final String FIREBASE_DATABASE_ACTIVE_HEALTH_URL_BANNER = "url_banner";
    public static final String FIREBASE_DATABASE_ACTIVE_HEALTH_DESCRIPTION = "description";

    public static final String FIREBASE_NOTIFICATION_TYPE = "type_notification";
    public static final String FIREBASE_NOTIFICATION_TYPE_PARTNER = "partner";
    public static final String FIREBASE_NOTIFICATION_TYPE_OFFER = "offer";
    public static final String FIREBASE_NOTIFICATION_TYPE_ACTIVE_HEALTH = "active health";
    public static final String FIREBASE_NOTIFICATION_TYPE_UPDATE = "update";
    public static final String FIREBASE_NOTIFICATION_DATABASE = "db";
    public static final String FIREBASE_NOTIFICATION_CHILD = "child";

    public static void writeNewUser(DatabaseReference mDatabase, String userId, String name, String email, String birth, String sex, String phone, String plan, String profile_pic) {

        User user = new User(name, email, birth, phone, sex, plan, profile_pic);

        mDatabase.child(FIREBASE_DATABASE_USERS).child(userId).setValue(user);
    }
}