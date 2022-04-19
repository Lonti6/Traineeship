package ru.work.trainsheep;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import lombok.NonNull;
import lombok.val;
import ru.work.trainsheep.data.FakeServerRepository;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.ChatMessage;
import ru.work.trainsheep.send.ChatRequest;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import static android.content.ContentValues.TAG;

public class MessagesActivity extends AppCompatActivity {
    String email;
    RecyclerView recyclerView;
    Adapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages);
        //Util.connectActivityLayout(this, R.layout.activity_messages);
        String name = null;
        email = "no_email";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            name = extras.getString("name");
            email = extras.getString("email");
        }
        if (name == null)
            name = "Избранное";

        adapter = new Adapter(email);

        recyclerView = findViewById(R.id.rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);



        TextView nameTop = findViewById(R.id.name_top);
        nameTop.setText(name);

        findViewById(R.id.backButton).setOnClickListener(v -> {
            Intent intent = new Intent(this, AllChatsActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            this.startActivity(intent);
        });
        final EditText text = findViewById(R.id.text_message);
        text.setImeActionLabel("Send", KeyEvent.KEYCODE_ENTER);
        text.setOnEditorActionListener((v, actionId, event) -> {
            if (event != null && event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                sendMessage(adapter, recyclerView, text);
                return true;
            }
            return false;
        });
        findViewById(R.id.press_send_message).setOnClickListener(v -> {
            sendMessage(adapter, recyclerView, text);
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        val server = ServerRepositoryFactory.getInstance();
        server.getMessages(new ChatRequest(email, 1, 20), (res) -> {
            Log.i(getClass().getSimpleName(), "onCreate: " + res.getMessages() );
            adapter.addAll(res.getMessages(), true);
            recyclerView.smoothScrollToPosition(adapter.size() - 1);
        });
    }

    private void sendMessage(Adapter adapter, RecyclerView recyclerView, EditText text) {
        val mes = new ChatMessage(UserInfo.getInstance().getData().getEmail(), text.getText().toString(), new Date().getTime());
        adapter.add(mes);
        recyclerView.smoothScrollToPosition(adapter.size() - 1);
        text.setText("");
    }

    static class Adapter extends RecyclerView.Adapter<MyHolder> {
        List<ChatMessage> list;
        String name;

        public Adapter(String name) {
            this.name = name;
            this.list = new ArrayList<>();
        }

        public void addAll(List<ChatMessage> list, boolean clear) {
            if (clear) {
                val size = this.list.size();
                this.list.clear();
                notifyItemRangeRemoved(0, size);
            }
            this.list.addAll(0, list);
            notifyItemRangeInserted(0, list.size());
        }

        public void add(ChatMessage message) {
            this.list.add(message);
            notifyItemInserted(list.size() - 1);
        }

        public int size() {
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
            if (Objects.equals(message.getSender(), name)) {
                params.horizontalBias = 0;
                holder.bg.setBackgroundResource(R.drawable.left_message);
            } else {
                params.horizontalBias = 1;
                holder.bg.setBackgroundResource(R.drawable.right_message);
            }

            holder.message.setText(message.getMessage());
            holder.time.setText(format.format(new Date(message.getDate())));

        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    static class MyHolder extends RecyclerView.ViewHolder {


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