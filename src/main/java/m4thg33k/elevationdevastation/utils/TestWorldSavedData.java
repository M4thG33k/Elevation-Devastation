package m4thg33k.elevationdevastation.utils;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraft.world.storage.MapStorage;
import net.minecraft.world.storage.WorldSavedData;

public class TestWorldSavedData extends WorldSavedData {
    private static final String DATA_NAME_BASE = Constants.MODID + "_TestData_";

    public TestWorldSavedData(int dimId){
        super(DATA_NAME_BASE + dimId);
    }

    public TestWorldSavedData(String name) {
        super(name);
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {

    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        return null;
    }

    public static TestWorldSavedData get(World world){
        MapStorage storage = world.getPerWorldStorage();
        int dimId = world.provider.getDimension();
        String dataName = DATA_NAME_BASE + dimId;
        TestWorldSavedData instance = (TestWorldSavedData) storage.getOrLoadData(TestWorldSavedData.class, dataName);

        if (instance == null){
            instance = new TestWorldSavedData(dimId);
            storage.setData(dataName, instance);
        }

        return instance;
    }
}
