package github.baka943.simplewine.entity;

import github.baka943.simplewine.SimpleWine;
import github.baka943.simplewine.entity.rendering.EntityPandaRending;
import net.minecraft.client.model.ModelPolarBear;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;

import java.util.ArrayList;
import java.util.Collection;

public class EntityLoader {
    public static final ResourceLocation ENTITIES_PANDA = LootTableList.register(new ResourceLocation("simple_wine:entities/panda"));

    public static void registerEntities() {
        EntityRegistry.registerModEntity(ENTITIES_PANDA, EntityPanda.class, "entity_panda", 1, SimpleWine.instance, 80, 3, false, 16777215, 0);
    }

    public static void registerEntitiesSpawn() {
        final Collection<Biome> biomes = new ArrayList<>();
        biomes.addAll(BiomeDictionary.getBiomes(BiomeDictionary.Type.HILLS));
        Biome[] biomesArray = new Biome[biomes.size()];
        biomes.toArray(biomesArray);
        EntityRegistry.addSpawn(EntityPanda.class, 50, 1, 3, EnumCreatureType.CREATURE, biomesArray);
    }

    public static void registerEntitiesRending(){
        RenderingRegistry.registerEntityRenderingHandler(EntityPanda.class, EntityPandaRending.FACTORY);
    }
}
