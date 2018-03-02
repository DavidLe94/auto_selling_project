package poly.haule.autoselling.Fragment;

import android.app.Fragment;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import poly.haule.autoselling.R;

/**
 * Created by HauLe on 20/11/2017.
 */

public class FragmentDetailsProducts extends Fragment {

    private ImageView mImageViewPhoto;
    private TextView mNameProduct, mBrand, mModel, mPrice, mDescription;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details_products, container, false);

        mImageViewPhoto = (ImageView)view.findViewById(R.id.img_photo_details);
        mNameProduct = (TextView)view.findViewById(R.id.textview_name_product);
        mBrand = (TextView)view.findViewById(R.id.textview_brand);
        mModel = (TextView)view.findViewById(R.id.textview_model);
        mPrice = (TextView)view.findViewById(R.id.textview_price_products);
        mDescription = (TextView)view.findViewById(R.id.textview_description);

        //set value to view
        Picasso.with(getActivity()).load(Uri.parse(getArguments().getString("image"))).into(mImageViewPhoto);
        mNameProduct.setText("Products name: " + getArguments().getString("name"));
        mBrand.setText("Brand: " + getArguments().getString("brand"));
        mModel.setText("Model: " + getArguments().getString("model"));
        mPrice.setText("Price: " + getArguments().getString("price"));
        mDescription.setText("Description: " + getArguments().getString("des"));

        return view;
    }
}
