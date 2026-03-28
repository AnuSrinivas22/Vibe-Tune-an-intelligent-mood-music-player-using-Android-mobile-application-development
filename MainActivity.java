package com.example.moodmusicapp;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    Spinner spinnerMood;
    Button btnPlay;
    ImageView imgMood;
    String selectedMood = "";

    Map<String, String> moodToUrl;
    Map<String, Integer> moodToDrawable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        spinnerMood = findViewById(R.id.spinnerMood);
        btnPlay = findViewById(R.id.btnPlay);
        imgMood = findViewById(R.id.imgMood);

        initMoodMaps();

        spinnerMood.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedMood = parent.getItemAtPosition(position).toString();

                Integer drawableId = moodToDrawable.get(selectedMood);
                if (drawableId != null) {
                    imgMood.setImageResource(drawableId);
                } else {
                    imgMood.setImageResource(R.drawable.ic_music_placeholder);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                selectedMood = "";
            }
        });

        btnPlay.setOnClickListener(v -> {

            if (selectedMood.isEmpty()) {
                Toast.makeText(MainActivity.this, "Please select a mood first!", Toast.LENGTH_SHORT).show();
                return;
            }

            String uri = moodToUrl.get(selectedMood);
            if (uri == null) {
                Toast.makeText(MainActivity.this, "No track found for this mood.", Toast.LENGTH_SHORT).show();
                return;
            }

            // Attempt to open in Spotify app
            Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            appIntent.setPackage("com.spotify.music");

            try {
                startActivity(appIntent);
            } catch (Exception e) {
                // Spotify app not installed → Open fallback web link
                String fallbackUrl = getSpotifyWebUrlFromSpotifyUri(uri);
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(fallbackUrl));
                startActivity(browserIntent);
            }
        });
    }

    private void initMoodMaps() {
        moodToUrl = new HashMap<>();
        moodToDrawable = new HashMap<>();

        // Direct Spotify track URIs (will open directly in Spotify)
        moodToUrl.put("Happy", "spotify:track:4dJrjWtAhEkW7VdPYSL1Ip");
        moodToUrl.put("Sad", "spotify:track:68sZLZ4G6U8frxCiVCeaI2");
        moodToUrl.put("Calm", "spotify:track:7zalI0bZ7TUF6UWg18tRd7");
        moodToUrl.put("Angry","spotify:track:5GKTkiKu2u65SQEmQHlluT");

        // Other moods
        moodToUrl.put("Energetic", "spotify:track:27tLIdyFuCmLSeJpC1KNBK");
        moodToUrl.put("Romantic", "spotify:track:6uovzs0w8j9rbfJErO5WCU");
        moodToUrl.put("Nostalgic", "spotify:track:1G7xSx7AyVrQJ2ca1IKmEJ");
        moodToUrl.put("Party", "spotify:track:5RNoEo5uaozsYFtdtiqSdP");
        moodToUrl.put("Focused", "spotify:track:3SpnKsY0V1FW9IsRqZrmZ7");
        moodToUrl.put("Sleepy", "spotify:track:7zalI0bZ7TUF6UWg18tRd7");
        moodToUrl.put("Motivated", "spotify:track:4nVJqDRNtViFR0DilVQ7G0");
        moodToUrl.put("Melancholic", "spotify:track:0i5KYCaQs1xY7z9MWCFoXW");
        moodToUrl.put("Chill", "spotify:track:3BVjPpVvki8Jpm1Ew21UjH");
        moodToUrl.put("Instrumental","spotify:track:0EiDzOsHwf18jgtPgBWkqh");

        // Language (playlists or tracks)
        moodToUrl.put("Kannada", "spotify:playlist:37i9dQZF1DX1ahAlaaz0ZE");
        moodToUrl.put("Telugu", "spotify:playlist:37i9dQZF1DWTt3gMo0DLxA");
        moodToUrl.put("Tamil", "spotify:playlist:1uvSuVApwODnOSBGkpBiR6");

        // Drawables
        moodToDrawable.put("Happy", R.drawable.mood_happy);
        moodToDrawable.put("Sad", R.drawable.mood_sad);
        moodToDrawable.put("Calm", R.drawable.mood_calm);
        moodToDrawable.put("Angry", R.drawable.mood_angry);
        moodToDrawable.put("Energetic", R.drawable.mood_energetic);
        moodToDrawable.put("Romantic", R.drawable.mood_romantic);
        moodToDrawable.put("Nostalgic", R.drawable.mood_nostalgic);
        moodToDrawable.put("Party", R.drawable.mood_party);
        moodToDrawable.put("Focused", R.drawable.mood_focused);
        moodToDrawable.put("Sleepy", R.drawable.mood_sleepy);
        moodToDrawable.put("Motivated", R.drawable.mood_motivated);
        moodToDrawable.put("Melancholic", R.drawable.mood_melancholic);
        moodToDrawable.put("Chill", R.drawable.mood_chill);
        moodToDrawable.put("Instrumental", R.drawable.mood_instrumental);
        moodToDrawable.put("Kannada", R.drawable.mood_kannada);
        moodToDrawable.put("Telugu", R.drawable.mood_telugu);
        moodToDrawable.put("Tamil", R.drawable.mood_tamil);
    }

    // -----------------------------------------------------------
    //  Helper Method (Fully Correct)
    // -----------------------------------------------------------
    private String getSpotifyWebUrlFromSpotifyUri(String spotifyUri) {

        if (spotifyUri.startsWith("spotify:track:")) {
            String id = spotifyUri.replace("spotify:track:", "");
            return "https://open.spotify.com/track/" + id;
        }

        if (spotifyUri.startsWith("spotify:playlist:")) {
            String id = spotifyUri.replace("spotify:playlist:", "");
            return "https://open.spotify.com/playlist/" + id;
        }

        return "https://open.spotify.com/";
    }
}
