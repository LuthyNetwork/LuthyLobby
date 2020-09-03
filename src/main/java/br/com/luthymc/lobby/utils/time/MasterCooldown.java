package br.com.luthymc.lobby.utils.time;

import java.util.concurrent.TimeUnit;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Table;

public class MasterCooldown {

    private final Table<String, String, Long> cooldowns = HashBasedTable.create();

    public void add(final String uuid, final String key, final long delay, final TimeUnit unit) {
        this.cooldowns.put(uuid, key, (System.currentTimeMillis() + unit.toMillis(delay)));
    }

    public boolean contains(final String uuid, final String key) {
        return get(uuid, key) > 0;
    }

    public boolean remove(final String uuid, final String key) {
        if (!this.cooldowns.contains(uuid, key)) {
            return false;
        }
        this.cooldowns.remove(uuid, key);
        return true;
    }

    public long get(final String uuid, final String key) {
        if (!this.cooldowns.contains(uuid, key)) {
            return -1L;
        }
        long cooldown = millisLeft(uuid, key);

        return cooldown > 0 ? cooldown : -1;
    }

    public long millisLeft(final String player, final String key) {
        if (!this.cooldowns.contains(player, key)) {
            return 0L;
        }
        if (this.cooldowns.get(player, key) <= System.currentTimeMillis()) {
            this.cooldowns.remove(player, key);
            return 0L;
        }
        return this.cooldowns.get(player, key) - System.currentTimeMillis();
    }

    public int secondsLeft(final String playerName, final String key) {
        return (int) TimeUnit.MILLISECONDS.toSeconds(this.millisLeft(playerName, key));
    }

    public int minutesLeft(String playerName, String key) {
        return (int) TimeUnit.MILLISECONDS.toMinutes(this.millisLeft(playerName, key));
    }

    public String timeLeft(final String player, final String key) {
        final int totalSeconds = this.secondsLeft(player, key);
        return TimeUtilities.timeLeft(totalSeconds);
    }

    public Table<String, String, Long> getCooldowns() {
        return cooldowns;
    }

}