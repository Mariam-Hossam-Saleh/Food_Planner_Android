package com.example.food_planner.model.pojos.area;

import com.example.food_planner.R;

import java.util.ArrayList;
import java.util.List;


public class AreaFlags {

    public static class AreaFlagMap {
        public final String strArea;
        public final int flagResourceFile;

        public AreaFlagMap(String strArea, int flagResourceFile) {
            this.strArea = strArea;
            this.flagResourceFile = flagResourceFile;
        }
    }

    private static final List<AreaFlagMap> areaFlags = new ArrayList<>();

    static {
        areaFlags.add(new AreaFlagMap("American", R.drawable.american));
        areaFlags.add(new AreaFlagMap("British", R.drawable.british));
        areaFlags.add(new AreaFlagMap("American", R.drawable.american));
        areaFlags.add(new AreaFlagMap("British", R.drawable.british));
        areaFlags.add(new AreaFlagMap("Canadian", R.drawable.canadian));
        areaFlags.add(new AreaFlagMap("Chinese", R.drawable.chinese));
        areaFlags.add(new AreaFlagMap("Croatian", R.drawable.croatian));
        areaFlags.add(new AreaFlagMap("Dutch", R.drawable.dutch));
        areaFlags.add(new AreaFlagMap("Egyptian", R.drawable.egyptian));
        areaFlags.add(new AreaFlagMap("Filipino", R.drawable.filipino));
        areaFlags.add(new AreaFlagMap("French", R.drawable.french));
        areaFlags.add(new AreaFlagMap("Greek", R.drawable.greek));
        areaFlags.add(new AreaFlagMap("Indian", R.drawable.indian));
        areaFlags.add(new AreaFlagMap("Irish", R.drawable.irish));
        areaFlags.add(new AreaFlagMap("Italian", R.drawable.italian));
        areaFlags.add(new AreaFlagMap("Jamaican", R.drawable.jamaican));
        areaFlags.add(new AreaFlagMap("Japanese", R.drawable.japanese));
        areaFlags.add(new AreaFlagMap("Kenyan", R.drawable.kenyan));
        areaFlags.add(new AreaFlagMap("Malaysian", R.drawable.malaysian));
        areaFlags.add(new AreaFlagMap("Mexican", R.drawable.mexican));
        areaFlags.add(new AreaFlagMap("Moroccan", R.drawable.moroccan));
        areaFlags.add(new AreaFlagMap("Polish", R.drawable.polish));
        areaFlags.add(new AreaFlagMap("Portuguese", R.drawable.portuguese));
        areaFlags.add(new AreaFlagMap("Russian", R.drawable.russian));
        areaFlags.add(new AreaFlagMap("Spanish", R.drawable.spanish));
        areaFlags.add(new AreaFlagMap("Thai", R.drawable.thai));
        areaFlags.add(new AreaFlagMap("Tunisian", R.drawable.tunisian));
        areaFlags.add(new AreaFlagMap("Turkish", R.drawable.turkish));
        areaFlags.add(new AreaFlagMap("Ukrainian", R.drawable.ukrainian));
        areaFlags.add(new AreaFlagMap("Uruguayan", R.drawable.uruguayan));
        areaFlags.add(new AreaFlagMap("Vietnamese", R.drawable.vietnamese));

    }

    public static int getFlagForArea(String areaName) {
        for (AreaFlagMap map : areaFlags) {
            if (map.strArea.equalsIgnoreCase(areaName)) {
                return map.flagResourceFile;
            }
        }
        return 0;
    }

    public static List<AreaFlagMap> getAreaFlagList() {
        return areaFlags;
    }
}
