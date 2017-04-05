package mx.edu.utng.wsareagradoextracurricular;

import android.app.ListActivity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Created by JntReyes on 30/03/2017.
 */
public class ListAreaGradoExtracurricular extends ListActivity {


    final String NAMESPACE = "http://ws.utng.edu.mx";

    final SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
            SoapEnvelope.VER11);

    private ArrayList<AreaGradoExtracurricular> areas = new ArrayList<AreaGradoExtracurricular>();
    private int idSeleccionado;
    private int posicionSeleccionado;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        AreasGradoExtracurricular query = new AreasGradoExtracurricular();
        query.execute();
        registerForContextMenu(getListView());


    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_modificar:
                AreaGradoExtracurricular areaGradoExtracurricular = areas.get(posicionSeleccionado);
                Bundle bundleAreaGrado = new Bundle();
                for (int i = 0; i < areaGradoExtracurricular.getPropertyCount(); i++) {
                    bundleAreaGrado.putString("valor" + i, areaGradoExtracurricular.getProperty(i)
                            .toString());
                }
                bundleAreaGrado.putString("accion", "modificar");
                Intent intent = new Intent(ListAreaGradoExtracurricular.this, AreaGradoExtracurricularWS.class);
                intent.putExtras(bundleAreaGrado);
                startActivity(intent);

                return true;
            case R.id.item_eliminar:
                DeleteAreaGradoExtracurricular eliminar = new DeleteAreaGradoExtracurricular();
                eliminar.execute();

                return true;
            default:
                return super.onContextItemSelected(item);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = new MenuInflater(getApplicationContext());
        menuInflater.inflate(R.menu.menu_back, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_regresar:
                startActivity(new Intent(ListAreaGradoExtracurricular.this, AreaGradoExtracurricularWS.class));
                break;
            default:
                break;
        }
        return super.onMenuItemSelected(featureId, item);
    }



    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getMenuInflater();

        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;
        menu.setHeaderTitle(getListView().getAdapter().getItem(info.position).toString());
        idSeleccionado = (Integer) areas.get(info.position).getProperty(0);
        posicionSeleccionado = info.position;
        inflater.inflate(R.menu.menu_options, menu);
    }




    private class AreasGradoExtracurricular extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;
            final String METHOD_NAME = "getAreasGradoExtracurricular";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;
            areas.clear();
            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(AreaGradoExtracurricularWS.URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                Vector<SoapObject> response = (Vector<SoapObject>) envelope.getResponse();
                if (response != null) {
                    for (SoapObject objSoap : response) {
                        AreaGradoExtracurricular area = new AreaGradoExtracurricular();
                        area.setProperty(0, Integer.parseInt(objSoap.getProperty("cveAreaGradoExtracurricular").toString()));
                        area.setProperty(1, objSoap.getProperty("descripcion").toString());
                        area.setProperty(2, objSoap.getProperty("activo").toString());
                        areas.add(area);
                    }
                }

            } catch (XmlPullParserException e) {
                Log.e("Error XMLPullParser", e.toString());
                result = false;
            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());

                result = false;
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
                result = false;
            } catch (ClassCastException e) {
                try {
                    SoapObject objSoap = (SoapObject) envelope.getResponse();
                    AreaGradoExtracurricular area = new AreaGradoExtracurricular();
                    area.setProperty(0, Integer.parseInt(objSoap.getProperty("cveAreaGradoExtracurricular").toString()));
                    area.setProperty(1, objSoap.getProperty("descripcion").toString());
                    area.setProperty(2, objSoap.getProperty("activo").toString());
                    areas.add(area);
                } catch (SoapFault e1) {
                    Log.e("Error SoapFault", e.toString());
                    result = false;
                }
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                final String[] datos = new String[areas.size()];
                for (int i = 0; i < areas.size(); i++) {
                    datos[i] = areas.get(i).getProperty(0) + " - "
                            + areas.get(i).getProperty(1)+ " - "
                            + areas.get(i).getProperty(2);
                }

                ArrayAdapter<String> adaptador = new ArrayAdapter<String>(
                        ListAreaGradoExtracurricular.this,
                        android.R.layout.simple_list_item_1, datos);
                setListAdapter(adaptador);
            } else {
                Toast.makeText(getApplicationContext(), "No se encontraron datos.",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }









    private class DeleteAreaGradoExtracurricular extends AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "removeAreaGradoExtracurricular";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;


            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);
            request.addProperty("cveAreaGradoExtracurricular", idSeleccionado);

            envelope.setOutputSoapObject(request);
            HttpTransportSE transporte = new HttpTransportSE(AreaGradoExtracurricularWS.URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive resultado_xml = (SoapPrimitive) envelope.getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("0")) {
                    result = true;
                }

            } catch (Exception e) {
                Log.e("Error", e.toString());
                result = false;
            }
            return result;
        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Eliminado", Toast.LENGTH_SHORT).show();
                AreasGradoExtracurricular consulta = new AreasGradoExtracurricular();
                consulta.execute();
            } else {
                Toast.makeText(getApplicationContext(), "Error al eliminar",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }

}

