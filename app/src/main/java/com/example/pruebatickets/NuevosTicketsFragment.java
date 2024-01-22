package com.example.pruebatickets;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.pruebatickets.databinding.FragmentNuevosTicketsBinding;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class NuevosTicketsFragment extends Fragment {

    private FragmentNuevosTicketsBinding binding;
    private static final String TAG = NuevosTicketsFragment.class.getName();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentNuevosTicketsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseFirestore db = FirebaseFirestore.getInstance();

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
                        if (Integer.parseInt( doc.get("estatus").toString()) == Utils.ESTATUS_NUEVOS) {
                            tickets.add(doc.toObject(Ticket.class));
                            ListAdapter listAdapter = new ListAdapter(tickets, requireContext());
                            binding.recyclerView.setHasFixedSize(true);
                            binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
                            binding.recyclerView.setAdapter(listAdapter);
                        }
                    }
                }
            }
        });


        binding.floatingActionButton.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), TicketActivity.class);
            startActivity(intent);
        });

    }
}