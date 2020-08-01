package com.example.sqlite_data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.ArrayList;

public class Adapter_Listview extends BaseAdapter {
    private onData mData;
    private Context context;
    private ArrayList<Models_ListView> modelsListViews;
    private int mLayout;

    public Adapter_Listview(Context context, ArrayList<Models_ListView> modelsListViews, int mLayout, onData mDelete) {
        this.context = context;
        this.modelsListViews = modelsListViews;
        this.mLayout = mLayout;
        this.mData = mDelete;
    }

    @Override
    public int getCount() {
        return modelsListViews.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class Viewholder {
        TextView textView;
        ImageButton img_edit, img_delete;
    }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        int a =123;
        Viewholder viewholder;
        if (view == null) {
            viewholder = new Viewholder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(mLayout, null);
            viewholder.textView = view.findViewById(R.id.ten);
            viewholder.img_edit = (ImageButton) view.findViewById(R.id.img_edit);
            viewholder.img_delete = (ImageButton) view.findViewById(R.id.img_delete);
            view.setTag(viewholder);
        } else {
            viewholder = (Viewholder) view.getTag();
        }
        viewholder.textView.setText(modelsListViews.get(i).getTen());
        viewholder.img_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                ((MainActivity)context).dialog(modelsListViews.get(i).getId(), modelsListViews.get(i).getTen());
                mData.delete(modelsListViews.get(i).getId(), modelsListViews.get(i).getTen());
            }
        });
        viewholder.img_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mData.edit(modelsListViews.get(i).getId(), modelsListViews.get(i).getTen());
            }
        });
        return view;
    }

    interface onData {
        void delete(int id, String ten);
        void edit(int id , String ten);
    }
}
