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

public class PermissionAdapter extends ArrayAdapter<String>
{
    private final Context context;
    private final List<String> values;

    public PermissionAdapter(Context context, List<String> values)
    {
        super(context, android.R.layout.simple_list_item_1, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String permission = formatPermission(values.get(position));

        TextView textView = new TextView(getContext());
        textView.setText(permission);

        return textView;
    }

    private String formatPermission(String permission)
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
