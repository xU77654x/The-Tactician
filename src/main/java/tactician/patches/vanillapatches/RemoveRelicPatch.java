package tactician.patches.vanillapatches;

import com.evacipated.cardcrawl.modthespire.lib.*;
import com.evacipated.cardcrawl.modthespire.patcher.PatchingException;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.relics.BlueCandle;
import com.megacrit.cardcrawl.relics.RunicDome;
import javassist.CannotCompileException;
import javassist.CtBehavior;
import tactician.relics.*;
import java.util.ArrayList;
import java.util.Iterator;
import static tactician.TacticianMod.globalRelics;
import static tactician.character.TacticianRobin.Meta.TACTICIAN;

@SpirePatch2(clz = AbstractDungeon.class, method = "initializeRelicList")
public class RemoveRelicPatch {
	@SpireInsertPatch(locator = Locator.class)
	public static void Insert() {
		if (!globalRelics && AbstractDungeon.player.chosenClass != TACTICIAN) {
			AbstractDungeon.relicsToRemoveOnStart.add(SeraphRobe.ID);
			AbstractDungeon.relicsToRemoveOnStart.add(Dracoshield.ID);
			AbstractDungeon.relicsToRemoveOnStart.add(Talisman.ID);
			AbstractDungeon.relicsToRemoveOnStart.add(KillingEdge.ID);
		}

		if (AbstractDungeon.player.chosenClass == TACTICIAN) {
			AbstractDungeon.relicsToRemoveOnStart.add(BlueCandle.ID); // Blue Candle has a downside of losing HP with Hex / Anathemas; use Expiration for the choice instead.
			AbstractDungeon.relicsToRemoveOnStart.add(RunicDome.ID); // Take a wild guess.
			AbstractDungeon.relicsToRemoveOnStart.add("champ:DeflectingBracers"); // I don't want both Deflect and Counter used together.
			AbstractDungeon.relicsToRemoveOnStart.add("reliquary:ElizabethanCollar"); // Vampires event guarantees an Injury.
			AbstractDungeon.relicsToRemoveOnStart.add("reliquary:FishingReel"); // Applies a *debuff* to prevent infinite energy.
			AbstractDungeon.relicsToRemoveOnStart.add("HugYouRelics:RunicBlindfold"); // Same reason as Runic Dome.
		}

		for (String remove : AbstractDungeon.relicsToRemoveOnStart) {
			Iterator<String> s;

			for (s = AbstractDungeon.commonRelicPool.iterator(); s.hasNext(); ) {
				String common = s.next();
				if (common.equals(remove)) { s.remove(); }
			}
			for (s = AbstractDungeon.uncommonRelicPool.iterator(); s.hasNext(); ) {
				String uncommon = s.next();
				if (uncommon.equals(remove)) { s.remove(); }
			}
			for (s = AbstractDungeon.rareRelicPool.iterator(); s.hasNext(); ) {
				String rare = s.next();
				if (rare.equals(remove)) { s.remove(); }
			}
			for (s = AbstractDungeon.shopRelicPool.iterator(); s.hasNext(); ) {
				String boss = s.next();
				if (boss.equals(remove))  s.remove();
			}
			for (s = AbstractDungeon.bossRelicPool.iterator(); s.hasNext(); ) {
				String shop = s.next();
				if (shop.equals(remove)) { s.remove(); }
			}
		}
	}

	private static class Locator extends SpireInsertLocator {
		public int[] Locate(CtBehavior ctMethodToPatch) throws CannotCompileException, PatchingException {
			Matcher.MethodCallMatcher methodCallMatcher = new Matcher.MethodCallMatcher(ArrayList.class, "clear");
			return LineFinder.findInOrder(ctMethodToPatch, methodCallMatcher);
		}
	}
} // Credit to The Prismatic for this patch.