package clement.permissionexplorer;

import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import java.util.List;

public class MainActivity extends ListActivity
{
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        PackageManager p = getPackageManager();
        List<PackageInfo> appInstall = p.getInstalledPackages(PackageManager.GET_PERMISSIONS | PackageManager.GET_PROVIDERS);

        PackageInfoAdapter adapter = new PackageInfoAdapter(this, appInstall);
        setListAdapter(adapter);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id)
    {
        PackageInfo packageInfo = (PackageInfo)l.getAdapter().getItem(position);

        Intent intent = new Intent(this, PackageInfoActivity.class);
        intent.putExtra("packageInfo", packageInfo);

        this.startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.action_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_hide:
                return ToggleSystemApplications(item);
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private boolean ToggleSystemApplications(MenuItem item)
    {
        PackageInfoAdapter adapter = (PackageInfoAdapter)getListAdapter();

        if(item.getTitle().equals("Hide"))
        {
            adapter.showSystemApplications(false);
            item.setTitle("Show");
        }
        else
        {
            adapter.showSystemApplications(true);
            item.setTitle("Hide");
        }

        return true;
    }
}
