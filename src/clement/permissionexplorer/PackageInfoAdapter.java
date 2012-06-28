package clement.permissionexplorer;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PackageInfoAdapter extends ArrayAdapter<PackageInfo>
{
    static class ViewHolder
    {
        public TextView textView;
        public ImageView imageView;
    }

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
        View rowView = convertView;
        if (rowView == null)
        {
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = layoutInflater.inflate(R.layout.row, parent, false);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) rowView.findViewById(R.id.name);
            viewHolder.imageView = (ImageView) rowView.findViewById(R.id.imageView1);

            rowView.setTag(viewHolder);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();

        String appName = getItem(position).applicationInfo.loadLabel(getContext().getPackageManager()).toString();
        Drawable appIcon =  getItem(position).applicationInfo.loadIcon(getContext().getPackageManager());
        holder.textView.setText(appName);
        holder.imageView.setImageDrawable(appIcon);

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

    @Override
    public Filter getFilter()
    {
        return new Filter()
        {
            @Override
            protected Filter.FilterResults performFiltering(CharSequence charSequence)
            {
                FilterResults results = new FilterResults();
                List<PackageInfo> filteredResults = new ArrayList<PackageInfo>();
                for(PackageInfo packageInfo: packageInfoList)
                {
                    String appName = packageInfo.applicationInfo.loadLabel(getContext().getPackageManager()).toString();
                    if(appName.contains(charSequence))
                    {
                        filteredResults.add(packageInfo);
                    }
                }

                results.values =  filteredResults;
                return results;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults)
            {
                final ArrayList<PackageInfo> localItems = (ArrayList<PackageInfo>) filterResults.values;
                notifyDataSetChanged();
                clear();

                for (Iterator iterator = localItems.iterator(); iterator.hasNext();)
                {
                    PackageInfo gi = (PackageInfo) iterator.next();
                    add(gi);
                }
            }
        };
    }
}
