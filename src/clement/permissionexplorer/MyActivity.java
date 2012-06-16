package clement.permissionexplorer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.List;

public class MyActivity extends ListActivity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        PackageManager p = getPackageManager();
        final List<PackageInfo> appinstall =
                p.getInstalledPackages(PackageManager.GET_PERMISSIONS |
                        PackageManager.GET_PROVIDERS);

        PackageInfoAdapter adapter = new PackageInfoAdapter(this, appinstall);
        setListAdapter(adapter);
    }
}
