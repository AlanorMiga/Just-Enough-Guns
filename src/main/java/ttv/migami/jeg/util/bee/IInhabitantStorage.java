package ttv.migami.jeg.util.bee;

import net.minecraft.nbt.ListTag;
import ttv.migami.jeg.blockentity.BeehiveBlockEntityAbstract;

import javax.annotation.Nonnull;
import java.util.List;

public interface IInhabitantStorage
{
    @Nonnull
    List<BeehiveBlockEntityAbstract.Inhabitant> getInhabitants();

    int countInhabitants();

    void setInhabitants(List<BeehiveBlockEntityAbstract.Inhabitant> inhabitantList);

    void addInhabitant(BeehiveBlockEntityAbstract.Inhabitant inhabitant);

    void clearInhabitants();

    @Nonnull
    ListTag getInhabitantListAsListNBT();

    void setInhabitantsFromListNBT(ListTag list);

    void onContentsChanged();
}
