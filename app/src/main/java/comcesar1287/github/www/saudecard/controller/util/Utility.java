package comcesar1287.github.www.saudecard.controller.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Utility {

    public static final String URI_PACKAGE = "android.resource://comcesar1287.github.www.saudecard/";

    public static String STATE = "Estado";
    public static String CITY = "Cidade";

    public static final String CATEGORY = "category";
    public static final String SUBCATEGORY = "subcategory";

    public static final String HEALTH = "saude";
    public static final String DENTISTRY = "odontologia";
    public static final String EXAM = "exame";
    public static final String ALIMENTATION = "alimentacao";
    public static final String ANIMAL = "animal";
    public static final String GRAPHIC_ART = "arte_grafica";
    public static final String AUTO = "auto";
    public static final String BEAUTY = "beleza";
    public static final String COMMERCE = "comercio";
    public static final String CONSULTING = "consultoria";
    public static final String EDUCATION = "educacao";
    public static final String ENTERTAINMENT = "entretenimento";
    public static final String SPORT = "esporte";
    public static final String HOME = "lar_construcao";
    public static final String TECH = "tecnologia";
    public static final String TOURISM = "turismo";
    public static final String CLOTHING = "vestuario";

    public static final String KEY_CONTENT_EXTRA_PLANS = "link";
    public static final String KEY_CONTENT_EXTRA_PARTNER = "partner";
    public static final String KEY_CONTENT_EXTRA_OFFER = "offer";
    public static final String KEY_CONTENT_EXTRA_ACTIVE_HEALTH = "active_health";
    public static final String KEY_CONTENT_EXTRA_ACTIVE_PARTNERS_NEARBY = "partners";
    public static final String KEY_MAIN_FRAGMENT = "mainFrag";
    public static final String KEY_MAP_FRAGMENT = "mapFrag";

    public static final String LOGIN_SHARED_PREF_NAME = "LoginActivityPreferences";
    public static final String WARNING_SHARED_PREF_NAME = "WarningActivityPreferences";
    public static final String FAVORITE_SHARED_PREF_NAME = "FavoritePreferences";
    public static final String FAVORITE_FIRST_TIME = "favorites_inserted";
    public static final String DONE = "done";

    public static final int TIME_TO_WARNING_ACTIVITY_APPEAR = 30000;

    public static final String APP_PACKAGE_NAME = "comcesar1287.github.www.saudecard";

    private static final int[] pesoCPF = {11, 10, 9, 8, 7, 6, 5, 4, 3, 2};

    public static boolean verifyEmptyField(String email, String pass){

        return (email.isEmpty() || pass.isEmpty());
    }

    public static boolean verifyEmptyField(String name, String email, String pass, String cpf){

        return (name.isEmpty() || email.isEmpty() || pass.isEmpty() || cpf.isEmpty());
    }

    public static boolean isValidCPF(String cpf) {
        if ((cpf==null) || (cpf.length()!=11)) return false;

        Integer digito1 = calcularDigito(cpf.substring(0,9), pesoCPF);
        Integer digito2 = calcularDigito(cpf.substring(0,9) + digito1, pesoCPF);
        return cpf.equals(cpf.substring(0,9) + digito1.toString() + digito2.toString());
    }

    private static int calcularDigito(String str, int[] peso) {
        int soma = 0;
        for (int indice=str.length()-1, digito; indice >= 0; indice-- ) {
            digito = Integer.parseInt(str.substring(indice,indice+1));
            soma += digito*peso[peso.length-str.length()+indice];
        }
        soma = 11 - soma % 11;
        return soma > 9 ? 0 : soma;
    }

    public static String unmask(String s) {
        return s.replaceAll("[.]", "").replaceAll("[-]", "")
                .replaceAll("[/]", "").replaceAll("[(]", "")
                .replaceAll("[)]", "");
    }

    public static TextWatcher insertMask(final String mask, final EditText ediTxt) {
        return new TextWatcher() {
            boolean isUpdating;
            String old = "";

            public void onTextChanged(CharSequence s, int start, int before,
                                      int count) {
                String str = unmask(s.toString());
                String mascara = "";
                if (isUpdating) {
                    old = str;
                    isUpdating = false;
                    return;
                }
                int i = 0;
                for (char m : mask.toCharArray()) {
                    if (m != '#' && str.length() > old.length()) {
                        mascara += m;
                        continue;
                    }
                    try {
                        mascara += str.charAt(i);
                    } catch (Exception e) {
                        break;
                    }
                    i++;
                }
                isUpdating = true;
                ediTxt.setText(mascara);
                ediTxt.setSelection(mascara.length());
            }

            public void beforeTextChanged(CharSequence s, int start, int count,
                                          int after) {
            }

            public void afterTextChanged(Editable s) {
            }
        };
    }
}
