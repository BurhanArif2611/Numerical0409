package com.pixelmarketo.nrh.adapter;

import android.content.Context;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.github.chrisbanes.photoview.PhotoView;
import com.pixelmarketo.nrh.R;


import java.util.List;

public class ProductImageAdapter extends PagerAdapter {
    private List<String> IMAGES;
    private LayoutInflater inflater;
    private Context context;

    public ProductImageAdapter(Context context, List<String> IMAGES) {
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
        View imageLayout = inflater.inflate(R.layout.slider_adapter, view, false);

        assert imageLayout != null;

        final PhotoView imageView = (PhotoView) imageLayout
                .findViewById(R.id.full_screen_img);


        //ProductImage installGuideModel = IMAGES.get(position);
//        installTv.setText(installGuideModel.getImage_id());

        Glide.with(context).load(IMAGES.get(position)).placeholder(R.drawable.ic_upload_document).into(imageView);
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