package tactician.util;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.characters.AbstractPlayer;
import com.megacrit.cardcrawl.core.CardCrawlGame;
import com.megacrit.cardcrawl.core.Settings;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.helpers.PowerTip;
import com.megacrit.cardcrawl.relics.AbstractRelic;
import com.megacrit.cardcrawl.rooms.AbstractRoom;
import com.megacrit.cardcrawl.ui.buttons.ProceedButton;
import com.megacrit.cardcrawl.unlock.UnlockTracker;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import java.util.ArrayList;
import java.util.HashMap;

public class FragileRelics {
	public static ArrayList<AbstractRelic> relicsToAdd = new ArrayList<>();
	public static ArrayList<AbstractRelic> relicsToRemove = new ArrayList<>();
	public static ArrayList<AbstractRelic> fragileRelics = new ArrayList<>();

	public static void obtainFragileRelic(AbstractRelic relic) {
		relic.tips.add(new PowerTip("Fragile", "Removed at the end of combat."));
		fragileRelics.add(relic);
		relicsToAdd.add(relic);
	}

	public interface OnFragileRelic { void onEquipDuringCombat(); }

	@SpirePatch(clz = AbstractRoom.class, method = "updateObjects", paramtypez = {})
	public static class OnUpdateObject {
		public static void Prefix() {
			boolean removedRelics = false;
			while (!FragileRelics.relicsToRemove.isEmpty()) {
				AbstractRelic relicToRemove = FragileRelics.relicsToRemove.get(0);
				relicToRemove.isObtained = false;
				AbstractDungeon.player.relics.remove(relicToRemove);
				(AbstractDungeon.getCurrRoom()).relics.remove(relicToRemove);
				relicToRemove.onUnequip();
				for (int i = 0; i < CardCrawlGame.metricData.relics_obtained.size(); i++) {
					HashMap entry = CardCrawlGame.metricData.relics_obtained.get(i);
					if (entry.containsKey("key") && entry.get("key").equals(relicToRemove.relicId)) {
						CardCrawlGame.metricData.relics_obtained.remove(i);
						break;
					}
				}
				removedRelics = true;
				FragileRelics.relicsToRemove.remove(0);
			}
			if (removedRelics && AbstractDungeon.topPanel != null) {
				int i = 0;
				float START_X = 64.0F * Settings.scale;
				float PAD_X = 72.0F * Settings.scale;
				for (AbstractRelic r : AbstractDungeon.player.relics) {
					r.currentX = START_X + i * PAD_X;
					i++;
				}
				AbstractDungeon.topPanel.adjustRelicHbs();
			}
			while (!FragileRelics.relicsToAdd.isEmpty()) {
				AbstractRelic relicToAdd = FragileRelics.relicsToAdd.get(0);
				AbstractDungeon.getCurrRoom().spawnRelicAndObtain(Settings.WIDTH / 2.0F, Settings.HEIGHT / 2.0F, relicToAdd);
				CardCrawlGame.metricData.addRelicObtainData(relicToAdd);
				UnlockTracker.markRelicAsSeen(relicToAdd.relicId);
				if (relicToAdd instanceof OnFragileRelic && Wiz.isInCombat()) { ((OnFragileRelic)relicToAdd).onEquipDuringCombat(); }
				FragileRelics.relicsToAdd.remove(0);
			}
		}
	}

	@SpirePatch(clz = AbstractPlayer.class, method = "onVictory")
	public static class OnVictoryHook {
		public static void Prefix(AbstractPlayer __instance) {
			relicsToRemove.addAll(FragileRelics.fragileRelics);
			FragileRelics.fragileRelics.clear();
		}
	}

	@SpirePatch(clz = ProceedButton.class, method = "update")
	public static class OnProceedHook {
		@SpireInsertPatch(locator = OnProceedClickedLocator.class)
		public static SpireReturn<Void> Insert(ProceedButton __instance) {
			if (Wiz.isInCombat()) {
				AbstractDungeon.closeCurrentScreen();
				__instance.hide();
				(AbstractDungeon.getCurrRoom()).phase = AbstractRoom.RoomPhase.COMBAT;
				AbstractDungeon.overlayMenu.showCombatPanels();
				return SpireReturn.Return();
			}
			return SpireReturn.Continue();
		}

		private static class OnProceedClickedLocator extends SpireInsertLocator {
			public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
				Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(AbstractDungeon.class, "getCurrRoom");
				return LineFinder.findInOrder(ctMethodToPatch, new ArrayList<>(), methodCallMatcher);
			}
		}
	}
} // Credit to Captain Falcon, though the original code was buggy and needed clean-up.