package com.petswote.pet.add;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.petsvote.domain.entity.pet.PetPhoto;
import com.petsvote.ui.AnimatedRoundedImage;
import com.petsvote.ui.BesieLayout;
import com.petsvote.ui.ext.GlideKt;
import com.petswote.pet.R;
import com.petswote.pet.helpers.ItemTouchHelperAdapter;
import com.petswote.pet.helpers.ItemTouchHelperViewHolder;
import com.petswote.pet.helpers.OnStartDragListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AddPetPhotoAdapter extends RecyclerView.Adapter<AddPetPhotoAdapter.ItemViewHolder>
        implements ItemTouchHelperAdapter {

    private List<PetPhoto> mItems = new ArrayList<>();

    private final OnStartDragListener mDragStartListener;

    public AddPetPhotoAdapter(Context context, OnStartDragListener dragStartListener) {
        mDragStartListener = dragStartListener;
    }

    public List<PetPhoto> getItems(){
        return mItems;
    }

    public void addData(List<PetPhoto> list){
        mItems.addAll(list);
        notifyDataSetChanged();
    }

    public void addItem(PetPhoto photo){
        if(photo.getId() != null){
            for (PetPhoto p: mItems){
                if(p.getId() != null && p.getId() == photo.getId()) return;
            }
        }
        for(PetPhoto i: mItems){
            if(i.getBitmap() == null && i.getImage() == null) {
                if(photo.getBitmap() != null ) i.setBitmap(photo.getBitmap());
                else if (photo.getImage() != null ) i.setImage(photo.getImage());
                i.setId(photo.getId());
                notifyDataSetChanged();
                return;
            }
        }
    }

    public void removeItem(int position){
        mItems.remove(position);
        notifyItemRemoved(position);
        mItems.add(mItems.size(), new PetPhoto());
        notifyItemInserted(mItems.size() -1);
        notifyDataSetChanged();
    }

    @Override
    public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_add_photo, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int pos) {

        DisplayMetrics dm = holder.root.getContext().getResources().getDisplayMetrics();
        int with = dm.widthPixels;
        int height = dm.heightPixels;
        float cardHeight = height * 0.21f;
        float cardWidth = (with - dm.density * 16) / 3;

        ViewGroup.LayoutParams lp = holder.root.getLayoutParams();
        lp.height = (int) cardHeight;
        lp.width = (int) cardWidth;
        holder.root.setLayoutParams(lp);

        PetPhoto item = mItems.get(holder.getBindingAdapterPosition());
        if(item.getBitmap() != null){
            holder.handleView.setImageBitmap(mItems.get(holder.getAbsoluteAdapterPosition()).getBitmap());
            holder.add_photo.setVisibility(View.GONE);
            holder.close.setVisibility(View.VISIBLE);
        }else if(item.getImage() != null){
            GlideKt.loadUrl(holder.handleView,item.getImage());
            holder.add_photo.setVisibility(View.GONE);
            holder.close.setVisibility(View.VISIBLE);
        }
        else {
            holder.add_photo.setVisibility(View.VISIBLE);
            holder.close.setVisibility(View.GONE);
            holder.handleView.setImageBitmap(null);
        }

        holder.root.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                if(item.getBitmap() != null) mDragStartListener.onStartDrag(holder);
                return false;
            }
        });

        holder.handleView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(item.getBitmap() == null)  mDragStartListener.onClick();
            }
        });

        holder.close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDragStartListener.onClose(mItems.get(holder.getBindingAdapterPosition()));
                removeItem(holder.getBindingAdapterPosition());
            }
        });
    }

    @Override
    public void onItemDismiss(int position) {
        mItems.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public boolean onItemMove(int fromPosition, int toPosition) {
        Collections.swap(mItems, fromPosition, toPosition);
        notifyItemMoved(fromPosition, toPosition);
        return true;
    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public static class ItemViewHolder extends RecyclerView.ViewHolder implements
            ItemTouchHelperViewHolder {

        //public final TextView textView;
        public final AnimatedRoundedImage handleView;
        public final ImageView add_photo;
        public final ConstraintLayout root;
        public final BesieLayout close;
        public final View select;

        public ItemViewHolder(View itemView) {
            super(itemView);
            //textView = (TextView) itemView.findViewById(R.id.text);
            handleView = (AnimatedRoundedImage) itemView.findViewById(R.id.pet_photo);
            add_photo = (ImageView) itemView.findViewById(R.id.add_photo);
            root = (ConstraintLayout) itemView.findViewById(R.id.root);
            select = (View) itemView.findViewById(R.id.select);
            close = (BesieLayout) itemView.findViewById(R.id.close);
        }

        @Override
        public void onItemSelected() {
            select.setAlpha(0.2f);
        }

        @Override
        public void onItemClear() {
            select.setAlpha(0.0f);
        }
    }
}