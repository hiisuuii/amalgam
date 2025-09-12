package live.hisui.amalgam.entities.renderers;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import live.hisui.amalgam.entities.TeleportGatewayEntranceEntity;
import live.hisui.amalgam.util.Util;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import org.joml.Matrix4f;

public class TeleportGatewayEntranceRenderer extends EntityRenderer<TeleportGatewayEntranceEntity> {
    private static final ResourceLocation TEXTURE_LOCATION = Util.modLoc("textures/entity/teleport_gateway/entrance.png");
    private static final RenderType RENDER_TYPE = RenderType.entityCutout(TEXTURE_LOCATION);

    public TeleportGatewayEntranceRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.shadowRadius = 0.5f;
    }

    private void vertex(VertexConsumer vc, PoseStack poseStack,
                        float x, float y, float z,
                        float u, float v, int packedLight) {
        Matrix4f mat = poseStack.last().pose();

        vc.addVertex(mat, x, y, z)
                .setColor(255, 255, 255, 255)
                .setUv(u, v)
                .setOverlay(OverlayTexture.NO_OVERLAY)
                .setLight(packedLight) // important: lightmap for shading
                .setNormal(poseStack.last(), 0.0F, 0.0F, 1.0F);
    }



    @Override
    public void render(TeleportGatewayEntranceEntity entity, float entityYaw, float partialTicks,
                       PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        poseStack.pushPose();

        // Face direction the entity is facing
        poseStack.mulPose(Axis.YP.rotationDegrees(-entityYaw));

        // Center quad on entity
        poseStack.translate(0.0D, 1.0D, 0.0D);

        // Use block/entity cutout shader so it respects lighting
        VertexConsumer vertexConsumer = buffer.getBuffer(RenderType.entityCutout(TEXTURE_LOCATION));

        float halfWidth = 0.5F; // 1 block wide
        float height = 2.0F;    // 2 blocks tall

        // Quad vertices (two triangles)
        // Front face
        vertex(vertexConsumer, poseStack, -halfWidth, -1.0F, 0.0F, 0.0F, 1.0F, packedLight);
        vertex(vertexConsumer, poseStack, halfWidth, -1.0F, 0.0F, 1.0F, 1.0F, packedLight);
        vertex(vertexConsumer, poseStack, halfWidth, height - 1.0F, 0.0F, 1.0F, 0.0F, packedLight);
        vertex(vertexConsumer, poseStack, -halfWidth, height - 1.0F, 0.0F, 0.0F, 0.0F, packedLight);

        // Back face (double sided)
        vertex(vertexConsumer, poseStack, -halfWidth, -1.0F, 0.0F, 0.0F, 1.0F, packedLight);
        vertex(vertexConsumer, poseStack, -halfWidth, height - 1.0F, 0.0F, 0.0F, 0.0F, packedLight);
        vertex(vertexConsumer, poseStack, halfWidth, height - 1.0F, 0.0F, 1.0F, 0.0F, packedLight);
        vertex(vertexConsumer, poseStack, halfWidth, -1.0F, 0.0F, 1.0F, 1.0F, packedLight);

        poseStack.popPose();
        super.render(entity, entityYaw, partialTicks, poseStack, buffer, packedLight);
    }

    @Override
    public ResourceLocation getTextureLocation(TeleportGatewayEntranceEntity entity) {
        return TEXTURE_LOCATION;
    }
}
