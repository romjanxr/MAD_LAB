package com.romjan.learning;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.romjan.learning.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    List<FontItems> fontItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        fontItems.add(new FontItems("Cursive-Bold", R.drawable.new_font, "fonts/Cursive-Bold.ttf"));
        fontItems.add(new FontItems("Debrosee", R.drawable.debrosee ,"fonts/Debrosee.ttf"));

        FontAdapter adapter = new FontAdapter(this, fontItems);
        binding.listItem.setAdapter(adapter);

        binding.listItem.setOnItemClickListener((adapterView, view, position, id) -> {
            Typeface typeface = Typeface.createFromAsset(getAssets(), fontItems.get(position).getFontPath());
            binding.myText.setTypeface(typeface);
        });



    }
}