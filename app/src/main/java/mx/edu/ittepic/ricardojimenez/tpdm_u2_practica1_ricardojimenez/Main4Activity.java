package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica1_ricardojimenez;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Main4Activity extends AppCompatActivity {
    EditText desc, ub,fe,pre;
    Button regresar,actualizar;
    int id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);
        //referencias
        desc = findViewById(R.id.actualizaDescripcion);
        ub = findViewById(R.id.actualizaUbicacion);
        fe = findViewById(R.id.actualizaFecha);
        pre = findViewById(R.id.actualizaPresupuesto);
        regresar = findViewById(R.id.regresaActualizar);
        actualizar = findViewById(R.id.actualizaProyecto);

        Bundle parametros = getIntent().getExtras();
        desc.setText(parametros.getString("descripcion"));
        ub.setText(parametros.getString("ubicacion"));
        fe.setText(parametros.getString("fecha"));
        pre.setText(""+parametros.getFloat("presupuesto"));
        id = parametros.getInt("idProyecto");

        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualiza();
            }
        });
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void actualiza() {
        String descp =desc.getText().toString();
        if(descp.equals("")){
            mensaje("Error","No se coloco la descripcion del proyecto");
            return;
        }
        ProyectoCiviles pc = new ProyectoCiviles(this);
       boolean result= pc.actualizar(new ProyectoCiviles(id,descp,ub.getText().toString(),fe.getText().toString(),
                Float.parseFloat(pre.getText().toString())));
       if(result){
           mensaje("Atencion","Se actualizo con exito ");
       }else{
           mensaje("Error","No se logro actualizar el proyecto "+pc.error);
       }
    }
    private void mensaje(String title, String message) {
        AlertDialog.Builder ale = new AlertDialog.Builder(this);
        ale.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null).show();
    }
}
