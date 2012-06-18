package clement.permissionexplorer;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MyActivity extends ListActivity
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

        /*
        try
        {

            JSONObject jsonArray = new JSONObject(new String(text));
            //jsonArray = jsonArray.getJSONObject("permission");
            //JSONArray array = jsonArray.getJSONArray("permission");
            jsonArray = jsonArray;
            for (int i = 0; i < jsonArray.length(); i++)
            {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
            }
        } catch (JSONException e)
        {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        */
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
