package Model;

/**
 * InHouse part class
 *
 * @author Nathaniel Unruh
 */

public class InHouse extends Part {

    private int machineId;

    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {

        super(id, name, price, stock, min, max);

        this.machineId = machineId;

    }

    /**
     *
     * @param machineId - Machine ID Setter
     */
    public void setMachineId(int machineId) {

        this.machineId = machineId;

    }

    /**
     *
     * @return - Machine ID Getter
     */
    public int getMachineId() {

        return machineId;

    }
}
