package de.sabbertran.dressup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class Events implements Listener
{

    private DressUp main;

    public Events(DressUp main)
    {
        this.main = main;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent ev)
    {
        Player p = ev.getPlayer();
        main.getWardrobe().updateWardrobe(p);
        main.getWardrobe().loadPlayerArmor(p);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent ev)
    {
        Player p = ev.getPlayer();
        main.getWardrobe().savePlayerArmor(p);
    }

    @EventHandler
    public void onInventoryClose(InventoryCloseEvent ev)
    {
        if (ev.getInventory().getName().contains("Wardrobe"))
        {
            main.getWardrobe().cancelInventoryTasks((Player) ev.getPlayer());
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent ev)
    {
        final Player p = (Player) ev.getWhoClicked();

        if (ev.getInventory().getName().equals(main.getMessage("menuname")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("hatblocksitem")))
                {
                    main.getWardrobe().openHatBlocks(p, 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("helmetstatic")))
                {
                    main.getWardrobe().openHelmetStatic(p, 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("helmetchanging")))
                {
                    main.getWardrobe().openHelmetChanging(p, 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("chestplatestatic")))
                {
                    main.getWardrobe().openChestplateStatic(p, 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("chestplatechanging")))
                {
                    main.getWardrobe().openChestplateChanging(p, 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("leggingsstatic")))
                {
                    main.getWardrobe().openLeggingsStatic(p, 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("leggingschanging")))
                {
                    main.getWardrobe().openLeggingsChanging(p, 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("bootsstatic")))
                {
                    main.getWardrobe().openBootsStatic(p, 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("bootschanging")))
                {
                    main.getWardrobe().openBootsChanging(p, 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("removehelmet")))
                {
                    main.getWardrobe().cancelArmorTask(p, 0);
                    main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            p.getInventory().setHelmet(null);
                        }
                    }, 1L);
                    p.closeInventory();
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("removechestplate")))
                {
                    main.getWardrobe().cancelArmorTask(p, 1);
                    main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            p.getInventory().setChestplate(null);
                        }
                    }, 1L);
                    p.closeInventory();
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("removeleggings")))
                {
                    main.getWardrobe().cancelArmorTask(p, 2);
                    main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            p.getInventory().setLeggings(null);
                        }
                    }, 1L);
                    p.closeInventory();
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("removeboots")))
                {
                    main.getWardrobe().cancelArmorTask(p, 3);
                    main.getServer().getScheduler().scheduleSyncDelayedTask(main, new Runnable()
                    {
                        @Override
                        public void run()
                        {
                            p.getInventory().setBoots(null);
                        }
                    }, 1L);
                    p.closeInventory();
                }
            }
            ev.setCancelled(true);
        } else if (ev.getInventory().getName().equals(main.getMessage("hatblocksinventory")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("backtomainmenu")))
                {
                    main.getWardrobe().openMainMenu(p);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("nextpage")))
                {
                    main.getWardrobe().openHatBlocks(p, ev.getInventory().getItem(4).getAmount() + 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("previouspage")))
                {
                    main.getWardrobe().openHatBlocks(p, ev.getInventory().getItem(4).getAmount() - 1);
                }
            } else if (ev.getCurrentItem() != null)
            {
                main.getWardrobe().cancelArmorTask(p, 0);
                p.getInventory().setHelmet(ev.getCurrentItem());
                main.getWardrobe().openMainMenu(p);
            }
            ev.setCancelled(true);
        } else if (ev.getInventory().getName().equals(main.getMessage("helmetstaticmenu")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("backtomainmenu")))
                {
                    main.getWardrobe().openMainMenu(p);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("nextpage")))
                {
                    main.getWardrobe().openHelmetStatic(p, ev.getInventory().getItem(4).getAmount() + 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("previouspage")))
                {
                    main.getWardrobe().openHelmetStatic(p, ev.getInventory().getItem(4).getAmount() - 1);
                }
            } else if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() == Material.LEATHER_HELMET)
            {
                p.getInventory().setHelmet(ev.getCurrentItem());
                main.getWardrobe().openMainMenu(p);
            }
            ev.setCancelled(true);
        } else if (ev.getInventory().getName().equals(main.getMessage("helmetchangingmenu")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("backtomainmenu")))
                {
                    main.getWardrobe().openMainMenu(p);
                } else if (ev.getCurrentItem().getType() == Material.LEVER && ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("nextpage")))
                {
                    main.getWardrobe().openHelmetChanging(p, ev.getInventory().getItem(4).getAmount() + 1);
                } else if (ev.getCurrentItem().getType() == Material.LEVER && ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("previouspage")))
                {
                    main.getWardrobe().openHelmetChanging(p, ev.getInventory().getItem(4).getAmount() - 1);
                } else
                {
                    String name = ev.getCurrentItem().getItemMeta().getDisplayName();
                    ArrayList<HashMap<ItemStack[], String>> sets = main.getWardrobe().getHelmetChanging().get(p.getUniqueId().toString().replace("-", ""));
                    int changeTime = -1;
                    ItemStack[] items = null;
                    for (HashMap<ItemStack[], String> en : sets)
                    {
                        Map.Entry<ItemStack[], String> entry = en.entrySet().iterator().next();
                        if (entry.getValue().split(", ")[0].equals(ChatColor.stripColor(name)))
                        {
                            changeTime = Integer.parseInt(entry.getValue().split(", ")[1]);
                            items = entry.getKey();
                            break;
                        }
                    }
                    final ItemStack[] f_items = items;
                    if (changeTime != -1 && items != null)
                    {
                        main.getWardrobe().cancelArmorTask(p, 0);

                        main.getWardrobe().getArmorTasks().get(p)[0] = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
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
                        }, 0L, changeTime);
                        ev.setCancelled(true);
                        main.getWardrobe().openMainMenu(p);
                    }
                }
            }
            ev.setCancelled(true);
        } else if (ev.getInventory().getName().equals(main.getMessage("chestplatestaticmenu")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("backtomainmenu")))
                {
                    main.getWardrobe().openMainMenu(p);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("nextpage")))
                {
                    main.getWardrobe().openChestplateStatic(p, ev.getInventory().getItem(4).getAmount() + 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("previouspage")))
                {
                    main.getWardrobe().openChestplateStatic(p, ev.getInventory().getItem(4).getAmount() - 1);
                }
            } else if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE)
            {
                p.getInventory().setChestplate(ev.getCurrentItem());
                main.getWardrobe().openMainMenu(p);
            }
            ev.setCancelled(true);

        } else if (ev.getInventory().getName().equals(main.getMessage("chestplatechangingmenu")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("backtomainmenu")))
                {
                    main.getWardrobe().openMainMenu(p);
                } else if (ev.getCurrentItem().getType() == Material.LEVER && ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("nextpage")))
                {
                    main.getWardrobe().openChestplateChanging(p, ev.getInventory().getItem(4).getAmount() + 1);
                } else if (ev.getCurrentItem().getType() == Material.LEVER && ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("previouspage")))
                {
                    main.getWardrobe().openChestplateChanging(p, ev.getInventory().getItem(4).getAmount() - 1);
                } else
                {
                    String name = ev.getCurrentItem().getItemMeta().getDisplayName();
                    ArrayList<HashMap<ItemStack[], String>> sets = main.getWardrobe().getChestplateChanging().get(p.getUniqueId().toString().replace("-", ""));
                    int changeTime = -1;
                    ItemStack[] items = null;
                    for (HashMap<ItemStack[], String> en : sets)
                    {
                        Map.Entry<ItemStack[], String> entry = en.entrySet().iterator().next();
                        if (entry.getValue().split(", ")[0].equals(ChatColor.stripColor(name)))
                        {
                            changeTime = Integer.parseInt(entry.getValue().split(", ")[1]);
                            items = entry.getKey();
                            break;
                        }
                    }
                    final ItemStack[] f_items = items;
                    if (changeTime != -1 && items != null)
                    {
                        main.getWardrobe().cancelArmorTask(p, 1);

                        main.getWardrobe().getArmorTasks().get(p)[1] = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
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
                        }, 0L, changeTime);
                        ev.setCancelled(true);
                        main.getWardrobe().openMainMenu(p);
                    }
                }
            }
            ev.setCancelled(true);
        } else if (ev.getInventory().getName().equals(main.getMessage("leggingsstaticmenu")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("backtomainmenu")))
                {
                    main.getWardrobe().openMainMenu(p);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("nextpage")))
                {
                    main.getWardrobe().openLeggingsStatic(p, ev.getInventory().getItem(4).getAmount() + 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("previouspage")))
                {
                    main.getWardrobe().openLeggingsStatic(p, ev.getInventory().getItem(4).getAmount() - 1);
                }
            } else if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() == Material.LEATHER_LEGGINGS)
            {
                p.getInventory().setLeggings(ev.getCurrentItem());
                main.getWardrobe().openMainMenu(p);
            }
            ev.setCancelled(true);
        } else if (ev.getInventory().getName().equals(main.getMessage("leggingschangingmenu")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("backtomainmenu")))
                {
                    main.getWardrobe().openMainMenu(p);
                } else if (ev.getCurrentItem().getType() == Material.LEVER && ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("nextpage")))
                {
                    main.getWardrobe().openLeggingsChanging(p, ev.getInventory().getItem(4).getAmount() + 1);
                } else if (ev.getCurrentItem().getType() == Material.LEVER && ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("previouspage")))
                {
                    main.getWardrobe().openLeggingsChanging(p, ev.getInventory().getItem(4).getAmount() - 1);
                } else
                {
                    String name = ev.getCurrentItem().getItemMeta().getDisplayName();
                    ArrayList<HashMap<ItemStack[], String>> sets = main.getWardrobe().getLeggingsChanging().get(p.getUniqueId().toString().replace("-", ""));
                    int changeTime = -1;
                    ItemStack[] items = null;
                    for (HashMap<ItemStack[], String> en : sets)
                    {
                        Map.Entry<ItemStack[], String> entry = en.entrySet().iterator().next();
                        if (entry.getValue().split(", ")[0].equals(ChatColor.stripColor(name)))
                        {
                            changeTime = Integer.parseInt(entry.getValue().split(", ")[1]);
                            items = entry.getKey();
                            break;
                        }
                    }
                    final ItemStack[] f_items = items;
                    if (changeTime != -1 && items != null)
                    {
                        main.getWardrobe().cancelArmorTask(p, 2);

                        main.getWardrobe().getArmorTasks().get(p)[2] = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
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
                        }, 0L, changeTime);
                        ev.setCancelled(true);
                        main.getWardrobe().openMainMenu(p);
                    }
                }
            }
            ev.setCancelled(true);
        } else if (ev.getInventory().getName().equals(main.getMessage("bootsstaticmenu")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("backtomainmenu")))
                {
                    main.getWardrobe().openMainMenu(p);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("nextpage")))
                {
                    main.getWardrobe().openBootsStatic(p, ev.getInventory().getItem(4).getAmount() + 1);
                } else if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("previouspage")))
                {
                    main.getWardrobe().openBootsStatic(p, ev.getInventory().getItem(4).getAmount() - 1);
                }
            } else if (ev.getCurrentItem() != null && ev.getCurrentItem().getType() == Material.LEATHER_BOOTS)
            {
                p.getInventory().setBoots(ev.getCurrentItem());
                main.getWardrobe().openMainMenu(p);
            }
            ev.setCancelled(true);
        } else if (ev.getInventory().getName().equals(main.getMessage("bootschangingmenu")))
        {
            if (ev.getCurrentItem() != null && ev.getCurrentItem().getItemMeta() != null && ev.getCurrentItem().getItemMeta().getDisplayName() != null)
            {
                if (ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("backtomainmenu")))
                {
                    main.getWardrobe().openMainMenu(p);
                } else if (ev.getCurrentItem().getType() == Material.LEVER && ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("nextpage")))
                {
                    main.getWardrobe().openBootsChanging(p, ev.getInventory().getItem(4).getAmount() + 1);
                } else if (ev.getCurrentItem().getType() == Material.LEVER && ev.getCurrentItem().getItemMeta().getDisplayName().contains(main.getMessage("previouspage")))
                {
                    main.getWardrobe().openBootsChanging(p, ev.getInventory().getItem(4).getAmount() - 1);
                } else
                {
                    String name = ev.getCurrentItem().getItemMeta().getDisplayName();
                    ArrayList<HashMap<ItemStack[], String>> sets = main.getWardrobe().getBootsChanging().get(p.getUniqueId().toString().replace("-", ""));
                    int changeTime = -1;
                    ItemStack[] items = null;
                    for (HashMap<ItemStack[], String> en : sets)
                    {
                        Map.Entry<ItemStack[], String> entry = en.entrySet().iterator().next();
                        if (entry.getValue().split(", ")[0].equals(ChatColor.stripColor(name)))
                        {
                            changeTime = Integer.parseInt(entry.getValue().split(", ")[1]);
                            items = entry.getKey();
                            break;
                        }
                    }
                    final ItemStack[] f_items = items;
                    if (changeTime != -1 && items != null)
                    {
                        main.getWardrobe().cancelArmorTask(p, 3);

                        main.getWardrobe().getArmorTasks().get(p)[3] = main.getServer().getScheduler().scheduleSyncRepeatingTask(main, new Runnable()
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
                        }, 0L, changeTime);
                        ev.setCancelled(true);
                        main.getWardrobe().openMainMenu(p);
                    }
                }
            }
            ev.setCancelled(true);
        } else if (ev.getCurrentItem() != null)
        {
            if (ev.getRawSlot() == 5 && ev.getCurrentItem().getType() == Material.LEATHER_HELMET)
            {
                if (ev.getCurrentItem().hasItemMeta() && ev.getCurrentItem().getItemMeta().getDisplayName() != null && main.contains(main.getWardrobe().getHelmetChanging().get(p.getUniqueId().toString().replace("-", "")), ev.getCurrentItem().getItemMeta().getDisplayName()))
                {
                    ev.setCancelled(true);
                }
            } else if (ev.getRawSlot() == 6 && ev.getCurrentItem().getType() == Material.LEATHER_CHESTPLATE)
            {
                if (ev.getCurrentItem().hasItemMeta() && ev.getCurrentItem().getItemMeta().getDisplayName() != null && main.contains(main.getWardrobe().getChestplateChanging().get(p.getUniqueId().toString().replace("-", "")), ev.getCurrentItem().getItemMeta().getDisplayName()))
                {
                    ev.setCancelled(true);
                }
            } else if (ev.getRawSlot() == 7 && ev.getCurrentItem().getType() == Material.LEATHER_LEGGINGS)
            {
                if (ev.getCurrentItem().hasItemMeta() && ev.getCurrentItem().getItemMeta().getDisplayName() != null && main.contains(main.getWardrobe().getLeggingsChanging().get(p.getUniqueId().toString().replace("-", "")), ev.getCurrentItem().getItemMeta().getDisplayName()))
                {
                    ev.setCancelled(true);
                }
            } else if (ev.getRawSlot() == 8 && ev.getCurrentItem().getType() == Material.LEATHER_BOOTS)
            {
                if (ev.getCurrentItem().hasItemMeta() && ev.getCurrentItem().getItemMeta().getDisplayName() != null && main.contains(main.getWardrobe().getBootsChanging().get(p.getUniqueId().toString().replace("-", "")), ev.getCurrentItem().getItemMeta().getDisplayName()))
                {
                    ev.setCancelled(true);
                }
            }
        }
    }
}
