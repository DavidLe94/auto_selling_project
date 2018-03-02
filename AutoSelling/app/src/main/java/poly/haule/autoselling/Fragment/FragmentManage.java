package poly.haule.autoselling.Fragment;

import android.app.Dialog;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import poly.haule.autoselling.Adapter.ProductsAdapter;
import poly.haule.autoselling.Model.Products;
import poly.haule.autoselling.R;

/**
 * Created by HauLe on 15/11/2017.
 */

public class FragmentManage extends Fragment {
    public static final String ip = "http://haulcps04107.esy.es";
    private String getAllProductsUrl = ip + "/AndroidNetworking/getProducts.php";
    private String updateProductUrl = ip + "/AndroidNetworking/updateProduct.php";
    private String deleteProductUrl = ip + "/AndroidNetworking/deleteProduct.php";
    List<Products> productsList;
    ProductsAdapter adapter;
    Dialog dialog;

    ViewHolder viewHolder = null;
    View view = null;

    private static class ViewHolder{
        GridView gridView;
        ProgressBar progressBar;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


            if (view == null){
                view = inflater.inflate(R.layout.fragment_manage, container, false);
                viewHolder = new ViewHolder();

                viewHolder.gridView = (GridView)view.findViewById(R.id.gridview_products);
                viewHolder.progressBar = (ProgressBar)view.findViewById(R.id.progress_bar);
                viewHolder.progressBar.setVisibility(View.VISIBLE);
                productsList = new ArrayList<>();
                adapter = new ProductsAdapter(getActivity(), productsList);
                //show all products
                showProducts();

                viewHolder.gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        showDialogDetails(position);
                    }
                });
                registerForContextMenu(viewHolder.gridView);
                view.setTag(viewHolder);
            }else{
                viewHolder = (FragmentManage.ViewHolder)view.getTag();
            }
        return view;
    }

    private void showProducts(){
        StringRequest stringrequest = new StringRequest(Request.Method.GET, getAllProductsUrl, new Response.Listener<String>(){
            @Override
            public void onResponse(String response) {
                progressGetAllProducts(response);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        Volley.newRequestQueue(getActivity()).add(stringrequest);

    }

    private void progressGetAllProducts(String response){
        String success = "";
        try {
            JSONObject jsonObject = new JSONObject(response);
            success = jsonObject.getString("success");


            //doc tat ca du lieu tu json bo vao ArrayList
            if(Integer.parseInt(success) == 1){
                //truy mang ten products trong json
                JSONArray arrayProduct = jsonObject.getJSONArray("products");
                //duyet mang
                for(int i=0; i<arrayProduct.length(); i++){
                    JSONObject item = arrayProduct.getJSONObject(i);

                    String id = item.getString("id");
                    String nameProduct = item.getString("nameProduct");
                    String model = item.getString("model");
                    String brand = item.getString("brand");
                    String price = item.getString("price");
                    String image = item.getString("image");
                    String description = item.getString("description");

                    productsList.add(new Products(id, nameProduct, brand, model, price + "$", description, image, 0));
                }

                viewHolder.gridView.setAdapter(adapter);
                viewHolder.progressBar.setVisibility(View.GONE);

                //TODO: add item to list here
            }
            else{
                Toast.makeText(getActivity(), "that van bai", Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater inflater = getActivity().getMenuInflater();
        inflater.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo adapterContextMenuInfo = (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
        int position = adapterContextMenuInfo.position;

        switch (item.getItemId()){
            case R.id.action_delete:
                //TODO: detele product in here
                deleteProduct(position);
                break;
            case R.id.action_updtae:
                //TODO: update info products in here
                showDialogUpdate(position);
                break;
        }
        return super.onContextItemSelected(item);
    }

    private void deleteProduct(int position){
        Products item = productsList.get(position);
        final String id = item.getId();

        StringRequest stringrequest = new StringRequest(
                Request.Method.POST, deleteProductUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO: hien thi thong bao khi them san pham thanh cong
                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();

                Fragment fragment = new FragmentManage();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();

                Fragment fragment = new FragmentManage();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.commit();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("id", id);

                return param;//super.getParams();
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringrequest);


    }

    private void showDialogUpdate(final int position){

        String[] arrayBrand;
        String[] arrayModel;
        ArrayAdapter<String> adapterBrand;
        ArrayAdapter<String> adapterModel;

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_update_product);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        final EditText edtNameProduct = (EditText)dialog.findViewById(R.id.edit_text_name_product);
        final EditText edtPrice = (EditText)dialog.findViewById(R.id.edit_text_price);
        final EditText edtImage = (EditText)dialog.findViewById(R.id.edit_text_image);
        final EditText edtDes = (EditText)dialog.findViewById(R.id.edit_text_description);
        Button btnCancel = (Button)dialog.findViewById(R.id.btnCancel);
        Button btnSave = (Button)dialog.findViewById(R.id.btnSave);
        final Spinner spnBrand = (Spinner)dialog.findViewById(R.id.spinner_brand);
        final Spinner spnModel = (Spinner)dialog.findViewById(R.id.spinner_model);

        arrayBrand = new String[] {
                "---brand---",
                "Apple",
                "Samsung",
                "Nokia",
                "BKAV",
                "Xiaomi",
                "Sony",
                "Asus",
                "Acer",
                "HP",
                "Dell",
                "Microsoft"

        };

        arrayModel = new String[]{
                "---model---",
                "Smartphone",
                "Laptop",
                "Tablet",
                "Other"
        };

        final Products products = productsList.get(position);
        edtNameProduct.setText(products.getName_Products().toString().trim());
        edtPrice.setText(products.getPrice().toString().trim());
        edtDes.setText(products.getDescription().toString().trim());
        edtImage.setText(products.getImage().toString().trim());


        String brand = products.getBrand().toString().trim();
        String model = products.getModel().toString().trim();
        //get id of item selection
        final int po = Arrays.asList(arrayBrand).indexOf(brand);
        final int p = Arrays.asList(arrayModel).indexOf(model);

        adapterBrand = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayBrand){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position==0) {
                    // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };
        adapterModel = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, arrayModel){
            @Override
            public boolean isEnabled(int position){
                if(position == 0)
                {
                    // Disable the second item from Spinner
                    return false;
                }
                else
                {
                    return true;
                }
            }

            @Override
            public View getDropDownView(int position, View convertView,
                                        ViewGroup parent) {
                View view = super.getDropDownView(position, convertView, parent);
                TextView tv = (TextView) view;
                if(position==0) {
                    // Set the disable item text color
                    tv.setTextColor(Color.GRAY);
                }
                else {
                    tv.setTextColor(Color.BLACK);
                }
                return view;
            }
        };

        //set adpater
        spnModel.setAdapter(adapterModel);
        spnBrand.setAdapter(adapterBrand);

        //set item selection to spinner
        spnBrand.setSelection(po);
        spnModel.setSelection(p);



        btnSave.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               //TODO: implements update
               String id = products.getId();
               String nameProduct = edtNameProduct.getText().toString().trim();
               String price = edtPrice.getText().toString().trim();
               String model = spnModel.getSelectedItem().toString().trim();
               String brand = spnBrand.getSelectedItem().toString().trim();
               String image = edtImage.getText().toString().trim();
               String des = edtDes.getText().toString().trim();

               updateProduct(id, nameProduct, model, brand, price, image, des);
           }
       });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

       //mSocket.on("update-product", )

        dialog.show();
        //set size for dialog
        Window window = dialog.getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;

        window.setLayout((int) (width * 0.8), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    private void showDialogDetails(final int position){
        Products products = productsList.get(position);

        Dialog mDialog = new Dialog(getActivity());
        mDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDialog.setContentView(R.layout.dialog_product_details);

        mDialog.getWindow().setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));

        ImageView imgThumb = (ImageView)mDialog.findViewById(R.id.img_thumb);
        TextView tvNameProduct = (TextView)mDialog.findViewById(R.id.tv_name_product);
        TextView tvPrice = (TextView)mDialog.findViewById(R.id.tv_price);
        TextView tvBrand = (TextView)mDialog.findViewById(R.id.tv_brand);
        TextView tvModel = (TextView)mDialog.findViewById(R.id.tv_model);
        TextView tvDes = (TextView)mDialog.findViewById(R.id.tv_des);

        Picasso.with(getActivity()).load(Uri.parse(products.getImage())).into(imgThumb);
        tvNameProduct.setText(products.getName_Products().toString().trim());
        tvPrice.setText(products.getPrice().toString().trim());
        tvBrand.setText(products.getBrand().toString().trim());
        tvModel.setText(products.getModel().toString().trim());
        tvDes.setText(products.getDescription().toString().trim());


        mDialog.show();
        //set size for dialog
        Window window = mDialog.getWindow();
        Point size = new Point();

        Display display = window.getWindowManager().getDefaultDisplay();
        display.getSize(size);

        int width = size.x;

        window.setLayout((int) (width * 0.8), WindowManager.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
    }

    private void updateProduct(final String id, final String name, final String model, final String brand, final String price, final String image, final String des){
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST, updateProductUrl, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //TODO: hien thi thong bao khi them san pham thanh cong
                Toast.makeText(getActivity(), "Success!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Fragment fragment = new FragmentManage();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.commit();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Failed!", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                Fragment fragment = new FragmentManage();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.commit();
            }
        }){

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> param=new HashMap<String,String>();
                param.put("id", id);
                param.put("nameProduct",name);
                param.put("model",model);
                param.put("brand",brand);
                param.put("price",price);
                param.put("image", image);
                param.put("description", des);
                param.put("staDelete", 0+"");

                return param;//super.getParams();
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringrequest);
    }
}
