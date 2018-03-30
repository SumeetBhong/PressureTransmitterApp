package com.orion_instruments.www.pressuretransmitterver11;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * Created by vineetphatak on 01-03-2018.
 */

 public class MyCustomPagerAdapter extends PagerAdapter
{
    public static int positionImage;
    Context context;
    int images[];
    int selectedImage = -1;
    LayoutInflater layoutInflater;

    public MyCustomPagerAdapter(Context context, int images[]) {
        this.context = context;
        this.images = images;
        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return images.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        View itemView = layoutInflater.inflate(R.layout.item, container, false);

        final ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(images[position]);
        //positionImage = position;
        container.addView(itemView);

        //positionImage = getItemPosition(itemView);
        //Toast.makeText(context, "you selected screen " + (positionImage), Toast.LENGTH_SHORT).show();

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               //positionImage = position;
               // Toast.makeText(context, "you selected screen " + (position), Toast.LENGTH_SHORT).show();

            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((LinearLayout) object);
}

}
