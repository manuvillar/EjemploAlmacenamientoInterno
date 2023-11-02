package es.iesoretania.ejemploalmacenamientointerno;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

import es.iesoretania.ejemploalmacenamientointerno.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {
    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        String archivos [] = fileList();

        if (ArchivoExiste(archivos, "bitacora.txt")) {
            try {
                InputStreamReader archivo = new InputStreamReader(openFileInput("bitacora.txt"));
                BufferedReader br = new BufferedReader(archivo);
                String linea = br.readLine();
                String bitacoraCompleta = "";

                while (linea != null) {
                    bitacoraCompleta = bitacoraCompleta + linea + "\n";
                    linea = br.readLine();
                }
                br.close();
                archivo.close();
                binding.txtBitacora.setText(bitacoraCompleta);
            } catch (IOException e) {
                Log.e("Ficheros", "Error al leer fichero de la memoria interna");
            }
        }
    }

    private boolean ArchivoExiste(String archivos [], String NombreArchivo){
        for(int i = 0; i < archivos.length; i++) {
            if (NombreArchivo.equals(archivos[i]))
                return true;
        }
        return false;
    }

    //Método para el botón Guardar
    public void Guardar(View view){
        try {
            OutputStreamWriter archivo = new OutputStreamWriter(openFileOutput("bitacora.txt", Activity.MODE_PRIVATE));
            archivo.write(binding.txtBitacora.getText().toString());
            archivo.flush();
            archivo.close();
        }catch (IOException e){
            Log.e("Ficheros", "Error al escribir fichero a memoria interna");
        }
        Toast.makeText(this, "Bitacora guardada correctamente", Toast.LENGTH_SHORT).show();
    }
}