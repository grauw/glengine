package nl.grauw.hellotriangle;

import nl.grauw.hellotriangle.GameSurfaceView;
import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;

public class GameActivity extends Activity {

	private GameSurfaceView gameView;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
		gameView = new GameSurfaceView(this);
		setContentView(gameView);
		
		// XXX: is this necessary?
		gameView.setFocusableInTouchMode(true);
		gameView.requestFocus();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add("Test");
		return true;
    }

	@Override
	protected void onPause() {
		super.onPause();
		gameView.onPause();
	}

	@Override
	protected void onResume() {
		super.onResume();
		gameView.onResume();
	}

	/**
	 * Invoked right before getting killed.
	 */
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

}
