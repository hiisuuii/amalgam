package live.hisui.amalgam.mixin.client;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.authlib.yggdrasil.ProfileResult;
import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.AmalgamClient;
import live.hisui.amalgam.attachments.ModDataAttachments;
import net.minecraft.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.resources.DefaultPlayerSkin;
import net.minecraft.client.resources.PlayerSkin;
import net.minecraft.client.resources.SkinManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.UUIDUtil;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Mixin(AbstractClientPlayer.class)
public abstract class GetPlayerSkinEvent extends Player {
    public GetPlayerSkinEvent(Level level, BlockPos pos, float yRot, GameProfile gameProfile) {
        super(level, pos, yRot, gameProfile);
    }


    @ModifyReturnValue(method = "getSkin", at = @At("RETURN"))
    private PlayerSkin getSkin(PlayerSkin original){
        Optional<String> nameToUse = this.getData(ModDataAttachments.DISGUISE_SKIN).skinName();
        if(nameToUse.isPresent()) {

            PlayerSkin customSkin = amalgam$getSkinFromUsername(nameToUse.get());
            if (customSkin != null) {
                return customSkin;
            }

        }
        return original;
    }

    @Unique
    private PlayerSkin amalgam$getSkinFromUsername(String username) {
        // First, try to get a cached, complete profile
        CompletableFuture<GameProfile> profileFuture = amalgam$getCompleteGameProfile(username);

        // Get the profile if it's already loaded
        GameProfile completeProfile = profileFuture.getNow(null);
        if (completeProfile != null) {
            // We have a complete profile, get the skin
            SkinManager skinManager = Minecraft.getInstance().getSkinManager();
            return skinManager.getInsecureSkin(completeProfile);
        }

        // Profile is still loading, return default skin for now
        UUID fallbackUuid = UUIDUtil.createOfflinePlayerUUID(username);
        return DefaultPlayerSkin.get(fallbackUuid);
    }

    @Unique
    private CompletableFuture<GameProfile> amalgam$getCompleteGameProfile(String username) {
        return AmalgamClient.profileCache.computeIfAbsent(username.toLowerCase(), this::amalgam$fetchCompleteProfile);
    }

    @Unique
    private CompletableFuture<GameProfile> amalgam$fetchCompleteProfile(String username) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                // Try the user cache first
                Optional<GameProfile> cachedProfile = Optional.empty();

                // If on integrated server, check server cache
                MinecraftServer server = Minecraft.getInstance().getSingleplayerServer();
                if (server != null) {
                    cachedProfile = server.getProfileCache().get(username);
                }

                GameProfile profile;
                if (cachedProfile.isPresent()) {
                    Amalgam.LOGGER.debug("Profile is present in cache");
                    profile = cachedProfile.get();
                } else {
                    // Fetch UUID from Mojang
                    Amalgam.LOGGER.debug("Fetching UUID from mojang API");
                    UUID uuid = amalgam$fetchUuidFromMojang(username);
                    if (uuid == null) return null;
                    profile = new GameProfile(uuid, username);
                }

                // Fill with texture properties
                MinecraftSessionService sessionService = Minecraft.getInstance().getMinecraftSessionService();
                ProfileResult profileResult = sessionService.fetchProfile(profile.getId(), false);
                if(profileResult != null) {
                    return profileResult.profile();
                }
                return null;

            } catch (Exception e) {
                Amalgam.LOGGER.warn("Failed to fetch complete profile for: {}", username, e);
                return null;
            }
        }, Util.backgroundExecutor());
    }

    @Unique
    private UUID amalgam$fetchUuidFromMojang(String username) {
        try {
            Amalgam.LOGGER.debug("WARNING! Sending stuff to Mojang API by HTTP! This is really not ideal, please open an issue on github if this happens!");
            // Make HTTP request to: https://api.mojang.com/users/profiles/minecraft/{username}

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("https://api.mojang.com/users/profiles/minecraft/" + username))
                    .build();

            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() == 200) {
                JsonObject json = JsonParser.parseString(response.body()).getAsJsonObject();
                String uuidString = json.get("id").getAsString();

                client.close();
                // Convert UUID string to UUID object (add dashes (I think theyre necessary? I havent had this method go off yeT))
                return UUID.fromString(
                        uuidString.replaceFirst(
                                "(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)",
                                "$1-$2-$3-$4-$5"
                        )
                );
            }
        } catch (Exception e) {
            Amalgam.LOGGER.warn("Failed to fetch UUID for username: {}", username, e);
        }
        return null;
    }

}
