package tactician.events;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.events.beyond.MysteriousSphere;
import com.megacrit.cardcrawl.events.city.MaskedBandits;
import static tactician.TacticianMod.combatEventFix;

public class CombatEventFix {
	@SpirePatch(clz = MaskedBandits.class, method = "buttonEffect")
	public static class MaskedBanditsSoftlock {
		@SpireInsertPatch(rloc = 42)
		public static void Insert() { if (combatEventFix) { (AbstractDungeon.getCurrRoom()).smoked = true; }}
	}

	@SpirePatch(clz = MysteriousSphere.class, method = "buttonEffect")
	public static class MysteriousSphereSoftlock {
		@SpireInsertPatch(rloc = 40)
		public static void Insert() { if (combatEventFix) { (AbstractDungeon.getCurrRoom()).smoked = true; }}
	}
}