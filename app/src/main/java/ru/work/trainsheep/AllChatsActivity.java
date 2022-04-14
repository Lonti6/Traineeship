package ru.work.trainsheep;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import lombok.NonNull;
import lombok.val;
import ru.work.trainsheep.data.ServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.send.ChatBlock;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class AllChatsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_all_chats);
        findViewById(R.id.user_button).setOnClickListener(v -> drawer.openMenu(true));

        RecyclerView rv = findViewById(R.id.rv);
        rv.setLayoutManager(new LinearLayoutManager(this));

        val adapter = new Adapter(new ArrayList<>(), this);
        rv.setAdapter(adapter);
        rv.addItemDecoration(new SpaceItemDecoration(80));

        ServerRepository server = ServerRepositoryFactory.getInstance();
        server.getChats(adapter::addAll);
    }

    static class Adapter extends RecyclerView.Adapter<MyViewHolder>{
        List<ChatBlock> list;
        Context context;

        public Adapter(List<ChatBlock> list, Context context) {
            this.context = context;
            this.list = list;
        }

        public void addAll(List<ChatBlock> list){
            this.list.addAll(list);
            notifyItemRangeInserted(this.list.size(), list.size());
        }


        @Override
        public @NonNull MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_block, parent, false));
        }

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");

        @Override
        public void onBindViewHolder(AllChatsActivity.MyViewHolder holder, int position) {
            val chat = list.get(position);
            holder.message.setText(chat.minMessage());
            holder.time.setText(format.format(chat.getLastMessageDate()));
            holder.header.setText(chat.getName());
            if (chat.getCountUnreadMessages() > 0){
                holder.bg.setBackgroundResource(R.color.bg_message);
                holder.countMessages.setText("" + chat.getCountUnreadMessages());
                holder.countMessages.setVisibility(View.VISIBLE);
            } else {
                holder.countMessages.setVisibility(View.GONE);
                holder.bg.setBackgroundResource(R.color.white);
            }
            holder.bg.setOnClickListener((view) -> {
                val intent = new Intent(view.getContext(), MessagesActivity.class);
                intent.putExtra("name", chat.getName());
                view.getContext().startActivity(intent);
            });

            Glide.with(context).load(chat.getIcon()).circleCrop().into(holder.icon);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    static class MyViewHolder extends RecyclerView.ViewHolder{

        TextView header;
        TextView message;
        TextView time;
        ImageView icon;
        ConstraintLayout bg;
        TextView countMessages;

        public MyViewHolder(View view) {
            super(view);
            header = view.findViewById(R.id.name);
            message = view.findViewById(R.id.message);
            time = view.findViewById(R.id.time);
            icon = view.findViewById(R.id.icon);
            bg = view.findViewById(R.id.bg);
            countMessages = view.findViewById(R.id.count_messages);
        }

    }


}