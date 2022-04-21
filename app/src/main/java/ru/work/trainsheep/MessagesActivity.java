package ru.work.trainsheep;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import lombok.NonNull;
import lombok.val;
import ru.work.trainsheep.data.ServerRepositoryFactory;
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.ChatMessage;
import ru.work.trainsheep.send.ChatRequest;
import ru.work.trainsheep.send.SendMessageRequest;

import java.text.SimpleDateFormat;
import java.util.*;


public class MessagesActivity extends AppCompatActivity {
    String email;
    RecyclerView recyclerView;
    Adapter adapter;
    Handler handler = new Handler(Looper.getMainLooper());

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
            val imageSrc = extras.getString("image");
            if(imageSrc != null){
                Glide.with(getBaseContext())
                        .load(imageSrc)
                        .placeholder(R.drawable.ic_zaticha)
                        .error(R.drawable.ic_zaticha)
                        .circleCrop()
                        .into((ImageView) findViewById(R.id.profile_image));
            }
        }
        if (name == null)
            name = "Избранное";

        adapter = new Adapter();

        recyclerView = findViewById(R.id.rv);
        val linear = new LinearLayoutManager(this);
        linear.setReverseLayout(true);
        //linear.setStackFromEnd(true);
        recyclerView.setLayoutManager(linear);
        recyclerView.setHasFixedSize(true);
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
    private boolean canAutoUpdate = false;
    private void autoUpdateMessageFromServer(){
        handler.postDelayed(() -> {
            updateMessages();
            if (canAutoUpdate)
                autoUpdateMessageFromServer();
        }, 1500);
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateMessages();
        canAutoUpdate = true;
        autoUpdateMessageFromServer();
    }

    @Override
    protected void onStop() {
        super.onStop();
        canAutoUpdate = false;
    }

    private void updateMessages() {
        val server = ServerRepositoryFactory.getInstance();
        server.getMessages(new ChatRequest(email, 0, 20), (res) -> {
            Log.i(getClass().getSimpleName(), "update messages: " + res.getMessages());
            adapter.addAll(res.getMessages());

        });
    }

    private void sendMessage(Adapter adapter, RecyclerView recyclerView, EditText text) {
        val server = ServerRepositoryFactory.getInstance();
        val message = text.getText().toString();
        if (message.equals(""))
            return;
        val mes = new ChatMessage(UserInfo.getInstance().getData().getEmail(), message, 0);
        adapter.add(mes);
        recyclerView.smoothScrollToPosition(0);
        server.getSendMessage(new SendMessageRequest(email, message), (mess) -> {
            adapter.update(mess);
            //updateMessages();
        });
        text.setText("");
    }

    class Adapter extends RecyclerView.Adapter<MyHolder> {
        List<ChatMessage> list;

        public Adapter() {
            this.list = new ArrayList<>();
        }

        public void addAll(List<ChatMessage> nlist) {
            for (int i = 0; i < nlist.size() && (list.size() <= i || !nlist.get(i).equals(list.get(i))); i++) {
                list.add(i, nlist.get(i));
                notifyItemInserted(i);
                if (adapter.size() > 0)
                    recyclerView.smoothScrollToPosition(0);
            }
            for(int i = list.size() - 1; i >= nlist.size(); i--){
                list.remove(i);
                notifyItemRemoved(i);
            }
        }

        public void add(ChatMessage message) {
            this.list.add(0, message);
            notifyItemInserted(0);
        }

        public int size() {
            return list.size();
        }


        @Override
        public @NonNull MyHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.message_block, parent, false));
        }

        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        @Override
        public void onBindViewHolder(MyHolder holder, int position) {
            val message = list.get(position);
            val params = (ConstraintLayout.LayoutParams) holder.bg.getLayoutParams();
            Log.i(getClass().getSimpleName(), "onBindViewHolder: bind " + message.getSender() + " " + UserInfo.getInstance().getData().getEmail() + " " + message.getMessage());
            if (Objects.equals(message.getSender(), UserInfo.getInstance().getData().getEmail())) {
                params.horizontalBias = 1;
                holder.bg.setBackgroundResource(R.drawable.right_message);
            } else {
                params.horizontalBias = 0;
                holder.bg.setBackgroundResource(R.drawable.left_message);
            }

            holder.message.setText(message.getMessage());
            if (message.getDate() != 0) {
                holder.time.setText(format.format(new Date(message.getDate())));
            } else {
                holder.time.setText("...");
            }

        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void update(ChatMessage chatMessage) {
            for (int i = 0; i < list.size() && list.get(i).getDate() == 0; i++) {
                if (list.get(i).getMessage().equals(chatMessage.getMessage())) {
                    list.get(i).setDate(chatMessage.getDate());
                    notifyItemChanged(i);
                }
            }
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