package comcesar1287.github.www.saudecard.controller.util;

import java.util.ArrayList;

import comcesar1287.github.www.saudecard.controller.domain.Partner;

public class Singleton {

    private static Singleton singleton = new Singleton();

    private ArrayList<Partner> partnersList;

    /* A private Constructor prevents any other
     * class from instantiating.
     */
    private Singleton() { }

    /* Static 'instance' method */
    public static Singleton getInstance( ) {
        return singleton;
    }

    public ArrayList<Partner> getPartnersList() {
        return partnersList;
    }

    public void setPartnersList(ArrayList<Partner> partnersList) {
        this.partnersList = new ArrayList<>(partnersList);
    }
}