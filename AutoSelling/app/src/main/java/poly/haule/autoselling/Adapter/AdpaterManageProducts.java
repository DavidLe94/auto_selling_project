package poly.haule.autoselling.Adapter;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import poly.haule.autoselling.Model.Products;
import poly.haule.autoselling.R;

/**
 * Created by HauLe on 20/11/2017.
 */

public class AdpaterManageProducts extends BaseAdapter {
    private Context context;
    private List<Products> list;
    private LayoutInflater layoutInflater;

    public AdpaterManageProducts(Context context, List<Products> list) {
        this.context = context;
        this.list = list;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public static class ViewHolder{
        ImageView img;
        TextView tvNameProducts, tvPrice;

    }

    @Override
    public View getView(int position, View view, ViewGroup viewGroup) {
        ProductsAdapter.ViewHolder holder = null;

        if(view == null){
            view = layoutInflater.inflate(R.layout.fragment_manage_one_row, null);
            holder = new ProductsAdapter.ViewHolder();
            holder.img = (ImageView)view.findViewById(R.id.imageview_manage_photo);
            holder.tvNameProducts = (TextView)view.findViewById(R.id.textview_manage_name);
            holder.tvPrice = (TextView)view.findViewById(R.id.textviev_manage_price);
            view.setTag(holder);
        }else{
            holder = (ProductsAdapter.ViewHolder)view.getTag();
        }

        Products products = list.get(position);
        holder.tvPrice.setText(products.getPrice());
        holder.tvNameProducts.setText(products.getName_Products());
        Uri uri = Uri.parse(products.getImage());
        Picasso.with(context).load(uri).into(holder.img);
        return view;
    }
}
