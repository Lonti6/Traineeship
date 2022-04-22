package ru.work.trainsheep;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
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
import ru.work.trainsheep.data.UserInfo;
import ru.work.trainsheep.send.ChatBlock;
import ru.work.trainsheep.send.SearchChatsRequest;
import ru.work.trainsheep.send.UserData;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class AllChatsActivity extends AppCompatActivity {

    Adapter adapter;
    UserData instance = UserInfo.getInstance().getData();
    ServerRepository server = ServerRepositoryFactory.getInstance();
    EditText findText;

    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_left_panel);
        final FlowingDrawer drawer = Util.connectActivityLayout(this, R.layout.activity_all_chats);

        findViewById(R.id.menuBut).setOnClickListener(v -> drawer.openMenu(true));

        RecyclerView rv = findViewById(R.id.rv);
        val linear = new LinearLayoutManager(this);
        linear.setReverseLayout(true);
        linear.setStackFromEnd(true);
        rv.setLayoutManager(linear);

        adapter = new Adapter(new ArrayList<>(), this);
        rv.setAdapter(adapter);
        //rv.addItemDecoration(new SpaceItemDecoration(80));
        Log.i(getClass().getSimpleName(), "onCreate: open cre");
        findText = findViewById(R.id.search_field);
        findText.setOnEditorActionListener((v, actionId, event) -> {
            Log.i("TAG", "onEditorAction: " + event);
            if (actionId == EditorInfo.IME_ACTION_SEARCH){
                sendSearchChats();
                InputMethodManager imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
                findText.clearFocus();
                return true;
            }
            return false;
        });
        autoUpdateMessageFromServer();
    }

    private void sendSearchChats(){
        val text = findText.getText().toString();
        Log.i(getClass().getSimpleName(), "------------------ sendSearchChats: " + text);
        server.searchChats(new SearchChatsRequest(text), (list) -> adapter.addAll(list));
    }

    private void autoUpdateMessageFromServer(){
        handler.postDelayed(() -> {
            server.getChats(adapter::updates);
            autoUpdateMessageFromServer();
        }, 3000);
    }

    @Override
    protected void onStart() {
        super.onStart();

        server.getChats(adapter::addAll);

        Util.prepareLeftIcon(this);
    }

    static class Adapter extends RecyclerView.Adapter<MyViewHolder>{
        List<ChatBlock> list;
        Context context;

        public Adapter(List<ChatBlock> list, Context context) {
            this.context = context;
            this.list = list;
        }

        public void addAll(List<ChatBlock> list){
            this.list.clear();
            this.list.addAll(list);
            //Log.e("AllChar Act", "addAll: " +list);
            notifyDataSetChanged();
        }




        @Override
        public @NonNull MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_block, parent, false));
        }

        SimpleDateFormat format = new SimpleDateFormat("HH:mm", Locale.ENGLISH);

        @Override
        public void onBindViewHolder(AllChatsActivity.MyViewHolder holder, int position) {
            val chat = list.get(position);
            holder.message.setText(chat.minMessage());
            holder.time.setText(format.format(new Date(chat.getLastMessageDate())));
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
                intent.putExtra("email", chat.getEmail());
                intent.putExtra("image", chat.getIcon());
                view.getContext().startActivity(intent);
            });

            Glide.with(context)
                    .load(chat.getIcon())
                    .placeholder(R.drawable.ic_zaticha)
                    .error(R.drawable.ic_zaticha)
                    .circleCrop()
                    .into(holder.icon);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public void updates(List<ChatBlock> chatBlocks) {
            if (!list.equals(chatBlocks)){
                addAll(chatBlocks);
            }
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