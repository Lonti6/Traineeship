package ru.work.trainsheep;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ChatBlock {

    String name;
    String lastMessage;
    String icon;
    int countUnreadMessages;
    Date lastMessageDate;
    public static final int L = 30;
    public String minMessage(){

        if (lastMessage.length() > L){
            int index = lastMessage.lastIndexOf(' ', L - 1);
            if (index == -1)
                return lastMessage.substring(0, L) + "...";
            return lastMessage.substring(0, index) + "\n" + lastMessage.substring(index + 1, Math.min(index + 1 + L, lastMessage.length())) + "...";
        }
        return lastMessage;
    }

}
