package foi.air.rse.Model.dataAccess;

import com.google.firebase.firestore.Exclude;

public interface Identifiable<TKey> {
    @Exclude
    TKey getEntityKey();
}
