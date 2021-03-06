package mx.edu.utng.wsareagradoextracurricular;


import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.MarshalFloat;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpResponseException;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;


public class AreaGradoExtracurricularWS extends AppCompatActivity implements View.OnClickListener {
    private EditText etDescripcion;
    private EditText etActivo;
    private Button btGuardar;
    private Button btListar;
    private AreaGradoExtracurricular areaGradoExtracurricular = null;


    final String NAMESPACE =
            "http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL =
            "http://192.168.24.105:8080/WSAreaGradoExtracurricular/services/AreaGradoExtracurricularWS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_area_grado_extracurricular_ws);
        components();

    }

    private void components() {
        etDescripcion = (EditText) findViewById(R.id.et_descripcion);
        etActivo = (EditText) findViewById(R.id.et_activo);
        btGuardar = (Button) findViewById(R.id.bt_save);
        btListar = (Button) findViewById(R.id.bt_list);
        btGuardar.setOnClickListener(this);
        btListar.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_consume_w, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onClick(View v) {
        String descripcion = etDescripcion.getText().toString();
        String activo = etActivo.getText().toString();

        if (v.getId() == btGuardar.getId()) {
            if (descripcion != null && !descripcion.isEmpty() &&
                    activo != null && !activo.isEmpty()
                    ) {
                try {
                    if (getIntent().getExtras().getString("accion")
                            .equals("modificar")) {
                        UpdateAreaGradoExtracurricular tarea = new UpdateAreaGradoExtracurricular();
                        tarea.execute();

                    }

                } catch (Exception e) {
                    //Cuando no se haya mandado una accion por defecto es insertar.
                    InsertAreaGradoExtracurricular tarea = new InsertAreaGradoExtracurricular();
                    tarea.execute();
                }
            } else {
                Toast.makeText(this, "llenar todos los campos", Toast.LENGTH_LONG).show();
            }

        }
        if (btListar.getId() == v.getId()) {
            startActivity(new Intent(AreaGradoExtracurricularWS.this, ListAreaGradoExtracurricular.class));
        }
    }//fin conClick



    private class InsertAreaGradoExtracurricular extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addAreaGradoExtracurricular";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request =
                    new SoapObject(NAMESPACE, METHOD_NAME);

            areaGradoExtracurricular = new AreaGradoExtracurricular();
            areaGradoExtracurricular.setProperty(0, 0);
            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("areaGradoExtracurricular");
            info.setValue(areaGradoExtracurricular);
            info.setType(areaGradoExtracurricular.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "areaGradoExtracurricular", AreaGradoExtracurricular.class);

            /* Para serializar flotantes y otros tipos no cadenas o enteros*/
            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);
            try {
                transporte.call(SOAP_ACTION, envelope);
                SoapPrimitive response =
                        (SoapPrimitive) envelope.getResponse();
                String res = response.toString();
                if (!res.equals("1")) {
                    result = false;
                }

            } catch (Exception e) {
                Log.e("Error ", e.getMessage());
                result = false;
            }

            return result;
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(getApplicationContext(),
                        "Registro exitoso.",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "Error al insertar.",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }//fin tarea insertar

    private class UpdateAreaGradoExtracurricular extends
            AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "editAreaGradoExtracurricular";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            areaGradoExtracurricular = new AreaGradoExtracurricular();
            areaGradoExtracurricular.setProperty(0, getIntent().getExtras().getString("valor0"));

            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("areaGradoExtracurricular");
            info.setValue(areaGradoExtracurricular);
            info.setType(areaGradoExtracurricular.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "areaGradoExtracurricular", areaGradoExtracurricular.getClass());

            MarshalFloat mf = new MarshalFloat();
            mf.register(envelope);

            HttpTransportSE transporte = new HttpTransportSE(URL);

            try {
                transporte.call(SOAP_ACTION, envelope);

                SoapPrimitive resultado_xml = (SoapPrimitive) envelope
                        .getResponse();
                String res = resultado_xml.toString();

                if (!res.equals("1")) {
                    result = false;
                }

            } catch (HttpResponseException e) {
                Log.e("Error HTTP", e.toString());
            } catch (IOException e) {
                Log.e("Error IO", e.toString());
            } catch (XmlPullParserException e) {
                Log.e("Error XmlPullParser", e.toString());
            }

            return result;

        }

        protected void onPostExecute(Boolean result) {

            if (result) {
                Toast.makeText(getApplicationContext(), "Actualizado OK",
                        Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(getApplicationContext(), "Error al actualizar",
                        Toast.LENGTH_SHORT).show();

            }
        }
    }

    private void obtenerDatos() {
        areaGradoExtracurricular.setProperty(1, etDescripcion.getText().toString());
        areaGradoExtracurricular.setProperty(2, etActivo.getText().toString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datosRegreso = this.getIntent().getExtras();
        try {

            etDescripcion.setText(datosRegreso.getString("valor1"));
            etActivo.setText(datosRegreso.getString("valor2"));
        } catch (Exception e) {
            Log.e("Error al Recargar", e.toString());

        }

    }
}
