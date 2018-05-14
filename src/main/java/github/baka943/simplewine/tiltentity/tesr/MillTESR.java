package github.baka943.simplewine.tiltentity.tesr;

import github.baka943.simplewine.block.BlockLoader;
import github.baka943.simplewine.block.BlockMill;
import github.baka943.simplewine.tiltentity.TileEntityMill;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.world.World;
import org.lwjgl.opengl.GL11;

public class MillTESR extends TileEntitySpecialRenderer<TileEntityMill> {
    @Override
    public void render(TileEntityMill te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        // 貌似是获取相关东西
        GlStateManager.pushAttrib();
        GlStateManager.pushMatrix();

        // 转换成本地坐标，禁用法线变化？
        GlStateManager.translate(x, y, z);
        GlStateManager.disableRescaleNormal();

        // 渲染磨盘上半部分
        renderHandles(te);

        // 推回相关东西
        GlStateManager.popMatrix();
        GlStateManager.popAttrib();
    }

    private void renderHandles(TileEntityMill te) {
        // 貌似是获取相关东西
        GlStateManager.pushMatrix();

        // 转换坐标
        GlStateManager.translate(.5, 0, .5);
        long angle = (System.currentTimeMillis() / 40) % 360; // 貌似是通过系统时间来获取角度
        GlStateManager.rotate(angle, 0f, 1f, 0f); // 旋转，轴线为(0,1,0)

        RenderHelper.disableStandardItemLighting(); // 禁用标准物品光照，不明所以
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE); // 地图上显示样子？

        // 判定 Minecraft 是否开启了“环境光遮蔽”，来定义阴影平滑程度
        if (Minecraft.isAmbientOcclusionEnabled()) {
            GlStateManager.shadeModel(GL11.GL_SMOOTH);
        } else {
            GlStateManager.shadeModel(GL11.GL_FLAT);
        }

        World world = te.getWorld();  // 获取TE所在的世界
        // 转换回本地视图坐标
        GlStateManager.translate(-te.getPos().getX(), -te.getPos().getY(), -te.getPos().getZ());

        // 构造一个镶嵌器？实例？做撒子不知道……
        Tessellator tessellator = Tessellator.getInstance();
        // 获取？顶点缓冲？那是啥
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        // 终于到绘图了，用的是GL的四边形绘图
        bufferBuilder.begin(GL11.GL_QUADS, DefaultVertexFormats.BLOCK);

        // 获取石磨的上半部分
        IBlockState state = BlockLoader.blockMill.getDefaultState().withProperty(BlockMill.IS_TOP, true);
        // 获取方块渲染调度器？那是做撒子的
        BlockRendererDispatcher dispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        // 从这个 state 里面获取模型
        IBakedModel model = dispatcher.getModelForState(state);
        // 通过顶点缓冲来渲染它？
        dispatcher.getBlockModelRenderer().renderModel(world, model, state, te.getPos(), bufferBuilder, true);
        // 实际渲染？
        tessellator.draw();

        // 启用标准物品渲染，这是渲染好了再开启？
        RenderHelper.enableStandardItemLighting();
        // 推回什么东西？
        GlStateManager.popMatrix();
    }
}
