package de.sabbertran.dressup;

import de.sabbertran.dressup.commands.DressUpCommand;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.URL;
import java.net.UnknownHostException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

public class DressUp extends JavaPlugin
{

    private Logger log = getLogger();

    private ArrayList<String> sql;
    private Connection sql_connection;

    private boolean testMode;

    private Wardrobe wardrobe;
    private HashMap<String, String> loggedOutHelmet;
    private File loggedOutHelmetFile;
    private HashMap<String, String> loggedOutChestplate;
    private File loggedOutChestplateFile;
    private HashMap<String, String> loggedOutLeggings;
    private File loggedOutLeggingsFile;
    private HashMap<String, String> loggedOutBoots;
    private File loggedOutBootsFile;

    private File messagesFile;
    private HashMap<String, String> messages;

    @Override
    public void onEnable()
    {
        getConfig().addDefault("DressUp.SQL", new String[]
        {
            "Adress", "Port", "Database", "User", "Password"
        });
        getConfig().addDefault("DressUp.TestMode", false);
        getConfig().options().copyDefaults(true);
        saveConfig();

        sql = (ArrayList<String>) getConfig().getStringList("DressUp.SQL");

        testMode = getConfig().getBoolean("DressUp.TestMode");

        if (testMode)
        {
            log.info("DressUp is running in test mode!");
        }

        loggedOutHelmet = new HashMap<String, String>();
        loggedOutChestplate = new HashMap<String, String>();
        loggedOutLeggings = new HashMap<String, String>();
        loggedOutBoots = new HashMap<String, String>();

        try
        {
            File folder = new File("plugins/DressUp/ArmorSaves");
            folder.mkdirs();

            loggedOutHelmetFile = new File("plugins/DressUp/ArmorSaves/helmets.yml");
            if (!loggedOutHelmetFile.exists())
            {
                loggedOutHelmetFile.createNewFile();
            }
            BufferedReader read_helmet = new BufferedReader(new FileReader(loggedOutHelmetFile));
            String line_helmet;
            while ((line_helmet = read_helmet.readLine()) != null)
            {
                String[] split = line_helmet.split(": ");
                loggedOutHelmet.put(split[0], split[1]);
            }
            read_helmet.close();

            loggedOutChestplateFile = new File("plugins/DressUp/ArmorSaves/chestplates.yml");
            if (!loggedOutChestplateFile.exists())
            {
                loggedOutChestplateFile.createNewFile();
            }
            BufferedReader read_chestplate = new BufferedReader(new FileReader(loggedOutChestplateFile));
            String line_chestplate;
            while ((line_chestplate = read_chestplate.readLine()) != null)
            {
                String[] split = line_chestplate.split(": ");
                loggedOutChestplate.put(split[0], split[1]);
            }
            read_chestplate.close();

            loggedOutLeggingsFile = new File("plugins/DressUp/ArmorSaves/leggings.yml");
            if (!loggedOutLeggingsFile.exists())
            {
                loggedOutLeggingsFile.createNewFile();
            }
            BufferedReader read_leggings = new BufferedReader(new FileReader(loggedOutLeggingsFile));
            String line_leggings;
            while ((line_leggings = read_leggings.readLine()) != null)
            {
                String[] split = line_leggings.split(": ");
                loggedOutLeggings.put(split[0], split[1]);
            }
            read_leggings.close();

            loggedOutBootsFile = new File("plugins/DressUp/ArmorSaves/boots.yml");
            if (!loggedOutBootsFile.exists())
            {
                loggedOutBootsFile.createNewFile();
            }
            BufferedReader read_boots = new BufferedReader(new FileReader(loggedOutBootsFile));
            String line_boots;
            while ((line_boots = read_boots.readLine()) != null)
            {
                String[] split = line_boots.split(": ");
                loggedOutBoots.put(split[0], split[1]);
            }
            read_boots.close();

            //messages
            messages = new HashMap<String, String>();

            messagesFile = new File("plugins/DressUp/messages.yml");
            if (!messagesFile.exists())
            {
                messagesFile.getParentFile().mkdirs();
                copy(getResource("messages.yml"), messagesFile);
            }
            BufferedReader read_messages = new BufferedReader(new FileReader(messagesFile));
            String line_messages;
            while ((line_messages = read_messages.readLine()) != null)
            {
                String[] split = line_messages.split(": ");
                if (split.length == 2)
                {
                    messages.put(split[0], split[1]);
                } else if (split.length > 2)
                {
                    String message = "";
                    for (int i = 1; i < split.length; i++)
                    {
                        message = message + split[i] + ": ";
                    }
                    message = message.substring(0, message.length() - 2);
                    messages.put(split[0], message);
                }
            }
            read_messages.close();
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(DressUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(DressUp.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (!sql.get(0).equalsIgnoreCase("Adress") && !sql.get(1).equalsIgnoreCase("Port") && !sql.get(2).equalsIgnoreCase("Database") && !sql.get(3).equalsIgnoreCase("User") && !sql.get(4).equalsIgnoreCase("Password"))
        {
            try
            {
                getSqlConnection().createStatement().execute("CREATE TABLE IF NOT EXISTS dressup (id INT AUTO_INCREMENT, uuid text NOT NULL, hat_blocks text NOT NULL, helmets_static text NOT NULL, chestplates_static text NOT NULL, leggings_static text NOT NULL, boots_static text NOT NULL, helmets_changing text NOT NULL, chestplates_changing text NOT NULL, leggings_changing text NOT NULL, boots_changing text NOT NULL, PRIMARY KEY (id))");
            } catch (SQLException ex)
            {
                Logger.getLogger(DressUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
            log.info("Please set your database details in the config");
        }

        getCommand("dressup").setExecutor(new DressUpCommand(this));
        getServer().getPluginManager().registerEvents(new Events(this), this);

        wardrobe = new Wardrobe(this);

        if (getServer().getOnlinePlayers().length > 0)
        {
            for (Player p : getServer().getOnlinePlayers())
            {
                wardrobe.updateWardrobe(p);
            }
            getServer().getScheduler().scheduleSyncDelayedTask(this, new Runnable()
            {
                @Override
                public void run()
                {
                    for (Player p : getServer().getOnlinePlayers())
                    {
                        wardrobe.loadPlayerArmor(p);
                    }
                }
            }, 20L);
        }

        log.info("DressUp enabled");
    }

    @Override
    public void onDisable()
    {
        try
        {
            loggedOutHelmetFile.delete();
            loggedOutHelmetFile.createNewFile();
            PrintWriter pw_helmet = new PrintWriter(loggedOutHelmetFile);
            for (Map.Entry<String, String> entry : loggedOutHelmet.entrySet())
            {
                pw_helmet.println(entry.getKey() + ": " + entry.getValue());
            }
            pw_helmet.flush();
            pw_helmet.close();

            loggedOutChestplateFile.delete();
            loggedOutChestplateFile.createNewFile();
            PrintWriter pw_chestplate = new PrintWriter(loggedOutChestplateFile);
            for (Map.Entry<String, String> entry : loggedOutChestplate.entrySet())
            {
                pw_chestplate.println(entry.getKey() + ": " + entry.getValue());
            }
            pw_chestplate.flush();
            pw_chestplate.close();

            loggedOutLeggingsFile.delete();
            loggedOutLeggingsFile.createNewFile();
            PrintWriter pw_leggings = new PrintWriter(loggedOutLeggingsFile);
            for (Map.Entry<String, String> entry : loggedOutLeggings.entrySet())
            {
                pw_leggings.println(entry.getKey() + ": " + entry.getValue());
            }
            pw_leggings.flush();
            pw_leggings.close();

            loggedOutBootsFile.delete();
            loggedOutBootsFile.createNewFile();
            PrintWriter pw_boots = new PrintWriter(loggedOutBootsFile);
            for (Map.Entry<String, String> entry : loggedOutBoots.entrySet())
            {
                pw_boots.println(entry.getKey() + ": " + entry.getValue());
            }
            pw_boots.flush();
            pw_boots.close();
        } catch (FileNotFoundException ex)
        {
            Logger.getLogger(DressUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(DressUp.class.getName()).log(Level.SEVERE, null, ex);
        }

        log.info("DressUp disabled");
    }

    public String getMessage(String key)
    {
        if (messages.containsKey(key))
        {
            return ChatColor.translateAlternateColorCodes('&', messages.get(key));
        } else
        {
            return "Error";
        }
    }

    public Connection getSqlConnection()
    {
        try
        {
            if (sql_connection == null || sql_connection.isClosed())
            {
                Class.forName("com.mysql.jdbc.Driver");
                String url = "jdbc:mysql://" + sql.get(0) + ":" + sql.get(1) + "/" + sql.get(2);
                sql_connection = DriverManager.getConnection(url, sql.get(3), sql.get(4));
            }
        } catch (SQLException | ClassNotFoundException ex)
        {
            Logger.getLogger(DressUp.class.getName()).log(Level.SEVERE, null, ex);
        }
        return sql_connection;
    }

    private void logStart()
    {
        try
        {
            URL url = new URL("http://sabbertran.de/plugins/dressup/log.php?name=" + getServer().getName() + "&ip=" + getServer().getIp() + "&port=" + getServer().getPort());
            url.openStream();
        } catch (UnknownHostException ex)
        {
            Logger.getLogger(DressUp.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex)
        {
            Logger.getLogger(DressUp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean contains(ArrayList<HashMap<ItemStack[], String>> list, String name)
    {
        name = ChatColor.stripColor(name);
        for (HashMap<ItemStack[], String> entry : list)
        {
            for (Map.Entry<ItemStack[], String> entry2 : entry.entrySet())
            {
                if (entry2.getValue().contains(name))
                {
                    return true;
                }
            }
        }
        return false;
    }

    private void copy(InputStream in, File file)
    {
        try
        {
            OutputStream out = new FileOutputStream(file);
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0)
            {
                out.write(buf, 0, len);
            }
            out.close();
            in.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public boolean isTestMode()
    {
        return testMode;
    }

    public Wardrobe getWardrobe()
    {
        return wardrobe;
    }

    public HashMap<String, String> getLoggedOutHelmet()
    {
        return loggedOutHelmet;
    }

    public HashMap<String, String> getLoggedOutChestplate()
    {
        return loggedOutChestplate;
    }

    public HashMap<String, String> getLoggedOutLeggings()
    {
        return loggedOutLeggings;
    }

    public HashMap<String, String> getLoggedOutBoots()
    {
        return loggedOutBoots;
    }
}
