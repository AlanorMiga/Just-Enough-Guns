package ttv.alanorMiga.jeg.datagen;

import ttv.alanorMiga.jeg.Reference;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.data.LanguageProvider;

public class LanguageGen extends LanguageProvider
{
    public LanguageGen(DataGenerator gen)
    {
        super(gen, Reference.MOD_ID, "en_us");
    }

    @Override
    protected void addTranslations()
    {
    }
}
