package ttv.migami.jeg.datagen;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.EntityTypeTagsProvider;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.data.ExistingFileHelper;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.common.ModTags;
import ttv.migami.jeg.init.ModEntities;

public class EntityTagGen extends EntityTypeTagsProvider
{
    public EntityTagGen(DataGenerator generator, ExistingFileHelper existingFileHelper)
    {
        super(generator, Reference.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags()
    {
        this.tag(ModTags.Entities.HEAVY)
                .add(EntityType.GHAST)
                .add(EntityType.HOGLIN)
                .add(EntityType.POLAR_BEAR)
                .add(EntityType.RAVAGER)
                .add(EntityType.TURTLE)
                .add(EntityType.ZOGLIN);

        this.tag(ModTags.Entities.VERY_HEAVY)
                .add(EntityType.ELDER_GUARDIAN)
                .add(EntityType.ENDER_DRAGON)
                .add(EntityType.WARDEN)
                .add(EntityType.WITHER);

        this.tag(ModTags.Entities.UNDEAD)
                .add(EntityType.ZOMBIE)
                .add(EntityType.DROWNED)
                .add(EntityType.HUSK)
                .add(EntityType.PHANTOM)
                .add(EntityType.SKELETON)
                .add(EntityType.SKELETON_HORSE)
                .add(EntityType.STRAY)
                .add(EntityType.WITHER)
                .add(EntityType.WITHER_SKELETON)
                .add(EntityType.ZOGLIN)
                .add(EntityType.ZOMBIE)
                .add(EntityType.ZOMBIE_HORSE)
                .add(EntityType.ZOMBIFIED_PIGLIN)
                .add(EntityType.ZOMBIE_VILLAGER)
                .add(ModEntities.GHOUL.get())
                .add(ModEntities.BOO.get());

        this.tag(ModTags.Entities.GHOST)
                .add(ModEntities.BOO.get());

        this.tag(ModTags.Entities.FIRE)
                .add(EntityType.BLAZE)
                .add(EntityType.GHAST)
                .add(EntityType.HOGLIN)
                .add(EntityType.HUSK)
                .add(EntityType.MAGMA_CUBE)
                .add(EntityType.PIGLIN)
                .add(EntityType.PIGLIN_BRUTE)
                .add(EntityType.STRIDER)
                .add(EntityType.WITHER)
                .add(EntityType.WITHER_SKELETON);
    }
}
