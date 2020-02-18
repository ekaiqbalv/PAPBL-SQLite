package com.example.papbl;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import com.example.papbl.DB.CrudHelper;
import com.example.papbl.DB.DatabaseContract;
import com.example.papbl.DB.DatabaseHelper;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    TextView _txResult;
    CrudHelper _crudHelper;
    DatabaseHelper _dbHelper;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        _txResult = findViewById(R.id.txResult);

        _dbHelper = new DatabaseHelper(this, "dbBimbingan", null, 2);
        _crudHelper = new CrudHelper(_dbHelper);

        _crudHelper.clearTable(DatabaseContract.TbDosen.TABLE_NAME);
        _crudHelper.clearTable(DatabaseContract.TbMahasiswa.TABLE_NAME);
        _crudHelper.clearTable(DatabaseContract.TbJurusan.TABLE_NAME);

        ContentValues Dosen1 = new ContentValues();
        ContentValues Dosen2 = new ContentValues();
        ContentValues Jurusan1 = new ContentValues();
        ContentValues Jurusan2 = new ContentValues();
        ContentValues mhs1 = new ContentValues();
        ContentValues mhs2 = new ContentValues();
        ContentValues mhs3 = new ContentValues();
        ContentValues mhs4 = new ContentValues();
        ContentValues mhsArr[] = {mhs3, mhs4};

        Dosen1.put("nama", "Akbar");
        Dosen1.put("email", "muhammad.aminul@ub.ac.id");

        Dosen2.put("nama", "Afi");
        Dosen2.put("email", "tri.afi@ub.ac.id");

        Jurusan1.put("nama_jurusan", "Teknik Informatika");
        Jurusan2.put("nama_jurusan", "Sistem Informasi");

        mhs1.put(DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA, "mhs1");
        mhs1.put("email", "mhs1@ub.ac.id");
        mhs1.put("id_dosenpa", "1");
        mhs1.put("id_jurusan", "2");

        mhs2.put(DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA, "mhs2");
        mhs2.put("email", "mhs2@ub.ac.id");
        mhs2.put("id_dosenpa", "2");
        mhs2.put("id_jurusan", "1");

        mhs3.put(DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA, "mhs3");
        mhs3.put("email", "mhs3@ub.ac.id");
        mhs3.put("id_dosenpa", "1");
        mhs3.put("id_jurusan", "2");

        mhs4.put(DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA, "mhs4");
        mhs4.put("email", "mhs4@ub.ac.id");
        mhs4.put("id_dosenpa", "2");
        mhs4.put("id_jurusan", "1");

        //Insert Data
        _crudHelper.insertDosen(Dosen1);
        _crudHelper.insertDosen(Dosen2);
        _crudHelper.insertJurusan(Jurusan1);
        _crudHelper.insertJurusan(Jurusan2);
        _crudHelper.insertMahasiswa(mhs1);
        _crudHelper.insertMahasiswa(mhs2);

        //Insert Transaksi
        _crudHelper.insertMahasiswaTransaction(mhsArr);

        ArrayList<HashMap<String, String>> dosens = _crudHelper.getDosen(null);
        ArrayList<HashMap<String, String>> jurusans = _crudHelper.getJurusan(null);
        ArrayList<HashMap<String, String>> mahasiswas = _crudHelper.getMahasiswa(null);

        //Select
        _txResult.setText("Select Dosen" + System.getProperty("line.separator"));
        for (HashMap dosen : dosens) {
            _txResult.append("ID Dosen: " + dosen.get("id") + "\n" +
                    "Nama Dosen: " + dosen.get("nama") + "\n" +
                    "Email: " + dosen.get("email") + "\n\n"
            );
        }

        _txResult.append("Select Jurusan\n");
        for (HashMap jurusan : jurusans) {
            _txResult.append("ID Jurusan: " + jurusan.get("id") + "\n" +
                    "Nama Jurusan: " + jurusan.get("namaJurusan") + "\n\n"
            );
        }

        _txResult.append("Select Mahasiswa\n");
        for (HashMap mahasiswa : mahasiswas) {
            _txResult.append("Nama Mahasiswa: " + mahasiswa.get("nama") + "\n" +
                    "Email: " + mahasiswa.get("email") + "\n" +
                    "Dosen PA: " + mahasiswa.get("dosenPa") + "\n" +
                    "Jurusan: " + mahasiswa.get("jurusan") + "\n\n"
            );
        }

        //Update
        mhs1.put(DatabaseContract.TbMahasiswa.COLUMN_NAME_NAMA,"mhsSatu");
        mhs1.put("id_dosenpa","2");
        _crudHelper.updateMahasiswa("1",mhs1);
        Dosen2.put("nama", "Tri Afi");
        _crudHelper.updateDosen("2", Dosen2);

        //Select after update
        _txResult.append("Select After Update" + System.getProperty("line.separator"));
        _txResult.append(_crudHelper.getDosen(null).get(0).get("nama") + " " + _crudHelper.getDosen(null).get(0).get("id") + " " + _crudHelper.getDosen(null).get(0).get("email") + System.getProperty("line.separator"));
        _txResult.append(_crudHelper.getMahasiswa(null).get(0).get("nama")+" "+_crudHelper.getMahasiswa(null).get(0).get("dosenPa") + System.getProperty("line.separator"));
    }

    @Override
    protected void onDestroy() {
        _dbHelper.close();
        super.onDestroy();
    }
}
