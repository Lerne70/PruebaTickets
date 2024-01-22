package com.example.pruebatickets;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ViewHolder> {

    private List<Ticket> mData;
    private LayoutInflater mInflate;
    private Context context;

    public ListAdapter(List<Ticket> itemList, Context context) {
        this.mInflate = LayoutInflater.from(context);
        this.mData = itemList;
        this.context = context;
    }

    @Override
    public int getItemCount() {return mData.size();}

    @Override
    public ListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflate.inflate(R.layout.list_tickets, null);
        return new ListAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ListAdapter.ViewHolder holder, final int position) {
        holder.bindData(mData.get(position));
    }

    public void setItem(List<Ticket> items) {mData = items;}

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView idTicket, titulo, tipoIncidenica, gravedad, estatus;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            idTicket = itemView.findViewById(R.id.id_ticket);
            titulo = itemView.findViewById(R.id.titulo_ticket);
            tipoIncidenica = itemView.findViewById(R.id.incidencia_ticket);
            gravedad = itemView.findViewById(R.id.gravedad_ticket);
            estatus = itemView.findViewById(R.id.text_view_estatus);
        }

        public void bindData(final Ticket item) {
            idTicket.setText(item.getIdTicket());
            titulo.setText(item.getTituloTicket());
            tipoIncidenica.setText(item.getTipoIncidencia());
            gravedad.setText(item.getGravedad());
            switch (item.getEstatus()) {
                case Utils.ESTATUS_NUEVOS:
                    estatus.setBackgroundColor(itemView.getResources().getColor(R.color.estatus_nuevos));
                    estatus.setText(itemView.getResources().getString(R.string.nuevos_tickets_estatus));
                    break;
                case Utils.ESTATUS_EN_PROCESO:
                    estatus.setBackgroundColor(itemView.getResources().getColor(R.color.estatus_proceso));
                    estatus.setText(itemView.getResources().getString(R.string.proceso_tickets_estatus));
                    break;
                case Utils.ESTATUS_ATENDIDOS:
                    estatus.setBackgroundColor(itemView.getResources().getColor(R.color.estatus_atendidos));
                    estatus.setText(itemView.getResources().getString(R.string.atendidos_tickets_estatus));
                    break;
            }
        }
    }
}
