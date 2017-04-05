package mx.edu.utng.wsareagradoextracurricular;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by JntReyes on 30/03/2017.
 */
public class AreaGradoExtracurricular implements KvmSerializable {

    private int cveAreaGradoExtracurricular;
    private String descripcion;
    private String activo;


    public AreaGradoExtracurricular(int cveAreaGradoExtracurricular, String descripcion, String activo) {
        super();
        this.cveAreaGradoExtracurricular = cveAreaGradoExtracurricular;
        this.descripcion = descripcion;
        this.activo = activo;
    }

    public AreaGradoExtracurricular() {
        this(0,"","");
    }

    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return cveAreaGradoExtracurricular;
            case 1:
                return descripcion;
            case 2:
                return activo;
        }

        return  null;
    }

    @Override
    public int getPropertyCount() {
        return 3;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                cveAreaGradoExtracurricular =Integer.parseInt(o.toString());
                break;
            case 1:
                descripcion = o.toString();
                break;
            case 2:
                activo = o.toString();
                break;

        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "cveAreaGradoExtracurricular";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "descripcion";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "activo";
                break;
            default:
                break;
        }


    }
}
