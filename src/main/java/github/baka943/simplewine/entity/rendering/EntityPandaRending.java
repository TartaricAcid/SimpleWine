package github.baka943.simplewine.entity.rendering;

import github.baka943.simplewine.entity.EntityPanda;
import net.minecraft.client.model.ModelPolarBear;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.client.registry.IRenderFactory;


public class EntityPandaRending extends RenderLiving<EntityPanda> {
    private static final ResourceLocation TEXTURE = new ResourceLocation("simple_wine", "textures/entity/panda.png");
    public static final Factory FACTORY = new Factory();

    public EntityPandaRending (RenderManager rendermanagerIn){
        super(rendermanagerIn, new ModelPolarBear(), 0.7f);
    }

    @Override
    protected ResourceLocation getEntityTexture (EntityPanda entity) {
        return TEXTURE;
    }

    public static class Factory implements IRenderFactory<EntityPanda> {
        @Override
        public Render<? super EntityPanda> createRenderFor(RenderManager manager) {
            return new EntityPandaRending(manager);
        }
    }
}

