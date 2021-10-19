import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.*;

public class Main {
    private static int idNo = 1;

    // Source: https://www.census.gov/quickfacts/fact/table/US/LFE046219 (through 2019)
    private static final double WOMEN_IN_US = .508;
    private static final double BLACK_IN_US = .134;
    private static final double ASIAN_IN_US = .059;
    private static final double HISPANIC_IN_US = .185;

    public static void main(String[] args) {

        Map<String, Trial> trialMap = new HashMap<>();

        fillMapByID(trialMap);

        // Query 1 - number of trials that meet the threshold for certain categories:
        int countWomenTrials = countAboveThreshold(trialMap, Category.WOMEN);

        System.out.println("Number of " + trialMap.size() + " trials in 2017-2018 with appropriate representation of women: " +
                countWomenTrials + " (" + String.format("%.1f", (double) countWomenTrials/trialMap.size() * 100) + "%)");

        int countBlackTrials = countAboveThreshold(trialMap, Category.BLACK);
        System.out.println("Number of " + trialMap.size() + " trials in 2017-2018 with appropriate representation of African Americans: " +
                countBlackTrials + " (" + String.format("%.1f", (double)countBlackTrials/trialMap.size() * 100) + "%)");

        // Query 2 - list of trials that meet threshold for all major categories:
        ArrayList<Trial> trials = fairRepresentation(trialMap);

        System.out.println("The following trials had fair representation of women, African Americans, Asians and Hispanics:");
        if (trials.isEmpty()) {
            System.out.println("No trial had fair representation of minority groups.");
        } else {
            for (Trial t: trials) {
                System.out.println("ID " + t.getId() + ": brand " + t.getBrand());
            }
        }
    }

    private static void fillMapByID(Map<String, Trial> map) {

        try (Scanner fileScan = new Scanner(new FileInputStream("FDA_trials_edited.csv"))) {

            double women, white, black, asian, other, hispanic, us;
            int year;

            String line = fileScan.nextLine();          // Column headers
            line = fileScan.nextLine();

            while (fileScan.hasNext()) {
                line = fileScan.nextLine();

                Scanner lineScan = new Scanner(line);
                lineScan.useDelimiter(",");

                String id = String.valueOf(idNo);
                idNo++;

                String brand = lineScan.next();
                String description = lineScan.next();

                String womenString = lineScan.next();
                String whiteString = lineScan.next();
                String blackString = lineScan.next();
                String asianString = lineScan.next();
                String otherString = lineScan.next();
                String hispanicString = lineScan.next();
                String usString = lineScan.next();
                String yearString = lineScan.next();

                if (womenString.equalsIgnoreCase("NR") || womenString == "" || womenString.isEmpty()) {
                    women = Double.NaN;
                } else if (womenString.equalsIgnoreCase("<1%")) {
                    women = 0;
                } else {
                    women = Double.parseDouble(womenString.substring(0, womenString.length() - 1)) / 100;
                }

                if (whiteString.equalsIgnoreCase("NR") || whiteString == "" || whiteString.isEmpty()) {
                    white = Double.NaN;
                } else if (whiteString.equalsIgnoreCase("<1%")) {
                    white = 0;
                } else {
                    white = Double.parseDouble(whiteString.substring(0, whiteString.length() - 1)) / 100;
                }

                if (blackString.equalsIgnoreCase("NR") || blackString == "" || blackString.isEmpty()) {
                    black = Double.NaN;
                } else if (blackString.equalsIgnoreCase("<1%")) {
                    black = 0;
                } else {
                    black = Double.parseDouble(blackString.substring(0, blackString.length() - 1)) / 100;
                }

                if (asianString.equalsIgnoreCase("NR") || asianString == "" || asianString.isEmpty()) {
                    asian = Double.NaN;
                } else if (asianString.equalsIgnoreCase("<1%")) {
                    asian = 0;
                } else {
                    asian = Double.parseDouble(asianString.substring(0, asianString.length() - 1)) / 100;
                }

                if (otherString.equalsIgnoreCase("NR") || otherString == "" || otherString.isEmpty()) {
                    other = Double.NaN;
                } else if (otherString.equalsIgnoreCase("<1%")) {
                    other = 0;
                } else {
                    other = Double.parseDouble(otherString.substring(0, otherString.length() - 1)) / 100;
                }

                if (hispanicString.equalsIgnoreCase("NR") || hispanicString == "" || hispanicString.isEmpty()) {
                    hispanic = Double.NaN;
                } else if (hispanicString.equalsIgnoreCase("<1%")) {
                    hispanic = 0;
                } else {
                    hispanic = Double.parseDouble(hispanicString.substring(0, hispanicString.length() - 1)) / 100;
                }

                if (usString.equalsIgnoreCase("NR") || usString == "" || usString.isEmpty()) {
                    us = Double.NaN;
                } else if (usString.equalsIgnoreCase("<1%")) {
                    us = 0;
                } else {
                    us = Double.parseDouble(usString.substring(0, usString.length() - 1)) / 100;
                }

                year = Integer.parseInt(yearString);

                Trial t = new Trial(id, brand, description, women, white, black, asian, other, hispanic, us, year);
                map.put(id, t);
            }

            fileScan.close();

        } catch(FileNotFoundException e){
            System.out.println("Upload Failed: File does not exist.");
        }
    }

    private static int countAboveThreshold(Map<String, Trial> map, Category category) {
        int total = 0;

        Collection<Trial> values = map.values();
        Iterator<Trial> iterator = values.iterator();

        while (iterator.hasNext()) {
            Trial t = iterator.next();

            switch (category) {
                case WOMEN -> {
                    if (t.getWomen() >= WOMEN_IN_US) {
                        total++;
                    }
                }
                case BLACK -> {
                    if (t.getBlack() >= BLACK_IN_US) {
                        total++;
                    }
                }
                case ASIAN -> {
                    if (t.getAsian() >= ASIAN_IN_US) {
                        total++;
                    }
                }
                case HISPANIC -> {
                    if (t.getHispanic() >= HISPANIC_IN_US) {
                        total++;
                    }
                }
            }
        }
        return total;
    }

    private static ArrayList fairRepresentation(Map<String, Trial> map) {
        ArrayList<Trial> fair = new ArrayList();
        Collection<Trial> values = map.values();
        Iterator<Trial> iterator = values.iterator();

        while (iterator.hasNext()) {
            Trial t = iterator.next();

            if (t.getWomen() >= WOMEN_IN_US && t.getBlack() >= BLACK_IN_US && t.getAsian() >= ASIAN_IN_US &&
            t.getHispanic() >= HISPANIC_IN_US) {
                fair.add(t);
            }
        }

        return fair;
    }

}
