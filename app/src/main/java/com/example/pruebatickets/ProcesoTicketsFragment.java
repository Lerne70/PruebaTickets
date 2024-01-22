package com.example.pruebatickets;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.pruebatickets.databinding.DialogDetalleTicketBinding;
import com.example.pruebatickets.databinding.FragmentProcesoTicketsBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProcesoTicketsFragment extends Fragment {

    private FragmentProcesoTicketsBinding binding;

    private FirebaseFirestore db;
    private ListAdapter listAdapter;

    private DialogDetalleTicketBinding detalleTicketBinding;
    private AlertDialog alertDialog;

    private static final String TAG = ProcesoTicketsFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentProcesoTicketsBinding.inflate(inflater, container, false);

        detalleTicketBinding = DialogDetalleTicketBinding.inflate(LayoutInflater.from(requireContext()));
        alertDialog = new MaterialAlertDialogBuilder(requireContext()).setView(detalleTicketBinding.getRoot()).create();


        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseFirestore.getInstance();

        db.collection("tickets").addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if (error != null) {
                    Log.w(TAG, "Listen failed.", error);
                    return;
                }

                List<Ticket> tickets = new ArrayList<>();
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("tituloTicket") != null) {
                        if (Integer.parseInt( doc.get("estatus").toString()) == Utils.ESTATUS_EN_PROCESO) {
                            tickets.add(doc.toObject(Ticket.class));
                            listAdapter = new ListAdapter(tickets, requireContext());
                            binding.recyclerView.setHasFixedSize(true);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            binding.recyclerView.setAdapter(listAdapter);
                        }
                    }
                }
                acciones(value);
            }
        });

    }

    private void acciones(QuerySnapshot value) {

        if (listAdapter != null) {
            listAdapter.setOnClickListener(new ListAdapter.OnClickListener() {
                @Override
                public void onClick(int position, Ticket model) {
                    detalleTicketBinding.idTicket.setText(model.getIdTicket());
                    detalleTicketBinding.tituloTicket.setText(model.getTituloTicket());
                    detalleTicketBinding.fechaCreacionTicket.setText(model.getFechaCreacion());
                    detalleTicketBinding.nombreResponsableTicket.setText(model.getNombreResponsable());
                    detalleTicketBinding.equipoResponsableTicket.setText(model.getEquipoResponsable());
                    detalleTicketBinding.tipoIncidenciaTicket.setText(model.getTipoIncidencia());
                    detalleTicketBinding.gravedadTicket.setText(model.getGravedad());
                    detalleTicketBinding.versionSoftwareTicket.setText(model.getVersionSoftware());
                    detalleTicketBinding.descripcionTicket.setText(model.getDescripcion());

                    detalleTicketBinding.btnCambiarEstado.setVisibility(View.VISIBLE);
                    detalleTicketBinding.btnCambiarEstado.setText("Cambiar a en proceso");
                    detalleTicketBinding.btnCambiarEstado.setOnClickListener(v -> {

                        DocumentReference documentReference = db.collection("tickets").document(value.getDocuments().get(position).getId());
                        documentReference
                                .update("estatus", Utils.ESTATUS_ATENDIDOS)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(requireContext(), "Se cambio a proceso", Toast.LENGTH_SHORT).show();
                                        alertDialog.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(requireContext(), "No se pudo actulizar el estado", Toast.LENGTH_SHORT).show();
                                        Log.e(TAG, "onFailure: ", e);
                                        alertDialog.dismiss();
                                    }
                                });
                    });

                    alertDialog.show();
                }
            });
        }
    }
}