package Model;

/**
 * Outsourced part class
 *
 * @author Nathaniel Unruh
 */

public class Outsourced extends Part {

    private String companyName;

    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {

        super(id, name, price, stock, min, max);

        this.companyName = companyName;

    }

    /**
     * Company name setter
     * @param companyName
     */
    public void setCompanyName(String companyName) {

        this.companyName = companyName;

    }

    /**
     * Company name getter
     * @return
     */

    public String getCompanyName() {

        return companyName;

    }
}
