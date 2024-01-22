package com.example.pruebatickets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.pruebatickets.databinding.ActivityTicketBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TicketActivity extends AppCompatActivity {

    private ActivityTicketBinding binding;
    private static final String TAG = TicketActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityTicketBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        inicilizarComponentes();

    }

    private void inicilizarComponentes() {
        Date currentTime = Calendar.getInstance().getTime();

        SimpleDateFormat sdfDateAndTime = new SimpleDateFormat("ddMMyyyyhhmmss", Locale.getDefault());
        SimpleDateFormat sdfDate = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        String date = sdfDate.format(currentTime);
        String nameColletion = "Ticket_" + sdfDateAndTime.format(currentTime);

        binding.fechaCreacionEditText.setText(date);
        binding.idTicketEditText.setText(sdfDateAndTime.format(currentTime));

        ArrayAdapter<String> arrayAdapterEquipos = new ArrayAdapter<String>(this, R.layout.dropdown_menu, getResources().getStringArray(R.array.equipos));
        ArrayAdapter<String> arrayAdapterIncidencias = new ArrayAdapter<String>(this, R.layout.dropdown_menu, getResources().getStringArray(R.array.incidencias));
        ArrayAdapter<String> arrayAdapterGravedad = new ArrayAdapter<String>(this, R.layout.dropdown_menu, getResources().getStringArray(R.array.gravedad));

        binding.equipoResponsable.setAdapter(arrayAdapterEquipos);
        binding.tipoIncidencia.setAdapter(arrayAdapterIncidencias);
        binding.gravedadIncidencia.setAdapter(arrayAdapterGravedad);

        listenerCampos();

        binding.floatingActionButtonGuardar.setOnClickListener(v -> {
            if (validarCampos()) {
                guardarTicket(nameColletion);
            } else {
                binding.tituloTicketLayout.setErrorEnabled(true);
                binding.tituloTicketLayout.setError(getResources().getString(R.string.texto_error));

                binding.nombreResponsableLayout.setErrorEnabled(true);
                binding.nombreResponsableLayout.setError(getResources().getString(R.string.texto_error));

                binding.tipoIncidenciaLayout.setErrorEnabled(true);
                binding.tipoIncidenciaLayout.setError(getResources().getString(R.string.texto_error));

                binding.gravedadIncidenciaLayout.setErrorEnabled(true);
                binding.gravedadIncidenciaLayout.setError(getResources().getString(R.string.texto_error));

                binding.descripcionLayout.setErrorEnabled(true);
                binding.descripcionLayout.setError(getResources().getString(R.string.texto_error));

                Toast.makeText(this, "Llenar los campos obligatorios (*)", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void listenerCampos() {
        binding.tituloTicketEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tituloTicketLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.nombreResponsableEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.nombreResponsableLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.tipoIncidencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.tipoIncidenciaLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.gravedadIncidencia.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.gravedadIncidenciaLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        binding.descripcionEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                binding.descripcionLayout.setErrorEnabled(false);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private boolean validarCampos() {
        boolean formularioCompleto = true;

        if (binding.tituloTicketEditText.getText().toString().trim().equals("")) {
            formularioCompleto = false;
        }

        if (binding.nombreResponsableEditText.getText().toString().trim().equals("")) {
            formularioCompleto = false;
        }

        if (binding.tipoIncidencia.getText().toString().equals("") || binding.tipoIncidencia.getText().toString().equals(getResources().getString(R.string.elegir_opcion))) {
            formularioCompleto = false;
        }

        if (binding.gravedadIncidencia.getText().toString().equals("") || binding.gravedadIncidencia.getText().toString().equals(getResources().getString(R.string.elegir_opcion))) {
            formularioCompleto = false;
        }

        if (binding.descripcionEditText.getText().toString().trim().equals("")) {
            formularioCompleto = false;
        }

        return formularioCompleto;
    }

    private void guardarTicket(String nameColletion) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        Ticket ticket = new Ticket(
                binding.idTicketEditText.getText().toString(),
                binding.tituloTicketEditText.getText().toString(),
                binding.fechaCreacionEditText.getText().toString(),
                binding.nombreResponsableEditText.getText().toString(),
                binding.equipoResponsable.getText().toString(),
                binding.tipoIncidencia.getText().toString(),
                binding.gravedadIncidencia.getText().toString(),
                binding.versionSoftwareEditText.getText().toString(),
                binding.descripcionEditText.getText().toString(),
                Utils.ESTATUS_NUEVOS
        );

        db.collection("tickets").document(nameColletion).set(ticket)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(TicketActivity.this, "Ticket agregado", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getBaseContext(), MainActivity.class);
                        startActivity(intent);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(TicketActivity.this, "No se puedo agregar el ticket", Toast.LENGTH_SHORT).show();
                    }
                });
    }
}