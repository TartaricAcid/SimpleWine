package github.baka943.simplewine.entity;

import github.baka943.simplewine.block.BlockLoader;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityPolarBear;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityPanda extends EntityPolarBear {
    public EntityPanda (World world){
        super(world);
        this.setSize(1.0f, 1.1f);
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageable)
    {
        return new EntityPanda(this.world);
    }

    @Override
    public boolean isBreedingItem(ItemStack stack){
        return stack.getItem() == Item.getItemFromBlock(BlockLoader.blockBamboo);
    }

    @Override
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(30.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(20.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }
}
