package com.orion_instruments.www.pressuretransmitterver11;

import android.content.Context;
<<<<<<< HEAD
=======
import android.content.Intent;
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
<<<<<<< HEAD
=======
import android.widget.Toast;
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92

/**
 * Created by vineetphatak on 01-03-2018.
 */

 public class MyCustomPagerAdapter extends PagerAdapter
{
<<<<<<< HEAD
    public static int positionImage;
    Context context;
    int images[];
    int selectedImage = -1;
=======

    Context context;
    int images[];
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92
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
<<<<<<< HEAD
        //positionImage = position;
        container.addView(itemView);

        //positionImage = getItemPosition(itemView);
        //Toast.makeText(context, "you selected screen " + (positionImage), Toast.LENGTH_SHORT).show();
=======


        container.addView(itemView);
>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92

        //listening to image click
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
<<<<<<< HEAD
               //positionImage = position;
               // Toast.makeText(context, "you selected screen " + (position), Toast.LENGTH_SHORT).show();
=======
                Intent intent2=new Intent(imageView.getContext(),Mode.class);
                intent2.putExtra("id",position);


                Toast.makeText(context, "you selected screen " + (position), Toast.LENGTH_SHORT).show();

>>>>>>> 9c9722a7a55377d26820d8d4656724b65ea11c92

            }
        });

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
       container.removeView((LinearLayout) object);
}

}
