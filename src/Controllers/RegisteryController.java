package Controllers;

import java.util.HashMap;

/**
 * Created by Arjen on 1/28/2017.
 */
public class RegisteryController {

    private static RegisteryController Instance = null;
    private HashMap<String, AbstractController> ControllerObjects;

    private RegisteryController(){
        this.Instance = this;
        this.ControllerObjects = new HashMap<>();
    }

    public static RegisteryController getInstance()
    {
        if(Instance == null)
            new RegisteryController();

        return Instance;
    }

    public void addObjectReference(AbstractController o)
    {
        String[] name = o.getClass().getName().split("\\.");
        ControllerObjects.put(name[1],o);
    }

    public Object getObjectInstance(String ObjectName)
    {
        if(ControllerObjects.get(ObjectName) != null)
            return ControllerObjects.get(ObjectName);

        return null;
    }


}
