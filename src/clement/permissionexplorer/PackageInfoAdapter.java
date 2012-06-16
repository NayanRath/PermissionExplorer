package clement.permissionexplorer;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PackageInfoAdapter extends ArrayAdapter<PackageInfo> {
    private final Context context;
    private final List<PackageInfo> values;

    public PackageInfoAdapter(Context context, List<PackageInfo> values)
    {
        super(context, android.R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        TextView view = new TextView(getContext());

        String appname = values.get(position).applicationInfo.loadLabel(getContext().getPackageManager()).toString();
        view.setText(appname);
        return view;
    }
}
