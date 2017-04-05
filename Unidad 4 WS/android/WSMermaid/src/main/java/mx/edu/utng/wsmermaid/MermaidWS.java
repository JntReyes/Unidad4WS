package mx.edu.utng.wsmermaid;

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

public class MermaidWS extends AppCompatActivity implements View.OnClickListener {
    private EditText etName;
    private EditText etAge;
    private EditText etDescription;
    private EditText etType;
    private EditText etSing;
    private EditText etColor;
    private Button btGuardar;
    private Button btListar;
    private Mermaid mermaid = null;


    final String NAMESPACE =
            "http://ws.utng.edu.mx";
    final SoapSerializationEnvelope envelope =
            new SoapSerializationEnvelope(SoapEnvelope.VER11);
    static String URL =
            "http://192.168.24.105:8080/WSMermaid/services/MermaidWS";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mermaid_ws);
        components();

    }

    private void components() {
        etName = (EditText) findViewById(R.id.et_name);
        etAge = (EditText) findViewById(R.id.et_age);
        etDescription = (EditText) findViewById(R.id.et_description);
        etType = (EditText) findViewById(R.id.et_type);
        etSing = (EditText) findViewById(R.id.et_sing);
        etColor = (EditText) findViewById(R.id.et_color);
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
        String name = etName.getText().toString();
        String age = etAge.getText().toString();
        String description = etDescription.getText().toString();
        String type = etType.getText().toString();
        String sing = etSing.getText().toString();
        String color = etColor.getText().toString();

        if (v.getId() == btGuardar.getId()) {
            if (name != null && !name.isEmpty() &&
                    age != null && !age.isEmpty() &&
                    description != null && !description.isEmpty() &&
                    type != null && !type.isEmpty() &&
                    sing != null && !sing.isEmpty() &&
                    color != null && !color.isEmpty()) {
                try {
                    if (getIntent().getExtras().getString("accion")
                            .equals("modificar")) {
                        UpdateMermaid tarea = new UpdateMermaid();
                        tarea.execute();

                    }

                } catch (Exception e) {
                    //Cuando no se haya mandado una accion por defecto es insertar.
                    InsertMermaid tarea = new InsertMermaid();
                    tarea.execute();
                }
            } else {
                Toast.makeText(this, "llenar todos los campos", Toast.LENGTH_LONG).show();
            }

        }
        if (btListar.getId() == v.getId()) {
            startActivity(new Intent(MermaidWS.this, ListMermaid.class));
        }
    }//fin conClick



    private class InsertMermaid extends
            AsyncTask<String, Integer, Boolean> {

        @Override
        protected Boolean doInBackground(String... params) {
            boolean result = true;
            final String METHOD_NAME = "addMermaid";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request =
                    new SoapObject(NAMESPACE, METHOD_NAME);

            mermaid = new Mermaid();
            mermaid.setProperty(0, 0);
            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("mermaid");
            info.setValue(mermaid);
            info.setType(mermaid.getClass());
            request.addProperty(info);
            envelope.setOutputSoapObject(request);
            envelope.addMapping(NAMESPACE, "mermaid", Mermaid.class);

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

    private class UpdateMermaid extends
            AsyncTask<String, Integer, Boolean> {

        protected Boolean doInBackground(String... params) {

            boolean result = true;

            final String METHOD_NAME = "editMermaid";
            final String SOAP_ACTION = NAMESPACE + "/" + METHOD_NAME;

            SoapObject request = new SoapObject(NAMESPACE, METHOD_NAME);

            mermaid = new Mermaid();
            mermaid.setProperty(0, getIntent().getExtras().getString("valor0"));

            obtenerDatos();

            PropertyInfo info = new PropertyInfo();
            info.setName("mermaid");
            info.setValue(mermaid);
            info.setType(mermaid.getClass());

            request.addProperty(info);

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(
                    SoapEnvelope.VER11);

            envelope.setOutputSoapObject(request);

            envelope.addMapping(NAMESPACE, "mermaid", mermaid.getClass());

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
        mermaid.setProperty(1, etName.getText().toString());
        mermaid.setProperty(2, etAge.getText().toString());
        mermaid.setProperty(3, etDescription.getText().toString());
        mermaid.setProperty(4, etType.getText().toString());
        mermaid.setProperty(5, etSing.getText().toString());
        mermaid.setProperty(6, etColor.getText().toString());

    }

    @Override
    protected void onResume() {
        super.onResume();
        Bundle datosRegreso = this.getIntent().getExtras();
        try {

            etName.setText(datosRegreso.getString("valor1"));
            etAge.setText(datosRegreso.getString("valor2"));
            etDescription.setText(datosRegreso.getString("valor3"));
            etType.setText(datosRegreso.getString("valor4"));
            etSing.setText(datosRegreso.getString("valor5"));
            etColor.setText(datosRegreso.getString("valor6"));
        } catch (Exception e) {
            Log.e("Error al Recargar", e.toString());

        }

    }
}

