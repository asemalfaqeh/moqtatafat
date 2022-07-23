package com.af.moqtatfat.ui;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import com.af.moqtatfat.R;
import com.af.moqtatfat.model.DocumentResponse;

import java.util.List;

public class ViewPagerAdapter extends androidx.viewpager.widget.PagerAdapter {

    private final Context mContext;
    private final List<DocumentResponse> mListData;

    ViewPagerAdapter(Context context, List<DocumentResponse> listDate) {
        mContext = context;
        mListData = listDate;
    }

    //android
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

    @SuppressLint("SetTextI18n")
    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {

        LayoutInflater inflater = LayoutInflater.from(mContext);

        ViewGroup view = (ViewGroup) inflater.inflate(R.layout.pager_item, container, false);

        DocumentResponse documentResponse = mListData.get(position);

        final TextView content = view.findViewById(R.id.content);
        final TextView title = view.findViewById(R.id.title);
        final TextView page_num = view.findViewById(R.id.page_number);
        container.addView(view);

        content.setText(documentResponse.getContent());
        title.setText(documentResponse.getTitle());
        page_num.setText(position+1 + "-" + mListData.size());

        return view;
    }
}
