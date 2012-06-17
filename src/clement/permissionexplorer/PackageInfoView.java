package clement.permissionexplorer;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Clément
 * Date: 17/06/12
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class PackageInfoView extends Activity
{
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_info);

        Bundle extras = getIntent().getExtras();
        if(extras !=null)
        {
            PackageInfo packageInfo = (PackageInfo)extras.getParcelable("packageInfo");

            TextView appNameView = (TextView)findViewById(R.id.appName);
            ImageView appIconView = (ImageView)findViewById(R.id.appIcon);

            String appName = packageInfo.applicationInfo.loadLabel(getPackageManager()).toString();
            Drawable appIcon =  packageInfo.applicationInfo.loadIcon(getPackageManager());

            appNameView.setText(appName);
            appIconView.setImageDrawable(appIcon);


            ListView listView = (ListView)findViewById(R.id.permission_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_list_item_1, packageInfo.requestedPermissions);
            listView.setAdapter(adapter);
        }
    }
}