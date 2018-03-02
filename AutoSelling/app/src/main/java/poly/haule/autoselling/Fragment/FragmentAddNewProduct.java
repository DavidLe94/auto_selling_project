package poly.haule.autoselling.Fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

import poly.haule.autoselling.R;

import static poly.haule.autoselling.Fragment.FragmentManage.ip;

/**
 * Created by HauLe on 15/11/2017.
 */

public class FragmentAddNewProduct extends Fragment {

    private String addNewProductUrl = ip + "/AndroidNetworking/addProduct.php";

    private EditText edtNameProduct, edtPrice, edtDes, edtImage;
    private Spinner spnBrand, spnModel;
    private Button btnSave, btnCancel;
    private String[] arrayBrand;
    private String[] arrayModel;
    private ArrayAdapter<String> adapterBrand;
    private ArrayAdapter<String> adapterModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_add_new_product, container, false);

        edtDes = (EditText)view.findViewById(R.id.edit_text_description);
        edtImage = (EditText)view.findViewById(R.id.edit_text_image);
        edtNameProduct = (EditText)view.findViewById(R.id.edit_text_name_product);
        edtPrice = (EditText)view.findViewById(R.id.edit_text_price);
        spnBrand = (Spinner)view.findViewById(R.id.spinner_brand);
        spnModel = (Spinner)view.findViewById(R.id.spinner_model);
        btnCancel = (Button)view.findViewById(R.id.btnCancel);
        btnSave =(Button)view.findViewById(R.id.btnSave);

        arrayBrand = new String[]{
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

        spnModel.setAdapter(adapterModel);
        spnBrand.setAdapter(adapterBrand);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addNewProduct();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManage fragment = new FragmentManage();
                FragmentManager fm = getFragmentManager();
                FragmentTransaction fragmentTransaction = fm.beginTransaction();
                fragmentTransaction.replace(R.id.content_main, fragment);
                fragmentTransaction.commit();
            }
        });

        return view;
    }

    private void addNewProduct(){
        StringRequest stringrequest = new StringRequest(
                Request.Method.POST, addNewProductUrl, new Response.Listener<String>() {
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
                param.put("nameProduct",edtNameProduct.getText().toString());
                param.put("model",spnModel.getSelectedItem().toString());
                param.put("brand",spnBrand.getSelectedItem().toString());
                param.put("price",edtPrice.getText().toString());
                param.put("image", edtImage.getText().toString());
                param.put("description", edtDes.getText().toString());
                param.put("staDelete", 0+"");

                return param;//super.getParams();
            }
        };
        Volley.newRequestQueue(getActivity()).add(stringrequest);
    }

}
