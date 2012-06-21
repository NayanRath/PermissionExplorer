package clement.permissionexplorer;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

public class PackageInfoAdapter extends ArrayAdapter<PackageInfo>
{
    private final Context context;
    private final List<PackageInfo> packageInfoList;

    public PackageInfoAdapter(Context context, List<PackageInfo> values)
    {
        super(context, R.layout.row, values);
        this.context = context;

        packageInfoList = new ArrayList<PackageInfo>(values);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = layoutInflater.inflate(R.layout.row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);

        String appName = getItem(position).applicationInfo.loadLabel(getContext().getPackageManager()).toString();
        Drawable appIcon =  getItem(position).applicationInfo.loadIcon(getContext().getPackageManager());
        textView.setText(appName);
        imageView.setImageDrawable(appIcon);

        return rowView;
    }

    public void showSystemApplications(boolean value)
    {
        List<PackageInfo> newValues = null;

        if(value == true)
        {
            newValues = new ArrayList<PackageInfo>(packageInfoList);
        }
        else
        {
            newValues = filterOutSystemApplications(packageInfoList);
        }

        clear();
        addAll(newValues);

        notifyDataSetChanged();
    }

    private List<PackageInfo> filterOutSystemApplications(List<PackageInfo> values)
    {
        List<PackageInfo> newValues = new ArrayList<PackageInfo>();

        for(PackageInfo packageInfo: values)
        {
            int isSystemApp =  packageInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM;
            if(isSystemApp == 0)
            {
                newValues.add(packageInfo);
            }
        }

        return newValues;
    }
}
