package ru.work.trainsheep;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lombok.NonNull;
import lombok.val;
import ru.work.trainsheep.data.ServerRepositoryFactory;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MessagesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        String name = null;
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
        }
        if (name == null)
            name = "Избранное";

        val server = ServerRepositoryFactory.getInstance();

        val adapter = new Adapter(name);

        RecyclerView recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

        server.getMessages(new ChatRequest(name, 1, 20), (res) -> {
            if(res.isSuccess()){
                adapter.addAll(res.getResult().getMessages());
                recyclerView.smoothScrollToPosition(adapter.size() - 1);
            }
        });

        TextView nameTop = findViewById(R.id.name_top);
        nameTop.setText(name);

        findViewById(R.id.backButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, AllChatsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent);
        });

    }

    static class Adapter extends RecyclerView.Adapter<MyHolder>{
        List<ChatMessage> list;
        String name;

        public Adapter(String name) {
            this.name = name;
            this.list = new ArrayList<>();
        }

        public void addAll(List<ChatMessage> list){
            this.list.addAll(0, list);
            notifyItemRangeInserted(0, list.size());
        }
        public int size(){
            return list.size();
        }



        @Override
        public @NonNull MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_block, parent, false));
        }

        SimpleDateFormat format = new SimpleDateFormat("hh:mm");

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            val message = list.get(position);
            val params = (ConstraintLayout.LayoutParams) holder.bg.getLayoutParams();
            if(Objects.equals(message.getSender(), name)) {
                params.horizontalBias = 0;
                holder.bg.setBackgroundResource(R.drawable.left_message);
            } else  {
                params.horizontalBias = 1;
                holder.bg.setBackgroundResource(R.drawable.right_message);
            }

            holder.message.setText(message.getMessage());
            holder.time.setText(format.format(message.getDate()));

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    static class MyHolder extends RecyclerView.ViewHolder{


        TextView message;
        TextView time;
        ConstraintLayout bg;

        public MyHolder(View view) {
            super(view);
            message = view.findViewById(R.id.content);
            time = view.findViewById(R.id.time_message);
            bg = view.findViewById(R.id.constraint);
        }

    }
}