package vonzeeple.maplesyrup.client.particles;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;


import java.awt.*;

public class ParticleSap extends Particle {


    /** The height of the current bob */
    private int bobTimer;

    public ParticleSap(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn, 0.0D, 0.0D, 0.0D);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;


        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;

        this.setParticleTextureIndex(113);
        this.setSize(0.2F, 0.2F);
        this.particleGravity = 0.06F;
        this.particleMaxAge = (int)(43);
        this.bobTimer = 40;
        this.canCollide = false;
        this.particleScale = (0.5F);
    }

    public int getBrightnessForRender(float p_189214_1_)
    {
        return super.getBrightnessForRender( p_189214_1_);
    }

    @Override
    public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;


        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;


        this.motionY -= (double)this.particleGravity;

        if (this.bobTimer-- > 0)
        {
            this.motionY *= 0.005D;
            this.setParticleTextureIndex(113);//113
        }
        else
        {
            this.setParticleTextureIndex(112);//112
        }

        this.move(this.motionX, this.motionY, this.motionZ);



        if (this.particleMaxAge-- <= 0)
        {
            this.setExpired();
        }



    }

}
