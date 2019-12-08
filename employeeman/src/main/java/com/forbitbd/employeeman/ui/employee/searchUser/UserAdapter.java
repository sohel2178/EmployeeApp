package com.forbitbd.employeeman.ui.employee.searchUser;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.forbitbd.androidutils.models.User;
import com.forbitbd.employeeman.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserHolder> implements Filterable {

    private SearchUserActivity activity;
    private List<User> userList;
    private List<User> originalList;
    private LayoutInflater inflater;

    public UserAdapter(SearchUserActivity activity) {
        this.activity = activity;
        this.userList = new ArrayList<>();
        this.inflater = LayoutInflater.from(activity);
    }

    @NonNull
    @Override
    public UserHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.single_user,parent,false);
        return new UserHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserHolder holder, int position) {
        User user = userList.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public void addUser(User user){
        userList.add(user);
        int position = userList.indexOf(user);
        notifyItemInserted(position);
    }

    public void addUsers(List<User> users){
        this.userList.addAll(users);
        notifyDataSetChanged();
    }

    public void clear(){
        userList.clear();
        notifyDataSetChanged();
    }

    @Override
    public Filter getFilter() {
        if(originalList==null){
            originalList = userList;
        }
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();

                List<User> filteredList = new ArrayList<>();

                if(charString.isEmpty()){
                    filteredList = originalList;
                }else{
                    List<User> tmpList = new ArrayList<>();
                    for (User x: originalList){
                        if(x.getEmail().toLowerCase().startsWith(charString)){
                            tmpList.add(x);
                        }
                    }

                    filteredList = tmpList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values=filteredList;

                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userList = (List<User>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    class UserHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        ImageView ivImage;
        TextView tvName,tvContact;

        public UserHolder(@NonNull View itemView) {
            super(itemView);

            ivImage = itemView.findViewById(R.id.image);
            tvName = itemView.findViewById(R.id.name);
            tvContact = itemView.findViewById(R.id.contact);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            activity.sendUserBack(userList.get(getAdapterPosition()));
        }

        public void bind(User user){
            Picasso.with(activity)
                    .load((user.getImage()))
                    .into(ivImage);

            tvName.setText(user.getName());
            tvContact.setText(user.getContact());
        }
    }
}
