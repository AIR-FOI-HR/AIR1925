package foi.air.rse.Controllers.guest.restaurants.helperClasses;

import com.example.core.NavigationItem;


public class DataManager {

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

    public void sendData(final NavigationItem module, String id){
        module.setData(id);
    }
}