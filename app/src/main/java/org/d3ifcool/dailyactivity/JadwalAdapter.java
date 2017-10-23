package org.d3ifcool.dailyactivity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

/**
 * Created by aldiand on 10/4/17.
 */

public class JadwalAdapter  extends RecyclerView.Adapter<JadwalAdapter.ViewHolder>  {
    private final Context mContext;
    private final OnClickHandler mClickHandler;
    private final View mEmptyView;

    private ArrayList<Jadwal> mData;
    private ArrayList<String> mDataIds;
    private ArrayList<String> mSelectionIds;

    JadwalAdapter(Context context, DatabaseReference mDatabase, View emptyView, OnClickHandler clickHandler) {
        mContext = context;
        mClickHandler = clickHandler;
        mEmptyView = emptyView;

        mData = new ArrayList<>();
        mDataIds = new ArrayList<>();
        mSelectionIds = new ArrayList<>();

        ChildEventListener childEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                mData.add(dataSnapshot.getValue(Jadwal.class));
                mDataIds.add(dataSnapshot.getKey());
                notifyDataSetChanged();
                setEmptyView();
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                mData.set(mDataIds.indexOf(dataSnapshot.getKey()),dataSnapshot.getValue(Jadwal.class));
                notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                int posision = mDataIds.indexOf(dataSnapshot.getKey());
                mData.remove(posision);
                mDataIds.remove(posision);
                notifyDataSetChanged();
                setEmptyView();

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) { }

            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };

        mDatabase.addChildEventListener(childEventListener);
    }

    private void setEmptyView(){
        if(mData.size() == 0)
            mEmptyView.setVisibility(View.VISIBLE);
        else
            mEmptyView.setVisibility(View.GONE);
    }

    public ArrayList<String> getmSelectionIds(){
        return mSelectionIds;
    }

    public Jadwal getJadwalById(String jadwalId){
        return mData.get(mDataIds.indexOf(jadwalId));
    }

    public void toggleSelection(String petId){
        if (mSelectionIds.contains(petId))
            mSelectionIds.remove(petId);
        else
            mSelectionIds.add(petId);
        notifyDataSetChanged();
    }

    public int getSelectionCount(){
        return mSelectionIds.size();
    }

    public void resetSelection(){
        mSelectionIds.clear();
        notifyDataSetChanged();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(
                R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Jadwal currentJadwal = mData.get(position);
        holder.mJam.setText(currentJadwal.getJam());
        holder.mSubject.setText(currentJadwal.getSubject());
        holder.itemView.setSelected(mSelectionIds.contains(mDataIds.get(position)));
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    interface OnClickHandler {
        void onClick(Jadwal currentJadwal, String jadwalId);

        boolean onLongClick(Jadwal currentPet, String petId);
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnLongClickListener {
        final TextView mJam;
        final TextView mSubject;

        ViewHolder(View view) {
            super(view);
            mJam = (TextView) view.findViewById(R.id.jadwal_jam);
            mSubject = (TextView) view.findViewById(R.id.jadwal_subject);
            view.setOnClickListener(this);
            view.setOnLongClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mClickHandler.onClick(mData.get(getAdapterPosition()),mDataIds.get(getAdapterPosition()));
        }

        @Override
        public boolean onLongClick(View view) {
            return mClickHandler.onLongClick(mData.get(getAdapterPosition()), mDataIds.get(getAdapterPosition()));
        }
    }
}
