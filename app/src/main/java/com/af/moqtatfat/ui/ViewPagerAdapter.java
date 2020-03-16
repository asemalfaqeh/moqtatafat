package com.af.moqtatfat.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.af.moqtatfat.R;
import java.util.List;

public class ViewPagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private Context mContext;
    private List<String> mListData;

    public ViewPagerAdapter(Context context, List<String> listDate) {
        mContext = context;
        mListData = listDate;
    }

    @Override
    public int getCount() {
        return mListData.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((View) object);
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.pager_item, container, false);
        final TextView textView = view.findViewById(R.id.content);
        textView.setText(mListData.get(position));
        container.addView(view);
        return view;
    }
}
