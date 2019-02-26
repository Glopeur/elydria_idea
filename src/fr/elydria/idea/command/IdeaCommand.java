package fr.elydria.idea.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import fr.elydria.idea.Main;

public class IdeaCommand implements TabExecutor {

	Main plugin;

	public IdeaCommand(Main plugin) {
		this.plugin = plugin;
	}

	private String h1 = "/eidea new ", h2 = "/eidea help ", h3 = "/eidea list ", h4 = "/eidea show ",
			h5 = "/eidea delete ", h6 = "/eidea version ", d1 = "| Ajoute une idée dans la boite à idée.",
			d2 = "| Affiche cette page d'aide.", d3 = "| Affiche la liste des idée.",
			d4 = "| Affiche l'idée selectionnée.", d5 = "| Supprime l'idée selectionnée.",
			d6 = "| Affiche la version du plugin.", hs1 = "<idée> ", title = "§3---==={§aElydria Idea§3}===---",
			ver = "Version : ", verd = " by ", vers = "@Glopeur", list = "Total d'idée : ";

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {

		if (a.length == 0) {
			if (s instanceof Player) {
				argHelp(s, true);
			} else {
				argHelp(s, false);
			}
		}

		if (a.length == 1) {
			switch (a[0]) {
			case "new":
				if (s instanceof Player) {
					s.sendMessage("§cErreur : Indiquez votre idée.");
				} else {
					s.sendMessage("Erreur : Indiquez votre idée.");
				}
				break;
			case "help":
				if (s instanceof Player) {
					argHelp(s, true);
				} else {
					argHelp(s, false);
				}
				break;
			case "list":
				if (s instanceof Player) {
					argList(s, true);
				} else {
					argList(s, false);
				}
				break;
			case "show":
				if (s instanceof Player) {
					s.sendMessage("§cErreur : Indiquez le numéro de l'idée.");
				} else {
					s.sendMessage("Erreur : Indiquez le numéro de l'idée.");
				}
				break;
			case "delete":
				break;
			case "version":
				if (s instanceof Player) {
					argVer(s, true);
				} else {
					argVer(s, false);
				}
				break;
			}
		}

		if (a.length > 1) {
			switch (a[0]) {
			case "new":
				if (s instanceof Player) {
					argNew(s, true, a);
				} else {
					argNew(s, false, a);
				}
				break;
			case "show":
				try {
					
					int i = Integer.parseInt(a[1]);
					if (i > plugin.getInt("total")) { // SI TROP
						if (s instanceof Player) {
							s.sendMessage("§cErreur : Il y a " + plugin.getInt("total") + " idées.");
						} else {
							s.sendMessage("Erreur : Il y a " + plugin.getInt("total") + " idées.");
						}
					} else {
						if (i < 0) { // SI EN DESSOUS
							if (s instanceof Player) {
								s.sendMessage("§cErreur : Numéro éronné.");
							} else {
								s.sendMessage("Erreur : Numéro éronné.");
							}
						} else {
							if (plugin.getInt("total") == 0) {// SI PAS IDEE
								if (s instanceof Player) {
									s.sendMessage("§cErreur : Aucune idée dans la boite à idée.");
								} else {
									s.sendMessage("Erreur : Aucune idée dans la boite à idée.");
								}
							} else {
								if (i == 0) { // SI 0
									if (s instanceof Player) {
										s.sendMessage("§cErreur : Il ne peut pas y avoir de numéro 0.");
									} else {
										s.sendMessage("Erreur : Il ne peut pas y avoir de numéro 0.");
									}
								} else {
									if (s instanceof Player) {
										argShow(s, true, i);
									} else {
										argShow(s, false, i);
									}
								}
							}
						}
					}





				} catch (NumberFormatException e) {
					if (s instanceof Player) {
						s.sendMessage("§cErreur : Indiquez un numéro.");
					} else {
						s.sendMessage("Erreur : Indiquez un numéro.");
					}
				}
				break;
			}
		}

		return false;

	}

	private void argNew(CommandSender s, boolean b, String[] a) {
		if (b) {
			String res = "";
			for (int i = 1; i < a.length; i++) {
				String txt = "";
				if (i == a.length - 1) {
					txt = a[i];
				} else {
					txt = a[i] + " ";
				}
				res = res + txt;
			}
			plugin.setIdeaText("idea" + (plugin.getInt("total") + 1) + ".author", s.getName());
			plugin.setIdeaText("idea" + (plugin.getInt("total") + 1) + ".text", res);
			plugin.setIdeaNumber("total", plugin.getInt("total") + 1);
			s.sendMessage("§aVotre idée a bien été prise en compte et sera traitée par nos soins.");
		} else {
			String res = "";
			for (int i = 1; i < a.length; i++) {
				String txt = "";
				if (i == a.length - 1) {
					txt = a[i];
				} else {
					txt = a[i] + " ";
				}
				res = res + txt;
			}
			plugin.setIdeaText("idea" + (plugin.getInt("total") + 1) + ".author", s.getName());
			plugin.setIdeaText("idea" + (plugin.getInt("total") + 1) + ".text", res);
			plugin.setIdeaNumber("total", plugin.getInt("total") + 1);
			s.sendMessage("Votre idée a bien été prise en compte et sera traitée par nos soins.");
		}

	}

