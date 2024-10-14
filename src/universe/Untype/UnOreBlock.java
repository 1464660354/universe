package universe.Untype;

import arc.Core;
import arc.graphics.Color;
import arc.graphics.Pixmap;
import arc.graphics.g2d.PixmapRegion;
import mindustry.graphics.MultiPacker;
import mindustry.type.Item;
import mindustry.world.blocks.environment.OreBlock;
import universe.Annotations;

import static mindustry.Vars.tilesize;

public class UnOreBlock extends OreBlock {
    public UnOreBlock(String name, Item ore){
        super(name);
        this.localizedName = ore.localizedName;
        this.itemDrop = ore;
        this.variants = 3;
        this.mapColor.set(ore.color);
        this.useColor = true;
    }

    @Override
    @Annotations.OverrideCallSuper
    public void createIcons(MultiPacker packer){
        for(int i = 0; i < variants; i++){
            //use name (e.g. "ore-copper1"), fallback to "copper1" as per the old naming system
            PixmapRegion shadow = Core.atlas.has(name + (i + 1)) ?
                    Core.atlas.getPixmap(name + (i + 1)) :
                    Core.atlas.getPixmap(itemDrop.name + (i + 1));

            Pixmap image = shadow.crop();

            int offset = image.width / tilesize - 1;
            int shadowColor = Color.rgba8888(0, 0, 0, 0.3f);

            for(int x = 0; x < image.width; x++){
                for(int y = offset; y < image.height; y++){
                    if(shadow.getA(x, y) == 0 && shadow.getA(x, y - offset) != 0){
                        image.setRaw(x, y, shadowColor);
                    }
                }
            }

            packer.add(MultiPacker.PageType.environment, name + (i + 1), image);
            packer.add(MultiPacker.PageType.editor, "editor-" + name + (i + 1), image);

            if(i == 0){
                packer.add(MultiPacker.PageType.editor, "editor-block-" + name + "-full", image);
                packer.add(MultiPacker.PageType.main, "block-" + name + "-full", image);
            }

            image.dispose();
        }
    }
}
