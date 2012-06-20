package clement.permissionexplorer;

/**
 * Created with IntelliJ IDEA.
 * User: clement
 * Date: 20/06/12
 * Time: 13:46
 * To change this template use File | Settings | File Templates.
 */
public class Permission
{
    private String m_name;

    public Permission(String name)
    {
        m_name = name;
    }

    public String prettyPrintName()
    {
        String formattedPermission = "";

        String[] split = getName().split("\\.");
        if(split.length >= 1)
        {
            formattedPermission = split[split.length - 1];

        }
        return formattedPermission;
    }

    public String getName()
    {
        return m_name;
    }
}
