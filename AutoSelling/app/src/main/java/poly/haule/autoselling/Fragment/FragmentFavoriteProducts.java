package poly.haule.autoselling.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import poly.haule.autoselling.R;

/**
 * Created by HauLe on 15/11/2017.
 */

public class FragmentFavoriteProducts extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_favorite_products, container, false);
    }
}
