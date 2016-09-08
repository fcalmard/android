package ouccelo.com.acquisti;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;
import android.support.annotation.Nullable;
import android.util.Log;

import java.io.FileDescriptor;

/**
 * Created by francois on 08/09/16.
 */
public class ArtServices extends Service {

    public void onCreate() {
// Création du service
        Log.v("ARTSERVICE","21 onCreate ArtServices ");
    }

    public void onDestroy() {
// Destruction du service
        Log.v("ARTSERVICE","26 onDestroy ArtServices ");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
// Démarrage du service
        Log.v("ARTSERVICE","31 DEMARRAGE SERVICE onStartCommand ArtServices ");

        ArticleDataSource artdts = new ArticleDataSource(this);

        artdts.open();

        artdts.chargeFamillesArticles(this);

        artdts.close();

        Log.v("ARTSERVICE","41 FIN DEMARRAGE SERVICE onStartCommand ArtServices ");

        return START_STICKY;
    }

    public IBinder onBind(Intent arg0) {
        Log.v("ARTSERVICE","36 onBind ArtServices ");

        IBinder ib = new IBinder() {
            @Override
            public String getInterfaceDescriptor() throws RemoteException {
                return null;
            }

            @Override
            public boolean pingBinder() {
                return false;
            }

            @Override
            public boolean isBinderAlive() {
                return false;
            }

            @Override
            public IInterface queryLocalInterface(String s) {
                return null;
            }

            @Override
            public void dump(FileDescriptor fileDescriptor, String[] strings) throws RemoteException {

            }

            @Override
            public void dumpAsync(FileDescriptor fileDescriptor, String[] strings) throws RemoteException {

            }

            @Override
            public boolean transact(int i, Parcel parcel, Parcel parcel1, int i1) throws RemoteException {
                return false;
            }

            @Override
            public void linkToDeath(DeathRecipient deathRecipient, int i) throws RemoteException {

            }

            @Override
            public boolean unlinkToDeath(DeathRecipient deathRecipient, int i) {
                return false;
            }
        };
        return ib;
    }
}
