package ttv.migami.jeg.datagen;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.resources.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.common.Gun;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

/**
 * Author: MrCrayfish
 */
public abstract class GunProvider implements DataProvider
{
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().create();

    private final DataGenerator generator;
    private final Map<ResourceLocation, Gun> gunMap = new HashMap<>();

    protected GunProvider(DataGenerator generator)
    {
        this.generator = generator;
    }

    protected abstract void registerGuns();

    protected final void addGun(ResourceLocation id, Gun gun)
    {
        this.gunMap.put(id, gun);
    }

    @Override
    public void run(CachedOutput cache)
    {
        this.gunMap.clear();
        this.registerGuns();
        this.gunMap.forEach((id, gun) ->
        {
            Path path = this.generator.getOutputFolder().resolve("data/" + id.getNamespace() + "/guns/" + id.getPath() + ".json");
            try
            {
                JsonObject object = gun.toJsonObject();
                DataProvider.saveStable(cache, object, path);
            }
            catch(IOException e)
            {
                LOGGER.error("Couldn't save trades to {}", path, e);
            }
        });
    }

    @Override
    public String getName()
    {
        return "Guns: " + Reference.MOD_ID;
    }

}
