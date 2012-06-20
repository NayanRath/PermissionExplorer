package clement.permissionexplorer;

import android.app.Activity;
import android.app.Dialog;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
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

        mPermissionsDescription = getMap();

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

            List<String> permissions = new ArrayList<String>();
            for(String permission : packageInfo.requestedPermissions)
            {
                permissions.add(prettyPrintPermission(permission));
            }

            ListView listView = (ListView)findViewById(R.id.permission_list);
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,  permissions);
            listView.setAdapter(adapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener()
            {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l)
                {
                    if(mPermissionsDescription == null)
                        return;

                    String permission = (String)adapterView.getAdapter().getItem(i);
                    try
                    {
                        String description = mPermissionsDescription.getString(permission);
                        showDialog(permission, description);
                    }
                    catch (JSONException e)
                    {
                        e.printStackTrace();
                    }
                }
            }) ;
        }
    }

    private void showDialog(String title, String description)
    {
        // custom dialog
        final Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.permission_dialog);
        dialog.setTitle(title);

        // set the custom dialog components - text, image and button
        TextView text = (TextView) dialog.findViewById(R.id.description);
        text.setText(description);

        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
        // if button is clicked, close the custom dialog
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
    private JSONObject getMap()
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

    private String prettyPrintPermission(String permission)
    {
        String formattedPermission = "";

        String[] split = permission.split("\\.");
        if(split.length >= 1)
        {
            formattedPermission = split[split.length - 1];

        }
        return formattedPermission;
    }
}