package by.koshko.crimes.entity;

public class StopSearchCorrelationWithCrime {

    private long id;
    private String name;
    private String month;
    private int drugs;
    private int controlledDrugs;
    private int possessionOfWeapon;
    private int offenciveWeapon;
    private int theftFromThePerson;
    private int stolenGoods;

    public StopSearchCorrelationWithCrime(long id, String name, String month,
                                          int drugs, int controlledDrugs,
                                          int possessionOfWeapon, int offenciveWeapon,
                                          int theftFromThePerson, int stolenGoods) {
        this.id = id;
        this.name = name;
        this.month = month;
        this.drugs = drugs;
        this.controlledDrugs = controlledDrugs;
        this.possessionOfWeapon = possessionOfWeapon;
        this.offenciveWeapon = offenciveWeapon;
        this.theftFromThePerson = theftFromThePerson;
        this.stolenGoods = stolenGoods;
    }

    @Override
    public String toString() {
        return String.format("%d;%s;%s;%d;%d;%d;%d;%d;%d;", id, name, month, drugs, controlledDrugs,
                possessionOfWeapon, offenciveWeapon, theftFromThePerson, stolenGoods);
    }
}
