package clement.permissionexplorer;

import android.content.Context;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class Utils
{
    public static String readTextFile(Context context, int resource)
    {
        InputStream inputStream = context.getResources().openRawResource(resource);

        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader bufferedReader = new BufferedReader(inputReader);

        String line;
        StringBuilder text = new StringBuilder();

        try
        {
            while ((line = bufferedReader.readLine()) != null)
            {
                text.append(line);
                text.append('\n');
            }
        } catch (IOException e)
        {
        }

        return new String(text);
    }
}