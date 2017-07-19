package com.orion_instruments.www.pressuretransmitterver11;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


public class CustomGridViewActivity extends BaseAdapter {
    private Context mContext;
    private final String[] gridViewString;
    private final int[] gridViewImageId;
    private Object convertView;

    public CustomGridViewActivity(Context context, String[] gridViewString, int[] gridViewImageId) {
        mContext= context;
        this.gridViewString = gridViewString;
        this.gridViewImageId = gridViewImageId;
    }

    @Override
    public int getCount() {
        return gridViewString.length;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View gridViewAndroid;
        LayoutInflater inflater=(LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);


        if (convertView == null)
        {
            gridViewAndroid=new View(mContext);
            gridViewAndroid=inflater.inflate(R.layout.activity_grid_design,null);


            TextView textViewAndroid=(TextView) gridViewAndroid.findViewById(R.id.gridView_text);
            ImageView imageViewAndroid=(ImageView) gridViewAndroid.findViewById(R.id.gridView_image);

            textViewAndroid.setText(gridViewString[i]);
            imageViewAndroid.setImageResource(gridViewImageId[i]);
        }
        else {
            gridViewAndroid=(View) convertView;
        }
        return gridViewAndroid;
    }
}
