package ttv.migami.jeg.util.bee;

import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;

public class CapabilityBee
{
    public static net.minecraftforge.common.capabilities.Capability<IInhabitantStorage> BEE = null;

    public CapabilityBee() {
        BEE = CapabilityManager.get(new CapabilityToken<>() {});
    }
}
