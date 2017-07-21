package comcesar1287.github.www.saudecard.controller.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;

public class Utility {

    public static String STATE = "Estado";
    public static String CITY = "Cidade";

    public static final String CATEGORY = "category";
    public static final String SUBCATEGORY = "subcategory";

    public static final String LINK_BE_PARTNER = "http://www.brasilcardfacil.com.br/modalidade-parceria.html";

    public static final String FOOD = "alimentacao";
    public static final String ART_DESIGN = "art_design";
    public static final String BEAUTY = "beleza";
    public static final String CONSULTING = "consultoria";
    public static final String EDUCATION = "educacao";
    public static final String SPORT = "esporte";
    public static final String CLOTHING = "vestuario";
    public static final String TOURISM = "turismo";
    public static final String AUTO = "auto";
    public static final String TECH = "tecnologia";
    public static final String HEALTH = "saude";
    public static final String HOME = "lar_construcao";
    public static final String ENTERTAINMENT = "entretenimento";
    public static final String WORLD_ANIMAL = "mundo_animal";
    public static final String TRADE = "trade";
    public static final String SERVICES = "servicos";

    public static final String KEY_CONTENT_EXTRA_PLANS = "link";
    public static final String KEY_CONTENT_EXTRA_PARTNER = "partner";
    public static final String KEY_CONTENT_EXTRA_OFFER = "offer";
    public static final String KEY_CONTENT_EXTRA_ACTIVE_HEALTH = "active_health";
    public static final String KEY_CONTENT_EXTRA_ACTIVE_PARTNERS_NEARBY = "partners";
    public static final String KEY_MAIN_FRAGMENT = "mainFrag";
    public static final String KEY_MAP_FRAGMENT = "mapFrag";

    public static final String ART_DESIGN_SUBCATEGORY_AGENCY = "agencia";
    public static final String ART_DESIGN_SUBCATEGORY_ART = "arte";
    public static final String ART_DESIGN_SUBCATEGORY_GRAPHICS = "graficas";
    public static final String ART_DESIGN_SUBCATEGORY_DESIGN = "design";
    public static final String ART_DESIGN_SUBCATEGORY_DECORATION = "decoracao";
    public static final String ART_DESIGN_SUBCATEGORY_TATTO = "tatto";

    public static final String TECH_SUBCATEGORY_CELLPHONE = "celular";
    public static final String TECH_SUBCATEGORY_PHOTOGRAPHY = "fotografia";
    public static final String TECH_SUBCATEGORY_GAMES = "games";
    public static final String TECH_SUBCATEGORY_COMPUTING = "informatica";
    public static final String TECH_SUBCATEGORY_INTERNET = "internet";
    public static final String TECH_SUBCATEGORY_TV_VIDEO = "tv_video";
    public static final String TECH_SUBCATEGORY_TECNICIANS = "tecnicos";

    public static final String ENTERTAINMENT_SUBCATEGORY_ART = "arte";
    public static final String ENTERTAINMENT_SUBCATEGORY_TOY = "brinquedo";
    public static final String ENTERTAINMENT_SUBCATEGORY_MOVIE_THEATER = "cinema";
    public static final String ENTERTAINMENT_SUBCATEGORY_DECORATION = "decoracao";
    public static final String ENTERTAINMENT_SUBCATEGORY_DJ = "dj";
    public static final String ENTERTAINMENT_SUBCATEGORY_NEWSPAPER = "jornal";
    public static final String ENTERTAINMENT_SUBCATEGORY_PARK = "parque";
    public static final String ENTERTAINMENT_SUBCATEGORY_RADIO = "radio";
    public static final String ENTERTAINMENT_SUBCATEGORY_PARTY_HALL = "salao_festa";
    public static final String ENTERTAINMENT_SUBCATEGORY_SOUND = "som";

