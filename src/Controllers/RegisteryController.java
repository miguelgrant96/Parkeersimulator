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

    public static RegisteryController GetInstance()
    {
        if(Instance == null)
            new RegisteryController();

        return Instance;
    }

    public void AddObjectReference(Object o)
    {
        ObjectReferences.put(o.getClass().getName(),o);
    }

    public <T> Object GetObjectInstance(String ObjectName)
    {
        if(ObjectReferences.get(ObjectName) != null)
            return ObjectReferences.get(ObjectName);

        return null;
    }


}
