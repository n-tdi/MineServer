package world.ntdi.api.pet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import world.ntdi.api.pet.pets.CreeperPet;

import java.lang.reflect.InvocationTargetException;

@Getter
@AllArgsConstructor
public enum Pets {
    CREEPER(CreeperPet.class);

    private Class<? extends CustomPet> m_class;

    public CustomPet create() {
        try {
            return m_class.getDeclaredConstructor().newInstance();
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException | NoSuchMethodException p_e) {
            throw new RuntimeException(p_e);
        }
        return null;
    }
}
