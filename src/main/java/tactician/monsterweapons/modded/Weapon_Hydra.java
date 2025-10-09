package tactician.monsterweapons.modded;

import com.evacipated.cardcrawl.modthespire.lib.SpireInsertPatch;
import com.evacipated.cardcrawl.modthespire.lib.SpirePatch;
import com.megacrit.cardcrawl.actions.common.ApplyPowerAction;
import com.megacrit.cardcrawl.dungeons.AbstractDungeon;
import com.megacrit.cardcrawl.monsters.AbstractMonster;
import tactician.character.TacticianRobin;
import tactician.powers.weapons.Weapon6FirePower;
import tactician.powers.weapons.Weapon7ThunderPower;

public class Weapon_Hydra {
	@SpirePatch(cls = "spireMapOverhaul.zones.invasion.monsters.Hydra", method = "getMove", requiredModId = "anniv6")
	public static class RavenousHunger {
		@SpireInsertPatch(rloc = 6)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon6FirePower(_inst))); }
		}
	}

	@SpirePatch(cls = "spireMapOverhaul.zones.invasion.monsters.Hydra", method = "getMove", requiredModId = "anniv6")
	public static class Swipe {
		@SpireInsertPatch(rloc = 4)
		public static void Insert(AbstractMonster _inst) {
			if (AbstractDungeon.player instanceof TacticianRobin) { AbstractDungeon.actionManager.addToBottom(new ApplyPowerAction(_inst, _inst, new Weapon7ThunderPower(_inst))); }
		}
	}
}