    public static final String HOME_SUBCATEGORY_ARCHITECTURE = "arquitetura";
    public static final String HOME_SUBCATEGORY_DECORATION = "decoracao";
    public static final String HOME_SUBCATEGORY_ENGINEERING = "engenharia";
    public static final String HOME_SUBCATEGORY_HARDWARE = "ferragens";
    public static final String HOME_SUBCATEGORY_TIMBER = "madeireira";
    public static final String HOME_SUBCATEGORY_CONSTRUCTION_MATERIAL = "material_construcao";
    public static final String HOME_SUBCATEGORY_FURNITURE = "moveis";
    public static final String HOME_SUBCATEGORY_PROVISION_SERVICES = "prestacao_servico";
    public static final String HOME_SUBCATEGORY_INK = "tinta";
    public static final String HOME_SUBCATEGORY_GLASSES = "vidros";
    public static final String HOME_SUBCATEGORY_TOOLS = "ferramentas";

    public static final String HEALTH_SUBCATEGORY_CLINIC = "clinica";
    public static final String HEALTH_SUBCATEGORY_DRUGSTORE = "farmacia";
    public static final String HEALTH_SUBCATEGORY_EXAMS = "exames";
    public static final String HEALTH_SUBCATEGORY_PHYSIOTHERAPY = "fisioterapia";
    public static final String HEALTH_SUBCATEGORY_HOSPITAL = "hospital";
    public static final String HEALTH_SUBCATEGORY_DOCTOR = "medico";
    public static final String HEALTH_SUBCATEGORY_NUTRITIONIST = "nutricionista";
    public static final String HEALTH_SUBCATEGORY_DENTISTRY = "odontologia";
    public static final String HEALTH_SUBCATEGORY_PSYCHOLOGY = "psicologia";
    public static final String HEALTH_SUBCATEGORY_PILATES = "pilates";
    public static final String HEALTH_SUBCATEGORY_ACUPUNCTURE = "acupuntura";
    public static final String HEALTH_SUBCATEGORY_AESTHETICS = "estetica";

    public static final String AUTO_SUBCATEGORY_ACCESSORIES = "acessorios";
    public static final String AUTO_SUBCATEGORY_AIR_CONDITIONING = "ar_condicionado";
    public static final String AUTO_SUBCATEGORY_DRIVING_SCHOOL = "auto_escola";
    public static final String AUTO_SUBCATEGORY_AUTO_PARTS = "auto_pecas";
    public static final String AUTO_SUBCATEGORY_BATTERIE = "bateria";
    public static final String AUTO_SUBCATEGORY_FUEL = "combustivel";
    public static final String AUTO_SUBCATEGORY_ESCAPEMENT = "escapamento";
    public static final String AUTO_SUBCATEGORY_PARKING = "estacionamento";
    public static final String AUTO_SUBCATEGORY_WASH = "lavagem";
    public static final String AUTO_SUBCATEGORY_MECHANICAL = "mecanica";
    public static final String AUTO_SUBCATEGORY_TIRES = "pneus";
    public static final String AUTO_SUBCATEGORY_CARS_FOR_SALE = "concessionarias";
    public static final String AUTO_SUBCATEGORY_RESALE = "revendas";

    public static final String WORLD_ANIMAL_SUBCATEGORY_CLINIC = "clinica";
    public static final String WORLD_ANIMAL_SUBCATEGORY_HOTEL = "hotel";
    public static final String WORLD_ANIMAL_SUBCATEGORY_LAB = "lab";
    public static final String WORLD_ANIMAL_SUBCATEGORY_VETERINARY = "veterinaria";
    public static final String WORLD_ANIMAL_SUBCATEGORY_PET_SHOP = "pet_shop";

    public static final String CLOTHING_SUBCATEGORY_SHOES = "calcados";
    public static final String CLOTHING_SUBCATEGORY_CHILDLIKE = "infantil";
    public static final String CLOTHING_SUBCATEGORY_BEACH = "praia";
    public static final String CLOTHING_SUBCATEGORY_GARMENT = "roupas";
    public static final String CLOTHING_SUBCATEGORY_FITNESS = "fitness";

