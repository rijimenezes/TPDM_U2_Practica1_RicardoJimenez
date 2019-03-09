package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica1_ricardojimenez;

import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Main3Activity extends AppCompatActivity {
    EditText desc, ub, fe,pre;
    Button insert,regresar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        desc = findViewById(R.id.insertaDescripcion);
        ub = findViewById(R.id.insertaUbicacion);
        fe = findViewById(R.id.insertaFecha);
        pre = findViewById(R.id.insertaPresupuesto);
        regresar = findViewById(R.id.regresaProyecto);
        insert = findViewById(R.id.registraProyecto);
        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        insert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(desc.getText()==null) {
                    Toast.makeText(Main3Activity.this,"Ingrese la descripcion del proyecto",Toast.LENGTH_LONG).show();
                    return;
                }
                insertar();
            }
        });
    }

    private void insertar() {
        ProyectoCiviles pc = new ProyectoCiviles(this);
        String mensaje = "Se logro registrar el proyecto con exito!";
        float pres=0;
        try{
            pres = Float.parseFloat(pre.getText().toString());
        }catch (NumberFormatException e){}
        boolean salida = pc.insertar( new ProyectoCiviles(0,desc.getText().toString(),
                ub.getText().toString(),fe.getText().toString(),pres));
        if(!salida)
            mensaje = "No se pudo registrar el proyecto "+pc.error;
        AlertDialog.Builder al = new AlertDialog.Builder(this);
        al.setTitle("Atención")
                .setMessage(mensaje)
                .setPositiveButton("OK",null).show();
        pre.setText("");
        fe.setText("");
        ub.setText("");
        desc.setText("");
    }
}
