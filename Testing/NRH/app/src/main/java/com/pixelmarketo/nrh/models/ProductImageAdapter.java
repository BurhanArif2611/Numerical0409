package com.pixelmarketo.nrh.models;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.pixelmarketo.nrh.R;
import com.pixelmarketo.nrh.models.Service_Models.Banner;


import java.util.List;

public class ProductImageAdapter extends PagerAdapter {
    private List<Banner> IMAGES;
    private LayoutInflater inflater;
    private Context context;

    public ProductImageAdapter(Context context, List<Banner> IMAGES) {
        this.context = context;
        this.IMAGES=IMAGES;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return IMAGES.size();
    }

    @Override
    public Object instantiateItem(ViewGroup view, int position) {
        View imageLayout = inflater.inflate(R.layout.welcome_slide1, view, false);

        assert imageLayout != null;
        final ImageView imageView = (ImageView) imageLayout
                .findViewById(R.id.sliderImage);

        Glide.with(context).load(IMAGES.get(position).getAdvertisement_image()).into(imageView);
        view.addView(imageLayout);

        return imageLayout;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }

    @Override
    public void restoreState(Parcelable state, ClassLoader loader) {
    }

    @Override
    public Parcelable saveState() {
        return null;
    }


}
