package clement.permissionexplorer;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import java.util.List;

public class MainActivity extends ListActivity
{
    private List<PackageInfo> appinstall;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        PackageManager p = getPackageManager();
        appinstall = p.getInstalledPackages(PackageManager.GET_PERMISSIONS | PackageManager.GET_PROVIDERS);

        PackageInfoAdapter adapter = new PackageInfoAdapter(this, appinstall);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        PackageInfo packageInfo = appinstall.get(position);

        Intent intent = new Intent(this, PackageInfoView.class);
        intent.putExtra("packageInfo", packageInfo);

        this.startActivity(intent);
    }
}
