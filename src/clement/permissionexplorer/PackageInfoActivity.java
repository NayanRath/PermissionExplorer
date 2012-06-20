package clement.permissionexplorer;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created with IntelliJ IDEA.
 * User: Clément
 * Date: 17/06/12
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class PackageInfoActivity extends Activity
{
    private JSONObject mPermissionsDescription;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.package_info);

        mPermissionsDescription = getPermissionsDescription();

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

            List<Permission> permissions = new ArrayList<Permission>();
            for(String permission : packageInfo.requestedPermissions)
            {
                permissions.add(new Permission(permission));
            }

            ListView listView = (ListView)findViewById(R.id.permission_list);
            PermissionAdapter adapter = new PermissionAdapter(this, permissions);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    if(mPermissionsDescription == null)
                        return;

                    Permission permission = (Permission)adapterView.getAdapter().getItem(i);
                    String permissionName = permission.prettyPrintName();
                    String description = "No description available.";

                    try
                    {
                        description = mPermissionsDescription.getString(permissionName);

                    }
                    catch (JSONException e)
                    {
                        Log.w("PermissionExplorer", "No description for permission " + permission);
                    }

                    showDialog(permissionName, description);
                }
            }) ;
        }
    }

    private void showDialog(String title, String description)
    {
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.permission_dialog);
        dialog.setTitle(title);

        TextView text = (TextView) dialog.findViewById(R.id.description);
        text.setText(description);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);

        // If button is clicked, close the custom dialog.
        dialogButton.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                dialog.dismiss();
            }
        });

        dialog.show();
    }
    private JSONObject getPermissionsDescription()
    {
        String text = Utils.readTextFile(this, R.raw.permissions);

        try
        {
            JSONObject root = new JSONObject(text);
            return root.getJSONObject("permission");
        }
        catch (JSONException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}