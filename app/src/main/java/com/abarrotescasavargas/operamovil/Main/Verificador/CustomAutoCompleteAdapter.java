package com.abarrotescasavargas.operamovil.Main.Verificador;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;

import java.util.ArrayList;
import java.util.List;

public class CustomAutoCompleteAdapter extends ArrayAdapter<String> implements Filterable {
    private List<String> originalData;
    private List<String> filteredData;

    public CustomAutoCompleteAdapter(Context context, int resource, List<String> data) {
        super(context, resource, data);
        originalData = new ArrayList<>(data);
        filteredData = new ArrayList<>();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                filteredData.clear();
                FilterResults results = new FilterResults();

                if (constraint == null || constraint.length() == 0) {
                    filteredData.addAll(originalData);
                } else {
                    String filterPattern = constraint.toString().toLowerCase().trim();
                    for (String item : originalData) {
                        if (item.toLowerCase().contains(filterPattern)) {
                            filteredData.add(item);
                        }
                    }
                }

                results.values = filteredData;
                results.count = filteredData.size();
                return results;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                clear();
                addAll((List<String>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
