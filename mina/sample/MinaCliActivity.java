package walker.learn.mina.sample;

import android.app.Activity;
import android.os.Bundle;

import walker.learn.R;
import walker.learn.mina.sample.MinaFileClient;


public class MinaCliActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina);

        new Thread(new Runnable() {
            @Override
            public void run() {
                MinaFileClient client = new MinaFileClient();
                client.createClienStream();
            }
        }).start();

    }
}
