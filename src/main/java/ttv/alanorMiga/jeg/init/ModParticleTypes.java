package ttv.alanorMiga.jeg.init;

import com.mojang.serialization.Codec;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import ttv.alanorMiga.jeg.Reference;
import ttv.alanorMiga.jeg.particles.BulletHoleData;
import ttv.alanorMiga.jeg.particles.TrailData;

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
    public static final RegistryObject<SimpleParticleType> TRACER_PARTICLE = REGISTER.register("tracer", () -> new SimpleParticleType(true));
    public static final RegistryObject<SimpleParticleType> SCRAP = REGISTER.register("scrap", () -> new SimpleParticleType(true));
}
