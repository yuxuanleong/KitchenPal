package com.example.kitchenpal.adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kitchenpal.FirebaseHelper;
import com.example.kitchenpal.FirebaseSuccessListener;
import com.example.kitchenpal.R;
import com.example.kitchenpal.models.RequestPendingModel;

import java.util.List;

public class RequestAcceptedAdapter extends RecyclerView.Adapter<RequestAcceptedAdapter.ViewHolder> {

    Context context;
    List<RequestPendingModel> modelList;

    public RequestAcceptedAdapter(Context context, List<RequestPendingModel> list) {
        this.context = context;
        this.modelList = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.card_request_pending_accpeted, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String name = modelList.get(position).getItemName();
        String publisher = modelList.get(position).getPublisher();


        holder.image.setImageResource(modelList.get(position).getImage());
        holder.publisher.setText(publisher);
        holder.itemName.setText(name);
        holder.condition.setText(modelList.get(position).getCondition());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(view.getContext());
                dialog.setContentView(R.layout.contact_details_dialog);
                dialog.getWindow().setBackgroundDrawableResource(R.drawable.dialog_bg);
                dialog.getWindow().setLayout(1000, ViewGroup.LayoutParams.WRAP_CONTENT);
                dialog.setCancelable(false);

                TextView close = dialog.findViewById(R.id.close_dialog);
                TextView username = dialog.findViewById(R.id.contact_username);
                TextView contact = dialog.findViewById(R.id.contact_details);

                FirebaseHelper.getContactData(publisher, new FirebaseSuccessListener() {
                    @Override
                    public void onDataFound(boolean isDataFetched) {
                        if (isDataFetched) {
                            username.setText(publisher);
                            String stContact = FirebaseHelper.getContact();
                            contact.setText(stContact);
                        }
                    }
                });

                dialog.show();
                close.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            dialog.dismiss();
                        }
                    });
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView image;
        TextView itemName, publisher, condition;

        public ViewHolder(View v) {
            super(v);
            image = v.findViewById(R.id.request_pending_accepted_image);
            itemName = v.findViewById(R.id.item_name_request_pending_accepted);
            publisher = v.findViewById(R.id.publisher_request_pending_accepted);
            condition = v.findViewById(R.id.condition_request_pending_accepted);
        }
    }
}
