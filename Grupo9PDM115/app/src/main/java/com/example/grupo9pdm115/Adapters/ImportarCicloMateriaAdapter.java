package com.example.grupo9pdm115.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.grupo9pdm115.Modelos.CicloMateria;
import com.example.grupo9pdm115.R;

import java.util.List;

public class ImportarCicloMateriaAdapter  extends RecyclerView.Adapter<ImportarCicloMateriaAdapter.CicloMateriaViewHolder> {
    Context context;
    List<CicloMateria> cicloMateria;
    public ImportarCicloMateriaAdapter(Context context, List<CicloMateria> listaUsuarios) {
        this.context = context;
        this.cicloMateria = listaUsuarios;
    }
    @NonNull
    @Override
    public ImportarCicloMateriaAdapter.CicloMateriaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View vista = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_importar_ciclo_materia, null, false);
        return new ImportarCicloMateriaAdapter.CicloMateriaViewHolder(vista);

    }

    @Override
    public void onBindViewHolder(@NonNull ImportarCicloMateriaAdapter.CicloMateriaViewHolder holder, int position) {
        holder.tvNombre.setText(cicloMateria.get(position).getCodMateria());
    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class CicloMateriaViewHolder extends RecyclerView.ViewHolder{
        TextView tvNombre;

        public CicloMateriaViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.txtCodMateria);
        }
    }
}