    public static final String SPORT_SUBCATEGORY_GYM = "academia";
    public static final String SPORT_SUBCATEGORY_EQUIPMENT = "equipamentos";
    public static final String SPORT_SUBCATEGORY_PERSONAL = "personal";
    public static final String SPORT_SUBCATEGORY_SUPPLEMENT = "suplemento";
    public static final String SPORT_SUBCATEGORY_CLOTHING = "vestuario";
    public static final String SPORT_SUBCATEGORY_BIKES = "bikes";
    public static final String SPORT_SUBCATEGORY_SWIMMING = "natacao";

    public static final String EDUCATION_SUBCATEGORY_SCHOOL = "colegio";
    public static final String EDUCATION_SUBCATEGORY_TECHNICIAN = "tecnico";
    public static final String EDUCATION_SUBCATEGORY_UNIVERSITY = "universidade";
    public static final String EDUCATION_SUBCATEGORY_COURSES = "cursos";

    public static final String TOURISM_SUBCATEGORY_TRAVEL_AGENCY = "agencia_viagem";
    public static final String TOURISM_SUBCATEGORY_GUIDE = "guia";
    public static final String TOURISM_SUBCATEGORY_HOSTEL = "hostel";
    public static final String TOURISM_SUBCATEGORY_HOTEL = "hotel";
    public static final String TOURISM_SUBCATEGORY_TRANSPORT = "transporte";

    public static final String BEAUTY_SUBCATEGORY_AESTHETICS = "estetica";
    public static final String BEAUTY_SUBCATEGORY_BARBER_SHOP = "barbearia";
    public static final String BEAUTY_SUBCATEGORY_MAKE_UP = "maquiagem";
    public static final String BEAUTY_SUBCATEGORY_BEAUTY_SALON = "salao_beleza";
    public static final String BEAUTY_SUBCATEGORY_MANICURE = "manicure";
    public static final String BEAUTY_SUBCATEGORY_PERFUMERY = "perfumaria";

    public static final String CONSULTING_SUBCATEGORY_BUSINESS = "empresarial";
    public static final String CONSULTING_SUBCATEGORY_LEGAL = "legal";

    public static final String FOOD_SUBCATEGORY_PUB = "bar";
    public static final String FOOD_SUBCATEGORY_RESTAURANT = "restaurante";
    public static final String FOOD_SUBCATEGORY_BAKERY = "padaria";
    public static final String FOOD_SUBCATEGORY_WATER = "agua";
    public static final String FOOD_SUBCATEGORY_SUPERMARKET = "supermercado";
    public static final String FOOD_SUBCATEGORY_DISTRIBUTOR = "distribuidor";
    public static final String FOOD_SUBCATEGORY_DELIVERY = "delivery";
    public static final String FOOD_SUBCATEGORY_STEAK_HOUSE = "churrascaria";
    public static final String FOOD_SUBCATEGORY_SKEWER = "espetinhos";
    public static final String FOOD_SUBCATEGORY_PARTY = "festas";
    public static final String FOOD_SUBCATEGORY_BUTCHER = "acougues";

    public static final String TRADE_SUBCATEGORY_HOME = "cama";
    public static final String TRADE_SUBCATEGORY_DEPARTAMENT = "departamento";
    public static final String TRADE_SUBCATEGORY_FLORICULTURE = "floricultura";
    public static final String TRADE_SUBCATEGORY_JEWELRY = "joias";
    public static final String TRADE_SUBCATEGORY_OPTICAL = "oticas";
    public static final String TRADE_SUBCATEGORY_SECURITY = "seguranca";
    public static final String TRADE_SUBCATEGORY_STATIONERY = "papelaria";
    public static final String TRADE_SUBCATEGORY_CONSORTIUM = "consortium";

    public static final String LOGIN_SHARED_PREF_NAME = "LoginActivityPreferences";
    public static final String WARNING_SHARED_PREF_NAME = "WarningActivityPreferences";

    public static final int TIME_TO_WARNING_ACTIVITY_APPEAR = 30000;

    public static final String APP_PACKAGE_NAME = "br.com.brasilcardfacil.www.brasilcardfacil";

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
