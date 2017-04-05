package mx.edu.utng.wsarqueocaja;

import org.ksoap2.serialization.KvmSerializable;
import org.ksoap2.serialization.PropertyInfo;

import java.util.Hashtable;

/**
 * Created by JntReyes on 04/04/2017.
 */
public class ArqueoCaja implements KvmSerializable {

    private int cveArqueo;
    private int cveUnidadAcademica;
    private String fechaArqueo;
    private String observaciones;
    private float total;
    private String personal;


    public ArqueoCaja(int cveArqueo, int cveUnidadAcademica, String fechaArqueo, String observaciones, float total, String personal) {
        this.cveArqueo = cveArqueo;
        this.cveUnidadAcademica = cveUnidadAcademica;
        this.fechaArqueo = fechaArqueo;
        this.observaciones = observaciones;
        this.total = total;
        this.personal = personal;
    }

    public ArqueoCaja() {
        this(0,0,"","",0.0F,"");
    }


    @Override
    public Object getProperty(int i) {
        switch (i) {
            case 0:
                return cveArqueo;
            case 1:
                return cveUnidadAcademica;
            case 2:
                return fechaArqueo;
            case 3:
                return observaciones;
            case 4:
                return total;
            case 5:
                return personal;

        }

        return  null;
    }

    @Override
    public int getPropertyCount() {
        return 6;
    }

    @Override
    public void setProperty(int i, Object o) {
        switch (i){
            case 0:
                cveArqueo =Integer.parseInt(o.toString());
                break;
            case 1:
                cveUnidadAcademica = Integer.parseInt(o.toString());
                break;
            case 2:
                fechaArqueo = o.toString();
                break;
            case 3:
                observaciones = o.toString();
                break;
            case 4:
                total = Float.parseFloat(o.toString());
                break;
            case 5:
                personal = o.toString();
                break;
            default:
                break;
        }
    }

    @Override
    public void getPropertyInfo(int i, Hashtable hashtable, PropertyInfo propertyInfo) {
        switch (i) {
            case 0:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "cveArqueo";
                break;
            case 1:
                propertyInfo.type = PropertyInfo.INTEGER_CLASS;
                propertyInfo.name = "cveUnidadAcademica";
                break;
            case 2:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "fechaArqueo";
                break;
            case 3:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "observaciones";
                break;
            case 4:
                propertyInfo.type = Float.class;
                propertyInfo.name = "total";
                break;
            case 5:
                propertyInfo.type = PropertyInfo.STRING_CLASS;
                propertyInfo.name = "personal";
                break;
            default:
                break;
        }


    }
}

