package Controllers;

import com.sun.deploy.ref.AppModel;
import com.sun.org.apache.bcel.internal.generic.ObjectType;

import java.util.HashMap;

/**
 * Created by Arjen on 1/28/2017.
 */
public class RegisteryController {

    private static RegisteryController Instance = null;
    private HashMap<String, Object> ObjectReferences;

    private RegisteryController(){
        this.Instance = this;
        this.ObjectReferences = new HashMap<>();
    }

    public static RegisteryController getInstance()
    {
        if(Instance == null)
            new RegisteryController();

        return Instance;
    }

    public void addObjectReference(Object o)
    {
        ObjectReferences.put(o.getClass().getName(),o);
    }

    public <T> Object getObjectInstance(String ObjectName)
    {
        if(ObjectReferences.get(ObjectName) != null)
            return ObjectReferences.get(ObjectName);

        return null;
    }


}
