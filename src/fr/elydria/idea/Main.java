package fr.elydria.idea;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.elydria.idea.command.IdeaCommand;

public class Main extends JavaPlugin {

	private File ideaFile;
	private FileConfiguration idea;
	private IdeaCommand icommand = new IdeaCommand(this);

	@Override
	public void onEnable() {
		startFile();
		commandList();
		tabCompleteList();
	}

	@Override
	public void onDisable() {

	}

	private void commandList() {
		getCommand("elydriaidea").setExecutor(icommand);
	}

	private void tabCompleteList() {
		getCommand("elydriaidea").setExecutor(icommand);
	}
	
	public int getInt(String s) {
		int i = idea.getInt(s);
		return i;
	}
	
	public String getString(String s) {
		String str = idea.getString(s);
		return str;
	}

	public void setIdeaText(String s, String i) {
		idea.set(s, i);
		saveFile();
	}
	
	public void setIdeaNumber(String s, int i) {
		idea.set(s, i);
		saveFile();
	}
	
	private void startFile() {
		ideaFile = new File(getDataFolder(), "ideaList.yml");
		try {
			checkFile();
		} catch (Exception e) {
			e.printStackTrace();
		}
		idea = new YamlConfiguration();
		loadFile();
	}
	

	private void checkFile() {
		if (!ideaFile.exists()) {
			this.ideaFile.getParentFile().mkdirs();
			copyFile(getResource("ideaList.yml"), ideaFile);
		}
	}

	private void copyFile(InputStream is, File file) {
		try {
			FileOutputStream out = new FileOutputStream(file);
			byte[] buf = new byte[1024];
			int len;
			while ((len = is.read(buf)) > 0) {
				out.write(buf, 0, len);
			}
			out.close();
			is.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void loadFile() {
		try {
			idea.load(ideaFile);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void saveFile() {
		try {
			idea.save(ideaFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
