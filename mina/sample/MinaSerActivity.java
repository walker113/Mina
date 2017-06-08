package walker.learn.mina.sample;

import android.app.Activity;
import android.os.Bundle;

import walker.learn.R;
import walker.learn.mina.sample.MinaFileServer;


public class MinaSerActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mina);

        MinaFileServer server = new MinaFileServer();
        server.createServerStream();
    }

}
