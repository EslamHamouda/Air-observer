package com.example.airobserver.onBoarding;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;

public class ViewPagerAdapter extends PagerAdapter {

    Context context;

    int image[]={
            R.drawable.undraw_environment_iaus,
            R.drawable.undraw_flying_drone,
            R.drawable.undraw_qa_engineers_dg_5_p
    };

    int headings[] = {
            R.string.head_title_AirQuality,
            R.string.head_title_SelectArea,
            R.string.head_title_Predicting
    };

    int description[]={
            R.string.description_AirQuality,
            R.string.description_SelectArea,
            R.string.description_Predicting
    };

    public ViewPagerAdapter(Context context)
    {
        this.context=context;

    }

    @Override
    public int getCount() {
        return description.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == (LinearLayout) object;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
         LayoutInflater layoutInflater= (LayoutInflater)context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
         View v = layoutInflater.inflate(R.layout.slider_layout,container,false);

         ImageView titleImg = v.findViewById(R.id.titleImage);
         TextView heading = v.findViewById(R.id.titleText);
         TextView tDescription = v.findViewById(R.id.titleDescription);

         titleImg.setImageResource(image[position]);
         heading.setText(headings[position]);
         tDescription.setText(description[position]);

         container.addView(v);
         return v;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout)object);
    }
}
