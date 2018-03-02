package poly.haule.autoselling.Adapter;

import android.app.Activity;
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

import poly.haule.autoselling.Fragment.FragmentManage;
import poly.haule.autoselling.MainActivity;
import poly.haule.autoselling.Model.Products;
import poly.haule.autoselling.R;

/**
 * Created by HauLe on 15/11/2017.
 */

public class ProductsAdapter extends BaseAdapter {

    private Context context;
    private List<Products> productsList;
    private LayoutInflater layoutInflater;

    public ProductsAdapter(Context context, List<Products> productsList) {
        this.context = context;
        this.productsList = productsList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return productsList.size();
    }

    @Override
    public Object getItem(int position) {
        return productsList.get(position);
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
        ViewHolder holder = null;

        if(view == null){
            view = layoutInflater.inflate(R.layout.gridvied_one_row_products, null);
            holder = new ViewHolder();
            holder.img = (ImageView)view.findViewById(R.id.img_products);
            holder.tvNameProducts = (TextView)view.findViewById(R.id.tv_name_product);
            holder.tvPrice = (TextView)view.findViewById(R.id.tv_price_product);
            view.setTag(holder);
        }else{
            holder = (ViewHolder)view.getTag();
        }

        Products products = productsList.get(position);
        holder.tvPrice.setText(products.getPrice());
        holder.tvNameProducts.setText(products.getName_Products());
        Uri uri = Uri.parse(products.getImage());
        Picasso.with(context).load(uri).into(holder.img);

        return view;
    }
}
