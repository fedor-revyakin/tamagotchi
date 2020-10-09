package utilites.petproperties;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class PetData {
    /*
    Читать сохраненное значение.
     */
    public PetData() throws Exception {

        FileInputStream fileStream = new FileInputStream("mypet.ser");

        ObjectInputStream os = new ObjectInputStream(fileStream);

        petData = (Map<Enum<PropertyNameEnum>, Object>)os.readObject();

        os.close();
    }

    /*
    Создать питомца petNameEnum с начальными данными.
     */
    public PetData (Enum<NameEnum> petNameEnum) {

        petData = new HashMap<Enum<PropertyNameEnum>, Object>();

        petData.put(PropertyNameEnum.NAME, petNameEnum);
        petData.put(PropertyNameEnum.ISALIVE, true);
        petData.put(PropertyNameEnum.MOOD, MoodEnum.PERFECT);
        petData.put(PropertyNameEnum.GROWTH, GrowthEnum.LITTLE);

    }

    Map<Enum<PropertyNameEnum>, Object> petData;


    public Object getProperty(Enum<PropertyNameEnum> name) {

        return petData.get(name);
    }

    public void setProperty(Enum<PropertyNameEnum> name, Object value) {

        petData.put(name,value);
    }

    public void savePetData() throws Exception {

        FileOutputStream fileStream = new FileOutputStream("mypet.ser");

        ObjectOutputStream os = new ObjectOutputStream(fileStream);

        os.writeObject(petData);

        os.close();
    }


}