	@Override
	public List<String> onTabComplete(CommandSender s, Command c, String l, String[] a) {
		List<String> res = new ArrayList<>();
		switch (a.length) {
		case 1:
			res.add("new");
			if (s.hasPermission("elydria.idea.list") || s.hasPermission("elydria.idea.*")
					|| s.hasPermission("elydria.*") || s.hasPermission("*") || s.isOp()) {
				res.add("list");
			}
			if (s.hasPermission("elydria.idea.show") || s.hasPermission("elydria.idea.*")
					|| s.hasPermission("elydria.*") || s.hasPermission("*") || s.isOp()) {
				res.add("show");
			}
			if (s.hasPermission("elydria.idea.delete") || s.hasPermission("elydria.idea.*")
					|| s.hasPermission("elydria.*") || s.hasPermission("*") || s.isOp()) {
				res.add("delete");
			}
			res.add("help");
			res.add("version");
			break;
		case 2:
			if(a[0].equals("show")) {
				if(plugin.getInt("total") >= 1) {
					for(int i = 0; i < plugin.getInt("total"); i++) {
						if(i > 0) {
							res.add("" + i);
						}
					}
				}
			}
			if(a[0].equals("delete")) {
				if(plugin.getInt("total") >= 1) {
					for(int i = 0; i < plugin.getInt("total"); i++) {
						if(i > 0) {
							res.add("" + i);
						}
					}
				}
			}
			break;
		}
		return res;
	}

	private void argHelp(CommandSender s, boolean b) {
		if (b) {
			s.sendMessage(title);
			s.sendMessage("§b" + h1 + "§3" + hs1 + "§7" + d1);
			s.sendMessage("§b" + h2 + "§7" + d2);
			if (s.hasPermission("elydria.idea.list") || s.hasPermission("elydria.idea.*")
					|| s.hasPermission("elydria.*") || s.hasPermission("*") || s.isOp()) {
				s.sendMessage("§b" + h3 + "§7" + d3);
			}
			if (s.hasPermission("elydria.idea.show") || s.hasPermission("elydria.idea.*")
					|| s.hasPermission("elydria.*") || s.hasPermission("*") || s.isOp()) {
				s.sendMessage("§b" + h4 + "§7" + d4);
			}
			if (s.hasPermission("elydria.idea.delete") || s.hasPermission("elydria.idea.*")
					|| s.hasPermission("elydria.*") || s.hasPermission("*") || s.isOp()) {
				s.sendMessage("§b" + h5 + "§7" + d5);
			}
			s.sendMessage("§b" + h6 + "§7" + d6);
		} else {
			s.sendMessage(" ");
			s.sendMessage("Elydria Idea command list :");
			s.sendMessage(h1 + hs1 + d1);
			s.sendMessage(h2 + d2);
			s.sendMessage(h3 + d3);
			s.sendMessage(h4 + d4);
			s.sendMessage(h5 + d5);
			s.sendMessage(h6 + d6);
		}
	}

	private void argList(CommandSender s, boolean b) {
		int i = plugin.getInt("total");
		if (b) {
			if (i == 0) {
				s.sendMessage("§7" + list + "(§c" + i + "§7)");
			} else {
				s.sendMessage("§7" + list + "(§a" + i + "§7)");
				String res = "";
				for (int y = 1; y <= i; y++) {
					String txt = "";
					if (y == i) {
						txt = "§7(§a" + y + "§7-§b" + plugin.getString("idea" + y + ".author") + "§7)";
					} else {
						txt = "§7(§a" + y + "§7-§b" + plugin.getString("idea" + y + ".author") + "§7) ";
					}
					res = res + txt;
				}
				s.sendMessage(res);
			}
		} else {
			if (i == 0) {
				s.sendMessage(list + "(" + i + ")");
			} else {
				s.sendMessage(list + "(" + i + ")");
				String res = "";
				for (int y = 1; y <= i; y++) {
					String txt = "";
					if (y == i) {
						txt = "(" + y + "-" + plugin.getString("idea" + y + ".author") + ")";
					} else {
						txt = "(" + y + "-" + plugin.getString("idea" + y + ".author") + ") ";
					}
					res = res + txt;
				}
				s.sendMessage(res);
			}
		}

	}

	private void argShow(CommandSender s, boolean b, int i) {
		if (b) {
			s.sendMessage("§7Auteur : §b" + plugin.getString("idea" + i + ".author"));
			s.sendMessage("§7Idée §6" + i + " §7: §a" + plugin.getString("idea" + i + ".text"));
		} else {
			s.sendMessage("Auteur : " + plugin.getString("idea" + i + ".author"));
			s.sendMessage("Idée " + i + " : " + plugin.getString("idea" + i + ".text"));
		}
	}

	private void argVer(CommandSender s, boolean b) {
		if (b) {
			s.sendMessage(title);
			s.sendMessage("§7" + ver + plugin.getDescription().getVersion() + verd + "§o" + vers + "§7.");
		} else {
			s.sendMessage(" ");
			s.sendMessage("Elydria Idea version :");
			s.sendMessage(plugin.getDescription().getVersion() + verd + vers + ".");
		}
	}
}
