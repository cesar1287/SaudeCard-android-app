package comcesar1287.github.www.saudecard.controller.domain;

import android.net.Uri;

public class Category {

    private String name;
    private Uri icon;

    public Uri getIcon() {
        return icon;
    }

    public void setIcon(Uri icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
