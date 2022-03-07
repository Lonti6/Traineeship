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

    public String minMessage(){
        if (lastMessage.length() > 24){
            return lastMessage.substring(0, 24) + "...";
        }
        return lastMessage;
    }

}
