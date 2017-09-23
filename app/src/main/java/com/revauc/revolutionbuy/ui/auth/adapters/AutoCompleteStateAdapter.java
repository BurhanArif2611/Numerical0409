package com.revauc.revolutionbuy.ui.auth.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import com.revauc.revolutionbuy.R;
import com.revauc.revolutionbuy.network.response.profile.CountryDto;
import com.revauc.revolutionbuy.network.response.profile.StateDto;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 22/09/17.
 */

public class AutoCompleteStateAdapter extends ArrayAdapter<StateDto> {

    private final List<StateDto> countries;
    private List<StateDto> filteredCountries = new ArrayList<>();

    public AutoCompleteStateAdapter(Context context, List<StateDto> states) {
        super(context, 0, states);
        this.countries = states;
    }

    @Override
    public int getCount() {
        return filteredCountries.size();
    }

    @Override
    public Filter getFilter() {
        return new DogsFilter(this, countries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item from filtered list.
        StateDto StateDto = filteredCountries.get(position);

        // Inflate your custom row layout as usual.
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.row_country, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.text_name);
        tvName.setText(StateDto.getName());

        return convertView;
    }

    @Nullable
    @Override
    public StateDto getItem(int position) {
        return filteredCountries.get(position);
    }

    class DogsFilter extends Filter {

        AutoCompleteStateAdapter adapter;
        List<StateDto> originalList;
        List<StateDto> filteredList;

        public DogsFilter(AutoCompleteStateAdapter adapter, List<StateDto> originalList) {
            super();
            this.adapter = adapter;
            this.originalList = originalList;
            this.filteredList = new ArrayList<>();
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            filteredList.clear();
            final FilterResults results = new FilterResults();

            if (constraint == null || constraint.length() == 0) {
                filteredList.addAll(originalList);
            } else {
                final String filterPattern = constraint.toString().toLowerCase().trim();

                // Your filtering logic goes in here
                for (final StateDto StateDto : originalList) {
                    if (StateDto.getName().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(StateDto);
                    }
                }
            }
            results.values = filteredList;
            results.count = filteredList.size();
            return results;
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            adapter.filteredCountries.clear();
            adapter.filteredCountries.addAll((List) results.values);
            adapter.notifyDataSetChanged();
        }
    }

}