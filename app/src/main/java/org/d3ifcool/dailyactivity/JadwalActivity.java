package org.d3ifcool.dailyactivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

/**
 * Created by aldiand on 10/4/17.
 */

public class JadwalActivity extends AppCompatActivity {

    private DatabaseReference mDatabase;
    private JadwalAdapter mAdapter;
    private ActionMode mActionMode;
    private String mHari;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jadwal);


        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recyclerview);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        mDatabase = FirebaseDatabase.getInstance().getReference().child(getIntent().getStringExtra(Jadwal.EXTRA_HARI));
        mAdapter = new JadwalAdapter(this, mDatabase, findViewById(R.id.empty_view), new JadwalAdapter.OnClickHandler() {
            @Override
            public void onClick(Jadwal currentJadwal, String jadwalId) {

                if (mActionMode != null){
                    mAdapter.toggleSelection(jadwalId);
                    if (mAdapter.getSelectionCount()==0)
                        mActionMode.finish();
                    else
                        mActionMode.invalidate();
                    return;
                }

            }

            @Override
            public boolean onLongClick(Jadwal currentJadwal, String jadwalId) {
                if (mActionMode!=null) return false;

                mAdapter.toggleSelection(jadwalId);
                mActionMode = startSupportActionMode(mActionModeCallback);
                return true;
            }
        });
        recyclerView.setAdapter(mAdapter);

        // Setup FAB to add new pet
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addPet();
            }
        });
    }

    private ActionMode.Callback mActionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
            inflater.inflate(R.menu.context_catalog, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            menu.findItem(R.id.action_edit).setVisible(mAdapter.getSelectionCount() == 1);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.action_edit:
                    editPet();
                    break;

                case R.id.action_delete:
                    deletePet();
                    break;
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            mActionMode = null;
            mAdapter.resetSelection();
        }
    };

    private void addPet() {
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_editor, null, false);
        final EditText editJam = (EditText) view.findViewById(R.id.edit_jadwal_jam);
        final EditText editSubject = (EditText) view.findViewById(R.id.edit_jadwal_subject);

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(R.string.editor_activity_title_new_jadwal).setView(view).setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String key = mDatabase.push().getKey();
                Jadwal jadwal = new Jadwal(editJam.getText().toString(),editSubject.getText().toString());
                mDatabase.child(key).setValue(jadwal);
            }
        }).setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    private void editPet() {

        final String petId = mAdapter.getmSelectionIds().get(0);
        Jadwal curentJadwal = mAdapter.getJadwalById(petId);

        View view = LayoutInflater.from(this).inflate(R.layout.dialog_editor, null, false);
        final EditText editJam = (EditText) view.findViewById(R.id.edit_jadwal_jam);
        editJam.setText(curentJadwal.getJam());
        final EditText editSubject = (EditText) view.findViewById(R.id.edit_jadwal_subject);
        editSubject.setText(curentJadwal.getSubject());

        AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(R.string.editor_activity_title_edit_jadwal).setView(view).setPositiveButton(R.string.action_save, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Jadwal jadwal = new Jadwal(editJam.getText().toString(),editSubject.getText().toString());
                mDatabase.child(petId).setValue(jadwal);
                mActionMode.finish();
            }
        }).setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.create().show();
    }

    private void deletePet() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(R.string.delete_dialog_msg).setPositiveButton(R.string.action_delete, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int which) {
                ArrayList<String> ids = mAdapter.getmSelectionIds();
                for (int i=0; i<ids.size(); i++){
                    mDatabase.child(ids.get(i)).removeValue();
                }
                mActionMode.finish();
            }
        }).setNegativeButton(R.string.action_cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        }).create().show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }
}
