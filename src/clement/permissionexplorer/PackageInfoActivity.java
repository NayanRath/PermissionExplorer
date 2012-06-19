package clement.permissionexplorer;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: Clément
 * Date: 17/06/12
 * Time: 21:43
 * To change this template use File | Settings | File Templates.
 */
public class PackageInfoActivity extends Activity
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

            List<String> permissions = new ArrayList(Arrays.asList(packageInfo.requestedPermissions));

            ListView listView = (ListView)findViewById(R.id.permission_list);
            PermissionAdapter adapter = new PermissionAdapter(this,  permissions);
            listView.setAdapter(adapter);
        }
    }

    private HashMap<String, String> getMap()
    {
        InputStream is = getResources().openRawResource(R.raw.permissions);

        InputStreamReader inputreader = new InputStreamReader(is);
        BufferedReader buffreader = new BufferedReader(inputreader);
        String line;
        StringBuilder text = new StringBuilder();

        try
        {
            while (( line = buffreader.readLine()) != null)
            {
                text.append(line);
                text.append('\n');
            }
        }
        catch (IOException e)
        {
        }


        try
        {
            JSONObject jsonArray = (new JSONObject(new String(text))).getJSONObject("permission");
            HashMap<String, String> a = new  HashMap<String, String>();
            Iterator itr = jsonArray.keys();
            while(itr.hasNext())
            {
                String key = (String)itr.next();
                String value = (String)jsonArray.get(key);
                a.put(key, value);
            }



            return a;
        }
        catch (JSONException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }
}