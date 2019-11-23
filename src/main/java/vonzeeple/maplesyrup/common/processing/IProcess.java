package vonzeeple.maplesyrup.common.processing;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistryEntry;

interface IProcess<T> extends  IForgeRegistryEntry<T>{

    boolean isValid();

    T setRegistryName(ResourceLocation name);
}
