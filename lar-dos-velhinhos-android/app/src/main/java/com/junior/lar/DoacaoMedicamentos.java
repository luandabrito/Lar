package com.junior.lar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public class DoacaoMedicamentos extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao_medicamentos);
        requisitarDados(null);

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        requisitarDados(null);
    }

    public void voltar(View v) {
        finish();
    }

    public void confirmarDoacao(View v){
        ListView listView = (ListView) findViewById(R.id.listaMedicamento);

        if (listView.getCount() == 0){
            Context context = getApplicationContext();
            CharSequence text = "Lista de doação vazia";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0,0);
            toast.show();
        } else {
            int cont = listView.getCount();
            for (int i = 0; i < cont; i++) {
                Medicamento medicamento = (Medicamento) listView.getItemAtPosition(i);
                if (medicamento == null) {
                    break;
                }
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = "http://192.168.15.12:8181/medicamentos/" + medicamento.getId();
                StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                tratarRespostaExcluirOK();
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                tratarErro(error.getMessage());
                            }
                        });
                queue.add(stringRequest);
            }

            Context context = getApplicationContext();
            CharSequence text = "Agradecemos sua Doação de Medicamentos!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            finish();
        }
    }

    public void incluirMedicamento(View v) {
        // Pegar valores da Tela
        Spinner spinnerNome = (Spinner) findViewById(R.id.spinnerMed);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.medicamentos, android.R.layout.simple_spinner_item);

        Spinner spinnerQuantidade = (Spinner) findViewById(R.id.spinnerQTD);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.quantidade, android.R.layout.simple_spinner_item);

        Medicamento medicamento = new Medicamento();
        medicamento.setNome(spinnerNome.getSelectedItem().toString());
        medicamento.setQuantidade(spinnerQuantidade.getSelectedItem().toString());

        try {
            enviarServidor(medicamento);
        } catch (Exception e) {
            tratarErro(e.getMessage());
        }
    }
    private void enviarServidor(Medicamento medicamento) throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("nome", medicamento.getNome());
        obj.put("quantidade", medicamento.getQuantidade());
        final String json = obj.toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.15.12:8181/medicamentos";
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                //Recebimento
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tratarOK();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tratarErro(error.getMessage());
                    }
                })
        {
            //Envio do JSON
            @Override
            public String getBodyContentType() {
                return "application/json; charset=utf-8";
            }
            @Override
            public byte[] getBody() throws AuthFailureError {
                try {
                    return json.getBytes("utf-8");
                } catch (UnsupportedEncodingException uee) {
                    VolleyLog.wtf("Unsupported Encoding. Bytes of %s using %s", json, "utf-8");
                    tratarErro(uee.getMessage());
                    return null;
                }
            }
        };
        queue.add(stringRequest);
    }

    private void tratarOK() {
        onRestart();
    }

    public void requisitarDados(View v) {
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.15.12:8181/medicamentos";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        tratarResposta(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        tratarErro(error.getMessage());
                    }
                });
        queue.add(stringRequest);
    }

    private void tratarResposta(String json) {
        try {
            List<Medicamento> medicamentos = new ArrayList<>();
            JSONArray array = (JSONArray) new JSONTokener(json).nextValue();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Medicamento m = new Medicamento();
                m.setId(obj.getInt("id"));
                m.setNome(obj.getString("nome"));
                m.setQuantidade(obj.getString("quantidade"));


                medicamentos.add(m);
            }

            ListView listView = findViewById(R.id.listaMedicamento);
            final ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_checked, medicamentos);
            listView.setAdapter(adapter);
            listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        } catch (Exception ex) {
            tratarErro(ex.getMessage());
        }
    }

    private void tratarErro(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

    public void excluirMedicamento(View v) {
        ListView listView = findViewById(R.id.listaMedicamento);

        SparseBooleanArray checked = listView.getCheckedItemPositions();
        if (checked.size() == 0) {
            return;
        }
        for (int i = 0; i < checked.size(); i++){
            int position = checked.keyAt(i);
            Medicamento medicamento = (Medicamento) listView.getItemAtPosition(position);
            if (medicamento == null) {
                break;
            }
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.15.12:8181/medicamentos/" + medicamento.getId();
            StringRequest stringRequest = new StringRequest(Request.Method.DELETE, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            tratarRespostaExcluirOK();
                        }
                    },
                    new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            tratarErro(error.getMessage());
                        }
                    });
            queue.add(stringRequest);
        }

    }

    private void tratarRespostaExcluirOK() {
        requisitarDados(null);
    }

}