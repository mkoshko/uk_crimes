package by.koshko.crimes.entity;

import java.util.HashMap;
import java.util.Map;

public class Category {

    private static Map<String, Integer> categories = new HashMap<>();

    static {
        categories.put("Awaiting court outcome", 1);
        categories.put("Court result unavailable", 2);
        categories.put("Court case unable to proceed", 3);
        categories.put("Local resolution", 4);
        categories.put("Investigation complete; no suspect identified", 5);
        categories.put("Offender deprived of property", 6);
        categories.put("Offender fined", 7);
        categories.put("Offender given absolute discharge", 8);
        categories.put("Offender given a caution", 9);
        categories.put("Offender given a drugs possession warning", 10);
        categories.put("Offender given a penalty notice", 11);
        categories.put("Offender given community sentence", 12);
        categories.put("Offender given conditional discharge", 13);
        categories.put("Offender given suspended prison sentence", 14);
        categories.put("Offender sent to prison", 15);
        categories.put("Offender otherwise dealt with", 16);
        categories.put("Offender ordered to pay compensation", 17);
        categories.put("Suspect charged as part of another case", 18);
        categories.put("Suspect charged", 19);
        categories.put("Defendant found not guilty", 20);
        categories.put("Defendant sent to Crown Court", 21);
        categories.put("Unable to prosecute suspect", 22);
        categories.put("Formal action is not in the public interest", 23);
        categories.put("Action to be taken by another organisation", 24);
        categories.put("Further investigation is not in the public interest", 25);
        categories.put("Further action is not in the public interest", 26);
        categories.put("Under investigation", 27);
        categories.put("Status update unavailable", 28);
    }

    public static int getCategoryId(String category_name) {
        return categories.get(category_name);
    }
}
