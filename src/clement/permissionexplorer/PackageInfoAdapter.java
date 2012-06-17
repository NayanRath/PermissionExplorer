package clement.permissionexplorer;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class PackageInfoAdapter extends ArrayAdapter<PackageInfo>
{
    private final Context context;
    private final List<PackageInfo> values;

    public PackageInfoAdapter(Context context, List<PackageInfo> values)
    {
        super(context, R.layout.row, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.row, parent, false);
        TextView textView = (TextView) rowView.findViewById(R.id.name);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imageView1);

        String appname = values.get(position).applicationInfo.loadLabel(getContext().getPackageManager()).toString();
        Drawable appIcon =  values.get(position).applicationInfo.loadIcon(getContext().getPackageManager());
        textView.setText(appname);
        imageView.setImageDrawable(appIcon);

        return rowView;
    }
}
