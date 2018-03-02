package poly.haule.autoselling.Fragment;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.Arrays;

import de.hdodenhof.circleimageview.CircleImageView;
import poly.haule.autoselling.R;


/**
 * Created by HauLe on 14/11/2017.
 */

public class FragmentLogin extends Fragment{

    private LoginButton loginButton;
    private CallbackManager callbackManager;

    private ProgressBar progressBar;

    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        FacebookSdk.sdkInitialize(getActivity());
        callbackManager = CallbackManager.Factory.create();

        View view = inflater.inflate(R.layout.fragment_login, container, false);
        //init
        loginButton = (LoginButton)view.findViewById(R.id.connectWithFbButton);
        progressBar = (ProgressBar)view.findViewById(R.id.progress_bar_login);

        loginButton.setFragment(this);
        loginButton.setReadPermissions(Arrays.asList(
                "public_profile", "email"));

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {

            @Override
            public void onSuccess(LoginResult loginResult) {
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Toast.makeText(getActivity(),"Login attempt canceled.", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onError(FacebookException e) {
                Toast.makeText(getActivity(),"Login attempt failed.", Toast.LENGTH_LONG).show();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null){
                    setInfoUiOnLoginSuccess();
                }
            }
        };

        return view;
    }

    private void handleFacebookAccessToken(AccessToken accessToken) {
        loginButton.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);

        AuthCredential credential = FacebookAuthProvider.getCredential(accessToken.getToken());
        firebaseAuth.signInWithCredential(credential).addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(getActivity(), R.string.firebase_error_login, Toast.LENGTH_LONG).show();
                }
                loginButton.setVisibility(View.VISIBLE);
            }
        });
    }


    @Override
    public void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(firebaseAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(firebaseAuthListener);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

    public void setInfoUiOnLoginSuccess(){

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if(user != null){
            CircleImageView circleImageView = (CircleImageView) getActivity().findViewById(R.id.imageView_user_avatar);
            TextView tvUser = (TextView)getActivity().findViewById(R.id.tv_user_name);
            TextView tvEmail = (TextView)getActivity().findViewById(R.id.tv_user_email);

            tvEmail.setText(user.getEmail());
            tvUser.setText(user.getDisplayName());
            Picasso.with(getActivity()).load(user.getPhotoUrl()).into(circleImageView);

            Fragment fragment = new FragmentManage();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.commit();

            progressBar.setVisibility(View.GONE);

            Toast.makeText(getActivity(), "Sign in successfully!", Toast.LENGTH_LONG).show();

        }else{
            //back to fragmetn login
            Fragment fragment = new FragmentLogin();
            FragmentManager fm = getFragmentManager();
            FragmentTransaction fragmentTransaction = fm.beginTransaction();
            fragmentTransaction.replace(R.id.content_main, fragment);
            fragmentTransaction.commit();
        }
    }

}