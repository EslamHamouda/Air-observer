package com.example.airobserver.onBoarding;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.airobserver.profile.Profile;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager mSliderViewPager;
    LinearLayout mDotLayout;
    Button next,previous,skip,getStart;

    TextView[] dots;
    ViewPagerAdapter viewPagerAdapter;

    SaveState saveState;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.on_boarding);

        saveState = new SaveState(this,"onBoarding");
        if(saveState.getState()==1)
        {
            Intent i = new Intent(this,Profile.class);
            startActivity(i);
            finish();
        }
        next=findViewById(R.id.nextButton_id);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getitem(0) < 2)
                    mSliderViewPager.setCurrentItem(getitem(1),true);
                }
        });

        previous=findViewById(R.id.previousButton_id);
        previous.setVisibility(View.INVISIBLE);
        previous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (getitem(0) > 0)
                {
                    mSliderViewPager.setCurrentItem(getitem(-1),true);
                }
            }
        });

        skip=findViewById(R.id.skipButton_id);
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveState.setState(1);
                Intent i = new Intent(getApplicationContext(),Profile.class);
                startActivity(i);
                finish();
            }
        });

        getStart=findViewById(R.id.getStartButton_id);
        getStart.setVisibility(View.INVISIBLE);
        getStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveState.setState(1);
                Intent i = new Intent(getApplicationContext(), Profile.class);
                startActivity(i);
                finish();
            }
        });

        mSliderViewPager = findViewById(R.id.slideViewPager_id);
        mDotLayout = findViewById(R.id.indicator_layout);

        viewPagerAdapter = new ViewPagerAdapter(this);
        mSliderViewPager.setAdapter(viewPagerAdapter);

        setUpIndicator(0);
        mSliderViewPager.addOnPageChangeListener(viewListener);
    }

    public void setUpIndicator(int position)
    {
        dots = new TextView[3];
        mDotLayout.removeAllViews();

        for(int i = 0; i<dots.length; i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(getResources().getColor(R.color.inactive,getApplicationContext().getTheme()));
            mDotLayout.addView(dots[i]);
        }
        dots[position].setTextColor(getResources().getColor(R.color.active,getApplicationContext().getTheme()));

    }

    ViewPager.OnPageChangeListener viewListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            setUpIndicator(position);
            if (position > 0){
                previous.setVisibility(View.VISIBLE);
            }else {
                previous.setVisibility(View.INVISIBLE);
            }

            if (position == 2){
                getStart.setVisibility(View.VISIBLE);
                next.setVisibility(View.INVISIBLE);
                skip.setVisibility(View.INVISIBLE);
            }
            else if(position < 2)
            {
                getStart.setVisibility(View.INVISIBLE);
                next.setVisibility(View.VISIBLE);
                skip.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
    private int getitem(int i){

        return mSliderViewPager.getCurrentItem() + i;
    }
}
