import java.util.Comparator;

public class Trial implements Comparable<Trial> {
    private String id, brand, description;
    private double women, white, black, asian, other, hispanic, us;
    private int year;

    public Trial(String id, String brand, String description, double women, double white, double black, double asian,
                 double other, double hispanic, double us, int year) {
        this.id = id;
        this.brand = brand;
        this.description = description;
        this.women = women;
        this.white = white;
        this.black = black;
        this.asian = asian;
        this.other = other;
        this.hispanic = hispanic;
        this.us = us;
        this.year = year;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getWomen() {
        return women;
    }

    public void setWomen(double women) {
        this.women = women;
    }

    public double getWhite() {
        return white;
    }

    public void setWhite(double white) {
        this.white = white;
    }

    public double getBlack() {
        return black;
    }

    public void setBlack(double black) {
        this.black = black;
    }

    public double getAsian() {
        return asian;
    }

    public void setAsian(double asian) {
        this.asian = asian;
    }

    public double getOther() {
        return other;
    }

    public void setOther(double other) {
        this.other = other;
    }

    public double getHispanic() {
        return hispanic;
    }

    public void setHispanic(double hispanic) {
        this.hispanic = hispanic;
    }

    public double getUs() {
        return us;
    }

    public void setUs(double us) {
        this.us = us;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    @Override
    public String toString() {
        return "Trial [id=" + id + ", brand=" + brand + ", year=" + year + "]";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Trial) {
            Trial o = (Trial) obj;
            return (id == o.getId() && brand.equalsIgnoreCase(o.getBrand()) &&
                    description.equalsIgnoreCase(o.getDescription()) && (Math.abs(women - o.getWomen()) < .01) &&
                    (Math.abs(white - o.getWhite()) < .01) && (Math.abs(black - o.getBlack()) < .01) &&
                    (Math.abs(asian - o.getAsian()) < .01) && (Math.abs(other - o.getOther()) < .01) &&
                    (Math.abs(hispanic - o.getHispanic()) < .01) && (Math.abs(us - o.getUs()) < .01) &&
                    year == o.getYear());
        } else {
            return false;
        }
    }

    @Override
    public int compareTo(Trial obj) {
        if (brand.compareTo(obj.getBrand()) != 0) {
            return brand.compareTo(obj.getBrand());
        } else {
            return id.compareTo(obj.getId());
        }
    }
}
