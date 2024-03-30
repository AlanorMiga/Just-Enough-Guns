package ttv.migami.jeg.util;

import com.google.gson.JsonObject;

/**
 * Author: MrCrayfish
 */
public class GunJsonUtil
{
    public static void addObjectIfNotEmpty(JsonObject parent, String key, JsonObject child)
    {
        if(child.size() > 0)
        {
            parent.add(key, child);
        }
    }
}
