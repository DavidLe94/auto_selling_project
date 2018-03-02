package poly.haule.autoselling;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 * Created by HauLe on 03/12/2017.
 */

public class SpashScene extends Activity {
    /** Duration of wait **/
    private final int SPLASH_DISPLAY_LENGTH = 1000;

    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_spash);

        /* New Handler to start the Menu-Activity
         * and close this Splash-Screen after some seconds.*/
        new Handler().postDelayed(new Runnable(){
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent mainIntent = new Intent(SpashScene.this,MainActivity.class);
                SpashScene.this.startActivity(mainIntent);
                SpashScene.this.finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
