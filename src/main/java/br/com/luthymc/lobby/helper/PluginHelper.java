package br.com.luthymc.lobby.helper;

import com.ianlibanio.voidcommand.VoidCommand;
import com.ianlibanio.voidcommand.registration.VoidRegister;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PluginHelper extends JavaPlugin {

    private final VoidRegister voidRegister = new VoidRegister(this);

    public abstract void load();
    public abstract void enable();
    public abstract void disable();

    @Override
    public void onLoad() {
        load();
    }

    @Override
    public void onEnable() {
        enable();
    }

    @Override
    public void onDisable() {
        disable();
    }

    public void register(VoidCommand... commands) {
        voidRegister.add(commands);
    }

    public void listener(Listener... listeners) {
        for (Listener listener : listeners) {
            Bukkit.getPluginManager().registerEvents(listener, this);
        }
    }

}
