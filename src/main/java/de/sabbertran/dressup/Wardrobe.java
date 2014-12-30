package de.sabbertran.dressup;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class Wardrobe
{

    private DressUp main;
    private HashMap<Player, HashMap<String, Integer>> invTasks;
    private HashMap<Player, Integer[]> armorTasks;

    private HashMap<String, ArrayList<ItemStack>> helmetStatic; //UUID, helmets
    private HashMap<String, ArrayList<ItemStack>> chestplateStatic; //UUID, chestplatess
    private HashMap<String, ArrayList<ItemStack>> leggingsStatic; //UUID, leggings
    private HashMap<String, ArrayList<ItemStack>> bootsStatic; //UUID, boots

    private HashMap<String, ArrayList<HashMap<ItemStack[], String>>> helmetChanging; //UUID, List: Items/Name/Changing Time
    private HashMap<String, ArrayList<HashMap<ItemStack[], String>>> chestplateChanging; //UUID, List: Items/Name/Changing Time
    private HashMap<String, ArrayList<HashMap<ItemStack[], String>>> leggingsChanging; //UUID, List: Items/Name/Changing Time
    private HashMap<String, ArrayList<HashMap<ItemStack[], String>>> bootsChanging; //UUID, List: Items/Name/Changing Time

    private HashMap<String, ArrayList<ItemStack>> hatBlocks;

    public Wardrobe(DressUp main)
    {
        this.main = main;
        invTasks = new HashMap<Player, HashMap<String, Integer>>();
        armorTasks = new HashMap<Player, Integer[]>();

        helmetStatic = new HashMap<String, ArrayList<ItemStack>>();
        chestplateStatic = new HashMap<String, ArrayList<ItemStack>>();
        leggingsStatic = new HashMap<String, ArrayList<ItemStack>>();
        bootsStatic = new HashMap<String, ArrayList<ItemStack>>();

        helmetChanging = new HashMap<String, ArrayList<HashMap<ItemStack[], String>>>();
        chestplateChanging = new HashMap<String, ArrayList<HashMap<ItemStack[], String>>>();
        leggingsChanging = new HashMap<String, ArrayList<HashMap<ItemStack[], String>>>();
        bootsChanging = new HashMap<String, ArrayList<HashMap<ItemStack[], String>>>();

        hatBlocks = new HashMap<String, ArrayList<ItemStack>>();
    }

    public void openMainMenu(final Player p)
    {
        final Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("menuname"));

        ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
        LeatherArmorMeta helmet_meta = (LeatherArmorMeta) helmet.getItemMeta();
        helmet_meta.setDisplayName(main.getMessage("helmetstatic"));
        helmet_meta.setColor(Color.WHITE);
        helmet.setItemMeta(helmet_meta);

        ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
        LeatherArmorMeta chest_meta = (LeatherArmorMeta) chest.getItemMeta();
        chest_meta.setDisplayName(main.getMessage("chestplatestatic"));
        chest_meta.setColor(Color.WHITE);
        chest.setItemMeta(chest_meta);

        ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
        LeatherArmorMeta leggings_meta = (LeatherArmorMeta) leggings.getItemMeta();
        leggings_meta.setDisplayName(main.getMessage("leggingsstatic"));
        leggings_meta.setColor(Color.WHITE);
        leggings.setItemMeta(leggings_meta);

        ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
        LeatherArmorMeta boots_meta = (LeatherArmorMeta) boots.getItemMeta();
        boots_meta.setDisplayName(main.getMessage("bootsstatic"));
        boots_meta.setColor(Color.WHITE);
        boots.setItemMeta(boots_meta);

        inv.setItem(12, helmet);
        inv.setItem(21, chest);
        inv.setItem(30, leggings);
        inv.setItem(39, boots);

        ItemStack removeHelmet = new ItemStack(Material.STAINED_GLASS_PANE);
        removeHelmet.setDurability((short) 14);
        ItemMeta removeHelmet_meta = removeHelmet.getItemMeta();
        removeHelmet_meta.setDisplayName(main.getMessage("removehelmet"));
        removeHelmet.setItemMeta(removeHelmet_meta);

        ItemStack removeChestplate = new ItemStack(Material.STAINED_GLASS_PANE);
        removeChestplate.setDurability((short) 14);
        ItemMeta removeChestplate_meta = removeChestplate.getItemMeta();
        removeChestplate_meta.setDisplayName(main.getMessage("removechestplate"));
        removeChestplate.setItemMeta(removeChestplate_meta);

        ItemStack removeLeggings = new ItemStack(Material.STAINED_GLASS_PANE);
        removeLeggings.setDurability((short) 14);
        ItemMeta removeLeggings_meta = removeLeggings.getItemMeta();
        removeLeggings_meta.setDisplayName(main.getMessage("removeleggings"));
        removeLeggings.setItemMeta(removeLeggings_meta);

        ItemStack removeBoots = new ItemStack(Material.STAINED_GLASS_PANE);
        removeBoots.setDurability((short) 14);
        ItemMeta removeBoots_meta = removeBoots.getItemMeta();
        removeBoots_meta.setDisplayName(main.getMessage("removeboots"));
        removeBoots.setItemMeta(removeBoots_meta);

        inv.setItem(17, removeHelmet);
        inv.setItem(26, removeChestplate);
        inv.setItem(35, removeLeggings);
        inv.setItem(44, removeBoots);

        ItemStack hatBlocks = new ItemStack(Material.JACK_O_LANTERN);
        ItemMeta hatBlocksMeta = hatBlocks.getItemMeta();
        hatBlocksMeta.setDisplayName(main.getMessage("hatblocksitem"));
        hatBlocks.setItemMeta(hatBlocksMeta);

        inv.setItem(10, hatBlocks);

        p.openInventory(inv);

        invTasks.get(p).put(inv.getTitle(), main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
        {
            @Override
            public void run()
            {

                ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
                LeatherArmorMeta helmet_meta = (LeatherArmorMeta) helmet.getItemMeta();
                helmet_meta.setDisplayName(main.getMessage("helmetchanging"));
                helmet_meta.setColor(Color.fromRGB(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                helmet.setItemMeta(helmet_meta);

                ItemStack chest = new ItemStack(Material.LEATHER_CHESTPLATE);
                LeatherArmorMeta chest_meta = (LeatherArmorMeta) chest.getItemMeta();
                chest_meta.setDisplayName(main.getMessage("chestplatechanging"));
                chest_meta.setColor(Color.fromRGB(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                chest.setItemMeta(chest_meta);

                ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                LeatherArmorMeta leggings_meta = (LeatherArmorMeta) leggings.getItemMeta();
                leggings_meta.setDisplayName(main.getMessage("leggingschanging"));
                leggings_meta.setColor(Color.fromRGB(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                leggings.setItemMeta(leggings_meta);

                ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                LeatherArmorMeta boots_meta = (LeatherArmorMeta) boots.getItemMeta();
                boots_meta.setDisplayName(main.getMessage("bootschanging"));
                boots_meta.setColor(Color.fromRGB(new Random().nextInt(256), new Random().nextInt(256), new Random().nextInt(256)));
                boots.setItemMeta(boots_meta);

                inv.setItem(14, helmet);
                inv.setItem(23, chest);
                inv.setItem(32, leggings);
                inv.setItem(41, boots);
            }
        }, 0L, 4L));

        updateWardrobe(p);
    }

    public void openHelmetStatic(Player p, int page)
    {
        Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("helmetstaticmenu"));

        if (helmetStatic.containsKey(p.getUniqueId().toString().replace("-", "")) && helmetStatic.get(p.getUniqueId().toString().replace("-", "")).size() > 45 * page)
        {
            ItemStack next = new ItemStack(Material.LEVER);
            ItemMeta next_meta = next.getItemMeta();
            next_meta.setDisplayName(main.getMessage("nextpage"));
            next.setItemMeta(next_meta);
            inv.setItem(7, next);
        }
        if (page > 1)
        {
            ItemStack prev = new ItemStack(Material.LEVER);
            ItemMeta prev_meta = prev.getItemMeta();
            prev_meta.setDisplayName(main.getMessage("previouspage"));
            prev.setItemMeta(prev_meta);
            inv.setItem(1, prev);
        }
        ItemStack mainMenu = new ItemStack(Material.BOOK);
        ItemMeta mainMenuMeta = mainMenu.getItemMeta();
        mainMenuMeta.setDisplayName(main.getMessage("backtomainmenu"));
        mainMenu.setItemMeta(mainMenuMeta);
        inv.setItem(0, mainMenu);

        ItemStack ipage = new ItemStack(Material.PAPER);
        ipage.setAmount(page);
        ItemMeta ipage_meta = ipage.getItemMeta();
        ipage_meta.setDisplayName(main.getMessage("currentpage").replace("%page", "" + page));
        ipage.setItemMeta(ipage_meta);
        inv.setItem(4, ipage);

        if (helmetStatic.get(p.getUniqueId().toString().replace("-", "")).size() == 0)
        {
            ItemStack info = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName(main.getMessage("nostatichelmets"));
            info.setItemMeta(info_meta);
            inv.setItem(9, info);
        }

        ArrayList<ItemStack> items = helmetStatic.get(p.getUniqueId().toString().replace("-", ""));
        if (items != null)
        {
            for (int i = 0; i < 45 && i + (page * 45 - 45) < items.size(); i++)
            {
                inv.setItem(i + 9, items.get(i + (page * 45 - 45)));
            }
        }

        p.openInventory(inv);
    }

    public void openChestplateStatic(Player p, int page)
    {
        Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("chestplatestaticmenu"));

        if (chestplateStatic.containsKey(p.getUniqueId().toString().replace("-", "")) && chestplateStatic.get(p.getUniqueId().toString().replace("-", "")).size() > 45 * page)
        {
            ItemStack next = new ItemStack(Material.LEVER);
            ItemMeta next_meta = next.getItemMeta();
            next_meta.setDisplayName(main.getMessage("nextpage"));
            next.setItemMeta(next_meta);
            inv.setItem(7, next);
        }
        if (page > 1)
        {
            ItemStack prev = new ItemStack(Material.LEVER);
            ItemMeta prev_meta = prev.getItemMeta();
            prev_meta.setDisplayName(main.getMessage("previouspage"));
            prev.setItemMeta(prev_meta);
            inv.setItem(1, prev);
        }
        ItemStack mainMenu = new ItemStack(Material.BOOK);
        ItemMeta mainMenuMeta = mainMenu.getItemMeta();
        mainMenuMeta.setDisplayName(main.getMessage("backtomainmenu"));
        mainMenu.setItemMeta(mainMenuMeta);
        inv.setItem(0, mainMenu);

        ItemStack ipage = new ItemStack(Material.PAPER);
        ipage.setAmount(page);
        ItemMeta ipage_meta = ipage.getItemMeta();
        ipage_meta.setDisplayName(main.getMessage("currentpage").replace("%page", "" + page));
        ipage.setItemMeta(ipage_meta);
        inv.setItem(4, ipage);

        if (chestplateStatic.get(p.getUniqueId().toString().replace("-", "")).size() == 0)
        {
            ItemStack info = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName(main.getMessage("nostaticchestplates"));
            info.setItemMeta(info_meta);
            inv.setItem(9, info);
        }

        ArrayList<ItemStack> items = chestplateStatic.get(p.getUniqueId().toString().replace("-", ""));
        if (items != null)
        {
            for (int i = 0; i < 45 && i + (page * 45 - 45) < items.size(); i++)
            {
                inv.setItem(i + 9, items.get(i + (page * 45 - 45)));
            }
        }

        p.openInventory(inv);
    }

    public void openLeggingsStatic(Player p, int page)
    {
        Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("leggingsstaticmenu"));

        if (leggingsStatic.containsKey(p.getUniqueId().toString().replace("-", "")) && leggingsStatic.get(p.getUniqueId().toString().replace("-", "")).size() > 45 * page)
        {
            ItemStack next = new ItemStack(Material.LEVER);
            ItemMeta next_meta = next.getItemMeta();
            next_meta.setDisplayName(main.getMessage("nextpage"));
            next.setItemMeta(next_meta);
            inv.setItem(7, next);
        }
        if (page > 1)
        {
            ItemStack prev = new ItemStack(Material.LEVER);
            ItemMeta prev_meta = prev.getItemMeta();
            prev_meta.setDisplayName(main.getMessage("previouspage"));
            prev.setItemMeta(prev_meta);
            inv.setItem(1, prev);
        }
        ItemStack mainMenu = new ItemStack(Material.BOOK);
        ItemMeta mainMenuMeta = mainMenu.getItemMeta();
        mainMenuMeta.setDisplayName(main.getMessage("backtomainmenu"));
        mainMenu.setItemMeta(mainMenuMeta);
        inv.setItem(0, mainMenu);

        ItemStack ipage = new ItemStack(Material.PAPER);
        ipage.setAmount(page);
        ItemMeta ipage_meta = ipage.getItemMeta();
        ipage_meta.setDisplayName(main.getMessage("currentpage").replace("%page", "" + page));
        ipage.setItemMeta(ipage_meta);
        inv.setItem(4, ipage);

        if (leggingsStatic.get(p.getUniqueId().toString().replace("-", "")).size() == 0)
        {
            ItemStack info = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName(main.getMessage("nostaticleggings"));
            info.setItemMeta(info_meta);
            inv.setItem(9, info);
        }

        ArrayList<ItemStack> items = leggingsStatic.get(p.getUniqueId().toString().replace("-", ""));
        if (items != null)
        {
            for (int i = 0; i < 45 && i + (page * 45 - 45) < items.size(); i++)
            {
                inv.setItem(i + 9, items.get(i + (page * 45 - 45)));
            }
        }

        p.openInventory(inv);
    }

    public void openBootsStatic(Player p, int page)
    {
        Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("bootsstaticmenu"));

        if (bootsStatic.containsKey(p.getUniqueId().toString().replace("-", "")) && bootsStatic.get(p.getUniqueId().toString().replace("-", "")).size() > 45 * page)
        {
            ItemStack next = new ItemStack(Material.LEVER);
            ItemMeta next_meta = next.getItemMeta();
            next_meta.setDisplayName(main.getMessage("nextpage"));
            next.setItemMeta(next_meta);
            inv.setItem(7, next);
        }
        if (page > 1)
        {
            ItemStack prev = new ItemStack(Material.LEVER);
            ItemMeta prev_meta = prev.getItemMeta();
            prev_meta.setDisplayName(main.getMessage("previouspage"));
            prev.setItemMeta(prev_meta);
            inv.setItem(1, prev);
        }
        ItemStack mainMenu = new ItemStack(Material.BOOK);
        ItemMeta mainMenuMeta = mainMenu.getItemMeta();
        mainMenuMeta.setDisplayName(main.getMessage("backtomainmenu"));
        mainMenu.setItemMeta(mainMenuMeta);
        inv.setItem(0, mainMenu);

        ItemStack ipage = new ItemStack(Material.PAPER);
        ipage.setAmount(page);
        ItemMeta ipage_meta = ipage.getItemMeta();
        ipage_meta.setDisplayName(main.getMessage("currentpage").replace("%page", "" + page));
        ipage.setItemMeta(ipage_meta);
        inv.setItem(4, ipage);

        if (bootsStatic.get(p.getUniqueId().toString().replace("-", "")).size() == 0)
        {
            ItemStack info = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName(main.getMessage("nostaticboots"));
            info.setItemMeta(info_meta);
            inv.setItem(9, info);
        }

        ArrayList<ItemStack> items = bootsStatic.get(p.getUniqueId().toString().replace("-", ""));
        if (items != null)
        {
            for (int i = 0; i < 45 && i + (page * 45 - 45) < items.size(); i++)
            {
                inv.setItem(i + 9, items.get(i + (page * 45 - 45)));
            }
        }

        p.openInventory(inv);
    }

    public void openHelmetChanging(Player p, int page)
    {
        final Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("helmetchangingmenu"));

        if (helmetChanging.containsKey(p.getUniqueId().toString().replace("-", "")) && helmetChanging.get(p.getUniqueId().toString().replace("-", "")).size() > 45 * page)
        {
            ItemStack next = new ItemStack(Material.LEVER);
            ItemMeta next_meta = next.getItemMeta();
            next_meta.setDisplayName(main.getMessage("nextpage"));
            next.setItemMeta(next_meta);
            inv.setItem(7, next);
        }
        if (page > 1)
        {
            ItemStack prev = new ItemStack(Material.LEVER);
            ItemMeta prev_meta = prev.getItemMeta();
            prev_meta.setDisplayName(main.getMessage("previouspage"));
            prev.setItemMeta(prev_meta);
            inv.setItem(1, prev);
        }
        ItemStack mainMenu = new ItemStack(Material.BOOK);
        ItemMeta mainMenuMeta = mainMenu.getItemMeta();
        mainMenuMeta.setDisplayName(main.getMessage("backtomainmenu"));
        mainMenu.setItemMeta(mainMenuMeta);
        inv.setItem(0, mainMenu);

        ItemStack ipage = new ItemStack(Material.PAPER);
        ipage.setAmount(page);
        ItemMeta ipage_meta = ipage.getItemMeta();
        ipage_meta.setDisplayName(main.getMessage("currentpage").replace("%page", "" + page));
        ipage.setItemMeta(ipage_meta);
        inv.setItem(4, ipage);

        if (helmetChanging.containsKey(p.getUniqueId().toString().replace("-", "")) && helmetChanging.get(p.getUniqueId().toString().replace("-", "")).size() == 0)
        {
            ItemStack info = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName(main.getMessage("nochanginghelmets"));
            info.setItemMeta(info_meta);
            inv.setItem(9, info);
        }

        ArrayList<HashMap<ItemStack[], String>> items = helmetChanging.get(p.getUniqueId().toString().replace("-", ""));
        if (items != null)
        {
            for (int i = 0; i < 45 && i + (page * 45 - 45) < items.size(); i++)
            {
                Map.Entry<ItemStack[], String> entry = items.get(i).entrySet().iterator().next();
                int changeTime = (Integer.parseInt(entry.getValue().split(", ")[1]));
                final ItemStack[] sets = entry.getKey();
                final int slot = i + 9;
                invTasks.get(p).put(inv.getTitle(), main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
                {
                    private int current = 0;

                    @Override
                    public void run()
                    {
                        inv.setItem(slot, sets[current]);

                        current++;
                        if (current >= sets.length)
                        {
                            current = 0;
                        }
                    }
                }, 0L, (long) changeTime));
            }
        }
        p.openInventory(inv);
    }

    public void openChestplateChanging(Player p, int page)
    {
        final Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("chestplatechangingmenu"));

        if (chestplateChanging.containsKey(p.getUniqueId().toString().replace("-", "")) && chestplateChanging.get(p.getUniqueId().toString().replace("-", "")).size() > 45 * page)
        {
            ItemStack next = new ItemStack(Material.LEVER);
            ItemMeta next_meta = next.getItemMeta();
            next_meta.setDisplayName(main.getMessage("nextpage"));
            next.setItemMeta(next_meta);
            inv.setItem(7, next);
        }
        if (page > 1)
        {
            ItemStack prev = new ItemStack(Material.LEVER);
            ItemMeta prev_meta = prev.getItemMeta();
            prev_meta.setDisplayName(main.getMessage("previouspage"));
            prev.setItemMeta(prev_meta);
            inv.setItem(1, prev);
        }
        ItemStack mainMenu = new ItemStack(Material.BOOK);
        ItemMeta mainMenuMeta = mainMenu.getItemMeta();
        mainMenuMeta.setDisplayName(main.getMessage("backtomainmenu"));
        mainMenu.setItemMeta(mainMenuMeta);
        inv.setItem(0, mainMenu);

        ItemStack ipage = new ItemStack(Material.PAPER);
        ipage.setAmount(page);
        ItemMeta ipage_meta = ipage.getItemMeta();
        ipage_meta.setDisplayName(main.getMessage("currentpage").replace("%page", "" + page));
        ipage.setItemMeta(ipage_meta);
        inv.setItem(4, ipage);

        if (chestplateChanging.get(p.getUniqueId().toString().replace("-", "")).size() == 0)
        {
            ItemStack info = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName(main.getMessage("nochangingchestplates"));
            info.setItemMeta(info_meta);
            inv.setItem(9, info);
        }

        ArrayList<HashMap<ItemStack[], String>> items = chestplateChanging.get(p.getUniqueId().toString().replace("-", ""));
        if (items != null)
        {
            for (int i = 0; i < 45 && i + (page * 45 - 45) < items.size(); i++)
            {
                Map.Entry<ItemStack[], String> entry = items.get(i).entrySet().iterator().next();
                int changeTime = (Integer.parseInt(entry.getValue().split(", ")[1]));
                final ItemStack[] sets = entry.getKey();
                final int slot = i + 9;
                invTasks.get(p).put(inv.getTitle(), main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
                {
                    private int current = 0;

                    @Override
                    public void run()
                    {
                        inv.setItem(slot, sets[current]);

                        current++;
                        if (current >= sets.length)
                        {
                            current = 0;
                        }
                    }
                }, 0L, (long) changeTime));
            }
        }
        p.openInventory(inv);
    }

    public void openLeggingsChanging(Player p, int page)
    {
        final Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("leggingschangingmenu"));

        if (leggingsChanging.containsKey(p.getUniqueId().toString().replace("-", "")) && leggingsChanging.get(p.getUniqueId().toString().replace("-", "")).size() > 45 * page)
        {
            ItemStack next = new ItemStack(Material.LEVER);
            ItemMeta next_meta = next.getItemMeta();
            next_meta.setDisplayName(main.getMessage("nextpage"));
            next.setItemMeta(next_meta);
            inv.setItem(7, next);
        }
        if (page > 1)
        {
            ItemStack prev = new ItemStack(Material.LEVER);
            ItemMeta prev_meta = prev.getItemMeta();
            prev_meta.setDisplayName(main.getMessage("previouspage"));
            prev.setItemMeta(prev_meta);
            inv.setItem(1, prev);
        }
        ItemStack mainMenu = new ItemStack(Material.BOOK);
        ItemMeta mainMenuMeta = mainMenu.getItemMeta();
        mainMenuMeta.setDisplayName(main.getMessage("backtomainmenu"));
        mainMenu.setItemMeta(mainMenuMeta);
        inv.setItem(0, mainMenu);

        ItemStack ipage = new ItemStack(Material.PAPER);
        ipage.setAmount(page);
        ItemMeta ipage_meta = ipage.getItemMeta();
        ipage_meta.setDisplayName(main.getMessage("currentpage").replace("%page", "" + page));
        ipage.setItemMeta(ipage_meta);
        inv.setItem(4, ipage);

        if (leggingsChanging.get(p.getUniqueId().toString().replace("-", "")).size() == 0)
        {
            ItemStack info = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName(main.getMessage("nochangingleggings"));
            info.setItemMeta(info_meta);
            inv.setItem(9, info);
        }

        ArrayList<HashMap<ItemStack[], String>> items = leggingsChanging.get(p.getUniqueId().toString().replace("-", ""));
        if (items != null)
        {
            for (int i = 0; i < 45 && i + (page * 45 - 45) < items.size(); i++)
            {
                Map.Entry<ItemStack[], String> entry = items.get(i).entrySet().iterator().next();
                int changeTime = (Integer.parseInt(entry.getValue().split(", ")[1]));
                final ItemStack[] sets = entry.getKey();
                final int slot = i + 9;
                invTasks.get(p).put(inv.getTitle(), main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
                {
                    private int current = 0;

                    @Override
                    public void run()
                    {
                        inv.setItem(slot, sets[current]);

                        current++;
                        if (current >= sets.length)
                        {
                            current = 0;
                        }
                    }
                }, 0L, (long) changeTime));
            }
        }
        p.openInventory(inv);
    }

    public void openBootsChanging(Player p, int page)
    {
        final Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("bootschangingmenu"));

        if (bootsChanging.containsKey(p.getUniqueId().toString().replace("-", "")) && bootsChanging.get(p.getUniqueId().toString().replace("-", "")).size() > 45 * page)
        {
            ItemStack next = new ItemStack(Material.LEVER);
            ItemMeta next_meta = next.getItemMeta();
            next_meta.setDisplayName(main.getMessage("nextpage"));
            next.setItemMeta(next_meta);
            inv.setItem(7, next);
        }
        if (page > 1)
        {
            ItemStack prev = new ItemStack(Material.LEVER);
            ItemMeta prev_meta = prev.getItemMeta();
            prev_meta.setDisplayName(main.getMessage("previouspage"));
            prev.setItemMeta(prev_meta);
            inv.setItem(1, prev);
        }
        ItemStack mainMenu = new ItemStack(Material.BOOK);
        ItemMeta mainMenuMeta = mainMenu.getItemMeta();
        mainMenuMeta.setDisplayName(main.getMessage("backtomainmenu"));
        mainMenu.setItemMeta(mainMenuMeta);
        inv.setItem(0, mainMenu);

        ItemStack ipage = new ItemStack(Material.PAPER);
        ipage.setAmount(page);
        ItemMeta ipage_meta = ipage.getItemMeta();
        ipage_meta.setDisplayName(main.getMessage("currentpage").replace("%page", "" + page));
        ipage.setItemMeta(ipage_meta);
        inv.setItem(4, ipage);

        if (bootsChanging.get(p.getUniqueId().toString().replace("-", "")).size() == 0)
        {
            ItemStack info = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName(main.getMessage("nochangingboots"));
            info.setItemMeta(info_meta);
            inv.setItem(9, info);
        }

        ArrayList<HashMap<ItemStack[], String>> items = bootsChanging.get(p.getUniqueId().toString().replace("-", ""));
        if (items != null)
        {
            for (int i = 0; i < 45 && i + (page * 45 - 45) < items.size(); i++)
            {
                Map.Entry<ItemStack[], String> entry = items.get(i).entrySet().iterator().next();
                int changeTime = (Integer.parseInt(entry.getValue().split(", ")[1]));
                final ItemStack[] sets = entry.getKey();
                final int slot = i + 9;
                invTasks.get(p).put(inv.getTitle(), main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
                {
                    private int current = 0;

                    @Override
                    public void run()
                    {
                        inv.setItem(slot, sets[current]);

                        current++;
                        if (current >= sets.length)
                        {
                            current = 0;
                        }
                    }
                }, 0L, (long) changeTime));
            }
        }
        p.openInventory(inv);
    }

    public void openHatBlocks(Player p, int page)
    {
        Inventory inv = main.getServer().createInventory(p, 54, main.getMessage("hatblocksinventory"));

        if (hatBlocks.containsKey(p.getUniqueId().toString().replace("-", "")) && hatBlocks.get(p.getUniqueId().toString().replace("-", "")).size() > 45 * page)
        {
            ItemStack next = new ItemStack(Material.LEVER);
            ItemMeta next_meta = next.getItemMeta();
            next_meta.setDisplayName(main.getMessage("nextpage"));
            next.setItemMeta(next_meta);
            inv.setItem(7, next);
        }
        if (page > 1)
        {
            ItemStack prev = new ItemStack(Material.LEVER);
            ItemMeta prev_meta = prev.getItemMeta();
            prev_meta.setDisplayName(main.getMessage("previouspage"));
            prev.setItemMeta(prev_meta);
            inv.setItem(1, prev);
        }
        ItemStack mainMenu = new ItemStack(Material.BOOK);
        ItemMeta mainMenuMeta = mainMenu.getItemMeta();
        mainMenuMeta.setDisplayName(main.getMessage("backtomainmenu"));
        mainMenu.setItemMeta(mainMenuMeta);
        inv.setItem(0, mainMenu);

        ItemStack ipage = new ItemStack(Material.PAPER);
        ipage.setAmount(page);
        ItemMeta ipage_meta = ipage.getItemMeta();
        ipage_meta.setDisplayName(main.getMessage("currentpage").replace("%page", "" + page));
        ipage.setItemMeta(ipage_meta);
        inv.setItem(4, ipage);

        if (hatBlocks.get(p.getUniqueId().toString().replace("-", "")).size() == 0)
        {
            ItemStack info = new ItemStack(Material.REDSTONE_TORCH_ON);
            ItemMeta info_meta = info.getItemMeta();
            info_meta.setDisplayName(main.getMessage("nohatblocks"));
            info.setItemMeta(info_meta);
            inv.setItem(9, info);
        }

        ArrayList<ItemStack> items = hatBlocks.get(p.getUniqueId().toString().replace("-", ""));
        if (items != null)
        {
            for (int i = 0; i < 45 && i + (page * 45 - 45) < items.size(); i++)
            {
                inv.setItem(i + 9, items.get(i + (page * 45 - 45)));
            }
        }

        p.openInventory(inv);
    }

    public void cancelInventoryTasks(final Player p)
    {
        main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable()
        {
            @Override
            public void run()
            {
                if (invTasks.containsKey(p))
                {
                    for (Map.Entry<String, Integer> entry : invTasks.get(p).entrySet())
                    {
                        if (p.getOpenInventory() != null && !p.getOpenInventory().getTitle().equals(entry.getKey()))
                        {
                            main.getServer().getScheduler().cancelTask(entry.getValue());
                            invTasks.get(p).remove(p);
                        }
                    }
                }
            }
        }, 1L);
    }

    public void cancelArmorTask(Player p, int slot)
    {
        if (armorTasks.containsKey(p) && armorTasks.get(p)[slot] != -1)
        {
            main.getServer().getScheduler().cancelTask(armorTasks.get(p)[slot]);
            armorTasks.get(p)[slot] = -1;
        }
        if (slot == 0)
        {
            main.getLoggedOutHelmet().remove(p.getUniqueId().toString().replace("-", ""));
        } else if (slot == 1)
        {
            main.getLoggedOutChestplate().remove(p.getUniqueId().toString().replace("-", ""));
        } else if (slot == 2)
        {
            main.getLoggedOutLeggings().remove(p.getUniqueId().toString().replace("-", ""));
        } else if (slot == 4)
        {
            main.getLoggedOutBoots().remove(p.getUniqueId().toString().replace("-", ""));
        }
    }

    public void savePlayerArmor(Player p)
    {
        if (p.getInventory().getHelmet() != null && p.getInventory().getHelmet().getItemMeta() != null && p.getInventory().getHelmet().getItemMeta().getDisplayName() != null)
        {
            cancelArmorTask(p, 0);
            main.getLoggedOutHelmet().put(p.getUniqueId().toString().replace("-", ""), ChatColor.stripColor(p.getInventory().getHelmet().getItemMeta().getDisplayName()));
        }
        if (p.getInventory().getChestplate() != null && p.getInventory().getChestplate().getItemMeta() != null && p.getInventory().getChestplate().getItemMeta().getDisplayName() != null)
        {
            cancelArmorTask(p, 1);
            main.getLoggedOutChestplate().put(p.getUniqueId().toString().replace("-", ""), ChatColor.stripColor(p.getInventory().getChestplate().getItemMeta().getDisplayName()));
        }
        if (p.getInventory().getLeggings() != null && p.getInventory().getLeggings().getItemMeta() != null && p.getInventory().getLeggings().getItemMeta().getDisplayName() != null)
        {
            cancelArmorTask(p, 2);
            main.getLoggedOutLeggings().put(p.getUniqueId().toString().replace("-", ""), ChatColor.stripColor(p.getInventory().getLeggings().getItemMeta().getDisplayName()));
        }
        if (p.getInventory().getBoots() != null && p.getInventory().getBoots().getItemMeta() != null && p.getInventory().getBoots().getItemMeta().getDisplayName() != null)
        {
            cancelArmorTask(p, 3);
            main.getLoggedOutBoots().put(p.getUniqueId().toString().replace("-", ""), ChatColor.stripColor(p.getInventory().getBoots().getItemMeta().getDisplayName()));
        }
    }

    public void loadPlayerArmor(final Player p)
    {
        if (main.getLoggedOutHelmet().containsKey(p.getUniqueId().toString().replace("-", "")) && main.getLoggedOutHelmet().get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            String name = main.getLoggedOutHelmet().get(p.getUniqueId().toString().replace("-", ""));
            ArrayList<HashMap<ItemStack[], String>> sets = helmetChanging.get(p.getUniqueId().toString().replace("-", ""));
            int changeTime = -1;
            ItemStack[] items = null;
            for (HashMap<ItemStack[], String> en : sets)
            {
                Map.Entry<ItemStack[], String> entry = en.entrySet().iterator().next();
                if (entry.getValue().split(", ")[0].equals(name))
                {
                    changeTime = Integer.parseInt(entry.getValue().split(", ")[1]);
                    items = entry.getKey();
                    break;
                }
            }
            final ItemStack[] f_items = items;
            if (changeTime != -1 && items != null)
            {
                cancelArmorTask(p, 0);

                armorTasks.get(p)[0] = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
                {
                    ItemStack[] parts = f_items;
                    int current = 0;

                    @Override
                    public void run()
                    {
                        p.getInventory().setHelmet(parts[current]);

                        current++;
                        if (current >= parts.length)
                        {
                            current = 0;
                        }
                    }
                }, 0L, (long) changeTime);
            }
        }
        if (main.getLoggedOutChestplate().containsKey(p.getUniqueId().toString().replace("-", "")) && main.getLoggedOutChestplate().get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            String name = main.getLoggedOutChestplate().get(p.getUniqueId().toString().replace("-", ""));
            ArrayList<HashMap<ItemStack[], String>> sets = chestplateChanging.get(p.getUniqueId().toString().replace("-", ""));
            int changeTime = -1;
            ItemStack[] items = null;
            for (HashMap<ItemStack[], String> en : sets)
            {
                Map.Entry<ItemStack[], String> entry = en.entrySet().iterator().next();
                if (entry.getValue().split(", ")[0].equals(name))
                {
                    changeTime = Integer.parseInt(entry.getValue().split(", ")[1]);
                    items = entry.getKey();
                    break;
                }
            }
            final ItemStack[] f_items = items;
            if (changeTime != -1 && items != null)
            {
                cancelArmorTask(p, 1);

                armorTasks.get(p)[1] = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
                {
                    ItemStack[] parts = f_items;
                    int current = 0;

                    @Override
                    public void run()
                    {
                        p.getInventory().setChestplate(parts[current]);

                        current++;
                        if (current >= parts.length)
                        {
                            current = 0;
                        }
                    }
                }, 0L, (long) changeTime);
            }
        }
        if (main.getLoggedOutLeggings().containsKey(p.getUniqueId().toString().replace("-", "")) && main.getLoggedOutLeggings().get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            String name = main.getLoggedOutLeggings().get(p.getUniqueId().toString().replace("-", ""));
            ArrayList<HashMap<ItemStack[], String>> sets = leggingsChanging.get(p.getUniqueId().toString().replace("-", ""));
            int changeTime = -1;
            ItemStack[] items = null;
            for (HashMap<ItemStack[], String> en : sets)
            {
                Map.Entry<ItemStack[], String> entry = en.entrySet().iterator().next();
                if (entry.getValue().split(", ")[0].equals(name))
                {
                    changeTime = Integer.parseInt(entry.getValue().split(", ")[1]);
                    items = entry.getKey();
                    break;
                }
            }
            final ItemStack[] f_items = items;
            if (changeTime != -1 && items != null)
            {
                cancelArmorTask(p, 2);

                armorTasks.get(p)[2] = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
                {
                    ItemStack[] parts = f_items;
                    int current = 0;

                    @Override
                    public void run()
                    {
                        p.getInventory().setLeggings(parts[current]);

                        current++;
                        if (current >= parts.length)
                        {
                            current = 0;
                        }
                    }
                }, 0L, (long) changeTime);
            }
        }
        if (main.getLoggedOutBoots().containsKey(p.getUniqueId().toString().replace("-", "")) && main.getLoggedOutBoots().get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            String name = main.getLoggedOutBoots().get(p.getUniqueId().toString().replace("-", ""));
            ArrayList<HashMap<ItemStack[], String>> sets = bootsChanging.get(p.getUniqueId().toString().replace("-", ""));
            int changeTime = -1;
            ItemStack[] items = null;
            for (HashMap<ItemStack[], String> en : sets)
            {
                Map.Entry<ItemStack[], String> entry = en.entrySet().iterator().next();
                if (entry.getValue().split(", ")[0].equals(name))
                {
                    changeTime = Integer.parseInt(entry.getValue().split(", ")[1]);
                    items = entry.getKey();
                    break;
                }
            }
            final ItemStack[] f_items = items;
            if (changeTime != -1 && items != null)
            {
                cancelArmorTask(p, 3);

                armorTasks.get(p)[3] = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
                {
                    ItemStack[] parts = f_items;
                    int current = 0;

                    @Override
                    public void run()
                    {
                        p.getInventory().setBoots(parts[current]);

                        current++;
                        if (current >= parts.length)
                        {
                            current = 0;
                        }
                    }
                }, 0L, (long) changeTime);
            }
        }
    }

    public void updateWardrobe(Player p)
    {
        if (!invTasks.containsKey(p))
        {
            invTasks.put(p, new HashMap<String, Integer>());
        }
        if (!armorTasks.containsKey(p))
        {
            armorTasks.put(p, new Integer[]
            {
                -1, -1, -1, -1
            });
        }

        if (hatBlocks.get(p.getUniqueId().toString().replace("", "")) != null)
        {
            hatBlocks.get(p.getUniqueId().toString().replace("-", "")).clear();
        } else
        {
            hatBlocks.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<ItemStack>());
        }
        if (helmetStatic.get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            helmetStatic.get(p.getUniqueId().toString().replace("-", "")).clear();
        } else
        {
            helmetStatic.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<ItemStack>());
        }
        if (chestplateStatic.get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            chestplateStatic.get(p.getUniqueId().toString().replace("-", "")).clear();
        } else
        {
            chestplateStatic.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<ItemStack>());
        }
        if (leggingsStatic.get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            leggingsStatic.get(p.getUniqueId().toString().replace("-", "")).clear();
        } else
        {
            leggingsStatic.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<ItemStack>());
        }
        if (bootsStatic.get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            bootsStatic.get(p.getUniqueId().toString().replace("-", "")).clear();
        } else
        {
            bootsStatic.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<ItemStack>());
        }
        if (helmetChanging.get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            helmetChanging.get(p.getUniqueId().toString().replace("-", "")).clear();
        } else
        {
            helmetChanging.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<HashMap<ItemStack[], String>>());
        }
        if (chestplateChanging.get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            chestplateChanging.get(p.getUniqueId().toString().replace("-", "")).clear();
        } else
        {
            chestplateChanging.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<HashMap<ItemStack[], String>>());
        }
        if (leggingsChanging.get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            leggingsChanging.get(p.getUniqueId().toString().replace("-", "")).clear();
        } else
        {
            leggingsChanging.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<HashMap<ItemStack[], String>>());
        }
        if (bootsChanging.get(p.getUniqueId().toString().replace("-", "")) != null)
        {
            bootsChanging.get(p.getUniqueId().toString().replace("-", "")).clear();
        } else
        {
            bootsChanging.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<HashMap<ItemStack[], String>>());
        }

        if (!main.isTestMode())
        {
            try
            {
                ResultSet rs = main.getSqlConnection().createStatement().executeQuery("SELECT * FROM dressup WHERE uuid = '" + p.getUniqueId().toString().replace("-", "") + "'");
                while (rs.next())
                {
                    //helmet static
                    String helmets_static = rs.getString("helmets_static");
                    if (!helmets_static.equals(""))
                    {
                        String[] helmet_split = helmets_static.split(";");
                        for (String s : helmet_split)
                        {
                            String[] color_name = s.split(",");
                            if (s.length() == 2)
                            {
                                ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
                                LeatherArmorMeta meta = (LeatherArmorMeta) helmet.getItemMeta();
                                if (!color_name[1].equals(""))
                                {
                                    meta.setDisplayName(ChatColor.RESET + color_name[1]);
                                }
                                Color c = Color.fromRGB(Integer.parseInt(color_name[0].split("_")[0]), Integer.parseInt(color_name[0].split("_")[1]), Integer.parseInt(color_name[0].split("_")[2]));
                                meta.setColor(c);
                                helmet.setItemMeta(meta);
                                helmetStatic.get(p.getUniqueId().toString().replace("-", "")).add(helmet);
                            } else
                            {
                                main.getLogger().info(main.getMessage("errorstatichelmet").replace("%id", s).replace("%player", p.getName()));
                            }
                        }
                    }

                    //chestplate static
                    String chestplates_static = rs.getString("chestplates_static");
                    if (!chestplates_static.equals(""))
                    {
                        String[] chest_split = chestplates_static.split(";");
                        for (String s : chest_split)
                        {
                            String[] color_name = s.split(",");
                            if (s.length() == 2)
                            {
                                ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                                LeatherArmorMeta meta = (LeatherArmorMeta) chestplate.getItemMeta();
                                if (!color_name[1].equals(""))
                                {
                                    meta.setDisplayName(ChatColor.RESET + color_name[1]);
                                }
                                Color c = Color.fromRGB(Integer.parseInt(color_name[0].split("_")[0]), Integer.parseInt(color_name[0].split("_")[1]), Integer.parseInt(color_name[0].split("_")[2]));
                                meta.setColor(c);
                                chestplate.setItemMeta(meta);
                                chestplateStatic.get(p.getUniqueId().toString().replace("-", "")).add(chestplate);
                            } else
                            {
                                main.getLogger().info(main.getMessage("errorstaticchestplate").replace("%id", s).replace("%player", p.getName()));
                            }
                        }
                    }

                    //leggings static
                    String leggings_static = rs.getString("leggings_static");
                    if (!leggings_static.equals(""))
                    {
                        String[] leggings_split = leggings_static.split(";");
                        for (String s : leggings_split)
                        {
                            String[] color_name = s.split(",");
                            if (s.length() == 2)
                            {
                                ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                                LeatherArmorMeta meta = (LeatherArmorMeta) leggings.getItemMeta();
                                if (!color_name[1].equals(""))
                                {
                                    meta.setDisplayName(ChatColor.RESET + color_name[1]);
                                }
                                Color c = Color.fromRGB(Integer.parseInt(color_name[0].split("_")[0]), Integer.parseInt(color_name[0].split("_")[1]), Integer.parseInt(color_name[0].split("_")[2]));
                                meta.setColor(c);
                                leggings.setItemMeta(meta);
                                leggingsStatic.get(p.getUniqueId().toString().replace("-", "")).add(leggings);
                            } else
                            {
                                main.getLogger().info(main.getMessage("errorstaticleggings").replace("%id", s).replace("%player", p.getName()));
                            }
                        }
                    }

                    //boots static
                    String boots_static = rs.getString("boots_static");
                    if (!boots_static.equals(""))
                    {
                        String[] boots_split = boots_static.split(";");
                        for (String s : boots_split)
                        {
                            String[] color_name = s.split(",");
                            if (s.length() == 2)
                            {
                                ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                                LeatherArmorMeta meta = (LeatherArmorMeta) boots.getItemMeta();
                                if (!color_name[1].equals(""))
                                {
                                    meta.setDisplayName(ChatColor.RESET + color_name[1]);
                                }
                                Color c = Color.fromRGB(Integer.parseInt(color_name[0].split("_")[0]), Integer.parseInt(color_name[0].split("_")[1]), Integer.parseInt(color_name[0].split("_")[2]));
                                meta.setColor(c);
                                boots.setItemMeta(meta);
                                bootsStatic.get(p.getUniqueId().toString().replace("-", "")).add(boots);
                            } else
                            {
                                main.getLogger().info(main.getMessage("errorstaticboots").replace("%id", s).replace("%player", p.getName()));
                            }
                        }
                    }

                    //helmet changing
                    String helmets_changing = rs.getString("helmets_changing");
                    if (!helmets_changing.equals(""))
                    {
                        String[] helmets_split = helmets_changing.split(";");
                        for (String s : helmets_split)
                        {
                            String[] items_changetime_color = s.split(",");
                            if (items_changetime_color.length == 3)
                            {
                                String items = items_changetime_color[0];
                                String changetime = items_changetime_color[1];
                                String name = items_changetime_color[2];
                                ItemStack[] items_final = new ItemStack[items.split("~").length];
                                for (int i = 0; i < items.split("~").length; i++)
                                {
                                    String color = items.split("~")[i];
                                    ItemStack is = new ItemStack(Material.LEATHER_HELMET);
                                    LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
                                    im.setDisplayName(ChatColor.RESET + name);
                                    Color c = Color.fromRGB(Integer.parseInt(color.split("_")[0]), Integer.parseInt(color.split("_")[1]), Integer.parseInt(color.split("_")[2]));
                                    im.setColor(c);
                                    is.setItemMeta(im);
                                    items_final[i] = is;
                                }
                                HashMap<ItemStack[], String> temp = new HashMap<ItemStack[], String>();
                                temp.put(items_final, name + ", " + changetime);
                                helmetChanging.get(p.getUniqueId().toString().replace("-", "")).add(temp);
                            } else
                            {
                                main.getLogger().info(main.getMessage("errorchanginghelmet").replace("%id", s).replace("%player", p.getName()));
                            }
                        }
                    }

                    //chestplate changing
                    String chestplates_changing = rs.getString("chestplates_changing");
                    if (!chestplates_changing.equals(""))
                    {
                        String[] chestplates_split = chestplates_changing.split(";");
                        for (String s : chestplates_split)
                        {
                            String[] items_changetime_color = s.split(",");
                            if (items_changetime_color.length == 3)
                            {
                                String items = items_changetime_color[0];
                                String changetime = items_changetime_color[1];
                                String name = items_changetime_color[2];
                                ItemStack[] items_final = new ItemStack[items.split("~").length];
                                for (int i = 0; i < items.split("~").length; i++)
                                {
                                    String color = items.split("~")[i];
                                    ItemStack is = new ItemStack(Material.LEATHER_CHESTPLATE);
                                    LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
                                    im.setDisplayName(ChatColor.RESET + name);
                                    Color c = Color.fromRGB(Integer.parseInt(color.split("_")[0]), Integer.parseInt(color.split("_")[1]), Integer.parseInt(color.split("_")[2]));
                                    im.setColor(c);
                                    is.setItemMeta(im);
                                    items_final[i] = is;
                                }
                                HashMap<ItemStack[], String> temp = new HashMap<ItemStack[], String>();
                                temp.put(items_final, name + ", " + changetime);
                                chestplateChanging.get(p.getUniqueId().toString().replace("-", "")).add(temp);
                            } else
                            {
                                main.getLogger().info(main.getMessage("errorchangingchestplate").replace("%id", s).replace("%player", p.getName()));
                            }
                        }
                    }

                    //leggings changing
                    String leggings_changing = rs.getString("leggings_changing");
                    if (!leggings_changing.equals(""))
                    {
                        String[] leggings_split = leggings_changing.split(";");
                        for (String s : leggings_split)
                        {
                            String[] items_changetime_color = s.split(",");
                            if (items_changetime_color.length == 3)
                            {
                                String items = items_changetime_color[0];
                                String changetime = items_changetime_color[1];
                                String name = items_changetime_color[2];
                                ItemStack[] items_final = new ItemStack[items.split("~").length];
                                for (int i = 0; i < items.split("~").length; i++)
                                {
                                    String color = items.split("~")[i];
                                    ItemStack is = new ItemStack(Material.LEATHER_LEGGINGS);
                                    LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
                                    im.setDisplayName(ChatColor.RESET + name);
                                    Color c = Color.fromRGB(Integer.parseInt(color.split("_")[0]), Integer.parseInt(color.split("_")[1]), Integer.parseInt(color.split("_")[2]));
                                    im.setColor(c);
                                    is.setItemMeta(im);
                                    items_final[i] = is;
                                }
                                HashMap<ItemStack[], String> temp = new HashMap<ItemStack[], String>();
                                temp.put(items_final, name + ", " + changetime);
                                leggingsChanging.get(p.getUniqueId().toString().replace("-", "")).add(temp);
                            } else
                            {
                                main.getLogger().info(main.getMessage("errorchangingleggings").replace("%id", s).replace("%player", p.getName()));
                            }
                        }
                    }

                    //boots changing
                    String boots_changing = rs.getString("boots_changing");
                    if (!boots_changing.equals(""))
                    {
                        String[] boots_split = boots_changing.split(";");
                        for (String s : boots_split)
                        {
                            String[] items_changetime_color = s.split(",");
                            if (items_changetime_color.length == 3)
                            {
                                String items = items_changetime_color[0];
                                String changetime = items_changetime_color[1];
                                String name = items_changetime_color[2];
                                ItemStack[] items_final = new ItemStack[items.split("~").length];
                                for (int i = 0; i < items.split("~").length; i++)
                                {
                                    String color = items.split("~")[i];
                                    ItemStack is = new ItemStack(Material.LEATHER_BOOTS);
                                    LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
                                    im.setDisplayName(ChatColor.RESET + name);
                                    Color c = Color.fromRGB(Integer.parseInt(color.split("_")[0]), Integer.parseInt(color.split("_")[1]), Integer.parseInt(color.split("_")[2]));
                                    im.setColor(c);
                                    is.setItemMeta(im);
                                    items_final[i] = is;
                                }
                                HashMap<ItemStack[], String> temp = new HashMap<ItemStack[], String>();
                                temp.put(items_final, name + ", " + changetime);
                                bootsChanging.get(p.getUniqueId().toString().replace("-", "")).add(temp);
                            } else
                            {
                                main.getLogger().info(main.getMessage("errorchangingboots").replace("%id", s).replace("%player", p.getName()));
                            }
                        }
                    }

                    //hat blocks
                    if (hatBlocks.get(p.getUniqueId().toString().replace("-", "")) != null)
                    {
                        hatBlocks.get(p.getUniqueId().toString().replace("-", "")).clear();
                    } else
                    {
                        hatBlocks.put(p.getUniqueId().toString().replace("-", ""), new ArrayList<ItemStack>());
                    }
                    String hat_blocks = rs.getString("hat_blocks");
                    if (!hat_blocks.equals(""))
                    {
                        String[] hat_split = hat_blocks.split(";");
                        for (String s : hat_split)
                        {
                            String[] split = s.split(":");
                            ItemStack hat;
                            if (split.length == 2)
                            {
                                hat = new ItemStack(Material.getMaterial(Integer.parseInt(split[0])), 1, (short) Integer.parseInt(split[1]));

                            } else
                            {
                                hat = new ItemStack(Material.getMaterial(Integer.parseInt(s)));
                            }
                            hatBlocks.get(p.getUniqueId().toString().replace("-", "")).add(hat);
                        }
                    }
                }
            } catch (SQLException ex)
            {
                Logger.getLogger(DressUp.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else
        {
            Random r = new Random();
            for (int i = 0; i < 45; i++)
            {
                //helmet static
                String name_h = main.getMessage("statichelmetexample").replace("%id", "" + (i + 1));
                ItemStack helmet = new ItemStack(Material.LEATHER_HELMET);
                LeatherArmorMeta meta_h = (LeatherArmorMeta) helmet.getItemMeta();
                meta_h.setDisplayName(ChatColor.RESET + name_h);
                Color c_h = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                meta_h.setColor(c_h);
                helmet.setItemMeta(meta_h);
                helmetStatic.get(p.getUniqueId().toString().replace("-", "")).add(helmet);

                //chestplate static
                String name_c = main.getMessage("staticchestplateexample").replace("%id", "" + (i + 1));
                ItemStack chestplate = new ItemStack(Material.LEATHER_CHESTPLATE);
                LeatherArmorMeta meta_c = (LeatherArmorMeta) chestplate.getItemMeta();
                meta_c.setDisplayName(ChatColor.RESET + name_c);
                Color c_c = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                meta_c.setColor(c_c);
                chestplate.setItemMeta(meta_c);
                chestplateStatic.get(p.getUniqueId().toString().replace("-", "")).add(chestplate);

                //leggings static
                String name_l = main.getMessage("staticleggingsexample").replace("%id", "" + (i + 1));
                ItemStack leggings = new ItemStack(Material.LEATHER_LEGGINGS);
                LeatherArmorMeta meta_l = (LeatherArmorMeta) leggings.getItemMeta();
                meta_l.setDisplayName(ChatColor.RESET + name_l);
                Color c_l = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                meta_l.setColor(c_l);
                leggings.setItemMeta(meta_l);
                leggingsStatic.get(p.getUniqueId().toString().replace("-", "")).add(leggings);

                //boots static
                String name_b = main.getMessage("staticbootsexample").replace("%id", "" + (i + 1));
                ItemStack boots = new ItemStack(Material.LEATHER_BOOTS);
                LeatherArmorMeta meta_b = (LeatherArmorMeta) boots.getItemMeta();
                meta_b.setDisplayName(ChatColor.RESET + name_b);
                Color c_b = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                meta_b.setColor(c_b);
                boots.setItemMeta(meta_b);
                bootsStatic.get(p.getUniqueId().toString().replace("-", "")).add(boots);

                //helmet changing
                String name_h_ = main.getMessage("changinghelmetexample").replace("%id", "" + (i + 1));
                int amount_h = 3 + r.nextInt(3);
                ItemStack[] items_final_h = new ItemStack[amount_h];
                for (int j = 0; j < amount_h; j++)
                {
                    ItemStack is = new ItemStack(Material.LEATHER_HELMET);
                    LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
                    im.setDisplayName(ChatColor.RESET + name_h_);
                    Color c = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                    im.setColor(c);
                    is.setItemMeta(im);
                    items_final_h[j] = is;
                }
                HashMap<ItemStack[], String> temp_h = new HashMap<ItemStack[], String>();
                temp_h.put(items_final_h, name_h_ + ", " + (r.nextInt(20) + 5));
                helmetChanging.get(p.getUniqueId().toString().replace("-", "")).add(temp_h);

                //chestplate changing
                String name_c_ = main.getMessage("changingchestplateexample").replace("%id", "" + (i + 1));
                int amount_c = 3 + r.nextInt(3);
                ItemStack[] items_final_c = new ItemStack[amount_c];
                for (int j = 0; j < amount_c; j++)
                {
                    ItemStack is = new ItemStack(Material.LEATHER_CHESTPLATE);
                    LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
                    im.setDisplayName(ChatColor.RESET + name_c_);
                    Color c = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                    im.setColor(c);
                    is.setItemMeta(im);
                    items_final_c[j] = is;
                }
                HashMap<ItemStack[], String> temp_c = new HashMap<ItemStack[], String>();
                temp_c.put(items_final_c, name_c_ + ", " + (r.nextInt(20) + 5));
                chestplateChanging.get(p.getUniqueId().toString().replace("-", "")).add(temp_c);

                //leggings changing
                String name_l_ = main.getMessage("changingleggingsexample").replace("%id", "" + (i + 1));
                int amount_l = 3 + r.nextInt(3);
                ItemStack[] items_final_l = new ItemStack[amount_l];
                for (int j = 0; j < amount_l; j++)
                {
                    ItemStack is = new ItemStack(Material.LEATHER_LEGGINGS);
                    LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
                    im.setDisplayName(ChatColor.RESET + name_l_);
                    Color c = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                    im.setColor(c);
                    is.setItemMeta(im);
                    items_final_l[j] = is;
                }
                HashMap<ItemStack[], String> temp_l = new HashMap<ItemStack[], String>();
                temp_l.put(items_final_l, name_l_ + ", " + (r.nextInt(20) + 5));
                leggingsChanging.get(p.getUniqueId().toString().replace("-", "")).add(temp_l);

                //boots changing
                String name_b_ = main.getMessage("changingbootsexample").replace("%id", "" + (i + 1));
                int amount_b = 3 + r.nextInt(3);
                ItemStack[] items_final_b = new ItemStack[amount_b];
                for (int j = 0; j < amount_b; j++)
                {
                    ItemStack is = new ItemStack(Material.LEATHER_BOOTS);
                    LeatherArmorMeta im = (LeatherArmorMeta) is.getItemMeta();
                    im.setDisplayName(ChatColor.RESET + name_b_);
                    Color c = Color.fromRGB(r.nextInt(256), r.nextInt(256), r.nextInt(256));
                    im.setColor(c);
                    is.setItemMeta(im);
                    items_final_b[j] = is;
                }
                HashMap<ItemStack[], String> temp_b = new HashMap<ItemStack[], String>();
                temp_b.put(items_final_b, name_b_ + ", " + (r.nextInt(20) + 5));
                bootsChanging.get(p.getUniqueId().toString().replace("-", "")).add(temp_b);
            }
        }
    }

    public HashMap<String, ArrayList<HashMap<ItemStack[], String>>> getHelmetChanging()
    {
        return helmetChanging;
    }

    public HashMap<String, ArrayList<HashMap<ItemStack[], String>>> getChestplateChanging()
    {
        return chestplateChanging;
    }

    public HashMap<String, ArrayList<HashMap<ItemStack[], String>>> getLeggingsChanging()
    {
        return leggingsChanging;
    }

    public HashMap<String, ArrayList<HashMap<ItemStack[], String>>> getBootsChanging()
    {
        return bootsChanging;
    }

    public HashMap<Player, Integer[]> getArmorTasks()
    {
        return armorTasks;
    }

}
