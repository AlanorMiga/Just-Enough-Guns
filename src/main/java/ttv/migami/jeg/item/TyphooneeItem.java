package ttv.migami.jeg.item;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;

public class TyphooneeItem extends UnderwaterFirearmItem {
    public TyphooneeItem(Properties properties) {
        super(properties);
    }

    public static boolean canDamage(Entity entity) {

        return entity instanceof LivingEntity;

    }
}
