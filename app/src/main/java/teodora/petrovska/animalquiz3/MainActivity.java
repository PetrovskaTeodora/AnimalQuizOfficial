package teodora.petrovska.animalquiz3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    public static final String GUESSES="settings_numberOgGuesses";
    public static final String ANIMAL_TYPE="settings_animalsType";
    public static final String QUIZ_BACKGROUND_COLOR="settings_quiz_background";
    public static final String QUIZ_FONT="settings_quiz_font";


    private boolean isSettingsChanged=false;

    static Typeface chunkfive;
    static Typeface fontlerybrown;
    static Typeface wonderbarDemo;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("AnimalQuiz");

        chunkfive=Typeface.createFromAsset(getAssets(), "fonts/Chunkfive.otf");
        fontlerybrown=Typeface.createFromAsset(getAssets(), "fonts/FontleroyBrown.ttf");
        wonderbarDemo=Typeface.createFromAsset(getAssets(), "fonts/Wonderbar Demo.otf");

        PreferenceManager.setDefaultValues(MainActivity.this, R.xml.quiz_preferences, false);
        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).registerOnSharedPreferenceChangeListener(settingsChangeListener);



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        Intent preferencesIntent=new Intent(MainActivity.this,SettingsActivity.class);
        startActivity(preferencesIntent);
        return super.onOptionsItemSelected(item);

    }

    private SharedPreferences.OnSharedPreferenceChangeListener settingsChangeListener = new SharedPreferences.OnSharedPreferenceChangeListener() {
        @Override
        public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {


        }
    };
}
