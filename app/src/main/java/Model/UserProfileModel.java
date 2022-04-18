package Model;
import android.app.Activity;
import android.graphics.Bitmap;
import android.net.Uri;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import java.io.ByteArrayOutputStream;
import java.util.List;
import Entities.Flat;
import Presenter.UserProfile.UserProfileContract;

public class UserProfileModel implements UserProfileContract.Model, UserProfileContract.onUserDeletedListener {

    // MVP listener
    private UserProfileContract.onUserDeletedListener onUserDeletedListener;
    
    // Firebase
    private FirebaseDatabase database = FirebaseDatabase.getInstance(FIREBASEPATH);
    private DatabaseReference refFlat = database.getReference(FLATPATH);

    // Utils
    private static final String FIREBASEPATH = "https://wgfinance-b594f-default-rtdb.europe-west1.firebasedatabase.app/";
    private static final String FLATPATH = "WG";
    Flat retrievedFlat;
    
    public UserProfileModel(UserProfileContract.onUserDeletedListener onUserDeletedListener){
        this.onUserDeletedListener = onUserDeletedListener;
    }

    // Model -> Firebase
    // It is not stored inside the Realtime Database backend
    public void uploadImage(Activity activity, Bitmap bitmap) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100,byteArrayOutputStream);

        String uid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        StorageReference reference = FirebaseStorage.getInstance().getReference()
                .child("images")
                .child(uid + ".jpeg");
        reference.putBytes(byteArrayOutputStream.toByteArray())

                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        getDownloadUrl(activity,reference);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {}
                });

    }

    // Extract the URL
    public void getDownloadUrl(Activity activity, StorageReference reference){
        reference.getDownloadUrl()
                .addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        setUserProfileUrl(uri);
                    }
                });

    }

    // Init profile picture
    public void setUserProfileUrl(Uri uri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        UserProfileChangeRequest request = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();

        user.updateProfile(request)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                    }
                });
    }

    // Model -> Firebase
    // Model -> Presenter with listener to wait until task is executed
    @Override
    public Flat retrieveFlatFromFirebase(String email) {
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot snap : snapshot.getChildren()){
                    List<String> members = snap.getValue(Flat.class).getMembers();
                    if(members.contains(email)){
                        // create object from Firebase data
                        String address = snap.getValue(Flat.class).getAddress();
                        String id = snap.getValue(Flat.class).getId();
                        int size = members.size();
                        retrievedFlat = new Flat(address, id, members, size);
                    }
                }
                onUserDeletedListener.onFlatFound(retrievedFlat);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        };
        refFlat.addListenerForSingleValueEvent(valueEventListener);
        return retrievedFlat;
    }

    // Model -> Firebase
    @Override
    public void deleteUserInFirebase(Flat flat) {
        refFlat.child(flat.getId()).setValue(flat);
        onUserDeletedListener.onUserDeleted("Deleted");
    }

    // interface methods
    @Override
    public void onFlatFound(Flat flat) {}

    @Override
    public void onUserDeleted(String message) {}
}
