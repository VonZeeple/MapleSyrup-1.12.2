package vonzeeple.maplesyrup.client.particles;

import net.minecraft.client.particle.Particle;
import net.minecraft.world.World;

public class ParticleSteam extends Particle {

    public ParticleSteam(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn, 0.0D, 0.0D, 0.0D);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;


        this.particleRed = 1.0F;
        this.particleGreen = 1.0F;
        this.particleBlue = 1.0F;
        this.setSize(0.2F, 0.2F);
        this.particleGravity = 0.004F;
        this.particleMaxAge = (int)(100);
        this.canCollide = true;
        this.particleScale = (2F);

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



        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }

        this.setParticleTextureIndex(7 - this.particleAge * 8 / this.particleMaxAge);

        this.motionY += particleGravity;
        this.move(this.motionX, this.motionY, this.motionZ);

        if (this.posY == this.prevPosY)
        {
            this.motionX *= 1.1D;
            this.motionZ *= 1.1D;
        }

        this.motionX *= 0.9599999785423279D;
        this.motionY *= 0.9599999785423279D;
        this.motionZ *= 0.9599999785423279D;

        //if (this.isCollided)
        //{
            //this.motionX *= 0.699999988079071D;
          //  this.motionZ *= 0.699999988079071D;
        //}

    }

}
