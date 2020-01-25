package com.example.readysteadyeat.Model.dataModel;

import com.google.firebase.firestore.Exclude;
public interface Identifiable<TKey> {
    @Exclude
    TKey getEntityKey();
}
