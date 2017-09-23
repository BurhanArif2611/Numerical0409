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

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nishant on 22/09/17.
 */

public class AutoCompleteCountryAdapter extends ArrayAdapter<CountryDto> {

    private final List<CountryDto> countries;
    private List<CountryDto> filteredCountries = new ArrayList<>();

    public AutoCompleteCountryAdapter(Context context, List<CountryDto> dogs) {
        super(context, 0, dogs);
        this.countries = dogs;
    }

    @Override
    public int getCount() {
        return filteredCountries.size();
    }

    @Nullable
    @Override
    public CountryDto getItem(int position) {
        return filteredCountries.get(position);
    }

    @Override
    public Filter getFilter() {
        return new CountryFilter(this, countries);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item from filtered list.
        CountryDto countryDto = filteredCountries.get(position);

        // Inflate your custom row layout as usual.
        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.row_country, parent, false);

        TextView tvName = (TextView) convertView.findViewById(R.id.text_name);
        tvName.setText(countryDto.getName());

        return convertView;
    }

    class CountryFilter extends Filter {

        AutoCompleteCountryAdapter adapter;
        List<CountryDto> originalList;
        List<CountryDto> filteredList;

        public CountryFilter(AutoCompleteCountryAdapter adapter, List<CountryDto> originalList) {
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
                for (final CountryDto countryDto : originalList) {
                    if (countryDto.getName().toLowerCase().startsWith(filterPattern)) {
                        filteredList.add(countryDto);
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