package ttv.migami.jeg.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.migami.jeg.Reference;
import ttv.migami.jeg.particles.BulletHoleData;
import ttv.migami.jeg.particles.TrailData;

/**
 * Author: MrCrayfish
 */
public class ModParticleTypes {
    public static final DeferredRegister<ParticleType<?>> REGISTER = DeferredRegister.create(ForgeRegistries.PARTICLE_TYPES, Reference.MOD_ID);

    public static final RegistryObject<ParticleType<BulletHoleData>> BULLET_HOLE = REGISTER.register("bullet_hole", () -> new ParticleType<>(false, BulletHoleData.DESERIALIZER) {
        @Override
        public Codec<BulletHoleData> codec() {
            return BulletHoleData.CODEC;
        }
    });
    public static final RegistryObject<SimpleParticleType> BLOOD = REGISTER.register("blood", () -> new SimpleParticleType(true));
    public static final RegistryObject<ParticleType<TrailData>> TRAIL = REGISTER.register("trail", () -> new ParticleType<>(false, TrailData.DESERIALIZER) {
        @Override
        public Codec<TrailData> codec() {
            return TrailData.CODEC;
        }
    });
    public static final RegistryObject<SimpleParticleType> CASING_PARTICLE = REGISTER.register("casing", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SHELL_PARTICLE = REGISTER.register("shell", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SPECTRE_CASING_PARTICLE = REGISTER.register("spectre_casing", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SCRAP = REGISTER.register("scrap", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> HEALING_GLINT = REGISTER.register("healing_glint", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GHOST_FLAME = REGISTER.register("ghost_flame", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> GHOST_GLINT = REGISTER.register("ghost_glint", () -> new SimpleParticleType(true));
}
