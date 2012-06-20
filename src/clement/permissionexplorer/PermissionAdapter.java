package clement.permissionexplorer;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class PermissionAdapter extends ArrayAdapter<Permission>
{
    private final Context context;
    private final List<Permission> values;

    public PermissionAdapter(Context context, List<Permission> values)
    {
        super(context, R.layout.row, values);
        this.context = context;
        this.values = values;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        Permission permission = values.get(position);

        TextView textView = new TextView(context);
        textView.setText(permission.prettyPrintName());

        return textView;
    }
}
