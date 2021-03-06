package at.mlps.rc.event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;
import com.gamingmesh.jobs.Jobs;
import com.gamingmesh.jobs.container.JobProgression;

import at.mlps.rc.api.APIs;
import at.mlps.rc.api.ChannelManager;
import at.mlps.rc.api.ChatFont;
import at.mlps.rc.api.PerformanceMonitor;
import at.mlps.rc.cmd.MoneyAPI;
import at.mlps.rc.main.Main;
import at.mlps.rc.main.Serverupdater;
import at.mlps.rc.mysql.lb.MySQL;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import ru.tehkode.permissions.PermissionUser;
import ru.tehkode.permissions.bukkit.PermissionsEx;

public class ScoreboardCLS implements Listener{
	
	/*  used: ⬛
	 *  unused: ⬜
	 * 
	 */
	
	int sbmain = 0;
	private static HashMap<String, String> tabHM = new HashMap<>();
	private static HashMap<String, String> chatHM = new HashMap<>();
	
	//TODO checksum when server is lagging, to find out, WHY it lags.
	
	@SuppressWarnings("deprecation")
	public void setScoreboard(Player p) throws IllegalStateException, IllegalArgumentException, SQLException {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sb.registerNewObjective("aaa", "dummy", "InfoBoard");
		
		APIs api = new APIs();
		int ping = api.getPlayerPing(p);
		PermissionUser po = PermissionsEx.getUser(p);
		int pusergen = api.getPlayers("BungeeCord", "currPlayers");
		int pusermax = api.getPlayers("BungeeCord", "maxPlayers");
		int pusercurr = Bukkit.getOnlinePlayers().size();
		
		int userperc = (pusergen * 100 / pusermax);
		int cashmoney = MoneyAPI.getMoney(p.getUniqueId());
		int bankmoney = MoneyAPI.getBankMoney(p.getUniqueId());
		
		if(getSB(p) == 0) {
			//do nothing - as this is the off-state of the scoreboard
		}else if(getSB(p) == 1) {
			if(sbmain >= 0 && sbmain <= 5) {
				o.getScore("§7Player:").setScore(7);
				if(userperc <= 20) {
					o.getScore("  §7(§9" + pusercurr + "§7) §a" + pusergen + " §7/§c " + pusermax).setScore(6);
				}else if(userperc >= 21 && userperc <= 40) {
					o.getScore("  §7(§9" + pusercurr + "§7) §2" + pusergen + " §7/§c " + pusermax).setScore(6);
				}else if(userperc >= 41 && userperc <= 60) {
					o.getScore("  §7(§9" + pusercurr + "§7) §e" + pusergen + " §7/§c " + pusermax).setScore(6);
				}else if(userperc >= 61 && userperc <= 80) {
					o.getScore("  §7(§9" + pusercurr + "§7) §6" + pusergen + " §7/§c " + pusermax).setScore(6);
				}else if(userperc >= 81 && userperc <= 90) {
					o.getScore("  §7(§9" + pusercurr + "§7) §c" + pusergen + " §7/§c " + pusermax).setScore(6);
				}else if(userperc >= 91) {
					o.getScore("  §7(§9" + pusercurr + "§7) §4" + pusergen + " §7/§c " + pusermax).setScore(6);
				}
				o.getScore("  §a").setScore(5);
				o.getScore("§7Ping:").setScore(4);
				o.getScore("  §a" + ping + "§7ms").setScore(3);
				o.getScore("  §b").setScore(2);
				o.getScore(api.returnStringReady(p, "scoreboard.sideboard.rank")).setScore(1);
				if(po.inGroup("pman")) {
					o.getScore("§7» §9Project Manager").setScore(3);
				}else if(po.inGroup("cman")) {
					o.getScore("§7» §2Community Manager").setScore(3);
				}else if(po.inGroup("gmmman")) {
					o.getScore("§7» §4Game Mod. Manager").setScore(3);
				}else if(po.inGroup("dev")) {
					o.getScore("§7» §5Developer").setScore(3);
				}else if(po.inGroup("hr")) {
					o.getScore("§7» §6Human Resources").setScore(3);
				}else if(po.inGroup("cm")) {
					o.getScore("§7» §aCommunity Moderator").setScore(3);
				}else if(po.inGroup("ct")) {
					o.getScore("§7» §1Content Team").setScore(3);
				}else if(po.inGroup("st")) {
					o.getScore("§7» §eSupport Team").setScore(3);
				}else if(po.inGroup("bd")) {
					o.getScore("§7» §bBuilder").setScore(3);
				}else if(po.inGroup("gm")) {
					o.getScore("§7» §cGame Moderator").setScore(3);
				}else if(po.inGroup("aot")) {
					o.getScore("§7» §dAdd-On Team").setScore(3);
				}else if(po.inGroup("train")) {
					o.getScore("§7» §bTrainee").setScore(3);
				}else if(po.inGroup("rltm")) {
					o.getScore("§7» §3Retired Legend").setScore(3);
				}else if(po.inGroup("rtm")) {
					o.getScore("§7» §3Retired Team Member").setScore(3);
				}else if(po.inGroup("part")) {
					o.getScore("§7» §2Partner").setScore(3);
				}else if(po.inGroup("fs")) {
					o.getScore("§7» §dForum Supporter").setScore(3);
				}else if(po.inGroup("nb")) {
					o.getScore("§7» §dNitro Booster").setScore(3);
				}else if(po.inGroup("bt")) {
					o.getScore("§7» §dBeta Tester").setScore(3);
				}else if(po.inGroup("friend")) {
					o.getScore("§7» Friend").setScore(3);
				}else if(po.inGroup("default")) {
					o.getScore("§7» §fPlayer").setScore(3);
				}else {
					o.getScore("§7» §cunknown Role").setScore(3);
				}
			}else if(sbmain >= 6 && sbmain <= 10) {
				o.getScore("§7Server:").setScore(8);
				o.getScore("  §a" + api.getServerName() + " §7/§a " + api.getServerId()).setScore(7);
				o.getScore("  §9").setScore(6);
				o.getScore(api.returnStringReady(p, "scoreboard.sideboard.money")).setScore(5);
				o.getScore("  §a" + bankmoney + "§7 Bank").setScore(4);
				o.getScore("  §a" + cashmoney + "§7 Cash").setScore(3);
				o.getScore("  §8").setScore(2);
				o.getScore(api.returnStringReady(p, "scoreboard.sideboard.playerid")).setScore(1);
				o.getScore("  §7" + igpre(p) + " §9" + igid(p)).setScore(0);
			}
		}else if(getSB(p) == 2) {
			if(Main.serverlist.contains(api.getServerName())) {
				List<JobProgression> jobs = Jobs.getPlayerManager().getJobsPlayer(p).getJobProgression();
				int i = 0;
				int j = jobs.size();
				DecimalFormat df = new DecimalFormat("#.##");
				o.getScore("§9Jobs").setScore(15);
				for(JobProgression job : jobs) {
					o.getScore("§7Exp: §a" + df.format(job.getExperience()) + " §7/§c" + df.format(job.getMaxExperience())).setScore(i);
					i++;
					o.getScore("§7Job: §6" + job.getJob().getName()).setScore(i);
					i++;
					o.getScore("§9  Job " + j).setScore(i);
					j--;
					i++;
				}
			}else {
				/*
				 *  %server does
				 *  not support
				 *  the Jobview!
				 *  
				 *  Setting back
				 *  to default
				 *  view!
				 */
				o.getScore("§e" + api.getServerName() + " §cdoes").setScore(6);
				o.getScore("§cnot support").setScore(5);
				o.getScore("§cthe Jobview!").setScore(4);
				o.getScore("§b").setScore(3);
				o.getScore("§cSetting back").setScore(2);
				o.getScore("§cto default").setScore(1);
				o.getScore("§cview!").setScore(0);
				setSB(p, 1);
			}
		}else if(getSB(p) == 3) {
			if(sbmain >= 0 && sbmain <= 5) {
				o.getScore("§7Newest Report").setScore(9);
				o.getScore("§a§b§c").setScore(8);
				o.getScore("§7Reporter:").setScore(7);
				o.getScore("  §1§a" + retLatestReport("reporter")).setScore(6);
				o.getScore("§7Perpetrator:").setScore(5);
				o.getScore("  §2§c" + retLatestReport("perpetrator")).setScore(4);
				o.getScore("§7Server:").setScore(3);
				o.getScore("  §3§a" + retLatestReport("server")).setScore(2);
				o.getScore("§7Reason:").setScore(1);
				o.getScore("  §4§a" + retLatestReport("reason")).setScore(0);
			}else if(sbmain >= 6 && sbmain <= 10) {
				o.getScore("§7Reports total " + api.getServerName()).setScore(7);
				o.getScore("§1  §a" + retRepsTotCuSe() + " §7Reports").setScore(6);
				o.getScore("§7Reports today " + api.getServerName()).setScore(5);
				o.getScore("§2  §a" + retRepsTodCuSe() + " §7Reports").setScore(4);
				o.getScore("§7Reports total Network").setScore(3);
				o.getScore("§3  §a" + retRepstotal() + " §7Reports").setScore(2);
				o.getScore("§7Reports today Network").setScore(1);
				o.getScore("§4  §a" + retRepsToday() + " §7Reports").setScore(0);
			}
		}else if(getSB(p) == 4) {
			Runtime runtime = Runtime.getRuntime();
			PerformanceMonitor cpu = new PerformanceMonitor();
			int cpucores = runtime.availableProcessors();
			double cpuload = cpu.getCpuUsage();
			long ramusage = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
			long ramtotal = runtime.totalMemory() / 1048576L;
			float rampercentage = (ramusage * 100/ramtotal);
			float networkuserperc = (pusergen * 100/pusermax);
			float curruserperc = (pusercurr * 100/pusermax);
			
			if(sbmain >= 0 && sbmain <= 5) {
				o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
				o.getScore(visualizeDouble(cpuload, "§1")).setScore(6);
				o.getScore("§7Ramload:").setScore(5);
				o.getScore(visualizeDouble(rampercentage, "§2")).setScore(4);
				o.getScore("§7Playerload Network:").setScore(3);
				o.getScore(visualizeDouble(networkuserperc, "§3")).setScore(2);
				o.getScore("§7Playerload " + api.getServerName()).setScore(1);
				o.getScore(visualizeDouble(curruserperc, "§4")).setScore(0);
			}else if(sbmain >= 6 && sbmain <= 10) {
				DecimalFormat df = new DecimalFormat("#.##");
				o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
				o.getScore("  §1§a" + df.format(cpuload) + "§7% usage").setScore(6);
				o.getScore("§7Ramload: §a").setScore(5);
				o.getScore("  §2§a" + ramusage + "§7MB /§c " + ramtotal + "§7MB").setScore(4);
				o.getScore("§7Playerload Network:").setScore(3);
				o.getScore("  §3§a" + pusergen + " §7Players").setScore(2);
				o.getScore("§7Playerload " + api.getServerName()).setScore(1);
				o.getScore("  §4§a" + Bukkit.getOnlinePlayers().size() + " §7Players").setScore(0);
			}
		}else if(getSB(p) == 5) {
			String pl = getRadio("playlist");
			String art = getRadio("artist");
			String tra = getRadio("track");
			String alb = getRadio("album");
			String currListen = getRadio("current_listener");
			o.getScore("§aRedi§6FM").setScore(11);
			o.getScore("§a§b").setScore(10);
			o.getScore("§7Current Playlist:").setScore(9);
			if(pl.length() <= 24) {
				o.getScore("  §9§a" + pl).setScore(8);
			}else {
				o.getScore("  §9§a" + pl.substring(0, 24)).setScore(8);
			}
			o.getScore("§7Current Listeners:").setScore(7);
			o.getScore("  §6§a" + currListen).setScore(6);
			o.getScore("§7Current Track:").setScore(5);
			if(tra.length() <= 24) {
				o.getScore("  §8§a" + tra).setScore(4);
			}else {
				o.getScore("  §8§a" + tra.substring(0, 24)).setScore(4);
			}
			o.getScore("§7Current Artist:").setScore(3);
			if(art.length() <= 24) {
				o.getScore("  §7§a" + art).setScore(2);
			}else {
				o.getScore("  §7§a" + art.substring(0, 24)).setScore(2);
			}
			if(!alb.equalsIgnoreCase("null") || !alb.equalsIgnoreCase("none") || !alb.isBlank()) {
				o.getScore("§7Current Album:").setScore(1);
				if(alb.length() <= 24) {
					o.getScore("  §6§a" + alb).setScore(0);
				}else {
					o.getScore("  §6§a" + alb.substring(0, 24)).setScore(0);
				}
			}
		}else if(getSB(p) == 6) {
			if(p.hasPermission("mlps.isTeam")) {
				o.getScore("§7Servers/Players:").setScore(7);
				o.getScore("  §bStaffserver§7: §a" + getPlayers("Staffserver")).setScore(6);
			}else {
				o.getScore("§7Servers/Players:").setScore(6);
			}
			o.getScore("  §6Lobby§7: §a" + getPlayers("Lobby")).setScore(5);
			o.getScore("  §eCreative§7: §a" + getPlayers("Creative")).setScore(4);
			o.getScore("  §cSurvival§7: §a" + getPlayers("Survival")).setScore(3);
			o.getScore("  §fSky§2Block§7: §a" + getPlayers("SkyBlock")).setScore(2);
			o.getScore("  §6Towny§7: §a" + getPlayers("Towny")).setScore(1);
			o.getScore("  §5Farmserver§7: §a" + getPlayers("Farmserver")).setScore(0);
		}else if(getSB(p) == 7) {
			Location loc = p.getLocation();
			DecimalFormat df = new DecimalFormat("#.##");
			o.getScore("§7Position").setScore(6);
			o.getScore("§0").setScore(5);
			o.getScore("§7Server:§a " + api.getServerName()).setScore(4);
			o.getScore("§7World:§a " + loc.getWorld().getName()).setScore(3);
			o.getScore("§7X:§a " + df.format(loc.getX())).setScore(2);
			o.getScore("§7Y:§a " + df.format(loc.getY())).setScore(1);
			o.getScore("§7Z:§a " + df.format(loc.getZ())).setScore(0);
		}
		
		p.setScoreboard(sb);
		
		Team pman = getTeam(sb, "00000", retPrefix("pman", "prefix_tab"), ChatColor.GRAY);
		Team cman = getTeam(sb, "00010", retPrefix("cman", "prefix_tab"), ChatColor.GRAY);
		Team gmmman = getTeam(sb, "00020", retPrefix("gmmman", "prefix_tab"), ChatColor.GRAY);
		Team dev = getTeam(sb, "00030", retPrefix("dev", "prefix_tab"), ChatColor.GRAY);
		Team hr = getTeam(sb, "00040", retPrefix("hr", "prefix_tab"), ChatColor.GRAY);
		Team cm = getTeam(sb, "00050", retPrefix("cm", "prefix_tab"), ChatColor.GRAY);
		Team ct = getTeam(sb, "00060", retPrefix("ct", "prefix_tab"), ChatColor.GRAY);
		Team st = getTeam(sb, "00070", retPrefix("st", "prefix_tab"), ChatColor.GRAY);
		Team bd = getTeam(sb, "00080", retPrefix("bd", "prefix_tab"), ChatColor.GRAY);
		Team gm = getTeam(sb, "00090", retPrefix("gm", "prefix_tab"), ChatColor.GRAY);
		Team aot = getTeam(sb, "00100", retPrefix("aot", "prefix_tab"), ChatColor.GRAY);
		Team train = getTeam(sb, "00110", retPrefix("train", "prefix_tab"), ChatColor.GRAY);
		Team rltm = getTeam(sb, "00120", retPrefix("rltm", "prefix_tab"), ChatColor.GRAY);
		Team rtm = getTeam(sb, "00130", retPrefix("rtm", "prefix_tab"), ChatColor.GRAY);
		Team part = getTeam(sb, "00140", retPrefix("part", "prefix_tab"), ChatColor.GRAY);
		Team fs = getTeam(sb, "00150", retPrefix("fs", "prefix_tab"), ChatColor.GRAY);
		Team nb = getTeam(sb, "00160", retPrefix("nb", "prefix_tab"), ChatColor.GRAY);
		Team bt = getTeam(sb, "00170", retPrefix("bt", "prefix_tab"), ChatColor.GRAY);
		Team friend = getTeam(sb, "00180", retPrefix("friend", "prefix_tab"), ChatColor.GRAY);
		Team player = getTeam(sb, "00190", retPrefix("player", "prefix_tab"), ChatColor.GRAY);
		Team afk = getTeam(sb, "00200", retPrefix("safk", "prefix_tab"), ChatColor.GRAY);
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			PermissionUser pp = PermissionsEx.getUser(all);
			HashMap<String, Object> hm = new HashMap<>();
	    	hm.put("uuid", all.getUniqueId().toString().replace("-", ""));
	    	ResultSet rs = Main.mysql.select("redicore_userstats", hm);
	    	rs.next();
			if(pp.inGroup("pman")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						pman.addPlayer(all);
						all.setDisplayName(retPrefix("pman", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("pman", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("hr")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						hr.addPlayer(all);
						all.setDisplayName(retPrefix("hr", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("hr", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("dev")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						dev.addPlayer(all);
						all.setDisplayName(retPrefix("dev", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("dev", "prefix_tab") + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("cman")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						cman.addPlayer(all);
						all.setDisplayName(retPrefix("cman", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("cman", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("gmmman")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						gmmman.addPlayer(all);
						all.setDisplayName(retPrefix("gmmman", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("gmmman", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("gm")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						gm.addPlayer(all);
						all.setDisplayName(retPrefix("gm", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("gm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("cm")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						cm.addPlayer(all);
						all.setDisplayName(retPrefix("cm", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("cm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("ct")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						ct.addPlayer(all);
						all.setDisplayName(retPrefix("ct", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("ct", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("st")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						st.addPlayer(all);
						all.setDisplayName(retPrefix("st", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("st", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("bd")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						bd.addPlayer(all);
						all.setDisplayName(retPrefix("bd", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("bd", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("aot")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						aot.addPlayer(all);
						all.setDisplayName(retPrefix("aot", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("aot", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("train")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						train.addPlayer(all);
						all.setDisplayName(retPrefix("train", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("train", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("rltm")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						rltm.addPlayer(all);
						all.setDisplayName(retPrefix("rltm", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("rltm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("rtm")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						rtm.addPlayer(all);
						all.setDisplayName(retPrefix("rtm", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("rtm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("part")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						part.addPlayer(all);
						all.setDisplayName(retPrefix("part", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("part", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("bt")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						bt.addPlayer(all);
						all.setDisplayName(retPrefix("bt", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("bt", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("fs")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						fs.addPlayer(all);
						all.setDisplayName(retPrefix("fs", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("fs", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("nb")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						nb.addPlayer(all);
						all.setDisplayName(retPrefix("nb", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("nb", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("friend")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						friend.addPlayer(all);
						all.setDisplayName(retPrefix("friend", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("friend", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						player.addPlayer(all);
						all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("default")) {
				if(isAFK(all)) {
					afk.addPlayer(all);
					all.setPlayerListName(retPrefix("safk", "prefix_tab") + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
				}else {
					player.addPlayer(all);
					all.setDisplayName(retPrefix("default", "prefix_chat") + all.getName());
					all.setPlayerListName(retPrefix("default", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
				}
			}else {
				player.addPlayer(all);
				all.setDisplayName("§cunknown Role " + all.getName());
				all.setPlayerListName("§cunknown Role " + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
			}
		}
	} 
	
	
	/*public void setScoreboard(Player p) throws IllegalStateException, IllegalArgumentException, SQLException {
		Scoreboard sb = Bukkit.getScoreboardManager().getNewScoreboard();
		Objective o = sb.registerNewObjective("Info", "Boards", "RediBoard");
		
		APIs api = new APIs();
		ChatFont cf = new ChatFont();
		int ping = api.getPlayerPing(p);
		PermissionUser pu = PermissionsEx.getUser(p);
		int pusergen = api.getPlayers("BungeeCord", "currPlayers");
		int pusermax = api.getPlayers("BungeeCord", "maxPlayers");
		int pusercurr = Bukkit.getOnlinePlayers().size();
		
		int userperc = (pusergen * 100 / pusermax);
		int cashmoney = MoneyAPI.getMoney(p.getUniqueId());
		int bankmoney = MoneyAPI.getBankMoney(p.getUniqueId());
		
		o.setDisplaySlot(DisplaySlot.SIDEBAR);
		o.setDisplayName(dbpr);
		if(Main.serverlist.contains(api.getServerName())) {
			if(getSB(p) == 0) {
				//do nothing - as this is the off-state of the scoreboard
			}else if(getSB(p) == 1) {
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7Player:").setScore(7);
					if(userperc <= 20) {
						o.getScore("  §7(§9" + pusercurr + "§7) §a" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 21 && userperc <= 40) {
						o.getScore("  §7(§9" + pusercurr + "§7) §2" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 41 && userperc <= 60) {
						o.getScore("  §7(§9" + pusercurr + "§7) §e" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 61 && userperc <= 80) {
						o.getScore("  §7(§9" + pusercurr + "§7) §6" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 81 && userperc <= 90) {
						o.getScore("  §7(§9" + pusercurr + "§7) §c" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 91) {
						o.getScore("  §7(§9" + pusercurr + "§7) §4" + pusergen + " §7/§c " + pusermax).setScore(6);
					}
					o.getScore("  §a").setScore(5);
					o.getScore("§7Ping:").setScore(4);
					o.getScore("  §a" + ping + "§7ms").setScore(3);
					o.getScore("  §b").setScore(2);
					o.getScore(api.returnStringReady(p, "scoreboard.sideboard.rank")).setScore(1);
					if (pu.inGroup("pman")) {
					    o.getScore("  §9Project Manager").setScore(0);
					}else if (pu.inGroup("cman")) {
					    o.getScore("  §2Community Manager").setScore(0);
					}else if (pu.inGroup("gmmman")) {
					    o.getScore("  §4Game Moderation Manager").setScore(0);
					}else if (pu.inGroup("dev")) {
					    o.getScore("  §5Developer").setScore(0);
					}else if (pu.inGroup("hr")) {
					    o.getScore("  §6Human Resources").setScore(0);
					}else if (pu.inGroup("cm")) {
					    o.getScore("  §aCommunity Moderator").setScore(0);
					}else if (pu.inGroup("ct")) {
					    o.getScore("  §1Content Team").setScore(0);
					}else if (pu.inGroup("st")) {
					    o.getScore("  §eSupport Team").setScore(0);
					}else if (pu.inGroup("bd")) {
					    o.getScore("  §bBuilder").setScore(0);
					}else if (pu.inGroup("gm")) {
					    o.getScore("  §cGame Moderator").setScore(0);
					}else if (pu.inGroup("aot")) {
					    o.getScore("  §dAdd-On Team").setScore(0);
					}else if (pu.inGroup("train")) {
					    o.getScore(cf.format("  #1497ebTrainee")).setScore(0);
					}else if (pu.inGroup("rltm")) {
					    o.getScore("  §3Retired Legend").setScore(0);
					}else if (pu.inGroup("rtm")) {
					    o.getScore("  §3Retired Team Member").setScore(0);
					}else if (pu.inGroup("part")) {
					    o.getScore(cf.format("  #864b30Partner")).setScore(0);
					}else if (pu.inGroup("fs")) {
					    o.getScore(cf.format("  #f47fffForum Supporter")).setScore(0);
					}else if (pu.inGroup("nb")) {
					    o.getScore(cf.format(" #f47fffNitro Booster")).setScore(0);
					}else if (pu.inGroup("bt")) {
					    o.getScore(cf.format("  #632fc7Beta-Tester")).setScore(0);
					}else if (pu.inGroup("friend")) {
					    o.getScore("  §7Friend").setScore(0);
					}else if (pu.inGroup("default")) {
					    o.getScore("  §fPlayer").setScore(0);
					}else {
						o.getScore("§cunknown Role").setScore(0);
					}
				}else if(sbmain >= 6 && sbmain <= 10) {
					o.getScore("§7Server:").setScore(8);
					o.getScore("  §a" + api.getServerName() + " §7/§a " + api.getServerId()).setScore(7);
					o.getScore("  §9").setScore(6);
					o.getScore(api.returnStringReady(p, "scoreboard.sideboard.money")).setScore(5);
					o.getScore("  §a" + bankmoney + "§7 Bank").setScore(4);
					o.getScore("  §a" + cashmoney + "§7 Cash").setScore(3);
					o.getScore("  §8").setScore(2);
					o.getScore(api.returnStringReady(p, "scoreboard.sideboard.playerid")).setScore(1);
					o.getScore("  §7" + igpre(p) + " §9" + igid(p)).setScore(0);
				}
			}else if(getSB(p) == 2) {
				List<JobProgression> jobs = Jobs.getPlayerManager().getJobsPlayer(p).getJobProgression();
				int i = 0;
				int j = jobs.size();
				DecimalFormat df = new DecimalFormat("#.##");
				o.getScore("§9Jobs").setScore(15);
				for(JobProgression job : jobs) {
					o.getScore("§7Exp: §a" + df.format(job.getExperience()) + " §7/§c" + df.format(job.getMaxExperience())).setScore(i);
					i++;
					o.getScore("§7Job: §6" + job.getJob().getName()).setScore(i);
					i++;
					o.getScore("§9  Job " + j).setScore(i);
					j--;
					i++;
				}
			}else if(getSB(p) == 3) {
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7Newest Report").setScore(9);
					o.getScore("§a§b§c").setScore(8);
					o.getScore("§7Reporter:").setScore(7);
					o.getScore("  §1§a" + retLatestReport("reporter")).setScore(6);
					o.getScore("§7Perpetrator:").setScore(5);
					o.getScore("  §2§c" + retLatestReport("perpetrator")).setScore(4);
					o.getScore("§7Server:").setScore(3);
					o.getScore("  §3§a" + retLatestReport("server")).setScore(2);
					o.getScore("§7Reason:").setScore(1);
					o.getScore("  §4§a" + retLatestReport("reason")).setScore(0);
				}else if(sbmain >= 6 && sbmain <= 10) {
					o.getScore("§7Reports total " + api.getServerName()).setScore(7);
					o.getScore("§1  §a" + retRepsTotCuSe() + " §7Reports").setScore(6);
					o.getScore("§7Reports today " + api.getServerName()).setScore(5);
					o.getScore("§2  §a" + retRepsTodCuSe() + " §7Reports").setScore(4);
					o.getScore("§7Reports total Network").setScore(3);
					o.getScore("§3  §a" + retRepstotal() + " §7Reports").setScore(2);
					o.getScore("§7Reports today Network").setScore(1);
					o.getScore("§4  §a" + retRepsToday() + " §7Reports").setScore(0);
				}
			}else if(getSB(p) == 4) {
				Runtime runtime = Runtime.getRuntime();
				PerformanceMonitor cpu = new PerformanceMonitor();
				int cpucores = runtime.availableProcessors();
				double cpuload = cpu.getCpuUsage();
				long ramusage = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
				long ramtotal = runtime.totalMemory() / 1048576L;
				float rampercentage = (ramusage * 100/ramtotal);
				float networkuserperc = (pusergen * 100/pusermax);
				float curruserperc = (pusercurr * 100/pusermax);
				
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
					if(cpuload <= 10) {
						o.getScore("§1§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 11 && cpuload <= 20) {
						o.getScore("§1§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 21 && cpuload <= 30) {
						o.getScore("§1§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 31 && cpuload <= 40) {
						o.getScore("§1§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 41 && cpuload <= 50) {
						o.getScore("§1§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 51 && cpuload <= 60) {
						o.getScore("§1§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 61 && cpuload <= 70) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(6);
					}else if(cpuload >= 71 && cpuload <= 80) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(6);
					}else if(cpuload >= 81 && cpuload <= 90) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(6);
					}else if(cpuload >= 91 && cpuload <= 100) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(6);
					}else {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(6);
					}
					o.getScore("§7Ramload:").setScore(5);
					if(rampercentage <= 10) {
						o.getScore("§2§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 11 && rampercentage <= 20) {
						o.getScore("§2§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 21 && rampercentage <= 30) {
						o.getScore("§2§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 31 && rampercentage <= 40) {
						o.getScore("§2§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 41 && rampercentage <= 50) {
						o.getScore("§2§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 51 && rampercentage <= 60) {
						o.getScore("§2§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 61 && rampercentage <= 70) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 71 && rampercentage <= 80) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(4);
					}else if(rampercentage >= 81 && rampercentage <= 90) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(4);
					}else if(rampercentage >= 91 && rampercentage <= 100) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(4);
					}else {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(4);
					}
					o.getScore("§7Playerload Network:").setScore(3);
					if(networkuserperc <= 10) {
						o.getScore("§3§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 11 && networkuserperc <= 20) {
						o.getScore("§3§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 21 && networkuserperc <= 30) {
						o.getScore("§3§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 31 && networkuserperc <= 40) {
						o.getScore("§3§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 41 && networkuserperc <= 50) {
						o.getScore("§3§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 51 && networkuserperc <= 60) {
						o.getScore("§3§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 61 && networkuserperc <= 70) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 71 && networkuserperc <= 80) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(2);
					}else if(networkuserperc >= 81 && networkuserperc <= 90) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(2);
					}else if(networkuserperc >= 91 && networkuserperc <= 100) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(2);
					}else {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(2);
					}
					o.getScore("§7Playerload " + api.getServerName()).setScore(1);
					if(curruserperc <= 10) {
						o.getScore("§4§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 11 && curruserperc <= 20) {
						o.getScore("§4§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 21 && curruserperc <= 30) {
						o.getScore("§4§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 31 && curruserperc <= 40) {
						o.getScore("§4§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 41 && curruserperc <= 50) {
						o.getScore("§4§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 51 && curruserperc <= 60) {
						o.getScore("§4§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 61 && curruserperc <= 70) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 71 && curruserperc <= 80) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(0);
					}else if(curruserperc >= 81 && curruserperc <= 90) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(0);
					}else if(curruserperc >= 91 && curruserperc <= 100) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(0);
					}else {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(0);
					}
				}else if(sbmain >= 6 && sbmain <= 10) {
					DecimalFormat df = new DecimalFormat("#.##");
					o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
					o.getScore("  §1§a" + df.format(cpuload) + "§7% usage").setScore(6);
					o.getScore("§7Ramload: §a").setScore(5);
					o.getScore("  §2§a" + ramusage + "§7MB /§c " + ramtotal + "§7MB").setScore(4);
					o.getScore("§7Playerload Network:").setScore(3);
					o.getScore("  §3§a" + pusergen + " §7Players").setScore(2);
					o.getScore("§7Playerload " + api.getServerName()).setScore(1);
					o.getScore("  §4§a" + Bukkit.getOnlinePlayers().size() + " §7Players").setScore(0);
				}
			}else if(getSB(p) == 5) {
				String pl = getRadio("playlist");
				String art = getRadio("artist");
				String tra = getRadio("track");
				String alb = getRadio("album");
				String currListen = getRadio("current_listener");
				o.getScore("§aRedi§6FM").setScore(11);
				o.getScore("§a§b").setScore(10);
				o.getScore("§7Current Playlist:").setScore(9);
				if(pl.length() <= 24) {
					o.getScore("  §9§a" + pl).setScore(8);
				}else {
					o.getScore("  §9§a" + pl.substring(0, 24)).setScore(8);
				}
				o.getScore("§7Current Listeners:").setScore(7);
				o.getScore("  §6§a" + currListen).setScore(6);
				o.getScore("§7Current Track:").setScore(5);
				if(tra.length() <= 24) {
					o.getScore("  §8§a" + tra).setScore(4);
				}else {
					o.getScore("  §8§a" + tra.substring(0, 24)).setScore(4);
				}
				o.getScore("§7Current Artist:").setScore(3);
				if(art.length() <= 24) {
					o.getScore("  §7§a" + art).setScore(2);
				}else {
					o.getScore("  §7§a" + art.substring(0, 24)).setScore(2);
				}
				if(!alb.equalsIgnoreCase("null") || !alb.equalsIgnoreCase("none")) {
					o.getScore("§7Current Album:").setScore(1);
					if(alb.length() <= 24) {
						o.getScore("  §6§a" + alb).setScore(0);
					}else {
						o.getScore("  §6§a" + alb.substring(0, 24)).setScore(0);
					}
				}
			}else if(getSB(p) == 6) {
				if(p.hasPermission("mlps.isTeam")) {
					o.getScore("§7Servers/Players:").setScore(7);
					o.getScore("  §bStaffserver§7: §a" + getPlayers("Staffserver")).setScore(6);
				}else {
					o.getScore("§7Servers/Players:").setScore(6);
				}
				o.getScore("  §6Lobby§7: §a" + getPlayers("Lobby")).setScore(5);
				o.getScore("  §eCreative§7: §a" + getPlayers("Creative")).setScore(4);
				o.getScore("  §cSurvival§7: §a" + getPlayers("Survival")).setScore(3);
				o.getScore("  §fSky§2Block§7: §a" + getPlayers("SkyBlock")).setScore(2);
				o.getScore("  §6Towny§7: §a" + getPlayers("Towny")).setScore(1);
				o.getScore("  §5Farmserver§7: §a" + getPlayers("Farmserver")).setScore(0);
			}else if(getSB(p) == 7) {
				//server, world, x, y, z
				Location loc = p.getLocation();
				DecimalFormat df = new DecimalFormat("#.##");
				o.getScore("§7Position").setScore(6);
				o.getScore("§0").setScore(5);
				o.getScore("§7Server:§a " + api.getServerName()).setScore(4);
				o.getScore("§7World:§a " + loc.getWorld().getName()).setScore(3);
				o.getScore("§7X:§a " + df.format(loc.getX())).setScore(2);
				o.getScore("§7Y:§a " + df.format(loc.getY())).setScore(1);
				o.getScore("§7Z:§a " + df.format(loc.getZ())).setScore(0);
			}
		}else {
			if(getSB(p) == 0) {
				//do nothing - as this is the off-state of the scoreboard
			}else if(getSB(p) == 1 || getSB(p) == 2) {
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7Player:").setScore(7);
					if(userperc <= 20) {
						o.getScore("  §7(§9" + pusercurr + "§7) §a" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 21 && userperc <= 40) {
						o.getScore("  §7(§9" + pusercurr + "§7) §2" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 41 && userperc <= 60) {
						o.getScore("  §7(§9" + pusercurr + "§7) §e" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 61 && userperc <= 80) {
						o.getScore("  §7(§9" + pusercurr + "§7) §6" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 81 && userperc <= 90) {
						o.getScore("  §7(§9" + pusercurr + "§7) §c" + pusergen + " §7/§c " + pusermax).setScore(6);
					}else if(userperc >= 91) {
						o.getScore("  §7(§9" + pusercurr + "§7) §4" + pusergen + " §7/§c " + pusermax).setScore(6);
					}
					o.getScore("  §a").setScore(5);
					o.getScore("§7Ping:").setScore(4);
					o.getScore("  §a" + ping + "§7ms").setScore(3);
					o.getScore("  §b").setScore(2);
					o.getScore(api.returnStringReady(p, "scoreboard.sideboard.rank")).setScore(1);
					if (pu.inGroup("Developer")) {
					    o.getScore("  §dDeveloper").setScore(0);
					}else if (pu.inGroup("PMan")) {
					    o.getScore("  §6Projectmanager").setScore(0);
					}else if(pu.inGroup("HumanR")) {
						o.getScore("  §5Human Resources").setScore(0);
					}else if (pu.inGroup("CMan")) {
					    o.getScore("  §2Community Manager").setScore(0);
					}else if (pu.inGroup("AMan")) {
					    o.getScore("  §4Game Moderation Manager").setScore(0);
					}else if (pu.inGroup("Admin")) {
					    o.getScore("  §cGame Moderator").setScore(0);
					}else if (pu.inGroup("Support")) {
					    o.getScore("  §9Support").setScore(0);
					}else if (pu.inGroup("Translator")) {
						o.getScore("  §eTranslator").setScore(0);
					}else if (pu.inGroup("Mod")) {
					    o.getScore("  §aModerator").setScore(0);
					}else if (pu.inGroup("Builder")) {
					    o.getScore("  §bBuilder").setScore(0);
					}else if (pu.inGroup("RLTM")) {
					    o.getScore("  §3Retired Legend Team Member").setScore(0);
					}else if (pu.inGroup("RTM")) {
					    o.getScore("  §3Retired Team Member").setScore(0);
					}else if (pu.inGroup("Partner")) {
					    o.getScore("  §aPartner").setScore(0);
					}else if (pu.inGroup("Beta")) {
					    o.getScore("  §5Beta-Tester").setScore(0);
					}else if (pu.inGroup("Patron")) {
					    o.getScore("  §ePatron").setScore(0);
					}else if (pu.inGroup("NitroBooster")) {
					    o.getScore("  §5Nitro Booster").setScore(0);
					}else if (pu.inGroup("Friend")) {
					    o.getScore("  §3Friend").setScore(0);
					}else {
						o.getScore("  §7Player").setScore(0);
					}
				}else if(sbmain >= 6 && sbmain <= 10) {
					o.getScore("§7Server:").setScore(8);
					o.getScore("  §a" + api.getServerName() + " §7/§a " + api.getServerId()).setScore(7);
					o.getScore("  §9").setScore(6);
					o.getScore(api.returnStringReady(p, "scoreboard.sideboard.money")).setScore(5);
					o.getScore("  §a" + bankmoney + "§7 Bank").setScore(4);
					o.getScore("  §a" + cashmoney + "§7 Cash").setScore(3);
					o.getScore("  §8").setScore(2);
					o.getScore(api.returnStringReady(p, "scoreboard.sideboard.playerid")).setScore(1);
					o.getScore("  §7" + igpre(p) + " §9" + igid(p)).setScore(0);
				}
			}else if(getSB(p) == 3) {
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7Newest Report").setScore(9);
					o.getScore("§a§b§c").setScore(8);
					o.getScore("§7Reporter:").setScore(7);
					o.getScore("  §1§a" + retLatestReport("reporter")).setScore(6);
					o.getScore("§7Perpetrator:").setScore(5);
					o.getScore("  §2§c" + retLatestReport("perpetrator")).setScore(4);
					o.getScore("§7Server:").setScore(3);
					o.getScore("  §3§a" + retLatestReport("server")).setScore(2);
					o.getScore("§7Reason:").setScore(1);
					o.getScore("  §4§a" + retLatestReport("reason")).setScore(0);
				}else if(sbmain >= 6 && sbmain <= 10) {
					o.getScore("§7Reports total " + api.getServerName()).setScore(7);
					o.getScore("§1  §a" + retRepsTotCuSe() + " §7Reports").setScore(6);
					o.getScore("§7Reports today " + api.getServerName()).setScore(5);
					o.getScore("§2  §a" + retRepsTodCuSe() + " §7Reports").setScore(4);
					o.getScore("§7Reports total Network").setScore(3);
					o.getScore("§3  §a" + retRepstotal() + " §7Reports").setScore(2);
					o.getScore("§7Reports today Network").setScore(1);
					o.getScore("§4  §a" + retRepsToday() + " §7Reports").setScore(0);
				}
			}else if(getSB(p) == 4) {
				Runtime runtime = Runtime.getRuntime();
				PerformanceMonitor cpu = new PerformanceMonitor();
				int cpucores = runtime.availableProcessors();
				double cpuload = cpu.getCpuUsage();
				long ramusage = (runtime.totalMemory() - runtime.freeMemory()) / 1048576L;
				long ramtotal = runtime.totalMemory() / 1048576L;
				float rampercentage = (ramusage * 100/ramtotal);
				float networkuserperc = (pusergen * 100/pusermax);
				float curruserperc = (pusercurr * 100/pusermax);
				
				if(sbmain >= 0 && sbmain <= 5) {
					o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
					if(cpuload <= 10) {
						o.getScore("§1§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 11 && cpuload <= 20) {
						o.getScore("§1§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 21 && cpuload <= 30) {
						o.getScore("§1§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 31 && cpuload <= 40) {
						o.getScore("§1§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 41 && cpuload <= 50) {
						o.getScore("§1§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 51 && cpuload <= 60) {
						o.getScore("§1§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(6);
					}else if(cpuload >= 61 && cpuload <= 70) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(6);
					}else if(cpuload >= 71 && cpuload <= 80) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(6);
					}else if(cpuload >= 81 && cpuload <= 90) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(6);
					}else if(cpuload >= 91 && cpuload <= 100) {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(6);
					}else {
						o.getScore("§1§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(6);
					}
					o.getScore("§7Ramload:").setScore(5);
					if(rampercentage <= 10) {
						o.getScore("§2§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 11 && rampercentage <= 20) {
						o.getScore("§2§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 21 && rampercentage <= 30) {
						o.getScore("§2§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 31 && rampercentage <= 40) {
						o.getScore("§2§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 41 && rampercentage <= 50) {
						o.getScore("§2§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 51 && rampercentage <= 60) {
						o.getScore("§2§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 61 && rampercentage <= 70) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(4);
					}else if(rampercentage >= 71 && rampercentage <= 80) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(4);
					}else if(rampercentage >= 81 && rampercentage <= 90) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(4);
					}else if(rampercentage >= 91 && rampercentage <= 100) {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(4);
					}else {
						o.getScore("§2§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(4);
					}
					o.getScore("§7Playerload Network:").setScore(3);
					if(networkuserperc <= 10) {
						o.getScore("§3§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 11 && networkuserperc <= 20) {
						o.getScore("§3§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 21 && networkuserperc <= 30) {
						o.getScore("§3§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 31 && networkuserperc <= 40) {
						o.getScore("§3§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 41 && networkuserperc <= 50) {
						o.getScore("§3§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 51 && networkuserperc <= 60) {
						o.getScore("§3§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 61 && networkuserperc <= 70) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(2);
					}else if(networkuserperc >= 71 && networkuserperc <= 80) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(2);
					}else if(networkuserperc >= 81 && networkuserperc <= 90) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(2);
					}else if(networkuserperc >= 91 && networkuserperc <= 100) {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(2);
					}else {
						o.getScore("§3§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(2);
					}
					o.getScore("§7Playerload " + api.getServerName()).setScore(1);
					if(curruserperc <= 10) {
						o.getScore("§4§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 11 && curruserperc <= 20) {
						o.getScore("§4§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 21 && curruserperc <= 30) {
						o.getScore("§4§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 31 && curruserperc <= 40) {
						o.getScore("§4§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 41 && curruserperc <= 50) {
						o.getScore("§4§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 51 && curruserperc <= 60) {
						o.getScore("§4§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 61 && curruserperc <= 70) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜").setScore(0);
					}else if(curruserperc >= 71 && curruserperc <= 80) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜").setScore(0);
					}else if(curruserperc >= 81 && curruserperc <= 90) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜").setScore(0);
					}else if(curruserperc >= 91 && curruserperc <= 100) {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛").setScore(0);
					}else {
						o.getScore("§4§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛").setScore(0);
					}
				}else if(sbmain >= 6 && sbmain <= 10) {
					DecimalFormat df = new DecimalFormat("#.##");
					o.getScore("§7CPU load: (§a" + cpucores + " §7Cores)").setScore(7);
					o.getScore("  §1§a" + df.format(cpuload) + "§7% usage").setScore(6);
					o.getScore("§7Ramload: §a").setScore(5);
					o.getScore("  §2§a" + ramusage + "§7MB /§c " + ramtotal + "§7MB").setScore(4);
					o.getScore("§7Playerload Network:").setScore(3);
					o.getScore("  §3§a" + pusergen + " §7Players").setScore(2);
					o.getScore("§7Playerload " + api.getServerName()).setScore(1);
					o.getScore("  §4§a" + Bukkit.getOnlinePlayers().size() + " §7Players").setScore(0);
				}
			}else if(getSB(p) == 5) {
				String pl = getRadio("playlist");
				String art = getRadio("artist");
				String tra = getRadio("track");
				String alb = getRadio("album");
				String currListen = getRadio("current_listener");
				o.getScore("§aRedi§6FM").setScore(11);
				o.getScore("§a§b").setScore(10);
				o.getScore("§7Current Playlist:").setScore(9);
				if(pl.length() <= 24) {
					o.getScore("  §9§a" + pl).setScore(8);
				}else {
					o.getScore("  §9§a" + pl.substring(0, 24)).setScore(8);
				}
				o.getScore("§7Current Listeners:").setScore(7);
				o.getScore("  §6§a" + currListen).setScore(6);
				o.getScore("§7Current Track:").setScore(5);
				if(tra.length() <= 24) {
					o.getScore("  §8§a" + tra).setScore(4);
				}else {
					o.getScore("  §8§a" + tra.substring(0, 24)).setScore(4);
				}
				o.getScore("§7Current Artist:").setScore(3);
				if(art.length() <= 24) {
					o.getScore("  §7§a" + art).setScore(2);
				}else {
					o.getScore("  §7§a" + art.substring(0, 24)).setScore(2);
				}
				if(!alb.equalsIgnoreCase("null") || !alb.equalsIgnoreCase("none")) {
					o.getScore("§7Current Album:").setScore(1);
					if(alb.length() <= 24) {
						o.getScore("  §6§a" + alb).setScore(0);
					}else {
						o.getScore("  §6§a" + alb.substring(0, 24)).setScore(0);
					}
				}
			}else if(getSB(p) == 6) {
				if(p.hasPermission("mlps.isTeam")) {
					o.getScore("§7Servers/Players:").setScore(7);
					o.getScore("  §bStaffserver§7: §a" + getPlayers("Staffserver")).setScore(6);
				}else {
					o.getScore("§7Servers/Players:").setScore(6);
				}
				o.getScore("  §6Lobby§7: §a" + getPlayers("Lobby")).setScore(5);
				o.getScore("  §eCreative§7: §a" + getPlayers("Creative")).setScore(4);
				o.getScore("  §cSurvival§7: §a" + getPlayers("Survival")).setScore(3);
				o.getScore("  §fSky§2Block§7: §a" + getPlayers("SkyBlock")).setScore(2);
				o.getScore("  §6Towny§7: §a" + getPlayers("Towny")).setScore(1);
				o.getScore("  §5Farmserver§7: §a" + getPlayers("Farmserver")).setScore(0);
			}else if(getSB(p) == 7) {
				//server, world, x, y, z
				Location loc = p.getLocation();
				DecimalFormat df = new DecimalFormat("#.##");
				o.getScore("§7Position").setScore(6);
				o.getScore("§0").setScore(5);
				o.getScore("§7Server:§a " + api.getServerName()).setScore(4);
				o.getScore("§7World:§a " + loc.getWorld().getName()).setScore(3);
				o.getScore("§7X:§a " + df.format(loc.getX())).setScore(2);
				o.getScore("§7Y:§a " + df.format(loc.getY())).setScore(1);
				o.getScore("§7Z:§a " + df.format(loc.getZ())).setScore(0);
			}
		}
		
		p.setScoreboard(sb);
		
		Team pm = getTeam(sb, "00000", retPrefix("pm", "prefix_tab"), ChatColor.GRAY); //gold
		Team hr = getTeam(sb, "00005", retPrefix("hr", "Prefix_tab"), ChatColor.GRAY);
		Team cman = getTeam(sb, "00010", retPrefix("cman", "prefix_tab"), ChatColor.GRAY); //dark-green
		Team aman = getTeam(sb, "00020", retPrefix("aman", "prefix_tab"), ChatColor.GRAY); //dark-red
		Team dev = getTeam(sb, "00030", retPrefix("dev", "prefix_tab"), ChatColor.GRAY); //light-purple
		Team admin = getTeam(sb, "00040", retPrefix("admin", "prefix_tab"), ChatColor.GRAY); //red
		Team mod = getTeam(sb, "00050", retPrefix("mod", "prefix_tab"), ChatColor.GRAY); //green
		Team translator = getTeam(sb, "00055", retPrefix("translator", "prefix_tab"), ChatColor.GRAY);
		Team support = getTeam(sb, "00060", retPrefix("support", "prefix_tab"), ChatColor.GRAY); //blue
		Team builder = getTeam(sb, "00070", retPrefix("builder", "prefix_tab"), ChatColor.GRAY); //aqua
		Team rltm = getTeam(sb, "00080", retPrefix("rltm", "prefix_tab"), ChatColor.GRAY); //dark-purple
		Team rtm = getTeam(sb, "00090", retPrefix("rtm", "prefix_tab"), ChatColor.GRAY); //dark-purple
		Team partner = getTeam(sb, "00100", retPrefix("partner", "prefix_tab"), ChatColor.GRAY); //green
		Team beta = getTeam(sb, "00110", retPrefix("beta", "prefix_tab"), ChatColor.GRAY); //dark-blue & red
		Team nitrobooster = getTeam(sb, "00130", retPrefix("dcnitro", "prefix_tab"), ChatColor.GRAY); //dark-blue
		Team freund = getTeam(sb, "00140", retPrefix("freund", "prefix_tab"), ChatColor.GRAY); //dark-aqua
		Team spieler = getTeam(sb, "00150", retPrefix("spieler", "prefix_tab"), ChatColor.GRAY); //gray
		
		Team tafk = getTeam(sb, "00160", retPrefix("tafk", "prefix_tab"), ChatColor.GRAY);
		Team afk = getTeam(sb, "00170", retPrefix("safk", "prefix_tab"), ChatColor.GRAY);
		
		for(Player all : Bukkit.getOnlinePlayers()) {
			PermissionUser pp = PermissionsEx.getUser(all);
			HashMap<String, Object> hm = new HashMap<>();
	    	hm.put("uuid", all.getUniqueId().toString().replace("-", ""));
	    	ResultSet rs = Main.mysql.select("redicore_userstats", hm);
	    	rs.next();
			if(pp.inGroup("PMan")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						pm.addPlayer(all);
						all.setDisplayName(retPrefix("pm", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("pm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("HumanR")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						hr.addPlayer(all);
						all.setDisplayName(retPrefix("hr", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("hr", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Developer")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						dev.addPlayer(all);
						all.setDisplayName(retPrefix("dev", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("dev", "prefix_tab") + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("CMan")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						cman.addPlayer(all);
						all.setDisplayName(retPrefix("cman", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("cman", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("AMan")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						aman.addPlayer(all);
						all.setDisplayName(retPrefix("aman", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("aman", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Admin")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						admin.addPlayer(all);
						all.setDisplayName(retPrefix("admin", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("admin", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Mod")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						mod.addPlayer(all);
						all.setDisplayName(retPrefix("mod", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("mod", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Translator")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						translator.addPlayer(all);
						all.setDisplayName(retPrefix("translator", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("translator", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Support")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						support.addPlayer(all);
						all.setDisplayName(retPrefix("support", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("support", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Builder")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						tafk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						builder.addPlayer(all);
						all.setDisplayName(retPrefix("builder", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("builder", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("RLTM")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						rltm.addPlayer(all);
						all.setDisplayName(retPrefix("rltm", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("rltm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("RTM")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						rtm.addPlayer(all);
						all.setDisplayName(retPrefix("rtm", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("rtm", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Partner")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						partner.addPlayer(all);
						all.setDisplayName(retPrefix("partner", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("partner", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Beta")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						beta.addPlayer(all);
						all.setDisplayName(retPrefix("beta", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("beta", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Nitrobooster")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						nitrobooster.addPlayer(all);
						all.setDisplayName(retPrefix("dcnitro", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("dcnitro", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else if(pp.inGroup("Friend")) {
				if(rs.getBoolean("loggedin")) {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7- " + all.getName() + " §7| ID§7: §a" + igid(all) + " §f" + igpre(all));
					}else {
						freund.addPlayer(all);
						all.setDisplayName(retPrefix("freund", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("freund", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}else {
					if(isAFK(all)) {
						afk.addPlayer(all);
						all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
					}else {
						spieler.addPlayer(all);
						all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
						all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
					}
				}
			}else {
				if(isAFK(all)) {
					afk.addPlayer(all);
					all.setPlayerListName("§9AFK §7| " + all.getName() + "7| ID: §a" + igid(all) + " " + igpre(all));
				}else {
					spieler.addPlayer(all);
					all.setDisplayName(retPrefix("spieler", "prefix_chat") + all.getName());
					all.setPlayerListName(retPrefix("spieler", "prefix_tab") + all.getName() + " §7| ID: §a" + igid(all) + " §f" + igpre(all));
				}
			}
		}
	}*/
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		String channel = ChannelManager.playerChannel.get(p).replace("public", "English").replace("german", "German");
		if(p.hasPermission("mlps.colorChat")) {
			ChatFont cf = new ChatFont();
			e.setFormat(cf.format("§7[§a" + channel + "§7] " + p.getDisplayName() + "§7 (§9" + igid(p) + "§7): " + ChatColor.translateAlternateColorCodes('&', e.getMessage().replace("%", "%%"))));
		}else {
			e.setFormat("§7[§a" + channel + "§7] " + p.getDisplayName() + "§7 (§9" + igid(p) + "§7): " + e.getMessage().replace("%", "%%"));
		}
	}
	
	//TODO Scoreboard will be set when joining, a different method will be used for refreshing the sideboard
	@EventHandler
	public void onJoin(PlayerJoinEvent e) throws IllegalStateException, IllegalArgumentException, SQLException {
		setScoreboard(e.getPlayer());
		APIs api = new APIs();
		e.getPlayer().spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(api.returnStringReady(e.getPlayer(), "event.join.welcomemessage").replace("%server", api.getServerName()).replace("%displayer", e.getPlayer().getDisplayName())));
	}
	
	private String igid(Player p) {
		String s = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
		    ResultSet rs = ps.executeQuery();
		    rs.next();
		    s = rs.getString("userid");
		    rs.close();
		    ps.close();
		}catch (SQLException e) { }
		return s;
	}
	
	private String visualizeDouble(float value, String color) {
		String retVal = "";
		if(value <= 10) {
			retVal = color + "§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜";
		}else if(value >= 11 && value <= 20) {
			retVal = color + "§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜";
		}else if(value >= 21 && value <= 30) {
			retVal = color + "§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜";
		}else if(value >= 31 && value <= 40) {
			retVal = color + "§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜";
		}else if(value >= 41 && value <= 50) {
			retVal = color + "§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜";
		}else if(value >= 51 && value <= 60) {
			retVal = color + "§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜";
		}else if(value >= 61 && value <= 70) {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜";
		}else if(value >= 71 && value <= 80) {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜";
		}else if(value >= 81 && value <= 90) {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜";
		}else if(value >= 91 && value <= 100) {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛";
		}else {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛";
		}
		return retVal;
	}
	
	private String visualizeDouble(double value, String color) {
		String retVal = "";
		if(value <= 10) {
			retVal = color + "§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜⬜";
		}else if(value >= 11 && value <= 20) {
			retVal = color + "§c⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜⬜";
		}else if(value >= 21 && value <= 30) {
			retVal = color + "§c⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜⬜";
		}else if(value >= 31 && value <= 40) {
			retVal = color + "§c⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜⬜";
		}else if(value >= 41 && value <= 50) {
			retVal = color + "§c⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜⬜";
		}else if(value >= 51 && value <= 60) {
			retVal = color + "§c⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜⬜";
		}else if(value >= 61 && value <= 70) {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜⬜";
		}else if(value >= 71 && value <= 80) {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜⬜";
		}else if(value >= 81 && value <= 90) {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛⬛⬛§e⬛§a⬜";
		}else if(value >= 91 && value <= 100) {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛⬛⬛⬛§e⬛";
		}else {
			retVal = color + "§c⬛⬛⬛⬛⬛⬛⬛⬛⬛⬛";
		}
		return retVal;
	}
	
	private static String igpre(Player p) {
		String prefix = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replaceAll("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			String pre = rs.getString("userprefix");
			if(pre.equalsIgnoreCase("RESET")) {
				prefix = "";
			}else {
				prefix = pre;
			}
			rs.close();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return prefix;
	}
	
	private static boolean isAFK(Player p) {
		boolean boo = false;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			boo = rs.getBoolean("afk");
			rs.close();
		    ps.close();
		}catch (SQLException e) { e.printStackTrace(); }
		return boo;
	}
	
	public Team getTeam(Scoreboard sb, String Team, String prefix, ChatColor cc) {
		Team team = sb.registerNewTeam(Team);
		team.setPrefix(prefix);
		team.setColor(cc);
		return team;
	}
	
	private int getPlayers(String server) {
		int i = 0;
		PreparedStatement ps;
		try {
			ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_serverstats WHERE servername = ?");
			ps.setString(1, server);
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt("currPlayers");
			rs.close();
			ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}
	
	private int getSB(Player p) {
		int i = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_userstats WHERE uuid = ?");
			ps.setString(1, p.getUniqueId().toString().replace("-", ""));
			ResultSet rs = ps.executeQuery();
			rs.next();
			i = rs.getInt("scoreboard");
			ps.close();
			rs.close();
		}catch (SQLException e) { }
		return i;
	}
	
	private void setSB(Player p, int state) {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("UPDATE redicore_userstats SET scoreboard = ? WHERE uuid = ?");
			ps.setInt(1, state);
			ps.setString(2, p.getUniqueId().toString().replace("-", ""));
			ps.executeUpdate();
			ps.close();
			p.sendMessage(new APIs().prefix("main") + "Your Scoreboard-View has been changed back to the default view.\n§7Reason is, that §a%server §7hasn't Jobs available.");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	private String retDate(String format) {
		SimpleDateFormat time = new SimpleDateFormat(format);
		return time.format(new Date());
	}
	
	private int retRepsToday() {
		int reports = 0;
		try {
			String date = retDate("yyyy.MM.dd");
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports WHERE time_string = ?");
			ps.setString(1, date);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				reports++;
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			reports = 9999;
		}
		return reports;
	}
	
	private int retRepstotal() {
		int reports = 0;
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports");
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				reports++;
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			reports = 9999;
		}
		return reports;
	}
	
	private int retRepsTotCuSe() {
		int reports = 0;
		APIs api = new APIs();
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports WHERE server = ?");
			ps.setString(1, api.getServerName());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				reports++;
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			reports = 9999;
		}
		return reports;
	}
	
	private int retRepsTodCuSe() {
		int reports = 0;
		APIs api = new APIs();
		try {
			String csdate = retDate("yyyy.MM.dd");
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports WHERE time_string = ? AND server = ?");
			ps.setString(1, csdate);
			ps.setString(2, api.getServerName());
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				reports++;
			}
			rs.close();
			ps.close();
		}catch (SQLException e) {
			e.printStackTrace();
			reports = 9999;
		}
		return reports;
	}
	
	private String getRadio(String type) {
		String s = "";
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redifm_current WHERE id = ?");
			ps.setInt(1, 1);
			ResultSet rs = ps.executeQuery();
			rs.next();
			s = rs.getString(type);
			rs.close();
		    ps.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return s;
	}
	
	private String retLatestReport(String type) {
		String report = "";
		String csdate = retDate("yyyy.MM.dd");
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_reports ORDER BY id DESC");
			ResultSet rs = ps.executeQuery();
			rs.next();
			if(type.equalsIgnoreCase("reporter")) {
                if(rs.getString("time_string").equalsIgnoreCase(csdate)) {
                    report = rs.getString("user_reporter");
                }else {
                    report = "n/a";
                }
            }else if(type.equalsIgnoreCase("perpetrator")) {
                if(rs.getString("time_string").equalsIgnoreCase(csdate)) {
                    report = rs.getString("user_target");
                }else {
                    report = "n/a";
                }
            }else if(type.equalsIgnoreCase("reason")) {
                if(rs.getString("time_string").equalsIgnoreCase(csdate)) {
                    if(rs.getString("reason").length() <= 15) {
                        report = rs.getString("reason");
                    }else {
                        report = rs.getString("reason").substring(0, 16);
                    }
                }else {
                    report = "n/a";
                }
            }else if(type.equalsIgnoreCase("server")) {
                if(rs.getString("time_string").equalsIgnoreCase(csdate)) {
                    report = rs.getString("server");
                }else {
                    report = "n/a";
                }
            }else {
                report = "ERRORDEF1a";
            }
			rs.close();
			ps.close();
		}catch (SQLException e) {
			report = "ERRORDEF2a";
			e.printStackTrace();
		}
		return report;
	}
	
	private String retPrefix(String rank, String type) {
		String prefix = "";
		if(type.equalsIgnoreCase("prefix_chat")) {
			prefix = chatHM.get(rank);
		}else if(type.equalsIgnoreCase("prefix_tab")) {
			prefix = tabHM.get(rank);
		}
		return prefix.replace("&", "§");
	}
	
	public void downloadStrings() {
		try {
			PreparedStatement ps = MySQL.getConnection().prepareStatement("SELECT * FROM redicore_ranks");
			ResultSet rs = ps.executeQuery();
			tabHM.clear();
			chatHM.clear();
			while(rs.next()) {
				tabHM.put(rs.getString("rank"), rs.getString("prefix_tab"));
				chatHM.put(rs.getString("rank"), rs.getString("prefix_chat"));
			}
		}catch (SQLException e) {
		}
	}
	
	int cacheTimer = 0;
	
	public void sbSched(int delay, int periodsb, int periodot) {
		Serverupdater su = new Serverupdater();
		new BukkitRunnable() {
			@Override
			public void run() {
				su.updateServer();
				sbmain++;
				if(sbmain == 10) {
					sbmain = 0;
				}
				for(Player all : Bukkit.getOnlinePlayers()) {
					try {
						setScoreboard(all);
					} catch (IllegalStateException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IllegalArgumentException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}.runTaskTimer(Main.instance, delay, periodsb);
		
		new BukkitRunnable() {

			@Override
			public void run() {
				su.clearlag();
			}
		}.runTaskTimer(Main.instance, delay, periodot);
		
		new BukkitRunnable() {
			@Override
			public void run() {
				APIs api = new APIs();
				su.Serverrestarter();
				cacheTimer++;
				if(cacheTimer == 0) {
					cacheTimer = 0;
					api.loadConfig();
				}
				SimpleDateFormat time = new SimpleDateFormat("HH:mm:ss");
				String stime = time.format(new Date());
				for(Player all : Bukkit.getOnlinePlayers()) {
					all.setPlayerListHeaderFooter(api.returnStringReady(all, "scoreboard.playerlist.top").replace("|", "\n"), api.returnStringReady(all, "scoreboard.playerlist.bottom").replace("|", "\n").replace("%time", stime).replace("%servername", api.getServerName()).replace("%serverid", api.getServerId()));
				}
			}
		}.runTaskTimerAsynchronously(Main.instance, delay, periodot);
	}
}