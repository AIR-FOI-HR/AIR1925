package foi.air.rse.Model;

public class Category {

    public String idDish;
    public String name;

    public Category(){

    }

    public Category(String name, String idDish) {
        this.idDish = idDish;
        this.name = name;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getIdDish() {
        return idDish;
    }
    public void setIdDish(String idDish) {
        this.idDish = idDish;
    }

    //sjfllč


}