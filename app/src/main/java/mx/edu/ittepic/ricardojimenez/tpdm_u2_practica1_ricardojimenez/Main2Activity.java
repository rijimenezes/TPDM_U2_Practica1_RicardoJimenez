package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica1_ricardojimenez;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity {
    EditText txtConsulta;
    TextView resultado;
    Button consultar, eliminar,actualizar,regresar;
    ProyectoCiviles[] proyecto;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        txtConsulta = findViewById(R.id.txtbuscar);
        resultado = findViewById(R.id.consulta);
        consultar = findViewById(R.id.buscar);
        eliminar = findViewById(R.id.eliminar);
        actualizar = findViewById(R.id.actualizar);
        regresar = findViewById(R.id.regresarconsulta);

        regresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        consultar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                consulta();
            }
        });

        eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                elimina();
            }
        });
        actualizar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizar();
            }
        });
    }

    private void actualizar() {
        if(proyecto==null) {
            mensaje("Error", "No se a seleccionado el proyecto ");
            return;
        }
        ProyectoCiviles pc =proyecto[0];
        Intent actualiza = new Intent(this, Main4Activity.class);
        actualiza.putExtra("idProyecto",pc.getIdProyecto());
        actualiza.putExtra("descripcion", pc.getDescripcion());
        actualiza.putExtra("ubicacion", pc.getUbicacion());
        actualiza.putExtra("fecha", pc.getFecha());
        actualiza.putExtra("presupuesto", pc.getPresupuesto());
        vaciar();
        startActivity(actualiza);
    }

    private void elimina() {
        if(proyecto==null) {
            mensaje("Error", "No se a seleccionado el proyecto ");
            return;
        }
            final String descripcion = proyecto[0].getDescripcion();
            AlertDialog.Builder ale = new AlertDialog.Builder(this);
            ale.setTitle("Confirmación")
                    .setMessage("Esta seguro de eliminar el proyecto: " + descripcion + "?")
                    .setNegativeButton("Cancelar", null)
                    .setPositiveButton("Aceptar", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            ProyectoCiviles pc = new ProyectoCiviles(Main2Activity.this);
                            boolean result = pc.eliminar(proyecto[0].getIdProyecto());
                            if (result) {
                                mensaje("Correcto", "Se a eliminado el proyecto " + descripcion);
                                proyecto = null;
                            } else
                                mensaje("Error", "No se pudo eliminar el proyecto " + descripcion);
                            resultado.setText("");
                        }

                    }).show();

    }

    private void mensaje(String title, String message) {
        AlertDialog.Builder ale = new AlertDialog.Builder(this);
        ale.setTitle(title)
                .setMessage(message)
                .setPositiveButton("OK",null).show();
    }


    private void consulta() {
        String busqueda =txtConsulta.getText().toString();
        int id=1;
        if(busqueda.equals("")) {
            mensaje("Atencion", "Ingrese un proyecto para buscar");
            vaciar();
            return;
        }
        ProyectoCiviles pc = new ProyectoCiviles(this);
        try{
            id = Integer.parseInt(busqueda);
            proyecto = pc.consultar("idProyecto",id+"");
        }catch (NumberFormatException e){proyecto = pc.consultar("descripcion",busqueda);}
        String resultadoConsulta="";
        if (proyecto!=null){
            ProyectoCiviles tmp =proyecto[0];
            resultadoConsulta = "Descripcion:  "+tmp.getDescripcion()+"\nUbicacion:  "+tmp.getUbicacion()
                    +"\nFecha:  "+tmp.getFecha()+"\nPresupuesto:  $"+tmp.getPresupuesto();
        }
        resultado.setText(resultadoConsulta);
        txtConsulta.setText("");
    }

    private void vaciar(){
        resultado.setText("");
        proyecto=null;
    }
}
