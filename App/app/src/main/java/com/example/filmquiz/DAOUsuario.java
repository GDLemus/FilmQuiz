package com.example.filmquiz;

import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;

public class DAOUsuario {

private DatabaseReference databaseReference;

public DAOUsuario(){
    FirebaseDatabase db = FirebaseDatabase.getInstance();
    databaseReference = db.getReference(Usuario.class.getSimpleName());
}

public Task<Void> add(Usuario usu)
{
    return databaseReference.push().setValue(usu);
}

    public Query get(String key){
        if (key==null){
            return databaseReference.orderByKey().limitToFirst(5);
        }else {
            return databaseReference.orderByKey().startAfter(key).limitToFirst(5);
        }

    }

}
