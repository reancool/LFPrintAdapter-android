package com.jmrodrigg.printing;

import android.app.ListActivity;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FilePickerActivity extends ListActivity {
    private static final String FOLDER = "folder";

    File rootFolder = Environment.getExternalStorageDirectory();
    File currentFolder;

    ArrayList<File> childrenList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_file_picker);

        currentFolder = rootFolder;

        fillList();

        this.setListAdapter(new FileListAdapter(getBaseContext(),R.layout.file_item,childrenList));
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);

        File selection = childrenList.get(position);

        if (selection.isDirectory()) {
            currentFolder = selection;
            fillList();
        }

    }

    @Override
    public void onBackPressed() {
        if (rootFolder.equals(currentFolder)) {
            finish();
        } else {
            currentFolder = currentFolder.getParentFile();
            fillList();
        }
    }

    private void fillList() {
        this.setTitle(currentFolder.getName());

        File[] children = currentFolder.listFiles();
        childrenList = new ArrayList<>();

        for (int i=0;i<children.length;i++) {
            childrenList.add(children[i]);
        }

        this.setListAdapter(new FileListAdapter(getBaseContext(),R.layout.file_item,childrenList));
    }

    class FileListAdapter extends ArrayAdapter<File> {

        Context mContext;
        int mResourceId;
        List<File> mObjects;


        public FileListAdapter(Context context, int resource, List<File> objects) {
            super(context, resource, objects);

            mContext = context;
            mResourceId = resource;
            mObjects = objects;


        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            ImageView imgIcon;
            TextView txtName,txtSize,txtDate;

            if (convertView == null) {
                LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(mResourceId,null);
            }

            File file = mObjects.get(position);
            if (file != null) {
                imgIcon = (ImageView) convertView.findViewById(R.id.ImgViewFiletype);
                txtName = (TextView) convertView.findViewById(R.id.txtFileName);
                txtSize = (TextView) convertView.findViewById(R.id.txtSize);
                txtDate = (TextView) convertView.findViewById(R.id.txtDate);

                int resource = R.drawable.type_generic;

                String fileName = file.getName();
                long size = file.length();
                long lastModified = file.lastModified();

                if (file.isDirectory()) {
                    resource = R.drawable.type_folder;
                    txtSize.setText("");
                } else {
                    // Determine mime type:
                    if (fileName.toUpperCase().endsWith(".PDF")) {
                        // PDF:
                        resource = R.drawable.type_pdf;
                    } else if (fileName.toUpperCase().endsWith(".JPG") || file.getName().toUpperCase().endsWith(".JPEG")) {
                        // JPG:
                        resource = R.drawable.type_jpg;
                    } else if (fileName.toUpperCase().endsWith(".ONG")) {
                        // PNG:
                        resource = R.drawable.type_png;
                    }

                    // If file, set size:
                    if (size <= 1024) txtSize.setText(size + " Bytes.");
                    else if (size <= (1024*1024)) txtSize.setText(((float)size/1024) + " KBytes.");
                    else txtSize.setText(((float)size/(1024*1024)) + " MBytes.");
                }

                // Set Icon:
                imgIcon.setImageResource(resource);
                // Set name:
                txtName.setText(fileName);
                // Set Last Modified date:
                SimpleDateFormat sd = new SimpleDateFormat("dd/MM/yyyy - hh:mm");
                txtDate.setText(sd.format(new Date(lastModified)));

            }

            return convertView;
        }
    }

}
