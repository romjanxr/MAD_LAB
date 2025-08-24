package com.romjan.learning;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.romjan.learning.databinding.FontItemBinding;

import java.util.List;

public class FontAdapter extends BaseAdapter {
    private Context context;
    private List<FontItems> fontItems;
    private LayoutInflater inflater;

    public FontAdapter(Context context, List<FontItems> fontItems) {
        this.context = context;
        this.fontItems = fontItems;
        inflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return fontItems.size();
    }

    @Override
    public Object getItem(int position) {
        return fontItems.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        FontItems currentFontItems = fontItems.get(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.font_item, parent, false);
        }

        ImageView fontImage = convertView.findViewById(R.id.fontImage);
        TextView fontName = convertView.findViewById(R.id.fontName);

        fontImage.setImageResource(currentFontItems.getFontImage());
        fontName.setText(currentFontItems.getFontName());

        return convertView;
    }
}
