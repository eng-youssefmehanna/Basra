package main.java.model;

import main.java.repository.Lobbyrepository;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class AvatarManager {
    private static final List<String> AVATAR_URLS = List.of(
            "https://gpbnrcqmodagqloldhwm.supabase.co/storage/v1/object/public/avatars/Gemini_Generated_Image_1n8zgr1n8zgr1n8z.png"
            , "https://gpbnrcqmodagqloldhwm.supabase.co/storage/v1/object/public/avatars/Gemini_Generated_Image_gnwgiggnwgiggnwg.png"
            , "https://gpbnrcqmodagqloldhwm.supabase.co/storage/v1/object/public/avatars/Gemini_Generated_Image_gutxdkgutxdkgutx.png"
            , "https://gpbnrcqmodagqloldhwm.supabase.co/storage/v1/object/public/avatars/Gemini_Generated_Image_iu5mlsiu5mlsiu5m.png"
            ,"https://gpbnrcqmodagqloldhwm.supabase.co/storage/v1/object/public/avatars/Gemini_Generated_Image_wf03bxwf03bxwf03.png"
    );


    public static String getRandomAvatar() {
        return AVATAR_URLS.get(ThreadLocalRandom.current().nextInt(AVATAR_URLS.size()));
    }
}
