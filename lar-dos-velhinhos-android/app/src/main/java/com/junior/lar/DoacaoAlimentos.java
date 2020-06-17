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

public class DoacaoAlimentos extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doacao_alimentos);
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

        ListView listView = (ListView) findViewById(R.id.listaalimentos);

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
                Alimento alimento = (Alimento) listView.getItemAtPosition(i);
                if (alimento == null) {
                    break;
                }
                RequestQueue queue = Volley.newRequestQueue(this);
                String url = "http://192.168.15.12:8181/alimento/" + alimento.getId();
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
            CharSequence text = "Agradecemos sua Doação de Alimentos!";
            int duration = Toast.LENGTH_LONG;
            Toast toast = Toast.makeText(context, text, duration);
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
            toast.show();

            finish();
        }
    }

    public void incluirAlimento(View v) {
        // Pegar valores da Tela
        Spinner spinnerNome = (Spinner) findViewById(R.id.spinnerNome2);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.alimentos, android.R.layout.simple_spinner_item);

        Spinner spinnerQuantidade = (Spinner) findViewById(R.id.spinnerQuantidade2);
        ArrayAdapter adapter2 = ArrayAdapter.createFromResource(this, R.array.quantidade, android.R.layout.simple_spinner_item);

        Alimento alimento = new Alimento();
        alimento.setNome(spinnerNome.getSelectedItem().toString());
        alimento.setQuantidade(spinnerQuantidade.getSelectedItem().toString());

        try {
            enviarServidor(alimento);
        } catch (Exception e) {
            tratarErro(e.getMessage());
        }
    }
    private void enviarServidor(Alimento alimento) throws Exception {

        JSONObject obj = new JSONObject();
        obj.put("nome", alimento.getNome());
        obj.put("quantidade", alimento.getQuantidade());
        final String json = obj.toString();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="http://192.168.15.12:8181/alimento";
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
        String url ="http://192.168.15.12:8181/alimento";
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
            List<Alimento> alimentos = new ArrayList<>();
            JSONArray array = (JSONArray) new JSONTokener(json).nextValue();
            for (int i = 0; i < array.length(); i++) {
                JSONObject obj = array.getJSONObject(i);
                Alimento a = new Alimento();
                a.setId(obj.getInt("id"));
                a.setNome(obj.getString("nome"));
                a.setQuantidade(obj.getString("quantidade"));


                alimentos.add(a);
            }

            ListView listView = findViewById(R.id.listaalimentos);
            final ArrayAdapter adapter = new ArrayAdapter(this,
                    android.R.layout.simple_list_item_checked, alimentos);
            listView.setAdapter(adapter);
            listView.setChoiceMode(listView.CHOICE_MODE_MULTIPLE);
        } catch (Exception ex) {
            tratarErro(ex.getMessage());
        }
    }

    private void tratarErro(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG);
    }

    public void excluir(View v) {
        ListView listView = findViewById(R.id.listaalimentos);

        SparseBooleanArray checked = listView.getCheckedItemPositions();
        if (checked.size() == 0) {
            return;
        }
        for (int i = 0; i < checked.size(); i++){
            int position = checked.keyAt(i);
            Alimento alimento = (Alimento) listView.getItemAtPosition(position);
            if (alimento == null) {
                break;
            }
            RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.15.12:8181/alimento/" + alimento.getId();
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