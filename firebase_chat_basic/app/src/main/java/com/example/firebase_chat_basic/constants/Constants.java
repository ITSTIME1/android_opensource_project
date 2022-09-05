package com.example.firebase_chat_basic.constants;


/**
 * [Constants]
 *
 * <Topic>
 *
 *     This class takes care of constants.
 *     because we want to make less glueCode.
 *     so I wrote that real time database url, chatMessageViewType, chatImageViewType.
 *
 * </Topic>
 */
public class Constants {
    public static final String real_time_database_root_url = "https://fir-chat-basic-dfd08-default-rtdb.firebaseio.com/";
    public static final String videoTAG = "VideoPlayerRecyclerView";
    public static final int chatMessageViewType = 0;
    public static final int chatImageViewType = 1;
    public static final int chatVideoViewType = 2;
    public static final int MSG_IMAGE_LIST = 0;
    public Constants() {
    }
}

