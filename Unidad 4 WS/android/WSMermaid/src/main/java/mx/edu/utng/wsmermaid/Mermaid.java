package mx.edu.utng.wsmermaid;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by JntReyes on 31/03/2017.
 */
public class Mermaid implements KvmSerializable {

    private int id;
    private String name;
    private String age;
    private String description;
    private String type;
    private String sing;
    private String color;


    public Mermaid(int id, String name, String age, String description, String type, String sing, String color) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.description = description;
        this.type = type;
        this.sing = sing;
        this.color = color;
    }

    public Mermaid() {
        this(0,"","","","","","");
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return id;
            case 1:
                return name;
            case 2:
                return age;
            case 3:
                return description;
            case 4:
                return type;
            case 5:
                return sing;
            case 6:
                return color;
        }

        return  null;
    }

    @Override
    public int getPropertyCount() {
        return 7;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                id =Integer.parseInt(o.toString());
                break;
            case 1:
                name = o.toString();
                break;
            case 2:
                age = o.toString();
                break;
            case 3:
                description = o.toString();
                break;
            case 4:
                type = o.toString();
                break;
            case 5:
                sing = o.toString();
                break;
            case 6:
                color = o.toString();
                break;

        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "id";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "name";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "age";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "description";
                break;
            case 4:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "type";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "sing";
                break;
            case 6:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "color";
                break;
            default:
                break;
        }


    }
}
