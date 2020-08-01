package com.example.sqlite_data;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements Adapter_Listview.onData {
    Database database;
    ListView listview;
    Adapter_Listview adapter_listview;
    ArrayList<Models_ListView> arrayList;
    Button btn_them, btn_huy, btn_xacnhan, btn_huyhuy;
    EditText edt_them, edt_edit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        anhXa();
        createDatabase();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.add) {
            addDatabase();
        }
        return super.onOptionsItemSelected(item);
    }

    private void addDatabase() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_add);
        edt_them = dialog.findViewById(R.id.edt_them);
        btn_them = dialog.findViewById(R.id.btn_them);
        btn_huy = dialog.findViewById(R.id.btn_huy);
        dialog.show();
        dialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialogInterface.cancel();
            }
        });
        btn_them.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String s = edt_them.getText().toString();
                if (s.length() == 0) {
                    Toast.makeText(MainActivity.this, "Bạn chưa nhập dữ liệu", Toast.LENGTH_SHORT).show();
                } else {
                    database.queryData("INSERT INTO congviec (id,tencv) VALUES( null,'" + s + "')");
                    Toast.makeText(MainActivity.this, "Đã thêm thành công", Toast.LENGTH_SHORT).show();
                    dialog.cancel();
                    getDataCongViec();
                }

            }
        });
        btn_huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
                dialog.dismiss();
            }
        });
    }

    private void createDatabase() {
        // tạo database data
        database = new Database(this, "Data.sqlite", null, 1);
        // tạo bảng congviec có 2 thuộc tính là id, tencv
        // id kiểu integer làm khóa chính và tự động tăng
        // tencv kiểu varchar có 200 ký tự
        database.queryData("CREATE TABLE IF NOT EXISTS congviec (id INTEGER PRIMARY KEY AUTOINCREMENT, tencv VARCHAR(200))");
        // try vấn thêm dữ liệu.
//        database.queryData("INSERT INTO congviec(id,tencv) VALUES(null,'Lập trình')");
//        database.queryData("INSERT INTO congviec(id,tencv) VALUES(null,'Lập Đi chơi')");
        getDataCongViec();
    }

    private void getDataCongViec() {
        // try vấn lấy dữ liệu từ bảng congviec
        Cursor dataCongViec = database.getData("SELECT * FROM congviec");
        arrayList.clear();
        while (dataCongViec.moveToNext()) {
            int id = dataCongViec.getInt(0);
            String ten = dataCongViec.getString(1);
            arrayList.add(new Models_ListView(id, ten));
        }
        adapter_listview.notifyDataSetChanged();
    }

    private void anhXa() {
        listview = findViewById(R.id.listview);
        arrayList = new ArrayList<>();
        adapter_listview = new Adapter_Listview(this, arrayList, R.layout.item_listview, this);
        listview.setAdapter(adapter_listview);
    }

    @Override
    public void delete(int id, String ten) {
        database.queryData("DELETE FROM congviec WHERE id = '" + id + "'");
        getDataCongViec();
    }

    @Override
    public void edit(final int id, final String ten) {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_edit);
        btn_xacnhan = (Button) dialog.findViewById(R.id.btn_xacnhan);
        btn_huyhuy = (Button) dialog.findViewById(R.id.btn_huyhuy);
        edt_edit = (EditText) dialog.findViewById(R.id.edt_edit);
        edt_edit.setText(ten.toString());
        dialog.show();
        btn_huyhuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        btn_xacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String t = edt_edit.getText().toString();
                if (t.isEmpty())
                    Toast.makeText(MainActivity.this, "Dữ liệu trống", Toast.LENGTH_SHORT).show();
                else {
                    database.queryData("UPDATE congviec SET tencv = '" + t + "' WHERE id = '" + id + "'");
                    getDataCongViec();
                    dialog.cancel();
                    Toast.makeText(MainActivity.this, "Chỉnh sửa thành công!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        Toast.makeText(this, "" + id + " - " + ten, Toast.LENGTH_SHORT).show();
    }
}
