package foi.air.rse.Controllers.guest.restaurants;

import com.example.core.NavigationItem;

import java.util.ArrayList;



public class DataManager {
    //singleton
    private static DataManager instance;

    private DataManager()
    {

    }

    public static DataManager getInstance()
    {
        if (instance == null)
            instance = new DataManager();

        return instance;
    }

    public void sendData(final NavigationItem module){
        /*DataLoader dataLoader;
        if(Store.getAll().isEmpty()){
            System.out.println("Loading web data");
            dataLoader = new WsDataLoader();
        } else {
            System.out.println("Loading local data");
            dataLoader = new DbDataLoader();
        }
        dataLoader.loadData(new DataLoadedListener() {
            @Override
            public void onDataLoaded(ArrayList<Store> stores, ArrayList<Discount> discounts) {
                module.setData(stores, discounts);
            }
        });*/
    }
}