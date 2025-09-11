package live.hisui.amalgam.datagen;

import live.hisui.amalgam.Amalgam;
import live.hisui.amalgam.items.ModItems;
import live.hisui.amalgam.util.Util;
import net.minecraft.data.PackOutput;
import net.minecraft.world.item.Item;
import net.neoforged.neoforge.common.data.LanguageProvider;
import net.neoforged.neoforge.registries.DeferredItem;

public class ModLangProvider extends LanguageProvider {
    public ModLangProvider(PackOutput output) {
        super(output, Amalgam.MODID, "en_us");
    }

    @Override
    protected void addTranslations() {
        addSimple(ModItems.MAGIC_STEAK);
        addSimple(ModItems.ENCHANTING_POWDER);
        addSimple(ModItems.MAGIC_FEATHER);
        addSimple(ModItems.GLORP);
        addSimple(ModItems.AMBROSIA);
        addSimple(ModItems.GLORP_BLOCK);

        add("command.amalgam.name.set","Changed %1$s's name to \"%2$s\"");
        add("command.amalgam.name.clear","Cleared %1$s's name");
        add("command.amalgam.skin.set","Changed %1$s's skin to %2$s's skin");
        add("command.amalgam.skin.clear","Changed %1$s's skin");
        add("amalgam.itemGroup.amalgam","Amalgam");
    }

    private void addSimple(DeferredItem<? extends Item> item){
        String name = Util.formatString(item.getId().getPath());
        this.add(item.get(), name);
    }

}
