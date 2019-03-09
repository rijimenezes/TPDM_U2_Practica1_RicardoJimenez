package mx.edu.ittepic.ricardojimenez.tpdm_u2_practica1_ricardojimenez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {
    ListView lista;
    ProyectoCiviles[] proyectos;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lista = findViewById(R.id.listaproyectos);

    }

    @Override
    protected void onStart() {
        ProyectoCiviles pc = new ProyectoCiviles(this);
        proyectos = pc.consultar();
        String[] descripcion=null;
        if(proyectos ==null){
            descripcion = new String[1];
            descripcion[0] = "No hay proyectos aún";
        }else{
            descripcion = new String[proyectos.length];
            for(int i=0; i<proyectos.length;i++){
                descripcion[i] = proyectos[i].getDescripcion();
            }
        }
        ArrayAdapter<String> adaptador = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,descripcion);
        lista.setAdapter(adaptador);
        super.onStart();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.principal,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.insertar:
                Intent insertar = new Intent(this,Main3Activity.class);
                startActivity(insertar);
                break;
            case R.id.consultar:
                Intent consultar = new Intent(this,Main2Activity.class);
                startActivity(consultar);
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
